Telco VNPT
=== 

## Env Info

| Field/Env     | UT                                                          | PROD |
| ------------- | ----------------------------------------------------------- | ---- |
| URL           | https://lending-uat.vnptmoney.vn:8888/lending-score-api/api |      |
| client_code   | CUB_CIC_TEST                                                | null |
| client_secret | hd4m+NgTcol=R7e5                                            | null |

### RSA UT Public Key
```java
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAl/xI7gDIOPyDBCSSHnhyWTp5KqcxPda5pghmnKoeB3+yoWCRb9wTEQEYQQemVjpm6sBLHkBX92G5sIjT9jX0piJAs8BMEGMXy5J2GYYQbzgQ6cq5enQxcxrfyp1A0w8IFnPZl0p3CE+fGAYtxYm3ClIXk6H96GLZgiunrFsetFCQyAajqWrfZvJsbjMqp4jyv5LOIUlDPm9xKkXKomU4C0bjloPxfnpxfy95OFp2LB59ucu+bVEG4MUWJq9d69qpWUggZ0acn4h3C2mwz6fBN3jVj6718RxNQm7/1yg5fjx3s65POHGyGFQRkYCEMcnxmu/7J7XvF4+njmTNnijdDQIDAQAB
```

### RSA UT Private Key
```java
MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCX/EjuAMg4/IMEJJIeeHJZOnkqpzE91rmmCGacqh4Hf7KhYJFv3BMRARhBB6ZWOmbqwEseQFf3YbmwiNP2NfSmIkCzwEwQYxfLknYZhhBvOBDpyrl6dDFzGt/KnUDTDwgWc9mXSncIT58YBi3FibcKUheTof3oYtmCK6esWx60UJDIBqOpat9m8mxuMyqniPK/ks4hSUM+b3EqRcqiZTgLRuOWg/F+enF/L3k4WnYsHn25y75tUQbgxRYmr13r2qlZSCBnRpyfiHcLabDPp8E3eNWPrvXxHE1Cbv/XKDl+PHezrk84cbIYVBGRgIQxyfGa7/snte8Xj6eOZM2eKN0NAgMBAAECggEAKJKBiX+N86B8/SMiGp4+sFEqvVcqjv5+SrNdaR8Eqy3MqsXTrU/LizOGnP2EyuoGRU6V/vpgQTrGTjkIlGZs69ii+xkvdQRu+robas7eHkKSSUcD4ouYE8ktyrHV90HA//VnNbr8MazHpKsplt0fjtLBVnjdBic1N0meFiWw+aNK32n6GeEAA7kE4f9U2aq/aoeVeQwoUAVkMPL09zamNKrZtwWHWc0EUbeKtKLHXuErO5283VQhPk0uvOeNnBHjAzOMDBfMpb+zq7esmGYXS4sU64+72LYLDHCes0mBic04wDaFbp8QDFjdf5aw8MZTgNpFsHm97fiCUh2Wav/mpQKBgQDCL2MeJL2e2sMLeypwNNhAzs/cwIz0wKq79J5mLI4qZTUZ2sxr0DGvieDrhocL7obrX+QqBw1HnYVhTfdMvRuY4mXn6d3PMg7AZktwybmZKDH+mVwbHKhBg/qHUmHtXfhtJkbDO1+e9gK34FFAZvppSXEBLip2XpcPdZSND5/0twKBgQDIXfLwyweRKdo8ivZ7xqTqHI6stjX8wAeijD+TBOy7oKPROiPqih8Nc9aaRrT+UH1c3yLhEvmftUpQiasrC1AtFbMKgSDbZNZGK+j3uL9DU2p6cczE2FFmJxWAjasVP6vSwFZMzWAOuT3CZcABFt4pQukSFCBPXCYrvzQvofogWwKBgQCDbpuB3ZBSovL9JGI3DC2Kv/BFFDbTukxVZ16G/YVEALiORV+zYXkfvQz1nVpuowXC7mZ6Y8eDP4T1tVW7JQw20JX1AH/XK6R8aUCKmJ78oQ4lqxuCZkUkAvZg/Uu247Gv/wZKQjeYWh7A4h1cn9bByKmqCNtIvwEzv3vKdlbnxQKBgCh+fEPX9I6M89M6jngTfG7oGJmlNaIAOUrLp2mSHDoLTDBT3MBg68evOpbxZVDkeYGUgxsT16lEXWGLgTFw1uJVuVqNIFPWvEtwQmQWTWlQ6XsXlrfNtB2+FmVlvEbOGG6+enKJKzmujGHGaJiPw5R5Nlt1/9WqHPjIasaZqNBzAoGAc8jVwzY0a8GgcmbwVRJT0b0PViTHP2OxHDctUY/N84BX5oK7BbSqNPVMGJJj+kGZSAdKbPl1ZsnuDFigsT+BuBBxk+xHzeeltIT7X6LIMn5IjULzkqOAALTZv/ttcw/04z6vctUk/zIaQ1U13f5cnNYkdMMG/njxZB1m2RVbfWg=
```

