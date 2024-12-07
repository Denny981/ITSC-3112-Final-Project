import java.util.HashMap;
import java.util.Scanner;

// Class representing a product
class Product {
    private String productCode; // SKU
    private String brand;
    private String itemType;
    private int quantity;

    public Product(String productCode, String brand, String itemType, int quantity) {
        this.productCode = productCode;
        this.brand = brand;
        this.itemType = itemType;
        this.quantity = quantity;
    }

    public String getProductCode() {
        return productCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void addQuantity(int amount) {
        this.quantity += amount;
    }

    public boolean removeQuantity(int amount) {
        if (amount > quantity) {
            return false; // Not enough stock
        }
        this.quantity -= amount;
        return true;
    }

    @Override
    public String toString() {
        return String.format("SKU: %s, Brand: %s, Type: %s, Quantity: %d", 
                              productCode, brand, itemType, quantity);
    }
}

// Class representing the warehouse system
class Warehouse {
    private HashMap<String, Product> inventory = new HashMap<>();

    // Method to input stock into the system
    public void inputStock(String productCode, String brand, String itemType, int quantity) {
        if (inventory.containsKey(productCode)) {
            inventory.get(productCode).addQuantity(quantity);
        } else {
            inventory.put(productCode, new Product(productCode, brand, itemType, quantity));
        }
        System.out.println("Stock added successfully.");
    }

    // Method to remove stock from the system
    public void removeStock(String productCode, int quantity) {
        if (inventory.containsKey(productCode)) {
            Product product = inventory.get(productCode);
            if (product.removeQuantity(quantity)) {
                System.out.println("Stock removed successfully.");
                if (product.getQuantity() == 0) {
                    inventory.remove(productCode); // Remove product if quantity is zero
                }
            } else {
                System.out.println("Error: Not enough stock to remove.");
            }
        } else {
            System.out.println("Error: Product not found.");
        }
    }

    // Method to display all stock
    public void displayInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            System.out.println("Current Inventory:");
            for (Product product : inventory.values()) {
                System.out.println(product);
            }
        }
    }
}

// Main class
public class WarehouseStockSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Warehouse warehouse = new Warehouse();

        while (true) {
            System.out.println("\nWarehouse Stock System");
            System.out.println("1. Input Stock");
            System.out.println("2. Remove Stock");
            System.out.println("3. View Inventory");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1: // Input stock
                    System.out.print("Enter SKU (Product Code): ");
                    String productCode = scanner.nextLine();
                    System.out.print("Enter Brand: ");
                    String brand = scanner.nextLine();
                    System.out.print("Enter Item Type: ");
                    String itemType = scanner.nextLine();
                    System.out.print("Enter Quantity: ");
                    int quantity = scanner.nextInt();
                    warehouse.inputStock(productCode, brand, itemType, quantity);
                    break;

                case 2: // Remove stock
                    System.out.print("Enter SKU (Product Code): ");
                    productCode = scanner.nextLine();
                    System.out.print("Enter Quantity to Remove: ");
                    quantity = scanner.nextInt();
                    warehouse.removeStock(productCode, quantity);
                    break;

                case 3: // View inventory
                    warehouse.displayInventory();
                    break;

                case 4: // Exit
                    System.out.println("Exiting system. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
