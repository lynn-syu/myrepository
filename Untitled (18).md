- 目前越南消金專案關於資料的備份和加密分為以下類別

==A. 檔案類==
- 加密：目前存放的GCS空間皆用Cloud HSM進行加密，符合FIPS 140-2-level-3的等級
- 備份：File-batch會透過以上加密方式產生檔案到對應的GCS空間，該空間可搭配雲地檔案交換平台落地回行內或是越南當地

==B. 資料庫類==
- 加密：Cloud SQL默認提供AES-256靜態加密，這意味著存儲在數據庫中的***所有數據都會在磁盤上自動加密***。這是一種廣泛認可且高度安全的加密算法。因此，即使未經授權的一方獲得了對底層存儲的物理訪問權限，數據仍然受到保護且不可讀。
- 傳輸中加密：為了確保數據傳輸過程中的安全，***Cloud SQL 使用行業標準的 SSL/TLS 協議***。連接到 Cloud SQL 實例時，客戶端可以使用 SSL/TLS 建立加密連接，從而通過互聯網提供安全的通信通道。這種加密可以防止在客戶端和數據庫服務器之間傳輸數據時被竊聽和篡改

- 備份-ap面：透過Cdw-batch會將table內的資料產生csv到對應的GCS空間，該空間可搭配雲地檔案交換平台落地回行內或是越南當地
- 備份-Infra面：透過DB Snapshot 進行，目前每天(UAT以上 台灣時間01:00~05:00 固定會開始備份一代，最多七代

==C. 共同==
- 物理安全：Google Cloud 的基礎架構擁有嚴格的物理安全措施，包括嚴格的存取控制、監視和防火牆保護。資料中心設施受到嚴格的保護，並且只有授權的人員可以進入。
- 存取控制：只有授權的使用者和服務帳戶才能夠訪問和管理 Cloud SQL /GCS 的儲存。Google Cloud Identity and Access Management（IAM）可用於分配和管理使用者的存取權限，以確保只有授權的人員可以存取資料。

:::success
有關刪除舊檔案的部分，目前請TPI開發一個batch job，制定某某時間要根據條件清除DB資料。
雲地交換平台: 處理cloud-storage 到NAS的過程

![](https://hackmd.io/_uploads/H1KM7_AKn.png)
- 目前在銀行環境postgreSQl約定俗成的結構
    - dbname: batch -> dbschema: dbo -> table: 包含三個工具需要的
    - batch這個專案是特例，將三個服務都放在同一個DB下
    
### cdw batch 
> 是處理gcp上 Db資料面 && secret manager後製作業，透過 GKE - CronJob

![](https://hackmd.io/_uploads/ByIZVuRY3.png)
- 在CD的時候，透過yml的管控，來決定cdw-batch target dataSource要去哪一台
- cdw_setting-主要table: 
 (column) job_name 對應 destination (gcp路徑)
 (column) pubsub_project_id對應GCP pubsub服務名稱
 (column) check_requied ????? :question: 
- source_table-主要table
 (column) job_name 對應 cdw_setting 的 job_name
 去mapping 出 sql = custom_sql_head + custom_sql_body


### file batch 是處理gcp上檔案類型的後製作業==搭配雲地交換平台就能傳到地端==
> 這一支batch主要在做路徑 C1 & D1的事

![](https://hackmd.io/_uploads/B1KJuOAY2.png)

其主要目的:
1. 傳到地端再使用
2. 保留備存使用(機敏資料)

![](https://hackmd.io/_uploads/S1rgFOCYh.png)

- iterate_all: true + days: 2 => 用資料夾的'建立時間'距離現在往前2天前的全部都抓
- iterate_all: false + days: 1 => 用資料夾的'名稱'(YYYYMMDD)判斷距離現在往前1天前的全部都抓

- zip_required: true => 文龍經理的需求不希望檔案散亂且任意人都可開啟，所以進行壓縮且加密(hcmc_yyyymmdd)

flag檔不一定要生成，是要給 R6 file server使用
Q: 若檔案名稱相同，副檔名不同，這樣產生的flag檔案不就打架了?
Ans: 在業務層面上不會發生這個問題，因此不需考慮
![](https://hackmd.io/_uploads/SJWy7tRt2.png)

### house keeping 清除批次
![](https://hackmd.io/_uploads/H1AwYd0tn.png)
- 各apid下有需要自動化清除DB資料或是檔案
- 會有前後查詢script是為了遵守銀行規範，要先確認筆數

(column)before_script: 操作DB要先select一次找出要影響的筆數，確保查詢條件符合預期
(column)script 真正執行指令
(column)after_script:在用這個指令查詢，應該會查到before_script一樣的筆數及影響的那些資料有哪些
使用範例: 若script是delete指令，在before_script查出來有10筆，執行完script後，after_script查詢應該要是0筆==>表示刪對資料
:::
