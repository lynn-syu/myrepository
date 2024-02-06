
# Lynn
```java=
// VarKey.java
// 這些是目前camunda會執行的主流程嗎? 

// Process definition keys
public static final String REGISTER_CUSTOMER_PROCESS_DEFINITION_KEY = "RegisterCustomerProcess";
public static final String CREATE_APPLICATION_PROCESS_DEFINITION_KEY = "CreateApplicationProcess";
public static final String REQUEST_TS_DATA_PROCESS_DEFINITION_KEY = "RequestTSDataProcess";
public static final String REQUEST_FPT_DATA_PROCESS_DEFINITION_KEY = "RequestFPTDataProcess";
public static final String REQUEST_CIC_DATA_PROCESS_DEFINITION_KEY = "RequestCICDataProcess";
public static final String DOCUMENT_TEMPLATE_PROCESS_DEFINITION_KEY = "DocumentTemplateProcess";

// 對比 ProcessName.java
public static final String CASH_LOAN = "cash-loan";
public static final String REGISTRATION = "registration";
public static final String BNPL = "bnpl";
```

:::success
:bulb: **businessKey**
- 一張單的識別ID，在啟動一個新流程時提供，這樣在後續都可以拿出來使用 
- A business key (人工提供) is a domain-specific identifier of a process instance. Compare this to the process instance id, which is a Camunda-generated UUID (自動生成不易辨識).
- 使用方式: 透過businessKey去找出指定的流程instance
```java
ProcessInstance shipmentInstance = runtimeService
  .createProcessInstanceQuery()
  .processInstanceBusinessKey("填入自定義的businessKey")
  .singleResult();
```
:::


