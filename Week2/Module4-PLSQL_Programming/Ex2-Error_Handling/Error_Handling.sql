SET SERVEROUTPUT ON SIZE 1000000;

BEGIN
    EXECUTE IMMEDIATE 'CREATE TABLE Customers (
        CustomerID NUMBER PRIMARY KEY,
        Name VARCHAR2(100),
        DOB DATE,
        Balance NUMBER,
        LastModified DATE
    )';

    EXECUTE IMMEDIATE 'CREATE TABLE Accounts (
        AccountID NUMBER PRIMARY KEY,
        CustomerID NUMBER,
        AccountType VARCHAR2(20),
        Balance NUMBER,
        LastModified DATE,
        FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
    )';

    EXECUTE IMMEDIATE 'CREATE TABLE Transactions (
        TransactionID NUMBER PRIMARY KEY,
        AccountID NUMBER,
        TransactionDate DATE,
        Amount NUMBER,
        TransactionType VARCHAR2(10),
        FOREIGN KEY (AccountID) REFERENCES Accounts(AccountID)
    )';

    EXECUTE IMMEDIATE 'CREATE TABLE Loans (
        LoanID NUMBER PRIMARY KEY,
        CustomerID NUMBER,
        LoanAmount NUMBER,
        InterestRate NUMBER,
        StartDate DATE,
        EndDate DATE,
        FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
    )';

    EXECUTE IMMEDIATE 'CREATE TABLE Employees (
        EmployeeID NUMBER PRIMARY KEY,
        Name VARCHAR2(100),
        Position VARCHAR2(50),
        Salary NUMBER,
        Department VARCHAR2(50),
        HireDate DATE
    )'; 
END;
/

BEGIN
    INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
    VALUES (1, 'John Doe', TO_DATE('1985-05-15', 'YYYY-MM-DD'), 1000, SYSDATE);

    INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
    VALUES (2, 'Jane Smith', TO_DATE('1990-07-20', 'YYYY-MM-DD'), 1500, SYSDATE);

    INSERT INTO Accounts (AccountID, CustomerID, AccountType, Balance, LastModified)
    VALUES (1, 1, 'Savings', 1000, SYSDATE);

    INSERT INTO Accounts (AccountID, CustomerID, AccountType, Balance, LastModified)
    VALUES (2, 2, 'Checking', 1500, SYSDATE);

    INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
    VALUES (1, 1, SYSDATE, 200, 'Deposit');

    INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
    VALUES (2, 2, SYSDATE, 300, 'Withdrawal');

    INSERT INTO Loans (LoanID, CustomerID, LoanAmount, InterestRate, StartDate, EndDate)
    VALUES (1, 1, 5000, 5, SYSDATE, ADD_MONTHS(SYSDATE, 60));

    INSERT INTO Employees (EmployeeID, Name, Position, Salary, Department, HireDate)
    VALUES (1, 'Alice Johnson', 'Manager', 70000, 'HR', TO_DATE('2015-06-15', 'YYYY-MM-DD'));

    INSERT INTO Employees (EmployeeID, Name, Position, Salary, Department, HireDate)
    VALUES (2, 'Bob Brown', 'Developer', 60000, 'IT', TO_DATE('2017-03-20', 'YYYY-MM-DD'));

    COMMIT;
END;
/

-- Scenario 1: SafeTransferFunds Procedure
CREATE OR REPLACE PROCEDURE SafeTransferFunds(from_account NUMBER, to_account NUMBER, amount NUMBER) IS
    insufficient_funds EXCEPTION;
BEGIN
    -- Check balance of the from account
    DECLARE
        bal NUMBER;
    BEGIN
        SELECT Balance INTO bal FROM Accounts WHERE AccountID = from_account FOR UPDATE;
        IF bal < amount THEN
            RAISE insufficient_funds;
        END IF;

        -- Deduct from the source account
        UPDATE Accounts SET Balance = Balance - amount WHERE AccountID = from_account;
        -- Add to the destination account
        UPDATE Accounts SET Balance = Balance + amount WHERE AccountID = to_account;
        
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Transfer successful.');
    EXCEPTION
        WHEN insufficient_funds THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: Insufficient funds.');
        WHEN OTHERS THEN
            ROLLBACK;
            DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
    END;
END SafeTransferFunds;
/

-- Scenario 2: UpdateSalary Procedure
CREATE OR REPLACE PROCEDURE UpdateSalary(empid NUMBER, percentage NUMBER) IS
    sal NUMBER;
    employee_not_found EXCEPTION;
BEGIN
    BEGIN
        SELECT Salary INTO sal FROM Employees WHERE EmployeeID = empid FOR UPDATE;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RAISE employee_not_found;
    END;

    -- Update the salary
    UPDATE Employees SET Salary = Salary * (1 + percentage / 100) WHERE EmployeeID = empid;
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Salary updated successfully.');
EXCEPTION
    WHEN employee_not_found THEN
        DBMS_OUTPUT.PUT_LINE('Error: Employee not found.');
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END UpdateSalary;
/

-- Scenario 3: AddNewCustomer Procedure
CREATE OR REPLACE PROCEDURE AddNewCustomer(cid NUMBER, name VARCHAR2, dob DATE, balance NUMBER) IS
    duplicate_customer EXCEPTION;
BEGIN
    INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified) VALUES (cid, name, dob, balance, SYSDATE);
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Customer added successfully.');
EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error: Customer with ID ' || cid || ' already exists.');
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END AddNewCustomer;
/

-- SafeTransferFunds Test
BEGIN
    SafeTransferFunds(1, 2, 500);
END;
/

-- UpdateSalary Test
BEGIN
    UpdateSalary(1, 10);
END;
/

-- AddNewCustomer Test
BEGIN
    AddNewCustomer(1, 'New Customer', TO_DATE('2000-01-01', 'YYYY-MM-DD'), 2000);
    AddNewCustomer(3, 'New Customer 2', TO_DATE('2000-01-01', 'YYYY-MM-DD'), 2000);
END;
/
