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

- tiep tuc lam OAuth2...
