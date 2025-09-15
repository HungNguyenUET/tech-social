Test với Postman

1. Đăng ký  
Method: POST  
URL: http://localhost:8080/identity/api/auth/register  
Body (JSON):  
{  
  "username": "testuser",  
  "email": "test@example.com",  
  "password": "P@ssw0rd!",  
  "role": "USER",  
  "imageUrl": "https://example.com/avatar.png"  
}  
  
Kết quả: 200 + JSON User đã tạo.  
  
2. Đăng nhập (email/password)  
Method: POST  
URL: http://localhost:8080/identity/api/auth/login  
Body (JSON):  
{  
  "email": "test@example.com",  
  "password": "P@ssw0rd!"  
}  
  
Kết quả: 200 + "Login successful"  
Lưu ý:  
Context path đang là /identity (xem application.yaml), nên tất cả URL đều bắt đầu bằng http://localhost:8080/identity/....  
Các endpoint khác ngoài /api/auth/** yêu cầu auth. Hiện tại chưa trả JWT và controller login không tạo session cho các request sau, nên bạn sẽ không dùng được login này để gọi endpoint bảo vệ trong Postman (sẽ 401). Nếu muốn test bảo vệ endpoint bằng Postman, ta cần:  
Trả về JWT ở /api/auth/login và cấu hình filter xác thực JWT, hoặc  
Thiết lập session-auth đúng cách (thiết lập SecurityContext + HttpSession) khi login.  

- báo lỗi chưa trả về mã  
- tiếp tục phần oauth2  
  
OAuth2 (Google/Facebook): test qua trình duyệt, không thuận tiện bằng Postman.
Truy cập: http://localhost:8080/identity/oauth2/authorization/google
Sau khi login thành công sẽ redirect về /.
Nhớ thay client-id/client-secret thật trong application.yaml.//error
