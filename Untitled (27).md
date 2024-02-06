### 服務架構及開發環境相關
1. backoffice-frontend 就是react那一包程式
2. backoffice所有權控都是設定於keycloak
3. backoffice-api只會打外部服務的message-center, 其他中台外部服務都沒有
4. 主要的外部服務都是由camunda去呼叫

- [x] 看HES project裡面有merchant-api & flyway<--這是甚麼?看你們的版本沒有,請問目前最新的branch版本? 
    - 原本用flyway做db版控,目前沒有使用了 (直接去pull最新的code)
    

### 程式相關討論
1. util project: 
    - 每個都是一個spring boot project 
    - 裡面都是子專案且依據功能做分類
2. Helmcharts裡面也是分 app, bo, camunda
    - Helmcharts主要針對CICD上板的設定(ex:xx.yml檔)
3. SQL資料夾是當DB有調整時會新增一個檔案並依照日期分類
4. HES根目錄有一個setting.gradle，需要把每個project都寫到這個檔案才可以使用(將project include)
    - 找到主專案(app-api,camunda,backoffice)的build.gradle 新增所需要的project(implementation project XXX)
5. JDBI不會幫忙自動生成對應的table mapping,只有檔名&純SQL
    - 根據檔案路徑與resource資料夾下的資料結構**一致**來mapping

:::success
程式啟動需要調整: 每個專案的properties都要改 DB & keycloak連線設定
:::


- [x] 有甚麼情境是要新增util?util怎麼定義規則是甚麼?
    - 當覺得整套工具不只對一個專案使用,就可以抽出來util
    - ==util資料夾的瘦身,待評估==
- [x] 為何util沒有寫properties卻可以知道吃那些config?
    - 會設定在上層(使用方)project的application.yml，util裡的專案就可以吃到設定
