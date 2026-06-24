package com.cts.banking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*
 * Exercise 3: Assertions in JUnit
 *
 * JUnit's Assertions class is how a test actually declares "this is what I
 * expect" and fails loudly if reality doesn't match. Each assertion below
 * is applied to the real BankAccount class (instead of plain arithmetic) so
 * the exercise also doubles as meaningful test coverage, not just a syntax
 * demo.
 */
public class Exercise3_AssertionsTest {

    @Test
    public void testAssertEquals() {
        BankAccount account = new BankAccount(500.0);
        account.deposit(250.0);
        // assertEquals(expected, actual) - checks the two values are equal
        assertEquals(750.0, account.getBalance(), "Balance should reflect the deposit");
    }

    @Test
    public void testAssertTrue() {
        BankAccount account = new BankAccount(1000.0);
        // assertTrue(condition) - checks the condition evaluates to true
        assertTrue(account.getBalance() > 500, "Balance should be greater than 500");
    }

    @Test
    public void testAssertFalse() {
        BankAccount account = new BankAccount(100.0);
        // assertFalse(condition) - checks the condition evaluates to false
        assertFalse(account.getBalance() > 500, "Balance should not exceed 500 here");
    }

    @Test
    public void testAssertNull() {
        // Demonstrates assertNull using a lookup that legitimately has no result,
        // rather than just testing a bare null literal
        String missingAccountHolder = findAccountHolderName(null);
        assertNull(missingAccountHolder, "No account holder name should be found for a null lookup");
    }

    @Test
    public void testAssertNotNull() {
        BankAccount account = new BankAccount(0.0);
        // assertNotNull(object) - checks the reference is not null
        assertNotNull(account, "A newly constructed BankAccount should never be null");
    }

    @Test
    public void testAssertThrows() {
        BankAccount account = new BankAccount(100.0);
        // assertThrows is a JUnit 5 addition (no direct JUnit 4 equivalent
        // without @Test(expected = ...)) - it confirms the exact exception
        // type is thrown when withdrawing more than the available balance
        assertThrows(IllegalStateException.class, () -> account.withdraw(500.0),
            "Withdrawing more than the balance should throw IllegalStateException");
    }

    @Test
    public void testAssertAll_groupedAssertions() {
        BankAccount account = new BankAccount(200.0);
        account.deposit(50.0);

        // assertAll groups multiple related assertions together: if one
        // fails, JUnit still runs and reports the others, instead of
        // stopping at the first failure like separate assertions would
        assertAll("account state after deposit",
            () -> assertEquals(250.0, account.getBalance()),
            () -> assertTrue(account.getBalance() > 0),
            () -> assertNotNull(account)
        );
    }

    // Helper used by testAssertNull - simulates a lookup that returns no
    // result when given a null/unknown account reference
    private String findAccountHolderName(BankAccount account) {
        if (account == null) {
            return null;
        }
        return "Unknown";
    }
}
