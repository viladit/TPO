package com.testinglab.lab2.functions;

import java.util.Objects;

public final class PiecewiseSystemFunction implements MathFunction {

    private final MathFunction trigonometricBranch;
    private final MathFunction logarithmicBranch;

    public PiecewiseSystemFunction(MathFunction trigonometricBranch, MathFunction logarithmicBranch) {
        this.trigonometricBranch = Objects.requireNonNull(trigonometricBranch);
        this.logarithmicBranch = Objects.requireNonNull(logarithmicBranch);
    }

    @Override
    public double calculate(double x, double epsilon) {
        FunctionSupport.validateCommonArguments(x, epsilon);

        if (x <= 0.0d) {
            return trigonometricBranch.calculate(x, epsilon);
        }
        return logarithmicBranch.calculate(x, epsilon);
    }
}
