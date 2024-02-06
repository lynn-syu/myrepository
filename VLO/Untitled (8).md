[toc]

:::info
:bulb: 此controller為通路使用! eg.客戶走忘記密碼流程，會走發送OTP流程
:ballot_box_with_check: 目前 postApiMsgcrBQyotplgq001 沒有使用了
:ballot_box_with_check: 只要是由通路發起的otp流程(esign由fpt發送的就不是)如下: 

1. 先call MSGCR-B-GTOTPCFGQ001 取得相關參數
2. 再call MSGCR-B-QYOTPLGQ002 取得當天發送紀錄
3. 根據發送紀錄檢查是否超過當天次數上限、以及是否已達可resend 的時間間隔
4. 都沒問題再call MSGCR-B-SSENDOTPO001 發送OTP
5. 客戶收到OTP code 回填APP 後就會call MSGCR-B-CHECKOTPO001 驗證OTP
:::

## postApiMsgcrBQyotplgq001 (POST) (/api/MSGCR-B-QYOTPLGQ001)
> 目的: Query the OTP by TXNSEQ

> call otpService.postApiMsgcrBQyotplgq001()
:::success
- 查Table: 透過 req.txnseq 查找 otp 是不是有這筆資料
:warning: 找不到則 throw new BadRequestException("MSGCR-B-QYOTPLGQ001-1", "token-not-found")

- return OtpQueryResponseDTO (有 MWHEADER 包含 traceId + TRANRS ~= OtpEntity)
![](https://hackmd.io/_uploads/BJL_7Oz53.png)
:::

## postApiMsgcrBQyotplgq002 (POST) (/api/MSGCR-B-QYOTPLGQ002)
> 目的: Query the OTP by request

>call otpService.postApiMsgcrQyotplgq002()
:::success
- 根據 req 組合查詢區間條件: startTime, endTime
- 根據 req.Channel 決定要找 sms (0) or mail (1)的資料
- 綜合 req 查詢條件 查找 otp table 得到 List<OtpEntity>
- return QueryOtpResponseDTO (有 MWHEADER 包含 traceId + TRANRS ~= List<OtpEntity>)
:::

## postMsgcrBSsendotpo001 (POST) (/api/MSGCR-B-SSENDOTPO001)
> 目的: Send an OTP
![](https://hackmd.io/_uploads/SJc_V_G9n.png)

>call otpService.postMsgcrBSsendotpo001
:::success
- 檢查Table: otp 是不是有存在這筆資料(apid,txnseq)
:warning: 若存在表示有重複則 throw new BadRequestException("MSGCR-B-SSENDOTPO001-7", "duplicated-txnseq")

:question: otp 的 tag欄位是什麼意思?
Ans: 因為==通路的簡訊發送有卡規則==，在計數上作分類，以達到業務方(通路)的要求，利於之後的歸類分析。
    
- 根據request給的NotifyChannel(1,2,3)欄位決定發送的種類 :warning: 非以下 throw new BadRequestException("MSGCR-B-SSENDOTPO001-2", "illegal-request-format")
    1. SMS+Mail: 
    2. SMS:
    3. MAIL: 

    - 流程:
        1. 檢查req 電話號碼 or/and email
            - :warning: 格式有誤 throw new BadRequestException("MSGCR-B-SSENDOTPO001-2", "illegal-request-format")
        2. 檢核 透過電話號碼的驗證次數 or/and 透過email的驗證次數
            - :warning: 超過 throw new BadRequestException("MSGCR-B-SSENDOTPO001-5", "opt-sending-times-meet-limit")
        3. 透過 templateCode 取出對應的模板 & 確認是否為apid legal 
            - :warning: 找不到 thrown new BadRequestException("MSGCR-B-SSENDOTPO001-4", "template-not-fount")
            - :warning: 樣板格式缺少OTP欄位 throw new BadRequestException("MSGCR-B-SSENDOTPO001-8", "invalid-template-content")
            - :warning: APID不允許使用此template throw new BadRequestException("MSGCR-B-SSENDOTPO001-6", "apid-no-permission-template-code")
        4. 組合 smsTemplate or/and mailTemplate
            - :warning: 轉換req param有異常 throw new BadRequestException("MSGCR-B-SSENDOTPO001-2", "illegal-request-format")
            - :warning: 填寫content有異常 throw new InternalServerErrorException("500", "template-parse-error", e)
        
        5. 送出 sms or/and mail
            - sms: twilioManager.sendSms(phoneNumber, content) 
                - :warning: 任何錯誤 throw new InternalServerErrorException("500", "sms-platform-failed", e)
            - mail: sendgridManager.sendMail(mail, title, content, null)
                - :warning: 任何錯誤 throw new InternalServerErrorException("500", "sendgrid-handshake-error", e)
 
- 新增一筆資料存入DB_Table: otp (hash the otp code with sha256)

- return sendOtpResponseDTO (有 MWHEADER 包含 traceId + TRANRS 包含 otp-code+transUuid+responseTimestamp )
```java=
// otp code format: 以0去填補的6位數數字
 private String getOtpNumberString() {
    int number = random.nextInt(999999);
    return String.format("%06d", number);
}
```
![](https://hackmd.io/_uploads/HkRsNuM92.png)
:::

## postMsgcrBCheckotpo001 (POST) (/api/MSGCR-B-CHECKOTPO001)
> 目的: Check the OTP Code

>call otpService.postMsgcrBCheckotpo001
:::success
- 查找 DB_Table: otp 利用 request 的 transUuid
:warning: 若找不到則 thrown new BadRequestException("MSGCR-B-CHECKOTPO001-5", "tranduuid-not-found")
:warning: 若找到的otp entity 為已驗證 (passed) throw new BadRequestException("MSGCR-B-CHECKOTPO001-8", "otp-verified")
:warning: 若找到的otp entity 已timeout (SentTimestamp) throw new BadRequestException("MSGCR-B-CHECKOTPO001-7", "otp-timeout")
:warning: 若找到的otp entity 超過驗證次數 (CheckedTimes) throw new BadRequestException("MSGCR-B-CHECKOTPO001-6", "opt-verify-times-meet-limit")

- 通過以上: checkedTimes+1,checkedtimestamp,updatedtimestamp欄位並存回DB

- 檢查 req.otp == otp entity otp ?  (更新passed = true ,updatedtimestamp欄位並存回DB) : throw new BadRequestException("MSGCR-B-CHECKOTPO001-4", "opt-verify-failed")

- return CheckOtpResponseDTO (有 MWHEADER 包含 traceId + TRANRS 檢核通過時間 )
![](https://hackmd.io/_uploads/B1WZ-_Gqh.png)
:::

## postApiMsgcrBGtotpcfgq001 (POST) (/api/MSGCR-B-GTOTPCFGQ001)
> 目的: Get Otp Config

>call otpService.postApiMsgcrBGtotpcfgq001
:::success
- return 一個OtpConfigResponseDTO，取得以下config參數 (是由hemlchart設定): 
1. otpCountLimit
2. otpTimeoutMinute
3. otpVerifyLimit
![](https://hackmd.io/_uploads/SJKlPk6t3.png)
:::

