package com.testinglab.lab2.functions;

import java.util.Objects;

public final class LogarithmicSystemFunction implements MathFunction {

    private final MathFunction log2;
    private final MathFunction log3;
    private final MathFunction log5;
    private final MathFunction ln;

    public LogarithmicSystemFunction(MathFunction log2, MathFunction log3, MathFunction log5, MathFunction ln) {
        this.log2 = Objects.requireNonNull(log2);
        this.log3 = Objects.requireNonNull(log3);
        this.log5 = Objects.requireNonNull(log5);
        this.ln = Objects.requireNonNull(ln);
    }

    @Override
    public double calculate(double x, double epsilon) {
        FunctionSupport.validateCommonArguments(x, epsilon);
        FunctionSupport.requirePositive(x, "logarithmic branch");

        if (x == 1.0d) {
            return 0.0d;
        }

        double log2X = log2.calculate(x, epsilon);
        double log3X = log3.calculate(x, epsilon);
        double lnX = ln.calculate(x, epsilon);
        double log5X = log5.calculate(x, epsilon);
        FunctionSupport.requireNonZero(log3X, "log_3(x)");
        FunctionSupport.requireNonZero(lnX, "ln(x)");

        return (((((log2X - log2X) / log3X) / lnX) * log5X)
                - (log5X - (log5X - log2X)));
    }
}
