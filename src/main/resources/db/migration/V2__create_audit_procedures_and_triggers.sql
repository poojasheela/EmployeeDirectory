-- =====================================
-- Audit Triggers for Employee & Department
-- =====================================

-- Drop existing triggers to avoid duplicates
DROP TRIGGER IF EXISTS trg_employee_insert;
DROP TRIGGER IF EXISTS trg_employee_update;
DROP TRIGGER IF EXISTS trg_employee_delete;
DROP TRIGGER IF EXISTS trg_department_insert;
DROP TRIGGER IF EXISTS trg_department_update;
DROP TRIGGER IF EXISTS trg_department_delete;

DELIMITER $$

-- ==========================
-- Employee Triggers
-- ==========================

CREATE TRIGGER trg_employee_insert
AFTER INSERT ON employee
FOR EACH ROW
BEGIN
    INSERT INTO employee_audit (
        employee_id, full_name, contact_email, role, department_id,
        operation_type, performed_by, created_timestamp, last_updated_timestamp
    )
    VALUES (
        NEW.id, NEW.full_name, NEW.contact_email, NEW.role, NEW.department_id,
        'INSERT',
        COALESCE(@logged_user, 'system'),
        NOW(),
        NOW()
    );
END$$

CREATE TRIGGER trg_employee_update
AFTER UPDATE ON employee
FOR EACH ROW
BEGIN
    INSERT INTO employee_audit (
        employee_id, full_name, contact_email, role, department_id,
        operation_type, performed_by, created_timestamp, last_updated_timestamp
    )
    VALUES (
        NEW.id, NEW.full_name, NEW.contact_email, NEW.role, NEW.department_id,
        'UPDATE',
        COALESCE(@logged_user, 'system'),
        OLD.created_timestamp,
        NOW()
    );
END$$

CREATE TRIGGER trg_employee_delete
AFTER DELETE ON employee
FOR EACH ROW
BEGIN
    INSERT INTO employee_audit (
        employee_id, full_name, contact_email, role, department_id,
        operation_type, performed_by, created_timestamp, last_updated_timestamp
    )
    VALUES (
        OLD.id, OLD.full_name, OLD.contact_email, OLD.role, OLD.department_id,
        'DELETE',
        COALESCE(@logged_user, 'system'),
        OLD.created_timestamp,
        NOW()
    );
END$$

-- ==========================
-- Department Triggers
-- ==========================

CREATE TRIGGER trg_department_insert
AFTER INSERT ON department
FOR EACH ROW
BEGIN
    INSERT INTO department_audit (
        department_id, name, operation_type, performed_by, created_timestamp, last_updated_timestamp
    )
    VALUES (
        NEW.id, NEW.name,
        'INSERT',
        COALESCE(@logged_user, 'system'),
        NOW(),
        NOW()
    );
END$$

CREATE TRIGGER trg_department_update
AFTER UPDATE ON department
FOR EACH ROW
BEGIN
    INSERT INTO department_audit (
        department_id, name, operation_type, performed_by, created_timestamp, last_updated_timestamp
    )
    VALUES (
        NEW.id, NEW.name,
        'UPDATE',
        COALESCE(@logged_user, 'system'),
        OLD.created_timestamp,
        NOW()
    );
END$$

CREATE TRIGGER trg_department_delete
AFTER DELETE ON department
FOR EACH ROW
BEGIN
    INSERT INTO department_audit (
        department_id, name, operation_type, performed_by, created_timestamp, last_updated_timestamp
    )
    VALUES (
        OLD.id, OLD.name,
        'DELETE',
        COALESCE(@logged_user, 'system'),
        OLD.created_timestamp,
        NOW()
    );
END$$

DELIMITER ;
