- [x] 7/13 請教water: Helmchart, Keycloak, GCP
![](https://hackmd.io/_uploads/rkb9XORF3.png)

1. 說明Helmcharts & 提供連線的URL(keycloak,db)?
Helmcharts project是國泰用來管理每個環境config的解法
以HES專案為例，裡面目前提供了ut & uat環境的yaml設定，當要CD時，會讀取專案deployment.yml & configmap.yml檔案
(圖一)<font color="blue">{{ .Values.tfs.XXXX }} </font>的部分-->是吃ut/uat.yml裡面的設定(圖二)
:::info 
:question: 為甚麼是.Values開頭的路徑?
Ans: 這是國泰設計的，一律都是.Values開頭

圖一: 
![](https://hackmd.io/_uploads/B1n9D40Y3.png)

圖二:
![](https://hackmd.io/_uploads/BJ2LFERKn.png)


tip: 
![](https://hackmd.io/_uploads/Bk7XfdCYh.png)
:::

2. 看所有行為都記audit_event,請問這個有規範資料要放多久?
 - house keeping工具 開發中

3. HES & 全行資訊網設定?我們需要維護/設定keycloak?
:::success
- 設定要分兩塊看

**A.全行資訊網:** 只要是員工本來就會有帳號，需要先跟someone申請打開HES的功能在全行的表單才會有HES可點選(如下圖)
![](https://hackmd.io/_uploads/H1oPjNAFn.png)

**B.keycloak:**
keycloak server怎麼知道這個人是不是銀行員工呢?
Ans: 若有User需要使用，需要向someone提出申請，並在Keycloak建立相對應的帳號，在backoffice上面的role(maker,checker,admin...)也是設定在keycloak上面，再透過spring boot程式去讀取權限的部分。

- 情境: 
當User在全行資訊網點選HES後，會導頁到HES backoffice，導頁的過程中會到keycloak去取得身分的驗證，是透過User的帳號做mapping，依照裡面的設定賦予這個人對應的role & 權限。所以想要正常打開HES backoffice，需要==1.在全行有開通權限 (SSO負責員編和密碼的驗證 -> 會轉換成一組token)== ==2.在keycloak有建立帳號以及相對應的role設定==
:::

4. 透過SSO登入後->到keycloak建立 credential後還需要綁定(設定)甚麼才能使用backoffice
:::success
- 如何透過keycloak_id就可以mapping 出對應的 CustomerRealm UsersResource 資料??
Ans: 透過綁定特定的keycloak server，spring boot就可以得到相對應的realm,group...等等的資訊，再來就是讀書看怎麼使用keycloak裡面的method&parameter等等了
:::

5. 如果外部有系統有問題的話我們的窗口是?
:::success
國泰 AML : 宜峰
信用分數kalapa/pcb:數據中台,找mark or Jeff (數據部的)
電信分數TS/FS:數據中台,找mark(數據部的)
有關VCP，msg-center的簡訊費,send email fee等等: 守敬閔富
有關VCP但不是我們負責的: 守敬閔富

打外部的部分可能還要看簽約的廠商是誰，如果簽約的廠商是TPI->透過TPI聯繫
intellect的話可以先找TPI再判斷是TPI處理還是intellect來處理

GCP相關問題: 找金控的俊言
TFS CI/CD相關找::林雍原
:::

- [x] GCP目前所知使用到的套件，等待水哥再次巡迴台中，向他確認功能運用面向
:::info
- GKE 
![](https://hackmd.io/_uploads/rydkAvAF2.png)

- SQL: 只有一個project，依照APID or 功能面建立多個postgreSQL DB，schema都是dbo， table就是table name
![](https://hackmd.io/_uploads/HJD80vCK2.png)

- PUB/SUB
![](https://hackmd.io/_uploads/Sk8AAD0th.png)

- Secret Manager: 存放DB username, password
![](https://hackmd.io/_uploads/Bk1hCP0th.png)

- log
![](https://hackmd.io/_uploads/HytzJdRK3.png)

- Storage (GCS): 存放file的地方，目前維運team沒有權限可以看，若有需要，要向GCP負責人詢問
![](https://hackmd.io/_uploads/rJsO1_CYh.png)

- Redis 每個專案有一台，裡面再切分index

- Cron scheduler: for batch的cron job 設定
:::


- [x] VCP 將msg-center部分完成後，再與安邦同步
    - [x] Lynn協助整理問題+約時間與TPI/文寶宜峰討論 (已和TPI討論，問題使用jira追蹤)
    ref: https://jira.cathayholdings.com/browse/CDCVNCUBRT-316
    - [x] 詢問文寶何時可以與HCMC進行CICD學習 (已經知會守敬)
    - [x] 與安邦同步VCP結論 >> daily時 jira已經移到done >> 7/28有同步討論
    - [x] 手寫資料用draw.io畫一畫
      
- [x] localhost demo project
     - [x] 和通路約時間: 問VCP問題 + 起local (HES project) >>問題完成，然後他們也沒有起成功XD
     - [x] local demo prject :
         - [x] 加入一些 exception handle (ProblemHandling)
         - [x] 加入一些 exception handle (ResponseEntityExceptionHandler)
         - [x] GlobalExceptionHandler沒有implements ProblemHandling 這樣會有作用??
         - [x] feign Client 實作 ==(88.247.247.89:8081/5)==
             - [x] feignException (自己丟internalServerError看看呼叫的人是不是用feignException可以接到)==> 如果使用feign Client,==有錯的話都會被feignException==接到
             - [x] logger :
            需新增FeignConfig.java定義要註冊的 @Bean => 依照此例代表feign.Logger 要使用new FeignLogger.java
             ![](https://hackmd.io/_uploads/HyDPcrLi3.png)

             
              HES 自行定義一支FeignLogger 繼承feign.Logger，
             根據觀察，feign.Logger overridd了logRequest() & logAndRebufferResponse()兩支function，就不會執行log()了
![](https://hackmd.io/_uploads/HywVqS8i2.png)
             
        - [x] 建立一個Camunda 流程,包含
            - [x] bpmn
            - [x] DMN
            - [x] Delegation(java)
            - [x] throw exception
            - [x] timer
            - [x] signal
            - [x] user task (assignee by person + formKey)
- 補充 application-agreement相關流程
![](https://hackmd.io/_uploads/Bkq_Fv322.png)

- [x] HES某部分喪失記憶(eg.確認合約的CRUD)
    - [x] 補充資料在 DocumentTemplateController page
    - [x] 找出到底是誰以及是如何使用 CREDIT_LIMIT_APPLICATION_AGREEMENT
        - 玟君回覆目前沒有向backoffice取合約範本的需求，針對HES只有在GCS上透過bucket name取檔案而已。
        - 請教Pino,HES在哪個時間將user的資料與合約範本融在一起，並且上傳到GCS空間，以提供通路拉取?
ANS: backoffice-api 的 DocumentTemplateController 只是做合約模板的管理 (上下架、預覽..) :bulb: 徵審時實際產生合約，是由 Camunda 操作(從 DB 撈出合約模板及客戶資料，將資料填入到模板中，然後轉換成PDF) 可以看 DocumentService.java 負責取得合約 -> 填入參數 -> 轉成 PDF

:::success
由camunda trigger => application-agreement.bpmn
![](https://hackmd.io/_uploads/S1fee9Pj2.png)

處理user資料和合約範本組合在一起的Delegate: GenerateAgreementDelegate.java
- 呼叫DocumentService.generateAgreement
    - 透過 applicationId 查詢 application table 取得user資料
    - 然後到 document_template table (getActiveDocumentTemplateWithType) 透過type 取得範本
    - templateSubstitutor.substitute 開始做參數置換
    - 透過itext 轉docx to pdf
    - 以customerId+applicationId 生成 key (bucket - "customers/%s/applications/%d/agreement")
    - 就呼叫storage 儲存到GCS

:bulb: 生成的bucket 真的使用在 TaskService(Controller) 的 getApplicationAgreementAppTask API 裡面回覆這一個bucket (就是玟君說的直接取合約)
:::

- [x] HES*4 + VCP *3 + batch *3
    - [x] CICD >> 已實際操作並配合其他人上版
    - SonarQube: 目前不需要這個
    - [x] Lucent Sky報告 >> 0731完成第一次掃描(就SIT當前最新版本(7/26))

![](https://hackmd.io/_uploads/HycWbmL52.png)

- Data Source 是不是需要跟進研究調整?
    - :ballot_box_with_check:app端已反應，並向GCP管理小組提出加大連線數申請
![](https://hackmd.io/_uploads/r1ykr6ajh.png)

