package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestGym4U {

    static Gym4U gym4u;
    Corso corso;
    PersonalTrainer personalTrainer;

    @BeforeClass
    public static void init() {
        gym4u = Gym4U.getInstance();
    }

    @Before
    public void creaCorso() {
        personalTrainer = new PersonalTrainer();
        corso = new Corso("zumba", "Aerobica",
                Arrays.asList("lun", "mar", "gio", "ven"),
                Arrays.asList(LocalTime.of(10, 30), LocalTime.of(12, 30)),
                1.5f, 10, Arrays.asList(personalTrainer.getCodice()));
    }

    @Test
    public void testVerificaCliente() {
        Cliente cliente = new Cliente();

        gym4u.getClienti().put(cliente.getCodice(), cliente);

        assertTrue(gym4u.verificaCliente(cliente.getCodice()));
    }

    @Test
    public void testVisualizzaCorsi() {
        Cliente cliente = new Cliente();
        cliente.createAbbonamento();
        cliente.createCertificatoMedico();
        Map<Integer, Cliente> clienti = gym4u.getClienti();
        clienti.put(cliente.getCodice(), cliente);
        gym4u.setClienti(clienti);
        Corso corso2 = new Corso("pilates", "Funzionale",
                Arrays.asList("lun", "mar", "gio", "ven"),
                Arrays.asList(LocalTime.of(12, 30), LocalTime.of(19, 30)),
                1.5f, 10, Arrays.asList(personalTrainer.getCodice()));

        assertTrue(cliente.verificaCertificatoMedico() && cliente.verificaAbbonamento());

        Map<Integer, Corso> corsi = new LinkedHashMap<>();
        corsi.put(corso.getCodiceUnivoco(), corso);
        corsi.put(corso2.getCodiceUnivoco(), corso2);
        gym4u.setCorsi(corsi);
        List<Corso> corsiDisponibili = new ArrayList<Corso>();
        corsiDisponibili.add(corso);
        corsiDisponibili.add(corso2);

        assertEquals(Arrays.asList(corso, corso2), gym4u.getCorsiDisponibili(cliente.getCodice()));

        cliente.setCorso(corso2);
        corsiDisponibili.remove(corso2);

        assertEquals(corsiDisponibili, gym4u.getCorsiDisponibili(cliente.getCodice()));
    }

    @Test
    public void testSelezionaCorso() {
        Map<Integer, Corso> corsi = new HashMap<>();
        corsi.put(corso.getCodiceUnivoco(), corso);
        gym4u.setCorsi(corsi);
        gym4u.setCorsiDisponibili(Arrays.asList(corso));
        gym4u.selezionaCorso(corso.getCodiceUnivoco());

        assertEquals(corso, gym4u.getCorsoSelezionato());
    }

    @Test
    public void testConfermaIscrizione() {
        Cliente cliente = new Cliente();
        Map<Integer, Cliente> clienti = gym4u.getClienti();
        clienti.put(cliente.getCodice(), cliente);
        gym4u.setClienti(clienti);

        gym4u.setCorsoSelezionato(corso);
        gym4u.confermaIscrizione(cliente.getCodice());

        assertEquals(corso, cliente.getCorsi().get(corso.getCodiceUnivoco()));

        assertNull(gym4u.getCorsoSelezionato());

        assertEquals(Integer.valueOf(9), corso.getDisponibilità());
    }

    @Test
    public void testVisualizzaCorsiCliente() {
        Cliente cliente = new Cliente();
        cliente.createAbbonamento();
        cliente.createCertificatoMedico();
        Map<Integer, Cliente> clienti = gym4u.getClienti();
        clienti.put(cliente.getCodice(), cliente);
        gym4u.setClienti(clienti);

        assertTrue(cliente.verificaCertificatoMedico() && cliente.verificaAbbonamento());

        cliente.setCorso(corso);

        assertEquals(Arrays.asList(corso), new ArrayList<Corso>(cliente.getCorsi().values()));
    }

    @Test
    public void testSelezionaCorsoRestituisciLezione() {
        Cliente cliente = new Cliente();
        cliente.setCorso(corso);

        Map<Integer, Corso> corsi = new HashMap<>();
        corsi.put(corso.getCodiceUnivoco(), corso);
        gym4u.setCorsi(corsi);

        Map<Integer, Lezione> lezioni = new HashMap<>();
        lezioni = gym4u.selezionaCorsoRestituisciLezioni(corso.getCodiceUnivoco());
        assertEquals(corso.getLezioni(), lezioni);

        assertEquals(corso, gym4u.getCorsoSelezionato());
    }

    @Test
    public void testSelezionaLezione() {
        Cliente cliente = new Cliente();
        cliente.setCorso(corso);

        Set<Integer> mappaChiavi = corso.getLezioni().keySet();
        List<Integer> listaChiavi = new ArrayList<>(mappaChiavi);

        gym4u.setCorsoSelezionato(corso);

        assertEquals(corso.getLezioni().get(listaChiavi.get(0)), gym4u.selezionaLezione(listaChiavi.get(0)));
    }

    @Test
    public void testConfermaLezione() {
        Prenotazione prenotazione = new Prenotazione();

        Cliente cliente = new Cliente();

        Set<Integer> mappaChiavi = corso.getLezioni().keySet();
        List<Integer> listaChiavi = new ArrayList<>(mappaChiavi);
        Lezione lezione = corso.getLezioni().get(listaChiavi.get(0));

        gym4u.confermaLezione(prenotazione, cliente, lezione);

        assertEquals(lezione, prenotazione.getLezione());
        assertEquals(prenotazione, gym4u.getPrenotazioni().get(prenotazione.getCodice()));
        assertEquals(prenotazione, cliente.getPrenotazioni().get(prenotazione.getCodice()));
    }

     @Test
    public void testNuovoCorso() {
        gym4u.nuovoCorso("zumba", "Aerobica",
                Arrays.asList("Monday", "Tuesday", "Thursday", "Friday"),
                Arrays.asList(LocalTime.of(10, 30), LocalTime.of(12, 30)),
                1.5f, 10, new ArrayList<Integer>(gym4u.getPersonalTrainers().keySet()));
        assertNotNull(gym4u.getCorsoCorrente());
    }

    @Test
    public void testConfermaNuovoCorso() {
        gym4u.nuovoCorso("zumba", "Aerobica",
                Arrays.asList("Monday", "Tuesday", "Thursday", "Friday"),
                Arrays.asList(LocalTime.of(10, 30), LocalTime.of(12, 30)),
                1.5f, 10, new ArrayList<Integer>(gym4u.getPersonalTrainers().keySet()));
        gym4u.confermaNuovoCorso();
        assertNull(gym4u.getCorsoCorrente());
    }

    @Test
    public void testAssociaPersonalTrainer() {
        personalTrainer.setCorso(corso);

        assertEquals(corso, personalTrainer.getCorsi().get(corso.getCodiceUnivoco()));
    }
}
