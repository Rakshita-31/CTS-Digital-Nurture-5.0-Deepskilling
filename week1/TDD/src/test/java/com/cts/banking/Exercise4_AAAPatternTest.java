package com.cts.banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*
 * Exercise 4: Arrange-Act-Assert (AAA) Pattern, Test Fixtures, Setup and
 * Teardown Methods in JUnit
 *
 * AAA pattern: every test is split into three clear sections -
 *   Arrange: set up the objects and data the test needs
 *   Act:     perform the one action actually being tested
 *   Assert:  check the outcome matches what's expected
 * This keeps tests readable and makes it obvious what's actually being
 * verified versus what's just setup noise.
 *
 * Test fixtures: the common starting state several tests share (here, a
 * fresh BankAccount with a known balance) is called a fixture. Rather than
 * re-creating it inside every single test method, @BeforeEach builds it
 * once before each test runs.
 *
 * Setup/teardown: in JUnit 4 these were @Before and @After. In JUnit 5,
 * they're renamed to @BeforeEach and @AfterEach (since JUnit 5 also added
 * @BeforeAll/@AfterAll for setup that runs once per test class instead of
 * once per test method).
 */
public class Exercise4_AAAPatternTest {

    private BankAccount account;

    @BeforeEach
    public void setUp() {
        // Runs before every single test method below, giving each test a
        // fresh, known starting point instead of leftover state from a
        // previous test
        account = new BankAccount(1000.0);
        System.out.println("setUp: created a fresh BankAccount with balance 1000.0");
    }

    @AfterEach
    public void tearDown() {
        // Runs after every test method - useful for cleanup (closing files,
        // releasing connections, etc.). Here there's nothing external to
        // release, so it just logs that cleanup happened.
        System.out.println("tearDown: test finished, discarding this BankAccount instance");
        account = null;
    }

    @Test
    public void testDeposit_increasesBalance() {
        // Arrange: the fixture from setUp() already gave us a BankAccount
        // with balance 1000.0 - no extra arrangement needed here
        double depositAmount = 250.0;

        // Act
        account.deposit(depositAmount);

        // Assert
        assertEquals(1250.0, account.getBalance(), "Balance should increase by the deposited amount");
    }

    @Test
    public void testWithdraw_decreasesBalance() {
        // Arrange
        double withdrawAmount = 400.0;

        // Act
        account.withdraw(withdrawAmount);

        // Assert
        assertEquals(600.0, account.getBalance(), "Balance should decrease by the withdrawn amount");
    }

    @Test
    public void testWithdraw_throwsWhenBalanceInsufficient() {
        // Arrange
        double tooMuch = 5000.0;

        // Act + Assert combined here, since the "action" is the act of
        // calling withdraw() and catching what it throws
        IllegalStateException thrown = assertThrows(IllegalStateException.class,
            () -> account.withdraw(tooMuch));

        assertEquals("Insufficient balance", thrown.getMessage());
    }

    @Test
    public void testDeposit_rejectsNonPositiveAmount() {
        // Arrange
        double invalidAmount = -50.0;

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> account.deposit(invalidAmount));
    }
}
