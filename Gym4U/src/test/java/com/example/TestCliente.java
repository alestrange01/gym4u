package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import org.junit.Test;

public class TestCliente {
    @Test
    public void testSetPrenotazione() {
        Cliente cliente = new Cliente();
        Prenotazione prenotazione = new Prenotazione();

        cliente.setPrenotazione(prenotazione);

        assertEquals(prenotazione, cliente.getPrenotazioni().get(prenotazione.getCodice()));
    }

    @Test
    public void testSetCorso() {
        Cliente cliente = new Cliente();
        PersonalTrainer personalTrainer = new PersonalTrainer();
        Corso corso = new Corso("zumba", "Aerobica",
                Arrays.asList("Monday", "Tuesday", "Thursday", "Friday"),
                Arrays.asList(LocalTime.of(10, 30), LocalTime.of(12, 30)),
                1.5f, 10, Arrays.asList(personalTrainer.getCodice()));

        cliente.setCorso(corso);

        assertEquals(corso, cliente.getCorsi().get(corso.getCodiceUnivoco()));
    }

    @Test
    public void testVerificaCertificatoMedico() {
        Cliente cliente = new Cliente();

        cliente.setCertificatoMedico(new CertificatoMedico(LocalDate.now().plusDays(365)));

        assertTrue(cliente.verificaCertificatoMedico());
    }

    @Test
    public void testVerificaAbbonamento() {
        Cliente cliente = new Cliente();

        AbbonamentoAnnualeFactory abbonamentoAnnualeFactory = new AbbonamentoAnnualeFactory();
        cliente.setAbbonamento(abbonamentoAnnualeFactory.creaAbbonamento());

        assertTrue(cliente.verificaAbbonamento());
    }

}
