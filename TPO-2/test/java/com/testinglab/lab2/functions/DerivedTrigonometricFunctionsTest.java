package com.testinglab.lab2.functions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.testinglab.lab2.stubs.Lab2Stubs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DerivedTrigonometricFunctionsTest {

    private static final double ASSERT_DELTA = 1e-5;
    private final MathFunction cos = new CosFunction();
    private final MathFunction sin = new SinFunction(cos);
    private final MathFunction tan = new TanFunction(sin, cos);
    private final MathFunction cot = new CotFunction(sin, cos);
    private final MathFunction sec = new SecFunction(cos);
    private final MathFunction csc = new CscFunction(sin);

    @ParameterizedTest(name = "x={0}")
    @CsvSource({
            "-2.0, -0.41615, 2.18504, 0.45766, -2.403, -1.09975",
            "-1.0, 0.5403, -1.55741, -0.64209, 1.85082, -1.1884",
            "-0.7854, 0.70711, -1.0, -1.0, 1.41421, -1.41421"
    })
    @DisplayName("Computes derived trigonometric modules through cosine")
    void shouldCalculateDerivedModules(
            double x,
            double expectedCos,
            double expectedTan,
            double expectedCot,
            double expectedSec,
            double expectedCsc
    ) {
        assertEquals(expectedCos, cos.calculate(x, Lab2Stubs.EPSILON), ASSERT_DELTA);
        assertEquals(expectedTan, tan.calculate(x, Lab2Stubs.EPSILON), ASSERT_DELTA);
        assertEquals(expectedCot, cot.calculate(x, Lab2Stubs.EPSILON), ASSERT_DELTA);
        assertEquals(expectedSec, sec.calculate(x, Lab2Stubs.EPSILON), ASSERT_DELTA);
        assertEquals(expectedCsc, csc.calculate(x, Lab2Stubs.EPSILON), ASSERT_DELTA);
    }

    @Test
    @DisplayName("Rejects asymptote points for reciprocal trigonometric modules")
    void shouldRejectAsymptotes() {
        assertThrows(ArithmeticException.class, () -> cot.calculate(0.0d, Lab2Stubs.EPSILON));
        assertThrows(ArithmeticException.class, () -> csc.calculate(0.0d, Lab2Stubs.EPSILON));
        assertThrows(ArithmeticException.class, () -> tan.calculate(-Lab2Stubs.HALF_PI, Lab2Stubs.EPSILON));
        assertThrows(ArithmeticException.class, () -> sec.calculate(-Lab2Stubs.HALF_PI, Lab2Stubs.EPSILON));
    }
}
