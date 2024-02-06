[toc]

## VCP
:::success
- local啟動專案
1. 申請GCP service account - JSON key (金鑰)
2. 設定在電腦的環境變數裡 (變數名稱:GOOGLE_APPLICATION_CREDENTIALS)
![](https://hackmd.io/_uploads/HkymVn292.png)

- keycloak-SSO流程
![](https://hackmd.io/_uploads/HJpGFh2c2.png)
    - 1.全行資訊網=全行資訊網Web
    - 2.Web=各APID後台Web(不含Intellect)
    - 3.Keycloak
    - 4.checkToken=全行資訊網Web-checkToken功能

- 透過OPEN API.yml建立我們所要的restful 規格
    - 通常建立的規格會放在以下路徑: XXX\specs
    - ex: OVSLXVCP01-BSP\vcp-svc-msg-center\specs
![](https://hackmd.io/_uploads/Hks9lp3qn.png)
    - 目前執行gradle生成的檔案沒有推到git，執行方式為在local長出相對應的interface.java檔案，再人工搬移到src/xxx/xxx相對應的路徑下
![](https://hackmd.io/_uploads/rkfYGph5h.png)


- 依照需求選擇asynchronously OR synchronously 來發送訊息
![](https://hackmd.io/_uploads/BJh_S-R93.png)

- msg_center沒有重送機制

- esign 傳送去生成檔案時沒有紅框的內容，把空白檔案和客戶和國泰資料提供後就會如下圖
![](https://hackmd.io/_uploads/BJpYrz0ch.png)

提供PPT沒有的畫面
![](https://hackmd.io/_uploads/SkzWBMA93.png)


- itext是一個套件(build在image裡面，gradle/maven)，目前是用試用版，他需要license才可以真的使用。從license-key(json):裡面有一個expirationDate就是到期日
![](https://hackmd.io/_uploads/HktdUMA52.png)
:::

## CDW Batch
:::info
- 資料來源為資料庫
![](https://hackmd.io/_uploads/HJ7quf05h.png)

- 紀錄資料的table
![](https://hackmd.io/_uploads/H1ThOzRqh.png)
:::

## File Batch
:::info
- 資料來源為GCS
![](https://hackmd.io/_uploads/ryAR_fA9h.png)

- 紀錄資料的table
![](https://hackmd.io/_uploads/r1o1KfA93.png)
:::

## House Keeping
:::info
- 直接access GCP上面的SQL服務(postgre)去清除table 資料
![](https://hackmd.io/_uploads/Bya7YMCqh.png)
![](https://hackmd.io/_uploads/HydwFfC9n.png)

- 紀錄資料的table
![](https://hackmd.io/_uploads/HyPBKG0q2.png)
:::

# CUB x Google
- what's in a version? 
1(k8s major version).20(k8s minor version).5(k8s Patch)-gke(GKE-Specific Patch).10(etcd....)

























