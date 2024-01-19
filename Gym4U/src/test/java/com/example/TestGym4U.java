package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
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
                Arrays.asList("Monday", "Tuesday", "Thursday", "Friday"),
                Arrays.asList(LocalTime.of(10, 30), LocalTime.of(12, 30)),
                1.5f, 10, Arrays.asList(personalTrainer.getCodice()));
    }

    @Test
    public void testVerificaCliente() {
        Cliente cliente = new Cliente("Mario", "Rossi", LocalDate.of(1990, 1, 1), "Via Roma 1", "mariorossi@gmail.com",
                "3331234567");

        gym4u.getClienti().put(cliente.getCodice(), cliente);

        assertTrue(gym4u.verificaCliente(cliente.getCodice()));
    }

    @Test
    public void testVisualizzaCorsi() {
        Cliente cliente = new Cliente("Mario", "Rossi", LocalDate.of(1990, 1, 1), "Via Roma 1", "mariorossi@gmail.com",
                "3331234567");
        AbbonamentoAnnualeFactory abbonamentoAnnualeFactory = new AbbonamentoAnnualeFactory();
        cliente.setAbbonamento(abbonamentoAnnualeFactory.creaAbbonamento());
        cliente.setCertificatoMedico(new CertificatoMedico(LocalDate.now().plusDays(365)));
        Map<Integer, Cliente> clienti = gym4u.getClienti();
        clienti.put(cliente.getCodice(), cliente);
        gym4u.setClienti(clienti);
        Corso corso2 = new Corso("pilates", "Funzionale",
                Arrays.asList("Monday", "Tuesday", "Thursday", "Friday"),
                Arrays.asList(LocalTime.of(12, 30), LocalTime.of(19, 30)),
                1.5f, 10, Arrays.asList(personalTrainer.getCodice()));

        assertTrue(cliente.verificaCertificatoMedico() && cliente.verificaAbbonamento());

        Map<Integer, Corso> corsi = new LinkedHashMap<>(); // Dici cosi? Mi è andato circa 10 volte di fila senza mai
                                                           // errore
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
        Cliente cliente = new Cliente("Mario", "Rossi", LocalDate.of(1990, 1, 1), "Via Roma 1", "mariorossi@gmail.com",
                "3331234567");
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
        Cliente cliente = new Cliente("Mario", "Rossi", LocalDate.of(1990, 1, 1), "Via Roma 1", "mariorossi@gmail.com",
                "3331234567");
        AbbonamentoAnnualeFactory abbonamentoAnnualeFactory = new AbbonamentoAnnualeFactory();
        cliente.setAbbonamento(abbonamentoAnnualeFactory.creaAbbonamento());
        cliente.setCertificatoMedico(new CertificatoMedico(LocalDate.now().plusDays(365)));
        Map<Integer, Cliente> clienti = gym4u.getClienti();
        clienti.put(cliente.getCodice(), cliente);
        gym4u.setClienti(clienti);

        assertTrue(cliente.verificaCertificatoMedico() && cliente.verificaAbbonamento());

        cliente.setCorso(corso);

        assertEquals(Arrays.asList(corso), new ArrayList<Corso>(cliente.getCorsi().values()));
    }

    @Test
    public void testSelezionaCorsoRestituisciLezione() {
        Cliente cliente = new Cliente("Mario", "Rossi", LocalDate.of(1990, 1, 1), "Via Roma 1", "mariorossi@gmail.com",
                "3331234567");
        cliente.setCorso(corso);

        Map<Integer, Corso> corsi = new HashMap<>(); // Dava null perchè non trovava nessun corso settato su gym4U
        corsi.put(corso.getCodiceUnivoco(), corso);
        gym4u.setCorsi(corsi);

        Map<Integer, Lezione> lezioni = new HashMap<>();
        lezioni = gym4u.selezionaCorsoRestituisciLezioni(corso.getCodiceUnivoco());
        assertEquals(corso.getLezioni(), lezioni);

        assertEquals(corso, gym4u.getCorsoSelezionato());
    }

    @Test
    public void testSelezionaLezione() {
        Cliente cliente = new Cliente("Mario", "Rossi", LocalDate.of(1990, 1, 1), "Via Roma 1", "mariorossi@gmail.com",
                "3331234567");
        cliente.setCorso(corso);

        Set<Integer> mappaChiavi = corso.getLezioni().keySet();
        List<Integer> listaChiavi = new ArrayList<>(mappaChiavi);

        gym4u.setCorsoSelezionato(corso);

        assertEquals(corso.getLezioni().get(listaChiavi.get(0)), gym4u.selezionaLezione(listaChiavi.get(0)));
    }

    @Test
    public void testPrenotazionePossibile() {
        Cliente cliente = new Cliente("Mario", "Rossi", LocalDate.of(1990, 1, 1), "Via Roma 1", "mariorossi@gmail.com",
                "3331234567");
        cliente.setCorso(corso);

        List<Lezione> lezioniStessoGiorno = new ArrayList<>();
        for (Lezione lezione1 : corso.getLezioni().values()) {
            for (Lezione lezione2 : corso.getLezioni().values()) {
                if (!lezione1.equals(lezione2) && lezione1.getGiorno().equals(lezione2.getGiorno())) {
                    lezioniStessoGiorno.add(lezione1);
                    lezioniStessoGiorno.add(lezione2);
                    break; // Esci dopo aver trovato la prima coppia
                }
            }
        }
        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setLezione(lezioniStessoGiorno.get(0));
        cliente.setPrenotazione(prenotazione);
        assertFalse(gym4u.prenotazionePossibile(lezioniStessoGiorno.get(1), cliente));
    }

    @Test
    public void testConfermaLezione() {
        Prenotazione prenotazione = new Prenotazione();

        Cliente cliente = new Cliente("Mario", "Rossi", LocalDate.of(1990, 1, 1), "Via Roma 1", "mariorossi@gmail.com",
                "3331234567");

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

    @Test
    public void testNuovoCliente(){
        gym4u.nuovoCliente("Mario", "Rossi", LocalDate.of(1990, 1, 1), "Via Roma 1", "mariorossi@gmail.com",
                        "3331234567");
        assertNotNull(gym4u.getClienteCorrente());
    }

    @Test
    public void testConfermaNuovoCliente(){
        gym4u.nuovoCliente("Mario", "Rossi", LocalDate.of(1990, 1, 1), "Via Roma 1", "mariorossi@gmail.com",
                        "3331234567");    
        gym4u.associaCertificatoMedico(LocalDate.now().plusDays(365));
        gym4u.associaAbbonamento(1);
        gym4u.associaMetodoDiPagamento(123456789, LocalDate.now().plusDays(365));
        Integer codiceCliente = gym4u.getClienteCorrente().getCodice();
        Badge badge = gym4u.confermaCliente();

        assertNull(gym4u.getClienteCorrente());
        assertNotNull(badge);
        assertEquals(badge, gym4u.getClienti().get(codiceCliente).getBadge());
        assertTrue(gym4u.getClienti().get(codiceCliente).verificaCertificatoMedico());
        assertTrue(gym4u.getClienti().get(codiceCliente).verificaAbbonamento());
        assertTrue(gym4u.getClienti().get(codiceCliente).getMetodoDiPagamento().verificaMetodoDiPagamento());
    }


    @Test 
    public void testModificaAbbonamento(){
        gym4u.nuovoCliente("Mario", "Rossi", LocalDate.of(1990, 1, 1), "Via Roma 1", "mariorossi@gmail.com",
                        "3331234567");    
        gym4u.associaCertificatoMedico(LocalDate.now().plusDays(365));
        gym4u.associaAbbonamento(1);
        gym4u.associaMetodoDiPagamento(123456789, LocalDate.now().plusDays(365));
        Integer codiceCliente = gym4u.getClienteCorrente().getCodice();
        gym4u.confermaCliente();

        gym4u.modificaAbbonamento(1, gym4u.getClienti().get(codiceCliente));
        Abbonamento abbonamentoCorrente = gym4u.getAbbonamentoCorrente();
        assertNotNull(abbonamentoCorrente);
        assertEquals(abbonamentoCorrente, gym4u.getAbbonamentoCorrente());
    }

    @Test
    public void testConfermaModificaAbbonamento(){
        gym4u.nuovoCliente("Mario", "Rossi", LocalDate.of(1990, 1, 1), "Via Roma 1", "mariorossi@gmail.com",
                        "3331234567");    
        gym4u.associaCertificatoMedico(LocalDate.now().plusDays(365));
        gym4u.associaAbbonamento(1);
        gym4u.associaMetodoDiPagamento(123456789, LocalDate.now().plusDays(365));
        Integer codiceCliente = gym4u.getClienteCorrente().getCodice();
        gym4u.confermaCliente();
        gym4u.modificaAbbonamento(1, gym4u.getClienti().get(codiceCliente));
        Abbonamento a = gym4u.getAbbonamentoCorrente();

        gym4u.confermaModificaAbbonamento(gym4u.getClienti().get(codiceCliente));
        assertEquals(a, gym4u.getClienti().get(codiceCliente).getAbbonamento());
        assertNull(gym4u.getAbbonamentoCorrente());
    }

    @Test
    public void testModificaMetodoDiPagamento(){
        gym4u.nuovoCliente("Mario", "Rossi", LocalDate.of(1990, 1, 1), "Via Roma 1", "mariorossi@gmail.com",
                        "3331234567");    
        gym4u.associaCertificatoMedico(LocalDate.now().plusDays(365));
        gym4u.associaAbbonamento(1);
        gym4u.associaMetodoDiPagamento(123456789, LocalDate.now().plusDays(365));
        Integer codiceCliente = gym4u.getClienteCorrente().getCodice();
        gym4u.confermaCliente();

        gym4u.modificaMetodoDiPagamento(987654321, LocalDate.now().plusDays(365), gym4u.getClienti().get(codiceCliente));
        MetodoDiPagamento metodoDiPagamentoCorrente = gym4u.getMetodoDiPagamentoCorrente();
        assertNotNull(metodoDiPagamentoCorrente);
        assertEquals(metodoDiPagamentoCorrente, gym4u.getMetodoDiPagamentoCorrente());
    }

    @Test
    public void testConfermaModificaMetodoDiPagamento(){
        gym4u.nuovoCliente("Mario", "Rossi", LocalDate.of(1990, 1, 1), "Via Roma 1", "mariorossi@gmail.com",
                        "3331234567");    
        gym4u.associaCertificatoMedico(LocalDate.now().plusDays(365));
        gym4u.associaAbbonamento(1);
        gym4u.associaMetodoDiPagamento(123456789, LocalDate.now().plusDays(365));
        Integer codiceCliente = gym4u.getClienteCorrente().getCodice();
        gym4u.confermaCliente();
        gym4u.modificaMetodoDiPagamento(987654321, LocalDate.now().plusDays(365), gym4u.getClienti().get(codiceCliente));
        MetodoDiPagamento m = gym4u.getMetodoDiPagamentoCorrente();

        gym4u.confermaModificaMetodoDiPagamento(gym4u.getClienti().get(codiceCliente));
        assertEquals(m, gym4u.getClienti().get(codiceCliente).getMetodoDiPagamento());
        assertNull(gym4u.getMetodoDiPagamentoCorrente());
    }

}