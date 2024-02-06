## postApiDatacBVnchkrauq001 (POST) (/api/DATAC-B-VNCHKRAUQ001)
> 目的: Check reapply user and send message

> shiftedOffsetService.checkReapplyUser()
:::success
:bulb: 使用了 ExecutorService (ThreadPoolErrors) :question: 要找資料

- 查找table- cif: 當下時間 findByReApplyDateAndIntellectCifNotNullAndLocaleNotNull，得到 IntellectCif List結果
:bookmark: IntellectCIF欄位 (該客戶的==貸款還款帳號==) == 和 intellect 溝通的 customerNumber
```java=
// 是一個具有識別性可以和intellect溝通的資料 
@Parameter(name = "customerNumber", 
    description = "This represents the unique customer number 
           for whom the loan belongs to. This should be a 
           valid customer number.", required = true) 
```

- 將每一位找出的客戶，利用 customerNumber 去intellect拿客戶資料 (這裡為了得到電話號碼和信箱)

- 再分別使用 sms 非同步 + email 非同步 + notification 非同步 發送訊息

- return ResponseCheckReapplyUserDTO (僅 MWHEADER 包含 traceId )
:::

### postApiDatacBVngtrtsoq001 (POST) (/api/DATAC-B-VNGTRTSOQ001)
> 目的: Get Transaction Time Shifted Offset

> shiftedOffsetService.getTimeShiftedOffset()
:::success
- 查找table- SystemParam: 找出 TIME_SHIFTED_OFFSET 這個欄位的值
:warning: 發生錯誤 thrown new InternalServerErrorException("TIME_SHIFTED_OFFSET not set")

- 若找出的值為null，則設定為0，不然就是轉成integer

- return ResponseGetTransactionShiftedDTO (有 MWHEADER 包含 traceId + TRANRS 包含 TIME_SHIFTED_OFFSET )
:::