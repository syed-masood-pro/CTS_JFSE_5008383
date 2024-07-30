import java.util.Scanner;

// PaymentStrategy Interface
interface PaymentStrategy {
    void pay(double amount);
}

// PaymentContext Class
class PaymentContext {
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void executePayment(double amount) {
        paymentStrategy.pay(amount);
    }
}

// PayPalPayment Class
class PayPalPayment implements PaymentStrategy {
    private String email;

    public PayPalPayment(String email) {
        this.email = email;
    }

    @Override
    public void pay(double amount) {
        System.out.printf("Processing PayPal payment of %.2f with email %s%n", amount, email);
    }
}

// CreditCardPayment Class
class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    private String cardHolderName;

    public CreditCardPayment(String cardNumber, String cardHolderName) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
    }

    @Override
    public void pay(double amount) {
        System.out.printf("Processing credit card payment of %.2f with card number %s for %s%n",
                amount, cardNumber, cardHolderName);
    }
}

// Main Class
public class StrategyPattern {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PaymentContext context = new PaymentContext();

        while (true) {
            System.out.println("Choose Payment Method:");
            System.out.println("1. Credit Card\n2. PayPal\n3. Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1: {
                    System.out.println("Enter Credit Card Number:");
                    String cardNumber = scanner.next();
                    System.out.println("Enter Card Holder Name:");
                    String cardHolderName = scanner.next();
                    System.out.println("Enter Amount:");
                    double amount = scanner.nextDouble();

                    PaymentStrategy creditCardPayment = new CreditCardPayment(cardNumber, cardHolderName);
                    context.setPaymentStrategy(creditCardPayment);
                    context.executePayment(amount);
                    break;
                }
                case 2: {
                    System.out.println("Enter PayPal Email:");
                    String email = scanner.next();
                    System.out.println("Enter Amount:");
                    double amount = scanner.nextDouble();

                    PaymentStrategy paypalPayment = new PayPalPayment(email);
                    context.setPaymentStrategy(paypalPayment);
                    context.executePayment(amount);
                    break;
                }
                case 3: {
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                }
                default: {
                    System.out.println("Invalid option. Please try again.");
                }
            }
        }
    }
}
