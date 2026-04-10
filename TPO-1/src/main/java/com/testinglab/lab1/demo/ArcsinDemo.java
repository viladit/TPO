package com.testinglab.lab1.demo;

import com.testinglab.lab1.math.ArcsinSeries;

public final class ArcsinDemo {

    private ArcsinDemo() {
    }

    public static void main(String[] args) {
        double epsilon = 1e-12;
        double[] values = {0.0, 0.1, 0.5, 0.9, 1.0, -0.5};

        System.out.println("Демонстрация вычисления arcsin(x) через степенной ряд");
        System.out.println("---------------------------------------------------");
        for (double x : values) {
            double actual = ArcsinSeries.calculate(x, epsilon);
            double expected = Math.asin(x);
            System.out.printf("x = %.2f | ряд = %.12f | Math.asin = %.12f%n", x, actual, expected);
        }
    }
}