---

## Encrypt and Decode

- CUB自行產生 RSA Pair Key(Public & Private)，提供 Public Key 給 VNPT。
- 除了取得權杖之外，其餘 API 要求 Signature 驗證內容。
- Request Data 欄位使用 VNPT 提供的 AES 密鑰加密。
- 加密後轉換的文字內容，都採用Base64。

### RSA Generator Example Code
```java=

public void GenRSA() throws NoSuchAlgorithmException {
    KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
	keyGen.initialize(2048);

	KeyPair pair = keyGen.generateKeyPair();

	// get public
	PublicKey publicKey = pair.getPublic();
    // 編碼 X509
	X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
    // 提取文字都採用Base64
	System.out.println(Base64.getEncoder().encodeToString(x509EncodedKeySpec.getEncoded()));

	// get private
	PrivateKey privateKey = pair.getPrivate();
    // 編碼 PKCS8
	PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
    // 提取文字都採用Base64
	System.out.println(Base64.getEncoder().encodeToString(pkcs8EncodedKeySpec.getEncoded()));
}

```

### Convert RSA Private Key (PKCS8) to Private Key
```java=

public PrivateKey rsaPrivateKey;
public String pkcsPrivateKeyStr = "{{RSA UT Private Key}}";

public void ConvertRSAToPrivateKey(){
    try {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		rsaPrivateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(pkcsPrivateKeyStr.getBytes()));
	} catch (Exception e) {
		// TODO: handle exception
	}
}
```

### Signature Example Code
```java=

public PublicKey rsaPublicKey;
public PrivateKey rsaPrivateKey;

public void GenSignature(String body) {
    
	try {
        // init Signature
		Signature signature = Signature.getInstance("SHA256withRSA");
        // used RSA Private Key
		signature.initSign(rsaPrivateKey);
        // MD5 加密 body
        enBody = MD5(body);
        // 更新
		signature.update(enBody);
        // 簽署
		byte[] digitalSign = signature.sign();
        // 提取文字都採用Base64
        System.out.println(Base64.getEncoder().encodeToString(digitalSign));
	} catch (Exception e) {
		// TODO: handle exception
	}
}

public byte[] MD5(String body){
    try {
		MessageDigest md = MessageDigest.getInstance("MD5");
		return md.digest(body.getBytes());
	} catch (Exception e) {
		// TODO: handle exception
	}
}

```

### Convert AES Secret Key to Private Key
```java=

public SecretKeySpec aesSecretKeySpec;
public String secretKey = "{{client_secret}}";

public void ConvertAESToSecretKeySpec(){
    aesSecretKeySpec = new SecretKeySpec(secretKey, "AES");
}

```

### AES Encrypt Example Code
```java=

public String Encrypt(String data) {
	String output = null;

	if (aesSecretKeySpec != null) {
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, aesSecretKeySpec);

			output = Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes("UTF-8")));
		} catch (Exception ex) {
			// do ...
		}
	}

	return output;
}

```

### AES Decrypt Example Code
```java=

public String Decrypt(String data) {
	String output = null;

	if (aesSecretKeySpec != null) {
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, aesSecretKeySpec);

			output = new String(cipher.doFinal(Base64.getDecoder().decode(data)), StandardCharsets.UTF_8);

		} catch (Exception ex) {
			// do ...
		}
	}

	return output;
}

```

---

## API List

