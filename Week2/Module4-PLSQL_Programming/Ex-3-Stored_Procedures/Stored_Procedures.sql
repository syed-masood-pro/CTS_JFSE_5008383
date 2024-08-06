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

-- Scenario 1: ProcessMonthlyInterest Procedure
CREATE OR REPLACE PROCEDURE ProcessMonthlyInterest IS
BEGIN
    FOR i IN (SELECT AccountID, Balance FROM Accounts WHERE AccountType = 'Savings') LOOP
        UPDATE Accounts
        SET Balance = Balance + (Balance * 0.01),
            LastModified = SYSDATE
        WHERE AccountID = i.AccountID;
    END LOOP;
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Monthly interest processed for all savings accounts.');
END ProcessMonthlyInterest;
/

-- Scenario 2: UpdateEmployeeBonus Procedure
CREATE OR REPLACE PROCEDURE UpdateEmployeeBonus(department VARCHAR2, bonus_percentage NUMBER) IS
BEGIN
    FOR i IN (SELECT EmployeeID, Salary FROM Employees WHERE Department = department) LOOP
        UPDATE Employees
        SET Salary = Salary + (Salary * (bonus_percentage / 100))
        WHERE EmployeeID = i.EmployeeID;
    END LOOP;
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Employee bonuses updated for department: ' || department);
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END UpdateEmployeeBonus;
/

-- Scenario 3: TransferFunds Procedure
CREATE OR REPLACE PROCEDURE TransferFunds(from_account NUMBER, to_account NUMBER, amount NUMBER) IS
    insufficient_funds EXCEPTION;
BEGIN
    -- Check balance of the from account
    DECLARE
        balance NUMBER;
    BEGIN
        SELECT Balance INTO balance FROM Accounts WHERE AccountID = from_account FOR UPDATE;
        IF balance < amount THEN
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
END TransferFunds;
/

-- ProcessMonthlyInterest Test
BEGIN
    ProcessMonthlyInterest;
END;
/

-- UpdateEmployeeBonus Test
BEGIN
    UpdateEmployeeBonus('HR', 10);
END;
/

-- TransferFunds Test
BEGIN
    TransferFunds(1, 2, 500);
END;
/
