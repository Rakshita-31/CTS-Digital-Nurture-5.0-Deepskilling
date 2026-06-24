/*
 * Exercise 1: Control Structures
 *
 * Control structures (IF/ELSE, LOOP) let a PL/SQL block make decisions and
 * repeat actions based on data, rather than just running a single fixed
 * SQL statement. Here, each scenario loops through rows from a table,
 * checks a condition per row, and takes an action only when that condition
 * is true - something a single UPDATE statement with a WHERE clause could
 * also do for simple cases, but explicit loops make the per-row decision
 * logic clearer and allow more complex branching if needed later.
 *
 * Run 00_schema_setup.sql first before running this file.
 */

SET SERVEROUTPUT ON;

-- ============================================================
-- Scenario 1: Apply a 1% discount to loan interest rates for
-- customers above 60 years old.
-- ============================================================
DECLARE
    v_age NUMBER;
BEGIN
    FOR cust_rec IN (SELECT CustomerID, Name, DOB FROM Customers) LOOP
        -- MONTHS_BETWEEN gives age in months; dividing by 12 and rounding
        -- down via TRUNC gives a whole-number age in years
        v_age := TRUNC(MONTHS_BETWEEN(SYSDATE, cust_rec.DOB) / 12);

        IF v_age > 60 THEN
            UPDATE Loans
            SET InterestRate = InterestRate - (InterestRate * 0.01)
            WHERE CustomerID = cust_rec.CustomerID;

            DBMS_OUTPUT.PUT_LINE('Applied 1% senior discount for ' ||
                cust_rec.Name || ' (age ' || v_age || ')');
        END IF;
    END LOOP;
    COMMIT;
END;
/

-- ============================================================
-- Scenario 2: Set IsVIP to 'Y' for customers with balance over $10,000.
-- ============================================================
BEGIN
    FOR cust_rec IN (SELECT CustomerID, Name, Balance FROM Customers) LOOP
        IF cust_rec.Balance > 10000 THEN
            UPDATE Customers
            SET IsVIP = 'Y'
            WHERE CustomerID = cust_rec.CustomerID;

            DBMS_OUTPUT.PUT_LINE('Customer ' || cust_rec.Name ||
                ' promoted to VIP (balance ' || cust_rec.Balance || ')');
        END IF;
    END LOOP;
    COMMIT;
END;
/

-- ============================================================
-- Scenario 3: Print a reminder for loans due within the next 30 days.
-- ============================================================
BEGIN
    FOR loan_rec IN (
        SELECT l.LoanID, c.Name, l.EndDate
        FROM Loans l
        JOIN Customers c ON l.CustomerID = c.CustomerID
        WHERE l.EndDate BETWEEN SYSDATE AND SYSDATE + 30
    ) LOOP
        DBMS_OUTPUT.PUT_LINE('Reminder: Loan ' || loan_rec.LoanID ||
            ' for customer ' || loan_rec.Name ||
            ' is due on ' || TO_CHAR(loan_rec.EndDate, 'YYYY-MM-DD'));
    END LOOP;
END;
/

-- Verify results
SELECT LoanID, CustomerID, InterestRate FROM Loans;
SELECT CustomerID, Name, Balance, IsVIP FROM Customers;
