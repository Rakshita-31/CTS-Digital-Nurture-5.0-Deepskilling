import java.time.LocalDateTime;
import java.util.Scanner;

/*
 * Exercise 1: Implementing the Singleton Pattern
 *
 * Scenario: a logging utility needs exactly one instance throughout the
 * application's lifecycle, so every part of the app writes to the same
 * logger rather than creating competing instances with their own state.
 *
 * How the Singleton pattern enforces this:
 * - The constructor is private, so no other class can do `new Logger()`.
 * - A single static field holds the one instance that ever gets created.
 * - The static getInstance() method is the only way to get that instance -
 *   it creates it the first time it's called, then just returns the same
 *   object on every later call.
 *
 * This implementation uses "double-checked locking" so it's safe even if
 * multiple threads call getInstance() at the exact same time (which a naive
 * if (instance == null) check, without synchronization, would not handle
 * correctly under concurrent access).
 */

class Logger {

    // The one and only instance, created lazily on first use
    private static volatile Logger instance;

    // Tracks how many log messages have passed through this logger,
    // just to make it easy to prove it's the same object being reused
    private int logCount = 0;

    // Private constructor - blocks any class outside Logger from
    // instantiating it directly with `new Logger()`
    private Logger() {
        System.out.println("Logger instance created. (This should only print once.)");
    }

    // Public access point - the only way to obtain the Logger instance.
    // Synchronized + double-checked so concurrent calls can't accidentally
    // create two instances in a race condition.
    public static Logger getInstance() {
        if (instance == null) {
            synchronized (Logger.class) {
                if (instance == null) {
                    instance = new Logger();
                }
            }
        }
        return instance;
    }

    public void log(String message) {
        logCount++;
        System.out.println("[" + LocalDateTime.now() + "] LOG #" + logCount + ": " + message);
    }

    public int getLogCount() {
        return logCount;
    }
}

public class Exercise1_SingletonPattern {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Singleton Logger Menu ---");
            System.out.println("1. Get Logger instance and log a message");
            System.out.println("2. Verify two references point to the same instance");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = Integer.parseInt(scanner.nextLine().trim());

            switch (choice) {
                case 1:
                    System.out.print("Enter a message to log: ");
                    String message = scanner.nextLine();
                    Logger.getInstance().log(message);
                    break;

                case 2:
                    // Calling getInstance() twice from completely separate
                    // call sites should still return the exact same object
                    Logger first = Logger.getInstance();
                    Logger second = Logger.getInstance();
                    System.out.println("first == second? " + (first == second));
                    System.out.println("Both references report logCount = " +
                            first.getLogCount() + " and " + second.getLogCount());
                    break;

                case 3:
                    System.out.println("Exiting. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option, try again.");
            }
        }
    }
}

/*
 * Analysis:
 * - Only one "Logger instance created" message ever prints, no matter how
 *   many times getInstance() is called - proof that the constructor only
 *   ever runs once.
 * - first == second evaluates to true, confirming both variables reference
 *   the exact same object in memory, not two separate Logger objects.
 * - logCount keeps incrementing across calls from "different" references,
 *   which is only possible because they're really the same underlying object
 *   holding shared state.
 * - Why this matters for logging specifically: if multiple Logger instances
 *   existed, each might buffer or write logs independently, leading to
 *   inconsistent ordering, duplicated setup (e.g., opening the same log file
 *   twice), or scattered state. A single shared instance keeps all logging
 *   centralized and consistent across the whole application.
 */
