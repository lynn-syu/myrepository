###### tags: `backoffice-api 學習筆記`

>請說明 getNationalIdFrontSide, getNationalIdBackSide 使用情境
```java=
 // 這是什麼用法?
 @Parameter(example = "ey..iJ",
            name = "access_token",
            schema = @Schema(type = "string"),
            in = ParameterIn.QUERY,
            description = "Only jwt token without Bearer")
```

[toc]

# CustomerController
## getCustomers (GET) (api/customers)
> call CustomerService.findAll
:::success
1.呼叫 CustomerRepository SQL
- findAll: 找 customer, application
- 
2.依照查詢條件分頁回傳前端
:::

## getCustomer (GET) (api/customers/{id})
> call CustomerService.getById
:::success
1.呼叫 CustomerRepository SQL
- findById: 找 customer, application
:information_source: 找不到回傳 NOT_FOUND

2.依照查詢條件回傳前端
:::

## updateCustomerContacts (PUT) (api/customers/{id}/contacts)
> call CustomerService.updateContacts
:::success
1.呼叫 CustomerRepository SQL
- canUpdateCustomer: 找 customer, application
:information_source: 找不到回傳 CONFLICT

2.呼叫 CustomerRepository SQL
- updateContacts: 更新 customer
:::

## getBankAccounts (GET) (api/customers/{id}/bank-accounts)
> call CustomerService.getBankAccounts
:::success
1.呼叫 CustomerRepository SQL
- findAllBankAccountsByCustomerId: 找 bank_account, bank

2.依照查詢條件分頁回傳前端
:::

## createBankAccount (POST) (api/customers/{id}/bank-accounts)
> call CustomerService.createBankAccount
:::success
1.呼叫 CustomerRepository SQL
- findBankAccountId: 找 bank_account
:information_source: 如果customerId不存在該筆資料則往下執行

2.呼叫 CustomerRepository SQL
- createBankAccount: 新增 bank_account
 
3.回傳前端bank_account ==return new Created的用法???==
:::

## updateBankAccount (PUT) (api/customers/{id}/bank-accounts/{bankAccountId})
> call CustomerService.updateBankAccount
:::success
1.呼叫 CustomerRepository SQL
- updateBankAccount: 更新 bank_account
:::

## updatePhone (PUT) (api/customers/{id}/phone)
> call CustomerService.updatePhone
:::success
1.呼叫 CustomerRepository SQL
- findById: 找 customer
:information_source: 找不到回傳 NOT_FOUND

2.呼叫 CustomerRepository SQL
- isApplicationInStatus: 找 customer, application
:information_source: 申請狀態不符回傳 CONFLICT

3.呼叫 CustomerRepository SQL
- updatePhoneReturningKeycloakId: 更新 customer 回傳 keycloak_id <-> CustomerRealm ==這是如何串接的??? 如何使用裡面的內容 toRepresentation ????==
:information_source: 找不到無法更新回傳 NOT_FOUND

4.呼叫 AuditRepository SQL
- getCustomerIdNumber: 更新
- getUserEmployeeNumber:

5.更新 CustomerRealm.update

6.call AuditEventService.addCustomerPhoneChangedEvent
- 呼叫 AuditEventRepository SQL
    - addEvent: 新增一筆修改電話號碼的紀錄
    ==不是不開放修改電話號碼?????==
:::

## getCustomerApplications (GET) (api/customers/{id}/applications)
> call CustomerService.getApplications
:::success
1.呼叫 CustomerRepository SQL
- findAllApplicationsByCustomerId: 找 application

2.依照查詢條件分頁回傳前端
:::

## updateStatus (PUT) (api/customers/{id}/status)
> call CustomerService.updateStatus
:::success
1.呼叫 CustomerRepository SQL
- canChangeStatus: 找 customer
:information_source: 不符合 ACTIVE', 'FROZEN' 無法更新回傳 CONFLICT

2.呼叫 AuditRepository SQL
- getCustomerIdNumber: 找 customer
- getUserEmployeeNumber: 找 bo_user
- getCustomerStatus: 找 customer

3.呼叫 CustomerRepository SQL
- updateStatus: 更新 customer

4.call AuditEventService.addCustomerStatusChangedEvent
- 呼叫 AuditEventRepository SQL
    - addEvent: 新增一筆修改客戶狀態的紀錄
:::

## getNationalIdFrontSide (GET) (api/customers/{id}/national-id-front-side)
> call StorageService.getNationalIdFrontSide
:::success
1.呼叫 StorageRepository SQL
- getNationalIdFrontSideContentType: 找 customer
==會透過Storage寫到哪裡????? GcpStorage? LocalStorage? LoggingStorage?==
:information_source: 找不到回傳 NOT_FOUND

2.依照查詢條件回傳
:::

## getNationalIdBackSide (GET) (api/customers/{id}/national-id-back-side)
> call StorageService.getNationalIdBackSide
:::success
1.呼叫 StorageRepository SQL
- getNationalIdBackSideContentType: 找 customer
==會透過Storage寫到哪裡????? GcpStorage? LocalStorage? LoggingStorage?==
:information_source: 找不到回傳 NOT_FOUND

2.依照查詢條件回傳
:::

## getSelfie (GET) (api/customers/{id}/selfie)
> call StorageService.getSelfie
:::success
1.呼叫 StorageRepository SQL
- getSelfieContentType: 找 customer
==會透過Storage寫到哪裡????? GcpStorage? LocalStorage? LoggingStorage?==
:information_source: 找不到回傳 NOT_FOUND

2.依照查詢條件回傳
:::