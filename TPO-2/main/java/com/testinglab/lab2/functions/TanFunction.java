package com.testinglab.lab2.functions;

import java.util.Objects;

public final class TanFunction implements MathFunction {

    private final MathFunction sin;
    private final MathFunction cos;

    public TanFunction(MathFunction sin, MathFunction cos) {
        this.sin = Objects.requireNonNull(sin);
        this.cos = Objects.requireNonNull(cos);
    }

    @Override
    public double calculate(double x, double epsilon) {
        FunctionSupport.validateCommonArguments(x, epsilon);

        double denominator = cos.calculate(x, epsilon);
        FunctionSupport.requireNonZero(denominator, "cos(x)");
        return sin.calculate(x, epsilon) / denominator;
    }
}
