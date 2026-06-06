package com.testinglab.lab2.functions;

import java.util.Objects;

public final class LogBaseFunction implements MathFunction {

    private final MathFunction ln;
    private final double base;
    private final String name;

    public LogBaseFunction(MathFunction ln, double base, String name) {
        this.ln = Objects.requireNonNull(ln);
        FunctionSupport.validateFiniteX(base);
        FunctionSupport.requirePositive(base, "log base");
        if (Math.abs(base - 1.0d) <= FunctionSupport.ZERO_TOLERANCE) {
            throw new IllegalArgumentException("log base must not be 1.");
        }
        this.base = base;
        this.name = Objects.requireNonNull(name);
    }

    @Override
    public double calculate(double x, double epsilon) {
        FunctionSupport.validateCommonArguments(x, epsilon);
        FunctionSupport.requirePositive(x, name);

        double denominator = ln.calculate(base, epsilon);
        FunctionSupport.requireNonZero(denominator, "ln(base)");
        return ln.calculate(x, epsilon) / denominator;
    }
}
