
## 架構面: 

- [x] 專案與keycloak是如何綁定及設定??

1. 根據專案裡面的role，請問我們再keycloak要怎麼做設定才能符合專案的需求呢?
以後會是Water/守敬您們設定嗎?請問這邊會有流程圖/文件?

Ans: keycloak上面的角色 設定會是由==業管==在需求訪談的時候，就討論出來
在本專案~ 九月上線，uat/prod，keycloak 基本上不會需要特別操作啥，(最多進行檢查 以及設定SSO的部分)
==權限啥的會由init sql(ddl dml 的部分就建立好)==
如果之後有新的需求會再增加新的role，才有你提的這段實作，
不過TPI在教學HES的時候?應該會講到這段? keycloak的操作，(我自己看來2023年內 不會有新增role的需求)


2. 看程式有需求是需要上傳檔案的，請問我們有那些空間 GcpStorage? LocalStorage? LoggingStorage?

Ans:詳見excel (OVS-LX-VLO-01_SA頁籤:Cloud-Storage欄位)
![](https://hackmd.io/_uploads/B1k2Ay-K2.png)


:question: ==還是不懂????== 可以協助說明: StorageAutoConfiguration對應上方那些xxxStorage.java
![](https://hackmd.io/_uploads/SyZzUgZK3.png)

3.請問CICD在TFS上我們可以在哪邊看到設定?
因在HES找不到資料夾"Helmcharts"，請問我們在哪邊可以找到最新的設定檔?

Ans: https://pialm01.cathaybk.intra.uwccb/tfs/DefaultCollection/CUB-DevOps_Configuration_Management/_git/OVSLXVLO01 =>Water提供，

![](https://hackmd.io/_uploads/Hy5YElbYh.png)
![](https://hackmd.io/_uploads/ryGoVebK3.png)

---



