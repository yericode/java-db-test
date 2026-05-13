-- CREATE DATABASE IF NOT EXISTS demo
-- CHARACTER SET utf8mb4
-- COLLATE utf8mb4_unicode_ci;
--
-- USE demo;

CREATE TABLE IF NOT EXISTS users(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    email VARCHAR(100),
    phone VARCHAR(10),
    age INT
);