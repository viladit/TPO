package com.testinglab.lab2.functions;

public final class CosFunction implements MathFunction {

    private static final int MAX_ITERATIONS = 100_000;

    @Override
    public double calculate(double x, double epsilon) {
        FunctionSupport.validateCommonArguments(x, epsilon);

        double reduced = reduceToPrincipalPeriod(x);
        double term = 1.0d;
        double sum = 1.0d;

        for (int n = 1; n <= MAX_ITERATIONS; n++) {
            term = -term * reduced * reduced / ((2.0d * n - 1.0d) * (2.0d * n));
            sum += term;

            if (Math.abs(term) <= epsilon) {
                return normalizeSignedZero(sum);
            }
        }

        throw new IllegalStateException("cos(x) series did not converge.");
    }

    private static double reduceToPrincipalPeriod(double x) {
        double reduced = x % FunctionSupport.TWO_PI;
        if (reduced > FunctionSupport.PI) {
            reduced -= FunctionSupport.TWO_PI;
        }
        if (reduced < -FunctionSupport.PI) {
            reduced += FunctionSupport.TWO_PI;
        }
        return reduced;
    }

    private static double normalizeSignedZero(double value) {
        if (Math.abs(value) <= FunctionSupport.ZERO_TOLERANCE) {
            return 0.0d;
        }
        return value;
    }
}
