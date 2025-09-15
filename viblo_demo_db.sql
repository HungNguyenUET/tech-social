
-- Kết nối đến database: \c viblo_demo_db

-- Kích hoạt extension uuid-ossp để sử dụng gen_random_uuid()
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Tạo bảng users
CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(), -- Sử dụng uuid thay cho SERIAL
    username VARCHAR(255) UNIQUE,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255),
    provider VARCHAR(50),
    provider_id VARCHAR(255),
    role VARCHAR(50) NOT NULL,
    image_url VARCHAR(255) -- Thêm cột để lưu URL ảnh đại diện
);

-- Thêm index cho query nhanh
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_users_username ON users(username);

-- Tạo bảng posts
CREATE TABLE IF NOT EXISTS posts (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(), -- Sử dụng uuid thay cho SERIAL
    title VARCHAR(255) NOT NULL,
    content TEXT,
    author_id UUID NOT NULL, -- Thay INTEGER bằng UUID để khớp với users.id
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Thêm index cho author_id
CREATE INDEX IF NOT EXISTS idx_posts_author ON posts(author_id);

select * from users
select * from posts
