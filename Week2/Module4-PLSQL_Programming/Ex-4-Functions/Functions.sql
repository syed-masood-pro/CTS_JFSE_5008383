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
        HireDate DATE,
        LastModified DATE
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

    INSERT INTO Employees (EmployeeID, Name, Position, Salary, Department, HireDate, LastModified)
    VALUES (1, 'Alice Johnson', 'Manager', 70000, 'HR', TO_DATE('2015-06-15', 'YYYY-MM-DD'), SYSDATE);

    INSERT INTO Employees (EmployeeID, Name, Position, Salary, Department, HireDate, LastModified)
    VALUES (2, 'Bob Brown', 'Developer', 60000, 'IT', TO_DATE('2017-03-20', 'YYYY-MM-DD'), SYSDATE);

    COMMIT;
END;
/

-- Scenario 1: CalculateAge Function
CREATE OR REPLACE FUNCTION CalculateAge(dob DATE) RETURN NUMBER IS
    age NUMBER;
BEGIN
    age := TRUNC((SYSDATE - dob) / 365.25);
    RETURN age;
END CalculateAge;
/

-- Scenario 2: CalculateMonthlyInstallment Function
CREATE OR REPLACE FUNCTION CalculateMonthlyInstallment(loan_amount NUMBER, interest_rate NUMBER, duration_years NUMBER) RETURN NUMBER IS
    monthly_rate NUMBER;
    num_payments NUMBER;
    monthly_payment NUMBER;
BEGIN
    monthly_rate := interest_rate / 12 / 100;
    num_payments := duration_years * 12;
    monthly_payment := loan_amount * monthly_rate / (1 - POWER((1 + monthly_rate), -num_payments));
    RETURN monthly_payment;
END CalculateMonthlyInstallment;
/

-- Scenario 3: HasSufficientBalance Function
CREATE OR REPLACE FUNCTION HasSufficientBalance(account_id NUMBER, amount NUMBER) RETURN BOOLEAN IS
    balance NUMBER;
BEGIN
    SELECT Balance INTO balance FROM Accounts WHERE AccountID = account_id;
    IF balance >= amount THEN
        RETURN TRUE;
    ELSE
        RETURN FALSE;
    END IF;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN FALSE;
    WHEN OTHERS THEN
        RETURN FALSE;
END HasSufficientBalance;
/


-- Test CalculateAge
DECLARE
    age NUMBER;
BEGIN
    age := CalculateAge(TO_DATE('1985-05-15', 'YYYY-MM-DD'));
    DBMS_OUTPUT.PUT_LINE('Age: ' || age);
END;
/

-- Test CalculateMonthlyInstallment
DECLARE
    monthly_installment NUMBER;
BEGIN
    monthly_installment := CalculateMonthlyInstallment(5000, 5, 5);
    DBMS_OUTPUT.PUT_LINE('Monthly Installment: ' || monthly_installment);
END;
/

-- Test HasSufficientBalance
DECLARE
    has_balance BOOLEAN;
BEGIN
    has_balance := HasSufficientBalance(1, 500);
    IF has_balance THEN
        DBMS_OUTPUT.PUT_LINE('Sufficient balance.');
    ELSE
        DBMS_OUTPUT.PUT_LINE('Insufficient balance.');
    END IF;
END;
/


