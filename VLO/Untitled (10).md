:::warning
:heavy_exclamation_mark: phone 與 cuid 的交互作用 (通路觀點)
- 在註冊流程中，客戶一開始提供phone, status, consent(同意xxx相關)，就會去DC新增一筆CIF資料(這時候cuid欄位還是空的)
- 等到註冊流程整個成功，這時由==通路生成==一個具有識別性的==cuid==，在透過phone去DC查出CIF並更新這個欄位
- 換言之，其實客戶的資料是由通路中台的一個table在維護(包含 phone, cuid...)
- 註冊成功後，APP內部的操作要通路與DC-CIF做查詢就會採用cuid當條件!!!!
:::

[toc]
:::info
- 透過CifManager.java filterCImpl()方法
    - 透過phone找到Table:cif entity
    - get cifEntity.getStatus()查看是0~5
    - 0: new, 1: process, 2: approve, 3: agree, 4: reject, 5: cancel
    - 根據取的的status來決定要實作哪個class
![](https://hackmd.io/_uploads/ByvZ7Ef5n.png)
:::


## postApiDatacBVnacifdtm001 (POST) (/api/DATAC-B-VNACIFDTM001)
:::success
>purpose: 新增CifData
>call cifManager.addCifData()
- 取得CifService
- call cifService.addCifData
- 透過phone取得cifEntity，找不到則new CifEntity
- 如果cifEntity.getStatus()!null && CIFStatusEnum.NUMBER_0與cifEntity.getStatus()不一樣 
:warning: throw new BadRequestException("DATAC-B-VNACIFDTM001-1",MessageSourceUtils.getMessage("member-exists"));
- 將req資料填入entity再儲存到Table: cif
- 回覆HttpStatus.OK & ResponseAddCIFInfoDTO (僅 MWHEADER)
:::

## postApiDatacBVnqucifdm001 (POST) (/api/DATAC-B-VNQUCIFDM001)
:::success
>purpose: 查找CifData
> call cifManager.queryCifData()
- 取得 CifService
- call cifService.queryCifData
- 透過 phone 取得Table: cif 的cifEntity
:warning: 若取不到則new BadRequestException("DATAC-B-VNQUCIFDM001-1",MessageSourceUtils.getMessage("member-not-exist")))
- 回覆HttpStatus.OK & ResponseQueryCIFInfoDTO

:::

## postApiDatacBVnqucifdm002 (POST) (/api/DATAC-B-VNQUCIFDM002)
:::success
>purpose: 查找CifData
>call cifManager.queryCifData002()
- call this.filterCImplByCuid() 知道CifService實際是實作哪一隻Impl
- call cifService.queryCifData002()
- 透過 Cuid 取得Table: cif 的cifEntity，取不到則拋
:warning: new BadRequestException("DATAC-B-VNQUCIFDM001-1",MessageSourceUtils.getMessage("member-not-exist")));
- 回覆HttpStatus.OK & ResponseQueryCIFDataDTO

:::
## postApiDatacBVnupcifdm001 (POST) (/api/DATAC-B-VNUPCIFDM001)
:::success
>purpose: 更新CifData
>call cifManager.updateCifData()
- 取得 CifService ，目前針對updateCifData()六個Impl都有覆寫
    - cifServiceNewImpl 
    - cifServiceProcessImpl
    - cifServiceApproveImpl
    - cifServiceAgreeImpl
    - cifServiceRejectImpl
    - cifServiceCancelImpl
- 六隻Impl實作部分如下:
1. 透過 phone 取得Table: cif 的cifEntity
2. 若找不到則拋:warning: new BadRequestException("DATAC-B-VNUPCIFDM001-1",MessageSourceUtils.getMessage("member-not-exist")));
3. 取得requestUpdateCIFInfoDTO.getTRANRQ().getStatus()==若與表格內的值***不一樣***則拋錯==:warning:new BadRequestException("DATAC-B-VNUPCIFDM001-2",MessageSourceUtils.getMessage("request-status-not-eligibility"));

| call service | cifManager.updateCifData()|
| -------- | -------- |
| 判斷變數     | requestUpdateCIFInfoDTO.getTRANRQ().getStatus() | 
| cifServiceNewImpl | 0,1 |
| cifServiceProcessImpl | 1,2,4 |
| cifServiceApproveImpl | 2,3,4 |
| cifServiceAgreeImpl | 1,3 |
| cifServiceRejectImpl | 1,4 |
| cifServiceCancelImpl | 1 |

