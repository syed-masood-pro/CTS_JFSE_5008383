import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;


public class Ecommerce {

    // Product class
    static class Product {
        private int productId;
        private String productName;
        private String category;

        public Product(int productId, String productName, String category) {
            this.productId = productId;
            this.productName = productName;
            this.category = category;
        }

        public int getProductId() {
            return productId;
        }

        public String getProductName() {
            return productName;
        }

        public String getCategory() {
            return category;
        }

        @Override
        public String toString() {
            return "Product ID: " + productId + ", Name: " + productName + ", Category: " + category;
        }
    }

    // LinearSearch class
    static class LinearSearch {
        public static Product linearsearch(Product[] products, String productName) {
            for (Product p : products) {
                if (p.getProductName().equalsIgnoreCase(productName)) {
                    return p;
                }
            }
            return null;
        }
    }

    // BinarySearch class
    static class BinarySearch {
        public static Product binarysearch(Product[] products, String productName) {
            Arrays.sort(products, Comparator.comparing(Product::getProductName));
            int left = 0;
            int right = products.length - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                int res = products[mid].getProductName().compareToIgnoreCase(productName);
                if (res == 0) {
                    return products[mid];
                }
                if (res < 0) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            return null;
        }
    }

    // Main method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Product[] products = {
                new Product(1, "Laptop", "Electronics"),
                new Product(2, "Phone", "Electronics"),
                new Product(3, "Book", "Stationery"),
                new Product(4, "Shoes", "Footwear"),
                new Product(5, "Shirt", "Clothing")
        };

        // Linear search
        System.out.println("Linear Search:");
        Product foundProduct = LinearSearch.linearsearch(products, "phone");
        System.out.println(foundProduct != null ? foundProduct : "Product Not Found");

        // Binary search
        System.out.println("Binary Search:");
        foundProduct = BinarySearch.binarysearch(products, "shirt");
        System.out.println(foundProduct != null ? foundProduct : "Product Not Found");

    }
}
