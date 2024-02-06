[toc]

:::info
此Controller的使用方為後台畫面
1. 先透過 getDocumentTemplates 取出所有的template成為清單呈現在畫面
    - 清單上每一筆資料都能透過id進行preview (getDocumentPreview) 和download (getDocumentTemplate)的(Actions按鈕)功能
2. 畫面右上角有一個upload的按鈕，可以執行新增動作 (uploadDocumentTemplate)
    - 目前只定義一種type (CREDIT_LIMIT_APPLICATION_AGREEMENT)
    - 每筆資料須提供： type, startsAt(生效時間 yyyy-mm-dd HH:mm:ss), template(檔案本人 byte[]), status預設為 INACTIVE
    - 直接依照提供的資料新增一筆到db (document_template)
    - 呼叫camunda 新增一個 document_template_process (以template id去辨識)
        - 呼叫 generateDocumentPreviewDelegate 把db的 document_preview欄位補上去
        - 以startsAt為計時器，才去呼叫 documentTemplateActivationDelegate 讓該合約生效 => 把目前ACTIVE變成INACTIVE，再把該ID的變成ACTIVE 

3. 隱藏功能(畫面上沒有)，可以依照id去執行delete: 會直接去刪除db資料，以及呼叫camunda把processInstance刪掉

![](https://hackmd.io/_uploads/rJN1k3Vs3.png)

:::

# DocumentTemplateController
## getDocumentTemplates (GET) (api/document-templates)
> call DocumentTemplateService.findAll
:::success
1.呼叫 DocumentTemplateRepository SQL
- findAll: 找 document_template

2.call DocumentTemplateController
- getDocumentPreview: 取得previewUrl
- getDocumentTemplate: 取得templateUrl

3.組合以上資料，以分頁方式回傳前端
:::

## getDocumentPreview (GET) (api/document-templates/{id}/preview)
> call DocumentTemplateService.getDocumentPreview
:::success
1.呼叫 DocumentTemplateRepository SQL
- getDocumentPreview: 找 document_template

2.依照查詢條件回傳
:::

## getDocumentTemplate (GET) (api/document-templates/{id}/template)
> call DocumentTemplateService.getDocumentTemplate
:::success
1.呼叫 DocumentTemplateRepository SQL
- getDocumentTemplate: 找 document_template

2.依照查詢條件回傳
:::

## deleteDocumentTemplate (DELETE) (api/document-templates/{id})
> call DocumentTemplateService.delete
:::success
1.呼叫 DocumentTemplateRepository SQL
- delete: 找 document_template ==(真的從DB刪除)==

2.呼叫 CamundaClient.documentTemplateProcessDelete
- call BoUserService.documentTemplateProcessDelete(id)
    - call RuntimeService.createProcessInstanceQuery 
        - 透過documentTemplateId -> map(::getProcessInstanceId)
    - for loop call RuntimeService.deleteProcessInstanceIfExists(instanceId) 
:::

## uploadDocumentTemplate (POST) (api/document-templates)
> call DocumentTemplateService.saveDocumentTemplate
:::success
1.呼叫 DocumentTemplateRepository SQL
- create: 新增一筆 document_template ==初始的status設定為 INACTIVE==

2.將剛才新增的 documentTemplateId 回覆給CamundaClient使用

3.呼叫 CamundaClient.documentTemplateProcessStart
- call BoUserService.documentTemplateProcessStart(documentTemplateId)
    - call RuntimeService.startProcessInstanceByKey 
        - 組合一個 key: "document_template_" + documentTemplateId
        - 以及提供Camunda此template的生效日
:::