4. set data (cifEntity,consent)
5. 回覆HttpStatus.OK & ResponseUpdateCIFInfoDTO

:::
## postApiDatacBVnupcifdm002 (POST) (/api/DATAC-B-VNUPCIFDM002)
::::success
>purpose: 更新CifData
>call cifManager.updateCifData002()
- 取得 CifService ，目前針對updateCifData()六個Impl都有覆寫
    - cifServiceNewImpl
    - cifServiceProcessImpl
    - cifServiceApproveImpl
    - cifServiceAgreeImpl
    - cifServiceRejectImpl
    - cifServiceCancelImpl
- 六隻Impl實作部分如下:
1. 透過 phone 取得Table: cif 的cifEntity
若找不到則拋:warning:new BadRequestException("DATAC-B-VNUPCIFDM002-1", MessageSourceUtils.getMessage("member-not-exist")));
2. 取得requestDTO.getTRANRQ().getStatus()==若與表格內的值***不一樣***則拋錯==:warning:new BadRequestException("DATAC-B-VNUPCIFDM002-2",MessageSourceUtils.getMessage("request-status-not-eligibility"));

| call service | cifManager.updateCifData002()|
| -------- | -------- |
| 判斷變數     | requestDTO.getTRANRQ().getStatus() | 
| cifServiceNewImpl | 0,1 |
| cifServiceProcessImpl | 1,2,4 |
| cifServiceApproveImpl | 2,3,4 |
| cifServiceAgreeImpl | 1,3 |
| cifServiceRejectImpl | 1,4 |
| cifServiceCancelImpl | 1 |

3. set data (cifEntity,consent)
4. save cifEntity
5. 回覆HttpStatus.OK & ResponseUpdateCIFDataDTO

::::

## postApiDatacBVnqcifwpq001 (POST) (/api/DATAC-B-VNQCIFWPQ001)
:::success
>purpose: query-CIF-info-with-personalId
>call cifManager.queryCifInfoByPersonalId()
- call this.filterCImpl() 知道CifService實際是實作哪一隻Impl
- call cifService.queryCifInfoByPersonalId
- 透過PersonalId到Table: cif找到EntityList
- 若找不到則報錯:warning:throw new BadRequestException("DATAC-B-VNQCIFWPQ001-1",MessageSourceUtils.getMessage("member-not-exist"));
- 取得EntityList第0個位子的Entity==>**取第一筆資料**
- 回覆HttpStatus.OK & ResponseQueryCIFInfoWithPersonalIdDTO
::::

## postApiDatacBVnupculcm001 (POST) (/api/DATAC-B-VNUPCULCM001)
:::success
>purpose: Update Customer Locale
>call cifManager.updateCustomerLocale
- 透過requestDTO.getTRANRQ().getCuid()查找Table: cif取得Entity
- 若取不到則丟自定義memberNotExistException
- 取得requestDTO.getTRANRQ().getLocale()並更新Entity的Locale欄位
- reutrn ApiResponseHeaderDTO
:::

## postApiDatacBVnquculcq001 (POST) (/api/DATAC-B-VNQUCULCQ001)
:::success
>purpose: Query Customer Locale with cuid
>call cifManager.queryCustomerLocaleWithCuid()
- call cifServiceImpl.queryCustomerLocaleWithCuid()
- 透過requestDTO.getTRANRQ().getCuid()查找Table: cif取得Entity
- 若取不到則丟自定義memberNotExistException
- 如果cifEntity.getLocale()有值，回傳該欄位的值
- 如果cifEntity.getLocale()沒有值，則透過抓取@Value("${system.locale}")判斷值為何
- return ResponseQueryCustomerLocaleDTO
:::

## postApiDatacBVnquclwcq001 (POST) (/api/DATAC-B-VNQUCLWCQ001)
:::success
>purpose: Query Customer Locale with customer number(intellectCif)
>call cifManager.queryCustomerLocaleWithIntellectCif()
- call cifServiceImpl.queryCustomerLocaleWithIntellectCif() 
- 透過requestDTO.getTRANRQ().getIntellectCIF()查找Table: cif 取的Entity
- 若取不到則丟自定義memberNotExistException
- 如果cifEntity.getLocale()有值，回傳該欄位的值
- 如果cifEntity.getLocale()沒有值，則透過抓取@Value("${system.locale}")判斷值為何
- return ResponseQueryCustomerLocaleDTO
:::