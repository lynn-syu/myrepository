- [x] 是不是從app一註冊就要申請借錢額度(application)??? 要不要使用錢是另一回事
Ans: 註冊APP只需要電話跟email,如果要借錢，則是註冊完後再申請。
要不要使用錢是另一回事=>若貸款申請過確實是給額度而已，User可以依照自己的需要提取錢，而不用一次領取全部貸款
- [x] 所以一個人就是一次註冊機會??? 如何啟動第二筆貸款申請?? 
Ans: 不需要這樣考慮，申請能借多少就是多少
- [x] 請說明 annotation使用方式 controller method 帶有 @Schema是甚麼?
```java=
public CustomerTask getProcessCurrentTaskV1(
            @PathVariable @Schema(
                    required = true,
                    allowableValues = {
                            ProcessName.REGISTRATION,
                    }) String processName,
            @RequestParam String cuid) {}
```

- [ ] 請說明APP呼叫此method (getApplicationAgreementAppTaskV1) 是拿到了什麼結果?
    - StorageKeyUtil.generateApplicationAgreementKey() 用法??

- [ ] appStorgage是甚麼東西???

- [ ] 請說明APP呼叫此method (getCreditLineV1) 回覆的結果為什麼是這樣?
**目前應該是沒有用，可以在跟通路確認**
```java=
// 為什麼其他欄位都是100000????
return CreditLine.builder()
            .bnplCreditLineAvailable(new BigDecimal("100000"))
            .cashLoanCreditLineAvailable(new BigDecimal("100000"))
            .outstanding(new BigDecimal("100000"))
            .totalCreditLimit(totalCreditLimit)
            .build();
```