| Method | Endpoint                              | Usage              | Encryption         |
| ------ | ------------------------------------- | ------------------ | ------------------ |
| Post   | {env_url}/v1/token                    | Get Token          |                    |
| Post   | {env_url}/v1/credit/check_mnp         | Check MNP          | :heavy_check_mark: |
| Post   | {env_url}/v1/credit/otp/send          | Send OTP           | :heavy_check_mark: |
| Post   | {env_url}/v1/credit/otp/verify        | Verify OTP         | :heavy_check_mark: |
| Post   | {env_url}/v1/credit/otp/score_non_otp | Query Credit Score | :heavy_check_mark: |
| Post   | {env_url}/v1/fraud/otp/fraud_non_otp  | Query Fraud Score  | :heavy_check_mark: |

___

### Get Token

- API Information

| Name         | Value              |
| ------------ | ------------------ |
| URI          | {env_url}/v1/token |
| Method       | POST               |
| Content-Type | application/json   |

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
    "code": 0,
    "message": "Success"
    "data": {
        "access_token": "eyJhbGciOiJIUzI1NiIsI...",
        "token_type": "Bearer",
        "expires_in": 1800
    }
}
```

- Body Schema

| Name            | Type    | Required | Title        | description                   |
| --------------- | ------- | -------- | ------------ | ----------------------------- |
| » code          | integer | true     | 錯誤代碼     | [Error Code.](#Error-Code)    |
| » message       | string  | true     | 錯誤訊息     | [Error Message.](#Error-Code) |
| » data          | object  | true     |              |                               |
| »» access_token | string  | false    | 存取權杖     | Token.                        |
| »» token_type   | string  | false    | 權杖類型     | Type of token.                |
| »» expires_in   | integer | false    | 有效時間(秒) | Expires time in seconds.      |


---

### Check MNP

- API Information

| Name         | Value                         |
| ------------ | ----------------------------- |
| URI          | {env_url}/v1/credit/check_mnp |
| Method       | POST                          |
| Content-Type | application/json              |

> Request [color=#39E32E]

- Header

| Name          | Value                 |
| ------------- | --------------------- |
| Authorization | Bearer {access_token} |
| Signature     | Signature(MD5(body))  |

- Body
```json=
{
  "request_id": "a962a587-766d-46af-965d-e66ca53b5fd7",
  "request_time": "2023-12-22T02:02:02Z",
  "request_name": "check_mnp",
  "data": "{{encrypt by AES secret Key string, refer data desc below.}}"
}
```

- data
```json=
{
    "phone_number": "84XXXXXX", // 手機號碼
}
```

- Body Schema

| Name            | Type   | Required | Title        | Description                          |
| --------------- | ------ | -------- | ------------ | ------------------------------------ |
| » request_id    | string | true     | 請求編號     | GUID/UUID.                           |
| » request_time  | string | true     | 請求時間     | yyyy-MM-ddTHH:mm:ssZ, UTC Date Time. |
| » request_name  | string | true     | 請求方法名稱 | method name.                         |
| » data          | string | true     | 回應資料     | Response data.                       |
| »» phone_number | string | true     | 查詢手機號碼 | query phone.                         |

> Response [color=#5DADE2]
- Body Examples
```json
{
  "request_id": "a962a587-766d-46af-965d-e66ca53b5fd7",
  "request_time": "2023-12-22T02:02:02Z",
  "request_name": "check_mnp",
  "code": "0",
  "message": "Success",
  "data":"{{encrypt by AES secret Key string, refer data desc below.}}"
}
```

- data
```json=
{
    "id": "XXXXXXXX",
    "mnp":1
}
```

- Body Schema

| Name           | Type    | Required | Title        | Description                          |
| -------------- | ------- | -------- | ------------ | ------------------------------------ |
| » request_id   | string  | true     | 請求編號     | GUID/UUID.                           |
| » request_time | string  | true     | 請求時間     | yyyy-MM-ddTHH:mm:ssZ, UTC Date Time. |
| » request_name | string  | true     | 請求方法名稱 | method name.                         |
| » code         | integer | true     | 錯誤代碼     | [Error Code.](#Error-Code)           |
| » message      | string  | true     | 錯誤訊息     | [Error Message.](#Error-Code)        |
| » data         | string  | true     | 回應資料     | AES密鑰加密後內容.                   |
| »» id          | string  | true     | 回應編號     | Response id.                         |
| »» mnp         | integer | true     | 是否為VNPT   | 0:False, 1:True.                     |


---

### Send OTP

- API Information

| Name         | Value                                             |
| ------------ | ------------------------------------------------- |
| URI          | {env_url}/v1/credit/otp/send |
| Method       | POST                                              |
| Content-Type | application/json                                  |

> Request [color=#39E32E]

- Header

| Name          | Value                 |
| ------------- | --------------------- |
| Authorization | Bearer {access_token} |
| Signature     | Signature(MD5(body))  |

- Body
```json=
{
  "request_id": "a962a587-766d-46af-965d-e66ca53b5fd7",
  "request_time": "2023-12-22T02:02:02Z",
  "request_name": "send_otp",
  "data": "{{encrypt by AES secret Key string, refer data desc below.}}"
}
```

- data
```json=
{
    "phone_number": "84XXXXXX", // 手機號碼
    "client_otp": "123456" // OTP號碼，隨機產生 6 位數字，不能為0開頭
}
```

- Body Schema

| Name            | Type   | Required | Title           | Description                          |
| --------------- | ------ | -------- | --------------- | ------------------------------------ |
| » request_id    | string | true     | 請求編號        | GUID/UUID.                           |
| » request_time  | string | true     | 請求時間        | yyyy-MM-ddTHH:mm:ssZ, UTC Date Time. |
| » request_name  | string | true     | 請求方法名稱    | method name.                         |
| » data          | string | true     | 回應資料        | Response data.                       |
| »» phone_number | string | true     | 接收OTP手機號碼 | OTP receiver’s phone.                |
| »» client_otp   | string | true     | 發送OTP碼       | OTP, 隨機產生 6 位數字，不能為0開頭. |

> Response [color=#5DADE2]
- Body Examples
```json
{
  "request_id": "a962a587-766d-46af-965d-e66ca53b5fd7",
  "request_time": "2023-12-22T02:02:02Z",
  "request_name": "send_otp",
  "code": "0",
  "message": "Success",
  "data":"{{encrypt by AES secret Key string, refer data desc below.}}"
}
```

- data
```json=
{
    "id": "XXXXXXXX",
    "telco":"Vinaphone"
}
```

- Body Schema

| Name           | Type    | Required | Title        | Description                          |
| -------------- | ------- | -------- | ------------ | ------------------------------------ |
| » request_id   | string  | true     | 請求編號     | GUID/UUID.                           |
| » request_time | string  | true     | 請求時間     | yyyy-MM-ddTHH:mm:ssZ, UTC Date Time. |
| » request_name | string  | true     | 請求方法名稱 | method name.                         |
| » code         | integer | true     | 錯誤代碼     | [Error Code.](#Error-Code)           |
| » message      | string  | true     | 錯誤訊息     | [Error Message.](#Error-Code)        |
| » data         | string  | true     | 回應資料     | AES密鑰加密後內容.                       |
| »» id          | string  | true     | 回應編號     | Response id.                         |
| »» telco       | string  | true     | 平台名稱     | Telco name (Vinaphone).              |

---

### Verify OTP

- API Information

| Name         | Value                                             |
| ------------ | ------------------------------------------------- |
| URI          | {env_url}/v1/credit/otp/verify |
| Method       | POST                                              |
| Content-Type | application/json                                  |

> Request [color=#39E32E]

- Header

| Name          | Value                 |
| ------------- | --------------------- |
| Authorization | Bearer {access_token} |
| Signature     | Signature(MD5(body))  |

- Body
```json
{
  "request_id": "a962a587-766d-46af-965d-e66ca53b5fd7",
  "request_time": "2023-12-22T02:02:02Z",
  "request_name": "verify_otp",
  "data": "{{encrypt by AES secret Key string, refer data desc below.}}"
}
```

- data
```json=
{
    "phone_number": "84XXXXXX", // 手機號碼
    "otp": "123456" // OTP號碼
}
```

- Body Schema

| Name            | Type   | Required | Title           | Description                          |
| --------------- | ------ | -------- | --------------- | ------------------------------------ |
| » request_id    | string | true     | 請求編號        | GUID/UUID.                           |
| » request_time  | string | true     | 請求時間        | yyyy-MM-ddTHH:mm:ssZ, UTC Date Time. |
| » request_name  | string | true     | 請求方法名稱    | method name.                         |
| » data          | string | true     | 回應資料        | Response data.                       |
| »» phone_number | string | true     | 接收OTP手機號碼 | OTP receiver’s phone.                |
| »» otp          | string | true     | OTP             | OTP.                                 |

> Response [color=#5DADE2]
- Body Examples
```json
{
  "request_id": "a962a587-766d-46af-965d-e66ca53b5fd7",
  "request_time": "2023-12-22T02:02:02Z",
  "request_name": "verify_otp",
  "code": "0",
  "message": "Success",
  "data":"{{encrypt by AES secret Key string, refer data desc below.}}"
}
```

- data
```json=
{
    "id": "XXXXXXXX",
    "telco":"Vinaphone",
    "expire_time":"2023-02-03",
    "available_product":["???", "???"]
}
```

- Body Schema

| Name                 | Type    | Required | Title        | Description                                                  |
| -------------------- | ------- | -------- | ------------ | ------------------------------------------------------------ |
| » request_id         | string  | true     | 請求編號     | GUID/UUID.                                                   |
| » request_time       | string  | true     | 請求時間     | yyyy-MM-ddTHH:mm:ssZ, UTC Date Time.                         |
| » request_name       | string  | true     | 請求方法名稱 | method name.                                                 |
| » code               | integer | true     | 錯誤代碼     | [Error Code.](#Error-Code)                                   |
| » message            | string  | true     | 錯誤訊息     | [Error Message.](#Error-Code)                                |
| » data               | string  | true     | 回應資料     | AES密鑰加密後內容.                                           |
| »» id                | string  | true     | 回應編號     | Response id.                                                 |
| »» telco             | string  | true     | 平台名稱     | Telco name (Vinaphone).                                      |
| »» expire_time       | string  | true     | 有效期限     | Consent expired time, which was created after verifying OTP. |
| »» available_product | string  | true     | 支援服務     | Which product is available to use after verified OTP.        |

---

### Query Credit Score

- API Information

| Name         | Value                                             |
| ------------ | ------------------------------------------------- |
| URI          | {env_url}/v1/credit/score_non_otp |
| Method       | POST                                              |
| Content-Type | application/json                                  |

> Request [color=#39E32E]

- Header

| Name          | Value                 |
| ------------- | --------------------- |
| Authorization | Bearer {access_token} |
| Signature     | Signature(MD5(body))  |

- Body
```json
{
  "request_id": "a962a587-766d-46af-965d-e66ca53b5fd7",
  "request_time": "2023-12-22T02:02:02Z",
  "request_name": "score_non_otp",
  "data": "{{encrypt by AES secret Key string, refer data desc below.}}"
}
```

- data
```json=
{
    "phone_number": "84XXXXXX" // 手機號碼
}
```

- Body Schema

| Name            | Type   | Required | Title        | Description                          |
| --------------- | ------ | -------- | ------------ | ------------------------------------ |
| » request_id    | string | true     | 請求編號     | GUID/UUID.                           |
| » request_time  | string | true     | 請求時間     | yyyy-MM-ddTHH:mm:ssZ, UTC Date Time. |
| » request_name  | string | true     | 請求方法名稱 | method name.                         |
| » data          | string | true     | 回應資料     | Response data.                       |
| »» phone_number | string | true     | 查詢手機號碼 | phone.                               |

> Response [color=#5DADE2]
- Body Examples
```json
{
  "request_id": "a962a587-766d-46af-965d-e66ca53b5fd7",
  "request_time": "2023-12-22T02:02:02Z",
  "request_name": "score_non_otp",
  "code": "0",
  "message": "Success",
  "data":"{{encrypt by AES secret Key string, refer data desc below.}}"
}
```

- data
```json=
{
    "id": "XXXXXXXX",
    "credit_score":"0",
    "telco":"Vinaphone"
}
```

- Body Schema

| Name            | Type    | Required | Title        | Description                          |
| --------------- | ------- | -------- | ------------ | ------------------------------------ |
| » request_id    | string  | true     | 請求編號     | GUID/UUID.                           |
| » request_time  | string  | true     | 請求時間     | yyyy-MM-ddTHH:mm:ssZ, UTC Date Time. |
| » request_name  | string  | true     | 請求方法名稱 | method name.                         |
| » code          | integer | true     | 錯誤代碼     | [Error Code.](#Error-Code)           |
| » message       | string  | true     | 錯誤訊息     | [Error Message.](#Error-Code)        |
| » data          | string  | true     | 回應資料     | AES密鑰加密後內容.                   |
| »» id           | string  | true     | 回應編號     | Response id.                         |
| »» credit_score | string  | true     | 信用分數     | Credit score.                        |
| »» telco        | string  | true     | 平台名稱     | Telco name (Vinaphone).              |

---

### Query Fraud Score

- API Information

| Name         | Value                                             |
| ------------ | ------------------------------------------------- |
| URI          | {env_url}/v1/fraud/fraud_non_otp |
| Method       | POST                                              |
| Content-Type | application/json                                  |

> Request [color=#39E32E]

- Header

| Name          | Value                 |
| ------------- | --------------------- |
| Authorization | Bearer {access_token} |
| Signature     | Signature(MD5(body))  |

- Body
```json
{
  "request_id": "a962a587-766d-46af-965d-e66ca53b5fd7",
  "request_time": "2023-12-22T02:02:02Z",
  "request_name": "fraud_non_otp",
  "data": "{{encrypt by AES secret Key string, refer data desc below.}}"
}
```

- data
```json=
{
    "phone_number": "84XXXXXX", // 手機號碼
    "reference_number": "0915322322" // 關聯手機號碼
}
```

- Body Schema

| Name                | Type   | Required | Title            | Description                          |
| ------------------- | ------ | -------- | ---------------- | ------------------------------------ |
| » request_id        | string | true     | 請求編號         | GUID/UUID.                           |
| » request_time      | string | true     | 請求時間         | yyyy-MM-ddTHH:mm:ssZ, UTC Date Time. |
| » request_name      | string | true     | 請求方法名稱     | method name.                         |
| » data              | string | true     | 回應資料         | Response data.                       |
| »» phone_number     | string | true     | 查詢手機號碼     | phone.                               |
| »» reference_number | string | true     | 查詢手機關聯號碼 | phone.                               |

> Response [color=#5DADE2]
- Body Examples
```json
{
  "request_id": "a962a587-766d-46af-965d-e66ca53b5fd7",
  "request_time": "2023-12-22T02:02:02Z",
  "request_name": "fraud_non_otp",
  "code": "0",
  "message": "Success",
  "data":"{{encrypt by AES secret Key string, refer data desc below.}}"
}
```

- data
```json=
{
    "id": "XXXXXXXX",
    "fraud_score":"0",
    "fraud_level":"0",
    "telco":"Vinaphone"
}
```

- Body Schema

| Name           | Type    | Required | Title        | Description                          |
| -------------- | ------- | -------- | ------------ | ------------------------------------ |
| » request_id   | string  | true     | 請求編號     | GUID/UUID.                           |
| » request_time | string  | true     | 請求時間     | yyyy-MM-ddTHH:mm:ssZ, UTC Date Time. |
| » request_name | string  | true     | 請求方法名稱 | method name.                         |
| » code         | integer | true     | 錯誤代碼     | [Error Code.](#Error-Code)           |
| » message      | string  | true     | 錯誤訊息     | [Error Message.](#Error-Code)        |
| » data         | string  | true     | 回應資料     | AES密鑰加密後內容.                   |
| »» id          | string  | true     | 回應編號     | Response id.                         |
| »» fraud_score | string  | true     | 詐欺分數     | Fraud score.                         |
| »» fraud_level | string  | true     | 詐欺等級     | Fraud level.                         |
| »» telco       | string  | true     | 平台名稱     | Telco name (Vinaphone).              |

---

## Error Code

| Value | Message                                                                                              |
| ----- | ---------------------------------------------------------------------------------------------------- |
| 0     | Success                                                                                              |
| 1     | Failed / Send OTP failed / Invalid phone number                                                      |
| 2     | The client is not allowed to query credit score / The client is not allowed to query the fraud score |
| 3     | Success but duplicate request                                                                        |
| 4     | Failed - wrong OTP / Failed - OTP incorrect / Failed, does not have consent                          |
| 8     | Failed - more than two reference numbers                          |
| 15    | Verify OTP success, but the customer needed to be approached by leadgen service                      |
| 22    | Wrong OTP format                                                                                     |
| 400   | Invalid request signature                                                                            |
| 401   | Invalid request body (detail in message)                                                             |
| 500   | Internal Server Error                                                                                |
