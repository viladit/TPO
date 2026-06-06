package com.testinglab.lab2.functions;

final class FunctionSupport {

    static final double PI = Math.PI;
    static final double TWO_PI = 2.0d * Math.PI;
    static final double HALF_PI = Math.PI / 2.0d;
    static final double ZERO_TOLERANCE = 1e-10;

    private FunctionSupport() {
    }

    static void validateFiniteX(double x) {
        if (!Double.isFinite(x)) {
            throw new IllegalArgumentException("x must be a finite number.");
        }
    }

    static void validateEpsilon(double epsilon) {
        if (!Double.isFinite(epsilon) || epsilon <= 0.0d) {
            throw new IllegalArgumentException("epsilon must be a positive finite number.");
        }
    }

    static void validateCommonArguments(double x, double epsilon) {
        validateFiniteX(x);
        validateEpsilon(epsilon);
    }

    static void requirePositive(double x, String functionName) {
        if (x <= 0.0d) {
            throw new IllegalArgumentException(functionName + " is defined only for x > 0.");
        }
    }

    static void requireNonZero(double value, String denominatorName) {
        if (Math.abs(value) <= ZERO_TOLERANCE) {
            throw new ArithmeticException(denominatorName + " is zero at this point.");
        }
    }

    static double square(double value) {
        return value * value;
    }

    static double cube(double value) {
        return value * value * value;
    }
}
