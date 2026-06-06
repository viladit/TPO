package com.testinglab.lab2.functions;

import java.util.Objects;

public final class CotFunction implements MathFunction {

    private final MathFunction sin;
    private final MathFunction cos;

    public CotFunction(MathFunction sin, MathFunction cos) {
        this.sin = Objects.requireNonNull(sin);
        this.cos = Objects.requireNonNull(cos);
    }

    @Override
    public double calculate(double x, double epsilon) {
        FunctionSupport.validateCommonArguments(x, epsilon);

        double denominator = sin.calculate(x, epsilon);
        FunctionSupport.requireNonZero(denominator, "sin(x)");
        return cos.calculate(x, epsilon) / denominator;
    }
}
