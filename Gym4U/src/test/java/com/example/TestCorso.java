package com.example;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;
import java.util.Arrays;

import org.junit.Test;

public class TestCorso {
    @Test
    public void testDiminuisciDisponibilità() {
        PersonalTrainer personalTrainer = new PersonalTrainer();
        Corso corso = new Corso("zumba", "Aerobica",
                Arrays.asList("Monday", "Tuesday", "Thursday", "Friday"),
                Arrays.asList(LocalTime.of(10, 30), LocalTime.of(12, 30)),
                1.5f, 1, Arrays.asList(personalTrainer.getCodice()));

        corso.diminuisciDisponibilità();

        assertEquals(Integer.valueOf(0), corso.getDisponibilità());

        corso.diminuisciDisponibilità();
        
        assertEquals(Integer.valueOf(0), corso.getDisponibilità());
    }
}
