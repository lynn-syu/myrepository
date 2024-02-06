https://meet.google.com/avo-dfdv-ort?hs=224

## CHR(因廢除此project,以下問題priority為'低') 
:::success
:heavy_exclamation_mark: 以下問題無解沒人可以問，但應該不影響程式運作，就pass
- bch pom.xml
:question: 確認一下使用 db-encrypt加了兩個(line.8, line.15)且版本不一樣?? 用法??
:question: 確認一下使用 kafka串接 & ip & 目的??
:question: 確認一下使用 jwt ??
- PostgreSQL
:question: ovslxvlo01_00589362是誰?? 看起來db owner是他
:question: 目前DB是串哪一台? 似乎沒有看到對應的 HelmChart config
- EncryptionPropertyResolver
:question: 是不是根本沒用到???
- CommonErrorHandler
:question: 是不是根本沒用到???
:::

## esign-bl
:::info
- [x] bl是什麼的縮寫? 另外有看到esign-fpt是? fpt-client 是在哪一個專案? 若出現問題該與誰討論?
Ans: bl= base layer，因為這一個專案的設計確實是第三方服務的一個接口。fpt是第三方簽章服務，目前由TPI接洽，相關的呼叫規格也只有他們知道(TPI和他們有簽約，所以如果FPT有任何改動都會通知他們)

