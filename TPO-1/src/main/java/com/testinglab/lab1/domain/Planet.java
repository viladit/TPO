package com.testinglab.lab1.domain;

import java.util.Objects;

public final class Planet {

    private final boolean nightSideVisible;
    private final Color nightSideColor;

    public Planet(boolean nightSideVisible, Color nightSideColor) {
        if (!nightSideVisible) {
            throw new IllegalArgumentException("У планеты должна быть видимая ночная сторона.");
        }
        this.nightSideColor = Objects.requireNonNull(nightSideColor, "Цвет ночной стороны не должен быть null");
        if (this.nightSideColor != Color.BLACK) {
            throw new IllegalArgumentException("Ночная сторона планеты должна быть чёрной.");
        }
        this.nightSideVisible = true;
    }

    public boolean nightSideVisible() {
        return nightSideVisible;
    }

    public Color nightSideColor() {
        return nightSideColor;
    }
}
