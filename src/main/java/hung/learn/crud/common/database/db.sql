-- Create database
CREATE DATABASE IF NOT EXISTS crud;
USE crud;

-- Create teacher table
CREATE TABLE IF NOT EXISTS users (
                                       id          INT AUTO_INCREMENT PRIMARY KEY,
                                       fullname    VARCHAR(255) NOT NULL,
    username    VARCHAR(255) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    createdAt   DATETIME DEFAULT CURRENT_TIMESTAMP,
    updatedAt   DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );

-- Create student table
CREATE TABLE IF NOT EXISTS students (
                                       id          INT AUTO_INCREMENT PRIMARY KEY,
                                       fullname    VARCHAR(255) NOT NULL,
    phone        VARCHAR(20), -- Increased length for international formats
    address     VARCHAR(255),
    point       DECIMAL(10, 2), -- Changed to DECIMAL for precision
    createdAt   DATETIME DEFAULT CURRENT_TIMESTAMP,
    updatedAt   DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );
