import java.util.Arrays;
import java.util.Comparator;

/*
 * Exercise 6: Library Management System
 *
 * Search algorithms recap:
 * - Linear search checks each book one at a time until a title match is found
 *   (or the list runs out). No ordering assumptions needed.
 * - Binary search repeatedly splits a sorted list in half, discarding the half
 *   that can't contain the target - but it only works correctly if the list
 *   is already sorted by the field being searched (title, in this case).
 */

class LibraryBook {
    int bookId;
    String title;
    String author;

    LibraryBook(int bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
    }

    @Override
    public String toString() {
        return "\"" + title + "\" by " + author + " (id=" + bookId + ")";
    }
}

public class Exercise6_LibraryManagement {

    // Linear search by title - works regardless of order
    public static LibraryBook linearSearchByTitle(LibraryBook[] books, String title) {
        for (LibraryBook b : books) {
            if (b.title.equalsIgnoreCase(title)) {
                return b;
            }
        }
        return null;
    }

    // Binary search by title - assumes books[] is already sorted by title
    public static LibraryBook binarySearchByTitle(LibraryBook[] sortedBooks, String title) {
        int low = 0, high = sortedBooks.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int comparison = sortedBooks[mid].title.compareToIgnoreCase(title);

            if (comparison == 0) {
                return sortedBooks[mid];
            } else if (comparison < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        LibraryBook[] books = {
            new LibraryBook(1, "Clean Code", "Robert Martin"),
            new LibraryBook(2, "The Pragmatic Programmer", "Andrew Hunt"),
            new LibraryBook(3, "Introduction to Algorithms", "Thomas Cormen"),
            new LibraryBook(4, "Design Patterns", "Erich Gamma"),
            new LibraryBook(5, "Effective Java", "Joshua Bloch")
        };

        java.util.Scanner scanner = new java.util.Scanner(System.in);

        while (true) {
            System.out.println("\n--- Library Catalog ---");
            for (LibraryBook b : books) {
                System.out.println("  " + b);
            }

            System.out.println("\n--- Search Menu ---");
            System.out.println("1. Linear Search by Title");
            System.out.println("2. Binary Search by Title");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = Integer.parseInt(scanner.nextLine().trim());

            if (choice == 3) {
                System.out.println("Exiting. Goodbye!");
                scanner.close();
                return;
            }

            if (choice != 1 && choice != 2) {
                System.out.println("Invalid option, try again.");
                continue;
            }

            System.out.print("Enter book title to search: ");
            String query = scanner.nextLine().trim();

            if (choice == 1) {
                LibraryBook found = linearSearchByTitle(books, query);
                System.out.println(found == null ? "Not found." : "Linear search found: " + found);
            } else {
                LibraryBook[] sortedByTitle = books.clone();
                Arrays.sort(sortedByTitle, Comparator.comparing(b -> b.title.toLowerCase()));
                LibraryBook found = binarySearchByTitle(sortedByTitle, query);
                System.out.println(found == null ? "Not found." : "Binary search found: " + found);
            }
        }
    }
}

/*
 * Analysis:
 * - Linear search: O(n) time complexity - in the worst case (book not present,
 *   or it's the last one checked), every entry gets examined.
 * - Binary search: O(log n) - each step eliminates half of the remaining
 *   candidates, making it dramatically faster for large catalogs.
 * - When to use which: for a small library (a few dozen books) or a
 *   frequently-changing, unsorted collection, linear search is simpler and
 *   the performance difference is negligible. For a large library catalog
 *   that's searched often and doesn't change every minute, sorting once and
 *   using binary search pays off significantly - especially as the
 *   collection grows into the thousands.
 */
