public class AdapterPattern {
    public static void main(String[] args) {
        PaymentProcess gpayProcess = new GpayAdapter(new Gpay());
        gpayProcess.processPayment(1000.0);

        PaymentProcess ppProcess = new PhonepayAdapter(new Phonepay());
        ppProcess.processPayment(5000.0);
    }

    // PaymentProcess Interface
    public interface PaymentProcess {
        void processPayment(double amount);
    }

    // Gpay Class
    public static class Gpay {
        public void makePayment(double amount) {
            System.out.println("Processing payment of " + amount + " rupees through gpay");
        }
    }

    // Phonepay Class
    public static class Phonepay {
        public void makePayment(double amount) {
            System.out.println("Processing payment of " + amount + " rupees through phonepay");
        }
    }

    // GpayAdapter Class
    public static class GpayAdapter implements PaymentProcess {
        private Gpay gpay;

        public GpayAdapter(Gpay gpay) {
            this.gpay = gpay;
        }

        @Override
        public void processPayment(double amount) {
            gpay.makePayment(amount);
        }
    }

    // PhonepayAdapter Class
    public static class PhonepayAdapter implements PaymentProcess {
        private Phonepay phonepay;

        public PhonepayAdapter(Phonepay phonepay) {
            this.phonepay = phonepay;
        }

        @Override
        public void processPayment(double amount) {
            phonepay.makePayment(amount);
        }
    }
}
