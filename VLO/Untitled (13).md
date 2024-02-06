[toc]

:question: 請問 add-m001是不是舊版的 (使用 customerUuid, personalId, phone)? add-m002直接使用 cuid, customerUuid

:question: 請問 qucuapd-q001是不是舊版的 (使用 phone)? qucuapd-q002直接使用 cuid

:question: 請問 crcrd-q001是不是舊版的 (使用 phone)? crcrd-q002直接使用 cuid

### postApiDatacBVnaappdtm001 (POST) (/api/DATAC-B-VNAAPPDTM001)
> 目的: add-application-data 呼叫方-HES

:question: 文件決定的流程和程式的順序不同
![](https://hackmd.io/_uploads/SkWp_mI9n.png)

> ApplicationInfoService.addApplicationData()
:::success
- 查找 CIF table: 透過 req.phone 找到客戶資料
:warning: 找不到 entity 則 BadRequestException("DATAC-B-VNAAPPDTM001-1",MessageSourceUtils.getMessage("member-not-exist"))

- 查找 contract_information table: 透過 req.applicationId
:warning: 找有資料 則 BadRequestException("DATAC-B-VNAAPPDTM001-2",MessageSourceUtils.getMessage("application-exist"))
:question: 為什麼要這個動作???? (Hank猜測因為這一隻API是ADD，所以id如果存在表示重複)

- 將req的資料，更新到上述查得的 CIF entity
:question: 這邊將exception列為M002-1 是因為M001沒有使用了?
```java=
// req提供的狀態只允許 3 or 4 其餘都會 BadRequestException("DATAC-B-VNAAPPDTM002-1", MessageSourceUtils.getMessage("request-status-not-eligibility"))

// CIFStatusEnum 0: new, 1: process, 2: approve, 3: agree, 4: reject, 5: cancel
private CIFStatusEnum convertCIFStatusEnum(ApplicationStatusEnum cifStatusEnum) {
    return switch (cifStatusEnum) {
        case NUMBER_3 -> CIFStatusEnum.NUMBER_2;
        case NUMBER_4 -> CIFStatusEnum.NUMBER_4;
        default -> throw new BadRequestException("DATAC-B-VNAAPPDTM002-1",
                MessageSourceUtils.getMessage("request-status-not-eligibility"));
        };
}
```

- 查找 contract_information table: 透過 req.customerUuid 查得合約清單，若有資料則把合約狀態更新為 4:reject 

- 正式拿取req提供的資料，新增一筆 contract_information 

- return ResponseAddApplicationInfoDTO (僅有 MWHEADER 包含 TRACEID )
:::

### postApiDatacBVnaappdtm002 (POST) (/api/DATAC-B-VNAAPPDTM002)
> 目的: add-application-data

> ApplicationInfoService.addApplicationData002()
:::success
- 查找 CIF table: 透過 req.cuid 找到客戶資料
:warning: 找不到 entity 則 BadRequestException("DATAC-B-VNAAPPDTM002-2", MessageSourceUtils.getMessage("member-not-exist"))

- 查找 contract_information table: 透過 req.applicationId
:warning: 找有資料 則 BadRequestException("DATAC-B-VNAAPPDTM002-3", MessageSourceUtils.getMessage("application-exist"))

- 將req的資料，更新到上述查得的 CIF entity

- 查找 contract_information table: 透過 req.customerUuid 查得合約清單，若有資料則把合約狀態更新為 4:reject 

- 正式拿取req提供的資料，新增一筆 contract_information 

- return ResponseAddApplicationDataDTO (僅有 MWHEADER 包含 TRACEID )
:::

### postApiDatacBVnquapdtq001 (POST) (/api/DATAC-B-VNQUAPDTQ001)
> 目的: query-application-data

> ApplicationInfoService.queryApplicationData
:::success
- 查找 contract_information table: 透過 req.applicationId 查得合約entity
:warning: 找不到 entity 則 BadRequestException("DATAC-B-VNQUAPDTQ001-1", MessageSourceUtils.getMessage("application-not-exist"))

- 查找 CIF table: 透過 合約entity.cuid 找到客戶資料
:warning: 找不到 entity 則 InternalServerErrorException("DATAC-B-VNQUAPDTQ001-2", MessageSourceUtils.getMessage("member-not-exist"))
:question: 這裡怎麼突然變成 InternalServerErrorException

- return 透過以上的兩個資料組合回傳 (有完整的信用額度==表示有一隻HES app-api是廢物==)
![](https://hackmd.io/_uploads/H1JocZH9n.png)
:::

### postApiDatacBVnqucadtq001 (POST) (/api/DATAC-B-VNQUCADTQ001)
> 目的: query-customer-application-data 查詢客戶現在的application狀態

> ApplicationInfoService.queryCustomerApplicationData()
:::success
- 查找 contract_information table: 透過 req.phone+ req.status 查得合約清單
:warning: 找不到則 BadRequestException("DATAC-B-VNQUCADTQ001-1", MessageSourceUtils.getMessage("member-not-exist"))
:question: corpus code是不是定義的怪怪的????
![](https://hackmd.io/_uploads/ByzuTZrc2.png)


:heavy_exclamation_mark: 這裡呼叫了兩次查找table....
- return 將合約清單做資料轉換回傳 
:::

### postApiDatacBVnqucadtq002 (POST) (/api/DATAC-B-VNQUCADTQ002)
> 目的: query-customer-application-data 查詢客戶現在的application狀態

> ApplicationInfoService.queryCustomerApplicationData002()
:::success
- 查找 contract_information table: 透過 req.cuid+ req.status 查得合約清單
:warning: 找不到則 BadRequestException("DATAC-B-VNQUCADTQ002-1",
MessageSourceUtils.getMessage("application-not-exist"))
![](https://hackmd.io/_uploads/rJE3abHc3.png)

:heavy_exclamation_mark: 這裡呼叫了兩次查找table....
- return 將合約清單做資料轉換回傳 
:::

### postApiDatacBVnupdtadm001 (POST) (/api/DATAC-B-VNUPDTADM001)
> 目的: update-application-data 更新客戶現在的application狀態 
> :heavy_exclamation_mark: 程式端註記為 @deprecated
> :heavy_check_mark: @Transactional(rollbackFor = Exception.class)

> ApplicationInfoService.updateApplicationData()
:::success
- 查找 contract_information table: 透過 req.applicationId 查得合約
:warning: 找不到則 BadRequestException("DATAC-B-VNUPDTADM001-1",
MessageSourceUtils.getMessage("application-not-exist"))

- 將找到的合約 entity 狀態欄位更新為 req.Status 

- return ResponseUpdateApplicationInfoDTO (僅 MWHEADER 包含 TRACEID)
:::

### postApiDatacBVnquapclq001 (POST) (/api/DATAC-B-VNQUAPCLQ001)
> 目的: query-quarterly-application-checklist

> ApplicationInfoService.postApiDatacBVnquapclq001()
:::success
- 查找 QuarterlyCheckList table: 透過 req.CheckDate 並聯集 ContractInformation + Cif 得到清單

- 將清單找出的客戶 for loop 去==呼叫 intellect.fetchCustomerGet== 驗證是否還為active
    - 滿足active 才會加入待回傳的list: 
    ![](https://hackmd.io/_uploads/BkpSyMH53.png)
    :warning: 呼叫intellect過程有任何錯誤 InternalServerErrorException("500", "intellect-handshake-error", e, e.getMessage())

- return ResponseQueryApplicationChecklistDTO
:::

### postApiDatacBVnqcrcrdq001 (POST) (/api/DATAC-B-VNQCRCRDQ001)
> 目的: query-customer-rejectCode-and-reApplyDate

> ApplicationInfoService.queryCustomerRejectCodeAndReApplyDate()
:::success
- 查找 CIF table: 透過 req.phone 找到客戶資料
:warning: 找不到 entity 則 BadRequestException("DATAC-B-VNQCRCRDQ001-1", "member-not-exist"))

- 查找 contract_information table: 透過 req.phone 找到最新的合約 
:warning: 找不到 entity 則 BadRequestException("DATAC-B-VNQCRCRDQ001-2", "application-not-exist"))

- return 組合以上兩個entity資料回傳
:::

### postApiDatacBVnqcrcrdq002 (POST) (/api/DATAC-B-VNQCRCRDQ002)
> 目的: query-customer-rejectCode-and-reApplyDate

> ApplicationInfoService.queryCustomerRejectCodeAndReApplyDate002()
:::success
- 查找 CIF table: 透過 req.cuid 找到客戶資料
:warning: 找不到 entity 則 BadRequestException("DATAC-B-VNQCRCRDQ002-1", "member-not-exist"))

- 查找 contract_information table: 透過 req.cuid 找到最新的合約 
:warning: 找不到 entity 則 BadRequestException("DATAC-B-VNQCRCRDQ002-2", "application-not-exist"))

- return 組合以上兩個entity資料回傳
:::