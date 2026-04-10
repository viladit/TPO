package com.testinglab.lab1.math;

public final class ArcsinSeries {

    private static final int MAX_ITERATIONS = 100_000;

    private ArcsinSeries() {
    }

    public static double calculate(double x, double epsilon) {
        return calculate(x, epsilon, MAX_ITERATIONS);
    }

    static double calculate(double x, double epsilon, int maxIterations) {
        validateArguments(x, epsilon);
        if (maxIterations <= 0) {
            throw new IllegalArgumentException("Максимальное число итераций должно быть больше нуля.");
        }

        if (x == 1.0d) {
            return Math.PI / 2;
        }
        if (x == -1.0d) {
            return -Math.PI / 2;
        }

        double sum = x;
        double term = x;
        int n = 0;

        while (Math.abs(term) > epsilon && n < maxIterations) {
            double factor1 = (2.0 * n + 1.0);
            double factor2 = (2.0 * n + 3.0);

            double multiplier = (factor1 * factor1 * x * x) / (2.0 * (n + 1.0) * factor2);

            term = term * multiplier;
            sum = sum + term;
            n++;
        }

        if (n == maxIterations && Math.abs(term) > epsilon) {
            throw new IllegalStateException("Ряд не сошёлся за допустимое число итераций.");
        }

        return sum;
    }

    private static void validateArguments(double x, double epsilon) {
        if (Double.isNaN(x)) {
            throw new IllegalArgumentException("x должен быть корректным числом.");
        }
        if (Math.abs(x) > 1.0d) {
            throw new IllegalArgumentException("arcsin(x) определён только для x из отрезка [-1, 1].");
        }
        if (epsilon <= 0.0d || Double.isNaN(epsilon)) {
            throw new IllegalArgumentException("epsilon должен быть больше нуля.");
        }
    }
}
