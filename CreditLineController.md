[toc]

# CreditLineController
## getCreditLineV1 (GET) (api/v1/credit-line)
> call CreditLineService.getCreditLine
:::success
1.call AuthService.getCustomerIdByCuid: 將前端的cuid轉換為customerId
- 呼叫 CustomerRepository SQL
    - findIdByCuid: 找 customer
    - 找不到則回傳 NOT_FOUND

2.呼叫 CreditLineRepository SQL
- getTotalCreditLimit: 找customer, application 取得 total_credit_limit

3.組合以上查詢資料回覆前端
```java=
return CreditLine.builder()
            .bnplCreditLineAvailable(new BigDecimal("100000"))
            .cashLoanCreditLineAvailable(new BigDecimal("100000"))
            .outstanding(new BigDecimal("100000"))
            .totalCreditLimit(totalCreditLimit)
            .build();
```
==為什麼其他欄位都是100000????==
Ans: ==與通路討論== :目前沒有使用這支API
:::
