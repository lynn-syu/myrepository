
[toc]

# EventController
## getEvents (GET) (api/events)
> call AuditEventService.findAll
:::success
1.呼叫 AuditEventRepository SQL
- findAll: 找 audit_event, bo_user, customer

2.依照查詢條件分頁回傳前端
:::

## getEvent (GET) (api/events/{id})
> call AuditEventService.getById
:::success
1.呼叫 AuditEventRepository SQL
- findById: 找 audit_event, bo_user, customer
:information_source: 若找不到 回傳NOT_FOUND

2.依照查詢條件回傳前端
:::