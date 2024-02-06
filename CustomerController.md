
[toc]

# CustomerController
## changeEmailV1 (PATCH) (api/v1/customers/change-email)
> call CustomerService.changeEmail
:::success
:bulb: PUT用於更新整筆資料；PATCH用於更新部分資料 

1.call AuthService.getCustomerIdByCuid: 將前端的cuid轉換為customerId
- 呼叫 CustomerRepository SQL
    - findIdByCuid: 找 customer
    - 找不到則回傳 NOT_FOUND

2.呼叫 CustomerRepository SQL
- changeEmail: 更新該位customer的email資料
:::

## deleteCustomerData (DELETE) (api/customers/delete-customer/{phone})
> :no_entry_sign: 被標記為deprecated
> call CustomerService.deleteCustomerDataByPhone
:::success
1.call AuthService.getCustomerIdByPhone
- 呼叫 CustomerRepository SQL
    - findIdByPhone: 找 customer
    - 找不到則回傳 NOT_FOUND

2.呼叫 ApplicationRepository SQL
- findApplicationIdsByCustomerId: 找 application

3.呼叫 CustomerRepository SQL
- deleteAuditEventByApplicationIds: 刪除audit_event中，對應applications的紀錄 ==真的刪除==
- deleteCustomerById:  刪除customer ==真的刪除==
- deleteApplicationByCustomerId:  刪除該客戶所有的 application ==真的刪除==
:::

## deleteCustomerDataV1 (DELETE) (api/v1/customers/delete-customer/{cuid})
> :no_entry_sign: 被標記為deprecated
> call CustomerService.deleteCustomerDatadeleteCustomerDatadeleteCustomerData
:::success
1.call AuthService.getCustomerIdByCuid: 將前端的cuid轉換為customerId
- 呼叫 CustomerRepository SQL
    - findIdByCuid: 找 customer
    - 找不到則回傳 NOT_FOUND

2.呼叫 ApplicationRepository SQL
- findApplicationIdsByCustomerId: 找 application

3.呼叫 CustomerRepository SQL
- deleteAuditEventByApplicationIds: 刪除audit_event中，對應applications的紀錄 ==真的刪除==
- deleteCustomerById:  刪除customer ==真的刪除==
- deleteApplicationByCustomerId:  刪除該客戶所有的 application ==真的刪除==
:::