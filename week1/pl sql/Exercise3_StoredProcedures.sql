/*
 * Exercise 3: Stored Procedures
 *
 * A stored procedure packages a piece of business logic (like "apply
 * monthly interest" or "transfer funds") into a named, reusable unit that
 * lives inside the database itself. Instead of an application repeating the
 * same multi-step SQL logic every time it needs to do this, it just calls
 * the procedure by name. This keeps the logic consistent no matter which
 * application or script calls it, and keeps that logic close to the data
 * it operates on.
 *
 * Run 00_schema_setup.sql first before running this file.
 */

SET SERVEROUTPUT ON;

-- ============================================================
-- Scenario 1: ProcessMonthlyInterest
-- Applies 1% interest to every Savings account's current balance.
-- ============================================================
CREATE OR REPLACE PROCEDURE ProcessMonthlyInterest IS
BEGIN
    FOR acct_rec IN (SELECT AccountID, Balance FROM Accounts WHERE AccountType = 'Savings') LOOP
        UPDATE Accounts
        SET Balance = Balance + (Balance * 0.01),
            LastModified = SYSDATE
        WHERE AccountID = acct_rec.AccountID;

        DBMS_OUTPUT.PUT_LINE('Account ' || acct_rec.AccountID ||
            ' balance updated from ' || acct_rec.Balance ||
            ' to ' || (acct_rec.Balance * 1.01));
    END LOOP;
    COMMIT;
END ProcessMonthlyInterest;
/

-- ============================================================
-- Scenario 2: UpdateEmployeeBonus
-- Adds a bonus percentage (passed as a parameter) to the salary of every
-- employee in a given department.
-- ============================================================
CREATE OR REPLACE PROCEDURE UpdateEmployeeBonus (
    p_department IN Employees.Department%TYPE,
    p_bonus_percent IN NUMBER
) IS
BEGIN
    UPDATE Employees
    SET Salary = Salary + (Salary * p_bonus_percent / 100)
    WHERE Department = p_department;

    IF SQL%ROWCOUNT = 0 THEN
        DBMS_OUTPUT.PUT_LINE('No employees found in department: ' || p_department);
    ELSE
        DBMS_OUTPUT.PUT_LINE('Applied ' || p_bonus_percent ||
            '% bonus to ' || SQL%ROWCOUNT || ' employee(s) in department ' || p_department);
    END IF;
    COMMIT;
END UpdateEmployeeBonus;
/

-- ============================================================
-- Scenario 3: TransferFunds
-- Transfers a specified amount from one account to another, but only
-- after confirming the source account has sufficient balance.
-- ============================================================
CREATE OR REPLACE PROCEDURE TransferFunds (
    p_from_account IN Accounts.AccountID%TYPE,
    p_to_account IN Accounts.AccountID%TYPE,
    p_amount IN NUMBER
) IS
    v_from_balance NUMBER;
BEGIN
    SELECT Balance INTO v_from_balance
    FROM Accounts
    WHERE AccountID = p_from_account;

    IF v_from_balance < p_amount THEN
        DBMS_OUTPUT.PUT_LINE('Transfer failed: insufficient balance in account ' || p_from_account);
        RETURN;
    END IF;

    UPDATE Accounts SET Balance = Balance - p_amount WHERE AccountID = p_from_account;
    UPDATE Accounts SET Balance = Balance + p_amount WHERE AccountID = p_to_account;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Transferred ' || p_amount ||
        ' from account ' || p_from_account || ' to account ' || p_to_account);

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('Transfer failed: account ' || p_from_account || ' does not exist');
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Transfer failed due to an unexpected error: ' || SQLERRM);
END TransferFunds;
/

-- ============================================================
-- Test calls - run these to see each procedure in action
-- ============================================================

SELECT AccountID, AccountType, Balance FROM Accounts;

EXEC ProcessMonthlyInterest;

SELECT AccountID, AccountType, Balance FROM Accounts;

EXEC UpdateEmployeeBonus('IT', 10);

SELECT EmployeeID, Name, Department, Salary FROM Employees;

-- Should succeed (account 1 has enough balance)
EXEC TransferFunds(1, 2, 500);

-- Should fail gracefully (account 2 won't have enough balance for this)
EXEC TransferFunds(2, 1, 5000);

SELECT AccountID, Balance FROM Accounts;
