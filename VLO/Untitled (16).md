:question: 目前DB是串哪一台? 似乎沒有看到對應的 HelmChart config

### pom.xml 設定內容 
:::info
:bulb: 將與gke的連線資訊都存放在secret manager? ${sm://projects/448387779994/secrets/dsuovslxvcp01g01/versions/4}
![](https://hackmd.io/_uploads/H1Ft21NY3.png)

```java=
spring boot version: 2.5.13
java vesion: 11
    
<!-- google cloud secretmanager -->
<dependency>
	<groupId>com.google.cloud</groupId>
	<artifactId>spring-cloud-gcp-starter-secretmanager</artifactId>
	<version>3.4.0</version>
</dependency> 
```
:::

### GsmPostgreDataSourceAutoConfiguration
:::info
1. 和gke有什麼關係? 
Ans: Gsm = Goolgle Secret Manager
2. 這個的用途? HikariConfig
Ans: PostgreSQL利用 HikariConfig 完成連線設定 
ref: https://stackoverflow.com/questions/50213381/how-do-i-configure-hikaricp-for-postgresql
:::

### ExceptionHandler 說明
:::info
**此為@ControllerAdvice層級**
1. 當接收到 CHRException (自定義)
- ResponseStatus: HttpStatus.BAD_REQUEST
- ResponseBody: 把呼叫此API時傳入的參數(MwHeader)透過WebContext取出，連同對應的returnCode,returnDesc一起回覆

2. 除了(1.) 任何非預期的錯誤都視為 Exception
- ResponseStatus: HttpStatus.INTERNAL_SERVER_ERROR
- ResponseBody: 把呼叫此API時傳入的參數(MwHeader)透過WebContext取出，設定ReturnCode.E999(System Error)一起回覆
:::

### CHRController.queryEmpData (POST) (/queryEmpData)
:::success
1. 傳入參數: 
- numberType (NumberType should be 1 )
- number (Number length should be 5 or 8 => 應該是員工編號5碼或8碼)
- 若傳入的參數格式有誤，會回傳CHRException:==ReturnCode.E102(檢核錯誤) && 參數對應的錯誤訊息==

2. 處理參數 WebContext.setBaseRequest: BaseRequest<?> 紀錄requests內容

3. 呼叫 QueryEmpDataService.queryEmpData
- 傳入的是5碼: 呼叫 EmpDataRepository SQL
    - findByEmpCode: 找 EmpData 的 empCode

- 傳入的是8碼: 呼叫 EmpDataRepository SQL
    - findByEmpCode8: 找 EmpData 的 empCode8

- 回傳的staffList: 若是空陣列，會回傳CHRException:==ReturnCode.E101(查無資料)==


4. 將找到的員工資料組合整理，回覆結果==ReturnCode.SUCESS(交易成功)==
```java=
@JsonProperty("EmpName")
String empName;
	
@JsonProperty("EmpEmail")
String empEmail;
	
@JsonProperty("JobStatus")
String jobStatus;
```
:::
