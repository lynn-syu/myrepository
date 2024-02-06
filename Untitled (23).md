
###### tags: `backoffice-api 學習筆記`

[toc]

:::info 
#### *列出通用的 interface*
1. **call interface camundaClient.getTaskVariables()**
*@GetMapping("api/bo-user/tasks/{taskId}/variables")*
--> BoUserTaskController getTaskVariables()實作boUserService.getTaskVariables()
--> boUserService.getTaskVariables() 實作 taskService.getVariables()
- taskService.getVariables()可撈取到task整包資訊，若無權限會跳AuthorizationException

:information_source: taskService為 org.camunda.bpm.engine.TaskService 

2. **call interface camundaClient.getRoomId()**
*@GetMapping("api/bo-user/tasks/{taskId}/room-id")*
BoUserTaskController getRoomId()實作boUserService.getRoomId()
-->中間撈取各種資料
-->call camundaRepository.getRoomId()
-->撈取Table: application 資料得到 roomId
:::

## getTasks (GET) (api/tasks)
> call taskService.getTasks

找 Table: act_ru_task,act_ru_variable,act_ru_variable,application,customer,bo_user,configuration列出all task

## getTaskById (GET) (api/tasks/{taskId}):bulb:
> call taskService.getTaskById
:::success
- call camundaClient.getTaskById()
*@GetMapping("api/bo-user/tasks/{taskId}")*
--> call BoUserTaskController getTaskById() 實作boUserService.getTaskById()
--> call taskService.createTaskQuery()取得資料
:information_source:taskService為 org.camunda.bpm.engine.TaskService 
:::
## getTaskAssigneeCandidates (GET) (api/tasks/{taskId}/assignee-candidates)
> call taskService.getTaskAssigneeCandidates
:::success
- call camundaClient.getTaskVariables()
- call taskRepository.getTaskAssigneeCandidates()
在Table: bo_user找出可assignee的清單(List)
:::

## assignTask (POST) (api/tasks/{taskId}/assign) :bulb: 
> call taskService.changeAssignee
:::success
-  call camundaClient.getTaskVariables() 
-  call camundaClient.changeAssignee()
*@PostMapping("api/bo-user/tasks/{taskId}/assign")*
BoUserTaskController changeAssignee()實作boUserService.changeAssignee()
-->中間撈取各種資料，且檢查是否有permission
-->call auditEventService.addAssignedUserChangedEvent
-->call auditEventRepository.addEvent寫資料到Table: audit_event做紀錄
-->call auditEventService.addTaskManagementEvent
-->call auditEventRepository.addEvent寫資料到Table: audit_event做紀錄
-->set assignee
:::
## getTaskComments (GET)(api/tasks/{taskId}/comments)
> call taskService.getComments
:::success
- isAssignPermissionMissing()檢查權限
- call camundaClient.getRoomId()
- taskRepository.findAllComments()利用roomId查找Table: comment,bo_user的資料
:::

## addTaskComment (POST) (api/tasks/{taskId}/comments)
> call taskService.addComment
:::success
- call camundaClient.getRoomId()
- taskRepository.addComment()寫資料到Table: comment
- call auditEventRepository.addEvent寫資料到Table: audit_event做紀錄
:::

## deleteTaskComment (Delete) (api/tasks/{taskId}/comments/{commentId})
> call taskService.deleteComment
:::success
- call camundaClient.getRoomId()
- taskRepository.canDeleteComment()找Table: comment是不是有這筆資料
- auditRepository.getCommentText()找comment 的內容(text)
- taskRepository.deleteComment()從Table: comment刪除資料
- call auditEventRepository.addEvent寫資料到Table: audit_event做紀錄
:::

## getAllTaskAttachments (GET) (api/task/{taskId}/attachments)
> call taskService.findAttachments
:::success
- 檢查是否有permission
- call camundaClient.getRoomId()
- taskController = methodOn(TaskController.class);:question: :question:
- taskRepository.findAllAttachments()找Table: attachment撈取資料
:::


