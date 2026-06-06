package com.testinglab.lab2.functions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.testinglab.lab2.stubs.Lab2Stubs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SystemFunctionsTest {

    private static final double GRAPH_ABSOLUTE_DELTA = 1e-4;
    private static final double ROOT_ABSOLUTE_DELTA = 2e-3;
    private static final double RELATIVE_DELTA = 1e-6;
    private final MathFunction trigonometricBranch = new TrigonometricSystemFunction(
            Lab2Stubs.sin(),
            Lab2Stubs.cos(),
            Lab2Stubs.tan(),
            Lab2Stubs.cot(),
            Lab2Stubs.sec(),
            Lab2Stubs.csc()
    );
    private final MathFunction logarithmicBranch = new LogarithmicSystemFunction(
            Lab2Stubs.log2(),
            Lab2Stubs.log3(),
            Lab2Stubs.log5(),
            Lab2Stubs.ln()
    );
    private final MathFunction system = new PiecewiseSystemFunction(
            Lab2Stubs.trigonometricBranch(),
            Lab2Stubs.logarithmicBranch()
    );

    @ParameterizedTest(name = "trig({0}) ~= {1}")
    @CsvSource({
            "-4.0, 53.01461", //
            "-2.5, 7.30885",
            "-2.0, -56.42188",
            "-1.0, 13.0572", //
            "-0.8465, 8.4784", //
            "-0.74, 13.13613", //
            "-1.236, 40.27487", //
            "-1.28, 26.52005", //
            "-3.88683, 16.0881", //
            "-3.83, 24.63278", //
            "-5.66648, 5.20849", //
            "-5.636, 15.54246", //
            "-5.692, 17.01965", //
             "-2.17, -8.90288", //
            "-2.53, 22.05888", //
            "-1.3001, 0", // добавил после прошлой защиты
            "-2.443492, 0", // тоже
    })
    @DisplayName("Computes the trigonometric branch near extrema and steep neighborhoods")
    void shouldCalculateTrigonometricBranch(double x, double expected) {
        assertClose(expected, trigonometricBranch.calculate(x, Lab2Stubs.EPSILON));
    }

    @ParameterizedTest(name = "log({0}) ~= {1}")
    @CsvSource({
            "0.006, 7.38082",
            "0.073, 3.77592",
            "0.00009, 13.43972",
            "1, 0", // добавил после прошлой защиты
    })
    @DisplayName("Computes the logarithmic branch around the removable hole and growth areas")
    void shouldCalculateLogarithmicBranch(double x, double expected) {
        assertClose(expected, logarithmicBranch.calculate(x, Lab2Stubs.EPSILON));
    }

    @Test
    @DisplayName("Keeps the trigonometric branch periodicity")
    void trigonometricBranchShouldBePeriodic() {
        double current = trigonometricBranch.calculate(-4.3d, Lab2Stubs.EPSILON);
        double shifted = trigonometricBranch.calculate(-4.3d - Lab2Stubs.TWO_PI, Lab2Stubs.EPSILON);

        assertClose(current, shifted);
    }

    @Test
    @DisplayName("Rejects trigonometric asymptotes")
    void shouldRejectSpecialUndefinedPoints() {
        MathFunction realTrigonometricBranch = FunctionFactory.trigonometricBranch();

        assertThrows(ArithmeticException.class, () -> realTrigonometricBranch.calculate(0.0d, Lab2Stubs.EPSILON));
        assertThrows(ArithmeticException.class, () -> realTrigonometricBranch.calculate(-Lab2Stubs.HALF_PI, Lab2Stubs.EPSILON));
    }

    @ParameterizedTest(name = "system({0}) ~= {1}")
    @CsvSource({
            "0.5, 1.0",
            "2.0, -1.0",
            "10.0, -3.32193",
            "-0.8465, 8.4784",
            "-1.236, 40.27487",
            "-3.88683, 16.0881",
            "-5.66648, 5.20849",
            "8, -3",
            "12.01, -3.58616",
            "0.0295, 5.08314"
    })
    @DisplayName("Routes the piecewise system to the correct branch")
    void shouldCalculatePiecewiseSystem(double x, double expected) {
        assertClose(expected, system.calculate(x, Lab2Stubs.EPSILON));
    }

    @ParameterizedTest(name = "system({0}) is close to Ox")
    @CsvSource({
            "-1.3001",
            "-2.44349"
    })
    @DisplayName("Computes neighborhoods of x-axis intersections taken from Desmos")
    void shouldBeCloseToZeroNearXAxisIntersections(double x) {
        assertEquals(0.0d, system.calculate(x, Lab2Stubs.EPSILON), 2e-3);
    }

    private static void assertClose(double expected, double actual) {
        double absoluteDelta = expected == 0.0d ? ROOT_ABSOLUTE_DELTA : GRAPH_ABSOLUTE_DELTA;
        double delta = Math.max(absoluteDelta, Math.abs(expected) * RELATIVE_DELTA);
        assertEquals(expected, actual, delta);
    }
}
