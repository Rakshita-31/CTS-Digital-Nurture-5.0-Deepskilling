package com.library;

import com.library.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Scanner;

/**
 * Entry point for the Library Management application.
 *
 * Covers:
 *  - Exercise 1: Loads applicationContext.xml and verifies the Spring
 *    container starts up correctly with BookService and BookRepository beans.
 *  - Exercise 2: Demonstrates that BookService's BookRepository dependency
 *    was injected by the container (setter injection), not created manually.
 */
public class LibraryManagementApplication {

    public static void main(String[] args) {
        System.out.println("Loading Spring application context...");
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        System.out.println("Spring context loaded successfully!\n");

        BookService bookService = (BookService) context.getBean("bookService");

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    bookService.displayAllBooks();
                    break;
                case "2":
                    System.out.print("Enter title of the book to add: ");
                    String addTitle = scanner.nextLine().trim();
                    bookService.addBook(addTitle);
                    break;
                case "3":
                    System.out.print("Enter title of the book to remove: ");
                    String removeTitle = scanner.nextLine().trim();
                    bookService.removeBook(removeTitle);
                    break;
                case "4":
                    System.out.println("Exiting application. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
            System.out.println();
        }

        scanner.close();
        ((ClassPathXmlApplicationContext) context).close();
    }

    private static void printMenu() {
        System.out.println("===== Library Management Menu =====");
        System.out.println("1. Display all books");
        System.out.println("2. Add a book");
        System.out.println("3. Remove a book");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }
}
