[toc]

:question: 請問 m001是不是舊版的 (使用 customerUuid, personalId, phone)? m002直接使用 cuid

### postApiDatacBVnuancifm001 (POST) (/api/DATAC-B-VNUANCIFM001)
> 目的: update-application-and-CIF-data

> CifManager.updateApplicationAndCifData()
:::success
- 透過 req.getTRANRQ().getPhone() 去查找CIF table 取得對應的 status 來做impl的判斷
```java=
// 註解的impl不存在對應的 override function，所以會直接使用繼承的 cifServiceImpl

public CifService filterCImpl(String phone) {
    if (TPStringUtils.isNullOrEmpty(phone)) {
        throw new BadRequestException("", MessageSourceUtils.getMessage("member-not-exist"));
    }
    CifEntity cifEntity = cifDAO.findByPhone(phone).orElse(null);
    if (cifEntity == null) {
        return cifServiceImpl;
    }
    // * 0: new, 1: process, 2: approve, 3: agree, 4: reject, 5: cancel
    return switch (cifEntity.getStatus()) {
        // case NUMBER_0 -> cifServiceNewImpl;
        // case NUMBER_1 -> cifServiceProcessImpl;
        case NUMBER_2 -> cifServiceApproveImpl;
        // case NUMBER_3 -> cifServiceAgreeImpl;
        // case NUMBER_4 -> cifServiceRejectImpl;
        // case NUMBER_5 -> cifServiceCancelImpl;
    };
}
```
:warning: 若 mapping到 cifServiceImpl 會直接拋出錯誤
```java=
throw new BadRequestException("DATAC-B-VNUANCIFM001-2", MessageSourceUtils.getMessage("request-status-not-eligibility"))
```

