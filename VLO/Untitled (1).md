:question: 了解此project使用時間點&目的
Ans: 目前僅提供給通路使用
:question: bl是什麼的縮寫? 另外有看到esign-fpt是?
Ans: bl= base layer，因為這一個專案的設計確實是第三方服務的一個接口。fpt是第三方簽章服務，目前由TPI接洽，相關的呼叫規格也只有他們知道(TPI和他們有簽約，所以如果FPT有任何改動都會通知他們)
:question: exception編號 -1 -2是什麼意思? 
A: 沒意義
:question: RETURNDESC 會根據locale 轉成 messages/EN, TW ???
A: 以後會由另一份檔案去維護

[toc]

### config && sql
:::info
- 有一份基礎的 helmchart 設定 (configmap, deployment, values)
- 該專案的postgreSQl owner to cloudsqlsuperuser
1.otp_template: oid (自增) as PK

![](https://hackmd.io/_uploads/SJfiDXBKh.png)
:::

### message & json-files
:::info
- messages 有中英翻譯
- json檔的用途: 用來給 MockEsignServiceImpl 解析回應

![](https://hackmd.io/_uploads/Sy-SCQrKh.png)
:::

### exception
:::info
@ControllerAdvice Handler : GlobalExceptionHandler.java

有以下的分類:
* MethodArgumentNotValid: 處理Request參數有誤，歸類為  CommonResponseMessage CLIENT_ERROR，回傳 status code 400 (BAD_REQUEST) 以及參數錯誤原因

- BadRequestException: 處理用戶端相關錯誤，回傳 status code 400 (BAD_REQUEST)
- ConflictException: 處理用戶端相關錯誤，回傳 status code 409 (CONFLICT)
- ForbiddenException: 處理邀請碼相關錯誤，回傳 status code 403 (FORBIDDEN)
- InternalServerErrorException: 處理未能識別的例外，一律視為系統錯誤並回傳 HTTP status code 500 (INTERNAL_SERVER_ERROR)
- Exception: 處理未能識別的例外，一律視為系統錯誤並回傳 HTTP status code 500 (INTERNAL_SERVER_ERROR)

* FeignException:  Handle All Feign Client Exception，依據原本的status code回傳，若沒有則以 500 (INTERNAL_SERVER_ERROR)

處理過程:
1. 以error層級將log印出，內容包含有 Trace ID %s, Error message %s
2. 組裝exceptionDTO: 包含從req Header取資料 + 解析error code + error msg 
3. 以當下時間為戳記 + http_status_code + exceptionDTO 同時存入 execution_record table 並進行回傳
:::

==使用順序: PREPCERT -> PREPFILE -> AUTHORIZ (詳細說明 參考VCP問題集)==
:bulb: API HttpStatus 預設為 NOT_IMPLEMENTED, 成功執行則為 OK
![](https://hackmd.io/_uploads/r16bBz052.png)


## postApiEsignBPrepcerto001 (POST) (/api/ESIGN-B-PREPCERTO001)
:::success
> 用途: create a new account on Remote Signing service and request to CA for preparing digital certificate

- 處理req資料:
    - 填資料: 讀取自config檔 fpt.esign.credential系列
    - 這是要刪除的??? TPI說不改
    ![](https://hackmd.io/_uploads/S16fFEBY2.png)
    - 準備 CredentialData: 
        - 產生以時間戳為基礎的簽核資料
        - 去GCP Secret Manager拿keystore資料
        - getPKCS1Signature: 取得privateKey，將簽核資料轉為Signature
        - :warning: 過程中發生任何錯誤都會 throw new InternalServerErrorException("ESIGN-B-PREPCERTO001-2", "encryption-error", e);
- 透過 fpt 生成 certificate
    - 將二次處理後的req資料轉為json
    - 自主產生AES key & iv 並進行 req json 的 AES 加密
    - 將自主產生的 AES key & iv 進行 RSA 加密
    - 呼叫 FptApiClient.postPrepareCertificateForSignCloud 
        - 傳入 req json (AES enc), AES key (RSA enc), AES iv (RSA enc)
            - :warning: 過程中發生FeignException 會轉換成 throw new InternalServerErrorException("500", "internal-server-error", e);
            - :warning: 其他錯誤都會 throw new InternalServerErrorException("ESIGN-B-PREPCERTO001-1", "fpt-handshake-error", e);
            - :warning: 呼叫後的 response 為null 也會 throw new InternalServerErrorException("ESIGN-B-PREPCERTO001-1", "fpt-handshake-error", e);
    - 解密 ResponseEntity<String>的結果
        - 從 header 取出 RSA 加密過的 AES key & AES iv
            - :warning: 若AES key或iv 任一不存在則會 throw new InternalServerErrorException("ESIGN-B-RGENAUTHO001", "fpt-handshake-error");
        - AES key & AES iv 進行 RSA 解密
            - 解密失敗的Exception是印出log，改為回傳null就結束
        - 從 body 取出 AES 加密過的資料，進行 AES 解密
            - 解密失敗的Exception是印出log，改為回傳null就結束
        - :warning: 過程中發生GeneralSecurityException 會轉為 throw new InternalServerErrorException("ESIGN-B-PREPCERTO001-2", "encryption-error", e);
    
- 組合資料回傳
    - MWHEADER 有 TraceId
    - TRANS 有解密後的String 轉型為 ==CertificateSignCloudResponseDTO==
:::

## postApiEsignBPrepfileo001 (POST) (/api/ESIGN-B-PREPFILEO001)
:::success
> 用途: 客戶向遠端發出 Sign Request -> Remote Signing 透過 SMS or Email 回覆客戶一組 Authorize Code 用來做後續的 authorize the client.

> This method also requires the binary of document which needs to be signed. ??????
    
- 處理req資料
    - 設定 AuthorizeMethod: 根據當前config檔 (1:SMS, 2:email, 3:mobile, 4:passcode, 5:UAF)
    - 設定 AuthorizeCode: null
    - 透過 req.NotificationTemplateCode 尋找 OtpTemplateEntity 取得 otp template
        - :warning: 找不到 entity 就會 throw new BadRequestException("ESIGN-B-PREPFILEO001-3", "invalid-template-code")
    - 根據當前 Locale 取得英文或越南文的 otp_template & otp_subject 並進行設定
    - 準備 CredentialData: 與第一支api過程相同
        - :warning: 過程中發生任何錯誤都會 throw new InternalServerErrorException("ESIGN-B-PREPFILEO001-2", "encryption-error", e);
    - 準備與設定 SignCloudMetaData:
        - 得到 ipAddress (req內的經度和緯度組合 or 空字串)
        - 設定借款人簽名面板顯示資訊 + 設定債權人簽名面板顯示資訊 (銀行)
    - 處理 MultipleSigningFileData: 步驟和 SignCloudMetaData 相同
    :question: 這個TODO???? TPI說不改
    ![](https://hackmd.io/_uploads/HJ2OfLrFn.png)

- 透過 fpt 生成 file
    - 呼叫(過程如第一支api)，此處的 exception 代號為 ESIGN-B-PREPFILEO001-X
    :question: 第197行是不是寫錯了?????? 等TPI修改
    ![](https://hackmd.io/_uploads/B1HzZISFn.png)
    - 解密 ResponseEntity<String>的結果(過程如第一支api)
        - :warning: 過程中發生GeneralSecurityException 會轉為 throw new InternalServerErrorException("ESIGN-B-PREPFILEO001-2", "encryption-error", e);
    
- 組合資料回傳
    - MWHEADER 有 TraceId
    - TRANS 有解密後的String 轉型為 ==PrepareFileSignCloudResponseDTO==
:::

    
## postApiEsignBAuthorizo001 (POST) (/api/ESIGN-B-AUTHORIZO001)
:::success
> 用途:客戶提供 Authorize Code to remote signing. 遠端服務會去驗證code然後回傳對應的signed document 

1. new **一個AuthorizeSignCloudRequestDTO**並根據需要set各個欄位資料    
其中authorizeSignCloudRequestDTO.setRelyingParty(credentialDataConfig.getRelyingParty());要到下圖取config
![](https://hackmd.io/_uploads/SyX2iytYn.png)
    
    
2. create credential data  
    - 透過 this.createCredentialData 得到一個CredentialDataDTO 並set到**authorizeSignCloudRequestDTO**
3. 透過EncryptionUtils.aesEncrypt()加密**AuthorizeSignCloudRequestDTO**
透過同樣的方式生了兩把KEY  
![](https://hackmd.io/_uploads/SJu00Qf9n.png)
    
4. new ResponseEntity call **fptApiClient.postAuthorizeCounterSigningForSignCloud()**
    
5. 透過this.decryptResponse()解析 return 回來的 responseEntity
    
6. create ***AuthorizeSignResponseDTO*** return 給前端
:::
           
## postApiEsignBRgenautho001 (POST) (/api/ESIGN-B-RGENAUTHO001)
:::success
> 用途: regenerate the authorization code

1. new 一個 RegenerateAuthorizationCodeForSignCloudRequestDTO 並根據需要set各個欄位資料 
2. regenerateAuthorizationCodeForSignCloudRequestDTO.setAuthorizeMethod()來set哪個認證方式   //1:SMS, 2:email, 3:mobile, 4:passcode, 5:UAF

3. OTP格式內容存放在DB_Table: otp_template
    
4. OTP語言則透過抓取LocaleContextHolder.getLocale()抓取，如果不是英文語系就將OTP title,content轉換成越南語
    
5. create credential data
透過 this.createCredentialData 得到一個CredentialDataDTO 並set到 regenerateAuthorizationCodeForSignCloudRequestDTO     

6. 上述資料整理完後，new ResponseEntity call fptApiClient.postRegenerateAuthorizationCodeForSignCloud()

7. 透過this.decryptResponse()解析 return 回來的 responseEntity        
:::