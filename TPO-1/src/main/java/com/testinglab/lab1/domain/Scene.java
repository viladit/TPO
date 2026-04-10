package com.testinglab.lab1.domain;

import java.util.List;
import java.util.Objects;

public final class Scene {

    private SceneState state;
    private Star firstStar;
    private BinaryStarSystem binaryStarSystem;
    private Planet planet;
    private Crescent crescent;

    private Scene(SceneState state) {
        this.state = Objects.requireNonNull(state, "Состояние сцены не должно быть null");
    }

    public static Scene beginObservation() {
        return new Scene(SceneState.NOTHING_VISIBLE);
    }

    public SceneState state() {
        return state;
    }

    public boolean isEmpty() {
        return state == SceneState.NOTHING_VISIBLE;
    }

    public boolean glowVisible() {
        return state != SceneState.NOTHING_VISIBLE;
    }

    public Star firstStar() {
        return firstStar;
    }

    public BinaryStarSystem binaryStarSystem() {
        return binaryStarSystem;
    }

    public Planet planet() {
        return planet;
    }

    public Crescent crescent() {
        return crescent;
    }

    public Scene revealGlow() {
        requireState(SceneState.NOTHING_VISIBLE, "Свечение может появиться только в начале наблюдения.");
        state = SceneState.GLOW_APPEARED;
        return this;
    }

    public Scene revealFirstStar(Star star) {
        requireState(SceneState.GLOW_APPEARED, "Первая звезда может появиться только после свечения.");
        firstStar = requireRedStar(star, "Первая звезда должна быть красной.");
        state = SceneState.FIRST_STAR_VISIBLE;
        return this;
    }

    public Scene revealSecondStar(Star star) {
        requireState(SceneState.FIRST_STAR_VISIBLE,
                "Вторая звезда может появиться только после первой звезды.");
        Star secondStar = requireRedStar(star, "Вторая звезда должна быть красной.");
        binaryStarSystem = BinaryStarSystem.of(List.of(firstStar, secondStar));
        state = SceneState.BINARY_SYSTEM_VISIBLE;
        return this;
    }

    public Scene revealPlanetCrescent(Planet planet, Crescent crescent) {
        requireState(SceneState.BINARY_SYSTEM_VISIBLE,
                "Полумесяц планеты может появиться только после формирования бинарной системы.");
        this.planet = Objects.requireNonNull(planet, "Планета не должна быть null");
        this.crescent = Objects.requireNonNull(crescent, "Полумесяц не должен быть null");
        state = SceneState.PLANET_CRESCENT_VISIBLE;
        return this;
    }

    public Scene completeObservation() {
        requireState(SceneState.PLANET_CRESCENT_VISIBLE,
                "Наблюдение можно завершить только после появления полумесяца планеты.");
        state = SceneState.COMPLETE;
        return this;
    }

    private void requireState(SceneState expectedState, String message) {
        if (state != expectedState) {
            throw new IllegalStateException(message + " Текущее состояние: " + state);
        }
    }

    private static Star requireRedStar(Star star, String message) {
        Star validatedStar = Objects.requireNonNull(star, "Звезда не должна быть null");
        if (validatedStar.color() != Color.RED) {
            throw new IllegalArgumentException(message);
        }
        return validatedStar;
    }
}