- 呼叫 CifServiceApproveImpl.updateApplicationAndCifData() 
    :bulb: 使用了 @Transactional(rollbackFor = Exception.class)
    -  req.getPhone() 查找 CIF table : 
    :warning: 找不到對應 entity: BadRequestException("DATAC-B-VNUANCIFM001-1", MessageSourceUtils.getMessage("member-not-exist"))
    -  req.getStatus() ==只能是 3: agree 或 4: reject==: 
    :warning: 不滿足 BadRequestException("DATAC-B-VNUANCIFM001-2", "request-status-not-eligibility")
    -  req.getApplicationId() 查找 contract_information table 進行資料檢核: 
    :warning: 找不到: BadRequestException("DATAC-B-VNUANCIFM001-3", "application-not-exist")
    :warning: contract.getStatus() 不等於 3: approve: BadRequestException("DATAC-B-VNUANCIFM001-2", "request-status-not-eligibility")
    
    - 將req的資料更新到 CIF entity，如果 req.getStatus() == 4 還會填入 ReApplyDate
    :question: 註解(line.109)是不是寫錯了 不是cancel, 是reject
    ![](https://hackmd.io/_uploads/BJ_8lAVc3.png)
    
    - 將req的資料更新到 contract_information entity，如果 req.getStatus() == 4 還會填入 RejectCode
    - 若 contract.getStatus() 等於 0: approve 還要新增 quarterly check list
        - 拿取req的 RegisterDate和 Overdue 計算兩者的天數差距
        - 大於等於==90天==，每一天新增一筆 QuarterlyCheckList Entity
        ![](https://hackmd.io/_uploads/rypI4RN5n.png)
        :question: 詢問 QuarterlyCheckList 的功用?
    
- return ResponseUpdateCIFAndApplicationInfoDTO (僅包含 MWHEADER 有 traceId)
:::

### postApiDatacBVnuancifm002 (POST) (/api/DATAC-B-VNUANCIFM002)
> 目的: update-application-and-CIF-data

> CifManager.updateApplicationAndCifData002()
:::success
- 透過 req.getTRANRQ().getCuid() 去查找CIF table 取得對應的 status 來做impl的判斷
```java=
// 註解的impl不存在對應的 override function，所以會直接使用繼承的 cifServiceImpl

public CifService filterCImplByCuid(String cuid) {
    if (TPStringUtils.isNullOrEmpty(cuid)) {
        throw new BadRequestException("cuid is null", MessageSourceUtils.getMessage("member-not-exist"));
    }
    CifEntity cifEntity = cifDAO.findByCuid(cuid).orElse(null);
    if (cifEntity == null) {
        return cifServiceImpl;
    }
    // * 0: new, 1: process, 2: approve, 3: agree, 4: reject, 5: cancel
    return switch (cifEntity.getStatus()) {
        // case NUMBER_0 -> cifServiceNewImpl;
        // case NUMBER_1 -> cifServiceProcessImpl;
        case NUMBER_2 -> cifServiceApproveImpl;
        // case NUMBER_3 -> cifServiceAgreeImpl;
        // case NUMBER_4 -> cifServiceRejectImpl;
        // case NUMBER_5 -> cifServiceCancelImpl;
    };
}
```
:warning: 若 mapping到 cifServiceImpl 會直接拋出錯誤
```java=
throw new BadRequestException("DATAC-B-VNUANCIFM001-2", MessageSourceUtils.getMessage("request-status-not-eligibility"))
```

- 呼叫 CifServiceApproveImpl.updateApplicationAndCifData002() 
    :bulb: 使用了 @Transactional(rollbackFor = Exception.class)
    -  req.getCuid() 查找 CIF table : 
    :warning: 找不到對應 entity: BadRequestException("DATAC-B-VNUANCIFM002-1",MessageSourceUtils.getMessage("member-not-exist"))
    -  req.getStatus() ==只能是 3: agree 或 4: reject==: 
    :warning: 不滿足 BadRequestException("DATAC-B-VNUANCIFM002-2", "request-status-not-eligibility")
    -  req.getApplicationId() 查找 contract_information table 進行資料檢核: 
    :warning: 找不到: BadRequestException("DATAC-B-VNUANCIFM002-3", "application-not-exist")
    :warning: contract.getStatus() 不等於 3: approve: BadRequestException("DATAC-B-VNUANCIFM002-2", "request-status-not-eligibility")
    
    - 將req的資料更新到 CIF entity，如果 req.getStatus() == 4 還會填入 ReApplyDate
    :question: 註解(line.161)是不是寫錯了 不是cancel, 是reject
    ![](https://hackmd.io/_uploads/ByPvSAEc3.png)
    
    - 將req的資料更新到 contract_information entity，如果 req.getStatus() == 4 還會填入 RejectCode
    - 若 contract.getStatus() 等於 0: approve 還要新增 quarterly check list
        - 拿取req的 RegisterDate和 Overdue 計算兩者的天數差距
        - 大於等於==90天==，每一天新增一筆 QuarterlyCheckList Entity
        :bulb: Check Date Caculate rule: The check_date_N value is use "register_date" (從contract_information table) +3 month*N to insert value ???????
![](https://hackmd.io/_uploads/ry9ck48qn.png)

![](https://hackmd.io/_uploads/B19vlEIq2.png)

- return ResponseUpdateCIFAndApplicationInfoDTO (僅包含 MWHEADER 有 traceId)
:::

### postApiDatacBVnquactcq001 (POST) (/api/DATAC-B-VNQUACTCQ001)
> 目的: query-active-customers
> 條件: 1. Data Centralized customer status 為 agree 2. Data Centralized contract status 為 active

> CifManager.queryActiveCustomers()
:::success
- 呼叫 CifServiceImpl.queryActiveCustomers
    - 查找 Cif & ContractInformation table 滿足客戶狀態為3: agree + 合約狀態為0: active
    - 將得到的 CifEntity List for loop 轉換==回覆(customerUuid, intellectCif, phone, personalId)==
- return ResponseQueryActiveCustomerListDTO (包含 MWHEADER 有 traceId + TRANRS ~= CifEntity List)
:::