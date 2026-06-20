/*
 * Exercise 3: Sorting Customer Orders
 *
 * Sorting algorithms, briefly:
 * - Bubble Sort: repeatedly compares adjacent elements and swaps them if out
 *   of order, "bubbling" the largest values to the end. Simple but slow.
 * - Insertion Sort: builds the sorted array one element at a time, inserting
 *   each new element into its correct position among the already-sorted ones.
 * - Quick Sort: picks a pivot, partitions the array so smaller elements go
 *   left and larger ones go right, then recursively sorts both halves.
 * - Merge Sort: splits the array in half repeatedly until single elements
 *   remain, then merges them back together in sorted order.
 */

class CustomerOrder {
    int orderId;
    String customerName;
    double totalPrice;

    CustomerOrder(int orderId, String customerName, double totalPrice) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "CustomerOrder#" + orderId + " [" + customerName + ", Rs." + totalPrice + "]";
    }
}

public class Exercise3_SortingOrders {

    // Bubble Sort - swaps adjacent out-of-order elements across repeated passes
    // Stops early if a full pass makes no swaps (array is already sorted)
    public static void bubbleSort(CustomerOrder[] orders) {
        int n = orders.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (orders[j].totalPrice > orders[j + 1].totalPrice) {
                    CustomerOrder temp = orders[j];
                    orders[j] = orders[j + 1];
                    orders[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) break; // already sorted, no need to keep looping
        }
    }

    // Quick Sort - partitions around a pivot, recursively sorts each side
    public static void quickSort(CustomerOrder[] orders, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(orders, low, high);
            quickSort(orders, low, pivotIndex - 1);
            quickSort(orders, pivotIndex + 1, high);
        }
    }

    private static int partition(CustomerOrder[] orders, int low, int high) {
        double pivotPrice = orders[high].totalPrice; // last element as pivot
        int i = low - 1; // boundary for elements smaller than pivot

        for (int j = low; j < high; j++) {
            if (orders[j].totalPrice < pivotPrice) {
                i++;
                CustomerOrder temp = orders[i];
                orders[i] = orders[j];
                orders[j] = temp;
            }
        }
        CustomerOrder temp = orders[i + 1];
        orders[i + 1] = orders[high];
        orders[high] = temp;

        return i + 1; // final position of the pivot
    }

    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        System.out.print("How many orders do you want to enter? ");
        int n = Integer.parseInt(scanner.nextLine().trim());

        CustomerOrder[] orders = new CustomerOrder[n];
        for (int i = 0; i < n; i++) {
            System.out.println("\nOrder " + (i + 1) + ":");
            System.out.print("  Order ID: ");
            int orderId = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("  Customer name: ");
            String customerName = scanner.nextLine().trim();
            System.out.print("  Total price: ");
            double totalPrice = Double.parseDouble(scanner.nextLine().trim());
            orders[i] = new CustomerOrder(orderId, customerName, totalPrice);
        }

        while (true) {
            System.out.println("\n--- Sort Menu ---");
            System.out.println("1. Bubble Sort by Total Price");
            System.out.println("2. Quick Sort by Total Price");
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

            CustomerOrder[] copy = orders.clone();

            if (choice == 1) {
                bubbleSort(copy);
                System.out.println("Bubble sorted by totalPrice:");
            } else {
                quickSort(copy, 0, copy.length - 1);
                System.out.println("Quick sorted by totalPrice:");
            }

            for (CustomerOrder o : copy) System.out.println("  " + o);
        }
    }
}

/*
 * Analysis:
 * - Bubble Sort: O(n^2) time complexity in average and worst case, since it
 *   compares and potentially swaps every pair across repeated passes. The
 *   early-exit optimization helps with already-sorted or nearly-sorted data,
 *   but doesn't change the worst-case behavior.
 * - Quick Sort: O(n log n) on average, since each partition step roughly
 *   halves the problem size. Worst case is O(n^2) (e.g., consistently picking
 *   the smallest or largest element as pivot), but this is rare with random
 *   or well-distributed data and can be mitigated with better pivot selection
 *   (like picking a random or median element).
 * - Quick Sort is generally preferred for larger order volumes because its
 *   average-case performance scales far better than Bubble Sort's. Bubble
 *   Sort only makes sense for very small datasets or teaching purposes.
 */
