package com.testinglab.lab1.domain;

import java.util.List;
import java.util.Objects;

public final class BinaryStarSystem {

    private final List<Star> stars;

    private BinaryStarSystem(List<Star> stars) {
        this.stars = List.copyOf(stars);
    }

    public static BinaryStarSystem of(List<Star> stars) {
        Objects.requireNonNull(stars, "Список звёзд не должен быть null");
        if (stars.size() != 2) {
            throw new IllegalArgumentException("Бинарная звёздная система должна содержать ровно две звезды.");
        }
        if (stars.stream().anyMatch(star -> star == null || star.color() != Color.RED)) {
            throw new IllegalArgumentException("Обе звезды в бинарной системе должны быть красными.");
        }
        return new BinaryStarSystem(stars);
    }

    public List<Star> stars() {
        return stars;
    }
}
