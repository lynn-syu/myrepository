[toc]

:bulb: 通路會使用在==新註冊 & 換機登入==的時候都會 add / update device token 給DC

## postApiDatacBVnadddtkm001 (POST) (/api/DATAC-B-VNADDDTKM001)
:::success
> 目的: add-device-token 儲存客戶手機上推播用的token

> call deviceTokenService.addDeviceToken()
- 透過requestAddDeviceTokenDTO.getTRANRQ().getPhone())取得電話，並到Table: device_token找到該筆Entity
- 如果找不到該筆Entity，則產生一筆新的DAO並存入Table: device_token
- 完成後回傳HttpStatus.OK & ResponseAddDeviceTokenDTO 僅 MWHEADER
:::
## postApiDatacBVnqurdtkq001 (POST) (/api/DATAC-B-VNQURDTKQ001)
:::success
> 目的: query-device-token 查詢客戶手機上推播用的token,uuid
 
> call deviceTokenService.queryDeviceToken()
- 透過requestAddDeviceTokenDTO.getTRANRQ().getPhone())取得電話，並到Table: device_token找到該筆Entity
:warning: 找不到則丟錯 new BadRequestException("DATAC-B-VNQURDTKQ001-1",
                MessageSourceUtils.getMessage("member-not-exist")))
- 將找到的phone,token,deviceUuid set到ResponseQueryDeviceTokenbodyDTO
- 完成後回傳HttpStatus.OK & requestQueryDeviceTokenDTO內容
::::
## postApiDatacBVnupddtkm001 (POST) (/api/DATAC-B-VNUPDDTKM001)
:::success
> 目的: update-device-token 更新客戶手機上推播用的token

>call deviceTokenService.updateDeviceToken()
- 透過requestAddDeviceTokenDTO.getTRANRQ().getPhone())取得電話，並到Table: device_token找到該筆Entity
:warning: 找不到則丟錯 new BadRequestException("DATAC-B-VNUPDDTKM001-1",
                MessageSourceUtils.getMessage("member-not-exist")))
- 由req提供的 token 將entity token欄位資料做更新，再save
:::

## postApiDatacBVnqdvwudq001 (POST) (/api/DATAC-B-VNQDVWUDQ001)
:::success
> 目的: use device-uuid to query phone(device)

>call deviceTokenService.queryDeviceWithDeviceUuid()
- 透過DeviceUuid到Table: device_token找到該筆Entity
- 若找不到則new DeviceTokenEntity :question: 長一個新的目的只是要回覆的格是正常而已?
- 完成後回傳HttpStatus.OK & ResponseQueryDeviceWithDeviceUuidDTO內容

::::
## postApiDatacBVnquadtkq001 (POST) (/api/DATAC-B-VNQUADTKQ001)
:::success
> 目的: query-active-device-token 查詢有存在的手機token,uuid

> call deviceTokenService.queryActiveDeviceToken()
- 查找Table: device_token所有的Entity :question: 這樣哪裡判別了active???
- 將找到的Entity List set到 responxeBody裡
- 回傳HttpStatus.OK & ResponseQueryActiveDeviceTokenDTO內容
::::