## 跟通路借的電話號碼
Viettel: +84 343254990
VNPT: +84 842421380

## PROXY
PROD:
 proxy_ip: 10.171.85.2
 proxy_port: 8080
UAT:
 proxy_ip: 10.171.53.2
 proxy_port: 8080
UT:
 proxy_ip: 10.171.21.2
 proxy_port: 8080

## Jira 紀錄
1. Story 664 - 導入政策電信 (持續追蹤直到看哪個 Spring 結束)
2. Task 864 - 功能實作 DataNest & Viettel (Spring 31)
3. Task 856 - 功能實作 VNPT & Telco API Prepare Deploy to SIT (Spring 32)

## 前置準備
- [x] 真的閱讀規格書 有沒有不理解 需補充
- [x] redis 開通，真的確實請水哥動作，給他一個禮拜的時間 
      =>已開申請單:00083202302060
- [x] 在VCP 開一個 telco-schedule repo
- [x] 在local建立一個telco 的db schema


## 待回覆的問題
- [x] 確認db記錄範圍? 
- [x] telcoapi 合併回金控git lab 時間點及方式?
- [x] telco-schedule 合併回金控git lab 時間點及方式?