package com.testinglab.lab1.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DomainModelTest {

    @Test
    @DisplayName("Сцена должна начинаться пустой")
    void shouldStartWithEmptyScene() {
        Scene scene = Scene.beginObservation();

        assertEquals(SceneState.NOTHING_VISIBLE, scene.state());
        assertTrue(scene.isEmpty());
        assertFalse(scene.glowVisible());
        assertNull(scene.firstStar());
        assertNull(scene.binaryStarSystem());
        assertNull(scene.planet());
        assertNull(scene.crescent());
    }

    @Test
    @DisplayName("После свечения сцена должна стать непустой")
    void shouldRevealGlow() {
        Scene scene = Scene.beginObservation();

        scene.revealGlow();

        assertEquals(SceneState.GLOW_APPEARED, scene.state());
        assertFalse(scene.isEmpty());
        assertTrue(scene.glowVisible());
        assertNull(scene.firstStar());
    }

    @Test
    @DisplayName("После появления первой звезды должны сохраниться её свойства")
    void shouldRevealFirstStar() {
        Scene scene = Scene.beginObservation().revealGlow();
        Star firstStar = new Star(Color.RED, 1.0);

        scene.revealFirstStar(firstStar);

        assertEquals(SceneState.FIRST_STAR_VISIBLE, scene.state());
        assertEquals(firstStar, scene.firstStar());
        assertEquals(Color.RED, scene.firstStar().color());
        assertEquals(1.0, scene.firstStar().visualSize());
        assertNull(scene.binaryStarSystem());
    }

    @Test
    @DisplayName("После второй звезды должна появиться бинарная система")
    void shouldRevealBinarySystem() {
        Star firstStar = new Star(Color.RED, 1.0);
        Star secondStar = new Star(Color.RED, 2.5);
        Scene scene = Scene.beginObservation()
                .revealGlow()
                .revealFirstStar(firstStar);

        scene.revealSecondStar(secondStar);

        assertEquals(SceneState.BINARY_SYSTEM_VISIBLE, scene.state());
        assertNotNull(scene.binaryStarSystem());
        assertEquals(List.of(firstStar, secondStar), scene.binaryStarSystem().stars());
        assertEquals(2, scene.binaryStarSystem().stars().size());
        assertNull(scene.planet());
        assertNull(scene.crescent());
    }

    @Test
    @DisplayName("После появления полумесяца должны фиксироваться свойства планеты и цвета")
    void shouldRevealPlanetCrescent() {
        Planet planet = new Planet(true, Color.BLACK);
        Crescent crescent = new Crescent(Color.RED, Color.BLACK);
        Scene scene = createBinarySystemScene();

        scene.revealPlanetCrescent(planet, crescent);

        assertEquals(SceneState.PLANET_CRESCENT_VISIBLE, scene.state());
        assertEquals(planet, scene.planet());
        assertEquals(crescent, scene.crescent());
        assertTrue(scene.planet().nightSideVisible());
        assertEquals(Color.BLACK, scene.planet().nightSideColor());
        assertEquals(Color.RED, scene.crescent().startColor());
        assertEquals(Color.BLACK, scene.crescent().endColor());
    }

    @Test
    @DisplayName("Наблюдение должно корректно завершаться после появления полумесяца")
    void shouldCompleteObservation() {
        Scene scene = createPlanetCrescentScene();

        scene.completeObservation();

        assertEquals(SceneState.COMPLETE, scene.state());
        assertFalse(scene.isEmpty());
        assertTrue(scene.glowVisible());
        assertNotNull(scene.binaryStarSystem());
        assertNotNull(scene.planet());
        assertNotNull(scene.crescent());
    }

    @Test
    @DisplayName("Сцена должна отклонять недопустимые переходы состояний")
    void shouldRejectInvalidSceneStateTransitions() {
        Scene scene = Scene.beginObservation();
        Star redStar = new Star(Color.RED, 1.0);
        Planet planet = new Planet(true, Color.BLACK);
        Crescent crescent = new Crescent(Color.RED, Color.BLACK);

        assertThrows(IllegalStateException.class, () -> scene.revealFirstStar(redStar));
        assertThrows(IllegalStateException.class, () -> scene.revealSecondStar(redStar));
        assertThrows(IllegalStateException.class, () -> scene.revealPlanetCrescent(planet, crescent));
        assertThrows(IllegalStateException.class, scene::completeObservation);

        scene.revealGlow();
        scene.revealFirstStar(redStar);
        scene.revealSecondStar(new Star(Color.RED, 2.0));
        scene.revealPlanetCrescent(planet, crescent);
        scene.completeObservation();

        assertThrows(IllegalStateException.class, scene::revealGlow);
        assertThrows(IllegalStateException.class, () -> scene.revealPlanetCrescent(planet, crescent));
    }

    @Test
    @DisplayName("Сцена должна отклонять некорректные данные на переходах")
    void shouldRejectInvalidTransitionArguments() {
        Scene glowScene = Scene.beginObservation().revealGlow();

        assertThrows(NullPointerException.class, () -> glowScene.revealFirstStar(null));
        assertThrows(IllegalArgumentException.class, () -> glowScene.revealFirstStar(new Star(Color.BLACK, 1.0)));

        Scene binaryScene = Scene.beginObservation()
                .revealGlow()
                .revealFirstStar(new Star(Color.RED, 1.0))
                .revealSecondStar(new Star(Color.RED, 1.5));

        assertThrows(NullPointerException.class, () -> binaryScene.revealPlanetCrescent(null,
                new Crescent(Color.RED, Color.BLACK)));
        assertThrows(NullPointerException.class,
                () -> binaryScene.revealPlanetCrescent(new Planet(true, Color.BLACK), null));
    }

    @Test
    @DisplayName("Звезда должна отклонять null-цвет и неположительный размер")
    void shouldRejectInvalidStarArguments() {
        assertThrows(NullPointerException.class, () -> new Star(null, 1.0));
        assertThrows(IllegalArgumentException.class, () -> new Star(Color.RED, 0.0));
    }

    @Test
    @DisplayName("Бинарная система должна отклонять некорректные наборы звёзд")
    void shouldRejectInvalidBinaryStarSystemArguments() {
        assertThrows(NullPointerException.class, () -> BinaryStarSystem.of(null));
        assertThrows(IllegalArgumentException.class,
                () -> BinaryStarSystem.of(List.of(new Star(Color.RED, 1.0))));
        assertThrows(IllegalArgumentException.class,
                () -> BinaryStarSystem.of(List.of(
                        new Star(Color.RED, 1.0),
                        new Star(Color.RED, 1.0),
                        new Star(Color.RED, 1.0)
                )));
        assertThrows(IllegalArgumentException.class,
                () -> BinaryStarSystem.of(List.of(
                        new Star(Color.RED, 1.0),
                        new Star(Color.BLACK, 1.0)
                )));
        assertThrows(IllegalArgumentException.class,
                () -> BinaryStarSystem.of(java.util.Arrays.asList(
                        new Star(Color.RED, 1.0),
                        null
                )));
    }

    @Test
    @DisplayName("Планета и полумесяц должны валидировать свои инварианты")
    void shouldRejectInvalidPlanetAndCrescentArguments() {
        assertThrows(IllegalArgumentException.class, () -> new Planet(false, Color.BLACK));
        assertThrows(NullPointerException.class, () -> new Planet(true, null));
        assertThrows(IllegalArgumentException.class, () -> new Planet(true, Color.RED));

        assertThrows(NullPointerException.class, () -> new Crescent(null, Color.BLACK));
        assertThrows(NullPointerException.class, () -> new Crescent(Color.RED, null));
        assertThrows(IllegalArgumentException.class, () -> new Crescent(Color.BLACK, Color.RED));
        assertThrows(IllegalArgumentException.class, () -> new Crescent(Color.RED, Color.RED));
    }

    private static Scene createBinarySystemScene() {
        return Scene.beginObservation()
                .revealGlow()
                .revealFirstStar(new Star(Color.RED, 1.0))
                .revealSecondStar(new Star(Color.RED, 2.5));
    }

    private static Scene createPlanetCrescentScene() {
        return createBinarySystemScene()
                .revealPlanetCrescent(new Planet(true, Color.BLACK), new Crescent(Color.RED, Color.BLACK));
    }
}
