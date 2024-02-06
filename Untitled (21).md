[一般] 60400202300611_數位消金專案VLO部署申請單_2023/09/18

[部署資訊]
- 預計部署時間: 2023/09/18 : ~ :
- 部署流程:
    1. DB ???
    2. AP (待通知再執行)

DB
- 組建成品: DB


AP
1.
- 組建成品: keycloak
- CD管線名稱: OVSLXVLO01-HES_keycloak
- Version: gcp image
- Deploy: keycloak-prod

2.
- 組建成品: camunda
- CD管線名稱: OVSLXVLO01-HES_camunda
- Version: #20230908.1
- Deploy: prod

3.
- 組建成品: app
- CD管線名稱: OVSLXVLO01-HES_app
- Version: #20230908.1
- Deploy: prod

4.
- 組建成品: backoffice
- CD管線名稱: OVSLXVLO01-HES_backoffice
- Version: #20230908.1
- Deploy: prod

5.
- 組建成品: backoffice-frontend
- CD管線名稱: OVSLXVLO01-HES_backoffice-frontend
- Version: #20230908.1
- Deploy: prod

[復原計劃]
DB  (無須復原)
AP  (無須復原)



