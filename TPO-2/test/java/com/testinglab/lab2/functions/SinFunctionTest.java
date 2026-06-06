package com.testinglab.lab2.functions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.testinglab.lab2.stubs.Lab2Stubs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SinFunctionTest {

    private static final double ASSERT_DELTA = 1e-5;
    private final MathFunction sin = new SinFunction(new CosFunction());

    @ParameterizedTest(name = "sin({0}) ~= {1}")
    @CsvSource({
            "-1.5708, -1.0",
            "-0.7854, -0.70711",
            "0.0, 0.0",
            "0.7854, 0.70711",
            "1.5708, 1.0"
    })
    @DisplayName("Computes sine through the base cosine module")
    void shouldMatchTabulatedValues(double x, double expected) {
        assertEquals(expected, sin.calculate(x, Lab2Stubs.EPSILON), ASSERT_DELTA);
    }

    @ParameterizedTest(name = "x={0}")
    @CsvSource({
            "-2.0",
            "-0.7",
            "1.3"
    })
    @DisplayName("Keeps the 2pi periodicity inherited from cosine")
    void shouldBePeriodic(double x) {
        double current = sin.calculate(x, Lab2Stubs.EPSILON);
        double shifted = sin.calculate(x + Lab2Stubs.TWO_PI, Lab2Stubs.EPSILON);

        assertEquals(current, shifted, ASSERT_DELTA);
    }

    @Test
    @DisplayName("Rejects invalid arguments")
    void shouldRejectInvalidArguments() {
        assertThrows(IllegalArgumentException.class, () -> sin.calculate(Double.NaN, Lab2Stubs.EPSILON));
        assertThrows(IllegalArgumentException.class, () -> sin.calculate(0.0d, 0.0d));
    }
}