## getTaskAttachment (GET) (api/tasks/{taskId}/attachments/{id})
> call taskService.getAttachment
:::success
- 檢查是否有permission
- call camundaClient.getRoomId()
- taskController = methodOn(TaskController.class);:question: :question:
- 利用roomID 找Table call taskRepository.getApplicationIdByRoomId找applicationId
- call auditEventRepository.addEvent寫資料到Table: audit_event做紀錄
- call storageService.getAttachment取得附件檔案
    - 取metadata: call storageRepository.getAttachmentMetadata()
    - 取employeeNumber: call auditRepository.getUserEmployeeNumber()
    - 取customerIdNumber: call auditRepository.getCustomerIdNumberByApplicationId()
    - call auditEventRepository.addEvent寫資料到Table: audit_event做紀錄
    - 取attachmentKey: StorageKeyUtil.generateAttachmentKey()
:::

## addTaskAttachment (POST) (api/tasks/{taskId}/attachments)
> call taskService.addAttachment
:::success
- 取attachmentId
    - call camundaClient.getRoomId()
    - call auditEventRepository.addEvent寫資料到Table: audit_event做紀錄
    - call storageService.addTaskAttachment新增檔案
        - attachmentId: storageRepository.createTaskAttachment()
        - key: StorageKeyUtil.generateAttachmentKey()
        - employeeNumber: auditRepository.getUserEmployeeNumber()
        - applicationId: auditRepository.getApplicationIdByRoomId()
        - customerIdNumber: auditRepository.getCustomerIdNumberByApplicationId()
        - call auditEventRepository.addEvent寫資料到Table: audit_event做紀錄
- 取attachmentUri
    - attachmentUri=linkTo(methodOn(TaskController.class).getTaskAttachment(taskId, attachmentId, null)).toUri():question::question:
:::

## deleteTaskAttachment (DELETE) (api/tasks/{taskId}/attachments/{attachmentId})
> call taskService.deleteAttachement
:::success
- call camundaClient.getRoomId()
- call taskRepository.canDeleteAttachment查看是否有這筆資料
- call storageService.deleteTaskAttachment()刪除檔案
- call auditEventRepository.addEvent寫資料到Table: audit_event做紀錄
:::

## getKycManualMakerTask (GET) (api/tasks/{taskId}/kyc-manual-maker)
> call taskService.getKycManualMakerTask
:::success
- call camundaClient.getTaskVariables()
- 檢查是否有permission
- call camundaClient.getKycManualMakerTask 取<font color=#0000FF><b>kycManualMakerTask(Object)</b></font>
@GetMapping("api/bo-user/tasks/{taskId}/kyc-manual-maker")
    - call boUserService.getKycManualMakerTask()
        - checkAssignedTask()
        - 取customerId: call taskService.getVariable()
        - return camundaRepository.getKycManualMakerTask() Table:customer,application
- customerController = methodOn(CustomerController.class);
- 整理kycManualMakerTask並return
:::

## kycManualMakerApprove (POST) (api/tasks/{taskId}/kyc-manual-maker/approve )
> call taskService.kycManualMakerComplete
:::success
- 取applicationId: taskRepository.getApplicationIdByTaskId() Table: act_ru_task
- call camundaClient.kycManualMakerComplete
@PostMapping("api/bo-user/tasks/{taskId}/kyc-manual-maker/complete")
    - call boUserService.kycManualMakerComplete()
    - approve or reject是依據變數decision所決定
- call auditEventRepository.addEvent寫資料到Table: audit_event做紀錄
:::
## kycManualMakerReject (POST) (api/tasks/{taskId}/kyc-manual-maker/reject)
> call taskService.kycManualMakerComplete
:::success
- 取applicationId: taskRepository.getApplicationIdByTaskId() Table: act_ru_task
- call camundaClient.kycManualMakerComplete
@PostMapping("api/bo-user/tasks/{taskId}/kyc-manual-maker/complete")
    - call boUserService.kycManualMakerComplete()
    - approve or reject是依據變數decision所決定
