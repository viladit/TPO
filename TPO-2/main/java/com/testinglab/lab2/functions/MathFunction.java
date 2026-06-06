package com.testinglab.lab2.functions;

@FunctionalInterface
public interface MathFunction {

    double calculate(double x, double epsilon);
}
