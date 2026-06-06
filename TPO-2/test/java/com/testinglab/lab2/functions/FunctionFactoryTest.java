package com.testinglab.lab2.functions;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.testinglab.lab2.stubs.Lab2Stubs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FunctionFactoryTest {

    @Test
    @DisplayName("Creates every public lab2 function module")
    void shouldCreateEveryFunctionModule() {
        assertDoesNotThrow(() -> FunctionFactory.sin().calculate(-1.0d, Lab2Stubs.EPSILON));
        assertDoesNotThrow(() -> FunctionFactory.cos().calculate(-1.0d, Lab2Stubs.EPSILON));
        assertDoesNotThrow(() -> FunctionFactory.tan().calculate(-1.0d, Lab2Stubs.EPSILON));
        assertDoesNotThrow(() -> FunctionFactory.cot().calculate(-1.0d, Lab2Stubs.EPSILON));
        assertDoesNotThrow(() -> FunctionFactory.sec().calculate(-1.0d, Lab2Stubs.EPSILON));
        assertDoesNotThrow(() -> FunctionFactory.csc().calculate(-1.0d, Lab2Stubs.EPSILON));
        assertDoesNotThrow(() -> FunctionFactory.ln().calculate(2.0d, Lab2Stubs.EPSILON));
        assertDoesNotThrow(() -> FunctionFactory.log2().calculate(2.0d, Lab2Stubs.EPSILON));
        assertDoesNotThrow(() -> FunctionFactory.log3().calculate(2.0d, Lab2Stubs.EPSILON));
        assertDoesNotThrow(() -> FunctionFactory.log5().calculate(2.0d, Lab2Stubs.EPSILON));
        assertDoesNotThrow(() -> FunctionFactory.trigonometricBranch().calculate(-4.3d, Lab2Stubs.EPSILON));
        assertDoesNotThrow(() -> FunctionFactory.logarithmicBranch().calculate(2.0d, Lab2Stubs.EPSILON));
        assertDoesNotThrow(() -> FunctionFactory.system().calculate(2.0d, Lab2Stubs.EPSILON));
    }
}
