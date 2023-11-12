-- SQL Server Forward Engineering

-- -----------------------------------------------------
-- Schema cineguardian
-- -----------------------------------------------------
USE cineguardian;
GO

-- -----------------------------------------------------
-- Table tb_address
-- -----------------------------------------------------
CREATE TABLE tb_address (
    id INT NOT NULL PRIMARY KEY IDENTITY(1,1),
    cep VARCHAR(255) NULL,
    neighborhood VARCHAR(255) NULL,
    number VARCHAR(255) NULL,
    street VARCHAR(255) NULL,
    compl VARCHAR(255) NULL,
    city VARCHAR(255) NULL
    );
GO

-- -----------------------------------------------------
-- Table tb_users
-- -----------------------------------------------------
CREATE TABLE tb_users (
    id INT NOT NULL PRIMARY KEY IDENTITY(1,1),
    name VARCHAR(255) NULL,
    email VARCHAR(255) NULL,
    password VARCHAR(255) NULL
    );
GO

-- -----------------------------------------------------
-- Table tb_companies
-- -----------------------------------------------------
CREATE TABLE tb_companies (
    id INT NOT NULL PRIMARY KEY IDENTITY(1,1),
    id_address INT NULL,
    tb_users_id INT NOT NULL,
    name VARCHAR(255) NULL,
    cnpj VARCHAR(255) NULL,
    CONSTRAINT fk_tb_companies_tb_users1 FOREIGN KEY (tb_users_id) REFERENCES tb_users(id),
    CONSTRAINT fk_tb_companies_tb_address1 FOREIGN KEY (id_address) REFERENCES tb_address(id)
    );
GO

-- -----------------------------------------------------
-- Table tb_cpu
-- -----------------------------------------------------
CREATE TABLE tb_cpu (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    name VARCHAR(255) NULL
    );
GO

-- -----------------------------------------------------
-- Table tb_disk
-- -----------------------------------------------------
CREATE TABLE tb_disk (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    model VARCHAR(255) NULL
    );
GO

-- -----------------------------------------------------
-- Table tb_computers
-- -----------------------------------------------------
CREATE TABLE tb_computers (
    id INT NOT NULL PRIMARY KEY IDENTITY(1,1),
    tb_companies_id INT NOT NULL,
    id_cpu VARCHAR(255) NULL,
    id_disk VARCHAR(255) NULL,
    hostname VARCHAR(255) NULL,
    maker VARCHAR(255) NULL,
    system_info VARCHAR(255) NULL,
    CONSTRAINT fk_tb_computers_tb_companies1 FOREIGN KEY (tb_companies_id) REFERENCES tb_companies(id),
    CONSTRAINT fk_tb_computers_tb_cpu1 FOREIGN KEY (id_cpu) REFERENCES tb_cpu(id),
    CONSTRAINT fk_tb_computers_tb_disk1 FOREIGN KEY (id_disk) REFERENCES tb_disk(id)
    );
GO

-- -----------------------------------------------------
-- Table tb_network
-- -----------------------------------------------------
CREATE TABLE tb_network (
    id INT NOT NULL PRIMARY KEY IDENTITY(1,1),
    id_computer INT NULL,
    name VARCHAR(255) NULL,
    mac_address VARCHAR(255) NULL,
    packages_received INT NULL,
    packages_sent INT NULL,
    CONSTRAINT fk_tb_network_tb_computers1 FOREIGN KEY (id_computer) REFERENCES tb_computers(id)
    );
GO

-- -----------------------------------------------------
-- Table tb_statistics
-- -----------------------------------------------------
CREATE TABLE tb_statistics (
    id INT NOT NULL PRIMARY KEY IDENTITY(1,1),
    id_computer INT NULL,
    temperature DOUBLE NULL,
    cpu_usage DOUBLE NULL,
    ram_usage DOUBLE NULL,
    ram_available DOUBLE NULL,
    ram_total DOUBLE NULL,
    disk_total DOUBLE NULL,
    disk_usage DOUBLE NULL,
    CONSTRAINT fk_tb_statistics_tb_computers1 FOREIGN KEY (id_computer) REFERENCES tb_computers(id)
    );
GO

-- -----------------------------------------------------
-- Table tb_alerts
-- -----------------------------------------------------
CREATE TABLE tb_alerts (
    id INT NOT NULL PRIMARY KEY IDENTITY(1,1),
    tb_companies_id INT NOT NULL,
    percentual_cpu DOUBLE NULL,
    percentual_disk DOUBLE NULL,
    percentual_ram DOUBLE NULL,
    CONSTRAINT fk_tb_alerts_tb_companies1 FOREIGN KEY (tb_companies_id) REFERENCES tb_companies(id)
    );
GO

-- Sample data (use INSERT INTO statements accordingly)

-- Select statements
SELECT * FROM tb_computers;
SELECT * FROM tb_users;
SELECT * FROM tb_companies;
SELECT * FROM tb_address;
SELECT * FROM tb_statistics;
SELECT * FROM tb_cpu;
SELECT * FROM tb_disk;
SELECT * FROM tb_network;
SELECT * FROM tb_alerts;

-- Adapted SELECT statement for SQL Server
SELECT * FROM tb_computers WHERE id_cpu = 'bfebfbff000806c1';
