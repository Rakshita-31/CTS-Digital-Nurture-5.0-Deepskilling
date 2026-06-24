package com.cts.banking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Exercise 1: Logging Error Messages and Warning Levels
 *
 * SLF4J (Simple Logging Facade for Java) is a thin abstraction layer over
 * an actual logging implementation - here, Logback. The code only ever
 * talks to the SLF4J Logger interface; it never imports anything from
 * Logback directly. This means the underlying logging library could be
 * swapped out later (e.g., for Log4j2) without changing a single line in
 * this class - only the dependency and configuration would change.
 *
 * Log levels (from least to most severe): TRACE, DEBUG, INFO, WARN, ERROR.
 * This exercise focuses on the two most severe levels:
 *   - WARN: something unexpected happened, but the application can still
 *     continue running (e.g., a balance is getting low, a retry occurred)
 *   - ERROR: something failed outright and likely needs attention
 *     (e.g., a transaction couldn't complete)
 *
 * Logback (the implementation backing SLF4J here) looks for a
 * logback.xml file on the classpath to configure where logs go and how
 * they're formatted. Without one, it falls back to a sensible default
 * (printing to the console), which is enough for this exercise.
 */
public class LoggingExample {

    // The Logger is tied to this specific class - log output will be
    // tagged with "com.cts.banking.LoggingExample" so it's clear exactly
    // where each log line came from, even in a large application with many
    // classes logging at once
    private static final Logger logger = LoggerFactory.getLogger(LoggingExample.class);

    public static void main(String[] args) {

        // --- WARN level examples ---
        // Used for situations that are unusual but not breaking - the
        // application keeps running, but someone should probably notice
        double accountBalance = 150.0;
        logger.warn("Account balance is low: {}", accountBalance);

        int retryAttempt = 2;
        logger.warn("Retrying transaction after failed attempt, attempt number {}", retryAttempt);

        // --- ERROR level examples ---
        // Used when something has genuinely gone wrong and the operation
        // could not complete as expected
        logger.error("Failed to process withdrawal: insufficient funds");

        String accountId = "ACC-1042";
        logger.error("Transaction failed for account {}: connection to payment gateway timed out", accountId);

        // Demonstrates logging an actual exception alongside an error
        // message - SLF4J's error(String, Throwable) overload prints the
        // full stack trace after the message, which is invaluable for
        // diagnosing what actually went wrong
        try {
            processTransaction(-50.0);
        } catch (IllegalArgumentException ex) {
            logger.error("Transaction processing failed due to invalid input", ex);
        }
    }

    // A small helper just to give the try/catch block above something
    // realistic to fail on
    private static void processTransaction(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Transaction amount must be positive, got: " + amount);
        }
    }
}
