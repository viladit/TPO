package com.testinglab.lab2.functions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.testinglab.lab2.stubs.Lab2Stubs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class LnFunctionTest {

    private static final double ASSERT_DELTA = 1e-5;
    private final MathFunction ln = new LnFunction();

    @ParameterizedTest(name = "ln({0}) ~= {1}")
    @CsvSource({
            "0.5, -0.69315",
            "1.0, 0.0",
            "2.0, 0.69315",
            "3.0, 1.09861",
            "10.0, 2.30259"
    })
    @DisplayName("Computes tabulated natural logarithm values")
    void shouldMatchTabulatedValues(double x, double expected) {
        assertEquals(expected, ln.calculate(x, Lab2Stubs.EPSILON), ASSERT_DELTA);
    }

    @Test
    @DisplayName("Shows the vertical asymptote neighborhood near zero")
    void shouldDecreaseNearZeroAsymptote() {
        double closerToZero = ln.calculate(0.1d, Lab2Stubs.EPSILON);
        double fartherFromZero = ln.calculate(0.5d, Lab2Stubs.EPSILON);

        assertTrue(closerToZero < fartherFromZero);
    }

    @Test
    @DisplayName("Rejects values outside the natural logarithm domain")
    void shouldRejectInvalidDomain() {
        assertThrows(IllegalArgumentException.class, () -> ln.calculate(0.0d, Lab2Stubs.EPSILON));
        assertThrows(IllegalArgumentException.class, () -> ln.calculate(-1.0d, Lab2Stubs.EPSILON));
    }
}
