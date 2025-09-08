-- Database: postDB

-- USE DATABASE postDB 

-- Insert sample users
INSERT INTO users (username, email, password, image_url) VALUES
('tech_guru', 'guru@tech.com', 'hashed_password_1', 'https://example.com/avatars/guru.jpg'),
('code_wizard', 'wizard@code.dev', 'hashed_password_2', 'https://example.com/avatars/wizard.png'),
('data_scientist', 'data@science.ai', 'hashed_password_3', 'https://example.com/avatars/datasci.jpg'),
('web_dev', 'dev@web.io', 'hashed_password_4', 'https://example.com/avatars/webdev.png'),
('ai_researcher', 'ai@research.org', 'hashed_password_5', 'https://example.com/avatars/ai.jpg');

-- Insert sample tech posts
INSERT INTO Posts (title, content, author_id, created_at) VALUES
('Getting Started with React Hooks', 
 'React Hooks have revolutionized how we write functional components. In this post, we''ll explore useState, useEffect, and custom hooks to build modern React applications.',
 1, '2024-01-15 09:30:00'),

('Python vs JavaScript: Which to Learn in 2024?', 
 'Both Python and JavaScript are incredibly popular languages. Python excels in data science and backend development, while JavaScript dominates web development. Let''s compare their ecosystems, job markets, and learning curves.',
 2, '2024-01-16 14:20:00'),

('Machine Learning Model Deployment Strategies', 
 'Deploying ML models can be challenging. This guide covers various deployment options including Docker containers, serverless functions, and cloud platforms like AWS SageMaker and Google AI Platform.',
 3, '2024-01-17 11:45:00'),

('Building a RESTful API with Node.js and Express', 
 'Learn how to create a robust REST API using Node.js and Express. We''ll cover routing, middleware, authentication with JWT, and database integration with PostgreSQL.',
 4, '2024-01-18 16:10:00'),

('The Future of AI: GPT-4 and Beyond', 
 'With the rapid advancement of large language models, what does the future hold for AI? We explore the capabilities of GPT-4, ethical considerations, and potential applications across industries.',
 5, '2024-01-19 13:05:00'),

('CSS Grid vs Flexbox: When to Use Which', 
 'Both CSS Grid and Flexbox are powerful layout tools, but they serve different purposes. Grid is ideal for two-dimensional layouts, while Flexbox excels at one-dimensional content flow.',
 1, '2024-01-20 10:30:00'),

('Database Optimization Techniques', 
 'Slow database queries can cripple your application. Learn about indexing, query optimization, connection pooling, and caching strategies to keep your database performing at its best.',
 2, '2024-01-21 15:40:00'),

('Introduction to Docker and Containerization', 
 'Docker has changed how we develop and deploy applications. This tutorial covers container basics, Dockerfiles, docker-compose, and best practices for containerized development.',
 3, '2024-01-22 08:55:00');


SELECT * FROM posts;

SELECT * FROM users;