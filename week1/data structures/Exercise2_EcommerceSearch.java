import java.util.Arrays;
import java.util.Comparator;

/*
 * Exercise 2: E-commerce Platform Search Function
 *
 * Big O notation tells us how an algorithm's running time grows as input size
 * grows, without depending on the specific machine running it. It lets us
 * compare two approaches (like linear vs binary search) purely on how they
 * scale, instead of just timing them once and getting a result tied to that
 * one test run.
 *
 * For search operations:
 * - Best case: the target is found almost immediately (e.g., first element
 *   in linear search, or the middle element in binary search).
 * - Average case: the target is somewhere in the middle of the typical search path.
 * - Worst case: the target is missing entirely, or sits at the very end of
 *   the structure being searched.
 */

class SearchableProduct {
    int productId;
    String productName;
    String category;

    SearchableProduct(int productId, String productName, String category) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
    }

    @Override
    public String toString() {
        return productName + " (id=" + productId + ", category=" + category + ")";
    }
}

public class Exercise2_EcommerceSearch {

    // Linear search - checks every element one by one until a match is found
    // Works on unsorted data, but doesn't take advantage of any ordering
    public static SearchableProduct linearSearch(SearchableProduct[] products, String name) {
        for (SearchableProduct p : products) {
            if (p.productName.equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }

    // Binary search - repeatedly halves the search space
    // Requires the array to already be sorted by productName
    public static SearchableProduct binarySearch(SearchableProduct[] sortedProducts, String name) {
        int low = 0, high = sortedProducts.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int comparison = sortedProducts[mid].productName.compareToIgnoreCase(name);

            if (comparison == 0) {
                return sortedProducts[mid];
            } else if (comparison < 0) {
                low = mid + 1;   // target is alphabetically after mid, search right half
            } else {
                high = mid - 1;  // target is alphabetically before mid, search left half
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SearchableProduct[] products = {
            new SearchableProduct(1, "Bluetooth Speaker", "Electronics"),
            new SearchableProduct(2, "Yoga Mat", "Fitness"),
            new SearchableProduct(3, "Air Fryer", "Kitchen"),
            new SearchableProduct(4, "Office Chair", "Furniture"),
            new SearchableProduct(5, "Desk Lamp", "Furniture")
        };

        java.util.Scanner scanner = new java.util.Scanner(System.in);

        while (true) {
            System.out.println("\n--- Product Catalog ---");
            for (SearchableProduct p : products) {
                System.out.println("  " + p);
            }

            System.out.println("\n--- Search Menu ---");
            System.out.println("1. Linear Search by Name");
            System.out.println("2. Binary Search by Name");
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

            System.out.print("Enter product name to search: ");
            String query = scanner.nextLine().trim();

            if (choice == 1) {
                SearchableProduct found = linearSearch(products, query);
                System.out.println(found == null ? "Not found." : "Linear search result: " + found);
            } else {
                SearchableProduct[] sortedByName = products.clone();
                Arrays.sort(sortedByName, Comparator.comparing(p -> p.productName.toLowerCase()));
                SearchableProduct found = binarySearch(sortedByName, query);
                System.out.println(found == null ? "Not found." : "Binary search result: " + found);
            }
        }
    }
}

/*
 * Analysis:
 * - Linear search: O(n) in the worst and average case, since it may have to
 *   check every product before finding (or ruling out) a match.
 * - Binary search: O(log n), since each comparison eliminates half the
 *   remaining candidates. Much faster on large catalogs.
 * - Binary search needs the data pre-sorted (sorting itself costs O(n log n)
 *   if not already sorted), so it's most beneficial when the dataset is
 *   searched many times after being sorted once, like a product catalog
 *   that's rebuilt nightly but searched thousands of times during the day.
 * - For a live e-commerce platform with frequent searches and infrequent
 *   catalog changes, binary search (or better, a proper search index) is the
 *   more suitable choice.
 */
