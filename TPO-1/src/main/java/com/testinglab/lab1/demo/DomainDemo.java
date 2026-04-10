package com.testinglab.lab1.demo;

import com.testinglab.lab1.domain.Color;
import com.testinglab.lab1.domain.Crescent;
import com.testinglab.lab1.domain.Planet;
import com.testinglab.lab1.domain.Scene;
import com.testinglab.lab1.domain.Star;

public final class DomainDemo {

    private DomainDemo() {
    }

    public static void main(String[] args) {
        Star firstStar = new Star(Color.RED, 1.0);
        Star secondStar = new Star(Color.RED, 1.0);
        Planet planet = new Planet(true, Color.BLACK);
        Crescent crescent = new Crescent(Color.RED, Color.BLACK);
        Scene scene = Scene.beginObservation()
                .revealGlow()
                .revealFirstStar(firstStar)
                .revealSecondStar(secondStar)
                .revealPlanetCrescent(planet, crescent)
                .completeObservation();

        System.out.println("Демонстрация доменной модели");
        System.out.println("----------------------------");
        System.out.println("Состояние сцены: " + scene.state());
        System.out.println("Количество звёзд в системе: " + scene.binaryStarSystem().stars().size());
        System.out.println("Ночная сторона планеты видима: " + scene.planet().nightSideVisible());
        System.out.println("Цвет ночной стороны планеты: " + scene.planet().nightSideColor());
        System.out.println("Переход полумесяца: " + scene.crescent().startColor() + " -> " + scene.crescent().endColor());
    }
}
