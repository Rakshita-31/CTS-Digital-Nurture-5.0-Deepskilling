/*
 * Exercise 7: Financial Forecasting
 *
 * Recursion is when a method calls itself to solve a smaller version of the
 * same problem, until it reaches a "base case" simple enough to answer
 * directly. It's a natural fit here because compound growth is inherently
 * recursive: the value at year n depends on the value at year (n-1), which
 * depends on year (n-2), and so on back to year 0.
 */

public class Exercise7_FinancialForecasting {

    /**
     * Recursively calculates the future value of an investment after a given
     * number of years, applying a fixed annual growth rate.
     *
     * @param presentValue the starting amount
     * @param growthRate   the annual growth rate as a decimal (e.g., 0.08 for 8%)
     * @param years        number of years to project forward
     * @return the projected future value
     */
    public static double calculateFutureValue(double presentValue, double growthRate, int years) {
        // Base case: at year 0, the future value is just the present value
        if (years == 0) {
            return presentValue;
        }
        // Recursive case: grow last year's value by one more year, one step at a time
        return calculateFutureValue(presentValue, growthRate, years - 1) * (1 + growthRate);
    }

    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        while (true) {
            System.out.println("\n--- Financial Forecast ---");
            System.out.print("Enter present value (or -1 to exit): ");
            double presentValue = Double.parseDouble(scanner.nextLine().trim());

            if (presentValue == -1) {
                System.out.println("Exiting. Goodbye!");
                scanner.close();
                return;
            }

            System.out.print("Enter annual growth rate as a decimal (e.g., 0.08 for 8%): ");
            double growthRate = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Enter number of years to project: ");
            int years = Integer.parseInt(scanner.nextLine().trim());

            double futureValue = calculateFutureValue(presentValue, growthRate, years);
            System.out.printf("Future value after %d years: %.2f%n", years, futureValue);
        }
    }
}

/*
 * Analysis:
 * - Time complexity: O(n), where n is the number of years. Each recursive
 *   call does a constant amount of work and reduces the problem by exactly
 *   one year, so the calls form a simple chain down to the base case.
 * - Space complexity: O(n) as well, since each recursive call adds a new
 *   frame to the call stack until the base case is hit - for very large year
 *   counts this could risk a stack overflow.
 * - Optimization: this can be rewritten iteratively (a simple for-loop
 *   multiplying the value by (1 + growthRate) each iteration) to get the same
 *   O(n) time complexity but O(1) space, avoiding the call stack overhead
 *   entirely. Recursion here is mainly valuable for how clearly it mirrors
 *   the year-over-year growth relationship, not for raw performance.
 */
