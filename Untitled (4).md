[toc]

## CAB2-2.3
### 1/16 上線當天 晚上?點
- [x] 確認 data-api PROD GCP ingess timeout秒數 是否改為60秒 (00083202400092)
![image](https://hackmd.io/_uploads/SkMr7smKT.png)

- [x] 先請資管執行DB prod > 請dba執行sql > 要執行verify sql
- [x] 再請資管執行service prod > 到gcp確認服務 > 開一下backoffice-frontend
- [x] 確認 data-api, telcoapi 有沒有執行 sql + deploy

### 上線值班表
https://pialm01/tfs/DefaultCollection/Release%20Project/_wiki/wikis/Release-Project.wiki?wikiVersion=GBwikiMaster&pagePath=%2FHome%2F%E4%B8%8A%E7%B7%9A%E5%8D%A1%E7%89%87%E9%83%A8%E7%BD%B2%E5%9F%B7%E8%A1%8C%E4%BA%BA%E8%AA%AA%E6%98%8E&pageId=11998

### 檢查進度
- [x] 所有的sql都定版，也放置VLO-DB repo即可以執行CI: ==20240112.1==
- [x] VLO:
    - helmchart: 皆為調整configmap
    - source code (PROD CD image)
        - keycloak: ==gcp image 2023112801==
        - camunda: ==20240102.1==
        - app-api : ==20240102.1==
        - backoffice-api: ==20240102.1==
        - backoffice-frontend: ==20240102.1==
    - sql: done
    ![image](https://hackmd.io/_uploads/HyDt-qQup.png)
- [x] Data-api > 已轉通知守敬
    - helmchart: 無異動
    - source code: PROD CD image ==20240102.1==
    - sql: done
    ![image](https://hackmd.io/_uploads/Sy5-gZZdp.png)
- [x] Telcoapi > 已轉通知守敬
    - helmchart: 無異動
    - source code: PROD CD image ==20240102.1==
    - sql: 無異動 
- [x] VLO cdw-batch
    - helmchart: 無異動
    - source code: 無異動
    - sql: done 
    ![image](https://hackmd.io/_uploads/BJIk-e-up.png)

### VLO icontact
- [x] ==單號: 60400202301238== > 由 CDC填寫風險評估 轉TFS > 以串聯
URL: https://pialm01/tfs/web/wi.aspx?pcguid=759a57b1-a42e-42f9-ab76-9ae16f31a1c2&id=1009241
- [x] ==單號:00083202400030== >話機轉接手機 > 所以是 ?????
![image](https://hackmd.io/_uploads/BkmqaMzYa.png)

### VLO Release project 
- [x] ==單號:1004012== > 已和資管人員約好1/16 晚上8點
資管人員: **林孝欣**
URL: https://pialm01/tfs/DefaultCollection/Release%20Project/_workitems/edit/1004012

### VLO DBA 任務單
- [x] ==單號:1008151== > 已和DBA約好1/16 晚上8點
DBA人員: **鍾依蓉**
URL: https://pialm01/tfs/DefaultCollection/Release%20Project/_workitems/edit/1008151

### GCP VCP PROD data-api timeout setting
- 可以參考 00083202302112
![image](https://hackmd.io/_uploads/BJ45i-Ywp.png)
- [x] cab2通過之後，需要開icontact單 > 1/11 開單 00083202400092

### PROD Keycloak CD 管線 Release Project 調整變數
- [x] 提單請孟哲幫忙修改，需要修改IMAGE_TAG為 ==2023112801== > 1/2 done
申請單 : https://pialm01/tfs/DefaultCollection/ALM-Service/_workitems/edit/1003152

![image](https://hackmd.io/_uploads/rkViqZYDp.png)

### SQL (需統一放在 DB: cab2_2_3 資料夾內) 
HES Backend 上版SQL: TPI提供合併版本SQL > 沒有提供 verify
  - [x] 20231108.sql
  - [x] 20231115.sql
  - 20231117.sql(先前已即時上板)
  - [x] 20231211.sql

HES cdw-batch 上版SQL: 
  - [x] cdw-batch_ddl.sql

VCP data-api 上版SQL:
  - [x] tbSubMessageId_ddl.sql

## TFS上線部署單 格式有更新
![image](https://hackmd.io/_uploads/ryBvsf6I6.png)

## 一些綜合性待辦事項
- [x] VLO PROD DB 查詢帳號展延 (icontact 00083202302153) > 結案
- [x] 12/22 掃描一版lucent sky > ==all pass==
![image](https://hackmd.io/_uploads/rJihpvzw6.png)
- [x] 確認cdw-batch for VLO table刪除問題: 
    - [x] 需移除bank,bank_account,credit
    - [x] customer table 少了一些欄位也要盤點
    - ==已整理出cdw-batch_ddl+ verify sql > 12/22 於 UT & UAT 皆執行完成==
![image](https://hackmd.io/_uploads/rJ44GtMwa.png)
- [x] 建立/申請VCP Telco Schedule管線
- [x] data-api 需要merge回金控git lab > 以利TPI進行壓測 > 於金控環境建立0.6.27已merge回main

### Telco api
- 要提供給第三方電信公司，專案於GCP三個環境的IP開通白名單=>可以從GCP資源表看到
![image](https://hackmd.io/_uploads/SyocAwzDp.png) 

- [ ] 需確認proxy問題，是否通用msg-center的設定?

## 一些範本
申請自動化測試腳本repo > done
https://pialm01/tfs/DefaultCollection/ALM-Service/_workitems/edit/981343

申請VCP telco-schedule repo > done
https://pialm01/tfs/DefaultCollection/ALM-Service/_workitems/edit/985470/