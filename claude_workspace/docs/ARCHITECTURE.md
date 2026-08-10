# 架構設計文件：前後端分離版本

## 1. 設計目標

基於 PRD 與專案上下文，本專案採用「前後端分離」架構，目標是：

- 前端專注於使用者介面與互動體驗
- 後端專注於業務邏輯、資料處理與安全驗證
- 兩端透過 REST API 進行資料交換，降低耦合度
- 後續可獨立擴充、部署與維護

## 2. 整體架構

### 2.1 架構示意

```text
使用者瀏覽器
  │
  ▼
Angular 前端應用 (SPA)
  │  HTTPS / JSON / REST API
  ▼
Spring Boot 後端服務
  │
  ├─ Auth Module
  ├─ Profile Module
  ├─ Nutrition Calculation Module
  ├─ Meal Plan Module
  └─ Weight History Module
  │
  ▼
PostgreSQL 資料庫
```

### 2.2 分層說明

- 前端層：Angular 18 + Angular Material + RxJS
  - 負責頁面渲染、表單輸入、API 呼叫、狀態管理
  - 使用 Feature Module 切分功能，避免單一模組過大

- 後端層：Spring Boot 2.7.18 + Java 8 + Maven
  - 負責會員驗證、資料驗證、營養計算、菜單生成、權限控制
  - 依照 Controller → Service → Repository 分層設計

- 資料層：PostgreSQL 15
  - 儲存使用者帳號、個人資料、體重歷史與菜單建議結果

## 3. 前端架構

### 3.1 技術選型

- Angular 18
- Angular Material
- RxJS + Service
- NgRx 不採用，改用 Service/BehaviorSubject 管理共用狀態

### 3.2 前端模組劃分

建議依功能切分如下：

- Auth Module
  - 註冊 / 登入 / 登出
  - 忘記密碼流程

- Profile Module
  - 身體數據輸入
  - 個人資料編輯
  - 飲食偏好設定

- Recommendation Module
  - 建議熱量與營養素顯示
  - 菜單生成結果展示

- History Module
  - 體重紀錄新增/更新
  - 趨勢圖表展示

- Shared Module
  - 共用元件、共用服務、共用 DTO 類型

### 3.3 前端資料流

- 前端不直接處理營養計算邏輯
- 所有核心計算由後端提供結果
- 前端只負責：
  - 收集使用者輸入
  - 呼叫 API
  - 顯示結果與錯誤訊息

## 4. 後端架構

### 4.1 技術選型

- Spring Boot 2.7.18
- Java 8
- Maven
- Spring Web
- Spring Security
- Spring Data JPA + Hibernate
- PostgreSQL 15

### 4.2 後端分層設計

```text
Controller
  ↓
Service
  ↓
Repository / JPA
  ↓
Database
```

#### Controller
- 負責接收 HTTP 請求
- 參數驗證與回傳 API 回應

#### Service
- 實作商業邏輯
- 包含：認證、個人資料管理、營養計算、菜單生成

#### Repository
- 對資料庫進行 CRUD 操作
- 只處理資料存取，不包含業務邏輯

### 4.3 後端模組劃分

建議依業務拆分：

- Auth Module
  - 註冊、登入、登出、刷新 token、撤銷 token

- User Profile Module
  - 儲存與更新使用者基本資料與健康資料

- Nutrition Calculation Module
  - BMR / TDEE 計算
  - 熱量目標換算
  - 營養素比例拆解

- Meal Plan Module
  - 依據建議熱量生成早餐/午餐/晚餐菜單
  - 產出 macroBreakdown

- Weight History Module
  - 新增與查詢歷史體重記錄
  - 提供趨勢圖資料來源

## 5. API 設計原則

### 5.1 API 路徑規則

- 統一前綴：`/api`
- 例如：
  - `/api/auth/register`
  - `/api/auth/login`
  - `/api/profile`
  - `/api/recommendation`
  - `/api/weights`

### 5.2 回傳格式

建議統一格式如下：

```json
{
  "code": 200,
  "message": "success",
  "data": {}
}
```

錯誤情況使用標準 HTTP status code，並附帶對應錯誤訊息。

### 5.3 資料交換格式

- 全部使用 JSON
- 前後端共用 DTO 定義，避免前端直接依賴 Entity
- API 版本控制不包含 `/v1`，採用 `/api` 前綴

## 6. 認證與授權設計

### 6.1 認證方式

依照 PRD，採用 JWT，並符合以下原則：

- Access Token：短效，15 分鐘
- Refresh Token：長效，14 天
- Refresh Token 需後端雜湊儲存
- 支援輪替與撤銷
- MVP 使用 HS256

### 6.2 儲存策略

建議採用以下方式：

- 前端不將 token 放入 localStorage
- 使用 HttpOnly Cookie 儲存 token
- 前端透過 `withCredentials: true` 發送請求
- 針對跨站請求情境，設定 SameSite 與 CSRF 防護

### 6.3 授權流程

1. 使用者登入成功後，後端發放 token
2. 前端透過認證 API 取得受保護資源
3. 後端檢查 token 是否有效，並判斷角色/權限
4. 若 token 無效或已撤銷，回傳 401 或 403

## 7. 資料模型建議

### 7.1 使用者資料

- id
- email
- passwordHash
- profileData
- createdAt
- updatedAt

### 7.2 體重紀錄

- id
- userId
- weight
- recordedAt

### 7.3 菜單建議

- id
- userId
- generatedAt
- dailyCalories
- macroBreakdown
- mealPlan

## 8. 內部流程設計

### 8.1 使用者輸入身體數據流程

1. 前端收集使用者輸入資料
2. 前端呼叫 `/api/recommendation/calculate`
3. 後端進行 BMR/TDEE 計算
4. 後端回傳每日熱量與營養素建議
5. 前端展示建議結果與菜單

### 8.2 生成菜單流程

1. 前端請求菜單生成
2. 後端根據每日熱量與營養素比例生成三餐菜單
3. 後端回傳可執行食譜內容與份量
4. 前端顯示早餐、午餐、晚餐結果

### 8.3 體重歷史追蹤流程

1. 使用者新增或更新體重紀錄
2. 前端送到 `/api/weights`
3. 後端儲存至資料庫
4. 前端從歷史資料中繪製趨勢圖

## 9. 部署架構建議

### 9.1 開發環境

- 前端：`frontend/` 本地啟動
- 後端：`backend/` 本地啟動
- 資料庫：PostgreSQL 本地或 Docker 容器

### 9.2 生產環境

建議採用以下方式：

- 前端部署為靜態網站，例：Nginx / S3 / CDN
- 後端部署為獨立服務，例：VM / Docker / K8s
- 資料庫部署為獨立 PostgreSQL 實例
- 前後端透過 HTTPS 與 API 互通

### 9.3 本地開發建議

可使用 Docker Compose 管理：

- 前端開發伺服器
- 後端 Spring Boot 應用
- PostgreSQL 資料庫

這樣可以讓開發者在不依賴複雜本機環境下快速啟動整個系統。

## 10. 實作原則與注意事項

- 前端只負責呈現與互動，不承擔核心計算邏輯
- 後端必須對所有重要業務進行驗證與權限控制
- API 應有清楚的功能管理與錯誤處理
- 先實作 MVP 功能，再逐步擴充進階功能
- 對個人健康資料，需遵守基本隱私與安全原則

## 11. 結論

本專案適合採用前後端分離架構，並以 Angular + Spring Boot + PostgreSQL 為核心技術組合。這樣不僅符合 PRD 的產品需求，也能讓專案後續在開發、測試、部署與擴充上更具彈性。