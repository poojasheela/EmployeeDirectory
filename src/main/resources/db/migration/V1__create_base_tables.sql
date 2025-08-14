-- Department table
CREATE TABLE IF NOT EXISTS department (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,
    created_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_updated_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

-- Employee table
CREATE TABLE IF NOT EXISTS employee (
    id BIGINT NOT NULL AUTO_INCREMENT,
    full_name VARCHAR(255) NOT NULL,
    contact_email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255),
    role VARCHAR(100),
    department_id BIGINT,
    created_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_updated_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_employee_department FOREIGN KEY (department_id)
        REFERENCES department (id)
);

-- Employee Audit table
CREATE TABLE IF NOT EXISTS employee_audit (
    id BIGINT NOT NULL AUTO_INCREMENT,
    employee_id BIGINT,
    full_name VARCHAR(255),
    contact_email VARCHAR(255),
    role VARCHAR(100),
    department_id BIGINT,
    operation_type VARCHAR(10),
    created_by VARCHAR(255),
    performed_by VARCHAR(255),
    created_timestamp TIMESTAMP,
    last_updated_timestamp TIMESTAMP,
    PRIMARY KEY (id)
);

-- Department Audit table
CREATE TABLE IF NOT EXISTS department_audit (
    id BIGINT NOT NULL AUTO_INCREMENT,
    department_id BIGINT,
    name VARCHAR(255),
    operation_type VARCHAR(10),
    created_by VARCHAR(255),
    performed_by VARCHAR(255),
    created_timestamp TIMESTAMP,
    last_updated_timestamp TIMESTAMP,
    PRIMARY KEY (id)
);
