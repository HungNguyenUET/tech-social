-- Database: postDB

-- USE DATABASE postDB;

-- CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS users (
	id SERIAL PRIMARY KEY, 
	username VARCHAR(255) UNIQUE, 
	email VARCHAR(255) UNIQUE, 
	password VARCHAR(255),
	image_url VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Posts (
	id SERIAL PRIMARY KEY, 
	title VARCHAR(255), 
	content TEXT, 
	author_id INT, 
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 

	FOREIGN KEY (author_id) REFERENCES Users ON DELETE CASCADE
)
