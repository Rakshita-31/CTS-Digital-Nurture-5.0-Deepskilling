package com.library.repository;

import java.util.ArrayList;
import java.util.List;

/**
 * BookRepository simulates the data access layer for the Library Management
 * application. In a real application this would talk to a database; here it
 * keeps an in-memory list so we can clearly observe Spring wiring it into
 * BookService via Dependency Injection (Exercise 2).
 */
public class BookRepository {

    private final List<String> books = new ArrayList<>();

    public BookRepository() {
        // Seed with a few books so the demo has something to show immediately
        books.add("Effective Java");
        books.add("Clean Code");
        books.add("Spring in Action");
        System.out.println("[BookRepository] Bean created and seeded with initial books.");
    }

    public void addBook(String title) {
        books.add(title);
        System.out.println("[BookRepository] Added book: " + title);
    }

    public boolean removeBook(String title) {
        boolean removed = books.remove(title);
        if (removed) {
            System.out.println("[BookRepository] Removed book: " + title);
        } else {
            System.out.println("[BookRepository] Book not found: " + title);
        }
        return removed;
    }

    public List<String> findAllBooks() {
        return books;
    }
}
