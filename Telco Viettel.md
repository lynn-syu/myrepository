Telco Viettel
=== 

## Testing Phone List
- 0343254990

---
## Env Info

| Field/Env     | UT                                   | PROD |
| ------------- | ------------------------------------ | ---- |
| URL           | https://api17.viettelpay.vn          | null |
| client_id     | da1a46d7-5fea-4874-aa59-e6bb33320d3a | null |
| client_secret | d197917b-3377-4ab0-b9d9-6df8b302c4d3 | null |
| username      | cub                                  | null |
| password      | $sf8v)3Pn%D&                         | null 

### Data Nest RSA UT Public Key
```java
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvyzWhmi2VW4BtBnLe9Z75wbJUlobsb1Y1RpDOlot843CTqhPG7N8VePNW2HTKKGNgyPaHbrKtSetoTdx40rFyqPBV5I6UsmdWd0NVadYYQMFKh5CBw7SgUbhrhOktHfDbTr9F7mqjEyUtNC7iL78emdjjwWfKfzSTg4URJxvg+glD/K2EvORCK5S8NxqUPqzyyOcBn9YEmZ6kGs55XyEf7YyGz6Zj5nSeELD5/YM1YJYpOW2TZu31EH5l/JZVf3yN0f97PZKboSPwubZf14sgbS633Ed7cLDieTfyHS7+KEckrcUAn5slt4lXHAJwXTkFfv506HUC/ETEVjXZDevwwIDAQAB
```

### Data Nest RSA UT Private Key
```java
MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC/LNaGaLZVbgG0Gct71nvnBslSWhuxvVjVGkM6Wi3zjcJOqE8bs3xV481bYdMooY2DI9odusq1J62hN3HjSsXKo8FXkjpSyZ1Z3Q1Vp1hhAwUqHkIHDtKBRuGuE6S0d8NtOv0XuaqMTJS00LuIvvx6Z2OPBZ8p/NJODhREnG+D6CUP8rYS85EIrlLw3GpQ+rPLI5wGf1gSZnqQaznlfIR/tjIbPpmPmdJ4QsPn9gzVglik5bZNm7fUQfmX8llV/fI3R/3s9kpuhI/C5tl/XiyBtLrfcR3twsOJ5N/IdLv4oRyStxQCfmyW3iVccAnBdOQV+/nTodQL8RMRWNdkN6/DAgMBAAECggEAAMfbWnzdf/lKztvrN/53kSNcsoM/ZIUDk8emkI6pIFdX7ogTpUq4pNFxs/uIuxGsC4czXChQFTusBoMbhovxn2z2lWJfj30P4LJKUtXQVZ35K3/5BX+wvNJpgHurBAArRwY/m2D5UIHNeoxKoT5UDG8s1ipi5ZBU5B6iv0sz87+kw89fdWhTnYElmGuVFv/ybzHHiYhhSzOF70F9oGQ/jx0UakavzonrsT7li6z3Z1eq8c7cYQ++1BeRjG8in8MixB2Eb2iTaCGPyf7Kb6l2Xw/0nAzEaHoif3Z4oL6HNi+RNscgfsEYRb85EzPdYR9NhxRcoI+RlcoVO5dbblTnAQKBgQDc/xIhWSDVYn/zYAa8Zv8C2pJNlJN0FON2Cc7Ec9iSk3VidW42lGevLi/DmJXyVUweugVuMBcMVboOAeqV1X6/ceMVp07RK3o13G48L9ib/RHNo0mU4ZChqoLHlBJYfg3sLS0I/HAP4WSUpenHBOxtUbeHvIjOSsEpadfbJ2ywQQKBgQDddJSdeVZ3b3XgguIfXPs9ju/oJngH9clab8dVGtIedqj+diMjGiMJhBurBQ3xG/1WnonfLp3twfWqHP4/s/R0UemWrq/DaKjJ/e+8DctTOO8OP5WzyaOzpwwhmXOmCZheZ+fXoCIj/7fE842RpWtbwmOee/MwGjcnd+REhinfAwKBgGpsSwbULQOVU5iX/aZs0xFEwH+rtPaJuJ/3SSNBxmGPsU+gzVprTSMHUukWad8xRF7npgXSEsdV4SstsRJGpar75SdgUoxYK5oUdOF3CRDVZeVpZN3VYhj4S9JgdBU5XusFNlLFfhGnsRAEdffpXxGl8IPlPtxZE3wmBFT9P1IBAoGBAJWV5bzo9Qr/8vOP9QWSXPKb1llNgs59q2C3GEHYOA3zwPsA+YMAaKJI5heC9oPikCEuPZrPdkXfOf2KUALfn0PjAJGTtY2c6EZojMLN3oV8c52APbKPhVAYig04BfIlkRJTxTrVI7qFRLB4gyvaPMZia7/locLgMlkqlrnile3ZAoGBAIZQPwYSfVtgMPMGqzJuVQVBTvTc9sSjR/tEqTza7rvsHDdr+9h+tshAIDklc/BZORRzZhYD8xWLi5tu8A96yNU6oUuIOwqBrLDGNKAJecnjJzyQF9oYYWGhoLmarlK9qaDlYPcoT7dtzQYNjy/eGpv9RBq7eqjc717BSnteBRmY
```

