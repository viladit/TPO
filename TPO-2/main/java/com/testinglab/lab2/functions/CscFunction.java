package com.testinglab.lab2.functions;

import java.util.Objects;

public final class CscFunction implements MathFunction {

    private final MathFunction sin;

    public CscFunction(MathFunction sin) {
        this.sin = Objects.requireNonNull(sin);
    }

    @Override
    public double calculate(double x, double epsilon) {
        FunctionSupport.validateCommonArguments(x, epsilon);

        double denominator = sin.calculate(x, epsilon);
        FunctionSupport.requireNonZero(denominator, "sin(x)");
        return 1.0d / denominator;
    }
}
