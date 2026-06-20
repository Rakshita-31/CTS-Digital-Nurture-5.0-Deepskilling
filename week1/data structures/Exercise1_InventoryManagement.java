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

        system.addProduct(new InventoryProduct(101, "Wireless Mouse", 50, 499.0));
        system.addProduct(new InventoryProduct(102, "Mechanical Keyboard", 30, 2499.0));

        System.out.println("Before update: " + system.getProduct(101));
        system.updateProduct(101, 45, 459.0);
        System.out.println("After update:  " + system.getProduct(101));

        system.deleteProduct(102);
        System.out.println("After delete, lookup 102: " + system.getProduct(102));
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