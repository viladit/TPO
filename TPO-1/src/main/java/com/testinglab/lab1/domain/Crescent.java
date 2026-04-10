package com.testinglab.lab1.domain;

import java.util.Objects;

public final class Crescent {

    private final Color startColor;
    private final Color endColor;

    public Crescent(Color startColor, Color endColor) {
        this.startColor = Objects.requireNonNull(startColor, "Начальный цвет не должен быть null");
        this.endColor = Objects.requireNonNull(endColor, "Конечный цвет не должен быть null");

        if (this.startColor != Color.RED || this.endColor != Color.BLACK) {
            throw new IllegalArgumentException("Полумесяц должен переходить от красного к чёрному.");
        }
    }

    public Color startColor() {
        return startColor;
    }

    public Color endColor() {
        return endColor;
    }
}
