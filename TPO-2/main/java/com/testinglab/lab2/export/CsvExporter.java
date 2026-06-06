package com.testinglab.lab2.export;

import com.testinglab.lab2.functions.MathFunction;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

public final class CsvExporter {

    public void export(MathFunction function, Path path, double from, double to, double step, double epsilon)
            throws IOException {
        validateRange(from, to, step, epsilon);
        Files.createDirectories(path.toAbsolutePath().getParent());

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer.write("X,Result");
            writer.newLine();

            int points = (int) Math.floor((to - from) / step);
            for (int i = 0; i <= points; i++) {
                double x = from + step * i;
                writer.write(format(x));
                writer.write(",");
                writer.write(formatResult(function, x, epsilon));
                writer.newLine();
            }

            double last = from + step * points;
            if (Math.abs(last - to) > step * 0.5d) {
                writer.write(format(to));
                writer.write(",");
                writer.write(formatResult(function, to, epsilon));
                writer.newLine();
            }
        }
    }

    private static void validateRange(double from, double to, double step, double epsilon) {
        if (!Double.isFinite(from) || !Double.isFinite(to)) {
            throw new IllegalArgumentException("CSV range boundaries must be finite.");
        }
        if (from > to) {
            throw new IllegalArgumentException("CSV range must satisfy from <= to.");
        }
        if (!Double.isFinite(step) || step <= 0.0d) {
            throw new IllegalArgumentException("CSV step must be positive and finite.");
        }
        if (!Double.isFinite(epsilon) || epsilon <= 0.0d) {
            throw new IllegalArgumentException("epsilon must be positive and finite.");
        }
    }

    private static String formatResult(MathFunction function, double x, double epsilon) {
        try {
            return format(function.calculate(x, epsilon));
        } catch (RuntimeException exception) {
            return "NaN";
        }
    }

    private static String format(double value) {
        if (!Double.isFinite(value)) {
            return "NaN";
        }
        return String.format(Locale.US, "%.12g", value);
    }
}
