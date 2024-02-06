Telco DataNest (Mobifone)
=== 

## Testing Phone List
- 0930010371 ~ 0930010379
- 0930010380 ~ 0930010385


## Env Info

| Field/Env     | UT                                   | PROD                       |
| ------------- | ------------------------------------ | -------------------------- |
| URL           | https://api-test.datanest.vn/v2      | https://api.datanest.vn/v2 |
| client_code     | CATHAY_BANK | null                       |
| client_secret | 80587d7d6e610b0958f5d17461250f1f | null                       |

## API List

| Method | Endpoint             | Usage      |
| ------ | -------------------- | ------------------ |
| Post   | {env_url}/token          | Get Token          |
| Post   | {env_url}/r/otp/send     | Send OTP           |
| Post   | {env_url}/r/otp/verify   | Verify OTP         |
| Post   | {env_url}/r/credit/query | Query Credit Score |
| Post   | {env_url}/r/fraud/query  | Query Fraud Score  |

___

### Get Token

- API Information

| Name         | Value            |
| ------------ | ---------------- |
| URI          | {env_url}/token  |
| Method       | POST             |
| Content-Type | application/json 

> Request [color=#39E32E]
- Body
```json
{
  "client_code": {client_id},
  "client_secret": {client_secret}
}
```

- Body Schema

| Name            | Type   | Required | Title    | Description           |
| --------------- | ------ | -------- | -------- | --------------------- |
| » client_code   | string | true      | 用戶代碼 | ID of client.         |
| » client_secret | string | true      | 用戶密碼 | Secret key of client. |


> Response [color=#5DADE2]
- Body Examples
```json
{
    "error": {
        "code": 200,
        "message": "Success"
    },
    "data": {
        "access_token": "eyJhbGciOiJIUzI1NiIsI...",
        "token_type": "Bearer",
        "expires_in": 1800
    }
}
```

- Body Schema

| Name            | Type    | Required | Title        | description              |
| --------------- | ------- | -------- | ------------ | ------------------------ |
| » error         | object  | true     |              |                      |
| »» code         | integer | true     | 錯誤代碼     |  [Error Code.](#Error-Code)            |
| »» message      | string  | true     | 錯誤訊息     | [Error Message.](#Error-Code)           |
| » data          | object  | true     |              |                      |
| »» access_token | string  | false    | 存取權杖     | Token.                   |
| »» token_type   | string  | false    | 權杖類型     | Type of token.           |
| »» expires_in   | integer | false    | 有效時間(秒) | Expires time in seconds. |

---

### Send OTP

- API Information

| Name         | Value                |
| ------------ | -------------------- |
| URI          | {env_url}/r/otp/send |
| Method       | POST                 |
| Content-Type | application/json     |

> Request [color=#39E32E]

- Header

| Name          | Value                 |
| ------------- | --------------------- |
| Authorization | Bearer {access_token} |


- Body
```json
{
  "request_id": "a47b9c69-0c10-4211-a175-b22331810e1f",
  "phone_number": "0912345678",
  "client_otp":"XXXX"
}
```

- Body Schema

| Name           | Type   | Required | Title    | Description            |
| -------------- | ------ | -------- | -------- | ---------------------- |
| » request_id   | string | true     | 請求編號 | Unique ID for request. |
| » phone_number | string | true     | 手機號碼 | Phone number.          |
| » client_otp   | string | true     | OTP      | OTP.                   |

> Response [color=#5DADE2]
- Body Examples
```json
{
    "error": {
        "code": 0,
        "message": "Success"
    },
    "data": {
        "id": "14259079386775610831",
        "telco": "mobifone"
    }
}
```

- Body Schema

| Name       | Type    | Required | Title    | description                                  |
| ---------- | ------- | -------- | -------- | -------------------------------------------- |
| » error    | object  | true     |          |                                              |
| »» code    | integer | true     | 錯誤代碼 | [Error Code.](#Error-Code)                   |
| »» message | string  | true     | 錯誤訊息 | [Error Message.](#Error-Code)                |
| » data     | object  | true     |          |                                              |
| »» id      | string  | false    | 請求編號 | ID of request used for audit and debug.      |
| »» telco   | string  | false    | 對應電信 | Current telco operator of this phone number. |

---

### Verify OTP

- API Information

| Name         | Value            |
| ------------ | ---------------- |
| URI          | {env_url}/r/otp/verify|
| Method       | POST             |
| Content-Type | application/json |

> Request [color=#39E32E]

- Header

| Name         | Value            |
| ------------ | ---------------- |
| Authorization          | Bearer {access_token}  |

- Body
```json
{
    "request_id": "a47b9c69-0c10-4211-a175-b22331810e1f",
    "phone_number": "0912345678",
    "otp": "12345678"
}
```

- Body Schema

| Name           | Type   | Required | Title     | Description            |
| -------------- | ------ | -------- | --------- | ---------------------- |
| » request_id   | string | true      | 請求編號  | Unique ID for request. |
| » phone_number | string | true      | 手機號碼  | Phone number.          |
| » otp          | string | true      | OTP驗證碼 | OTP.                   |

> Response [color=#5DADE2]
- Body Examples
```json
{
    "error": {
        "code": 0,
        "message": "Success, OTP code is verified"
    },
    "data": {
        "id": "1234",
        "telco": "viettel"
        "epxire_time": "2019-09-11",
        "available_product": [
            "grade_score",
            "credit_score"
        ]
    }
}
```

- Body Schema

| Name       | Type    | Required | Title    | description                                  |
| ---------- | ------- | -------- | -------- | -------------------------------------------- |
| » error    | object  | true     |          |                                              |
| »» code    | integer | true     | 錯誤代碼 | [Error Code.](#Error-Code)                   |
| »» message | string  | true     | 錯誤訊息 | [Error Message.](#Error-Code)                |
| » data     | object  | true     |          |                                              |
| »» id      | string  | false    | 請求編號 | ID of request used for audit and debug.      |
| »» telco   | string  | false    | 對應電信 | Current telco operator of this phone number. |
| »» epxire_time      | string  | false    | 到期時間 | Expire time of this consent.      |
| »» available_product   | array[string]  | false    | 可使用的功能清單 | Customer consent are avaiable for these types of products. |

---


### Query Credit Score

- API Information

| Name         | Value            |
| ------------ | ---------------- |
| URI          | {env_url}/r/credit/query|
| Method       | POST             |
| Content-Type | application/json |

> Request [color=#39E32E]

- Header

| Name         | Value            |
| ------------ | ---------------- |
| Authorization          | Bearer {access_token}  |

- Body
```json
{
    "request_id": "a47b9c69-0c10-4211-a175-b22331810e1f",
    "phone_number": "0912345678"
}
```

- Body Schema

| Name           | Type   | Required | Title     | Description            |
| -------------- | ------ | -------- | --------- | ---------------------- |
| » request_id   | string | true      | 請求編號  | Unique ID for request. |
| » phone_number | string | true      | 手機號碼  | Phone number.          |

> Response [color=#5DADE2]
- Body Examples
```json
{
    "error": {
        "code": 0,
        "message": "Success"
    },
    "data": {
        "id": "123141",
        "credit_score": 821,
        "client_count_30": 4,
        "telco": "viettel"
    }
}
```

- Body Schema

| Name               | Type    | Required | Title          | description                                                                                    |
| ------------------ | ------- | -------- | -------------- | ---------------------------------------------------------------------------------------------- |
| » error            | object  | true     |                |                                                                                                |
| »» code            | integer | true     | 錯誤代碼       | [Error Code.](#Error-Code)                                                                     |
| »» message         | string  | true     | 錯誤訊息       | [Error Message.](#Error-Code)                                                                  |
| » data             | object  | true     |                |                                                                                                |
| »» id              | string  | false    | 請求編號       | ID of request used for audit and debug.                                                        |
| »» credit_score    | integer | false    | 信用評價分數   | Credit Score ( 0, or 300-850 ).                                                                |
| »» client_count_30 | integer | false    | 近30天查詢次數 | How many time this phone has been requested last 30 days.                                      |
| »» telco           | string  | false    | 對應電信       | Current operating telco of this phone number.consent are avaiable for these types of products. |

---


### Query Fraud Score

- API Information

| Name         | Value                   |
| ------------ | ----------------------- |
| URI          | {env_url}/r/fraud/query |
| Method       | POST                    |
| Content-Type | application/json        |

> Request [color=#39E32E]

- Header

| Name          | Value                 |
| ------------- | --------------------- |
| Authorization | Bearer {access_token} |

- Body
```json
{
    "request_id": "a47b9c69-0c10-4211-a175-b22331810e1f",
    "phone_number": "0912345678"
}
```

- Body Schema

| Name           | Type   | Required | Title     | Description            |
| -------------- | ------ | -------- | --------- | ---------------------- |
| » request_id   | string | true      | 請求編號  | Unique ID for request. |
| » phone_number | string | true      | 手機號碼  | Phone number.          |

> Response [color=#5DADE2]
- Body Examples
```json
{
    "error": {
        "code": 0,
        "message": "Success"
    },
    "data": {
        "id": "911",
        "fraud_score": 400,
        "fraud_level": "high",
        "client_count_30": 4,
        "telco": "viettel"
    }
}
```

- Body Schema

| Name               | Type    | Required | Title          | description                                                                                    |
| ------------------ | ------- | -------- | -------------- | ---------------------------------------------------------------------------------------------- |
| » error            | object  | true     |                |                                                                                                |
| »» code            | integer | true     | 錯誤代碼       | [Error Code.](#Error-Code)                                                                     |
| »» message         | string  | true     | 錯誤訊息       | [Error Message.](#Error-Code)                                                                  |
| » data             | object  | true     |                |                                                                                                |
| »» id              | string  | false    | 請求編號       | ID of request used for audit and debug.                                                        |
| »» fraud_score     | integer | false    | 詐欺評價分數   | fraud_score of isdn.                                                                |
| »» fraud_level     | string  | false    | 詐欺等級 | fraud_level of isdn.                                      |
| »» client_count_30 | integer | false    | 近30日查詢次數 | How many requests was made in the last 30 days for this isdn.                                      |
| »» telco           | string  | false    | 對應電信       | Current telco operator of this phone number. |

---


## Error Code

| Value | Message                               |
| ----- | ------------------------------------- |
| 0     | Success                               |
| 1     | Invalid phone number                  |
| 2     | Not supported telco / Currently Unsupported Telco                   |
| 3     | Duplicate Request                     |
| 4     | Invalid OTP                     |
| 5     | Internal Server Error                     |
| 8     | Maximum references exceeded           |
| 9     | Invalid province code                 |
| 10    | Invalid district code                 |
| 11    | Invalid Reference                     |
| 13    | Fraud data not yet ready.             |
| 16    | Request not allowed: no consent       |
| 17    | Request not allowed: other reason     |
| 18    | Invalid National ID                   |
| 20    | MNP Error                             |
| 22    | Invalid Client OTP format.            |
| 401   | Invalid client code or client secret. |
| 500   | Internal Server Error.                |