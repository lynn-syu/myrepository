[toc]

## 操作方式
URL: 
https://88.8.70.166/authentication/login?ReturnUrl=%2fProject
https://88.8.70.167/authentication/login?ReturnUrl=%2FProject
1. 點新增應用程式 > 出現視窗
![](https://hackmd.io/_uploads/r1pMU-G2n.png)
2. 輸入應用程式名稱 (HES_camunda) 
3. 輸入應用程式標籤 (HES_camunda_{執行掃描的yyyymmdd}_{次數})
4. 選擇框架 > Java
5. 點更多選項 > 將掃描參數填入 ==AnalysisEngines,20== > 往下滑點建立
![](https://hackmd.io/_uploads/Bk2JIZf3h.png)
6. 輸入標籤 (HES_camunda_{TFS分支})
7. 選擇檔案 (zip檔) > 點建立
![](https://hackmd.io/_uploads/ryllUWGn2.png)
![](https://hackmd.io/_uploads/ryuxU-G3h.png)

## 掃描記錄
負責範圍: 
:::spoiler
- VLO
    - app-api
    - backoffice-api
    - camunda
    - util
    
- VCP
    - data-centralized
    - esign-bl
    - msg-center
    - telcoapi
    - data-api
    - payment
    - payment-momo-bff
    - payment-momo-bl
    - payment-napas-bff
    - payment-napas-bl
    - virtual-account-service
    - itext
    - middle-bo-cl
   
- Batch
    - housekeeping
    - file-batch
    - cdw-batch
:::

### 0731
- VLO
    - app-api(TPI-20230726):==高-3==, 低-1
    - backoffice-api(TPI-20230726):==高-351==, 低-2
    - camunda(TPI-20230726):==中-1==, 低-5
    - util(TPI-20230726):低-9

- VCP
    - data-centralized(SIT/0724-0.5.12):低-8
    - msg-center(SIT/0726-0.5.13):低-4
    - esign-bl(SIT/0719-0.5.11):低-7

### 0808
- VLO
    - app-api(feature/lucent-sky-fix):==高-3==
    - backoffice-api(feature/lucent-sky-fix):==高-351==
    - camunda(feature/lucent-sky-fix):0
    - util(feature/lucent-sky-fix):低-9

### 0810
- VCP
    - data-centralized(SIT/0802-0.5.15):低-8
    - esign-bl(SIT/0807-0.5.16):低-7
    - msg-center(SIT/0807-0.5.16):低-4
    - telcoapi(UAT-0712):高-0 中-0 低-9
    - data-api(feature/00583263): ==高-9== 中-0 低-8
    - payment(SIT/0807-0.5.16):高-0 中-0 低-3
    - payment-momo-bff(SIT/0807-0.5.16):高-0 中-0 低-3
    - payment-momo-bl(SIT/0807-0.5.16):高-0 中-0 低-4
    - payment-napas-bff(SIT/0807-0.5.16):高-0 中-0 低-3
    - payment-napas-bl(SIT/0807-0.5.16):高-0 中-0 低-2
    - virtual-account-service(SIT/0807-0.5.16):高-0 中-0 低-3
    - itext(SIT/0807-0.5.16):高-0 中-0 低-0
    - middle-bo-cl(SIT/0807-0.5.16):高-0 中-0 低-7

- Batch
    - housekeeping(SIT/TPI-20230807):低-2
    - file-batch(SIT/TPI-20230807):低-1
    - cdw-batch(SIT/0807):低-3

### 0815
- VLO
    - app-api(TPI-20230814-0.5.18):==高-3==
    - backoffice-api(TPI-20230814-0.5.18):==高-351==
    - camunda(TPI-20230814-0.5.18):0
    - util(TPI-20230814-0.5.18):低-9 (constant & 語料)

- VCP
    - data-api(SIT/0814-0.5.18):高-0 中-0 低-7

### 0822
- VLO
    - app-api(TPI-20230821-0.5.20):0
    - backoffice-api(TPI-20230821-0.5.20):==高-1==
    - camunda(TPI-20230821-0.5.20):0
    - util(TPI-20230821-0.5.20):低-9 (constant & 語料)

- VCP 
    - [X] data-centralized(SIT/0816-0.5.19):高-0 中-0 低-8 
    - [X] (程式未更新)esign-bl(SIT/0807-0.5.16):低-7
    - [X] (程式未更新)msg-center(SIT/0807-0.5.16):低-4
    - [X] telcoapi(UAT/0817-0.5.19):高-0 中-0 低-8 
    - [X] data-api(UAT/0818-0.5.19): 高-0 中-0 低-8
    - [X] payment(SIT/0821-0.5.20):高-0 中-0 低-6 
    - [X] payment-momo-bff(SIT/0816-0.5.19):高-0 中-0 低-1
    - [X] payment-momo-bl(SIT/0809-0.5.17):高-0 中-0 低-4
    - [X] (程式未更新)payment-napas-bff(SIT/0807-0.5.16):高-0 中-0 低-3
    - [X] payment-napas-bl(SIT/0816-0.5.19):高-0 中-0 低-2
    - [X] virtual-account-service(SIT/0821-0.5.20):高-0 中-0 低-3
    - [X] (程式未更新)itext(SIT/0807-0.5.16):高-0 中-0 低-0
    - [X] middle-bo-cl(SIT/0821-0.5.20):高-0 中-0 低-9 

### 0823
- VLO
    - app-api(TPI-20230822-0.5.21):0
    - backoffice-api(TPI-20230822-0.5.21):0
    - camunda(TPI-20230822-0.5.21):0
    - util(TPI-20230822-0.5.21):低-9 (constant & 語料)

### 0830
- VCP
    - data-api(SIT/0814-0.5.18): 確認是不是可以使用其他pdf轉檔程式(取代itext)

### 0911
- VLO (NAPAS-Pilot 定版)
    - app-api(TPI-20230907-0.6.3): 0
    - backoffice-api(TPI-20230907-0.6.3): 0
    - camunda(TPI-20230907-0.6.3): 0
    - util(TPI-20230907-0.6.3):低-9 (constant & 語料)

### 0927
- VCP (tb_io_log 優化)
    - data-api(localhost): 低-23
    - telcoapi(localhost): 低-18

### 1001
- VLO (0.6.9)
    - app-api(staging): 0
    - backoffice-api(staging): 0
    - camunda(staging): 0
    - util(staging):低-9 (constant & 語料)

### 1023
- VLO (0.6.12 CAB2-2)
    - app-api(staging): 0
    - backoffice-api(staging): 0
    - camunda(staging): 0
    - util(staging): 低-9
    - cdw-batch(staging): 低-2
    
- VCP (0.6.12 CAB2-2)
    - data-api(staging): 低-8
    - telcoapi(staging): 低-8
    - cdw-batch	            TPI/1020-0.6.12	low-2
    - customer-service	    TPI/1020-0.6.12	low-3,==mid-1==
    - data-api	            Staging	low-8
    - data-centralized	    TPI/1003-0.6.8	low-8
    - esign-bl	            TPI/1018-0.6.11	low-7
    - file-batch	            TPI/0807-0.5.16	low-1
    - housekeeping-batch	    TPI/0807-0.5.16	low-2
    - itext	                TPI/1018-0.6.11	all 0
    - middle-bo-bff	        TPI/1003-0.6.8	low-3
    - middle-bo-cl	        TPI/1020-0.6.12	low-4
    - middle-bo-web	        TPI/1018-0.6.11	low-7
    - payment	                TPI/1020-0.6.12	low-9
    - payment-momo-bff	    SIT/0816-0.5.19	low-1
    - payment-momo-bl	        SIT/0824-0.5.22	low-4
    - payment-napas-bff	    SIT/0807-0.5.16	low-3
    - payment-napas-bl	    HOTFIX/0816-0.5.19	low-2
    - telcoapi	            staging	low-8
    - vcp-bch-chr-emp	        feature/chr	先前第一次上板後掃過沒異動
    - vcp-svc-chr-emp	        feature/chr	先前第一次上板後掃過沒異動
    - vcp-svc-msg-center	    TPI/1020-0.6.12	low-7
    - vcp-svc-scheduler	    feature/00583263	應該是沒用到不用掃
    - virtual-account-service	SIT/0905-0.6.2	low-3

### 1222
- VLO (0.6.27 CAB2-2.1)
    - app-api(develop): 0
    - backoffice-api(develop): 0 
    - camunda(develop): 0
    - util(develop): 低-9 (constant & 語料)

- VCP (CAB2-2.1)
    - data-api(develop - 0.6.27.1): 低-8
    - telcoapi(develop - 0.6.27.1): 12/28 二次掃描 > 低-8 


### 2024 0117 
- VLO (0.6.28)
    - app-api(develop): 0
    - backoffice-api(develop): 0 
    - camunda(develop): 0
    - util(develop): 低-9 (constant & 語料)

### 0206 for cab2-3
- VLO (0.6.31)
    - app-api(develop): 0
    - backoffice-api(develop): 0
    - camunda(develop): 0
    - util(develop): 低-9 (constant & 語料)

- VCP (0.6.30-1)
    - telcoapi(develop): 低-11 (第三方加解密規則,語料)
    - telcoschedule(develop): 低-17 (第三方加解密規則,語料)

