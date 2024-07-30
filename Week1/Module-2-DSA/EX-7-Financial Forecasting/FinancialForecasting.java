import java.util.HashMap;
import java.util.Map;

public class FinancialForecasting {

    private static Map<Integer, Double> memo = new HashMap<>();

    // Optimized recursive method with memoization
    public static double calculateFutureValue(double presentValue, double growthRate, int periods) {
        // Base case: if no more periods, return the present value
        if (periods == 0) {
            return presentValue;
        }
        // Check if the value is already computed
        if (memo.containsKey(periods)) {
            return memo.get(periods);
        }
        // Recursive case: calculate the value for the next period
        double futureValue = calculateFutureValue(presentValue * (1 + growthRate), growthRate, periods - 1);
        memo.put(periods, futureValue);
        return futureValue;
    }

    public static void main(String[] args) {
        double presentValue = 1000.0; // Initial value
        double growthRate = 0.05; // 5% growth rate
        int periods = 10; // Number of periods

        double futureValue = calculateFutureValue(presentValue, growthRate, periods);
        System.out.println("Future Value: " + futureValue);
    }
}
