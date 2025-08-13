-- Create Employee Audit table
CREATE TABLE IF NOT EXISTS employee_audit (
    id BIGINT NOT NULL AUTO_INCREMENT,
    employee_id BIGINT,
    full_name VARCHAR(255),
    contact_email VARCHAR(255),
    role VARCHAR(100),
    department_id BIGINT,
    operation_type VARCHAR(10),
    performed_by VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

-- Create Department Audit table
CREATE TABLE IF NOT EXISTS department_audit (
    id BIGINT NOT NULL AUTO_INCREMENT,
    department_id BIGINT,
    name VARCHAR(255),
    operation_type VARCHAR(10),
    performed_by VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);
