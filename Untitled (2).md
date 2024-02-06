## 流程類別 *1
程式管理 Hank ==DONE==
- tfs 功能與repos說明
- 如何執行 git flow > 3 branch 對應什麼stage > 如果有一個新功能怎麼使用
    - develop長出新的branch
- helmchart 的 stage對應
- r1.0.0 的那個版號 是在上線前定好寫在release branch

簡易上線流程
- CICD: 管線設定 alm-service單 與執行
- 必要文件: 7個
    - SIT測試報告
    - UAT測試報告
    - 變更上線檢核表
    - USER 允許上線mail
    - HRI(風險辨識評估表)
    - Lucent sky掃描報告
    - 壓力測試報告
- 很多單子: icontact -> 轉tfs -> release project + DBA任務單

## HES架構 & 程式說明 *6
1.大方向 + build.gradle + GCP看看 > Lynn ==ppt DONE==

2.appi-api + 某一張圖 可能可以請宜峰補充 > Hank ==ppt DONE==
3.backoffice + 畫面搭配 > Lynn ==ppt DONE==

- 插件: 給大珍的 HES整體說明 20240117

4-1.camunda + modeler > Hank
4-2.create application.bpmn > Hank

5.feign + execution_record + exception + tracing > Lynn
6.helmchart + CICD + Dockerfile > Lynn ==ppt DONE==