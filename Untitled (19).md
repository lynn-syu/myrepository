各位長官、同仁好，
此為研究院系列課程的會議邀請，課程採線上授課，
請於指定時間加入會議，謝謝。
線上會議連結：研究院培訓ㄧ、Camunda介紹及微服務 - Webex :
https://cathay.webex.com/webappng/sites/cathay/meeting/info/95cbca9e848b0d35a2cb081393c68c60?siteurl=cathay&MTID=mb3e8f73ba3d29e8d8d8fa78b5b7842ab

![](https://hackmd.io/_uploads/BkpFpgP23.png)

## 8/14
Token Simulator Plugin
![](https://hackmd.io/_uploads/rJGQjWwhh.png)
![](https://hackmd.io/_uploads/HyDZ2WDhn.png)
![](https://hackmd.io/_uploads/Hkduhbwn2.png)
![](https://hackmd.io/_uploads/Bk2NaZvn3.png)
![](https://hackmd.io/_uploads/SyXia-Dn2.png)
![](https://hackmd.io/_uploads/HkBYRWw2n.png)
![](https://hackmd.io/_uploads/r1Rq0Zw2n.png)
![](https://hackmd.io/_uploads/S1WBkfP3n.png)
![](https://hackmd.io/_uploads/BJjL1MDhh.png)
![](https://hackmd.io/_uploads/B1ucJGD2h.png)
![](https://hackmd.io/_uploads/By4WgMvn3.png)
![](https://hackmd.io/_uploads/BJVPlGwnh.png)
![](https://hackmd.io/_uploads/HkQWWMD32.png)
![](https://hackmd.io/_uploads/rJkKZMD2n.png)
![](https://hackmd.io/_uploads/rkiWzMv32.png)
![](https://hackmd.io/_uploads/ByAsUzv3h.png)
![](https://hackmd.io/_uploads/ryQ0Lfv2n.png)
![](https://hackmd.io/_uploads/SkJXDMDn3.png)
![](https://hackmd.io/_uploads/SkjNwzDn3.png)
![](https://hackmd.io/_uploads/H16YvzDhh.png)
![](https://hackmd.io/_uploads/BJiJ_fwh3.png)
![](https://hackmd.io/_uploads/Hk88uGP32.png)
![](https://hackmd.io/_uploads/S1dvOfw33.png)
![](https://hackmd.io/_uploads/rkM5dGw22.png)
![](https://hackmd.io/_uploads/HkLa_zw33.png)
![](https://hackmd.io/_uploads/H1v1tzv3n.png)


## 8/15
![](https://hackmd.io/_uploads/Hkmn49_nh.png)
![](https://hackmd.io/_uploads/Byx1Hcuhh.png)
![](https://hackmd.io/_uploads/SJg1rr9d33.png)
![](https://hackmd.io/_uploads/SJU2Hqu22.png)
![](https://hackmd.io/_uploads/SklUVL9u33.png)

event-based :後面都接事件型的活動
Manual Task:表示手動的部分，不只是內部，外部的手動活動也可以用這個圖來表示

![](https://hackmd.io/_uploads/rJ1Fw5dhh.png)
![](https://hackmd.io/_uploads/r16svqd3n.png)
![](https://hackmd.io/_uploads/S1O5F5uhn.png)
![](https://hackmd.io/_uploads/r19N99On2.png)
![](https://hackmd.io/_uploads/HkEDo9_32.png)
![](https://hackmd.io/_uploads/r1tis9dnh.png)
![](https://hackmd.io/_uploads/rkN9n5_3n.png)
![](https://hackmd.io/_uploads/BJOnn5uhh.png)
![](https://hackmd.io/_uploads/SyCtacu2h.png)
![](https://hackmd.io/_uploads/Hy_rA5uhh.png)
![](https://hackmd.io/_uploads/H1YZJod2n.png)
![](https://hackmd.io/_uploads/ryj81jO33.png)
![](https://hackmd.io/_uploads/rkSiJiu33.png)
P20 任務內的錯誤子流程可以取到流程相關變數
P20 如果使用任務外的錯誤事件，是取不到流程內的相關變數
![](https://hackmd.io/_uploads/ByXHEiu33.png)
![](https://hackmd.io/_uploads/Hk2fHid32.png)
![](https://hackmd.io/_uploads/SyeJ8sdn2.png)
![](https://hackmd.io/_uploads/S1qE8s_3h.png)
![](https://hackmd.io/_uploads/BJ-BIjO22.png)
![](https://hackmd.io/_uploads/HyDh8jun3.png)
![](https://hackmd.io/_uploads/SyYVwjO32.png)
![](https://hackmd.io/_uploads/SyIHwoO3h.png)
![](https://hackmd.io/_uploads/HJy0Pid32.png)
![](https://hackmd.io/_uploads/HkVztsu32.png)
![](https://hackmd.io/_uploads/HkAmYs_h3.png)
![](https://hackmd.io/_uploads/SyAIYsun3.png)
![](https://hackmd.io/_uploads/BkOots_22.png)
![](https://hackmd.io/_uploads/rkFAciu22.png)
![](https://hackmd.io/_uploads/ryP1jju3n.png)
![](https://hackmd.io/_uploads/rkvMhs_nn.png)
![](https://hackmd.io/_uploads/B1uh3jO3h.png)
![](https://hackmd.io/_uploads/rkxTE3_33.png)

![](https://hackmd.io/_uploads/SJ6d5nunh.png)



User Task 如果要assign 給一個group再bpmn怎麼做比較好?
A: 在Camunda裡面可以設定candidate group來只派某個群組，在HES專案目前應該是透過keycloak來決定群組有哪些，並不是使用Camnuda的assign group功能

P21 exception event catch & throw 應該有個配對方式吧?
A:如果沒有特別設定，系統判定會由內而外來做throw & catch


P33 上圖 basic event 不太理解甚麼時候會寄信 & 跳通知?
A: 先踩到哪個哪個就先跑，依照p33圖，如果先收到信件，就跑信件的流程，如果過三十分鐘沒有收到信，就會跑下面的流程


## 8/22
![](https://hackmd.io/_uploads/S1EOb0Wah.png)
![](https://hackmd.io/_uploads/HJU2-AWp2.png)
![](https://hackmd.io/_uploads/rJWZMCZa2.png)
![](https://hackmd.io/_uploads/H1CfM0Za3.png)
![](https://hackmd.io/_uploads/SyGvfA-Th.png)
![](https://hackmd.io/_uploads/S17-70-Th.png)
![](https://hackmd.io/_uploads/Hk4dQ0-T2.png)
![](https://hackmd.io/_uploads/r1gcXAWp3.png)
![](https://hackmd.io/_uploads/BkQAX0-ph.png)
![](https://hackmd.io/_uploads/S1MN4RZ62.png)
![](https://hackmd.io/_uploads/H1b8E0-a2.png)
![](https://hackmd.io/_uploads/Bkn54RZ6h.png)
![](https://hackmd.io/_uploads/ry7a4Cba3.png)
![](https://hackmd.io/_uploads/HJHlLCZpn.png)
![](https://hackmd.io/_uploads/S1AKUC-63.png)
![](https://hackmd.io/_uploads/rJxbPC-p2.png)
![](https://hackmd.io/_uploads/HJyPv0WTn.png)
![](https://hackmd.io/_uploads/r1Mjw0Zan.png)
![](https://hackmd.io/_uploads/HkgaGORZ6n.png)
![](https://hackmd.io/_uploads/r1__9CZ6n.png)
![](https://hackmd.io/_uploads/rkfZ2RZ6h.png)
![](https://hackmd.io/_uploads/H1HrRAZ63.png)
![](https://hackmd.io/_uploads/H1-iRRba2.png)
![](https://hackmd.io/_uploads/ry5hRAWpn.png)
![](https://hackmd.io/_uploads/S1KWkyfT3.png)
![](https://hackmd.io/_uploads/H1C1p0bph.png)
![](https://hackmd.io/_uploads/ryxjEJfTh.png)
![](https://hackmd.io/_uploads/HkDsV1M6n.png)
![](https://hackmd.io/_uploads/HkMTuJGa3.png)
![](https://hackmd.io/_uploads/B1hpdJGp2.png)





businessKey: 業務流程唯一可以識別的資訊


formKey的作用??

