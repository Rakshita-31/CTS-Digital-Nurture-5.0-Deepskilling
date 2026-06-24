package com.cts.banking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 * Exercise 1: Setting Up JUnit
 *
 * Steps actually performed for this exercise:
 * 1. Created a Maven project (see pom.xml at the project root).
 * 2. Added the JUnit 5 (JUnit Jupiter) dependency to pom.xml:
 *
 *      <dependency>
 *          <groupId>org.junit.jupiter</groupId>
 *          <artifactId>junit-jupiter</artifactId>
 *          <version>5.10.2</version>
 *          <scope>test</scope>
 *      </dependency>
 *
 *    Note: the original exercise sheet shows the JUnit 4 dependency
 *    (junit:junit:4.13.2). This project uses JUnit 5 instead, since that's
 *    the version this module's tracker (TDD using JUnit5 and Mockito) calls
 *    for. The two main differences that show up across these exercises:
 *      - JUnit 4 uses org.junit.Test; JUnit 5 uses org.junit.jupiter.api.Test
 *      - JUnit 4 uses @Before/@After; JUnit 5 uses @BeforeEach/@AfterEach
 * 3. Created this test class (BankAccountSetupTest) to confirm everything
 *    is wired correctly.
 *
 * This first test is intentionally trivial - its only job is to prove that
 * JUnit is correctly set up and that Maven can find and run it.
 */
public class Exercise1_SetupTest {

    @Test
    public void testJUnitIsSetUpCorrectly() {
        // If this test runs and passes, the project, dependency, and test
        // runner are all correctly configured.
        assertEquals(2, 1 + 1, "Basic arithmetic should hold - confirms JUnit runs correctly");
    }

    @Test
    public void testBankAccountClassIsReachable() {
        // Confirms the test class can actually see and use the class under test
        BankAccount account = new BankAccount(100.0);
        assertEquals(100.0, account.getBalance(), "Newly created account should hold its initial balance");
    }
}
