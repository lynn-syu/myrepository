[toc]

7/27 meeting : https://meet.google.com/voo-nqij-uvi?hs=224

## Data-Centralized

:::info
- [x] IntellectArxManager 這一個todo還要處理嗎?
A: 這個目前不會再使用redis，忽略todo
![](https://hackmd.io/_uploads/S1Wg5zL9n.png)

- 說明這個對照方式 審查狀態 >> ==空白的格子就是表示不允許這樣的情況發生==
- [x] ApplicationStatusEnum 0: active, 1: overdue, 2: cancel, 3: approve, 4:reject
    - 一般: 該合約(Application)目前的狀態
    - 粗體: 是APP要求要切的狀態
    - 舉例:當客戶拒絕簽屬合約時，APP就會傳送cancel的狀態給API去進行資料變更，此時API會先檢查合約原本是不是approve狀態，才會執行將status欄位變成cancel的動作。換句話說，原本合約是approve狀態才可以讓客戶進行，完成簽屬(active), cancel(拒絕簽屬), 或 reject的狀態變更
    
![](https://hackmd.io/_uploads/B13fsX85n.png)

- [x] CIFStatusEnum 0: new, 1: process, 2: approve, 3: agree, 4: reject, 5: cancel
    - 一般: 該客戶(CIF)目前的狀態
    - 粗體: 是APP要求要切的狀態
    - 舉例:當客戶通過徵審時，APP就會傳送approve的狀態給API去進行資料變更，此時API會先檢查客戶原本是不是precess狀態，才會執行將status欄位變成approve的動作，來完成這個通過徵審紀錄。
    
![](https://hackmd.io/_uploads/B12-eE8c2.png)
:::

:::success
- CifController
    - [x] 在進行update CIF data時，請問這樣的設計方式是為什麼?
    Ans: 一種design-pattern
![](https://hackmd.io/_uploads/r1RRlsD93.png)
:::

- ApplicationInfoController
    - [x] addApplicationData 這個動作，為什麼用 applicationId 查的到資料(contract_information) 就是有問題 ? 但是使用 customerUuid 就沒關係? 
A: 一個客戶只能有一個application是active (沒有很了解，但這個是業務邏輯..請洽bu)
![](https://hackmd.io/_uploads/SkRwof8qh.png)

    - [x] addApplicationData 的enum轉換，將exception列為M002-1 是因為M001沒有使用了?
Ans: API 只要有001結尾基本上都是準備棄用 (phone -> cuid)
![](https://hackmd.io/_uploads/H1qg5X8cn.png)

    - [x] postApiDatacBVnquapdtq001 在查找 CIF table時，找不到則丟出 InternalServerErrorException，目前在其他API都是以 BadRequestException，為什麼突然是這個分類?
A: 如果要丟什麼錯，最正確的要問SD
![](https://hackmd.io/_uploads/B1bEhG893.png)
    - [ ] postApiDatacBVnqucadtq001 這一個編碼(member-not-exist)是不是怪怪的?
A: 等TPI確認文件    
![](https://hackmd.io/_uploads/H1zGTz8ch.png)
    - [x] postApiDatacBVnupdtadm001 標記為 @deprecated 是真的沒有使用?
A: TPI回覆確定不用
:::info

- FreezeEventController
:bulb: 目前有三種情境 1.intellect 發現有遲繳 2.AML變成黑名單 3.手動讓客戶變freeze(待需求訪談中)

- [x] 請問 freeze_list event欄位代表的意義?
Ans: 承上，所以只要被要求freeze就會新增，因為客戶有多個原因被凍結，一個客戶可能會有多筆

- [x] postApiDatacBVngtcufeq001 查找 freeze_list table: 透過 CIF entity.IntellectCif + action =‘1’ freezed，取得 event 欄位結果。這如果沒有找到資料 就會回傳 List 為 null的 response???
Ans: 查不到就是null，當呼叫方取得null 他們會在自己判斷處理

- [x] postApidatacBVntrcmfem001 為什麼進行unfreeze (line.221) 的時候有先查table，那freeze就沒有?
A: freeze是就是直接凍結，直接去動作。而unfreeze時要先查出是不是真的有那個類型(三者其一)的凍結記錄，才會拿著那些資料去intellect進行後續動作，以及更新那幾筆資料變成解凍
![](https://hackmd.io/_uploads/SyiPe8Iqh.png)

- ApplicationCIfController
    - [x] postApiDatacBVnuancifm001 註解(line.109 & line.161)是不是寫錯了？
    A:註解沒有改到，確認是要Number4-reject
![](https://hackmd.io/_uploads/HkG3km89h.png)
    - [x] 詢問 QuarterlyCheckList 的功用
    A: 一個客戶通過徵審後，還是要定期去檢查該客戶的某些資料，這時候就透過新增多筆的checkList(寫資料到DB)不同的檢查項目&不同的檢查時間。然後在==數據中台==會去讀這個table的資料，來做後續處理
![](https://hackmd.io/_uploads/SyfgxXU9h.png)
:::

- [x] ShiftedOffsetController，請問 checkReapplyUser方法，使用 ExecutorService (ThreadPoolErrors) 的目的?
Ａ：這一支API的設計是為了讓GCP上面的cron job去呼叫用的，所以要讓他以==非同步執行==，才會使用ExecutorService這個方法

:::info
- DeviceTokenController
    - [x] 請問 postApiDatacBVnqdvwudq001 若找不到則new DeviceTokenEntity 是生成一個新的entity目的只是要回覆的格式正常而已?
    A:目的只是讓接的人可以有這個entity而已
    - [x] postApiDatacBVnquadtkq001 查找Table: device_token所有的Entity，這樣哪裡判別了active???  (目的: query-active-device-token 查詢有存在的手機token,uuid)
A: 沒有active deactive這個分類，單純命名有誤

![](https://hackmd.io/_uploads/SJsPwQ853.png)
:::


## 確認 VCP問題集-1 回覆狀況

### (新增) SmsController-postApiMsgcrBAsyncsmso001
- [ ] 若存在有重複的資料則 throw new BadRequestException(=="MSGCR-B-ASYNNTFYO001-3"==, "duplicated-txnseq") 這個編號應該是錯的? :negative_squared_cross_mark: :negative_squared_cross_mark: :negative_squared_cross_mark: :negative_squared_cross_mark: 和API名稱不相符
Ans: 確實有錯誤等待修改
![](https://hackmd.io/_uploads/HyPviwD52.png)

### (新增) AsyncNotificationHandler
- [ ] 第69行的 cif (呼叫data-centralized) 卻沒有使用該資料?
Ans: TPI可能是很久以前需要用到，目前不用CIF了 等待修改
![](https://hackmd.io/_uploads/S1fOgYw9h.png)
