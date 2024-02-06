## postApiMsgcrBTempcretm001 (POST) (/api/MSGCR-B-TEMPCRETM001)
:::success
> 用途: Create a Template
> Req: (mail) templateCode, title, content, apid

- 檢查 apid 是否合法: req.MWHEADER.SOURCECHANNEL === req.TRANRQ.Apid
:warning: 若不合法則 throw new BadRequestException("MSGCR-B-TEMPCRETM001-1", "illegal-apid")
- 通過檢查: ==利用 TimeManager 取得 OffsetDateTime== + Req 資料 = TemplateEntity 寫入 table
:warning: Save TemplateEntity 若有錯誤: throw new BadRequestException("MSGCR-B-TEMPCRETM001-2", "template-code-exist", e)
- 結束回傳: RETURNCODE (200) + RETURNDESC (successful-tx) + MWHEADER (copy from req) + TraceId
![](https://hackmd.io/_uploads/ByPSBdz93.png)
:::

## postApiMsgcrBTempdeltm001 (POST) (/api/MSGCR-B-TEMPDELTM001)
:::success
> 用途: Delete the Template
> Req: oid

- 透過 oid 找到 templateEntity
:warning: 若找不到則 throw new BadRequestException("MSGCR-B-TEMPLATEQ001-1", "illegal-apid")
- 檢查 apid 是否合法: req.MWHEADER.SOURCECHANNEL === templateEntity.Apid
:warning: 若不合法則 throw new BadRequestException("MSGCR-B-TEMPLATEQ001-2", "apid-no-permission-template-code")
- 檢查通過: 執行delete(真的刪除)
:warning: Delete TemplateEntity 若有錯誤: throw new BadRequestException("MSGCR-B-TEMPLATEQ001-1", "template-not-fount", e)
- 結束回傳: RETURNCODE (200) + RETURNDESC (successful-tx) + MWHEADER (copy from req) + TraceId
![](https://hackmd.io/_uploads/SyxwHuz52.png)
:::

## postApiMsgcrBTemplateq001 (POST) (/api/MSGCR-B-TEMPLATEQ001)
:::success
> 用途: Get the Template
> Req: (mail) templateCode

call templateService.getTemplate
- 從DB_Table: template透過template code取得資料
- 比對DB拿到的資料&前端的Request 兩者APID是不是一致
- 如果一致就組資料
- 組response DTO:TemplateQueryResponseDTO 回覆前端
![](https://hackmd.io/_uploads/SyDFBuGc3.png)
:::

:heavy_exclamation_mark: 這一支api連系統文件都沒有描述
## postApiMsgcrBTemplateq002 (POST) (/api/MSGCR-B-TEMPLATEQ002)
:::success
> 用途: Query the Template By Apid
> Req: MWHEADER.SOURCECHANNEL 就是 apid

call templateService.queryTemplate
- 從DB_Table: template透過APID取得資料
- 跑foreach將資料組好變成一個templateDTOList
- response TemplateQueryByApidResponseDTO 回覆前端

:::

## postApiMsgcrBTemplateu001 (POST) (/api/MSGCR-B-TEMPLATEU001)
:::success
> 用途: Update the Template
> Req: oid, title, (mail) templateCode, content, apid

call templateService.updateTemplate
- 從DB_Table: template透過oid code取得資料
- 比對DB拿到的資料&前端的Request 兩者APID是不是一致
- response VoidResponseDTO 回覆前端
![](https://hackmd.io/_uploads/rkTsB_zqn.png)
:::
