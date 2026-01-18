/*
 * Database Creation Script
 * Project: Java Stock Management System
 * Author: Rami ELAMRI
 */

-- Create the database if it doesn't exist
CREATE DATABASE IF NOT EXISTS gestion_stock;

-- Select the database to use
USE gestion_stock;

-- Create the main table
CREATE TABLE IF NOT EXISTS articles (
    id INT PRIMARY KEY AUTO_INCREMENT,
    type VARCHAR(50),       -- Stores 'Livre', 'DVD', or 'JeuVideo' [cite: 702]
    titre VARCHAR(100),     -- Title of the item [cite: 702]
    prix DOUBLE,            -- Price of the item [cite: 702]
    auteur VARCHAR(100),    -- Specific to Books (NULL for others) [cite: 702]
    console VARCHAR(50)     -- Specific to Games (NULL for others) [cite: 702]
);

-- (Optional) Insert some test data so the app isn't empty
INSERT INTO articles (type, titre, prix, auteur, console) VALUES 
('Livre', 'Antigone', 40.0, 'Jean Anouilh', NULL),
('DVD', 'Oppenheimer', 50.0, NULL, NULL),
('JeuVideo', 'God of War', 120.0, NULL, 'PS5');
-- 3103