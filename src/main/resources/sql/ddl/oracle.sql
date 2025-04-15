CREATE TABLE customers (
    customer_id INT NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    payment_method VARCHAR(50)
)

ALTER TABLE customers
ADD CONSTRAINT PK_customers PRIMARY KEY(customer_id)


CREATE SEQUENCE seq_customers START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER tgr_customers_seq
BEFORE INSERT ON customers
FOR EACH ROW
BEGIN
  IF :NEW.customer_id IS NULL THEN
    SELECT seq_customers.NEXTVAL INTO :NEW.customer_id FROM dual;
  END IF;
END;
