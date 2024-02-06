[toc]

## postApiMsgcrBAsynmailo001 (POST) (/api/MSGCR-B-ASYNMAILO001)
> 目的: Send a Mail ==Asynchronously==
![](https://hackmd.io/_uploads/rJI2kdf53.png)

> call emailService.postApiMsgcrBAsynmailo001()
::: success
- 檢查Table: mail_msg 是不是有存在這筆資料(apid,txnseq)
:warning: 若存在表示有重複則 throw new BadRequestException("MSGCR-B-ASYNMAILO001-4", "duplicated-txnseq")

- :warning: 特別的檢核: 不可以同時提供 contentTemplateCode 和 content 就會 throw new BadRequestException("MSGCR-B-ASYNMAILO001-1", "illegal-request-format")

- 針對request內容組出mail內容:
    - content可能依照templateCode去查找 template table 
        - 若找不到 throw new BadRequestException("MSGCR-B-ASYNMAILO001-3", "invalid-template-code")
        - 還會檢核該 template 是不是符合apid允許使用: throw new BadRequestException("MSGCR-B-ASYNMAILO001-2", "illegal-request-format")
    - contentParam 若轉換有問題 throw new BadRequestException("MSGCR-B-ASYNMAILO001-1", "illegal-request-format")
    - title也會依照template做轉換，若有問題 throw new BadRequestException("MSGCR-B-ASYNMAILO001-1", "invalid-template-code")

    - 若有附件檔案則 call cloudStorageService.saveFile()
    :bulb: cloud storage path: {msg-center-bucket}/mail-attachment/{apid}/{txnseq}

- save data to table:mail_msg

- 非同步寄信: createAsyncMailDTO() => call pubsubManager.publishAsyncMail()
:bulb: topic: MSG-CENTER-ASYNC-MAIL
:warning: 為什麼這裡不用判斷messageId是不是存在去處理exception????? :question: TPI純粹就沒判斷...

- return sendMailResponseDTO (僅 MWHEADER 包含 traceId)
![](https://hackmd.io/_uploads/HkkvluGq2.png)

:::

## postApiMsgcrBSendmailo001 (POST) (/api/MSGCR-B-SENDMAILO001)
> 目的: Send a Mail ==Synchronously==
![](https://hackmd.io/_uploads/B13AXOG9n.png)

> call emailService.postApiMsgcrBSendmailo001()
::: success
- 檢查Table: mail_msg 是不是有存在這筆資料(apid,txnseq)
:warning: 若存在表示有重複則 throw new BadRequestException("MSGCR-B-SENDMAILO001-4", "duplicated-txnseq")

- :warning: 特別的檢核: 不可以同時提供 contentTemplateCode 和 content 就會 throw new BadRequestException("MSGCR-B-SENDMAILO001-1", "illegal-request-format")

- 針對request內容組出mail內容:
    - content可能依照templateCode去查找 template table 
        - 若找不到 throw new BadRequestException("MSGCR-B-SENDMAILO001-3", "invalid-template-code")
        - 還會檢核該 template 是不是符合apid允許使用: throw new BadRequestException("MSGCR-B-SENDMAILO001-2", "illegal-request-format")
    - contentParam 若轉換有問題 throw new BadRequestException("MSGCR-B-SENDMAILO001-1", "illegal-request-format")

    - title也會依照template做轉換，若有問題 throw new BadRequestException("MSGCR-B-SENDMAILO001-1", "invalid-template-code")

- 直接呼叫 sendgridManager.sendMail()寄信: 中間有問題會丟 Problem - INTERNAL_SERVER_ERROR :question:  :question: 

- save data to table: mail_msg

- 若有附件檔案則 call cloudStorageService.saveFile()
:bulb: cloud storage path: {msg-center-bucket}/mail-attachment/{apid}/{txnseq}

- return sendMailResponseDTO (僅 MWHEADER 包含 traceId)
![](https://hackmd.io/_uploads/Hk6MEdG5n.png)

:::

## postApiMsgcrBQymaillgq001 (POST) (/api/MSGCR-B-QYMAILLGQ001)
> 目的: Query the Mail by TXNSEQ
![](https://hackmd.io/_uploads/ry1gfOM52.png)

> call emailService.postApiMsgcrBQymaillgq001()
:::success
- 查Table: mail_msg 利用txnseq尋找
:warning: 找不到則 throw new BadRequestException("MSGCR-B-QYMAILLGQ001-1", "mail-msg-not-found")

- return mailQueryResponseDTO (有 MWHEADER 包含 traceId + TRANRS ~= mailMsg Entity)
![](https://hackmd.io/_uploads/BJdFGdz9n.png)
:::

## MSG-CENTER-ASYNC-MAIL(handler)
:::info
![](https://hackmd.io/_uploads/SymjIOzq3.png)
1.	messageId, message
2.	check if the messageId is processed from memstore 
    1.	memstore index: 0, key: {messageId}, value: bool
    2.	ignore the msg if processed
    3.	set value of the messageId as true if not processed
3.	return the result
4.	get mail msg by txnseq and apid
5.	return mail msg
6.	if mail msg not exist, write error log then ack pubsub
7.	get template if templateCode is not null
8.	return template
9.	get attachments from cloud storage if attachment field is not null
10.	attachments
11.	send mail by sendgrid
12.	success
13.	update mail msg
14.	success
15.	ack pubsub
:::
