import java.util.*;

class Product {
    int id;
    String name;
    double price;
    int quantity;

    Product(int id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}

public class Main {

    static Scanner sc = new Scanner(System.in);
    static Map<Integer, Product> inventory = new LinkedHashMap<>();

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n===== RETAIL SHOP MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Product");
            System.out.println("2. View Inventory");
            System.out.println("3. Search Product");
            System.out.println("4. Generate Bill");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            choice = readInt();

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    viewInventory();
                    break;
                case 3:
                    searchProduct();
                    break;
                case 4:
                    generateBill();
                    break;
                case 5:
                    System.out.println("Thank you for using the system!");
                    break;
                default:
                    System.out.println("Invalid choice. Enter 1-5.");
            }
        } while (choice != 5);
    }

    static int readInt() {
        while (true) {
            if (sc.hasNextInt()) {
                int value = sc.nextInt();
                sc.nextLine();
                return value;
            }

            System.out.print("Invalid input. Enter an integer: ");
            sc.nextLine();
        }
    }

    static double readDouble() {
        while (true) {
            if (sc.hasNextDouble()) {
                double value = sc.nextDouble();
                sc.nextLine();

                if (Double.isFinite(value)) {
                    return value;
                }
            } else {
                sc.nextLine();
            }

            System.out.print("Invalid input. Enter a valid number: ");
        }
    }

    static void addProduct() {
        System.out.print("Enter Product ID: ");
        int id = readInt();

        if (id <= 0) {
            System.out.println("Product ID must be positive.");
            return;
        }

        if (inventory.containsKey(id)) {
            System.out.println("Product ID already exists.");
            return;
        }

        System.out.print("Enter Product Name: ");
        String name = sc.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Product name cannot be empty.");
            return;
        }

        System.out.print("Enter Product Price: ");
        double price = readDouble();

        if (price < 0) {
            System.out.println("Price cannot be negative.");
            return;
        }

        System.out.print("Enter Stock Quantity: ");
        int quantity = readInt();

        if (quantity < 0) {
            System.out.println("Quantity cannot be negative.");
            return;
        }

        inventory.put(id, new Product(id, name, price, quantity));
        System.out.println("Product added successfully!");
    }

    static void viewInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }

        System.out.println("\n---------------- INVENTORY ----------------");
        System.out.printf("%-10s %-20s %-12s %-10s%n",
                "ID", "Product Name", "Price", "Quantity");

        for (Product p : inventory.values()) {
            System.out.printf("%-10d %-20s Rs.%-9.2f %-10d%n",
                    p.id, p.name, p.price, p.quantity);
        }
    }

    static void searchProduct() {
        System.out.print("Enter Product ID to search: ");
        int id = readInt();

        Product p = inventory.get(id);

        if (p != null) {
            System.out.println("\nProduct ID : " + p.id);
            System.out.println("Name       : " + p.name);
            System.out.printf("Price      : Rs. %.2f%n", p.price);
            System.out.println("Quantity   : " + p.quantity);
        } else {
            System.out.println("Product not found.");
        }
    }

    static void generateBill() {
        if (inventory.isEmpty()) {
            System.out.println("No products available for billing.");
            return;
        }

        System.out.print("Enter Customer Name: ");
        String customerName = sc.nextLine().trim();

        if (customerName.isEmpty()) {
            System.out.println("Customer name cannot be empty.");
            return;
        }

        double total = 0;
        boolean hasItems = false;

        System.out.println("\n========== CUSTOMER BILL ==========");
        System.out.printf("%-20s %-8s %-12s %-12s%n",
                "Product", "Qty", "Price", "Amount");

        while (true) {
            System.out.print("\nEnter Product ID (0 to finish): ");
            int id = readInt();

            if (id == 0) {
                break;
            }

            if (id < 0) {
                System.out.println("Product ID must be positive.");
                continue;
            }

            Product p = inventory.get(id);

            if (p == null) {
                System.out.println("Product not found.");
                continue;
            }

            System.out.println("Product: " + p.name);
            System.out.println("Available stock: " + p.quantity);

            System.out.print("Enter quantity: ");
            int qty = readInt();

            if (qty <= 0) {
                System.out.println("Quantity must be greater than zero.");
                continue;
            }

            if (qty > p.quantity) {
                System.out.println("Insufficient stock!");
                continue;
            }

            double amount = p.price * qty;

            if (!Double.isFinite(amount)
                    || !Double.isFinite(total + amount)) {
                System.out.println("Amount is too large to process.");
                continue;
            }

            total += amount;
            p.quantity -= qty;
            hasItems = true;

            System.out.printf("%-20s %-8d Rs.%-9.2f Rs.%-9.2f%n",
                    p.name, qty, p.price, amount);
        }

        if (!hasItems) {
            System.out.println("No products billed.");
            return;
        }

        System.out.println("--------------------------------------------");
        System.out.println("Customer Name: " + customerName);
        System.out.printf("Total Amount: Rs. %.2f%n", total);
        System.out.println("============================================");
        System.out.println("Bill generated successfully!");
    }
}
