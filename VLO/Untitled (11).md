[toc]

## postMsgcrBAsynntfyo001 (POST) (/api/MSGCR-B-ASYNNTFYO001)
> 目的: Push a Notification Message ==Asynchronously==
![](https://hackmd.io/_uploads/ryJKgOf9n.png)

> notificationService.postMsgcrBAsynntfyo001()
:::success
- 檢查Table: notification_msg 是不是有存在這筆資料(apid,txnseq)
:warning: 若存在表示有重複則 throw new BadRequestException("MSGCR-B-ASYNNTFYO001-3", "duplicated-txnseq")

- 透過request資料組出一包notificationMsgDAO並存入DB_Table:notification_msg

- call pubsubManager.publishAsyncNotification() 去推送通知
:bulb: ==topic: MSG-CENTER-ASYNC-NOTIFICATION?????? 文件沒寫==
:warning: 若寄送後回傳的messageId == null則 throw new InternalServerErrorException("MSGCR-B-ASYNNTFYO001-2", "backend-conn-abnormal")

- return SendNotificationResponseDTO (僅 MWHEADER 包含 traceId )
![](https://hackmd.io/_uploads/BJcixdGc2.png)
:::


## postMsgcrBSendntfyo001 (POST) (/api/MSGCR-B-SENDNTFYO001)
> 目的: Push a Notification Message ==Synchronously==
![](https://hackmd.io/_uploads/rJvNN_fq2.png)

> notificationService.postMsgcrBSendntfyo001
:::success
- 檢查Table: notification_msg 是不是有存在這筆資料(apid,txnseq)
:warning: 若存在表示有重複則 throw new BadRequestException("MSGCR-B-SENDNTFYO001-5", "duplicated-txnseq")

- 取得device token: 利用feignClient呼叫Data-Centralized 的 deviceTokenApiClient
:warning: 過程中有任何錯誤 throw new InternalServerErrorException("MSGCR-B-SENDNTFYO001-2", "backend-conn-abnormal")

- 直接 call firebaseManager.pushNotification() 去推送通知
:warning: 過程中有任何錯誤 throw new InternalServerErrorException("MSGCR-B-SENDNTFYO001-3", "firebase-handshake-error", e)

- call pubsubManager.publishNotificationSuccess() 告知推送通知成功
:warning: 過程中有任何錯誤 throw new InternalServerErrorException("MSGCR-B-SENDNTFYO001-4", "pub-sub-error", e)
:warning: 若寄送後回傳的messageId == null則 throw new InternalServerErrorException("MSGCR-B-SENDNTFYO001-4", "pub-sub-error", e)

- 將資料紀錄在Db_Table: notification_msg 
 
- return SendNotificationResponseDTO (僅 MWHEADER 包含 traceId )
![](https://hackmd.io/_uploads/rJgPNdMcn.png)
:::

## postApiMsgcrBQyntfylgq001 (POST) (/api/MSGCR-B-QYNTFYLGQ001)
> 目的: Query the Notification by TXNSEQ

> notificationService.postApiMsgcrBQyntfylgq001
:::success
- 查Table: notification_msg 利用txnseq尋找
:warning: 若不存在則 throw new BadRequestException("MSGCR-B-QYNTFYLGQ001-1", "notification-msg-not-found")

- return NotificationQueryResponseDTO (有 MWHEADER 包含 traceId + TRANRS ~= notification_msg entity)
![](https://hackmd.io/_uploads/BJRMmdMcn.png)
:::

## MSG-CENTER-ASYNC-NOTIFICATION(handler)
:::info
:question: 這個cif (呼叫data-centralized) 沒有拿來使用?
![](https://hackmd.io/_uploads/rkKvltvqh.png)


![](https://hackmd.io/_uploads/BJzlD_f93.png)
1.	messageId, message
2.	check if the messageId is processed from memstore 
    1.	memstore index: 0, key: {messageId}, value: bool
    2.	ignore the msg if processed
    3.	set value of the messageId as true if not processed
3.	return the result
4.	get notification msg by txnseq and apid
5.	return notification msg
6.	if notification msg not exist, write error log then ack pubsub
7.	get device token from dc
8.	return device token
9.	send notification by firebase
10.	success
11.	notify pub/sub that notification sent successfully
12.	return the result
13.	update notification msg
14.	success
15.	ack pubsub
:::