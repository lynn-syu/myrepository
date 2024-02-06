[toc]

## :question: 專案與keycloak是如何綁定及設定??

## getUsers (GET) (api/users)
> call userService.findAl

找 Table:bo_user 依照查詢條件回傳前端資料

## getUser (GET) (api/users/{id})
> call UserService.getById 
:::success
- 找Table: bo_user拿取User info
- 找Table: bo_user拿取keycloak_id,role資料
- 找keyclock API->users.get()取得roleId
:::
## createUser (POST) (api/users)
> call userService.createUser
:::success
- insert into Table:bo_user
- 如果call backofficeUserService.findKeycloakIdByUsername找的到就回傳此user keyclockId不然則call BackofficeUserService.createUserReturningKeycloakId 創建新的keyclockId
  }
- update user keycloakUserId 到 bo_user
- call notificationService.emailBoUserCreated寄信給User
- 在audit_event記錄一筆新增的資訊
:::
## editUser (PUT) (api/users/{id})
> call userService.editUser
:::success
- 取keycloak_id,role: call userRepository.findKeycloakId() Table: bo_user
- 取roleName : call roleService.getRoleNameById(keycloak API)
- 取userResource(Object) : call users.get(keycloak API)
- 取employee_number,email,name,role資料: call auditRepository.getUserDetails Table: bo_user
- 如果role不存在 -> call keycloak method : userResource.joinGroup()
- call auditEventRepository.addEvent寫資料到Table: audit_event做紀錄
:::





