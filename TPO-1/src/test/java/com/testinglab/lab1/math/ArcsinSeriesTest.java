package com.testinglab.lab1.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ArcsinSeriesTest {

    private static final double EPSILON = 1e-12;
    private static final double ASSERT_DELTA = 1e-10;

    @ParameterizedTest(name = "arcsin({0}) ~= {1}")
    @CsvSource({
            "0.0, 0.0",
            "0.5, 0.5235987755982989",
            "-0.5, -0.5235987755982989",
            "0.7071067811865476, 0.7853981633974483",
            "-0.7071067811865476, -0.7853981633974483",
            "0.8660254037844386, 1.0471975511965979",
            "-0.8660254037844386, -1.0471975511965979"
    })
    @DisplayName("Должен вычислять arcsin(x) по табличным значениям из области определения")
    void shouldMatchTabulatedValues(double x, double expected) {
        double actual = ArcsinSeries.calculate(x, EPSILON);

        assertEquals(expected, actual, ASSERT_DELTA);
    }

    @ParameterizedTest(name = "Функция должна быть нечётной для x={0}")
    @CsvSource({
            "0.1",
            "0.5",
            "0.9"
    })
    @DisplayName("Должен сохранять нечётность: asin(-x) = -asin(x)")
    void shouldBeOddFunction(double x) {
        double positive = ArcsinSeries.calculate(x, EPSILON);
        double negative = ArcsinSeries.calculate(-x, EPSILON);

        assertEquals(-positive, negative, ASSERT_DELTA);
    }

    @Test
    @DisplayName("Должен корректно обрабатывать границы области определения")
    void shouldHandleDomainBoundaries() {
        assertEquals(Math.PI / 2, ArcsinSeries.calculate(1.0, EPSILON), ASSERT_DELTA);
        assertEquals(-Math.PI / 2, ArcsinSeries.calculate(-1.0, EPSILON), ASSERT_DELTA);
    }

    @ParameterizedTest(name = "x={0} is outside the domain")
    @CsvSource({
            "1.1",
            "-1.1"
    })
    @DisplayName("Должен отклонять значения вне отрезка [-1, 1]")
    void shouldRejectValuesOutsideDomain(double x) {
        assertThrows(IllegalArgumentException.class, () -> ArcsinSeries.calculate(x, EPSILON));
    }

    @Test
    @DisplayName("Должен отклонять значение NaN")
    void shouldRejectNaNInput() {
        assertThrows(IllegalArgumentException.class, () -> ArcsinSeries.calculate(Double.NaN, EPSILON));
    }

    @Test
    @DisplayName("Должен отклонять неположительное epsilon")
    void shouldRejectNonPositiveEpsilon() {
        assertThrows(IllegalArgumentException.class, () -> ArcsinSeries.calculate(0.5, 0.0));
        assertThrows(IllegalArgumentException.class, () -> ArcsinSeries.calculate(0.5, -1e-6));
        assertThrows(IllegalArgumentException.class, () -> ArcsinSeries.calculate(0.5, Double.NaN));
    }

    @Test
    @DisplayName("Должен отклонять неположительное число итераций")
    void shouldRejectNonPositiveMaxIterations() {
        assertThrows(IllegalArgumentException.class, () -> ArcsinSeries.calculate(0.5, EPSILON, 0));
    }

    @Test
    @DisplayName("Должен сообщать о несходимости при слишком малом лимите итераций")
    void shouldFailWhenSeriesDoesNotConvergeWithinIterationLimit() {
        assertThrows(IllegalStateException.class, () -> ArcsinSeries.calculate(0.9, EPSILON, 1));
    }

    @Test
    @DisplayName("Не должен бросать исключение, если ряд сошёлся на последней допустимой итерации")
    void shouldNotFailWhenSeriesConvergesOnLastAllowedIteration() {
        double actual = ArcsinSeries.calculate(0.1, 1e-3, 1);

        assertEquals(0.10016666666666667, actual, ASSERT_DELTA);
    }
}
