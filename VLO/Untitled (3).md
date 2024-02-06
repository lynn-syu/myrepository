
### postApiDatacBVngtcufeq001 (POST) (/api/DATAC-B-VNGTCUFEQ001)
> 目的: get-customer-freezed-event

> FreezeEventService.getCustomerFreezedEvent()
:::success
- 查找 CIF table: 透過req.phone, req.personalId
:warning: 找不到 BadRequestException("DATAC-B-VNGTCUFEQ001-1", "member-not-exist")

- 查找 freeze_list table: 透過 CIF entity.IntellectCif + action ='1' freezed，取得 event 欄位結果 
會找到依照event&cif_no group起來的 多筆資料 因為有不同event
![](https://hackmd.io/_uploads/ByNL0HU5h.png)
再根據cif_no去限縮
![](https://hackmd.io/_uploads/rkk3CHLc3.png)
最後map event欄位 得到 List<Integer>
![](https://hackmd.io/_uploads/rkswCBL52.png)

:question: event 的欄位代表意義??
:question: 這如果沒有找到資料 就會回傳null的 response???
![](https://hackmd.io/_uploads/SyUFYHI93.png)

- return ResponseGetCustomerFreezedEventDTO (有 MWHEADER 包含 TRACEID + TRANRS 看起來沒有set phone, personalId, 且 event可能為null...:question::question:)
![](https://hackmd.io/_uploads/BJ1lTAV9n.png)
:::

### postApiDatacBVngtcufeq002 (POST) (/api/DATAC-B-VNGTCUFEQ002)
> 目的: get-customer-freezed-event

> FreezeEventService.getCustomerFreezedEvent002()
:::success
- 查找 CIF table: 透過req.Cuid
:warning: 找不到 BadRequestException("DATAC-B-VNGTCUFEQ002-1", "member-not-exist")

- 查找 freeze_list table: 透過 CIF entity.IntellectCif + action ='1' freezed，取得 event 欄位結果 
:question: 這如果沒有找到資料 就會回傳null的 response??? 同上問題

- return ResponseGetCustomerFreezedEventWithCuidDTO (有 MWHEADER 包含 TRACEID + TRANRS ~= event)
:::

### postApiDatacBVngtfrelq001 (POST) (/api/DATAC-B-VNGTFRELQ001)
> 目的: get freezed customer list

> FreezeEventService.getFreezedList()
:::success
- 查找 freeze_list table: 透過 req.Event 回傳 IntellectCif 清單
:bulb: event 0: pcb review, 1: r18 review, 2: late payment

- 查找 CIF table: 透過 IntellectCif 清單，找到客戶清單，再for loop轉換資料(phone, personalId)

- return ResponseQueryFreezeListDTO (有 MWHEADER 包含 TRACEID + TRANRS ~= 客戶清單)
:::

### postApidatacBVntrcmfem001 (POST) (/api/DATAC-B-VNTRCMFEM001)
> 目的: trigger customer to freeze or unfreeze

> FreezeEventService.triggerCustomerFreezeEvent()
:::success
- 將req給的資料，新增一筆 FreezeList entity
:bulb: system 0: HES, 1: Intellect, 2: Aml
:bulb: event 0: pcb review, 1: r18 review, 2: late payment
- 根據 req.action 判斷要進行 1:freeze or 0:unfreeze
:question: 為什麼進行unfreeze (line.221)的時候有先查table，那freeze就沒有?
![](https://hackmd.io/_uploads/SyiPe8Iqh.png)

    :bookmark: CIF-intellectCIF欄位 (該客戶的==貸款還款帳號==) == 和 intellect 溝通的 customerNumber
    
    - freeze: 
        - 呼叫 intellect-limit-inquiry 取得 LimitInformation
        :warning: 若intellect response == null 或 LimitInformation == null 則 throw new InternalServerErrorException("500", "intellect body is null")
        - 若 ==LimitInformation.FreezeState == N 呼叫 intellect 進行 freezeLimit==
        :warning: 若intellect-limitsResp == null 或 limitsResp.ResponseCode == null 就==直接結束程式!!!!==
        - :warning: 以上過程有exception 則 throw new InternalServerErrorException("500", "internal-server-error", e)
        - 根據 limitsResp.ResponseCode == 0 則表示成功，會進行 ==sendFreezeMessage==
            - 透過 msg-center 非同步寄發簡訊 FREEZE_SMS_TEMPLATE
            - 透過 msg-center 非同步推播通知 FREEZE_APP_PUSH_TEMPLATE
    
    - unfreeze: 
        - 透過呼叫 intellect-limit-inquiry 取得 LimitInformation
        :warning: 若intellect response == null 或 LimitInformation == null 則 throw new InternalServerErrorException("500", "intellect body is null")
        - 若 ==LimitInformation.FreezeState == Y 呼叫 intellect 進行 releaseFreezeLimit==
        :warning: 若intellect-limitsResp == null 或 limitsResp.ResponseCode == null 就==直接結束程式!!!!==
        - :warning: 以上過程有exception 則 throw new InternalServerErrorException("500", "internal-server-error", e)
        - 根據 limitsResp.ResponseCode == 0 則表示成功，會進行 ==sendUnFreezeMessage==
            - 透過 msg-center 非同步寄發簡訊 UNFREEZE_SMS_TEMPLATE
            - 透過 msg-center 非同步推播通知 UNFREEZE_APP_PUSH_TEMPLATE
        
- return ResponseTriggerCustomerFreezeEventDTO (僅有 MWHEADER 包含 TRACEID )
:::