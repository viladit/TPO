package com.testinglab.lab2.export;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.testinglab.lab2.functions.MathFunction;
import com.testinglab.lab2.stubs.Lab2Stubs;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class CsvExporterTest {

    @TempDir
    Path tempDirectory;

    @Test
    @DisplayName("Exports module values with arbitrary x step")
    void shouldExportFunctionValues() throws Exception {
        MathFunction function = (x, epsilon) -> x * x;
        Path csv = tempDirectory.resolve("square.csv");

        new CsvExporter().export(function, csv, -1.0d, 1.0d, 0.5d, Lab2Stubs.EPSILON);

        List<String> lines = Files.readAllLines(csv);
        assertEquals("X,Result", lines.get(0));
        assertEquals("-1.00000000000,1.00000000000", lines.get(1));
        assertEquals("0.00000000000,0.00000000000", lines.get(3));
        assertEquals("1.00000000000,1.00000000000", lines.get(5));
    }

    @Test
    @DisplayName("Writes NaN for undefined module values")
    void shouldWriteNanForUndefinedValues() throws Exception {
        MathFunction function = (x, epsilon) -> {
            throw new ArithmeticException("undefined");
        };
        Path csv = tempDirectory.resolve("undefined.csv");

        new CsvExporter().export(function, csv, 0.0d, 0.0d, 1.0d, Lab2Stubs.EPSILON);

        assertEquals("0.00000000000,NaN", Files.readAllLines(csv).get(1));
    }

    @Test
    @DisplayName("Rejects invalid export parameters")
    void shouldRejectInvalidParameters() {
        CsvExporter exporter = new CsvExporter();
        MathFunction function = (x, epsilon) -> x;
        Path csv = tempDirectory.resolve("bad.csv");

        assertThrows(IllegalArgumentException.class,
                () -> exporter.export(function, csv, 1.0d, 0.0d, 0.5d, Lab2Stubs.EPSILON));
        assertThrows(IllegalArgumentException.class,
                () -> exporter.export(function, csv, 0.0d, 1.0d, 0.0d, Lab2Stubs.EPSILON));
    }
}
