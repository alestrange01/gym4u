package com.example;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;
import java.util.Arrays;

import org.junit.Test;

public class TestPersonalTrainer {
    @Test
    public void testSetCorso() {
        PersonalTrainer personalTrainer = new PersonalTrainer();
        Corso corso = new Corso("zumba", "Aerobica",
                Arrays.asList("Monday", "Tuesday", "Thursday", "Friday"),
                Arrays.asList(LocalTime.of(10, 30), LocalTime.of(12, 30)),
                1.5f, 10, Arrays.asList(personalTrainer.getCodice()));

        personalTrainer.setCorso(corso);

        assertEquals(corso, personalTrainer.getCorsi().get(corso.getCodiceUnivoco()));
    }
}
