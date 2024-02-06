### VLO上線
:bulb: HRI 有問題詢問 係開小秘書：劉家妤(單) 陳俞儒(雙，資深)
:bulb: icontact 變更單單號範例：901002002300101 > 要找棨達新增APID選項
:bulb: 變更申請TFS有問題可以問:顏妙純
:bulb: VDI 申請範例 00083202300851

:bulb: 這裡可以驗證 Phone Number Parser Demo for LibPhoneNumber
https://libphonenumber.appspot.com/

![](https://hackmd.io/_uploads/SyYE6lv0n.png)

- 9/24之前萬用的上線申請，可參考 https://pialm01/tfs/DefaultCollection/Release%20Project/_workitems/edit/928441/

- URL收集區
keycloak > https://ovs-lx-vlo-01-keycloak.gcub.gcp.uwccb/admin/master/console/#/
camunda > https://ovs-lx-vlo-01-camunda.gcub.gcp.uwccb/camunda/app/welcome/default/#!/login
backoffice-frontend > https://ovs-lx-vlo-01-backoffice.gcub.gcp.uwccb

- [x] 執行事項
    - [x] 上版 VLO 為 SIT+UAT 0.6.3 = 大家的0.6.5
    - [x] helmchart參數再次確認 (itext忽略)
    - [x] VLO DDL定版？ > 在local DB跑一次，DML 沒辦法檢查 > done
    - [x] 程式定版? 就是VLO的0.6.3 對外是0.6.5
    - [x] VLO Lucent sky
    - [x] 詢問 PROD DB 查看如何開單 (是R6權限嗎? 00083202300614) > 水哥要接手
    - [x] 詢問 PROD DB任何申請單 對GCP的連線描述 (參考單號 00102202300233)
    - [x] 檢查建立好的PROD CD pipeline (給名單v) > 缺backoffice-frontend > done
    - [x] (9/14) VLO 上線單(串聯中介單) & DB task單
        - [x] 先部屬keycloak -> 通知俊言協助掛上ingress -> 再部屬其他服務
        - [x] 結單
    - [x] 服務運行驗證
        - [x] keycloak > ui > setting
        - [x] camunda > ui
        - [x] app : 不可以使用swagger
        - [x] backoffice : 不可以使用swagger
        - [x] backoffice-frontend > ui
    - [x] bo & keycloak 使用人員清單整理
        - [x] 請dba協助刪除資料 > delete from dbo.bo_user bu where bu.id not in (1,3,4,5,7,13,20) 
        - [x] keycloak同步清除
        - [x] 透過bo 重新加入允許名單 > lynn 加入成功 > 其他請宜峰or文龍經理處理

- [x] 完成helmchart參數設定: 已確定prod放在原本的空間就可以
    - [x] VLO
        - [x] backoffice prod.yml > cathay.itext_license_key 需要更新
            - 正式環境第二次上版才更新
        - [x] camunda prod.yml > cathay.bpm_aml_mock 這個是false 還是 true?
            - prod.yaml第一階段上線=true, CAB2-2應該為false
        - [x] camunda prod.yml > cathay.aml.url 未決定(向宜峰詢問)
            - 目前有PROD的URL但CAB2-2才放上去，以避免誤call
        - [x] camunda prod.yml > cathay.itext_license_key 需要更新
            - 正式環境第二次上版才更新
        - [x] camunda prod.yml > cathay.notification_mail_app_url 有點怪?
            - CAB2-2才更新
        - [x] camunda prod.yml > cathay.notification_sms_app_url 有點怪?
            - CAB2-2才更新
    - [x] VCP
        - [x] telcoapi configmap.yml > 是否移除第30行之後的內容? > 水哥說用不到，可以移除 > 已移除並commit
- [x] 完成CD pipeline設定
    - [x] ==和守敬確認 data-api, telcoapi CD管線 > 守敬說他要負責==
    - [x] VLO
        - [x] keycloak health check path 不確定 > excel有誤，比照UAT設定就好
        ![](https://hackmd.io/_uploads/SkM3Y9BA2.png)
    - [x] VCP
        - [x] telcoapi health check path 不確定 > excel有誤，比照UAT設定就好
        ![](https://hackmd.io/_uploads/HJkroqB0n.png)
        - [x] data-api health check path 不確定 > excel有誤，比照UAT設定就好
        ![](https://hackmd.io/_uploads/BJsvscS02.png)
- [x] 確認PROD DB查資料向誰申請??==水哥找國樑經理討論細節9/13==
    - GCP DB查詢方式
       - Workaround:用Webex直接連絡DBA做查詢
       - SOP預計下週出來:國樑今天值班人不在座位上(每個APID會申請時限內的固定帳號，使用前email+VDI操作)
       - 2024 Q1預計會有正規的申請工具
- [x] 確認資管人員: 管線建置是==李孟哲==，當天執行就是==值班人員==
- [x] 送出 prod CD pipeline 建置 資管單 (單號:919439) 
    - ==守敬協助開單== https://pialm01/tfs/DefaultCollection/ALM-Service/_workitems/edit/919439/
- [x] 取得 icontact 上線聯繫單 (單號: 60400202300611)
- [x] 取得 TFS APID 中介單 (https://pialm01/tfs/web/wi.aspx?pcguid=759a57b1-a42e-42f9-ab76-9ae16f31a1c2&id=918503)
- [x] 送出 prod release project 上線單 (單號:924053) > 9/14 上線部屬
https://pialm01/tfs/DefaultCollection/Release%20Project/_workitems/edit/924053
    - 檔案放置路徑: \\\pf03\越南數位消金專案
    - [x] NAPAS-Pilot不需要執行CICD任何batch相關的(所以VLO prod db沒有batch設定)
    - [x] 確認release project是否有關聯回自動建立的單(中介單)
    - [x] 確認上班日的資管負責人，才可以assign單
    ![](https://hackmd.io/_uploads/HyBYbsaR3.png)
    - [x] 需新增task assign to DBA 執行sql > DBA 邱啓原,9/13下午執行完成
    - [x] itext license 一定要更新!!! > 9/21 執行backoffice, camunda license更新!
    - [x] 程式定版(9/8會定版 > branch 0.6.3 = 外宣 0.6.5)
        - [x] security certs 等水哥提供prod的
        - [x] 然後Dockerfile要跟著修改
    - [x] SIT測試報告
    - [x] UAT測試報告
    - [x] 變更上線檢核表
    - [x] USER 允許上線mail
    - ~~HRI(風險辨識評估表) > icontact有了~~
    - [x] Lucent sky掃描報告
    - ~~壓力測試報告~~
- [x] 新增憑證 & 修改 dockerfile ==(0.6.3分支加上這些)==
    - [x] VLO app-api: 新增 cer-prod.cer
    - [x] VLO backoffice-api: 新增 cer-prod.cer
    - [x] VLO camunda: 新增 cer-prod.cer
    - RUN keytool -import -v -trustcacerts -alias "keycloak-prod" -file ./xxxxxx/cer-prod.cer -keystore "/usr/java/openjdk-17/lib/security/cacerts" -noprompt -storepass changeit
    ![](https://hackmd.io/_uploads/Syu4uVuC2.png)
- [x] 確認DB 的 instance create + DDL & DML
    - [x] instance create (icontact單:60400202300690)
    ![](https://hackmd.io/_uploads/r1WHDwuAh.png)
    ![](https://hackmd.io/_uploads/ryrXDPdCh.png)
    - [x] 檢查 VLO(HES+keycloak) DDL & DML
    https://gitlab.vn-loancloud-dev.com/ovs-lx-vlo-01/cdc/-/tree/main/sql/prod-init
    - [x] 檢查 data-api DDL & DML
    https://gitlab.vn-loancloud-dev.com/vn-bff/dataapi/-/tree/napas-prod-0908
    - [x] 檢查 telco-api DDL & DML
    https://gitlab.vn-loancloud-dev.com/vn-bff/telcoapi/-/tree/napas-prod-0908
- [x] 確認防火牆設定 
    - [x] ==守敬9/7會寄出統計信件==
    - [x] 單據處理進度
- [x] 服務帳號和權限設定
    - [x] GCP是否有service account & 對應權限
    - [x] User是否有SSO & keycloak相關設定權限 > 宜峰請郁涵協助將我們加入
    ![](https://hackmd.io/_uploads/B1SeJ-hAh.png)
    ![](https://hackmd.io/_uploads/H15eJ-nC2.png)
    ![](https://hackmd.io/_uploads/SykMy-hA2.png)
    ![](https://hackmd.io/_uploads/ryVzJ-302.png)
    ![](https://hackmd.io/_uploads/Hk_zybh0h.png)
- [ ] VDI 申請 預計9/?? (單號:)
- [x] ECAB 緊急上線變更SOP? 
    - CAB2核准信件是區間，專案類型不太可能一步到位，有執行空間
    - 沒有驗證結束，先別把Ticket關閉
    - CDC-通路經驗：在Ticket下面用留言增補內容，然後請科長在留言核准，就可進行增補

### 各服務mock串接狀況:
0906 有最新的
![](https://hackmd.io/_uploads/BkMUgpHR2.png)

### 設定keycloak
![](https://hackmd.io/_uploads/S1jf5Cpah.png)

### Role-Action對應表
![](https://hackmd.io/_uploads/HyCXqApTn.png)

### 柬埔寨上線流程參考資料
\\88.8.125.67\hq00083_其他\CDC 資訊開發中心\技術開發\參考資料\上線參考資料\
![](https://hackmd.io/_uploads/BkY0E-ECh.png)

### Customer-hub
![](https://hackmd.io/_uploads/r1u-2xw03.png)

### Memo
![](https://hackmd.io/_uploads/r1XPXXNAn.png)

1. icontact 變更單單號範例：90100202300101
2. 申請TFS Release Project權限: https://pialm01/tfs/DefaultCollection/ALM-Service/_workitems/edit/686927
3. 申請變更單下拉選單可看到特定APID: 要找棨達新增APID選項
![](https://hackmd.io/_uploads/rJ3FdOrCn.png)
4. release project 申請單範例: https://pialm01/tfs/DefaultCollection/Release%20Project/_workitems/edit/842390

找水哥or資管確認:
- 確認hemlchart正式環境的APID是跟UAT一樣??確認運行方式?
  水哥問庸原config的PROD環境要怎麼給資管(9/6確認)
- 若需DBA作業，需在TFS-Release Project此單(Step3-1)加入Task OR 俊言 OR someone??
標準作法: 
   1. 跟俊言申請PROD DB環境
   2. 再請DBA建立真正的secma instance等等->在gcp上看的到cloud sql的instance表示成功
   3. 再請DBA建立DDL-->守敬申請(預計9/8 TPI提供,守敬開單;9/11提出申請)

