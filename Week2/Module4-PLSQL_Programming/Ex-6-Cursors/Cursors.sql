SET SERVEROUTPUT ON SIZE 1000000;

CREATE TABLE Customers (
    CustomerID NUMBER PRIMARY KEY,
    Name VARCHAR2(100),
    DOB DATE,
    Balance NUMBER,
    LastModified DATE
);

CREATE TABLE Accounts (
    AccountID NUMBER PRIMARY KEY,
    CustomerID NUMBER,
    AccountType VARCHAR2(20),
    Balance NUMBER,
    LastModified DATE,
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
);

CREATE TABLE Transactions (
    TransactionID NUMBER PRIMARY KEY,
    AccountID NUMBER,
    TransactionDate DATE,
    Amount NUMBER,
    TransactionType VARCHAR2(10),
    FOREIGN KEY (AccountID) REFERENCES Accounts(AccountID)
);

CREATE TABLE Loans (
    LoanID NUMBER PRIMARY KEY,
    CustomerID NUMBER,
    LoanAmount NUMBER,
    InterestRate NUMBER,
    StartDate DATE,
    EndDate DATE,
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
);

CREATE TABLE Employees (
    EmployeeID NUMBER PRIMARY KEY,
    Name VARCHAR2(100),
    Position VARCHAR2(50),
    Salary NUMBER,
    Department VARCHAR2(50),
    HireDate DATE
);

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

-- Scenario 1: Generate monthly statements for all customers
DECLARE
    CURSOR GenerateMonthlyStatements IS
        SELECT c.CustomerID, c.Name, t.TransactionDate, t.Amount, t.TransactionType
        FROM Customers c
        JOIN Accounts a ON c.CustomerID = a.CustomerID
        JOIN Transactions t ON a.AccountID = t.AccountID
        WHERE t.TransactionDate BETWEEN TRUNC(SYSDATE, 'MM') AND LAST_DAY(SYSDATE);
BEGIN
    FOR i IN GenerateMonthlyStatements LOOP
        DBMS_OUTPUT.PUT_LINE('Customer ID: ' || i.CustomerID);
        DBMS_OUTPUT.PUT_LINE('Customer Name: ' || i.Name);
        DBMS_OUTPUT.PUT_LINE('Transaction Date: ' || i.TransactionDate);
        DBMS_OUTPUT.PUT_LINE('Amount: ' || i.Amount);
        DBMS_OUTPUT.PUT_LINE('Transaction Type: ' || i.TransactionType);
        DBMS_OUTPUT.PUT_LINE('------------------------------');
    END LOOP;
END;
/

-- Scenario 2: Apply annual fee to all accounts
DECLARE
    CURSOR ApplyAnnualFee IS
        SELECT AccountID, Balance
        FROM Accounts;
    annual_fee CONSTANT NUMBER := 50;
BEGIN
    FOR i IN ApplyAnnualFee LOOP
        UPDATE Accounts
        SET Balance = Balance - annual_fee,
            LastModified = SYSDATE
        WHERE AccountID = i.AccountID;
        DBMS_OUTPUT.PUT_LINE('Applied annual fee to Account ID: ' || i.AccountID || ', New Balance: ' || (i.Balance - annual_fee));
    END LOOP;
END;
/

-- Scenario 3: Update the interest rate for all loans based on a new policy
DECLARE
    CURSOR UpdateLoanInterestRates IS
        SELECT LoanID, InterestRate
        FROM Loans;
    new_interest_rate CONSTANT NUMBER := 4.5; 
BEGIN
    FOR i IN UpdateLoanInterestRates LOOP
        UPDATE Loans
        SET InterestRate = new_interest_rate
        WHERE LoanID = i.LoanID;
        DBMS_OUTPUT.PUT_LINE('Updated Interest Rate for Loan ID: ' || i.LoanID || ', New Interest Rate: ' || new_interest_rate);
    END LOOP;
END;
/
