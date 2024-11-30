# DBMS


Use Xampp to connect database 


CREATE DATABASE student_management;
USE student_management;



CREATE TABLE users (
    user_id VARCHAR(50) PRIMARY KEY,
    password VARCHAR(50) NOT NULL
);



CREATE TABLE IF NOT EXISTS students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    gender VARCHAR(10) NOT NULL,
    dob DATE NOT NULL,
    email VARCHAR(100)  UNIQUE,
    department VARCHAR(50) NOT NULL,
    mobile VARCHAR(15) NOT NULL
);


INSERT INTO users (user_id, password) VALUES
('admin', 'admin123'),
('Tushar', '222-15-6502');





