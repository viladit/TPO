package com.testinglab.lab2.demo;

import com.testinglab.lab2.export.CsvExporter;
import com.testinglab.lab2.export.ExportableModule;
import com.testinglab.lab2.export.GraphExporter;
import com.testinglab.lab2.export.Lab2Modules;
import java.nio.file.Path;

public final class Lab2ExportDemo {

    private static final double EPSILON = 1e-10;

    private Lab2ExportDemo() {
    }

    public static void main(String[] args) throws Exception {
        Path outputDirectory = args.length == 0 ? Path.of("lab2-output") : Path.of(args[0]);
        CsvExporter csvExporter = new CsvExporter();
        GraphExporter graphExporter = new GraphExporter();

        for (ExportableModule module : Lab2Modules.all()) {
            Path csv = outputDirectory.resolve(module.fileName() + ".csv");
            Path png = outputDirectory.resolve(module.fileName() + ".png");
            csvExporter.export(module.function(), csv, module.from(), module.to(), module.step(), EPSILON);
            graphExporter.export(csv, png, module.title());
        }

        System.out.println("Lab2 exports are written to " + outputDirectory.toAbsolutePath());
    }
}
