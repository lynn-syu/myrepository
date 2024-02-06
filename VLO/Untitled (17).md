Q: 如何在spring boot專案使用 GCP 的 GCS 功能?? 
![](https://hackmd.io/_uploads/r1ypXEZFn.png)
Ans:
![](https://hackmd.io/_uploads/BJAnP7Gq2.png)
![](https://hackmd.io/_uploads/HJeEumz9n.png)


### pom.xml 設定內容 
:::info
:question: 確認一下使用 db-encrypt加了兩個(line.8, line.15)且版本不一樣?? 用法????
:question: 確認一下使用 kafka串接 & ip & 目的????
:question: 確認一下使用 jwt ????
使用 secretmanager 用法?? GCP套件存放db username, password
使用 gcp storage 用法?? GCP-GCS套件存放檔案
```java=
spring boot version: 2.5.13
java vesion: 11
    
<!-- cathay Decrypter -->
<dependency>
	<groupId>cub.util</groupId>
	<artifactId>db-encrypt</artifactId>
	<version>2.3.1-RELEASE</version>
</dependency>

<!-- cathay Decrypter -->
<dependency>
	<groupId>cub.util</groupId>
	<artifactId>db-encrypt</artifactId>
	<version>2.3.3-RELEASE</version>
</dependency>

<dependency>
    <groupId>org.springframework.kafka</groupId>
	<artifactId>spring-kafka</artifactId>
</dependency>

<!-- JWT -->
<dependency>
	<groupId>com.auth0</groupId>
	<artifactId>java-jwt</artifactId>
	<version>3.11.0</version>
</dependency>
<dependency>
	<groupId>io.jsonwebtoken</groupId>
	<artifactId>jjwt</artifactId>
	<version>0.9.1</version>
</dependency>

<!-- GCP -->
<dependency>
	<groupId>com.google.cloud</groupId>
	<artifactId>spring-cloud-gcp-starter-secretmanager</artifactId>
	<version>3.4.0</version>
</dependency>
<dependency>
	<groupId>com.google.cloud</groupId>
	<artifactId>spring-cloud-gcp-starter-storage</artifactId>
	<version>3.4.0</version>
</dependency>
```
:::

### postgreSQL && sql
:::info
:question: ovslxvlo01_00589362是誰?? 看起來db owner是他
1.batch_log: OWNER to ovslxvlo01_00589362 & batch_id as PK
2.emp_data: OWNER to ovslxvlo01_00589362 & (emp_code, file_date) as PK
3.failed_data_log: create INDEX failed_data_log_file_date & id (自增) as PK

:bookmark: 為了改進查詢效率，在資料量大的時候，可以透過建立索引(INDEX)讓資料庫加速查詢效率

***postgreSQL連線方式*** 
![](https://hackmd.io/_uploads/rkktQYGK2.png)
:::

### GsmPostgreDataSourceAutoConfiguration
:::info
參考svc-chr
:::

### EncryptionPropertyResolver
:::info
:question: 是不是根本沒用到???
:::

### CommonErrorHandler
:::info
:question: 是不是根本沒用到???
:::

### RestConfig && RestTemplateLoggingInterceptor
:::success
- RestConfig: 做RestTemplate基礎設定 && getByPassSslHttpClient
- RestTemplateLoggingInterceptor: 每當有一個Rest Request送出時，會進行
    - traceRequest -> execute -> traceResponse
    - 資料格式皆以json做處理
```java=
// traceRequest
logger.info("request URI : " + request.getURI());
logger.info("request method : " + request.getMethod());                
logger.info("request header : " + request.getHeaders());
logger.info("request body : " + getRequestBody(body));

// traceResponse
String body = getBodyString(response);
logger.info("response status code: " + (response!=null ?response.getStatusCode():"null"));
logger.info("response status text: " + (response!=null ?response.getStatusText():"null"));
logger.info("response body : " + body);
```
:::

### SchedulerConfig && EmpDataTask
:::success
- SchedulerConfig: 
    - @ConditionalOnProperty(控制該Config是否生效): 
        - 從設定檔中取出"app.scheduling.enable"的值(value = "app.scheduling.enable",)
        - 比對是否為true (havingValue = "true")，比對成功就可以生效
        - 若application.properties沒有設定該值，還是允許使用(matchIfMissing = true)
    - 設定一個長度10的定時任務執行緒池

- EmpDataTask: 
    - 定時執行 insertEmpDataToDB:
        - 取出執行當下日期 format成 ==yyyyMMdd==，使用該值當作batch_id查詢是否有batch_log的紀錄。若無紀錄才可以往下執行!!!
        1. 清除==7天前==舊資料: empData, failedDataLog, batchLog
        2. 新增一筆batchLog: 執行當下日期為batch_id+Flag為LOADING
        3. 從gcsFlgFile取得檔案 :question: -> 不存在 batch_log改Flag為FAIL+存入errDesc，結束。
        4. 從gcsFlgFile取得檔案 -> 檔案為空檔 batch_log改Flag為FAIL+存入errDesc，結束。
        5. 將FlgFile內容作處理，得到 txtFileGsUri :question: 
        6. 從gcsTxtFile取得檔案 -> 不存在 batch_log改Flag為FAIL+存入errDesc，結束。
        7. 從gcsTxtFile取得檔案 -> 檔案為空檔 batch_log改Flag為FAIL+存入errDesc，結束。
        8. 將TxtFile內容作處理(員工清單 CSV格式 - EmpFlgFileHeaderEnum)逐筆檢查
            - 判斷deptCode: 滿足 totalCount++，==不滿足-換下一位==
            - isEmpDataValid: 各欄位的資料檢核，==不滿足-換下一位 & failCount++==
            - isEmpDataDuplicate: 查找 EmpData，==重複了-新增一筆 failedDataLog & 換下一位 & 加入 duplicateEmpDataIdList & failCount++==
            - 通過以上，則可以存入 EmpData: 若存入db時發生exception，則會新增一筆 failedDataLog & failCount++

        9. 檢查 duplicateEmpDataIdList 將重複的員工資料做刪除 & failCount+=size
        10. 統計 successCount = totalCount - failCount; 
        11. 更新 batch_log改Flag為SUCCESS+其他欄位，結束。
        
        ***step8-11 若發生exception，則會batch_log改Flag為FAIL+存入errDesc，結束。***
:::
