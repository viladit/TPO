package com.testinglab.lab2.export;

import com.testinglab.lab2.functions.MathFunction;

public record ExportableModule(
        String fileName,
        String title,
        MathFunction function,
        double from,
        double to,
        double step
) {
}
