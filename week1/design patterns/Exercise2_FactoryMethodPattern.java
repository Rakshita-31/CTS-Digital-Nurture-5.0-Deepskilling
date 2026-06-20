import java.util.Scanner;

/*
 * Exercise 2: Implementing the Factory Method Pattern
 *
 * Scenario: a document management system needs to create different document
 * types (Word, PDF, Excel) without the calling code having to know exactly
 * which concrete class to instantiate.
 *
 * How the Factory Method pattern helps here:
 * - Each document type implements a common Document interface, so calling
 *   code can work with any of them through the same set of methods.
 * - An abstract DocumentFactory declares createDocument() but doesn't decide
 *   which concrete document gets built - that decision is pushed down into
 *   each concrete factory subclass.
 * - To add a new document type later (say, PowerPointDocument), you just add
 *   a new Document implementation and a new factory - no existing code needs
 *   to change. This is the Open/Closed Principle in action.
 */

// Common interface every document type implements
interface Document {
    void open();
}

class WordDocument implements Document {
    @Override
    public void open() {
        System.out.println("Opening a Word document (.docx)...");
    }
}

class PdfDocument implements Document {
    @Override
    public void open() {
        System.out.println("Opening a PDF document (.pdf)...");
    }
}

class ExcelDocument implements Document {
    @Override
    public void open() {
        System.out.println("Opening an Excel document (.xlsx)...");
    }
}

// Abstract factory - declares the factory method but leaves the actual
// document creation to each concrete subclass
abstract class DocumentFactory {
    public abstract Document createDocument();
}

class WordDocumentFactory extends DocumentFactory {
    @Override
    public Document createDocument() {
        return new WordDocument();
    }
}

class PdfDocumentFactory extends DocumentFactory {
    @Override
    public Document createDocument() {
        return new PdfDocument();
    }
}

class ExcelDocumentFactory extends DocumentFactory {
    @Override
    public Document createDocument() {
        return new ExcelDocument();
    }
}

public class Exercise2_FactoryMethodPattern {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Document Factory Menu ---");
            System.out.println("1. Create Word Document");
            System.out.println("2. Create PDF Document");
            System.out.println("3. Create Excel Document");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = Integer.parseInt(scanner.nextLine().trim());

            // The calling code here never directly instantiates WordDocument,
            // PdfDocument, or ExcelDocument - it only ever talks to
            // DocumentFactory and Document, the abstraction layer
            DocumentFactory factory;

            switch (choice) {
                case 1:
                    factory = new WordDocumentFactory();
                    factory.createDocument().open();
                    break;

                case 2:
                    factory = new PdfDocumentFactory();
                    factory.createDocument().open();
                    break;

                case 3:
                    factory = new ExcelDocumentFactory();
                    factory.createDocument().open();
                    break;

                case 4:
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
 * - The main method never writes `new WordDocument()` directly - it only
 *   ever calls factory.createDocument(), and the actual concrete class
 *   returned depends entirely on which factory subclass is used.
 * - This decouples "what document type is needed" from "how that document
 *   type gets constructed." If WordDocument's constructor later needed extra
 *   setup steps, only WordDocumentFactory would need to change - the calling
 *   code in main() wouldn't be touched at all.
 * - Adding a new document type (e.g., PowerPointDocument) means adding one
 *   new class implementing Document and one new factory extending
 *   DocumentFactory - existing classes stay untouched, which is exactly the
 *   Open/Closed Principle: open for extension, closed for modification.
 */