---

## Encrypt and Decode

- Viettel 在安全性上，要求特定API需要加密特定欄位，且回應需要自行解密。
- Viettel 提供Get RSA Key，提供具有時效性Session Id & RSA Key。
- 我方自行產生 Secret Key，透過 Viettel 的 RSA Key 加密後傳給 Viettel。
- 後續溝通藉由 Session Id 認證與 AES Key 加密特定內容呼叫 Viettel API。
- Query Credit / Fraud Score分數，要用 Data Nest RSA Private Key 解密。
- 有需要加解密的API，在API List上有特別標註。

### Lead in process for encryption/decryption


```sequence
Telco API->Viettel: Get Token /oauth/token  
Note over Viettel: Check id, secret
Viettel->Telco API: Response token

Telco API->Viettel: Get RSA Key
Note over Viettel: Check token
Viettel->Telco API: Response session id, RSA Key

Telco API->Viettel: Set AES Key
Note over Viettel: Verify AES Key Spec
Viettel->Telco API: Response success

```

### AES Example Code
```java=
import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.StandardCharsets;

import java.util.Base64;

String secret = "這邊由CUB定義，可每次產生 or 固定，且 RSA 加密此文字給Viettel";
SecretKeySpec secretKey;

// 產生AES Key
public void AES(String secret) {
    try {
        byte[] key = secret.getBytes(StandardCharsets.UTF_8);
	MessageDigest sha = MessageDigest.getInstance("SHA-256");
	key = sha.digest(key);
	key = Arrays.copyOf(key, 16);
	secretKey = new SecretKeySpec(key, "AES");
    }catch (NoSuchAlgorithmException ex){
        // do ...
    }
}

// 加密
public String encrypt(String data){
    String output = null;
    
    if (secretKey != null){
        try{
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            
            output = Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes("UTF-8")));
            
        } catch(Exception ex){
            // do ...
        }
    }
    
    return output;
}

// 解密
public String decrypt(String data){
       String output = null;
    
    if (secretKey != null){
        try{
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            
            output = new String(cipher.doFinal(Base64.getDecoder().decode(data)), StandardCharsets.UTF_8);
            
        } catch(Exception ex){
            // do ...
        }
    }
    
    return output;
}

```

### RSA Public Key Encrypt Example Code
```java=
import javax.crypto.spec.SecretKeySpec;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Hex;

String RSA_Public_Key = "由get-rsa-key取得的key";
String secret = "這邊由CUB定義，可每次產生 or 固定，且 RSA 加密此文字給Viettel";

public String encryptRSA(){
    String output = null;
    
    try {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(RSA_Public_Key));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey rsaPublicKey = keyFactory.generatePublic(keySpec);

		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);

        // 獲得透過RSA Public Key 加密後的 AES secret Key
		output = Hex.encodeHexString(cipher.doFinal(secret.getBytes()));        
    } catch(Exception ex) {
        // do ...
    }
    
    return output;
}

```

