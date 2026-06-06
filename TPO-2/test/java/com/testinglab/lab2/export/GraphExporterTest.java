package com.testinglab.lab2.export;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class GraphExporterTest {

    @TempDir
    Path tempDirectory;

    @Test
    @DisplayName("Renders a PNG graph from a CSV export")
    void shouldRenderGraphFromCsv() throws Exception {
        Path csv = tempDirectory.resolve("module.csv");
        Path png = tempDirectory.resolve("module.png");
        Files.write(csv, List.of(
                "X,Result",
                "-1.0,1.0",
                "0.0,0.0",
                "1.0,1.0",
                "2.0,NaN",
                "3.0,9.0"
        ));

        new GraphExporter().export(csv, png, "test module");

        assertTrue(Files.exists(png));
        assertTrue(Files.size(png) > 0);
    }

    @Test
    @DisplayName("Renders an empty graph when all module values are undefined")
    void shouldRenderGraphForUndefinedCsv() throws Exception {
        Path csv = tempDirectory.resolve("undefined.csv");
        Path png = tempDirectory.resolve("undefined.png");
        Files.write(csv, List.of(
                "X,Result",
                "0.0,NaN",
                "1.0,NaN"
        ));

        new GraphExporter().export(csv, png, "undefined module");

        assertTrue(Files.exists(png));
        assertTrue(Files.size(png) > 0);
    }
}
