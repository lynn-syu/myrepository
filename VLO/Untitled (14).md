[toc]

:question: 同步寄信api有使用嗎? 目前沒有，但未來不一定，所以先保留

## postApiMsgcrBAsyncsmso001 (POST) (/api/MSGCR-B-ASYNCSMSO001)
> 目的: Send a SMS Message ==Asynchronously==
![](https://hackmd.io/_uploads/H1c_y_fq2.png)

> call smsService.postApiMsgcrBAsyncsmso001
:::success
- 查Table: sms_msg 是不是有存在這筆資料(apid,txnseq)
:warning: 若存在有重複的資料則 throw new BadRequestException("MSGCR-B-ASYNNTFYO001-3", "duplicated-txnseq")
:question: 這個編號錯了!!!!! MSGCR-B-ASYNNTFYO001 :negative_squared_cross_mark: :negative_squared_cross_mark: :negative_squared_cross_mark: :negative_squared_cross_mark: :negative_squared_cross_mark: :negative_squared_cross_mark: :negative_squared_cross_mark: :negative_squared_cross_mark: 
和API名稱不相符˙
![](https://hackmd.io/_uploads/HyPviwD52.png)


- 根據sms req dto 去組合一筆資料 save data to table (sms_msg)

- 根據sms req dto 去組合一筆資料 call pubsubManager.publishAsyncSms()送簡訊
:bulb: topic: MSG-CENTER-ASYNC-SMS
:warning: 若寄送後回傳的messageId == null則 throw new InternalServerErrorException("MSGCR-B-ASYNNTFYO001-2", "backend-conn-abnormal")

- return SendSmsResponseDTO (僅有 MWHEADER 包含 traceId )
![](https://hackmd.io/_uploads/B1FYydM53.png)
:::

## postApiMsgcrBSsendsmso001 (POST) (/api/MSGCR-B-SSENDSMSO001)
> 目的: Send a SMS Message ==Synchronously==
![](https://hackmd.io/_uploads/ByEp4_f5h.png)

> call smsService.postApiMsgcrBSsendsmso001()
:::success
- 查Table: sms_msg 是不是有存在這筆資料(apid,txnseq)
:warning: 若存在有重複的資料則 throw new BadRequestException("MSGCR-B-SSENDSMSO001-5", "duplicated-txnseq")

- 直接 call twilioManager.sendSms() 
:warning: 若發送過程中發生 Exception則 throw new InternalServerErrorException("MSGCR-B-SSENDSMSO001-3", "twilio-handshake-error", e)

- 根據sms req dto 去組合一筆資料 save data to table (sms_msg)

- return sendSmsResponseDTO (僅有 MWHEADER 包含 traceId )
![](https://hackmd.io/_uploads/HyEyr_Mc3.png)
:::

## postApiMsgcrBQysmslogq001 (POST) (/api/MSGCR-B-QYSMSLOGQ001)
> 目的: Query the SMS by TXNSEQ

> call smsService.postApiMsgcrBQysmslogq001()
:::success
- 查Table: sms_msg 利用txnseq尋找
:warning: 若不存在則 throw new BadRequestException("MSGCR-B-QYSMSLOGQ001-1", "sms-msg-not-found")

- return smsQueryResponseDTO (有 MWHEADER 包含 traceId + TRANRS ~= sms_msg entity)
:warning: 程式沒寫好 這樣根本沒有回傳 TRANRS :question: :question: TPI修改
![](https://hackmd.io/_uploads/r1jqQ_Mq3.png)
:::

## MSG-CENTER-ASYNC-SMS(handler)
:::info
![](https://hackmd.io/_uploads/rkvGvdMch.png)
1.	messageId, message
2.	check if the messageId is processed from memstore 
    1.	memstore index: 0, key: {messageId}, value: bool
    2.	ignore the msg if processed
    3.	set value of the messageId as true if not processed
3.	return the result
4.	get sms msg by txnseq and apid
5.	return sms msg
6.	if sms msg not exist, write error log then ack pubsub
7.	send sms message by sendgrid
8.	success
9.	update sms msg
10.	success
11.	ack pubsub
:::