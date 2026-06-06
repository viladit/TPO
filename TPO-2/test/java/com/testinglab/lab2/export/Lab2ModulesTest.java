package com.testinglab.lab2.export;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Lab2ModulesTest {

    @Test
    @DisplayName("Provides export descriptors for every lab2 module")
    void shouldProvideAllExportableModules() {
        List<ExportableModule> modules = Lab2Modules.all();

        assertEquals(13, modules.size());
        assertTrue(modules.stream().anyMatch(module -> module.fileName().equals("sin")));
        assertTrue(modules.stream().anyMatch(module -> module.fileName().equals("cos")));
        assertTrue(modules.stream().anyMatch(module -> module.fileName().equals("ln")));
        assertTrue(modules.stream().anyMatch(module -> module.fileName().equals("log2")));
        assertTrue(modules.stream().anyMatch(module -> module.fileName().equals("log5")));
        assertTrue(modules.stream().anyMatch(module -> module.fileName().equals("system")));
        assertTrue(modules.stream().allMatch(module -> module.step() > 0.0d));
    }
}
