import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class InventoryManagementSystem {

    // Product Class
    static class Product {
        int productId;
        String productName;
        int quantity;
        double price;

        public Product(int productId, String productName, int quantity, double price) {
            this.productId = productId;
            this.productName = productName;
            this.quantity = quantity;
            this.price = price;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }

    // InventoryManage Class
    static class InventoryManage {
        Map<Integer, Product> inventory = new HashMap<>();

        public void addProduct(Product product) {
            inventory.put(product.getProductId(), product);
        }

        public void updateProduct(int productId, Product newProduct) {
            if (inventory.containsKey(productId)) {
                inventory.put(productId, newProduct);
            } else {
                System.out.println("Product Not Found");
            }
        }

        public void deleteProduct(int productId) {
            if (inventory.containsKey(productId)) {
                inventory.remove(productId);
            } else {
                System.out.println("Product Not Found");
            }
        }

        public Product getProduct(int productId) {
            return inventory.get(productId);
        }

        public void printInventory() {
            for (Product p : inventory.values()) {
                System.out.println("ID: " + p.getProductId() + ", Name: " + p.getProductName() +
                        ", Quantity: " + p.getQuantity() + ", Price: $" + p.getPrice());
            }
        }
    }

    // Main Method
    public static void main(String[] args) {
        InventoryManage m = new InventoryManage();
        Scanner sc = new Scanner(System.in);
        boolean op = true;
        while (op) {
            System.out.println("\tInventory Management");
            System.out.println("\t\tEnter Options");
            System.out.println("1. Add Product\n2. Update Product\n3. Delete Product\n4. Get Product\n5. Display Products\n6. Exit");
            int x = sc.nextInt();
            switch (x) {
                case 1: {
                    System.out.println("Enter ProductId: ");
                    int pid = sc.nextInt();
                    System.out.println("Enter Product Name: ");
                    String pname = sc.next();
                    System.out.println("Enter Product Quantity: ");
                    int pquantity = sc.nextInt();
                    System.out.println("Enter Product Price: ");
                    double pprice = sc.nextDouble();
                    Product p = new Product(pid, pname, pquantity, pprice);
                    m.addProduct(p);
                    break;
                }
                case 2: {
                    System.out.println("Enter ProductId to Update: ");
                    int pid = sc.nextInt();
                    System.out.println("Enter Product Name to Update: ");
                    String pname = sc.next();
                    System.out.println("Enter Product Quantity to Update: ");
                    int pquantity = sc.nextInt();
                    System.out.println("Enter Product Price to Update: ");
                    double pprice = sc.nextDouble();
                    Product p = new Product(pid, pname, pquantity, pprice);
                    m.updateProduct(pid, p);
                    break;
                }
                case 3: {
                    System.out.println("Enter ProductId to delete: ");
                    int pid = sc.nextInt();
                    m.deleteProduct(pid);
                    break;
                }
                case 4: {
                    System.out.println("Enter ProductId:");
                    int pid = sc.nextInt();
                    Product p = m.getProduct(pid);
                    if (p != null) {
                        System.out.println("Id: " + p.getProductId() + "\tName: " + p.getProductName() +
                                "\tQuantity: " + p.getQuantity() + "\tPrice: $" + p.getPrice());
                    } else {
                        System.out.println("Product Not Found");
                    }
                    break;
                }
                case 5: {
                    m.printInventory();
                    break;
                }
                case 6: {
                    op = false;
                    break;
                }
                default: {
                    System.out.println("Enter valid Option");
                    break;
                }
            }
        }
    }
}