### RSA Private Key Decrypt Example Code
```java=
import javax.crypto.spec.SecretKeySpec;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Hex;

String RSA_private_Key = "Data Nest RSA UT Private Key";
String data = "Viettel查詢分數的Base64值";

public String decryptRSA(){
    String output = null;
    
    try {
        KeyFactory kf = KeyFactory.getInstance("RSA");
		rsaPrivateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(RSA_private_Key)));
        
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
        
		byte[] decrypt = cipher.doFinal(Base64.getDecoder().decode(data));

		output = new String(decrypt);
    } catch(Exception ex) {
        // do ...
    }
    
    return output;
}

```

---

## API List

| Method | Endpoint                                             | Usage              | Encryption         |
| ------ | ---------------------------------------------------- | ------------------ | ------------------ |
| Post   | {env_url}/vds_score_uat/api/v1/oauth/token               | Get Token          |                    |
| Post   | {env_url}/vds_score_uat/api/v1/common/get-rsa-key        | Get RSA Public Key |                    |
| Post   | {env_url}/vds_score_uat/api/v1/common/set-aes-key        | Set AES Key        |                    |
| Post   | {env_url}/vds_score_uat/api/v1/common/sent-consent-otp   | Send OTP           | :heavy_check_mark: |
| Post   | {env_url}/vds_score_uat/api/v1/common/submit-consent-otp | Verify OTP         | :heavy_check_mark: |
| Post   | {env_url}/vds_score_uat/api/v1/credit/get-score          | Query Credit Score | :heavy_check_mark: |
| Post   | {env_url}/vds_score_uat/api/v1/fraud/get-score           | Query Fraud Score  | :heavy_check_mark: |

___

### Get Token

- API Information

| Name         | Value                                  |
| ------------ | -------------------------------------- |
| URI          | {env_url}/vds_score_uat/api/v1/oauth/token |
| Method       | POST                                   |
| Content-Type | application/x-www-form-urlencoded      |

