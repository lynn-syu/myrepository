### 事前準備
- 需要申請VDI 連上PROD DB防火牆ex: 00083202301666
    - 申請後DBA會寄一組帳密到自己的信箱
- 需要請郁涵開通全行資訊網"VDI檔案傳送系統"的節點
### 步驟

#### step1
- 請同事們share PROD DB相關連線資訊到自己的share folder
![image](https://hackmd.io/_uploads/ByrK5YaN6.png)
- 新申請同事連線到自己的PROD share folder看到分享的檔案**並解壓縮**
    - postgres.zip (連線資訊)
    - PremiumSoft.zip (連線工具)
![image](https://hackmd.io/_uploads/BJ1OsKaNa.png)

#### step2
 - 解壓縮postgres.zip並確認有要連線的APID資料夾
 ![image](https://hackmd.io/_uploads/BkUJyq6Ep.png)

#### step3
 - 解壓縮PremiumSoft取得DB連線工具
 - 需選擇Tril版本(因為我們沒付錢)
 ![image](https://hackmd.io/_uploads/rJyN3Yp4T.png)
 - 打開後一直按下一步完成安裝

#### step4
 1. 點選連線選擇Postgres
 2. 點選General
 3. 輸入資訊
 4. 點選Tab:SSL
 ![image](https://hackmd.io/_uploads/rJO8lc6Na.png)
 5. 將紅框部分打勾且依照圖片放入相對應憑證
 ![image](https://hackmd.io/_uploads/ryK--5a4T.png)
 6. 點選OK
 7. 可看到新建立的連線資訊
 ![image](https://hackmd.io/_uploads/BJNUb5TEp.png)

#### 以上步驟即可正常連線且執行查詢

#### 以下步驟可節省連線時間，自行評估操作
#### step5
 - 第一次完成連線後，下次重新登入，PROD連線資訊又會被清除，因此衍生此方法
#### step6
 - 在開始輸入"regedit"且打開程式
 ![image](https://hackmd.io/_uploads/HkGpMqp4a.png)
#### step7
 - 選擇HKEY_CURRENT_USER => PremiumSoft => NavicatPG => Servers
![image](https://hackmd.io/_uploads/rkzwXcpET.png)
 - 對"Servers"點選右鍵 =>匯出
![image](https://hackmd.io/_uploads/HJ2C7qpN6.png)
 - ==一定要放在自己的share folder 根目錄，不然下次打開VDI檔案就會不見==
 ![image](https://hackmd.io/_uploads/BJoME5aNa.png)
#### step8
 - 完成以上動作後，改天有連線需求，先遵照 ==step3==安裝DB工具
 - 再double click 儲存的reg key即可匯入連線的資訊，節省重複輸入連線資訊的時間