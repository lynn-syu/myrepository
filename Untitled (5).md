
### VLO上線
- URL收集區
keycloak > https://ovs-lx-vlo-01-keycloak.gcub.gcp.uwccb/admin/master/console/#/
camunda > https://ovs-lx-vlo-01-camunda.gcub.gcp.uwccb/camunda/app/welcome/default/#!/login
backoffice-frontend > https://ovs-lx-vlo-01-backoffice.gcub.gcp.uwccb


2023/11/23 ==新增hotfix release project申請單==
http://pialm01:8080/tfs/DefaultCollection/Release%20Project/_workitems/edit/972957

AML URL
https://pirhchosm.cathaybk.com.tw:8381/customhub-rest/services/NewCIF
https://88.8.64.113:8381
https://pirhchosm:8381/customhub-rest/services/NewCIF

2023/11/21
==修改data_api PCB table內容==
https://pialm01/tfs/DefaultCollection/Release%20Project/_workitems/edit/970659/

2023/11/10 上線當天準備事項:
==11/15回來需討論部屬問題:CD只會抓最新更新的branch，這樣在名稱的判別上會有問題==
![image](https://hackmd.io/_uploads/H11Oa8smT.png)

==data-api上版delete pod遇到錯誤，原因: del pod裡面的script資管設定有誤==
![{6C8395EC-062E-4421-AA86-9FB3011368E8}](https://hackmd.io/_uploads/H1aM_HxEa.png)


- 執行事項
    - [x] 聯繫資管人員(孝欣)&DBA(啓原)開始上線作業
    - [x] 請孝欣先deploy "DB"的CD管線
    - [x] 聯繫DBA且請他分享畫面=>依序執行指令
    - [x] 看著DBA執行指令後截圖，一棟一棟截圖留存驗證
    - [x] 檢查HES excution_record grant的權限部分是否正確
    ![Screen_Capture_20231110_104115_622](https://hackmd.io/_uploads/HJwj_zsQT.png)
     ![image](https://hackmd.io/_uploads/Hka4_zoXp.png)
    - [x] 確認無誤後DBA部分執行結束
    - [x] 請孝欣依照release project單子執行相對應的del prod & deploy pod
    - [x] 執行全部後完成release project作業
- 驗證
    - [x] 上GCP確認每一個AP Service是否正常啓動(可確認更新日期)
    - [x] 點選backoffic確認網頁是否能正常啓動
    - [x] 重新update 合約範本(aggrement 10_12版本)
    - [x] 到Caumnda確認是否有跑此流程
    ==第一次上傳點選save又發生上次buf欄位是null的問題需確認追終->解決辦法:砍掉上傳，重新再傳一次就成功了==
    - [x] 透過GCP-Trace 查看 Cloud SQL Query 有沒有insert execution_record的紀錄
    ![image.png](https://hackmd.io/_uploads/HJA-8pumT.png)
    驗證:
    ![image](https://hackmd.io/_uploads/S1s4FzjXT.png)
    - [x] (下周一(11/13)中午之後吧?顯得我們跑太快，再請宜峰寄信)寄信請文龍經理協助~~再~~上傳合約範本
    - [x] 下周一檢查 cdw-batch-hes(問數據) **11/14才上，11/15再跟他們詢問**
    - [x] cdw-batch-government(問守敬)有沒有收到檔案 > 11/20檢查prod cron job 正常執行，但NAS路徑設定錯誤，導致檔案沒有傳到對的位置，請格致修改!! > 11/21 確認有進到prod NAS

- 問水哥 :
    - 甚麼時候定版(缺customer group 利率調整 > 11/3定版，11/6再開始調整
    - config可不可以動PROD了(缺TPI說有最終版更新) > 11/3定版，11/6再開始調整
    - 各service 壓力測試後最終決定的pod resource > 等11/3之後的數字，調教好只改PROD
    - data-api,telcoapi的true/false最終確認 > 奇怪的需求都是ccb5，目前就都mock=false
    - batch會由TPI提供ddl,dml嗎? 是不是連sql都建議是分開執行嗎?ans:可以一起在第一階段就上，程式依舊只上cdw-batch,但SQL全部都執行
    - 關於customer-service 調整for客戶資料統一 & keycloak弱掃升版 > 奇怪的需求都是ccb5
![](https://hackmd.io/_uploads/HkrMG10f6.jpg)

:bulb:對照TPI版號紀錄for CAB2-2
![image.png](https://hackmd.io/_uploads/r1d6hZP76.png)

- [x] CD pipeline設定
    - [x] 要新增delete的按鈕
    - [x] pod resource欄位要不要調整 > 11/6有定版了 > 通知中 > 11/8 完成
    https://pialm01/tfs/DefaultCollection/ALM-Service/_workitems/edit/961383
    
- [x] 收集 HES DML sql(最終版-Repo:DB DB\cab2_2)
    - [x] 更版TPI提供，請資管放入資料夾，並請DBA執行(需merge成一份放到repo(DB)裡面
        - 0.6.6版
        - [x] 20230912.sql
        - [x] 20230920.sql 
        - 0.6.8版
        - [x] 20230908.sql
        - [x] 20230921.sql
        - [x] 20231002.sql
        - 0.6.17版
        - [x] 20231101.sql
    - [X] delete_bo_user.sql 執行DB CD 動作(放入NAPAS_Pilot資料夾)

- [x] 程式或設定檔相關
    - [X] AML for camunda URL(https://pirhchosm:8381/customhub-rest/services/NewCIF) 防火牆(單號:60400202300710 DONE)
    - [X] TPI修改HES的log優化(traceId)
    - [x] aspose license (要更新最新效期 backoffice, camunda)
    - [x] RequestFailedDelegate.java 的 log.debug 改為log.error
    ![](https://hackmd.io/_uploads/Sk04yFcJT.png)
    - [x] HES 改串接 data-api 的 CICS11A + CIC37 不走mock了 > 修改config mock=false
    - [x] bo-frontend的最終版合約是哪一份??(目前是20231012版本)
    - [x] Camunda prod.yml需最終確認
        1.bpm_aml_mock參數為何?
        2.bpm_pcb_mock參數為何?
    ![](https://hackmd.io/_uploads/SyrTtJIG6.png)

- [x] 取得 icontact 上線聯繫單 (單號: 60400202300917) > ==只給HES==
- [x] 取得 TFS APID 中介單
https://pialm01/tfs/web/wi.aspx?pcguid=759a57b1-a42e-42f9-ab76-9ae16f31a1c2&id=960665
- [x] 送出 prod release project 上線單 (單號:955206) > 11/10 上線部屬
http://pialm01:8080/tfs/DefaultCollection/Release%20Project/_workitems/edit/955206
    - [x] 確認dba協助人員- 啓原 (單號:955216)
https://pialm01/tfs/DefaultCollection/Release%20Project/_workitems/edit/955216/
    - [x] 確認資管協助人員- 孝欣?
    - [X] 程式定版
    - [X] SIT測試報告
    - [X] UAT測試報告
    - [X] 變更上線檢核表(用icontact畫面點選就好)
    - [X] USER 允許上線mail
    - [X] Lucent sky掃描報告

- [x] HES cdw-batch 上線
    - [x] 將CICD管線完成
    - [x] helmchart 檔案 > prod-hes.yaml + prod-government.yaml (DONE)
    - [x] deploy > 定版 develop=staging 10/25 > 等待merge to release 再CI
    - [x] DDL&DML > ==資料夾位置 init/sql/cab2_2/xxxx
    ![image.png](https://hackmd.io/_uploads/r1WxpCBm6.png)

### data-api & telcoapi 上線
- [x] 申請dta_api & telcoapi UAT DB帳號&權限(單號:00083202301583)
- icontact 上線聯繫單 (單號: 60400202300915) > 只給VCP
- [x] CD pipeline設定
    - [x] 要新增delete的按鈕
    - [x] pod resource欄位要不要調整 >11/6有定版了 > 守敬一起改 VCP
    https://pialm01/tfs/DefaultCollection/ALM-Service/_workitems/edit/962387

- [x] 收集 DDL & DML sql
    - [X] init sql 執行DB CD 動作(放入VCP_data_api,telco資料夾)
    - log優化
        - [x] telcoapi DB alter table script需放入 ==VCP DB repo== 資料夾，並請DBA執行 > 守敬開單
        ![image.png](https://hackmd.io/_uploads/ryFKVzwQp.png)
        - [x] data_api DB alter table script需放入 ==VCP DB repo== 資料夾，並請DBA執行 > 守敬開單
        ![image.png](https://hackmd.io/_uploads/Bkpd4fDXT.png)
        - [x] data-api CustomerInfoService.java 修改 publishCustomerInfo()
        -  log.info("Publish type:" + apiType + "uuid:" + uuidStr);
        ![](https://hackmd.io/_uploads/H1s0rqyM6.png)

- [x] 程式或設定檔相關
    - [X] 上版 telco-api, data-apia log 優化
    - [x] lucent sky掃描
    - [x] data-api PCB/Kalapa 串接真實數據源(pub/sub-topic)
    ==PCB接數據那裏的正式資料API,Kalapa正式環境會接正確資料(CICS11A, CIC37) (同步修改HES設定檔)==
    - [x] data-api prod.yaml 最終確認 pcb的true/false ????
    ==> Ans: pcb:"false"
    ![](https://hackmd.io/_uploads/r1eU4S8GT.png)