> Request [color=#39E32E]

- Body

```yaml
grant_type: password
client_id: "{client_id}"
client_secret: "{client_secret}"
username: "{username}"
password: "{password}"
```

- Body Schema

| Name            | Type   | Required | Title    | Description                   |
| --------------- | ------ | -------- | -------- | ----------------------------- |
| » grant_type    | string | true      | 類型     | fix = “password”              |
| » client_id     | string | true      | 用戶代碼 | client_id provided by VDS     |
| » client_secret | string | true      | 用戶密鑰 | client_secret provided by VDS |
| » username      | string | true      | 用戶名稱 | username provided by VDS      |
| » password      | string | true      | 用戶密碼 | password provided by VDS      |

> Response [color=#5DADE2]
- Body Examples
```json
{
    "access_token": "69626784-4b73-4de7-941e-0bb205e98437",
    "token_type": "bearer",
    "refresh_token": "10464af4-aa61-4bcc-8fe0-05eb21f2e613",
    "expires_in": 1799,
    "scope": "trust read write"
}
```

- Body Schema

| Name                | Type    | Required | Title        | description                     |
| ------------------- | ------- | -------- | ------------ | ------------------------------- |
| » error             | string  | false    | 錯誤         |                                 |
| » error_description | string  | false    | 錯誤訊息     | [Error Message.](#Error-Code)   |
| » access_token      | string  | false    | 存取權杖     | Token.                          |
| » token_type        | string  | false    | 權杖類型     | Type of token.                  |
| » refresh_token     | string  | false    | 刷新權杖     | This feature has been disabled. |
| » expires_in        | integer | false    | 有效時間(秒) | Expires time in seconds.        |

---

### Get RSA Key

- API Information

| Name         | Value                                             |
| ------------ | ------------------------------------------------- |
| URI          | {env_url}/vds_score_uat/api/v1/common/get-rsa-key |
| Method       | POST                                              |
| Content-Type | application/json                                  |

> Request [color=#39E32E]

- Header

| Name          | Value                 |
| ------------- | --------------------- |
| Authorization | Bearer {access_token} |

- Body
```json
{
  "client_id": "{{client_id}}"
}
```

- Body Schema

| Name           | Type   | Required | Title    | Description            |
| -------------- | ------ | -------- | -------- | ---------------------- |
| » client_id   | string | true      | 用戶端編號 | client_id provided by VDS. |

> Response [color=#5DADE2]
- Body Examples
```json
{
  "errorCode": 0,
  "description": "",
  "contentType": 1,
  "content": {
    "session": "f8ad74e7-d5e3-4c1c-9f73-9f161a7cdc03",
    "key": "MIIBIjANBgk..."
  }
}
```

- Body Schema

| Name          | Type    | Required | Title        | description                   |
| ------------- | ------- | -------- | ------------ | ----------------------------- |
| » errorCode   | integer | true     | 錯誤代碼     | [Error Code.](#Error-Code)    |
| » description | string  | true     | 錯誤訊息     | [Error Message.](#Error-Code) |
| » contentType | integer | true     | 回應內容類型 | Type of return content        |
| » content     | object  | true     | 內容         | Return content                |
| »» session    | string  | true     | 期間編號     | sessionId.                    |
| »» key        | string  | true     | RSA公鑰      | RSA Public Key.               |

---

### Set AES Key

- API Information

| Name         | Value                                             |
| ------------ | ------------------------------------------------- |
| URI          | {env_url}/vds_score_uat/api/v1/common/set-aes-key |
| Method       | POST                                              |
| Content-Type | application/json                                  |

> Request [color=#39E32E]

- Header

| Name          | Value                 |
| ------------- | --------------------- |
| Authorization | Bearer {access_token} |

- Body
```json
{
  "sessionId": "{{from get rsa key session}}",
  "content": "{{encrypt AES secret Key by RSA Public Key}}"
}
```

- Body Schema

| Name        | Type   | Required | Title    | Description        |
| ----------- | ------ | -------- | -------- | ------------------ |
| » sessionId | string | true      | 期間編號 | sessionId.         |
| » content   | string | true      | 內容     | AES密鑰加密後內容. |

> Response [color=#5DADE2]
- Body Examples
```json
{
    "errorCode": 0,
    "description": "",
    "contentType": 0,
    "content": "ready to start"
}
```

- Body Schema

| Name          | Type    | Required | Title        | description                   |
| ------------- | ------- | -------- | ------------ | ----------------------------- |
| » errorCode   | integer | true     | 錯誤代碼     | [Error Code.](#Error-Code)    |
| » description | string  | true     | 錯誤訊息     | [Error Message.](#Error-Code) |
| » contentType | integer | true     | 回應內容類型 | Type of return content        |
| » content     | string  | true     | 內容         | Return content                |

---

### Send OTP

- API Information

| Name         | Value                                                  |
| ------------ | ------------------------------------------------------ |
| URI          | {env_url}/vds_score_uat/api/v1/common/sent-consent-otp |
| Method       | POST                                                   |
| Content-Type | application/json                                       |

> Request [color=#39E32E]

- Header

| Name          | Value                 |
| ------------- | --------------------- |
| Authorization | Bearer {access_token} |

- Body
```json
{
  "sessionId": "{{from get rsa key session}}",
  "content": "{{encrypt by AES secret Key string, refer Content desc below.}}"
}
```

- Content
```json=
{
    "msisdn": "84XXXXXX", // 手機號碼
    "otpPrefix":"XXXX" // OTP碼
}
```

- Body Schema

| Name         | Type   | Required | Title           | Description            |
| ------------ | ------ | -------- | --------------- | ---------------------- |
| » sessionId  | string | true     | 期間編號        | sessionId.             |
| » content    | string | true     | 內容            | AES密鑰加密後內容.     |
| »» msisdn    | string | true     | 接收OTP手機號碼 | OTP receiver’s msisdn. |
| »» otpPrefix | string | true     | OTP             | OTP                    |

> Response [color=#5DADE2]
- Body Examples
```json
{
    "errorCode": 0,
    "description": "success",
    "contentType": 0
}
```

- Body Schema

| Name          | Type    | Required | Title        | description                   |
| ------------- | ------- | -------- | ------------ | ----------------------------- |
| » errorCode   | integer | true     | 錯誤代碼     | [Error Code.](#Error-Code)    |
| » description | string  | true     | 錯誤訊息     | [Error Message.](#Error-Code) |
| » contentType | integer | true     | 回應內容類型 | Type of return content        |

---

### Verify OTP

- API Information

| Name         | Value                                                    |
| ------------ | -------------------------------------------------------- |
| URI          | {env_url}/vds_score_uat/api/v1/common/submit-consent-otp |
| Method       | POST                                                     |
| Content-Type | application/json                                         |

> Request [color=#39E32E]

- Header

| Name          | Value                 |
| ------------- | --------------------- |
| Authorization | Bearer {access_token} |

- Body
```json
{
  "sessionId": "{{from get rsa key session}}",
  "content": "{{encrypt by AES secret Key string, refer Content desc below.}}"
}
```

- Content
```json=
{
    "msisdn": "84XXXXXX", // 手機號碼
    "otp": "XXXXXX" // OTP 驗證碼
}
```

- Body Schema

| Name        | Type   | Required | Title           | Description            |
| ----------- | ------ | -------- | --------------- | ---------------------- |
| » sessionId | string | true      | 期間編號        | sessionId.             |
| » content   | string | true      | 內容            | AES密鑰加密後內容.     |
| »» msisdn   | string | true      | 接收OTP手機號碼 | OTP receiver’s msisdn. |
| »» otp      | string | true      | OTP號碼         | OTP value.             |

> Response [color=#5DADE2]
- Body Examples
```json
{
    "errorCode": 0,
    "description": "success",
    "contentType": 0
}
```

- Body Schema

| Name          | Type    | Required | Title        | description                   |
| ------------- | ------- | -------- | ------------ | ----------------------------- |
| » errorCode   | integer | true     | 錯誤代碼     | [Error Code.](#Error-Code)    |
| » description | string  | true     | 錯誤訊息     | [Error Message.](#Error-Code) |
| » contentType | integer | true     | 回應內容類型 | Type of return content        |

---

### Query Credit Score

- API Information

| Name         | Value                                           |
| ------------ | ----------------------------------------------- |
| URI          | {env_url}/vds_score_uat/api/v1/credit/get-score |
| Method       | POST                                            |
| Content-Type | application/json                                |

> Request [color=#39E32E]

- Header

| Name          | Value                 |
| ------------- | --------------------- |
| Authorization | Bearer {access_token} |

- Body
```json
{
  "sessionId": "{{from get rsa key session}}",
  "content": "{{encrypt by AES secret Key string, refer Content desc below.}}"
}
```

- Content
```json=
{
    "msisdn": "84XXXXXX" // 手機號碼
}
```

- Body Schema

| Name        | Type   | Required | Title        | Description                 |
| ----------- | ------ | -------- | ------------ | --------------------------- |
| » sessionId | string | true      | 期間編號     | sessionId.                  |
| » content   | string | true      | 內容         | AES密鑰加密後內容.          |
| »» msisdn   | string | true      | 查詢手機號碼 | msisdn requested for score. |

> Response [color=#5DADE2]
- Body Examples
```json
{
    "errorCode": 0,
    "description": "success",
    "contentType": 0,
    "content": "{{encrypt by AES secret Key string, refer Content desc below.}}"
}
```

- Content
```json=
{
    "score": "{{encrypt by Data Nest RSA Public Key string}}", // 信用分數
    "clientCount30": 1, // 30天信用分數查詢次數
}
```

- Body Schema

| Name             | Type    | Required | Title                | description                         |
| ---------------- | ------- | -------- | -------------------- | ----------------------------------- |
| » errorCode      | integer | true     | 錯誤代碼             | [Error Code.](#Error-Code)          |
| » description    | string  | true     | 錯誤訊息             | [Error Message.](#Error-Code)       |
| » contentType    | integer | true     | 回應內容類型         | Type of return content              |
| » content        | string  | true     | 內容                 | AES密鑰加密後內容.                  |
| »» score         | string  | true     | 信用分數             | :heavy_check_mark: RSA Private Key 解密  |
| »» clientCount30 | integer  | true     | 30天信用分數查詢次數 | Number of API calls within 30 days. |

---

### Query Fraud Score

- API Information

| Name         | Value                                          |
| ------------ | ---------------------------------------------- |
| URI          | {env_url}/vds_score_uat/api/v1/fraud/get-score |
| Method       | POST                                           |
| Content-Type | application/json                               |

> Request [color=#39E32E]

- Header

| Name          | Value                 |
| ------------- | --------------------- |
| Authorization | Bearer {access_token} |

- Body
```json
{
  "sessionId": "{{from get rsa key session}}",
  "content": "{{encrypt by AES secret Key string, refer Content desc below.}}"
}
```

- Content
```json=
{
    "msisdn": "84XXXXXX", // 手機號碼
    "refs":[              // 關聯者手機號碼，可多筆
        {
            "msisdn": "85XXXXXX", 
        },
        {
            "msisdn": "86XXXXXX", 
        }
    ] 
}
```

- Body Schema

| Name        | Type         | Required | Title        | Description                 |
| ----------- | ------------ | -------- | ------------ | --------------------------- |
| » sessionId | string       | true      | 期間編號     | sessionId.                  |
| » content   | string       | true      | 內容         | AES密鑰加密後內容.          |
| »» msisdn   | string       | true      | 查詢手機號碼 | msisdn requested for score. |
| »» refs     | array object | true      | 關聯手機清單 | List of references.         |
| »»»         | object       | true      |              |                             |
| »»»» msisdn | string       | true      | 關聯手機     | Reference msisdn.           |

> Response [color=#5DADE2]
- Body Examples
```json
{
    "errorCode": 0,
    "description": "success",
    "contentType": 0,
    "content": "{{encrypt by AES secret Key string, refer Content desc below.}}"
}
```

- Content
```json=
{
    "score": "{{encrypt by Data Nest RSA Public Key string}}", // 詐欺分數
    "fraudLevel":"low",         // 詐欺等級
    "clientCount30": 1,   // 30天詐欺分數查詢次數
}
```

- Body Schema

| Name             | Type    | Required | Title                | description                         |
| ---------------- | ------- | -------- | -------------------- | ----------------------------------- |
| » errorCode      | integer | true     | 錯誤代碼             | [Error Code.](#Error-Code)          |
| » description    | string  | true     | 錯誤訊息             | [Error Message.](#Error-Code)       |
| » contentType    | integer | true     | 回應內容類型         | Type of return content              |
| » content        | string  | true     | 內容                 | AES密鑰加密後內容.                  |
| »» score         | string  | true     | 詐欺分數             | :heavy_check_mark: RSA Private Key 解密                        |
| »» fraudLevel    | string  | true     | 詐欺等級             | Fraud level.                        |
| »» clientCount30 | integer | true     | 30天信用分數查詢次數 | Number of API calls within 30 days. |

---

## Error Code

| Value | Message                                                   |
| ----- | --------------------------------------------------------- |
| 0     | Success                                                   |
| 1     | failure                                                   |
| 2     | request is invalid                                        |
| 3     | content is invalid                                        |
| 4     | authorization credential is missing or invalid            |
| 7     | rsa key is invalid                                        |
| 8     | aes key is invalid                                        |
| 10    | financial organization is invalid                         |
| 13    | msisdn is invalid                                         |
| 14    | OTP is invalid                                            |
| 15    | there was an error in sending the OTP                     |
| 16    | OTP was expired                                           |
| 17    | consent exists                                            |
| 18    | not found consent to verification of personal information |
| 19    | score not found                                           |
| 20    | parameter is invalid                                      |
| 22    | msisdn is external network                                |
| 1010  | reference phone are invalid                               |
