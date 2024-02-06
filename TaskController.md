1.是不是一註冊就要申請借錢額度(application)?要不要使用錢是另一回事
2.所以一個人就是一次註冊機會 

[toc]

# TaskController 
## getProcessCurrentTaskV1 (GET) (api/v1/processes/{processName}/current-task)
:bulb: 參數有帶以下,想請教@Schema是甚麼?
```java=
public CustomerTask getProcessCurrentTaskV1(
            @PathVariable @Schema(
                    required = true,
                    allowableValues = {
                            ProcessName.REGISTRATION,
                    }) String processName,
            @RequestParam String cuid) {}
```
> call getCustomerTask()
:::success
- get customerId: call authService.getCustomerIdByCuid()
    - 利用傳入cuid找Table:customer找id
    - 找不到會丟ThrowableProblem(runtimeException)
- 如果是註冊的動作則call camundaClient.getRegistrationProcessCurrentTask()
@GetMapping("api/customer/processes/registration/current-task")
    - 專案CustomerProcessController實作getRegistrationProcessCurrentTask
    - call customerService.getCurrentRegistrationTask()
- 如果不是註冊的動作: default -> throw processKeyNotFound()
:::

## getApplicationAgreementAppTaskV1 (GET) (api/v1/processes/registration/{taskId}/application-agreement-app)
> call taskService.getApplicationAgreementAppTask()
::: success
- get customerId
- call checkTaskAssigned()檢查如果此id沒task則拋錯
- get applicationId:  Table:customer 取(column)current_application_id
- get agreementKey:call StorageKeyUtil.generateApplicationAgreementKey() :bulb:這是甚麼:question:
- 組資料回傳ApplicationAgreementAppTask(Object)
:::

## applicationAgreementAppAgreeTaskV1 (POST) (v1/processes/registration/{taskId}/application-agreement-app/agree)
> call taskService.applicationAgreementAppAgreeTask()
:::success
- get customerId
- call camundaClient.applicationAgreementAppAgreeTask()
@PostMapping("api/customer/tasks/{taskId}/application-agreement-sign")
    - call customerService.applicationAgreementAppAgreeTask()
    - 取各種資料並做CRUD
        - appStorgage是甚麼東西?:question: 
    - 最後call taskService.complete完成此Task
:::

## applicationAgreementAppDeclineTaskV1 (POST) (v1/processes/registration/{taskId}/application-agreement-app/decline)
call taskService.applicationAgreementAppDeclineTask()
:::success
- get customerId
- call camundaClient.applicationAgreementAppDeclineTask()
@PostMapping("api/customer/tasks/{taskId}/application-agreement-decline")
    - call customerService.applicationAgreementAppDeclineTask()
    - 檢查要取消的這筆Task是否存在
    - 最後call taskService.complete參數帶REJECT完成取消
:::







