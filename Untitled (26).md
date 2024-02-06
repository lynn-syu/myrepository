## appInit (POST) (api/v1/init)
> call util->camunda-api 介面camundaClient.appInit()
@PostMapping("api/customer/app-init")
:::success
- 真正實作為Camunda專案下的AppInitController
- call customerService.appInitCustomer()
    - 前端傳入customerAPPData取cuid
    - 在Table:customer找customerIdOpt
    - 如果沒有此customerId則createCustomer() ==寫紀錄到Table: aduit_event+註記為draft==
        - 檢查是否有正在註冊中 isCustomerProcessRunning
        - call saveCustomerAPPData存入User info
        - 寫紀錄到Table: aduit_event
        - ==啟動流程: startCreateApplicationProcess()==
        - 在各地方取資料後最後執行**runtimeService.startProcessInstanceByKey()** 真正在Camunda啟動流程
:::