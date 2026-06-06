package com.testinglab.lab2.functions;

import java.util.Objects;

public final class TrigonometricSystemFunction implements MathFunction {

    private final MathFunction sin;
    private final MathFunction cos;
    private final MathFunction tan;
    private final MathFunction cot;
    private final MathFunction sec;
    private final MathFunction csc;

    public TrigonometricSystemFunction(
            MathFunction sin,
            MathFunction cos,
            MathFunction tan,
            MathFunction cot,
            MathFunction sec,
            MathFunction csc
    ) {
        this.sin = Objects.requireNonNull(sin);
        this.cos = Objects.requireNonNull(cos);
        this.tan = Objects.requireNonNull(tan);
        this.cot = Objects.requireNonNull(cot);
        this.sec = Objects.requireNonNull(sec);
        this.csc = Objects.requireNonNull(csc);
    }

    @Override
    public double calculate(double x, double epsilon) {
        FunctionSupport.validateCommonArguments(x, epsilon);

        double sinX = sin.calculate(x, epsilon);
        double cosX = cos.calculate(x, epsilon);
        double tanX = tan.calculate(x, epsilon);
        double cotX = cot.calculate(x, epsilon);
        double secX = sec.calculate(x, epsilon);
        double cscX = csc.calculate(x, epsilon);

        FunctionSupport.requireNonZero(tanX, "tan(x)");
        FunctionSupport.requireNonZero(cotX, "cot(x)");
        FunctionSupport.requireNonZero(cosX, "cos(x)");
        FunctionSupport.requireNonZero(cscX, "csc(x)");

        double firstNumerator = ((FunctionSupport.square(((((cosX * cotX) * cosX) / sinX) / secX)) * secX)
                + cosX - (cotX * cosX)) * cosX;
        double firstDenominator = tanX / FunctionSupport.square(cotX);
        FunctionSupport.requireNonZero(firstDenominator, "tan(x) / cot(x)^2");
        double firstTerm = FunctionSupport.square((firstNumerator / firstDenominator) + tanX);

        double secondLeft = FunctionSupport.square(
                FunctionSupport.square(cosX) + (cscX + (tanX + (FunctionSupport.cube(cotX) - cotX))));
        double cscMinusTanDivSec = cscX - (tanX / secX);
        FunctionSupport.requireNonZero(cscMinusTanDivSec, "csc(x) - tan(x) / sec(x)");
        double secondInner = ((cotX - cotX) / tanX) / (cscX / cscMinusTanDivSec);
        double secondMiddle = (secondInner / cosX) - (cscX + secX);
        double secondDenominator = cotX / (cscX * (FunctionSupport.square(cosX) / cotX));
        FunctionSupport.requireNonZero(secondDenominator, "second branch denominator");
        double secondRight = (secondMiddle / secondDenominator) - tanX;
        double secondTerm = secondLeft * secondRight;

        double cosMinusCot = cosX - cotX;
        FunctionSupport.requireNonZero(cosMinusCot, "cos(x) - cot(x)");
        double thirdTerm = (secX + (cscX + ((FunctionSupport.square(sinX) / FunctionSupport.square(secX)) - sinX)))
                - FunctionSupport.cube((sinX + secX) / cosMinusCot);

        return firstTerm + secondTerm + thirdTerm;
    }
}
