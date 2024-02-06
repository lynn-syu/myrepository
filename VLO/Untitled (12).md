## postApiDATACTDELETE001 (POST) (/api/DATAC-T-DELETE001)
 ::::success
 >call deleteService.deleteAllDataByPhone()
 - 變數 phone = 取RequestDeleteUserDTO 裡面的CountryCallingCode + PhoneNumber
 - 透過phone 取Table: cif 的Entity
 - 如果找到此Entity，在透過此cifEntity的phone找到table: device_token
 - 刪除table: device_token的資料(by phone)
 - 刪除Table: cif 的資料(by cifEntity)
 - 刪除Table: contract_information的資料(by phone)
 ::::
 