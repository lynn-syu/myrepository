### 功能
:::info
用以儲存客戶最新狀態，包含會員狀態(新戶、甄審中、通過甄審、同意合約、未通過甄審)、帳戶狀態(Freeze/Unfreeze)、帳戶凍結事件紀錄、客戶甄審結果紀錄以及客戶狀態變更記錄
:::

### config & sql
:::success
- 有一份基礎的 helmchart 設定 (configmap, deployment, values)
- 每一個專案應該都有一份init的folder來建立專案所需要的所有內容
    - SQL folder
1.cif: serial_no (自增) as PK
2.contract_information: application_id (就是contract id) as PK 
3.device_token: ==phone as PK==
4.execution_record: serno (自增) as PK，裡面存有 keycloak_id
5.freeze_list: serno (自增) as PK
6.quarterly_checklist: application_id (就是contract id) as PK 
:::

### CifManager
:::info
處理data-centralized 自己的 CIF table的讀寫 和 CifClient不同
:::

### 在DC串接的服務(以feign client的方式)
:::success
- intellect: 集大成
    - arx: 主要建立出 ParametersInquiryApiClient，相關的規格設定寫再ArxConfig.java，由 IntellectArxManager 實作了 Token Generation
    :question: 這一個todo還要處理嗎?
    ![](https://hackmd.io/_uploads/BJ3zkCm9n.png)

    - customer_information: 跟intellect拿資料 CifClient 取得客戶資訊，由 IntellectManager.java 實作 (需要透過上方取得token)
    ![](https://hackmd.io/_uploads/H1Wok0m93.png)

    
    - limit_inquiry: 都是跟intellect互動 :
        - InquireLimits (This API is used to Inquiry the Limits amount of the requested Customer number.)
        - Transaction (This service is used to freeze the limits set against a customer.)

- msg-center: 都有一個interface定義呼叫的介面 (和msg-center定義的相同)，再利用 MessageCenterManager將每一個client進行對應方法的實作
    - email: 實作 Asynchronously + Synchronously
    - notification: 實作 Asynchronously
    - sms: 實作 Asynchronously + Synchronously
    - template: 實作透過 templateCode 查詢template
:::

### Secret Manager
:::info
- 設定 EncryptionPropertyResolver: 使用了SecretManagerTemplate 來解析
![](https://hackmd.io/_uploads/S1svKTQ5n.png)
:::

### redis
:::success
此處使用了 redis db3負責 PreventDuplicate
:::

### exception
:::info
:bulb: 和esign-bl設計相同

- 透過GlobalExceptionHandler.java來做AOP，且有制定以下幾種錯誤的exception，再依據需求丟出相對應的Exception
    - BadRequestException
    - ConflictException
    - ForbiddenException
    - InternalServerErrorException

- GlobalExceptionHandler.java 是實作抽象 ResponseEntityExceptionHandler，並override method:handleExceptionInternal()，將此method改成符合需求的樣子
:::