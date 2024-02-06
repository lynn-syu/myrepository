[toc]

## Overall
:::success
### 示意圖
![](https://hackmd.io/_uploads/Hk_HaaYTh.png)

### Data-api
1. HttpLogWriter(req & res) DB Log 紀錄: tb_io_log - id(Pk), log(json string), create_datetime(yyyy-mm-dd hh:mm:ss)
這一個table沒有traceId紀錄? 目前沒有
2. SPRING_PROFILES_ACTIVE: "gcp" (查看application-gcp.yml 結合 Helmchart)
:bulb: 水哥要再調整一次
4. Pub/Sub 紀錄: 
    - DB Log 紀錄: tb_sub_message_id - message_id, sub_topic, hostname, created_datetime
    - GCP Log 紀錄: log.info pub & sub 訊息相關內容
5. 請問 GenTestDataService 的使用情境? for mock，後續會刪掉
6. IntellectController & LoanController 已經移除嗎? 移除了
7. 請問 Customerinfo==na==Controller 的三隻api 是重複的並且使用假資料? yes
8. API執行過程中發生exception:
    - 有log.error 與對應的 CommonResponseStatus
9. 如果要再local啟動，需要確認以下打勾參數:
![](https://hackmd.io/_uploads/ry0OsRFT3.png)

### Telco-api
1. HttpLogWriter(req & res) DB Log 紀錄: tb_io_log - id(Pk), log(json string), create_datetime(yyyy-mm-dd hh:mm:ss) 沒有traceId
2. SPRING_PROFILES_ACTIVE: "gke" (查看application-gke.yml 結合 Helmchart)
3. TecloCacheEvictConfig的功能? 有一張table tb_setting為了不要一直去查詢，固定每天半夜查一次然後就把資料cache
4. 呼叫外部(feign_client)有db log紀錄嗎? 透過interceptor的加入也是寫到tb_io_log 遇到FeignException的處理方式? 因為feign的req,res都有紀錄了，所以在service會把feignException接起來轉換成 CommonResponseStatus 再回覆
5. MessageCenterController的功能? 目前因為合約還沒簽好因應而生的mock api
:::

## telco-api 電信分數 (對電信業者)
:::info
- 此服務有誰在call? A:Camunda,VMB的APP-bff 
- 提供五支api 給外部使用(/queryTelCo,/sendOTP,/verifyOTP,/queryFraudScore,/queryCreditScore)
- Exception handle : 
CommonResponse=>CommonResponseHeader 裡面status code做判斷
目前定義如下:
  E000("0000", "交易成功"),
  E001("E001", "Success but no data"),
  E002("E002", "Time out"),
  E003("E003", "Customer info error"),
  E500("E500", "System error");
- call 外部是走feign client : 
- 服務要去call誰? 
    - vnpt : 直接打中華電信(自己走的一套)
 request : VnptDto<T> otpDto = new VnptDto<T>()
 response: VnptRelayDto<T> otpRelayDto = vnptClient.sendOTP(otpDto);
     - ts: 現在沒用
     - datanest : 電信代理商去打遠傳,台哥大...等
 request:  DataNestOTPDto otpDto = new DataNestOTPDto();
 response : DataNestRelayDto<T> otpRelayDto = null
::: 
 
## data-api 信評分數
:::info
- 提供12支api給外部使用:
/queryCicS37,/queryCicS11A,/queryNonCic,"/queryTS,/queryTS_CS,/queryTS_FS,/internalCheck,/isEmployee,/isStakeholder,/queryPCB,/queryControlList,/otherCalculation
- 這幾支都是到DB取資料，請問DB是哪一台? 
Ans : data-api Data base
- DB的資料來源是誰給的?誰CRUD資料到DB?
Ans : 外部會打data-api
    - api先寫 UUID 和 基本資料到data-api DB 的table，組合request+UUID 一起publish
    - 透過pub/sub將這一筆紀錄推到GCP
    - 數據中台會consume pub/sub 知道要幫忙呼叫第三方查資料
    - 待數據中台查完資料(含call第三方)後會將資訊寫一筆訊息在pub/sub
    - 於此期間，data-api subscription一直持續訂閱著，當收到一筆資料就會經過轉換(包含UUID)，已update的方式更新到 data-api DB 的table
 
- Exception handle : 
CommonResponse=>CommonResponseHeader 裡面status code做判斷
目前有定義 如下
  E000("0000", "交易成功"),
  E001("E001", "Success but no data"),
  E002("E002", "Time out"),
  E003("E003", "External Service is down"),
  E004("E004", "Network is down"),
  E102("E102", "檢核錯誤"),
  E999("E999", "System Error");
    
有看到兩支controller目前應該是取假資料 : ==目前這兩支controller沒在使用==
- IntellectController(3 api) 
     - getLimits() return LimitRelayDto
     - getBills() return BillRelayDto
     - getLoans() return LonaRelayDto
- LoanController
     - getCredit() return CreditRelayDto

### data-api impl流程範例:
![](https://hackmd.io/_uploads/rJHyMT_in.png)
- 紅框第一行 tbCicS37HistoryRepository.save(newTbCicS37History);是外部打data-api時先寫一筆記錄到data-api table
- 透過publishCustomerInfo("01", dto, uuidStr);將資料pub/sub到GCP
- entityManager.unwrap(Session.class).clear(); 是要清除entity的session以利抓到table 最新的資料
- 綠框for loop 是持續到data-api DB的table去撈取該筆row是不是已經更新
頻率為十秒一次一共撈六次，有撈中就break掉，再return respone

:::  
    
## 補充
所謂PCB,Kalapa是信評分數的代理商 
![](https://hackmd.io/_uploads/B195bk9jn.png)
    
![](https://hackmd.io/_uploads/BJbe2WyAh.png)
