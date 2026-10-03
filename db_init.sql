-- ============================================
-- INDOMARET MASTER DATA - DATABASE INIT SCRIPT
-- ============================================

CREATE DATABASE IF NOT EXISTS db_indomaret;
USE db_indomaret;

-- Table: provinces
CREATE TABLE IF NOT EXISTS provinces (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    is_active   TINYINT(1) DEFAULT 1,
    is_deleted  TINYINT(1) DEFAULT 0,
    created_by  VARCHAR(100),
    created_at  DATETIME,
    updated_by  VARCHAR(100),
    updated_at  DATETIME
);

-- Table: branches
CREATE TABLE IF NOT EXISTS branches (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    is_active   TINYINT(1) DEFAULT 1,
    is_deleted  TINYINT(1) DEFAULT 0,
    province_id INT NOT NULL,
    created_by  VARCHAR(100),
    created_at  DATETIME,
    updated_by  VARCHAR(100),
    updated_at  DATETIME,
    deleted_at  DATETIME,
    CONSTRAINT fk_branch_province FOREIGN KEY (province_id) REFERENCES provinces(id)
);

-- Table: stores
CREATE TABLE IF NOT EXISTS stores (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(255) NOT NULL,
    is_active      TINYINT(1) DEFAULT 1,
    is_deleted     TINYINT(1) DEFAULT 0,
    is_whitelisted TINYINT(1) DEFAULT 0,
    branch_id      INT NOT NULL,
    created_by     VARCHAR(100),
    created_at     DATETIME,
    updated_by     VARCHAR(100),
    updated_at     DATETIME,
    CONSTRAINT fk_store_branch FOREIGN KEY (branch_id) REFERENCES branches(id)
);

-- Table: users
CREATE TABLE IF NOT EXISTS users (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    username   VARCHAR(100) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    created_at DATETIME,
    updated_at DATETIME
);

-- ============================================
-- SAMPLE DATA
-- ============================================

INSERT INTO provinces (name, is_active, is_deleted, created_by, created_at) VALUES
('DKI Jakarta',  1, 0, 'admin', NOW()),
('Jawa Barat',   1, 0, 'admin', NOW()),
('Jawa Tengah',  1, 0, 'admin', NOW()),
('Jawa Timur',   1, 0, 'admin', NOW()),
('Banten',       1, 0, 'admin', NOW());

INSERT INTO branches (name, is_active, is_deleted, province_id, created_by, created_at) VALUES
('Cabang Jakarta Pusat',   1, 0, 1, 'admin', NOW()),
('Cabang Jakarta Selatan', 1, 0, 1, 'admin', NOW()),
('Cabang Bandung',         1, 0, 2, 'admin', NOW()),
('Cabang Semarang',        1, 0, 3, 'admin', NOW()),
('Cabang Surabaya',        1, 0, 4, 'admin', NOW());

INSERT INTO stores (name, is_active, is_deleted, is_whitelisted, branch_id, created_by, created_at) VALUES
('Indomaret Thamrin',      1, 0, 0, 1, 'admin', NOW()),
('Indomaret Sudirman',     1, 0, 1, 1, 'admin', NOW()),
('Indomaret Kemang',       1, 0, 0, 2, 'admin', NOW()),
('Indomaret Braga',        1, 0, 0, 3, 'admin', NOW()),
('Indomaret Simpang Lima', 1, 0, 1, 4, 'admin', NOW()),
('Indomaret Darmo',        1, 0, 0, 5, 'admin', NOW());

INSERT INTO users (username, password, created_at) VALUES
('admin', '$2a$10$tyi7Uz/PJqzl3wDAFB4WhOVIToqduEG0vzWnLiLHdBf6J2Y5ebUSG', NOW());
-- password di atas adalah bcrypt dari: 'password123'
