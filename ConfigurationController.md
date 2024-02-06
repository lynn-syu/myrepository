[toc]

# ConfigurationController
## getConfiguration (GET) (api/configuration)
> call ConfigurationService.getConfiguration
:::success
1.呼叫 ConfigurationRepository SQL
- getConfiguration: 找 configuration

2.取出當前的設定資料回傳前端
:::


## updateConfiguration (PUT) (api/configuration)
> call ConfigurationService.updateConfiguration
:::success
1.呼叫 ConfigurationRepository SQL
- getConfiguration: 找 configuration

2.取出當前的設定資料 紀錄於 ConfigurationUpdatedEventDetails

3.填入新的設定值 呼叫 ConfigurationRepository SQL
- updateConfiguration: 更新 configuration

4.call AuditEventService.addConfigurationUpdatedEvent
- 呼叫 AuditEventRepository SQL
     - addEvent: 新增一筆 audit_event
:::