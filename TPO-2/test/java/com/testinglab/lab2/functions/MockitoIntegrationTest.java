package com.testinglab.lab2.functions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.testinglab.lab2.stubs.Lab2Stubs;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MockitoIntegrationTest {

    private static final double ASSERT_DELTA = 1e-5;
    private static final double TRIGONOMETRIC_X = -4.3d;
    private static final double TRIGONOMETRIC_EXPECTED = 79786.31398d;

    private record StubTable(String name, Supplier<MathFunction> factory, Map<Double, Double> values) {
    }

    @Test
    @DisplayName("Система выбирает ветку по табличным заглушкам")
    void shouldIntegrateSystemOverBranchStubs() {
        MathFunction trigonometric = Lab2Stubs.trigonometricBranch();
        MathFunction logarithmic = Lab2Stubs.logarithmicBranch();
        MathFunction system = new PiecewiseSystemFunction(trigonometric, logarithmic);

        assertEquals(TRIGONOMETRIC_EXPECTED, system.calculate(-4.3d, Lab2Stubs.EPSILON), ASSERT_DELTA);
        assertEquals(1.0d, system.calculate(0.5d, Lab2Stubs.EPSILON), ASSERT_DELTA);

        verify(trigonometric).calculate(-4.3d, Lab2Stubs.EPSILON);
        verify(trigonometric, never()).calculate(0.5d, Lab2Stubs.EPSILON);
        verify(logarithmic).calculate(0.5d, Lab2Stubs.EPSILON);
        verify(logarithmic, never()).calculate(-4.3d, Lab2Stubs.EPSILON);
    }

    @Test
    @DisplayName("Полная система работает с реальными cos и ln")
    void shouldIntegrateFullRealSystem() {
        MathFunction system = FunctionFactory.system();

        assertEquals(TRIGONOMETRIC_EXPECTED, system.calculate(-4.3d, Lab2Stubs.EPSILON), ASSERT_DELTA);
        assertEquals(-1.0d, system.calculate(2.0d, Lab2Stubs.EPSILON), ASSERT_DELTA);
    }

    @Test
    @DisplayName("Логарифмическая ветка над заглушками log2, log3, log5 и ln")
    void shouldIntegrateLogarithmicBranchOverLogStubs() {
        MathFunction log2 = Lab2Stubs.log2();
        MathFunction log3 = Lab2Stubs.log3();
        MathFunction log5 = Lab2Stubs.log5();
        MathFunction ln = Lab2Stubs.ln();
        MathFunction branch = new LogarithmicSystemFunction(log2, log3, log5, ln);

        assertEquals(-1.0d, branch.calculate(2.0d, Lab2Stubs.EPSILON), ASSERT_DELTA);

        verify(log2).calculate(2.0d, Lab2Stubs.EPSILON);
        verify(log3).calculate(2.0d, Lab2Stubs.EPSILON);
        verify(log5).calculate(2.0d, Lab2Stubs.EPSILON);
        verify(ln).calculate(2.0d, Lab2Stubs.EPSILON);
    }

    @Test
    @DisplayName("Логарифмы log2, log3 и log5 работают над заглушкой ln")
    void shouldIntegrateLogModulesOverLnStub() {
        MathFunction ln = Lab2Stubs.ln();
        MathFunction log2 = new LogBaseFunction(ln, 2.0d, "log_2(x)");
        MathFunction log3 = new LogBaseFunction(ln, 3.0d, "log_3(x)");
        MathFunction log5 = new LogBaseFunction(ln, 5.0d, "log_5(x)");
        MathFunction branch = new LogarithmicSystemFunction(log2, log3, log5, ln);

        assertEquals(-1.0d, branch.calculate(2.0d, Lab2Stubs.EPSILON), ASSERT_DELTA);

        verify(ln, times(5)).calculate(2.0d, Lab2Stubs.EPSILON);
        verify(ln).calculate(3.0d, Lab2Stubs.EPSILON);
        verify(ln).calculate(5.0d, Lab2Stubs.EPSILON);
    }

    @Test
    @DisplayName("Тригонометрия сверху вниз, этап 1: формула ветки над заглушками всех функций")
    void shouldIntegrateTrigonometricBranchOverModuleStubs() {
        MathFunction sin = Lab2Stubs.sin();
        MathFunction cos = Lab2Stubs.cos();
        MathFunction tan = Lab2Stubs.tan();
        MathFunction cot = Lab2Stubs.cot();
        MathFunction sec = Lab2Stubs.sec();
        MathFunction csc = Lab2Stubs.csc();
        MathFunction branch = new TrigonometricSystemFunction(sin, cos, tan, cot, sec, csc);

        assertEquals(TRIGONOMETRIC_EXPECTED, branch.calculate(TRIGONOMETRIC_X, Lab2Stubs.EPSILON), ASSERT_DELTA);

        verify(sin).calculate(TRIGONOMETRIC_X, Lab2Stubs.EPSILON);
        verify(cos).calculate(TRIGONOMETRIC_X, Lab2Stubs.EPSILON);
        verify(tan).calculate(TRIGONOMETRIC_X, Lab2Stubs.EPSILON);
        verify(cot).calculate(TRIGONOMETRIC_X, Lab2Stubs.EPSILON);
        verify(sec).calculate(TRIGONOMETRIC_X, Lab2Stubs.EPSILON);
        verify(csc).calculate(TRIGONOMETRIC_X, Lab2Stubs.EPSILON);
    }

    @Test
    @DisplayName("Тригонометрия сверху вниз, этап 2: реальные tan, cot, sec, csc над заглушками sin и cos")
    void shouldIntegrateRealDerivedTrigModulesOverSinCosStubs() {
        MathFunction sin = Lab2Stubs.sin();
        MathFunction cos = Lab2Stubs.cos();
        MathFunction branch = trigonometricBranchWithRealDerivedModules(sin, cos);

        assertEquals(TRIGONOMETRIC_EXPECTED, branch.calculate(TRIGONOMETRIC_X, Lab2Stubs.EPSILON), ASSERT_DELTA);

        verify(sin, times(4)).calculate(TRIGONOMETRIC_X, Lab2Stubs.EPSILON);
        verify(cos, times(4)).calculate(TRIGONOMETRIC_X, Lab2Stubs.EPSILON);
    }

    @Test
    @DisplayName("Тригонометрия сверху вниз, этап 3: реальный sin и производные функции над заглушкой cos")
    void shouldIntegrateRealSinAndDerivedTrigModulesOverCosStub() {
        MathFunction cos = Lab2Stubs.cos();
        MathFunction sin = new SinFunction(cos);
        MathFunction branch = trigonometricBranchWithRealDerivedModules(sin, cos);
        double shiftedToCosArgument = FunctionSupport.HALF_PI - TRIGONOMETRIC_X;

        assertEquals(TRIGONOMETRIC_EXPECTED, branch.calculate(TRIGONOMETRIC_X, Lab2Stubs.EPSILON), ASSERT_DELTA);

        verify(cos, times(4)).calculate(TRIGONOMETRIC_X, Lab2Stubs.EPSILON);
        verify(cos, times(4)).calculate(shiftedToCosArgument, Lab2Stubs.EPSILON);
    }

    @Test
    @DisplayName("Тригонометрия сверху вниз, этап 4: полная ветка работает через реальный cos")
    void shouldIntegrateFullTrigonometricBranchThroughRealCos() {
        MathFunction branch = FunctionFactory.trigonometricBranch();

        assertEquals(TRIGONOMETRIC_EXPECTED, branch.calculate(TRIGONOMETRIC_X, Lab2Stubs.EPSILON), ASSERT_DELTA);
    }

    @ParameterizedTest(name = "{0}({2}) = {3}")
    @MethodSource("tabulatedStubCases")
    @DisplayName("Каждая табличная заглушка возвращает все подготовленные значения")
    void shouldProvideTabulatedStubsForEveryValue(
            String module,
            MathFunction function,
            double x,
            double expected
    ) {
        assertEquals(expected, function.calculate(x, Lab2Stubs.EPSILON), ASSERT_DELTA);
    }

    private static Stream<Arguments> tabulatedStubCases() {
        return Stream.of(
                        new StubTable("sin", Lab2Stubs::sin, Lab2Stubs.SIN_VALUES),
                        new StubTable("cos", Lab2Stubs::cos, Lab2Stubs.COS_VALUES),
                        new StubTable("tan", Lab2Stubs::tan, Lab2Stubs.TAN_VALUES),
                        new StubTable("cot", Lab2Stubs::cot, Lab2Stubs.COT_VALUES),
                        new StubTable("sec", Lab2Stubs::sec, Lab2Stubs.SEC_VALUES),
                        new StubTable("csc", Lab2Stubs::csc, Lab2Stubs.CSC_VALUES),
                        new StubTable("ln", Lab2Stubs::ln, Lab2Stubs.LN_VALUES),
                        new StubTable("log2", Lab2Stubs::log2, Lab2Stubs.LOG2_VALUES),
                        new StubTable("log3", Lab2Stubs::log3, Lab2Stubs.LOG3_VALUES),
                        new StubTable("log5", Lab2Stubs::log5, Lab2Stubs.LOG5_VALUES),
                        new StubTable(
                                "trigonometric branch",
                                Lab2Stubs::trigonometricBranch,
                                Lab2Stubs.TRIGONOMETRIC_BRANCH_VALUES
                        ),
                        new StubTable(
                                "logarithmic branch",
                                Lab2Stubs::logarithmicBranch,
                                Lab2Stubs.LOGARITHMIC_BRANCH_VALUES
                        )
                )
                .flatMap(table -> table.values().entrySet().stream()
                        .map(entry -> Arguments.of(
                                table.name(),
                                table.factory().get(),
                                entry.getKey(),
                                entry.getValue()
                        )));
    }

    private static MathFunction trigonometricBranchWithRealDerivedModules(MathFunction sin, MathFunction cos) {
        return new TrigonometricSystemFunction(
                sin,
                cos,
                new TanFunction(sin, cos),
                new CotFunction(sin, cos),
                new SecFunction(cos),
                new CscFunction(sin)
        );
    }

}
