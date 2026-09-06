CREATE DATABASE IF NOT EXISTS employee_db;

USE employee_db;

CREATE TABLE IF NOT EXISTS employees (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    department VARCHAR(255) NOT NULL
);

INSERT INTO employees (name, department) VALUES
('Ahmed', 'SRE'),
('omar', 'Development'),
('Imran', 'Testing');