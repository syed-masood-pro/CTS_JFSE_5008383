public class SingletonPattern {

    public static void main(String[] args) {
        Logger log1 = Logger.getInstance();
        Logger log2 = Logger.getInstance();
        Logger log3 = Logger.getInstance();

        if (log1 == log2) {
            System.out.println("Both logger instances are the same");
        } else {
            System.out.println("Logger instances are different");
        }

        log1.log("This is a log message from log1");
        log2.log("This is a log message from log2");
        log3.log("This is a log message from log3");
    }

    // Nested Logger class
    public static class Logger {
        private static Logger instance;

        private Logger() {
            // Private constructor to prevent instantiation
        }

        public static Logger getInstance() {
            if (instance == null) {
                synchronized (Logger.class) {
                    if (instance == null) {
                        instance = new Logger();
                    }
                }
            }
            return instance;
        }

        public void log(String msg) {
            System.out.println("LOG MESSAGE: " + msg);
        }
    }
}
