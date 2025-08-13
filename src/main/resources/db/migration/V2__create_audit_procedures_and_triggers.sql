-- Drop procedures if they exist
DROP PROCEDURE IF EXISTS log_employee_audit;
DROP PROCEDURE IF EXISTS log_department_audit;

-- Drop triggers if they exist
DROP TRIGGER IF EXISTS trg_employee_insert;
DROP TRIGGER IF EXISTS trg_employee_update;
DROP TRIGGER IF EXISTS trg_employee_delete;
DROP TRIGGER IF EXISTS trg_department_insert;
DROP TRIGGER IF EXISTS trg_department_update;
DROP TRIGGER IF EXISTS trg_department_delete;

-- Stored procedure for employee audit
DELIMITER //
CREATE PROCEDURE log_employee_audit (
    IN p_employee_id INT,
    IN p_full_name VARCHAR(255),
    IN p_contact_email VARCHAR(255),
    IN p_role VARCHAR(100),
    IN p_department_id INT,
    IN p_operation_type VARCHAR(10),
    IN p_performed_by VARCHAR(255)
)
BEGIN
    INSERT INTO employee_audit (
        employee_id, full_name, contact_email, role, department_id,
        operation_type, performed_by
    )
    VALUES (
        p_employee_id, p_full_name, p_contact_email, p_role, p_department_id,
        p_operation_type, p_performed_by
    );
END//
DELIMITER ;

-- Stored procedure for department audit
DELIMITER //
CREATE PROCEDURE log_department_audit (
    IN p_department_id INT,
    IN p_name VARCHAR(255),
    IN p_operation_type VARCHAR(10),
    IN p_performed_by VARCHAR(255)
)
BEGIN
    INSERT INTO department_audit (
        department_id, name, operation_type, performed_by
    )
    VALUES (
        p_department_id, p_name, p_operation_type, p_performed_by
    );
END//
DELIMITER ;

-- Employee triggers
DELIMITER //
CREATE TRIGGER trg_employee_insert
AFTER INSERT ON employee
FOR EACH ROW
BEGIN
    CALL log_employee_audit(
        NEW.id, NEW.full_name, NEW.contact_email, NEW.role, NEW.department_id,
        'INSERT', @logged_user
    );
END//

CREATE TRIGGER trg_employee_update
AFTER UPDATE ON employee
FOR EACH ROW
BEGIN
    CALL log_employee_audit(
        NEW.id, NEW.full_name, NEW.contact_email, NEW.role, NEW.department_id,
        'UPDATE', @logged_user
    );
END//

CREATE TRIGGER trg_employee_delete
AFTER DELETE ON employee
FOR EACH ROW
BEGIN
    CALL log_employee_audit(
        OLD.id, OLD.full_name, OLD.contact_email, OLD.role, OLD.department_id,
        'DELETE', @logged_user
    );
END//
DELIMITER ;

-- Department triggers
DELIMITER //
CREATE TRIGGER trg_department_insert
AFTER INSERT ON department
FOR EACH ROW
BEGIN
    CALL log_department_audit(
        NEW.id, NEW.name, 'INSERT', @logged_user
    );
END//

CREATE TRIGGER trg_department_update
AFTER UPDATE ON department
FOR EACH ROW
BEGIN
    CALL log_department_audit(
        NEW.id, NEW.name, 'UPDATE', @logged_user
    );
END//

CREATE TRIGGER trg_department_delete
AFTER DELETE ON department
FOR EACH ROW
BEGIN
    CALL log_department_audit(
        OLD.id, OLD.name, 'DELETE', @logged_user
    );
END//
DELIMITER ;
