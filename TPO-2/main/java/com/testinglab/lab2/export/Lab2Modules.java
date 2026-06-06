package com.testinglab.lab2.export;

import com.testinglab.lab2.functions.FunctionFactory;
import java.util.List;

public final class Lab2Modules {

    private Lab2Modules() {
    }

    public static List<ExportableModule> all() {
        return List.of(
                new ExportableModule("cos", "cos(x)", FunctionFactory.cos(), -6.28d, 6.28d, 0.02d),
                new ExportableModule("sin", "sin(x)", FunctionFactory.sin(), -6.28d, 6.28d, 0.02d),
                new ExportableModule("tan", "tan(x)", FunctionFactory.tan(), -6.28d, 6.28d, 0.02d),
                new ExportableModule("cot", "cot(x)", FunctionFactory.cot(), -6.28d, 6.28d, 0.02d),
                new ExportableModule("sec", "sec(x)", FunctionFactory.sec(), -6.28d, 6.28d, 0.02d),
                new ExportableModule("csc", "csc(x)", FunctionFactory.csc(), -6.28d, 6.28d, 0.02d),
                new ExportableModule("ln", "ln(x)", FunctionFactory.ln(), 0.05d, 10.0d, 0.02d),
                new ExportableModule("log2", "log_2(x)", FunctionFactory.log2(), 0.05d, 10.0d, 0.02d),
                new ExportableModule("log3", "log_3(x)", FunctionFactory.log3(), 0.05d, 10.0d, 0.02d),
                new ExportableModule("log5", "log_5(x)", FunctionFactory.log5(), 0.05d, 10.0d, 0.02d),
                new ExportableModule("trigonometric-branch", "x <= 0 branch", FunctionFactory.trigonometricBranch(), -6.28d, -0.02d, 0.01d),
                new ExportableModule("logarithmic-branch", "x > 0 branch", FunctionFactory.logarithmicBranch(), 0.05d, 10.0d, 0.02d),
                new ExportableModule("system", "piecewise system", FunctionFactory.system(), -6.28d, 10.0d, 0.02d)
        );
    }
}
