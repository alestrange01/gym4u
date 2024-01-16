package com.example;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Test;

public class TestPrenotazione {
    @Test
    public void testSetLezione() {
        Prenotazione prenotazione = new Prenotazione();
        Lezione lezione = new Lezione(LocalDate.of(2023, 12, 21), LocalTime.of(10, 30), LezioneEnum.LezioneCorso);

        prenotazione.setLezione(lezione);

        assertEquals(lezione, prenotazione.getLezione());
    }
}