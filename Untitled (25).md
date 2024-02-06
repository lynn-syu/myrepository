[toc]

## getApplicationStatusReportData (GET) (api/reports/application-status"):bulb:
> call reportService.getApplicationStatusReportData
::: success
- get statuses: JdbiUtils.getEnumStrings()
- get customerGroups:JdbiUtils.getEnumStrings()
- call reportRepository.applicationStatusReportFindAll() Table: application,customer_group

:::

## getApplicationStatusReport (GET) (Export Excel) 
:question: download excel的方法??
1.應該是讀取DB之後再透過
 GETMAPPING 設定 produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
2. return  ResponseEntity"<"InputStreamResource">"來下載excel
![](https://hackmd.io/_uploads/HkZ4dSFK3.png)
> call reportService.getApplicationStatusReport
::: success
- call reportTemplateService.generateApplicationStatusReport
    - call reportTemplateRepository.getApplicationStatusReportItems() 
    - Table:application,customer_group
:::
## getApplicationCoveragereportData (GET)  (api/reports/application-coverage)
> reportService.getApplicationCoverageReportData
::: success
- call reportRepository.applicationCoverageReportFindAll()
    -取多張Table: date_range,applications,applications_in_range
:::    
## getApplicationCoverageReport (GET) (Export Excel)
(path = "application-coverage.xlsx",
produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
> call reportService.getApplicationCoverageReport
::: success
- call reportTemplateService.generateApplicationCoverageReport()
    - call reportTemplateRepository.getApplicationCoverageReportItems()
    - Table:date_range,applications,applications_in_range
:::