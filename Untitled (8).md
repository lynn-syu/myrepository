- URL收集區
keycloak > https://ovs-lx-vlo-01-keycloak.gcub.gcp.uwccb/admin/master/console/#/
camunda > https://ovs-lx-vlo-01-camunda.gcub.gcp.uwccb/camunda/app/welcome/default/#!/login
backoffice-frontend > https://ovs-lx-vlo-01-backoffice.gcub.gcp.uwccb

:::success
9/18 起的工作項目
- 第三期驗收文件(\\pf03\越南數位消金專案\03.驗收文件\第三期_20231003) 
- [x] TFS branch 切分(因應不同上線階段) & 和TPI討論
    - Branch (VLO + data-api, telco-api)
        - develop-SIT
        - staging-UAT
        - release-PROD
        1.每個repo建立release/staging/develop分支對應 PROD/UAT/SIT環境 > all done
        2.每個repo分支release(PROD)下tag v1.0.0 > all done
        3.每個repo對應的helmcahrt 下tag r1.0.0 > 請守敬開權限 > VLO done
        4.Jira建立版號 r1.0.0 > 安邦負責
    - [x] 等待batch定版也要執行git分支建立
    ![](https://hackmd.io/_uploads/HJ4GUDSya.png)
2. 盤點需要TPI修改的項目(ex log完整化+traceId)
- [x] 9/22 前要更換itext license 並且重新上版prod (backoffice-api & camunda 效期10/21)
- [x] 10/21 前要更換itext license 並且重新上版prod，**需申請eCAB**
     - 10/18完成SIT/PROD config更新
     - 10/20完成 ecab prod部屬 (==下個到期日 11/17==)
- [x] ==10/24== 找水哥核對CAB2-2的整個設定檔
- [x] 確認數據有沒有在GCP建置pub/sub topic
- [x] 確認call 數據兩支pub/sub topic name 
- [x] 確認雲地交換檔案的路徑是不是有通-HES
- [x] 確認雲地交換檔案的路徑是不是有通-government
- [x] data-api 新增 PCB接NA資料的API，水哥提供再部上SIT/UAT測試 ==期限:10/13==
- ==疑似 telcoapi 呼叫datanest部分也要留存log (新增table紀錄) 然後透過自己的cdw-batch 在轉成csv傳給數據中台==
- [x] 申請prod db 防火牆開通
    - icontact單號 for 防火牆開通 60400202300791 > 已開通
    ![](https://hackmd.io/_uploads/HJujshm-6.png)
    ![](https://hackmd.io/_uploads/B1TH32qk6.png)
- [x] telco-api, data-apia log優化 ==期限:10/13==
    10月中有close beta  表定10/2X報CAB2
    - [x] lucent sky掃描
    - SIT 測報(不需要)
    - UAT 測報(不需要)
    - [x] DDL for alter table (下次正式上板跟著給資管)
    - [x] 定版同步給水哥(表定下禮拜五10/6)
    - [x] 申請UAT db 查看權限(單號:00083202301583) > done
- [x] 申請UAT VLO batch db (單號:00083202301633)
- [x] 申請PROD VLO 臨時帳號(單號: 00083202301666，期限:2023/12/31)
:::

:::warning
- [x] Camunda 系統面交互盤點
    - [x] document-template.bpmn
    - 目前不一一盤點create-application，採取case by case的方式處理
    依照實際狀況再review程式部分，例如遇到issue/bug、宜峰or User提問，針對提問部分釐清就好。
- Camunda 正式進階維運情境 > ==此項工作暫時pending==
    - [x] localhost 啟動camunda cockpit畫面debug
    :bulb: solution: 這是intellij的bug
    https://forum.camunda.io/t/camunda-cockpit-plugin-produces-404-error-for-version-7-11/16788
    https://forum.camunda.io/t/camunda-webapp-is-stuck-on-loading/22042/15
    ![](https://hackmd.io/_uploads/SJ2sRey02.png)
    - [x] 了解camunda database 
    官網: https://docs.camunda.org/manual/7.19/user-guide/process-engine/database/database-schema/
    中文: https://zhuanlan.zhihu.com/p/543436973
    - [ ] 了解bpmn版本比對方式
        - 下載前後版本bpmn，使用Modeler比對
        - https://demo.bpmn.io/diff =>只能比流程name&icon等等，delegate修改比對不出來
    - [ ] cockpit version切換版本查看該如何操作使用
    - [ ] 當流程有新版本，確認舊版正在進行的流程該如何處理
    - [ ] 流程前後版欄位有變動要怎麼處理
        - 可能需要找BU端確認版本迭代的情境為?
        - 與宜峰聊過，BU端應該還沒想到這麼遠OR他們根本不知道也不care這一塊
            1. 要求舊版流程需要全部完成
            2. 舊版若未完成直接termiate
            3. 舊版In process流程直接切到新版本
        - 若有新增欄位，舊版流程如何補上新欄位資訊?
:::

- [x] HES UAT DB 連線申請? 
    - [x] Hank&Lynn申請單(for HES & keycloak read權限): 00083202301184 
    - 若有其他db需要再另作申請

- [x] 聯繫守敬分享HES 在GCP上設定(CD有成功就可以 gcp不用調整) batch手順及重點
    - 0821 已聯繫,待守敬喬時間
    - [x] 九月只需要完成house keeping UAT，自行研究CD，遇到問題再求救
    - [x] 需要加上cdw-batch > 10/6 已再UAT執行 (sql要再三確認)
    CDW => 主要是數據中台那邊要分析的資料，裡面有需要vlo的table & column
    - file-batch ==(SIT+UAT都沒有)==，要執行的標的尚未確認(水哥盤點中)
    File => 因為有些顧客敏感性資料議題，所以要把檔案放到地端 > 水哥說不需要


- [ ] ~~TPI於八月底協助~~ local(HES)啟動
    - [x] 0825 自立自強研究出來了
    - [x] 確認串SIT DB + SIT keycloak
    - [ ] 驗證front -> backoffice -> query sit DB (lynn done)
    - [x] 驗證app-api -> camunda查詢

- [x] 找水哥討論telco-api,data-apia 
    - [x] 0828 請負責人說明詳細邏輯-金控子謙
    - [x] 起 localhost by SIT環境
    - [x] ~~加入swagger ui 功能 > 0831水哥說要確認~~> ==在local 有開啟了==
    - [x] (前提 真的要接手的話) 維運組進行log調整 > 0831等下一個phase
        - 需要再討論要用甚麼方法統一記log的方式 > CAB2-3
    
- [x] 找水哥討論，HES專案要求TPI補齊log
    - [x] 0828 有會議跟水哥說明目前情況
    - [x] 等待水哥和TPI交涉的結果 >> 0829水哥回覆 會請TPI調整 仿照VCP with traceId
    - [x] 追蹤TPI修改的結果 & code review  > 0831回答等下一個phase
        - 2023/09/07 Pino的上板資訊 app-api, backoffice-api 也加上 FeignLogger
        - 1006 已確認 UAT 有正常紀錄 execution_record

- [x] daily meeting詢問 itext license @prod 時要怎麼維護?
    - 0828 水哥說已經有某人負責追蹤了，是簽約問題尚未有結論
    - 0905 會議說目前還是用試用版會在上線前再更新一次LICENSE

- [x] TFS 建立 VLO+VCP *2 for PROD CD pipeline & helmchart ==before 9/18==
    - [x] 先請教玟君
    - [x] 建立 VLO CD PROD 腳本，遇到問題問水哥 > 先完成Other資料夾6個CD pipeline
    - [x] 建立 VLO Helmchart PROD 設定，遇到問題問水哥 > 先完成對應的helmchart prod
    ![](https://hackmd.io/_uploads/BkyCwxy02.png)
    - [x] 建立 VCP *2 的 CD PROD 腳本 + helmchart
    - [x] 找張譯方(是不是離職了....)詢問 DB & Manual CD pipeline的功用
        - 玟君詳細解釋了
    - [x] 根據TPI回覆的Helmchart 設定結果再次調整
    - [x] 開單給資管同仁 for VLO PROD CD pipeline
    - ~~和守敬約 9/5 聯合資管一起討論~~

:::warning
- 於既有的專案找一個地方建 demo controller寫api呼叫GCP資源 並放上GCP實測 (安邦似乎覺得不用了XD)
- 前端 UI的研究
    - axio interceptor: https://axios-http.com/docs/interceptors, https://ithelp.ithome.com.tw/articles/10230336
- merge回master ==> git flow 的實踐
- 有聽玟君說可能會換itext=>之後程式可能要改=>換Aspose
- 玟君有約一場會議請TPI講解keycloak
:::
