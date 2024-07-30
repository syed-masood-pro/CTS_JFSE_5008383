import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SortingCustomer{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Order> orderList = new ArrayList<>();
        boolean running = true;

        while (running) {
            System.out.println("\nOrder Management System");
            System.out.println("1. Add Order\n2. Sort Orders (Bubble Sort)\n3. Sort Orders (Quick Sort)\n4. Exit\nEnter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Order ID: ");
                    int orderId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Customer Name: ");
                    String customerName = scanner.nextLine();
                    System.out.print("Enter Total Price: ");
                    double totalPrice = scanner.nextDouble();
                    orderList.add(new Order(orderId, customerName, totalPrice));
                    break;

                case 2:
                    Order[] bubbleSortedOrders = orderList.toArray(new Order[0]);
                    Sort.bubbleSort(bubbleSortedOrders);
                    System.out.println("Orders sorted using Bubble Sort:");
                    Sort.printOrders(bubbleSortedOrders);
                    break;

                case 3:
                    Order[] quickSortedOrders = orderList.toArray(new Order[0]);
                    Sort.quickSort(quickSortedOrders, 0, quickSortedOrders.length - 1);
                    System.out.println("Orders sorted using Quick Sort:");
                    Sort.printOrders(quickSortedOrders);
                    break;

                case 4:
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Enter a valid choice");
            }
        }
    }
    
    static class Order {
        private int orderId;
        private String custName;
        private double totalPrice;

        public Order(int orderId, String custName, double totalPrice) {
            this.orderId = orderId;
            this.custName = custName;
            this.totalPrice = totalPrice;
        }

        public int getOrderId() {
            return orderId;
        }

        public String getCustomerName() {
            return custName;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        @Override
        public String toString() {
            return "Order ID: " + orderId + ", Customer Name: " + custName + ", Total Price: " + totalPrice;
        }
    }
    
    static class Sort {
        public static void bubbleSort(Order[] orders) {
            int n = orders.length;
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - 1 - i; j++) {
                    if (orders[j].getTotalPrice() > orders[j + 1].getTotalPrice()) {
                        Order temp = orders[j];
                        orders[j] = orders[j + 1];
                        orders[j + 1] = temp;
                    }
                }
            }
        }

        public static void quickSort(Order[] orders, int low, int high) {
            if (low < high) {
                int pi = partition(orders, low, high);
                quickSort(orders, low, pi - 1);
                quickSort(orders, pi + 1, high);
            }
        }

        public static int partition(Order[] orders, int low, int high) {
            Order pivot = orders[high];
            int i = (low - 1);
            for (int j = low; j < high; j++) {
                if (orders[j].getTotalPrice() <= pivot.getTotalPrice()) {
                    i++;
                    Order temp = orders[i];
                    orders[i] = orders[j];
                    orders[j] = temp;
                }
            }
            Order temp = orders[i + 1];
            orders[i + 1] = orders[high];
            orders[high] = temp;
            return i + 1;
        }

        public static void printOrders(Order[] orders) {
            for (Order order : orders) {
                System.out.println(order);
            }
        }
    }
}
