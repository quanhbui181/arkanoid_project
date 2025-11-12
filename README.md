# Arkanoid: BTL môn Lập trình hướng đối tượng (OOP)

![Game Title](https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/game.png?raw=true)

![Tutorial Title](https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/tutorial.png?raw=true)

![Shop1 Title](https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/shop.png?raw=true)

![Shop2 Title](https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/shop2.png?raw=true)

![Play Title](https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/play1.png?raw=true)

![Pause Title](https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/pause.png?raw=true)

![Over Title](https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/over.png?raw=true)

![Win Title](https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/win.png?raw=true)


## I. Thông tin thành viên

**Thành viên nhóm 11:**
- **Nguyễn Hoàng Hải (K69I-IS1)**.
- **Nguyễn Tuấn Đạt (K69I-IS1)**.
- **Bùi Quang Anh (K69I-IS1)**.
- **Nguyễn Đức Khiêm (K69I-CS1)**.

## II. Giới thiệu dự án

Arkanoid là game đập gạch cổ điển được phát triển bằng Java và JavaFX, áp dụng các nguyên lý Lập trình Hướng Đối tượng.

### 🎮 Thông tin kỹ thuật
- **Ngôn ngữ**: Java 25.
- **Framework**: JavaFX.
- **Loại game**: Breakout clone.

### ⭐ Tính năng nổi bật
- **Gameplay cốt lõi**: Di chuyển thanh đỡ, đập vỡ gạch.
- **Hệ thống cửa hàng**: Mua bán skin cho bóng và thanh đỡ.
- **Hệ thống nâng cấp**: Nhiều loại vật phẩm tăng/giảm sức mạnh.
- **Âm thanh**: Hiệu ứng âm thanh real-time.
- **Đa dạng level**: Nhiều màn chơi với độ khó tăng dần.
- **Hệ thống lưu điểm:** Lưu điểm theo tên người dùng và bảng xếp hạng 8 người chơi có điểm cao nhất.

## III. Phân công công việc

### **Nguyễn Hoàng Hải**
- UI Design & Implementation
- Shop System (hệ thống cửa hàng)
- Scene System (hệ thống màn hình)

### **Nguyễn Tuấn Đạt**
- Codebase
- Sound Manager (quản lý âm thanh)
- JUnit Test (kiểm thử)
- Map Renderer (hiển thị bản đồ)

### **Bùi Quang Anh**
- UI Components
- Power-up System (hệ thống vật phẩm)
- Game Loop (vòng lặp game)

### **Nguyễn Đức Khiêm**
- Codebase
- Collision Logic (xử lý va chạm)
- Game Loop (vòng lặp game)
- Game Scene (quản lý màn chơi)

### **UML Diagram**
![UML Diagram](https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/Akanoi%20UML.png)


### **Tổ chức code**
<table align="center" style="border:none;">
  <tr>
    <td valign="top" style="border:none;">
      <img src="https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/3.png" alt="Cấu trúc UI" width="300"/>
    </td>
    <td valign="top" style="border:none;">
      <img src="https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/2.png" alt="Cấu trúc PowerUp" width="300"/>
    </td>
    <td valign="top" style="border:none;">
      <img src="https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/1.png" alt="Cấu trúc chung" width="300"/>
    </td>
  </tr>
</table>



## IV. Bộ sưu tập Skin

#### 🎱 Skin Bóng
<div style="display:flex; flex-wrap:wrap; justify-content:center; gap:10px;">
  <img src="https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/images/shopImages/ballSkins/ballSkin.png?raw=true">
  <img src="https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/images/shopImages/ballSkins/ballSkin4.png?raw=true">
  <img src="https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/images/shopImages/ballSkins/ballSkin6.png?raw=true">
  <img src="https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/images/shopImages/ballSkins/ballSkin2.png?raw=true">
  <img src="https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/images/shopImages/ballSkins/ballSkin8.png?raw=true">
  <img src="https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/images/shopImages/ballSkins/ballSkin5.png?raw=true">
  <img src="https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/images/shopImages/ballSkins/ballSkin3.png?raw=true">
  <img src="https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/images/shopImages/ballSkins/ballSkin7.png?raw=true">
</div>

#### 🛹 Skin Thanh Đỡ
<div style="display:flex; flex-wrap:wrap; justify-content:center; gap:10px; margin-top:20px;">
  <img src="https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/images/shopImages/paddleSkins/greenPaddle.png?raw=true">
  <img src="https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/images/shopImages/paddleSkins/purplePaddle.png?raw=true">
  <img src="https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/images/shopImages/paddleSkins/bluePaddle.png?raw=true">
  <img src="https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/images/shopImages/paddleSkins/redPaddle.png?raw=true">
  <img src="https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/images/shopImages/paddleSkins/orangePaddle.png?raw=true">
  <img src="https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/images/shopImages/paddleSkins/pinkPaddle.png?raw=true">
</div>

## V. Hướng dẫn chơi game

### Điều khiển
- **← →**: Di chuyển thanh đỡ sang trái/phải.
- Dùng chuột để thao tác với các nút.

### 🎁 **Vật phẩm có lợi**
| Vật phẩm | Hiệu ứng | Độ hiếm |
|----------|----------|----------|
| **Tăng kích thước thanh đỡ** | Thanh đỡ dài hơn 25% | Thường |
| **Giảm tốc độ bóng** | Bóng di chuyển chậm hơn 30% | Thường |
| **Hồi phục máu** | +1 mạng cho người chơi | Hiếm |
| **X2 số điểm** | Nhân đôi điểm hiện có | Rất hiếm |

### ⚠️ **Vật phẩm bất lợi**
| Vật phẩm | Hiệu ứng | Cảnh báo |
|----------|----------|----------|
| **Giảm kích thước thanh đỡ** | Thanh đỡ ngắn hơn 25% | Nguy hiểm |
| **Tăng tốc độ bóng** | Bóng nhanh hơn 40% | Rất nguy hiểm |
| **Giảm tốc độ toàn bộ** | Giảm tốc thanh đỡ, bóng và thời gian hiệu ứng | Cảnh báo |
| **Chết ngay lập tức** | Chết ngay lập tức | CỰC KỲ NGUY HIỂM |

## VI. Kết luận

- **Dự án Arkanoid không chỉ là một game giải trí mà còn là minh chứng cho việc áp dụng thành công các nguyên lý Lập trình Hướng Đối tượng vào thực tế. Qua dự án này, nhóm đã nắm vững các khái niệm quan trọng như encapsulation, inheritance, polymorphism và một chút design patterns**.

---
*Được phát triển bởi Nhóm 11 - Môn Lập trình Hướng Đối tượng*
