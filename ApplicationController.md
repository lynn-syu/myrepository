###### tags: `backoffice-api 學習筆記`

[toc]

# ApplicationController
## getApplications (GET) (api/applications)
> call ApplicationService.findAll
:::success
1.呼叫 ApplicationRepository SQL
- findAll: 找 application, customer

2.依照查詢條件分頁回傳前端
:::


## getApplication (GET) (api/applications/{id})
> call ApplicationService.getById
:::success
1.呼叫 ApplicationRepository SQL
- findById: 找 application, customer, customer_group
:information_source: 找不到回傳 NOT_FOUND

2.call CustomerController
- getSelfie
- getNationalIdFrontSide
- getNationalIdBackSide

3.call ApplicationController 
- getApplicationAgreement
- getApplicationOverview
- getNationalIdBackSide

4.呼叫 ApplicationRepository SQL
- hasOverview: 找 application
- hasAgreement: 找 application
- getProcessId: 找 act_hi_procinst

5.組合以上資料回傳前端
:::


## getTaskComments (GET) (api/applications/{id}/comments)
> call ApplicationService.getComments
:::success
1.呼叫 ApplicationRepository SQL
- findAllComments: 找 comment, room, application, bo_user

2.依照查詢條件分頁回傳前端
:::


## getAllAttachmentsByApplicationId (GET) (api/applications/{id}/attachments)
> call ApplicationService.findAttachments
:::success
1.呼叫 ApplicationRepository SQL
- findAttachments: 找 application, room, attachment, bo_user

2.依照查詢條件分頁回傳前端
:::

## getApplicationEvents (GET) (api/applications/{id}/history)
> call ApplicationService.getEvents
:::success
1.呼叫 ApplicationRepository SQL
- getEvents: 找 audit_event, bo_user, customer

2.依照查詢條件分頁回傳前端
:::

## getApplicationAttachment (GET) (api/applications/{id}/attachments/{attachmentId})
> call StorageService.getAttachment
:::success
1.呼叫 StorageRepository SQL
- isAttachmentMatchApplication: 找 application, room, attachment
- getAttachmentMetadata: 找 attachment

2.呼叫 AuditRepository SQL 
- getUserEmployeeNumber: 找 bo_user
- getCustomerIdNumberByApplicationId: 找 customer, application

3.call AuditEventService.addAttachmentActionEvent
- 呼叫 AuditEventRepository SQL
    - addEvent: 新增一筆 audit_event

4.組合以上資料回傳前端
:::

## getApplicationAgreement (GET) (api/applications/{id}/agreement)
> call DocumentService.getAgreementForDownload
:::success
1.call StorageService.getAgreement
- 呼叫 StorageRepository SQL: 
    - hasAgreement: 找 application
    - getCustomerIdByApplicationId: 找 application
 
:information_source: 若找不到(fileNotFound exception) 程式結束

2.呼叫 DocumentRepository SQL:
- getApplicationSerialNumber: 找 application

3.call ItextService.addWatermarkToPdf
- 呼叫 ItextClient.addWatermarkToPdf 

4.組合以上資料回傳前端
:::

## getApplicationOverview (GET) (api/applications/{id}/overview)
> call DocumentService.getApplicationOverview
:::success
1.call StorageService.getApplicationOverview
- 呼叫 StorageRepository SQL: 
    - hasApplicationOverview: 找 application
    - getCustomerIdByApplicationId: 找 application

:information_source: 若找不到(fileNotFound exception) 程式結束

2.呼叫 DocumentRepository SQL:
- getApplicationSerialNumber: 找 application

3.call ItextService.addWatermarkToPdf
- 呼叫 ItextClient.addWatermarkToPdf 
Ans: 使用spring boot feignClent方式連到外部服務

4.組合以上資料回傳前端
:::


## retry (POST) (api/applications/{id}/retry)
> call ApplicationService.retry
:::success
1.呼叫 CamundaClient.retryApplication
- call BoUserService.retryApplication(id)
    - call RuntimeService.createSignalEvent 
        - retry-application-%s".formatted(applicationId)
:::

## resume (POST) (api/applications/{id}/resume)
> call ApplicationService.resume
:::success
1.呼叫 CamundaClient.resumeApplication
- call BoUserService.resumeApplication(id)
    - call RuntimeService.createSignalEvent 
        - "resume-application-%s".formatted(applicationId)
:::

## retryByFilter (POST) (api/applications/retry)
> call ApplicationService.retryByFilter
:::success
1.呼叫 CamundaClient.retryApplication
- call BoUserService.retryApplication(filter).getApplicationRetryIds
    - 呼叫 CamundaRepository SQL
        - getApplicationRetryIds: 找 error, application
- for loop call ==BoUserService.retryApplication(id)==
:::

## resumeByFilter (POST) (api/applications/resume)
> call ApplicationService.resumeByFilter
:::success
1.呼叫 CamundaClient.resumeApplication
- call BoUserService.resumeApplication(filter).getApplicationRetryIds
    - 呼叫 CamundaRepository SQL
        - getApplicationRetryIds: 找 error, application
- for loop call ==BoUserService.resumeApplication(id)==
:::