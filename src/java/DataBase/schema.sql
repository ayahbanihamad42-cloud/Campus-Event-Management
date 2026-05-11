/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  user
 * Created: Apr 21, 2026
 */
CREATE TABLE events (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    organizer_name VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    department_club VARCHAR(100) NOT NULL,
    event_date_time DATETIME NOT NULL,
    location VARCHAR(150) NOT NULL,
    capacity INT NOT NULL,
    reserved_seats INT NOT NULL DEFAULT 0,
    category ENUM('EDUCATIONAL', 'SOCIAL', 'SPORTS', 'CULTURAL', 'TECHNICAL') NOT NULL,
    image_path VARCHAR(255),
    status ENUM('Open', 'Closed', 'Completed', 'Expired') NOT NULL DEFAULT 'Open',
    event_type ENUM('Workshop', 'Seminar', 'Club Social Event', 'Sports Activity') NOT NULL
);
