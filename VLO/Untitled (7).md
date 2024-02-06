[toc]

### config & sql
:::info
- 有一份基礎的 helmchart 設定 (configmap, deployment, values)
- 每一個專案應該都有一份init的folder來建立專案所需要的所有內容
    - SQL folder
1.mail_msg: oid (自增) as PK
2.notification_msg: oid (自增) as PK 
3.otp: oid (自增) as PK 
4.sms_msg: oid (自增) as PK 
5.template: oid (自增) as PK

![](https://hackmd.io/_uploads/HkByYrKtn.png)

- postgreSQL 連線設定:
    - DataSourceLogConfig: 
        - @ConfigurationProperties(prefix = "spring.log.datasource")
        - 管理的table: execution_record (init sql 寫在data-centralized裡面), pubsub_log
    - DataSourcePrimaryConfig (@Primary):
        - @ConfigurationProperties(prefix = "spring.primary.datasource")
        - 管理的table: mail_msg, notification_msg, otp, sms_msg, template
```java=
// 如何連線??? 有定義url? username? password? Ans: 參考下圖
     
@Bean(name = "logDataSource")
@ConfigurationProperties(prefix = "spring.log.datasource")
public DataSource logDataSource() {
    Driver driver = new org.postgresql.Driver();
    return new SimpleDriverDataSource(driver, "url", "username", "password");
}

@Bean(name = "primaryDataSource")
@ConfigurationProperties(prefix = "spring.primary.datasource")
public DataSource primaryDataSource() {
    Driver driver = new org.postgresql.Driver();
    return new SimpleDriverDataSource(driver, "url", "username", "password");
}
```
:bulb:helmchart有設定 spring.primary.datasource.url 相關的資料，就是對應需要的欄位
![](https://hackmd.io/_uploads/HJBpI4G9h.png)

:::

### Redis & Db0, Db1, Db3 
:::info
- 此處使用 JedisConnection，需提供 index, host, port, pswd, timeout 來創立RedisTemplate 進行Redis操作
![](https://hackmd.io/_uploads/ryLzP4fq3.png)

![](https://hackmd.io/_uploads/SJNWD4fc3.png)

- Redis在本專案的功能: 將xxx資料存入redis，再透過redisTemplate以指定的key取出對應的value，進行後續xxx處理，再回存redis

- Db0 (@Primary), Db1, Db3 的使用場景? 水哥回答，執行cache機制，以pub/sub為例，因為pub/sub handler其實不只一個，所以透過redis來記錄每一筆訊息有被處理過，避免重複寄送
    - db0RedisTemplate: AsyncMailHandler, AsyncNotificationHandler, AsyncSmsHandler
    - db1RedisTemplate: TimeShiftedOffsetRedisManager
    - db3RedisTemplate: PreventDuplicateRedisManager
:::

### PubSub
:::info
> Cloud PubSub (應用程式服務的中介) 是Google Cloud Platform上的一個Publish Subscriber的服務，讓使用者可以透過API將資料放到PubSub上，並且透過建立Subscriber讓其他的程式可以讀取該資料

- PubSubConfig: For serialization and deserialization of POJOs using Jackson JSON, configure a ***JacksonPubSubMessageConverter bean***, and the ***Spring Boot starter*** for GCP Pub/Sub will ==automatically wire it into the PubSubTemplate.==

- PubsubManager: 分別實作mail, sms, notification+notification success 的 async publish，將資料推寫到topic並回傳messageId
- PubSubHandlerManager: (類似aop)定義泛型方法，來處理subscriber定期向topic拉取資料的過程(consume)，會將message payload 印出並新增一筆 pubsub_log 資料
:::

### Firebase
:::info
使用google firebase套件 : 提供推播服務
:question:  推給誰? Ans: 就是推給 firebase server 然後APP就會收到推播訊息
![](https://hackmd.io/_uploads/B14YTynth.png)

![](https://hackmd.io/_uploads/ryXq9kht2.png)

**FirebaseManager.java** : 
- init():
```java=
builder.setCredentials(GoogleCredentials.getApplicationDefault())
            .setProjectId(FIREBASE_PROJECT_ID);

FirebaseApp.initializeApp(builder.build());
```

- 利用Message.Builder()的方式建立所以要推播的message內容
```java=
Message.builder()
    .setToken(registrationToken)
// registrationToken == DeviceToken (透過 FeignClient 呼叫 DeviceTokenApiClient)
```

- String response =FirebaseMessaging.getInstance().send(message.build());可真正將訊息推播出去
:::

### Sendgrid - for GCP send mail
:::info
**SendgridConfiguration.java** :
- 定義好 (class)BaseSendgridManager裡面的變數該長甚麼樣子
1. sendgridApiKey 
2. senderMail
3. sendName 
- 透過annotation定義讀取此config條件 如下圖:
![](https://hackmd.io/_uploads/Hk4MRqiKh.png)

**BaseSendgridManager.java** : 定義interface以提供其他程式實作

**SendgridManager.java** : 實作 (ingerface)BaseSendgridManager.java以提供各個程式去做send mail功能
*變數 proxyIp,proxyPort應該是TPI需透過代理伺服器才能寄信，因此多這兩個變數*

sendgrid套件提供Email library
![](https://hackmd.io/_uploads/HklWgsiY2.png)
![](https://hackmd.io/_uploads/BJBNgosFn.png)

建立一個http request連線call sendGrid API以達到寄信的目的
![](https://hackmd.io/_uploads/rJV2biotn.png)

寄信過程中若發生Exception: 這裡的GlobalExceptionHandler根本沒有implements ProblemHandling  這樣不會有作用????? :question: :question: 
```java=
log.error("Failed to send email: {}", e.getMessage());
    throw Problem.builder()
            .withStatus(Status.INTERNAL_SERVER_ERROR)
            .withTitle(Status.INTERNAL_SERVER_ERROR.getReasonPhrase())
            .withDetail("Failed to send email.")
            .with("email", receiver)
            .build();
```
ref for sendgrid mail:https://github.com/sendgrid/sendgrid-java
:::

### Twilio
:::info
**TwilioConfiguration** : 建立一個Twilio bean給大家使用
**BaseTwilioManager** : 定義interface以提供其他程式實作
**TwilioManager** : 實作 (ingerface)BaseTwilioManager.java以提供各個程式去做send sms功能

twilio 需要先執行以下程式碼取得username & password
````java=
Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
````
目前是設定在config裡面
![](https://hackmd.io/_uploads/r1xY8ToYh.png)

twilio套件提供寄簡訊的library
使用Message的方法create (creator) & send (create) 簡訊
![](https://hackmd.io/_uploads/ByQdVasFh.png)
![](https://hackmd.io/_uploads/SkinVasY2.png)

寄簡訊過程中若發生Exception: 沒有任何處理? :question: :question: 
:::

### JWT Token
:::info
JwtAuthenticationToken.java目前只有在TokenUtils.java被使用
![](https://hackmd.io/_uploads/H1h215sth.png)
但目前沒有看到任何程式使用getCurrentUser()

:question: 請教TPI這一隻程式的使用時機?
A: 目前在msg-center並未使用JWT Token的方法 (若有串接keycloak才會需要)
:::

### exception
:::info
:bulb: 和esign-bl設計相同

- 透過GlobalExceptionHandler.java來做AOP，且有制定以下幾種錯誤的exception，再依據需求丟出相對應的Exception
    - BadRequestException
    - ConflictException
    - ForbiddenException
    - InternalServerErrorException

- GlobalExceptionHandler.java 是實作抽象 ResponseEntityExceptionHandler，並override method:handleExceptionInternal()，將此method改成符合需求的樣子
:::
