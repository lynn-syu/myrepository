[toc]

金控gitlab lucent sky branch:https://gitlab.vn-loancloud-dev.com/ovs-lx-vlo-01/cdc/-/tree/feature/lucent-sky-fix
## 上板時間
- 原因: 上UAT需要DBA操作SQL所以會比較麻煩，且想降低大家上板的loading
- 所以遵照 :
    - 盡量以星期一才會上 SQL(ddl or dml ) + helmcharts 的(configmap or {env}.yaml) 
    - 星期三盡量僅更新程式碼

![](https://hackmd.io/_uploads/BJnQIgUsh.png)
![](https://hackmd.io/_uploads/rkirq4vjh.png)

- 版號疊代: 目前大外宣都是統一版號，內部版本控制遵照水哥在line群組上excel圖片的版本控制來跳號
![](https://hackmd.io/_uploads/BJn-6NPjn.png)


## 上板步驟
### **大前提，HES三個project(app-api,backoffice,camunda)要各別佈版到各CI/CD環境，每次版本更新都要確認三個project都要Deploy**


**step 1** TPI會寄一封信給國泰CDC

**step 2** 信中會有一個gitlab 
https://gitlab.vn-loancloud-dev.com/ovs-lx-vlo-01/cdc/-/compare/2023_07_05_Bank...2023_07_19_Bank?from_project_id=45
:heavy_check_mark: 請水哥申請帳號

**step 3** 切換至對應分支，檢查版本差異

**step 4** Repository-Files 下載source code(細到by project)
![](https://hackmd.io/_uploads/S16LGzH53.png)

**step 5** 打開eclipse 檢查當前分支還有沒有TFS提交需求 => 才可以切新的分支
==分支命名: 取TPI+TPI提交日期+本次更新版本號==
==**Example:**[摘要]TPI提交程式-20230726_0.5.XX==

**step 6** 打開兩個視窗，左邊是下載最新的，右邊的是本地的程式==>把右邊的SRC直接刪除==>將左邊新的code直接貼上
:bulb:有時候util會更新，不用特別CICD
:heavy_check_mark: 要記得檢查dockerfile或是gradle.setting有沒有更動，如果有更動也要更新
![](https://hackmd.io/_uploads/S1DnGfH9h.png)

**step 7** 透過git commit & push 程式
![](https://hackmd.io/_uploads/H1hbEzBq2.png)


**step 8** (CI)切到新的分支，點選Pipeline-Builds > 找到有修改的project對應的腳本，點右上角queue(選到新的分支)=>點選Queue
![](https://hackmd.io/_uploads/SJF_NMB5n.png)

:heavy_check_mark: 都等CI的過程都完成了再進行CD不然會找不到東西

**step 9** (CD)切到新的分支，點選Pipeline-Release > 找到有修改的project，點右上create-release
:warning: 要記得手動關閉 (執行SIT更新 就是選 del ut pod + ut)(執行UAT更新 就是選 del uat pod + uat)
:warning: 要記得選擇正確的Artifacts
![](https://hackmd.io/_uploads/BkbZUMHq3.png)
新增出來的 relase 要檢查 分支是不是正確
![](https://hackmd.io/_uploads/Byc2wfS53.png)



:::info
HES SIT DB環境root帳號密碼:
account: ovslxvlo01_00589361
password: 0z@@N@3M@aom07


example :修改DB 欄位type
![](https://hackmd.io/_uploads/Hyy-Chich.png)

:::




