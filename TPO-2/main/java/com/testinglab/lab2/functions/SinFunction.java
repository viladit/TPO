package com.testinglab.lab2.functions;

import java.util.Objects;

public final class SinFunction implements MathFunction {

    private final MathFunction cos;

    public SinFunction() {
        this(new CosFunction());
    }

    public SinFunction(MathFunction cos) {
        this.cos = Objects.requireNonNull(cos);
    }

    @Override
    public double calculate(double x, double epsilon) {
        FunctionSupport.validateCommonArguments(x, epsilon);
        return cos.calculate(FunctionSupport.HALF_PI - x, epsilon);
    }
}
