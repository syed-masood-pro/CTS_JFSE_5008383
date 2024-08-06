SET SERVEROUTPUT ON SIZE 1000000;

BEGIN
    EXECUTE IMMEDIATE 'CREATE TABLE Customers (
        CustomerID NUMBER PRIMARY KEY,
        Name VARCHAR2(100),
        DOB DATE,
        Balance NUMBER,
        LastModified DATE,
        IsVIP VARCHAR2(5) DEFAULT ''FALSE''
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

    INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
    VALUES (3, 'George Martin', TO_DATE('1940-03-10', 'YYYY-MM-DD'), 12000, SYSDATE);

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

    INSERT INTO Loans (LoanID, CustomerID, LoanAmount, InterestRate, StartDate, EndDate)
    VALUES (2, 3, 8000, 7, SYSDATE, ADD_MONTHS(SYSDATE, 24));
    
    
    INSERT INTO Loans (LoanID, CustomerID, LoanAmount, InterestRate, StartDate, EndDate)
    VALUES (3, 2, 2000, 8, SYSDATE, SYSDATE + INTERVAL '15' DAY);



    INSERT INTO Employees (EmployeeID, Name, Position, Salary, Department, HireDate)
    VALUES (1, 'Alice Johnson', 'Manager', 70000, 'HR', TO_DATE('2015-06-15', 'YYYY-MM-DD'));

    INSERT INTO Employees (EmployeeID, Name, Position, Salary, Department, HireDate)
    VALUES (2, 'Bob Brown', 'Developer', 60000, 'IT', TO_DATE('2017-03-20', 'YYYY-MM-DD'));
    
    
    
    COMMIT;
END;
/

-- Scenario 1: Apply a 1% discount to loan interest rates for customers above 60 years old
BEGIN
    FOR i IN (SELECT CustomerID, DOB FROM Customers) LOOP
        IF FLOOR(MONTHS_BETWEEN(SYSDATE, i.DOB) / 12) > 60 THEN
            UPDATE Loans
            SET InterestRate = InterestRate - 1
            WHERE CustomerID = i.CustomerID;
            DBMS_OUTPUT.PUT_LINE('Updated interest rate for CustomerID: ' || i.CustomerID);
        END IF;
    END LOOP;
    
    COMMIT;
END;
/

-- Scenario 2: Set IsVIP flag to TRUE for customers with a balance over $10,000
BEGIN
    FOR i IN (SELECT CustomerID, Balance FROM Customers) LOOP
        IF i.Balance > 10000 THEN
            UPDATE Customers
            SET IsVIP = 'TRUE'
            WHERE CustomerID = i.CustomerID;
            DBMS_OUTPUT.PUT_LINE('Updated IsVIP flag for CustomerID: ' || i.CustomerID);
        END IF;
    END LOOP;
    
    COMMIT;
END;
/

-- Scenario 3: Send reminders to customers whose loans are due within the next 30 days
BEGIN
    FOR loan_rec IN (
        SELECT l.LoanID, l.CustomerID, c.Name, l.EndDate
        FROM Loans l
        JOIN Customers c ON l.CustomerID = c.CustomerID
        WHERE l.EndDate BETWEEN SYSDATE AND SYSDATE + 30
    ) LOOP
        DBMS_OUTPUT.PUT_LINE('Reminder: Dear ' || loan_rec.Name || ', your loan with ID ' || loan_rec.LoanID || ' is due on ' || TO_CHAR(loan_rec.EndDate, 'YYYY-MM-DD') || '.');
    END LOOP;
END;
/
