CREATE DATABASE task_planner;

USE task_planner;

CREATE TABLE tasks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    task_name VARCHAR(255) NOT NULL,
    short_description TEXT,
    category VARCHAR(100),
    deadline DATE NOT NULL
);
