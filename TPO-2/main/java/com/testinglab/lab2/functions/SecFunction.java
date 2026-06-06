package com.testinglab.lab2.functions;

import java.util.Objects;

public final class SecFunction implements MathFunction {

    private final MathFunction cos;

    public SecFunction(MathFunction cos) {
        this.cos = Objects.requireNonNull(cos);
    }

    @Override
    public double calculate(double x, double epsilon) {
        FunctionSupport.validateCommonArguments(x, epsilon);

        double denominator = cos.calculate(x, epsilon);
        FunctionSupport.requireNonZero(denominator, "cos(x)");
        return 1.0d / denominator;
    }
}
