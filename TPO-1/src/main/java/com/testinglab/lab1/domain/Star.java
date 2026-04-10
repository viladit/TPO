package com.testinglab.lab1.domain;

import java.util.Objects;

public final class Star {

    private final Color color;
    private final double visualSize;

    public Star(Color color, double visualSize) {
        this.color = Objects.requireNonNull(color, "Цвет не должен быть null");
        if (visualSize <= 0.0d) {
            throw new IllegalArgumentException("Визуальный размер должен быть больше нуля.");
        }
        this.visualSize = visualSize;
    }

    public Color color() {
        return color;
    }

    public double visualSize() {
        return visualSize;
    }
}
