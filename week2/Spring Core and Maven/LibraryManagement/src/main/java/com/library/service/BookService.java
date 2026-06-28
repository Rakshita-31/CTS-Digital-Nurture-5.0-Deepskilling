package com.library.service;

import com.library.repository.BookRepository;
import java.util.List;

/**
 * BookService is the service layer for the Library Management application.
 * Its dependency on BookRepository is supplied by Spring's IoC container via
 * setter injection (Exercise 2), instead of the class creating its own
 * BookRepository with `new`.
 */
public class BookService {

    private BookRepository bookRepository;

    // Setter used by Spring for Dependency Injection (wired in applicationContext.xml)
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("[BookService] BookRepository injected via setter.");
    }

    public void addBook(String title) {
        bookRepository.addBook(title);
    }

    public boolean removeBook(String title) {
        return bookRepository.removeBook(title);
    }

    public List<String> getAllBooks() {
        return bookRepository.findAllBooks();
    }

    public void displayAllBooks() {
        List<String> books = bookRepository.findAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books available in the library.");
            return;
        }
        System.out.println("---- Library Catalogue ----");
        for (int i = 0; i < books.size(); i++) {
            System.out.println((i + 1) + ". " + books.get(i));
        }
        System.out.println("---------------------------");
    }
}
