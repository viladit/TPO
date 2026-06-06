package com.testinglab.lab2.functions;

public final class LnFunction implements MathFunction {

    private static final int MAX_ITERATIONS = 1_000_000;

    @Override
    public double calculate(double x, double epsilon) {
        FunctionSupport.validateCommonArguments(x, epsilon);
        FunctionSupport.requirePositive(x, "ln(x)");

        if (x == 1.0d) {
            return 0.0d;
        }

        double z = (x - 1.0d) / (x + 1.0d);
        double zSquared = z * z;
        double term = z;
        double sum = 0.0d;

        for (int n = 0; n < MAX_ITERATIONS; n++) {
            double addend = term / (2.0d * n + 1.0d);
            sum += addend;

            if (Math.abs(2.0d * addend) <= epsilon) {
                return 2.0d * sum;
            }

            term *= zSquared;
        }

        throw new IllegalStateException("ln(x) series did not converge.");
    }
}
