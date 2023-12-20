package com.example;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;

import org.junit.Test;

public class TestPrenotazione {
    @Test
    public void testSetLezione() {
        Prenotazione prenotazione = new Prenotazione();
        Lezione lezione = new Lezione("martedi", LocalTime.of(10, 30));

        prenotazione.setLezione(lezione);

        assertEquals(lezione, prenotazione.getLezione());
    }
}