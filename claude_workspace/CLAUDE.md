# CLAUDE.md

本文件提供給 Claude Code 作為專案上下文，每次開新 session 時會自動讀取。
請隨專案進展持續更新此檔案，讓它保持「單一事實來源」。

## 專案概述

- **專案名稱**：打造你的專屬營養師
- **一句話描述**：使用者輸入身體數據，系統以規則式計算自動生成飲食建議與菜單，並提供飲食紀錄追蹤與歷史趨勢圖表
- **相關文件**：
  - PRD：`docs/PRD.md`
  - 架構設計：`docs/ARCHITECTURE.md`
  - API 規格：`docs/API.md`
  - 任務清單：`docs/TODO.md`

## 技術棧

### 前端（Angular）
- Angular 版本：**Angular 18**
- 狀態管理：**Service + RxJS**（不使用 NgRx，透過 RxJS Subject/BehaviorSubject 管理共享狀態）
- UI 元件庫：**Angular Material**
- 套件管理：npm 10.x（隨 Node.js 20 LTS 內建）
- Node.js 版本：**Node.js 20 LTS（建議 v20.11.1 以上）**

### 後端（Spring Boot / Java 8）
- Java 版本：**Java 8**（注意：Spring Boot 3.x 需要 Java 17+，本專案僅能使用 Spring Boot 2.x）
- Spring Boot 版本：**2.7.18**（Spring Boot 2.x 系列的最終版本，官方支援 Java 8）
- 建置工具：**Maven**
- 資料庫：**PostgreSQL15**
- ORM：Spring Data JPA + Hibernate

### 其他
- 認證方式：**JWT**（請補充：Access Token 有效期、是否使用 Refresh Token、Token 儲存位置如 HttpOnly Cookie 或 LocalStorage）
- 部署環境：（例如 Docker / K8s / 內部伺服器）

## 目錄結構

```
project-root/
├── CLAUDE.md
├── docs/
│   ├── PRD.md
│   ├── ARCHITECTURE.md
│   ├── API.md
│   └── TODO.md
├── frontend/        # Angular 專案
└── backend/         # Spring Boot 專案
```

## 常用指令

### 前端
```bash
cd frontend
npm install
npm run start      # 本機開發
npm run build      # 打包
npm run test       # 單元測試
```

### 後端
```bash
cd backend
mvn clean install
mvn spring-boot:run
mvn test
```

## 程式碼規範

### 前端（Angular）
- 採用 Feature Module 劃分功能模組，避免單一模組過於龐大
- 元件命名：`kebab-case` 檔名，`PascalCase` 類別名
- 盡量使用 Lazy Loading 載入功能模組
- （其他團隊慣例，請補充）

### 後端（Spring Boot）
- 分層架構：`Controller` → `Service` → `Repository`
- DTO 與 Entity 分離，不直接將 Entity 暴露給前端
- 統一例外處理：使用 `@ControllerAdvice`
- 套件命名慣例：（請填寫，例如 `com.company.project.module`）
- （其他團隊慣例，請補充）

## API 設計約定

- 統一前綴：`/api/v1/...`
- 回傳格式：（請填寫，例如統一包裝 `{ code, message, data }`）
- 錯誤處理：（請填寫 HTTP status code 使用慣例）
- 詳細規格見 `docs/API.md`

## 開發流程

1. 每個功能對應 `docs/TODO.md` 中的一個任務項目
2. 完成一個功能即進行對應測試，確認無誤後再進行下一項
3. Commit message 慣例：（請填寫，例如遵循 Conventional Commits）

## 目前不做的事（Out of Scope）

- 真人營養師諮詢/媒合功能
- AI 生成菜單（本期採規則式計算，AI 為後續規劃方向）
- 社群/分享功能
- 付費訂閱/金流串接
- 原生 App（本期僅網頁）

## 待確認事項 / 已知限制

- Java 8 限制了可用的 Spring Boot 與部分第三方套件版本，新增依賴前請先確認相容性
- JWT 相關細節待補：Access Token / Refresh Token 有效期、簽章演算法（建議 HS256 或 RS256）、Token 存放位置與 CSRF 防護策略
- （其他待確認事項，請補充）