- call auditEventRepository.addEvent寫資料到Table: audit_event做紀錄
:::

## getKycManualCheckerTask (GET) (api/tasks/{taskId}/kyc-manual-checker)
> call taskService.getKycManualCheckerTask
:::success
- 取customerId: call camundaClient.getTaskVariables()
- call camundaClient.getKycManualCheckerTask
@GetMapping("api/bo-user/tasks/{taskId}/kyc-manual-checker")
    - 取<font color=#0000FF><b>kycManualMakerTask(Object)</b></font>: call camundaRepository.getKycManualCheckerTask()
- 取customerController: methodOn(CustomerController.class) 
:::

## kycManualCheckerApprove (POST) ({taskId}/kyc-manual-checker/approve)
> call taskService.kycManualCheckerComplete
:::success
- 取applicationId: taskRepository.getApplicationIdByTaskId() Table: act_ru_task
- call camundaClient.kycManualCheckerComplete
@PostMapping("api/bo-user/tasks/{taskId}/kyc-manual-checker/complete")
    - call boUserService.kycManualCheckerComplete()
    - approve or reject是依據變數decision所決定
- call auditEventRepository.addEvent寫資料到Table: audit_event做紀錄
:::

## kycManualCheckerReject (POST) ({taskId}/kyc-manual-checker/reject)
> call taskService.kycManualCheckerComplete
:::success
- 取applicationId: taskRepository.getApplicationIdByTaskId() Table: act_ru_task
- call camundaClient.kycManualCheckerComplete
@PostMapping("api/bo-user/tasks/{taskId}/kyc-manual-checker/complete")
    - call boUserService.kycManualCheckerComplete()
    - approve or reject是依據變數decision所決定
- call auditEventRepository.addEvent寫資料到Table: audit_event做紀錄
:::

## kycManualCheckerBack (POST) ({taskId}/kyc-manual-checker/back)
> call taskService.kycManualCheckerBack
:::success
- 取applicationId: taskRepository.getApplicationIdByTaskId() Table: act_ru_task
- call camundaClient.kycManualCheckerBack()
@PostMapping("api/bo-user/tasks/{taskId}/kyc-manual-checker/back")
    - call boUserService.backToPreviousTask()
- call auditEventRepository.addEvent寫資料到Table: audit_event做紀錄
:::

## updateCustomerInfo (PUT) ({taskId}/customer-info)
> call taskService.updateCustomerInfo
:::success
- call camundaClient.updateCustomerInfo() 
@PostMapping("api/bo-user/tasks/{taskId}/customer-info")
    - call boUserService.updateCustomerInfo()
    - 取各種User Info
    - 最終call camundaRepository.updateCustomerInfo() Table: customer更新資料
:::

## getCreditDataManualMakerTask (GET) ({taskId}/credit-data-manual-maker)
> call taskService.getCreditDataManualMakerTask
:::success
- 檢查是否有permission
- call camundaClient.getCreditDataManualMakerTask()
@GetMapping("api/bo-user/tasks/{taskId}/credit-data-manual-maker")
    - call boUserService.getCreditDataManualMakerTask()
    - call camundaRepository.getCreditDataManualMakerTask() Table: customer,application
:::

## creditDataManualMakerApprove (POST) ({taskId}/credit-data-manual-maker/approve)
> call taskService.creditDataManualMakerComplete
:::success
- 取applicationId: taskRepository.getApplicationIdByTaskId() Table: act_ru_task
- call camundaClient.completeCreditDataManualMakerTask
@PostMapping("api/bo-user/tasks/{taskId}/credit-data-manual-maker/complete")
    - call boUserService.creditDataManualMakerComplete()
    - approve or reject是依據變數decision所決定
- call auditEventRepository.addEvent寫資料到Table: audit_event做紀錄
:::

