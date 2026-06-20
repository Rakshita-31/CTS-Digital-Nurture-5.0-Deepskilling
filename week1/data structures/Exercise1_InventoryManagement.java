import java.util.HashMap;

/*
 * Exercise 1: Inventory Management System
 *
 * Why DSA matters here:
 * A warehouse can have thousands of SKUs, and operations like "find product X"
 * or "update stock count" happen constantly. If we store products in something
 * like an ArrayList and search linearly every time, lookups become O(n) -
 * painfully slow once inventory scales to 10,000+ items.
 *
 * Data structure choice:
 * A HashMap<Integer, InventoryProduct> keyed by productId gives O(1) average-case
 * add/update/delete/search, since the key hashes directly to a bucket instead
 * of scanning the whole collection. An ArrayList would have been simpler to
 * set up, but it loses that speed advantage the moment the inventory grows.
 */

class InventoryProduct {
    int productId;
    String productName;
    int quantity;
    double price;

    InventoryProduct(int productId, String productName, int quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public String toString() {
        return "InventoryProduct[id=" + productId + ", name=" + productName +
               ", qty=" + quantity + ", price=" + price + "]";
    }
}

public class Exercise1_InventoryManagement {

    // HashMap chosen so productId lookups don't require scanning every entry
    private final HashMap<Integer, InventoryProduct> inventory = new HashMap<>();

    // O(1) average case - hashing the key places it directly in the right bucket
    public void addProduct(InventoryProduct p) {
        if (inventory.containsKey(p.productId)) {
            System.out.println("InventoryProduct ID already exists. Use updateProduct instead.");
            return;
        }
        inventory.put(p.productId, p);
    }

    // O(1) average case - direct key lookup, no traversal needed
    public void updateProduct(int productId, int newQuantity, double newPrice) {
        InventoryProduct p = inventory.get(productId);
        if (p == null) {
            System.out.println("InventoryProduct not found: " + productId);
            return;
        }
        p.quantity = newQuantity;
        p.price = newPrice;
    }

    // O(1) average case
    public void deleteProduct(int productId) {
        if (inventory.remove(productId) == null) {
            System.out.println("InventoryProduct not found: " + productId);
        }
    }

    // O(1) average case - same hashing benefit applies to plain lookups
    public InventoryProduct getProduct(int productId) {
        return inventory.get(productId);
    }

    public static void main(String[] args) {
        Exercise1_InventoryManagement system = new Exercise1_InventoryManagement();
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        while (true) {
            System.out.println("\n--- Inventory Menu ---");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            System.out.println("4. View Product");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = Integer.parseInt(scanner.nextLine().trim());

            switch (choice) {
                case 1:
                    System.out.print("Enter product ID: ");
                    int addId = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Enter product name: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Enter quantity: ");
                    int qty = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Enter price: ");
                    double price = Double.parseDouble(scanner.nextLine().trim());
                    system.addProduct(new InventoryProduct(addId, name, qty, price));
                    System.out.println("Added: " + system.getProduct(addId));
                    break;

                case 2:
                    System.out.print("Enter product ID to update: ");
                    int updateId = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Enter new quantity: ");
                    int newQty = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Enter new price: ");
                    double newPrice = Double.parseDouble(scanner.nextLine().trim());
                    system.updateProduct(updateId, newQty, newPrice);
                    System.out.println("Updated: " + system.getProduct(updateId));
                    break;

                case 3:
                    System.out.print("Enter product ID to delete: ");
                    int deleteId = Integer.parseInt(scanner.nextLine().trim());
                    system.deleteProduct(deleteId);
                    System.out.println("After delete, lookup " + deleteId + ": " + system.getProduct(deleteId));
                    break;

                case 4:
                    System.out.print("Enter product ID to view: ");
                    int viewId = Integer.parseInt(scanner.nextLine().trim());
                    InventoryProduct found = system.getProduct(viewId);
                    System.out.println(found == null ? "Product not found." : found);
                    break;

                case 5:
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
 * - add/update/delete/search are all O(1) on average with HashMap, since hashing
 *   maps the productId straight to its bucket instead of scanning the collection.
 * - Worst case is O(n) if many keys collide into the same bucket, but Java's
 *   HashMap handles this well in practice with a good hash function and resizing.
 * - Optimization: keep the initial capacity reasonably sized (e.g., new HashMap<>(capacity))
 *   if the approximate inventory size is known ahead of time, to avoid repeated resizing
 *   as products are added.
 */