- [x] exception編號 -1 -2有特別的規劃分類? 
```java=
throw new InternalServerErrorException("ESIGN-B-PREPCERTO001-1", "fpt-handshake-error", e);

throw new InternalServerErrorException("ESIGN-B-PREPCERTO001-2", "encryption-error", e);
```
Ans: 都是由TPI自行規劃，並沒有特別分類，各自的描述寫在業務中台_專案系統文件_V2.0
![](https://hackmd.io/_uploads/S1gLpDM92.png)

- [x] 承上題，RETURNDESC 會根據locale 轉成 messages/EN, TW ?? 這有特別規劃? 怎麼沒有越文?
Ans: 會轉換成中文和英文，由TPI自行規劃exception的類別(當初國泰沒有和他們討論這一塊)。
之後會用一份語料Execl(包含中文 英文 越文)在CICD過程中轉成一份檔案分在resource裡面，利用corpus code對應到這份靜態檔案，就可以mapping出所有的message資訊

- [x] (請教通路) esign裡的api 呼叫的順序與使用情境?
:bulb: esign廠商也是fpt server

step1: App把客戶資料丟給esign進行憑證生成: postApiEsignBPrepcerto001 (POST) (/api/ESIGN-B-PREPCERTO001)=>產生一個agreement UUID==>回傳給APP(包含expire date)

step2: APP將合約原始檔和UUID丟給esign，產生組合的簽章檔案(存在fpt端)，但此時fpt不會直接提供檔案，會由fpt發送OTP (authorize)給客戶，讓客戶去確認他有執行進行簽章的動作: postApiEsignBPrepfileo001 (POST) (/api/ESIGN-B-PREPFILEO001)=> API回傳能識別這個otp的bill code

step3: 客戶從APP輸入了由fpt發送的簡訊裡寫的OTP code+APP提供識別碼(bill code)一起丟給esign: postApiEsignBAuthorizo001 (POST) (/api/ESIGN-B-AUTHORIZO001)=>由fpt驗證資料無誤後，會回傳API簽章檔案，再return給APP

如果客戶沒收到fpt寄發的簡訊，就會由APP提供那一組 agreement UUID 再次請求 FPT 重新發送 OTP code: postApiEsignBRgenautho001 (POST) (/api/ESIGN-B-RGENAUTHO001)=> API回傳能識別這個otp的bill code 再接續step3


- [x] (請教TPI) 這段code需要刪除?
![](https://hackmd.io/_uploads/Sk4S37G92.png)
目前會直接寫在程式端是因為應用方就是越南，在呼叫FPT所需要的參數就是那些內容。
註解的部分是多餘的，目前沒有特殊需求是不會動的=>Daily review 再向文寶等人提出詢問，是否請TPI修改

- [x] (請教TPI) 在處理呼叫FptApiClient後response的解密過程，若有Exception竟然是印出log???再回傳null就結束??? 這樣會影響後續流程嗎?
![](https://hackmd.io/_uploads/HkBWpmz93.png)
若解密失敗了就沒辦法走下去，目前規劃留log，然後由TPI在向FPT Server去詢問會失敗的原因，目前沒有辦法有其他動作

- [x] (請教TPI) postApiEsignBPrepfileo001中
1. 有一個欄位為 MessagingMode 設定為ASYNCHRONOUS_CLIENTSERVER 的意思?
A:這是呼叫FPT既定的規格，目前就跟著寫而已
```java=
// line.132
prepareFileSignCloudRequestDTO.setMessagingMode(PrepareFileSignCloudRequestDTO.MessagingModeEnum.NUMBER_1);
```
2. 這個todo 還需要修改?
現有情況不會再做修改。現在的esign動作會出現在兩個情境(徵審+動撥)，每一次都會有兩份簽約文件，一份是同意使用esign(給FPT的，裡面包含有客戶同意的簽名)，另一份是國泰提出的文件(裡面包含有國泰立約人簽名以及客戶同意的簽名)

![](https://hackmd.io/_uploads/ByX367fc2.png)

3. 這個exception代碼是不是有誤? (line.197)
確認要回同一個名稱，再請TPI修改
![](https://hackmd.io/_uploads/H1jyCXz5n.png)
:::

## msg-center
:::success
- [ ] 需要找人申請開通這個的閱讀權限，是TPI 更新規格文件的地方 => 先pending 
https://aiot-vn-loancloud-gitlab.tpisoftware.net/root/vn-loancloud-middle-platform-doc

- [x] postgreSQL
1.有使用 execution_record, pubsub_log這兩個table，但沒看到他們的init sql???
A: 這兩張table是全域性使用的，所以其init sql可能在其他地方就執行了

- [x] redis
1. 在本專案的用途? 
Ans: 水哥回答，執行cache機制，以pub/sub為例，因為pub/sub handler其實不只一個，所以透過redis來記錄每一筆訊息有被處理過，避免重複寄送
2. Db0 (@Primary), Db1, Db3 的使用場景?
A: 目前沒有太明確的分工，目前有一份文件(等上面的git lab開通)可以大概了解以下在幹嘛
==查看了config map，只有架設一台redis，切分成3個index==
db0RedisTemplate: AsyncMailHandler, AsyncNotificationHandler, AsyncSmsHandler
db1RedisTemplate: TimeShiftedOffsetRedisManager
db3RedisTemplate: PreventDuplicateRedisManager

- [x] (請教TPI) SendgridManager
寄信過程中若發生Exception: 這裡的GlobalExceptionHandler沒有implements ProblemHandling 這樣會有作用?????
A: TPI 需要再看一下程式確認問題，已經用demo project驗證了 不需等TPI
```java=
log.error("Failed to send email: {}", e.getMessage());
    throw Problem.builder()
            .withStatus(Status.INTERNAL_SERVER_ERROR)
            .withTitle(Status.INTERNAL_SERVER_ERROR.getReasonPhrase())
            .withDetail("Failed to send email.")
            .with("email", receiver)
            .build();
```

- [x] (請教TPI) TwilioManager
寄信過程中若發生Exception 看起來沒有特別處理? 這樣會以什麼方式回覆?
A: TPI認為問題不大，呼叫出錯了就會拋錯誤，catch的就可以看到錯誤，請他們提供exception回覆範例 >> 不用等了 這個問題有一部分也是因為需求說的不清楚，需額外處理
![](https://hackmd.io/_uploads/BJQ_OVMqh.png)

- [x] (請教TPI) JWT Token
請問 JwtAuthenticationToken 的使用時機?
A: 目前有掛keycloak的API才會使用JWT TOKEN，msg-center沒有用到此功能，就是廢code

- [x] (請教TPI) 目前寄信或是寄簡訊的controller，都有同步和非同步的api接口，目前都有在使用嗎? 若是同步寄送是什麼場景使用?
Ans: 水哥回答，一開始是國泰只跟TPI說要寄送東西，所以(POC)先開發出同步的方式，然後發現這樣不行，才又開發非同步，目前主要是非同步寄送，但不確定未來有沒有什麼場景會要在使用同步，所以不能清除code

- [x] (請教TPI) 在透過 pubsubManager.publishAsyncXXX() 之後會回傳messageId，為什麼有些api沒有透過messageId == null?去判斷有沒有發送成功????
A:目前沒有定義messageId==null這件事情，由不同PG開發才導致有落差，驗證依據為只要測試能過就OK，不特別判斷messageId是否有回傳，只要沒有exception發生就是有成功傳送...

- [x] (請教TPI+請教通路) 請問 otp table 的 tag欄位 是什麼定義? 如何使用? 好像沒有描述到...
A:計數上作分類，以達到業務方(通路)的要求，利於之後的歸類分析。
因為==通路的簡訊發送有卡規則==，例如綁定手機的一天內不能錯3次；或是註冊時的能有x次錯誤，目前規劃由msg-center幫忙計算次數，所以通路必須提供這一次的otp發送是屬於哪一個分類(tag)
根據eve說法，他認為這個規則不應該卡在msg-center....

- [x] (請教TPI) 所有的save table 若發生錯誤: 目前會有@Transactional 的rollback處理，那對應的exception會是什麼回覆?
A: 沒特別拋會被globalException接住(internalException)，基本來說會是500錯誤

- [x] (請教TPI) TimeManager (TimeShiftedOffsetRedisManager) 的使用邏輯?
A: 兩個功能
1.調整時區: 等貸款申請完後，會有撥款生效日的問題，透過這個時區調整，調整到intellect的時區。因為服務是部屬在台灣(+8)，但實際的使用方在越南(+7)，所以此功能是必須的
2.調整時間位移:基本上再測試環境才會用到，QA測試時比較會用(有開關可以設定，在PROD不會用到此功能)

- [x] (請教TPI) EmailController.postApiMsgcrBQymaillgq001 的查詢邏輯是txnseq 還是txnseq+apid?? 系統文件和code不一樣
A: txnseq+apid才是正確的，程式目前有誤，需要調整

NotificationController.postApiMsgcrBQyntfylgq001 也有一樣的問題
OtpController.postApiMsgcrBQyotplgq001
SmsController.postApiMsgcrBQysmslogq001
![](https://hackmd.io/_uploads/H1-OGOz9n.png)
![](https://hackmd.io/_uploads/H1v_z_f92.png)

- [x] (請教TPI) SmsController.postApiMsgcrBQysmslogq001 發現response好像沒寫完整?
A: TPI會再調整
![](https://hackmd.io/_uploads/By7fsNG93.png)

- [x] (請教TPI) TemplateController.postApiMsgcrBTemplateq002 系統文件都沒有描述，是不是沒在使用? 
A: 本意是需要知道系統目前新增了哪一些template，可透過這一支API取得資料，之前是for開發使用，
但在業務端中不見得有這樣的需求，可以先留著，以後可能有用到的機會
文件的部分要麻煩TPI再進行補充
:::