## creditDataManualMakerReject (POST) ({taskId}/credit-data-manual-maker/reject)
> call taskService.creditDataManualMakerComplete
:::success
- 取applicationId: taskRepository.getApplicationIdByTaskId() Table: act_ru_task
- call camundaClient.completeCreditDataManualMakerTask()
@PostMapping("api/bo-user/tasks/{taskId}/credit-data-manual-maker/complete")
    - call boUserService.creditDataManualMakerComplete()
    - approve or reject是依據變數decision所決定
- call auditEventRepository.addEvent寫資料到Table: audit_event做紀錄
:::

## creditDataManualMakerTaskRequestFptData (POST) ({taskId}/credit-data-manual-maker/fpt)
> call taskService.creditDataManualMakerTaskRequestFptData
:::success
- call camundaClient.creditDataManualMakerTaskRequestFptData
@PostMapping("api/bo-user/tasks/{taskId}/credit-data-manual-maker/fpt")
    - call boUserService.creditDataManualMakerComplete()
:::

## creditDataManualMakerTaskRequestCicData (POST) ({taskId}/credit-data-manual-maker/cic)
> call taskService.creditDataManualMakerTaskRequestCicData
:::success
- call camundaClient.creditDataManualMakerTaskRequestCicData
@PostMapping("api/bo-user/tasks/{taskId}/credit-data-manual-maker/cic")
    - call boUserService.creditDataManualMakerTaskRequestCicData()
:::

## creditDataManualMakerTaskRequestTelcoData (POST) ({taskId}/credit-data-manual-maker/telco)
> call taskService.creditDataManualMakerTaskRequestTelcoData
:::success
- call camundaClient.creditDataManualMakerTaskRequestTelcoData
@PostMapping("api/bo-user/tasks/{taskId}/credit-data-manual-maker/telco")
    - call boUserService.creditDataManualMakerTaskRequestTelcoData()
:::

## getCreditDataManualCheckerTask (GET) ({taskId}/credit-data-manual-checker)
> call taskService.getCreditDataManualCheckerTask
:::success
- call camundaClient.getCreditDataManualCheckerTask
@GetMapping("api/bo-user/tasks/{taskId}/credit-data-manual-checker")
    - call boUserService.getCreditDataManualCheckerTask()
    - call camundaRepository.getCreditDataManualCheckerTask 取<font color=#0000FF><b>CreditDataManualCheckerTask(Object)</b></font> Table:customer,application
:::

## creditDataManualCheckerApprove (POST) ({taskId}/credit-data-manual-checker/approve)
> call taskService.creditDataManualCheckerComplete
:::success
- 取applicationId: taskRepository.getApplicationIdByTaskId() Table: act_ru_task
- call camundaClient.completeCreditDataManualCheckerTask
@PostMapping("api/bo-user/tasks/{taskId}/credit-data-manual-checker/complete")
    - call boUserService.creditDataManualCheckerComplete()
    - approve or reject是依據變數decision所決定
- call auditEventRepository.addEvent寫資料到Table: audit_event做紀錄
:::

## creditDataManualCheckerReject (POST) ({taskId}/credit-data-manual-checker/reject)
> call taskService.creditDataManualCheckerComplete
:::success
- 取applicationId: taskRepository.getApplicationIdByTaskId() Table: act_ru_task
- call camundaClient.completeCreditDataManualCheckerTask
@PostMapping("api/bo-user/tasks/{taskId}/credit-data-manual-checker/complete")
    - call boUserService.creditDataManualCheckerComplete()
    - approve or reject是依據變數decision所決定
- call auditEventRepository.addEvent寫資料到Table: audit_event做紀錄
:::

## creditDataManualCheckerBack (POST) ({taskId}/credit-data-manual-checker/back)
call taskService.creditDataManualCheckerBack
- 取applicationId: taskRepository.getApplicationIdByTaskId() Table: act_ru_task
- call camundaClient.creditDataManualCheckerBack
@PostMapping("api/bo-user/tasks/{taskId}/credit-data-manual-checker/back")
    - call boUserService.backToPreviousTask()
