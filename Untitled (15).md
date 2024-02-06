- 路徑: (\\pf03\越南數位消金專案\03.驗收文件\第三期_20231003) 

## 第一部分 > 有問題再給 TPI 修改
:::success
- [ ] 5.HES徵審進件業務_專案需求規格文件 > ==宜峰有在維護==
- [ ] 42.程式設計規格書_HES_Frontend > Hank有在信上提出內容調整
- [ ] 43.程式設計規格書_HES_backend -> Hank有寄出範本，需要跟PM&水哥&TPI討價還價
- [ ] 44.gitlab MD檔 =>如何透過open api產生文件教學流程 
- [ ] 47.HES_資料庫規格書 > Lynn已列問題
        1. vlo_keycloak 是否要列舉重點table?
        2. camunda 是否要列舉重點table?
        3. access_event, access_event_header 沒有說明? (git上的紀錄 0.6.8 -- 移除不使用的 table)
        4. feign_client_record 沒有在文件裡?
        5. keys? merchant? 也都沒有在文件
        6. bank 上次小馬回答沒有使用了? bank_account 是空的沒資料?
        7. credit, attachment, comment SIT db是空的沒資料? 這樣還有在使用? 
        8. 缺少 execution_record?
- [ ] 79.系統安裝說明手冊_HES進件徵審系統(OVS-LX-VLO-01) > Lynn已列問題
    - 貳、系統安裝/設定說明
            1. GCP相關設定?
            2. Anisible 在哪裡有使用?
            3. 哪邊會說明專屬於HES 的keycloak image內容?
    - 參、開發環境設定說明
            1. Configuration的參數內容該如何調整與使用? (像是helmchart?)
            2. Keycloak configuration guide的圖片樣式和現行的不一樣吧? 是所有步驟(ex. email)都是prod需要的?
            3. Keycloak還需要 Customer Realm?
            4. Keycloak backoffice role and group guide 怎麼沒有User加入group? 
    - 如果是local 開發 該怎麼調整? 
    - Camunda 的使用說明?
:::

## 第二部分 > 宜峰協助
:::success
- 103.HES_徵審進件業務功能_專案系統文件_系統操作使用說明
- 104.HES User Guide
:::

## 第三部分 > 看裡面關於 HES的部分 or 有data-api&telcoapi
:::success
- 82.系統維運手冊_目錄 (只是目錄不重要)
- [ ] 83.01_架構圖(僅架構圖，無意見or跟水哥對一次)
- [ ] 84.02_應用系統服務
    - 應該是2.7.2??
    ![](https://hackmd.io/_uploads/SyKlcfqWT.png)
    ![](https://hackmd.io/_uploads/r1d0YGcWp.png)
       - 這邊與google service對應不起來
    ![](https://hackmd.io/_uploads/HyXB2Mcba.png)
       - 下圖水哥有相關INFO可提供比對?
    ![](https://hackmd.io/_uploads/SJM6fL9Za.png)
       - 需提供最新版
    ![](https://hackmd.io/_uploads/HydlrI5-p.png)
- [ ] 85.03_數據服務
    - 大部分是數據team相關的
    - 有一個 自動徵審流程架構圖 (hes -> data-api/telcoapi) 但也不知道架構對不對
- [ ] 86.04_外部服務及數據源
    - (說明:HyperVerge,Twilio,SendGrid,FPT E-Sign,Trusting Social,DataNest)
    - Trusting Social:
        ![](https://hackmd.io/_uploads/HyjtOU9bT.png)
    - Telco API 若單是文件上的，在程式都有，不過沒有看到後來新增NA部分的API
        ![](https://hackmd.io/_uploads/BJttAUc-6.png)
- 87.05_支付清算平台(不是我們的Scope)
        ![](https://hackmd.io/_uploads/r1GOkvqWa.png)
- 88.06_地端外圍系統
    - 架構沒包含到我們的batch系列 > 我覺得是TPI沒有寫上去
    ![](https://hackmd.io/_uploads/SygxfP5bT.png)
- [ ] 89.07_CIF
    - 此議題應該還在討論階段，可能有做法的調整
    ![](https://hackmd.io/_uploads/rk-2SDqbp.png)
- [ ] 90.08_全行資訊網安全系統整合說明(應有達到需求 > 同意，內部防火牆那些也很難寫)
- [ ] 91.09_應用系統監控說明-logging/tracing/monitoring
    - 只提到logging/tracing，並未提到監控，或許需新增紀錄record的table&機制?
- [ ] 92.10_應用系統備份及復原說明-backup/restore(infra)
    - 完全不是我們可以操作的範圍，這個應該要給水哥和俊言review
- [ ] 93.11_應用系統HA/負載平衡/備援機制說明
    - 可配合HorizontalPodAutoscaler設定當單一Pod負載過高時，即時產生新的Pod，避免臨時的大量請求，所有專案皆有使用此設定。設定值還沒確定吧?
    - 內容沒有意見，只是寫得很厲害，我們真的有做設定嗎?
- [ ] 94.12_應用系統安全性設定說明
    - 本案在系統部屬前接透過Sonaqube進行程式碼的弱掃處理? 有嗎
- [ ] 95.13_BCP異地備援演練程序說明
    - 內容是在描述{通路中台(全體適用)}的台灣區GKE異常，要切換到新加坡區的GKE == 
    - 到底有沒有新加坡的GKE我很懷疑(NO，我們只架設在台灣機房)
    - 情境一和情境二的動作真的有執行? 根本連GCP都無法操作，還要prod的db連線設定調整才能請資管deploy要報ecab吧?
:::