# Document-template
:::warning
- 觸發流程: backoffice-api => DocumentTemplateController => uploadDocumentTemplate => runtimeService.startProcessInstanceByKey()
    - Key: ==DocumentTemplateProcess== => mapping to bpmn process id
    - businessKey: "document_template_" + ${documentTemplateId} 
    - Context Variable: 
        - **"documentTemplateId"**: ${documentTemplateId} => mapping to document_template table entity PK 
        - **"startsAt"**: ${startsAt} => 生效時間
:::
:::info
- bpmn process
1. STAT EVENT 
2. SERVICE TASK-Generate document preview: 呼叫 GenerateDocumentPreviewDelegate.java
    - 查db: document_template
    - ==呼叫 Itext Service==: docx to pdf
3. TIMER INTERMEDIATE CATCH EVENT-Wait Until Start Time Event: 等待到生效時間
4. SERVICE TASK-Mack document template active: 呼叫 DocumentTemplateActivationDelegate.java
    - 查db: document_template
5. END EVENT   

![](https://hackmd.io/_uploads/S1hWxE0ih.png)
:::


# Create-application

