[toc]

:book: 參考 https://spring-gcp.saturnism.me/

## 等待釐清問題
- [x] 了解各服務之於維運組的
    - [x] 使用方式與範圍(各窗口)?
        - TFS Server + CICD : 雍原
        - AP(程式問題): 找各負責人
            - HES : Hank,Lynn
            - VMB : 玟君,至勛
            - 數據 : Zack,Mark
        - GCP相關(gke,pub/sub壞掉等等):找俊言

    - [x] 若有項目調整會如何announce?
        - 目前沒有協調的方式
        - 疑似有中台發展部(台北)會接手VCP(最常被呼叫)
        
    - [x] 要如何申請一個新的service?
        - 理想情況不建議有新的服務，短時間內也不會有
        如果真的有需注意：
         1. GKE相關內容, domain...
         2. DB schema
         3. GCP上面的權限先盤點再開單給俊言
         4. TFS CI/CD要自己建
    
- [x] GKE: 請問如何分配每一個service要住在哪裡?
    - [x] 有調整硬體(ram,容量)等的需求?
    可控範圍是在TFS CI/CD的pipelines上面調整(變數的頁籤)
    - [x] 先前看到itext需要增高pod數量，這些設定在哪裡控制?
    在TFS CI/CD調整replica的數量
        - [ ] 另外議題 : 在production的時候 license key如何延展需要請教ＴＰＩ
        ==20230825 TPI回覆尚未完成簽約也不確定展延的方式...==
        ![](https://hackmd.io/_uploads/r17o05ST3.png)

- [x] SQL: 
    - [x] 在GCP看不到db資料是因為權限不足? 
    目前沒有解法，就是不能看，如果production真的有問題要透過VDI查看(只有4小時)，申請臨時單(read的權限)看production DB的資料
    - [x] DB備份機制及切換處理?
    現在有snapshot機制一天備份一次
    還原的切換部分GCP業務同仁會搞定
    - [x] postgreSQL 有SSL/TLS憑證? 如果憑證過期有什麼處理步驟?
    不會有這個問題，因為都會透過國泰設定的proxy&防火牆設定去做連線

- [x] MemoryStore: 在GCP可以看到redis資料嗎? 
    GCP上面看不到，也只能類似db自己去連線
    
- [x] Pub/Sub: 請問如何看到publish的訊息?
    GCP上面看不到，只能用指標數量圖，不能看訊息內容(沒有權限)

- [ ] Cloud Scheduler: 趕緊找守敬！！！！！！
    - [ ] 如何在GCP上設定batch file三包服務 (VLO目前沒任何設定)
    - [ ] 初始設定需要? 透過TFS CICD
    - [ ] 維運可能會調整? 每一次做修改都要報告..

### 同場加映
 - telco api 前後串接對象
 - data api 前後串接對象
![](https://hackmd.io/_uploads/rk8s_0fp3.png)

GCS無法透過WEB的方式查看
pub/sub,gke在最右邊的畫面可以看到權限的清單
IAM管理的service account 裡面的account代表的意思有兩個1.人的帳號2.project的帳號

如果想要起localhost又連線GCP要有(但銀行環境不允許)
1.IAM的json憑證
2.將此憑證放入到local spring boot project

## Production環境的所有操作
1.我們先在現有的系統，模擬在prod的參數
2.開單(工作項目)給資管:可以詢問玟君
3.端末(資管)會找到資料，複製到我們碰不到的Release Project TFS

## GKE
:::info
- 功能: Kubernetes Engine，是專案中個服務運作與管理的地方
- 頁面瀏覽:
:bulb: 階層: project > cluster > node(4) > pod

![](https://hackmd.io/_uploads/r1Urspuon.png)

![](https://hackmd.io/_uploads/BJVDiadin.png)

![](https://hackmd.io/_uploads/HyYdjTdih.png)

:bulb: 點選工作負載就可以看到當前deployment的pod
![](https://hackmd.io/_uploads/SJzThTdi2.png)

![](https://hackmd.io/_uploads/H1gFppuin.png)

- 串接方式: 設定檔寫在HelmChart config 透過 TFS執行CICD
:::

## SQL
:::success
- 功能: 每個APID有一台postgreSQL(14) Server，再個別建立對應的db
- 頁面瀏覽:
![](https://hackmd.io/_uploads/SyQl10Osh.png)

:bulb: 透過此頁面可以查得相關連線資訊
![](https://hackmd.io/_uploads/SkvakCus2.png)

:bulb: 點選資料庫，可以查看當前建立的db (預設schema都是dbo)
![](https://hackmd.io/_uploads/S1-txR_j3.png)

- 串接方式: 
1. 連線所需的host, port, DbName, username, password 都是寫在設定檔HelmChart config
2. 於VLO程式端都是透過Jdbi的方式直接寫sql去做db資料操作
![](https://hackmd.io/_uploads/rytnQ0us2.png)
3. 於VCP程式端都是透過Jpa操作db資料
![](https://hackmd.io/_uploads/Sk9pNCuon.png)
:::

## MemoryStore
:::info
- 功能: 一台redis server，裡面再切分index，來負責不同的資料存放
- 頁面瀏覽:
:bulb: HES專案並沒有使用，VCP才有使用!!!

![](https://hackmd.io/_uploads/S1EyUR_oh.png)

- 串接方式:
1. 連線所需的host, port, password 以及對應index 都是寫在設定檔HelmChart config
2. 於VCP程式端都是使用 JedisConnection，提供對應的參數資料來創立RedisTemplate

![](https://hackmd.io/_uploads/ryLzP4fq3.png)

![](https://hackmd.io/_uploads/H1Z7q0_in.png)
:::

## GCS
:::success
- 功能: Cloud Storage 存放file (HES專案中用來存放已經填入客戶資料的合約檔案)(VCP專案中用來存放客戶發送email時的夾帶檔案)，目前維運team沒有權限可以看，若有需要，要向GCP負責人詢問
- 頁面瀏覽:
![](https://hackmd.io/_uploads/Bk1yoC_sn.png)

- 串接方式: 以下說明 HES專案使用方式
1. 連線所需的bucketName, storageKey 都是寫在設定檔HelmChart config
2. interface層: 
    - AppStorage => 取得resource連線，==實際使用可以參考 CustomerService== 
    - Storage => 專案中log (logback-spring.xml) 的讀寫刪操作,==實際使用可以參考 StorageService==
3. AppStorage implements層: 
    - LocalAppStorage => 預設mock連線,本機儲存空間 
    - GcpAppStorage => 透過bucketName,storageKey取得對應的GCP資源
4. Storage implements層: 
    - LocalStorage => 預設mock連線,將專案中的log儲存到本機指定資料夾下的路徑 
    - GcpStorage => 透過bucketName,storageKey取得對應的GCP資源,將專案中的log儲存到GCP 
    - LoggingStorage => 建立出來只為了有一個共同的class可以操作
5. Configuration檔案: StorageAutoConfiguration.java
    - 建立 DB-Storage table的操作
![](https://hackmd.io/_uploads/SkUEm0psn.png)
    - AppStorage implements的註冊
![](https://hackmd.io/_uploads/Byj5mApih.png)
    - Storage implements的註冊
![](https://hackmd.io/_uploads/B1oF7Apo3.png)

:bulb: GCP Storage 於Spring boot的連線方式
![](https://hackmd.io/_uploads/HJ_mFCTin.png)
:bulb: 或是在VCP使用更簡單的寫法
![](https://hackmd.io/_uploads/BkyWmyCi2.png)
ref: https://medium.com/@ashutoshshashi/using-java-to-interact-with-cloud-storage-5dfd3e30433f
:::

## GSM
:::info
- 功能: Secret Manager，用來存放DB username, password
- 頁面瀏覽:
:bulb: 這兩個project的分類: dsu(username)/dsp(password)
![](https://hackmd.io/_uploads/H10J9ATs3.png)
會依照版本迭代 projects/xxxxxxx/secrets/{secretID}/{version}
![](https://hackmd.io/_uploads/BkDwqA6s3.png)

- 串接方式:
1. 使用方式都是寫在設定檔HelmChart config
![](https://hackmd.io/_uploads/BJCDjRpsh.png)
:::

## Logging
:::success
- 功能: 專案中log記錄(通常不包含客戶個資或相關機敏資料)
- 頁面瀏覽:
![](https://hackmd.io/_uploads/HJ4nsCaoh.png)

- 串接方式: 和Storage的連線設定有關，在程式中是包含 log.info/log.error 相關的log記錄，藉由logback轉換成GCP可儲存的格式
![](https://hackmd.io/_uploads/BkeL3C6oh.png)
:::

## Pub/Sub
:::info
- 功能: 處理非同步寄發訊息的中介方，定義不同的topic，來提供發送(publish)和訂閱(subscription)
- 頁面瀏覽:
topic清單: 主題名稱就可以和訂閱清單mapping
![](https://hackmd.io/_uploads/H1QtpAps2.png)
訂閱清單: 訂閱項目ID(程式中訂閱者連線使用), 傳送類型(提取, 由訂閱者主動去拉資料回來)
![](https://hackmd.io/_uploads/Syb6R0Tj2.png)

- 串接方式: 舉例msg-center
1. 連線所需的projectID, topicId...都是寫在設定檔HelmChart config
![](https://hackmd.io/_uploads/Hy-HWJAjh.png)
2. publish
![](https://hackmd.io/_uploads/S1l0xkCs2.png)
3. subscription
![](https://hackmd.io/_uploads/SkrKxJAin.png)
:::

## Cloud Scheduler
:::success
- 功能: 全代管的企業級 Cron 工作排程器。無論是批次工作、大數據工作，還是雲端基礎架構的相關作業，幾乎任何工作都能交由這項工具輕鬆排程。您還可以自動化所有流程，包含讓工作在執行失敗後重試，進一步減少手動作業和人為操作介入。 (cdw-batch/file-batch使用)

- 頁面瀏覽:
![](https://hackmd.io/_uploads/SJaFD1Co3.png)

- 串接方式: 
1. 連線所需的資料都是寫在設定檔HelmChart config
![](https://hackmd.io/_uploads/r1nruJAih.png)
2. XXXBatchApplication 裡面會直接呼叫一個method 
:::