- call auditEventRepository.addEvent寫資料到Table: audit_event做紀錄

## getUnderwritingMakerTask (GET) ({taskId}/underwriting-maker)
> call taskService.getUnderwritingMakerTask
:::success
- - 檢查是否有permission
- call camundaClient.getUnderwritingMakerTask
@PostMapping("api/bo-user/tasks/{taskId}/credit-data-manual-checker/back")
    - call boUserService.getUnderwritingMakerTask()
    - call camundaRepository.getUnderwritingMakerTask()取<font color=#0000FF><b>UnderwritingMakerTask(Object)</b></font> Table:customer,application,customer_group
:::

## underwritingMakerApprove (POST) ({taskId}/underwriting-maker/approve)
> call taskService.underwritingMakerComplete
:::success
- 取applicationId: taskRepository.getApplicationIdByTaskId() Table: act_ru_task
- call camundaClient.underwritingMakerComplete
@PostMapping("api/bo-user/tasks/{taskId}/underwriting-maker/complete")
    - call boUserService.underwritingMakerComplete()
    - approve or reject是依據變數decision所決定
- call auditEventRepository.addEvent寫資料到Table: audit_event做紀錄
:::

## underwritingMakerReject (POST) ({taskId}/underwriting-maker/reject")
> call taskService.underwritingMakerComplete
:::success
- 取applicationId: taskRepository.getApplicationIdByTaskId() Table: act_ru_task
- call camundaClient.underwritingMakerComplete
@PostMapping("api/bo-user/tasks/{taskId}/underwriting-maker/complete")
    - call boUserService.underwritingMakerComplete()
    - approve or reject是依據變數decision所決定
- call auditEventRepository.addEvent寫資料到Table: audit_event做紀錄
:::

## getUnderwritingCheckerTask (GET) ({taskId}/underwriting-checker)
> call taskService.getUnderwritingCheckerTask
:::success
- call camundaClient.getUnderwritingCheckerTask
@GetMapping("api/bo-user/tasks/{taskId}/underwriting-checker")
    - call boUserService.getUnderwritingCheckerTask()
    - call camundaRepository.getUnderwritingCheckerTask()取<font color=#0000FF><b>UnderwritingCheckerTask(Object)</b></font>  Table:customer,application,customer_group 
:::

## underwritingCheckerApprove (POST) ({taskId}/underwriting-checker/approve)
> call taskService.underwritingCheckerComplete
:::success
- 取applicationId: taskRepository.getApplicationIdByTaskId() Table: act_ru_task
- call camundaClient.underwritingCheckerComplete
@PostMapping("api/bo-user/tasks/{taskId}/underwriting-checker/complete")
    - call boUserService.underwritingCheckerComplete()
    - approve or reject是依據變數decision所決定
- call auditEventRepository.addEvent寫資料到Table: audit_event做紀錄
:::

## underwritingCheckerReject (POST) ({taskId}/underwriting-checker/reject)
> call taskService.underwritingCheckerComplete
:::success
- 取applicationId: taskRepository.getApplicationIdByTaskId() Table: act_ru_task
- call camundaClient.underwritingCheckerComplete
@PostMapping("api/bo-user/tasks/{taskId}/underwriting-checker/complete")
    - call boUserService.underwritingCheckerComplete()
    - approve or reject是依據變數decision所決定
- call auditEventRepository.addEvent寫資料到Table: audit_event做紀錄
:::

## underwritingCheckerBack (POST) ({taskId}/underwriting-checker/back)
> call taskService.underwritingCheckerBack
:::success
- 取applicationId: taskRepository.getApplicationIdByTaskId() Table: act_ru_task
- call camundaClient.completeCreditDataManualMakerTask
@PostMapping("api/bo-user/tasks/{taskId}/credit-data-manual-maker/complete")
    - call boUserService.backToPreviousTask()
- call auditEventRepository.addEvent寫資料到Table: audit_event做紀錄
:::