- [x] ExceptionHandler現在有AOP(實作)甚麼嗎?
    - 會後提供再討論 => 參考zalando-problem ( https://www.baeldung.com/problem-spring-web )

- [x] 可以找誰提供連線的URL(keycloak,db)
    - 會後提供再討論,找Water


### DataBase issue

- [x] 可大概說明哪幾張表比較重要?
    1. application: 每次進件都是一個application
        - 如果有串到外部資訊源,HES很喜歡用json format 存在DB一個欄位
    2. configuration: 系統的設定參數
        - (column)aml_check_duration: 設定retry的時間上限
            - 所謂的24小時並非一天,而是工作時間的24h,
            - 假設上班是九點到六點,一天就是指經過九小時
            - 並且會跳過假日，==因此最長可能需要話4~5天才能執行一次retry==
        - (column)manual_processing_enable: 打勾表示人工審核介入
    3. customer:一個客戶**只有一筆**客戶資料，但可能對應多筆的application
        - 現在的PRIMARY KEY是中台會給==一組隨機產生的 unique key(CUID)==
    4. notification_tamle:目前只包含簡訊跟APP推播

- [x] 給ER model
    - 進去postgre裡面有提供

- [x] 看所有行為都記audit_event,請問這個有規範資料要放多久?
    - house keeping工具 開發中->請問這個哪邊可以知道開發相關事項?找Water
    - 預計存30天，詳細的需要再談

7/6 回答
:bulb: HES, intellect並沒有定義錯誤代碼
:bulb: 目前有任何錯誤發生的log沒有做什麽特別處理就直接寫到GCP上
- [x] HES, intellect並沒有定義錯誤代碼，有任何錯誤發生的log沒有做什麽特別處理，請問這樣以後查找問題的方式?
    - HES:
        - 對外: 透過http status code 錯誤來做基本的判斷 ex: 404 505....
        - 對內:目前透過error  message來看錯誤在哪邊，規劃上目前沒有error code
    - intellect: 
        - 目前從經驗上來看通常是連線不到或格式不正確
        - 格式部分必須查log才知道原因
    - [x] 是否定義查錯SOP?  Ans:剛剛會議中有說明debug流程

:::success
若是自己建立一個postgre可以使用SQL資料夾內的init sql
:::

### Keycloak
- [X] 服務架構(PPT)紅色線跟IT比較沒關??全行資訊網設定?我們需要維護/設定keycloak?
    - Backoffice: Authentication -> Flow detail -> SSO -> SSO provide裡的資料 -> 對應的就是紅線的 國泰check token api

- [X] 甚麼是Grant Types?
    - 為OAuth2.0設定 :使用權限設定的種類(app-api為例)
    - application.yml : security->oauth2->client->registration->app XXX可看到授權類別(authorization-grant-type: client_credentials)
- [X] 新建realm,group,role要跟誰申請?
    - 找守敬閔富建置
- [ ] realm是如何跟project綁上權限關係? 例如設定config?or? 為何可以直接下@BackOfficeRealm
ref: https://www.baeldung.com/spring-boot-keycloak

- [X] 可以再解釋一下client對應的是哪件事情?
    - 提供服務用的帳號(EX:通路對app-api)
    - clients(app)->credential 帶上key才能夠call app-api 的API (透過swagger測試時也要帶入)

:bulb: realm settings->Tokens->欄位Access Token Lifespan: 設定token時間


### Camunda
- [X] 除create-application外還有幾個啟動流程的地方?
    - 還有backoffice後台，操作合約更版的動作，也會啟動流程: document template
      
      
- [X] call bpmn流程時方法? 
    - 都是使用runtimeService.startProcessInstanceByKey()

- [X] 都是靠打進來的API啟動流程還是有其他啟動方法?
    - 目前啟動都是靠API打進來的

- [X] DMN :Unique之外是否有用到其他方法?
    - 目前HES多使用 unique & first
    
- [X] 重新佈署流程?
    - 直接改相同路徑下的bpmn檔案做更新迭代，等下次上版再一起更新

- [X] 重新佈署流程時原先在跑的流程會怎麼處理?
    - Camunda有自己的版控，在Camunda Admin Cockpit左上角有Definition Version可以看版本，已經在跑的不能做版本切換
    - 要注意既有的流程衝突問題

- [X] 針對Camunda Admin有更詳細操作說明?
    - 主要使用Cockpit 
    - 可點deployments查看流程是否部屬成功

- [X] create-application.bpmn啟動流程時，協助解釋Asynchronous Continuation->before/after的使用? 
- [X] 呈上，如果發生exception，對應的trsaction roll back範圍?
==TPI: before,after決定每一次流程的保存點(角度是依照選擇的流程來定)
例如有一個decision/function
如果選擇before 當下有錯的decision/function會重新作執行
選擇after 錯誤也一漾停在decision但是再往下執行時流程會往下跑，並不會重新執行decision==

:::info
- 流程部屬 : 直接放在resource下
    - 可以到cockpit的deployed-deployments查看有沒有剛剛加入的bpmn (有version可分辨)
    - Cockpit->Deployments 右上角有download可選擇部屬的版本，再利用modeler打開就可以確認詳細內容

- incidents裡面記錄的就是exception留下的log (不用跑到gcp查看較為快速)
:::


### 程式開發

### Camunda mock 
- [X] 到時候轉換成真實資料要怎麼驗證??除了本機開發階段外其他mock都會是false
- [X] 是不是全部改成false(PRD)? yes 在執行CICD時(Helmcharts)會再被覆蓋一次


### 客製模組
==代表HES針對國泰額外做開發，TPI加以維護修改==


### 監控、管理、Debug
基本查詢條件:
```yaml=
# 冒號是模糊查詢
labels.k8s-pod/app = "backoffice-api"      # 查一個
labels.k8s-pod/app = ("backoffice-api" OR "app-api") # 查兩個

severity >= "WARNING"

timestamp > "2023-07-07T02:03:24:334Z"

jsonPayload.message = "一模一樣的字串內容"
jsonPayload.message : "有包含字串內容 (模糊查詢)"

trace : "輸入trace id做模糊查詢"
```

- 如何知道GCP裡面的log的內容是從哪邊制定的? 
Ans: HES Util-Logback: logback-spring.xml有做設定

- 結合spring cloud，透過API觸發的話在GCP上都會有一個trace id
    - 通常徵審相關問題，就會由通路那裏拿到trace欄位裡的 trace id 來一條龍做查詢
![](https://hackmd.io/_uploads/Sy5BW-Stn.png)


- 查找camunda相關問題
1. backoffice-front Audit頁面
2. camunda UI -> variables -> error欄位會有訊息
3. camunda UI -> incidents ->如果沒有包到try catch(非預期錯誤)，則在incidents可以找到此log
4. 若是由camunda打外部服務的資料，會記錄在 feign_client_record: 透過發生錯誤的那個流程的 instance ID(每次執行都會不一樣) 下條件 對應的式 mdc.processInsranceId欄位，得到該次請求的req, res資訊
    - 查詢範例 : 
![](https://hackmd.io/_uploads/HJV3VZBFn.png)

- log紀錄的層級分配
    1. 呼叫外部服務的要存到 feign_client_record (資安考量)
    2. 其餘在程式面寫下的log都會存到對應的gcp 