## create application
起點:
```java=
runtimeService.startProcessInstanceByKey(
    CREATE_APPLICATION_PROCESS_DEFINITION_KEY, 
    businessKey, 
    Map.of(CUSTOMER_ID, customerId, APPLICATION_ID, applicationId
));
```
透過Start spinner設定:
![](https://hackmd.io/_uploads/BkrFb6ou2.png)

- businessKey (類似一張單的識別ID) 對應signal欄位的 execution.processBusinessKey

- [ ]  當process start時,businessKey會存到哪一張table(act_ru_task or??)

- Map.of裡面存放的資料，透過In mapping propagation: propagate all variable 不斷傳遞下去

![](https://hackmd.io/_uploads/SJ8TlTsdn.png)

:question: ==那input:skipRegistration怎麼沒給?????==


## notify customer application created
:question: ==source expression表示的意思??== 通常會寫表示是吧?${}
![](https://hackmd.io/_uploads/HJ3eYTiun.png)

:question: ==和進入activity之後的input參數對不上???==
![](https://hackmd.io/_uploads/HyRb96jd3.png)

- hit policy: ==collect==
![](https://hackmd.io/_uploads/ryovi6oOh.png)

:question: ==要了解發生錯誤的時候怎麼進去handle process==
![](https://hackmd.io/_uploads/SkGFzRjd2.png)

:question: ==計算pause time的用意? 使用了amlAutoRejectDelegate==

:question: ==CompleteErrorDelegate為什麼是這樣運作? 請協助整個retry機制是怎麼運作的(包含元件使用)== 

==這個Event Listener Type 是 end 是哪裡的End????==
![](https://hackmd.io/_uploads/rkZInJndh.png)

```java=
@Component
@RequiredArgsConstructor
public class CompleteErrorDelegate implements JavaDelegate {

    private final CamundaRepository camundaRepository;

    @Override
    public void execute(DelegateExecution execution) throws JsonProcessingException {
        var errorId = (Long) execution.getVariable(ERROR_ID);
        if (errorId != null) {
            camundaRepository.finishedError(errorId);
        }
    }

}
```

:question: ==到底applicationStatus要設定為PAUSE 還是 PENDING???==
==感覺執行了兩次??==
![](https://hackmd.io/_uploads/rkkVAy3d3.png)
TPI: 到三角形是設定成Pause=>等待USER從UI觸發，再把狀態改成PENDING


## internal credit data
:::info
@FeignClient用法
- 啟用功能: 在起點要加上 @EnableFeignClients
![](https://hackmd.io/_uploads/SkF0MkVY3.png)

- 定義interface
![](https://hackmd.io/_uploads/ryUOeyEY3.png)
- 註冊config + 關聯 interface
![](https://hackmd.io/_uploads/BygcxkVY3.png)
![](https://hackmd.io/_uploads/BJ7x814F2.png)

- 實際使用方式
![](https://hackmd.io/_uploads/S1wLUJ4Y2.png)

:bulb: Feign的動態代理會根據你在接口上的 @RequestMapping 等註解，來動態構造出你要請求的服務的地址。

- Feign Logging 日誌記錄
    - 每一個被創建的Feign客戶端都會有一個feign.Logger 實例。該logger默認的名稱為Feign客戶端對應的接口的全限定名。==在本專案如何實作??==
    - ==這個是如何使用的???==
![](https://hackmd.io/_uploads/Bkd14yEKh.png)

:::

:question: ==執行stakeholder check有呼叫 InternalCreditDataClient 是如何連通?==
![](https://hackmd.io/_uploads/rk3MFe3_3.png)

==response format是什麼????==

![](https://hackmd.io/_uploads/rysvKehd3.png)


---



# Hank


Camunda Modeler : 
圖例: 
- CALL ACTIVITY : 
    - (column)Called element 的name對應到程式是BPMN的id
![](https://hackmd.io/_uploads/S1fqL2id3.png)


## create application
起點:
```java=
runtimeService.startProcessInstanceByKey(
    CREATE_APPLICATION_PROCESS_DEFINITION_KEY, 
    businessKey, 
    Map.of(CUSTOMER_ID, customerId, APPLICATION_ID, applicationId
));
```
與流程裡面的input對不上，這邊是怎麼把變數傳進去的? :question: 
![](https://hackmd.io/_uploads/S1bIepid2.png)

### Start spinner 是對哪件事情?
start-spinner-${execution.processBusinessKey}是指?
![](https://hackmd.io/_uploads/B1V5lTi_n.png)
![](https://hackmd.io/_uploads/B1iVWpjd3.png)

- 在Call ACTIVITY時，透過"In mappings"將變數傳入到流程中
 **Type**
    - Source: ??
    - Source expression: ??
![](https://hackmd.io/_uploads/BJtEDpjO3.png)



## notify customer application created
- 實際對應的BPMN是要看"Called element"
![](https://hackmd.io/_uploads/r1Ylnps_n.png)

- notification.bpmn傳入的值與create-application.bpmn對不上
- ![](https://hackmd.io/_uploads/H1T7Tasd3.png)

第一步計算pause time為甚麼是看AML 的時間(select aml_check_duration from dbo."configuration";)
TPI answer: 因為一開始通知是for AML才有的,後來因很多功能都要寄信，所以把它提出來變共用，現在不只為AML check服務，應該是當時沒有改名稱而已。
![](https://hackmd.io/_uploads/r1zeZWhuh.png)


:question:delegate 這個節點哪裡來?
![](https://hackmd.io/_uploads/ry86abnu3.png)



## internal credit data
看到environment.getProperty()的部分表示接了mock的資料
![](https://hackmd.io/_uploads/H1NW7Mndh.png)


**IN stakeholder-data.bpm**
response 是從上一個function 得來?
如何把response帶到此流程內?
![](https://hackmd.io/_uploads/H1tm8Ghuh.png)


解釋:應該是打別人API之後利用objectMapper轉型，且透過execution.setVariable(key, responseMap);方法將資料傳到流程內
![](https://hackmd.io/_uploads/BJ1LPGn_h.png)



:bulb: 搞懂SIGNAL INTERMEDIATE CATCH EVENT,TIMER BOUNDARY EVENT用法