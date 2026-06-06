package com.testinglab.lab2.functions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.testinglab.lab2.stubs.Lab2Stubs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class LogarithmicFunctionsTest {

    private static final double ASSERT_DELTA = 1e-5;
    private final MathFunction ln = new LnFunction();
    private final MathFunction log2 = new LogBaseFunction(ln, 2.0d, "log_2(x)");
    private final MathFunction log3 = new LogBaseFunction(ln, 3.0d, "log_3(x)");
    private final MathFunction log5 = new LogBaseFunction(ln, 5.0d, "log_5(x)");

    @ParameterizedTest(name = "x={0}")
    @CsvSource({
            "0.1, -3.32193, -2.0959, -1.43068",
            "0.5, -1.0, -0.63093, -0.43068",
            "1.0, 0.0, 0.0, 0.0",
            "2.0, 1.0, 0.63093, 0.43068",
            "3.0, 1.58496, 1.0, 0.68261",
            "5.0, 2.32193, 1.46497, 1.0",
            "10.0, 3.32193, 2.0959, 1.43068"
    })
    @DisplayName("Computes logarithms through ln(x)")
    void shouldCalculateLogarithmsThroughNaturalLog(
            double x,
            double expectedLog2,
            double expectedLog3,
            double expectedLog5
    ) {
        assertEquals(expectedLog2, log2.calculate(x, Lab2Stubs.EPSILON), ASSERT_DELTA);
        assertEquals(expectedLog3, log3.calculate(x, Lab2Stubs.EPSILON), ASSERT_DELTA);
        assertEquals(expectedLog5, log5.calculate(x, Lab2Stubs.EPSILON), ASSERT_DELTA);
    }

    @ParameterizedTest(name = "x={0}")
    @CsvSource({
            "0.0",
            "-0.5"
    })
    @DisplayName("Rejects invalid logarithm domain")
    void shouldRejectInvalidDomain(double x) {
        assertThrows(IllegalArgumentException.class, () -> log2.calculate(x, Lab2Stubs.EPSILON));
        assertThrows(IllegalArgumentException.class, () -> log3.calculate(x, Lab2Stubs.EPSILON));
        assertThrows(IllegalArgumentException.class, () -> log5.calculate(x, Lab2Stubs.EPSILON));
    }
}
