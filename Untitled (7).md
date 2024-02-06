:::success
問題撰寫範本:
**標題 :** 通常放信件名稱OR簡單描述問題
**日期 :**
**提問人 :**
**現況 :**
**問題描述 :** 
**解決辦法 :**
**root cause:**
**備註 :**
:::

:::success
**標題 :** Call [VCP] customer service回覆訊息失敗
**日期 :** 2023/10/12
**提問人 :** 宜峰
**現況 :**
![](https://hackmd.io/_uploads/SJcGvQSWT.png)
在Application agreement流程中，會是簽署後打customer service 
**問題描述 :**
目前call customer service失敗，camunda走到retry的機制，但因為流程狀態不對，所以該張單並沒有執行retry
![](https://hackmd.io/_uploads/rJwcMVr-p.png)

User在畫面看到Tab: application看是pause狀態
但在Tab: service error看是service retry，狀態是不對的
**解決辦法 :**
![](https://hackmd.io/_uploads/BkzItQS-a.png)
**root cause :** 因程式有問題導致狀態變更失敗，無法從PENDING=>PAUSE，PINO目前進行調整
CreateCustomerService.bpmn裡面的throw sginal少設定變數
![](https://hackmd.io/_uploads/SJBwwVBba.png)

**備註 :**
:::

:::success
**標題 :** BackOffice eKYC圖片無法顯示、合約檔案無法下載
**日期 :** 2023/11/17
**提問人 :** 越南BU 以霖
**現況 :**
![1](https://hackmd.io/_uploads/BypelnE4T.jpg)
- 開啟eKYC，圖片無法顯示

![2](https://hackmd.io/_uploads/H187e3VN6.jpg)
![3](https://hackmd.io/_uploads/BJVUl2ENp.jpg)
- 檔案下載回應500錯誤

**問題描述 :**
- 在原始設計中，AOP有API操作紀錄的流程，但Lynn發現寫入紀錄有error log產生，推測因為寫入失敗的關係，導致API回應500錯誤。
![OUTLOOK_C5aNCHqPru](https://hackmd.io/_uploads/B1GafhVNT.png)

- 詢問TPI操作紀錄的邏輯。
![image (1)](https://hackmd.io/_uploads/SJRdX24E6.png)

- 發現execution_record_url的index，影響寫入資料，有長有短都會有機率造成寫入失敗。

- 移除index後，資料就沒有再顯示寫入失敗的log。

- 索引條目大小超過了PostgreSQL中B樹索引的最大允許大小
    - PostgreSQL Index：
        - 預設B-tree最大為2704 bytes.
        - 當該欄位直超過，會無法建立索引。
           - 所以當時有時候可以寫，有時候不能寫入(大家的token長度不同)。


**備註 :** 另外需要注意，待日後資料量大的時候，可能有其他Table的Index有相關問題。
**備註 :** execution_record & feign_client_record 日後須加上house-keeping機制
:::