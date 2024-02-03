package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

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

    // UC1
    @Before
    public void creaCorso() {
        personalTrainer = new PersonalTrainer("Luca", "Verdi");
        corso = new Corso("zumba", "Aerobica",
                Arrays.asList("Monday", "Tuesday", "Thursday", "Friday"),
                Arrays.asList(LocalTime.of(10, 30), LocalTime.of(12, 30)),
                1.5f, 10, Arrays.asList(personalTrainer.getCodice()));
        personalTrainer.setCorso(corso);
        for (Lezione l : corso.getLezioni().values()) {
            personalTrainer.setLezione(l);
        }
    }

    @Test
    public void testVerificaCliente() {
        Cliente cliente = new Cliente("Mario", "Rossi", LocalDate.of(1990, 1, 1), "Via Roma 1", "mariorossi@gmail.com",
                "3331234567");

        gym4u.getClienti().put(cliente.getCodice(), cliente);

        assertEquals(Integer.valueOf(1), gym4u.verificaUtente(cliente.getCodice(), "0"));
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

    // UC2
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
                    break;
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

    // UC3
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

    // UC4
    @Test
    public void testNuovoCliente() {
        gym4u.nuovoCliente("Mario", "Rossi", LocalDate.of(1990, 1, 1), "Via Roma 1", "mariorossi@gmail.com",
                "3331234567");
        assertNotNull(gym4u.getClienteCorrente());
    }

    @Test
    public void testConfermaNuovoCliente() {
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

    // UC5
    @Test
    public void testModificaAbbonamento() {
        gym4u.nuovoCliente("Mario", "Rossi", LocalDate.of(1990, 1, 1), "Via Roma 1", "mariorossi@gmail.com",
                "3331234567");
        gym4u.associaCertificatoMedico(LocalDate.now().plusDays(365));
        gym4u.associaAbbonamento(1);
        gym4u.associaMetodoDiPagamento(123456789, LocalDate.now().plusDays(365));
        Integer codiceCliente = gym4u.getClienteCorrente().getCodice();
        gym4u.confermaCliente();

        gym4u.modificaAbbonamento(1, gym4u.getClienti().get(codiceCliente));
        Abbonamento abbonamentoCorrente = gym4u.getClienti().get(codiceCliente).getAbbonamentoCorrente();
        assertNotNull(abbonamentoCorrente);
        assertEquals(abbonamentoCorrente, gym4u.getClienti().get(codiceCliente).getAbbonamentoCorrente());
    }

    @Test
    public void testConfermaModificaAbbonamento() {
        gym4u.nuovoCliente("Mario", "Rossi", LocalDate.of(1990, 1, 1), "Via Roma 1", "mariorossi@gmail.com",
                "3331234567");
        gym4u.associaCertificatoMedico(LocalDate.now().plusDays(365));
        gym4u.associaAbbonamento(1);
        gym4u.associaMetodoDiPagamento(123456789, LocalDate.now().plusDays(365));
        Integer codiceCliente = gym4u.getClienteCorrente().getCodice();
        gym4u.confermaCliente();
        gym4u.modificaAbbonamento(1, gym4u.getClienti().get(codiceCliente));
        Abbonamento a = gym4u.getClienti().get(codiceCliente).getAbbonamentoCorrente();

        gym4u.confermaModificaAbbonamento(gym4u.getClienti().get(codiceCliente));
        assertEquals(a, gym4u.getClienti().get(codiceCliente).getAbbonamento());
        assertNull(gym4u.getClienti().get(codiceCliente).getAbbonamentoCorrente());
    }

    @Test
    public void testModificaMetodoDiPagamento() {
        gym4u.nuovoCliente("Mario", "Rossi", LocalDate.of(1990, 1, 1), "Via Roma 1", "mariorossi@gmail.com",
                "3331234567");
        gym4u.associaCertificatoMedico(LocalDate.now().plusDays(365));
        gym4u.associaAbbonamento(1);
        gym4u.associaMetodoDiPagamento(123456789, LocalDate.now().plusDays(365));
        Integer codiceCliente = gym4u.getClienteCorrente().getCodice();
        gym4u.confermaCliente();

        gym4u.modificaMetodoDiPagamento(987654321, LocalDate.now().plusDays(365),
                gym4u.getClienti().get(codiceCliente));
        MetodoDiPagamento metodoDiPagamentoCorrente = gym4u.getClienti().get(codiceCliente)
                .getMedotoDiPagamentoCorrente();
        assertNotNull(metodoDiPagamentoCorrente);
        assertEquals(metodoDiPagamentoCorrente, gym4u.getClienti().get(codiceCliente).getMedotoDiPagamentoCorrente());
    }

    @Test
    public void testConfermaModificaMetodoDiPagamento() {
        gym4u.nuovoCliente("Mario", "Rossi", LocalDate.of(1990, 1, 1), "Via Roma 1", "mariorossi@gmail.com",
                "3331234567");
        gym4u.associaCertificatoMedico(LocalDate.now().plusDays(365));
        gym4u.associaAbbonamento(1);
        gym4u.associaMetodoDiPagamento(123456789, LocalDate.now().plusDays(365));
        Integer codiceCliente = gym4u.getClienteCorrente().getCodice();
        gym4u.confermaCliente();
        gym4u.modificaMetodoDiPagamento(987654321, LocalDate.now().plusDays(365),
                gym4u.getClienti().get(codiceCliente));
        MetodoDiPagamento m = gym4u.getClienti().get(codiceCliente).getMedotoDiPagamentoCorrente();

        gym4u.confermaModificaMetodoDiPagamento(gym4u.getClienti().get(codiceCliente));
        assertEquals(m, gym4u.getClienti().get(codiceCliente).getMetodoDiPagamento());
        assertNull(gym4u.getClienti().get(codiceCliente).getMedotoDiPagamentoCorrente());
    }

    // UC6
    @Test
    public void testVisualizzaPersonalTrainers() {
        Cliente cliente = new Cliente("Mario", "Rossi", LocalDate.of(1990, 1, 1), "Via Roma 1", "mariorossi@gmail.com",
                "3331234567");
        AbbonamentoAnnualeFactory abbonamentoAnnualeFactory = new AbbonamentoAnnualeFactory();
        cliente.setAbbonamento(abbonamentoAnnualeFactory.creaAbbonamento());
        cliente.setCertificatoMedico(new CertificatoMedico(LocalDate.now().plusDays(365)));
        assertEquals(gym4u.getPersonalTrainers(), gym4u.visualizzaPersonalTrainers(cliente));
    }

    @Test
    public void testSelezionaPersonalTrainer() {
        Map<Integer, PersonalTrainer> personalTrainers = new HashMap<>();
        personalTrainers.put(personalTrainer.getCodice(), personalTrainer);
        gym4u.setPersonalTrainers(personalTrainers);
        gym4u.selezionaPersonalTrainer(String.valueOf(personalTrainer.getCodice()), "Sunday", LocalTime.of(11, 30), 1f);
        assertEquals(personalTrainer, gym4u.getPersonalTrainerSelezionato());
        Lezione lezioneCorrente = gym4u.getPersonalTrainerSelezionato().getLezioneCorrente();
        DayOfWeek giornoDaAggiungere = DayOfWeek.valueOf("Sunday".toUpperCase());
        int giorniDiDifferenza = (giornoDaAggiungere.getValue() - LocalDate.now().getDayOfWeek().getValue() + 7) % 7;
        LocalDate dataLezione = LocalDate.now().plusDays(giorniDiDifferenza);
        assertEquals(lezioneCorrente.getGiorno(), dataLezione);
        assertEquals(lezioneCorrente.getOrario(), LocalTime.of(11, 30));
        assertEquals(lezioneCorrente.getDurata(), 1f, 0);
    }

    @Test
    public void testIsPersonalTrainerDisponibile() {
        DayOfWeek giornoDaAggiungere = DayOfWeek.valueOf("SUNDAY".toUpperCase());
        int giorniDiDifferenza = (giornoDaAggiungere.getValue() - LocalDate.now().getDayOfWeek().getValue() + 7) % 7;
        LocalDate dataLezione = LocalDate.now().plusDays(giorniDiDifferenza);
        gym4u.setPersonalTrainerSelezionato(personalTrainer);

        assertTrue(gym4u.isPersonalTrainerDisponibile(dataLezione, LocalTime.of(11, 30), 1f));
    }

    @Test
    public void testIsPersonalTrainerDisponibile2() {
        DayOfWeek giornoDaAggiungere = DayOfWeek.valueOf("Monday".toUpperCase());
        int giorniDiDifferenza = (giornoDaAggiungere.getValue() - LocalDate.now().getDayOfWeek().getValue() + 7) % 7;
        LocalDate dataLezione = LocalDate.now().plusDays(giorniDiDifferenza);
        gym4u.setPersonalTrainerSelezionato(personalTrainer);

        assertFalse(gym4u.isPersonalTrainerDisponibile(dataLezione, LocalTime.of(10, 30), 1f));
    }

    @Test
    public void testConfermaPrenotazione() {
        Cliente cliente = new Cliente("Mario", "Rossi", LocalDate.of(1990, 1, 1), "Via Roma 1", "mariorossi@gmail.com",
                "3331234567");
        Map<Integer, Cliente> clienti = new HashMap<>();
        clienti.put(cliente.getCodice(), cliente);
        gym4u.setClienti(clienti);
        gym4u.setClienteCorrente(cliente);

        Map<Integer, PersonalTrainer> personalTrainers = new HashMap<>();
        personalTrainers.put(personalTrainer.getCodice(), personalTrainer);
        gym4u.setPersonalTrainers(personalTrainers);
        gym4u.setPersonalTrainerSelezionato(personalTrainer);

        DayOfWeek giornoDaAggiungere = DayOfWeek.valueOf("Monday".toUpperCase());
        int giorniDiDifferenza = (giornoDaAggiungere.getValue() - LocalDate.now().getDayOfWeek().getValue() + 7) % 7;
        LocalDate dataLezione = LocalDate.now().plusDays(giorniDiDifferenza);
        Lezione lezione = new Lezione(dataLezione, LocalTime.of(10, 30), 1f, LezioneEnum.LezionePT);
        gym4u.getPersonalTrainerSelezionato().setLezioneCorrente(lezione);

        gym4u.confermaPrenotazione();

        assertEquals(personalTrainer.getLezioni().get(lezione.getCodice()),
                gym4u.getPersonalTrainers().get(personalTrainer.getCodice()).getLezioni().get(lezione.getCodice()));
    }

    @Test
    public void testIsBadgeValido() {
        Cliente cliente = new Cliente("Mario", "Rossi", LocalDate.of(1990, 1, 1), "Via Roma 1", "mariorossi@gmail.com",
                "3331234567");
        Map<Integer, Cliente> clienti = new HashMap<>();
        clienti.put(cliente.getCodice(), cliente);
        gym4u.setClienti(clienti);

        assertTrue(gym4u.isBadgeValido(cliente.getBadge().getCodice()));
    }

    // UC7
    @Test
    public void testInserisciOfferta() {
        Offerta offerta = new Offerta(20f, LocalDate.now(), LocalDate.now().plusMonths(1));
        gym4u.inserisciOfferta(20f, LocalDate.now(), LocalDate.now().plusMonths(1));

        assertEquals(offerta.getSconto(), gym4u.getOffertaCorrente().getSconto());
        assertEquals(offerta.getDataInizio(), gym4u.getOffertaCorrente().getDataInizio());
        assertEquals(offerta.getDataFine(), gym4u.getOffertaCorrente().getDataFine());
    }

    @Test
    public void testConfermaOfferta() {
        Offerta offerta = new Offerta(20f, LocalDate.now(), LocalDate.now().plusMonths(1));
        Map<Integer, Offerta> offerte = new HashMap<>();
        offerte.put(offerta.getCodice(), offerta);
        gym4u.setOffertaCorrente(offerta);
        gym4u.confermaOfferta();

        assertEquals(offerte, gym4u.getOfferte());
    }

    // UC8
    @Test
    public void testInserisciSchedaPersonalizzata() {
        Map<Integer, PersonalTrainer> personalTrainers = new HashMap<>();
        personalTrainers.put(personalTrainer.getCodice(), personalTrainer);
        gym4u.setPersonalTrainers(personalTrainers);
        gym4u.setPersonalTrainerSelezionato(personalTrainer);

        SchedaPersonalizzata schedaPersonalizzata = new SchedaPersonalizzata(
                Arrays.asList("Panca piana 4x5", "Squat 2x6"), LocalDate.now().plusMonths(1));
        gym4u.inserisciSchedaPersonalizzata(Arrays.asList("Panca piana 4x5", "Squat 2x6"),
                LocalDate.now().plusMonths(1));

        assertEquals(schedaPersonalizzata.getEsercizi(),
                gym4u.getPersonalTrainerSelezionato().getSchedaPersonalizzataCorrente().getEsercizi());
        assertEquals(schedaPersonalizzata.getDataInizio(),
                gym4u.getPersonalTrainerSelezionato().getSchedaPersonalizzataCorrente().getDataInizio());
        assertEquals(schedaPersonalizzata.getDataFine(),
                gym4u.getPersonalTrainerSelezionato().getSchedaPersonalizzataCorrente().getDataFine());
    }

    @Test
    public void testSelezionaCliente() {
        Cliente cliente = new Cliente("Mario", "Rossi", LocalDate.of(1990, 1, 1), "Via Roma 1", "mariorossi@gmail.com",
                "3331234567");
        Map<Integer, Cliente> clienti = new HashMap<>();
        clienti.put(cliente.getCodice(), cliente);
        gym4u.setClienti(clienti);

        assertEquals(cliente, gym4u.selezionaCliente(cliente.getCodice()));
        assertEquals(cliente, gym4u.getClienteCorrente());
    }

    @Test
    public void testConfermaSchedaPersonalizzata() {
        Cliente cliente = new Cliente("Mario", "Rossi", LocalDate.of(1990, 1, 1), "Via Roma 1", "mariorossi@gmail.com",
                "3331234567");
        gym4u.setClienteCorrente(cliente);
        SchedaPersonalizzata schedaPersonalizzata = new SchedaPersonalizzata(
                Arrays.asList("Panca piana 4x5", "Squat 2x6"), LocalDate.now().plusMonths(1));
        gym4u.setPersonalTrainerSelezionato(personalTrainer);
        gym4u.getPersonalTrainerSelezionato().setSchedaPersonalizzataCorrente(schedaPersonalizzata);
        Map<Integer, PersonalTrainer> personalTrainers = new HashMap<>();
        personalTrainers.put(personalTrainer.getCodice(), personalTrainer);
        gym4u.setPersonalTrainers(personalTrainers);

        gym4u.confermaSchedaPersonalizzata();
        assertEquals(schedaPersonalizzata,
                gym4u.getPersonalTrainers().get(personalTrainer.getCodice()).getSchedePersonalizzate()
                        .get(schedaPersonalizzata.getCodice()));
    }

    // UC9
    @Test
    public void testVisualizzaLezioniPT() {
        gym4u.setPersonalTrainerSelezionato(personalTrainer);
        List<Lezione> lezioni = new ArrayList<>(personalTrainer.getLezioni().entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.comparing(Lezione::getGiorno)))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList()));
        assertEquals(lezioni, gym4u.visualizzaLezioniPT());
    }

    @Test
    public void testSelezionaLezionePT() {
        Lezione lezionePT = new Lezione(LocalDate.now(), LocalTime.now(), 1f, LezioneEnum.LezionePT);
        personalTrainer.setLezione(lezionePT);
        gym4u.setPersonalTrainerSelezionato(personalTrainer);

        gym4u.selezionaLezionePT(lezionePT.getCodice());

        assertEquals(lezionePT, gym4u.getPersonalTrainerSelezionato().getLezioneCorrente());
    }

    @Test
    public void testvisualizzaPrenotati() {
        Cliente cliente = new Cliente("Mario", "Rossi", LocalDate.of(1990, 1, 1), "Via Roma 1", "mariorossi@gmail.com",
                "3331234567");
        Lezione lezionePT = new Lezione(LocalDate.now(), LocalTime.now(), 1f, LezioneEnum.LezionePT);
        gym4u.setPersonalTrainerSelezionato(personalTrainer);
        gym4u.getPersonalTrainerSelezionato().setLezioneCorrente(lezionePT);
        Prenotazione p = new Prenotazione();
        p.setLezione(lezionePT);
        cliente.setPrenotazione(p);

        Cliente cliente2 = new Cliente("Marco", "Verdi", LocalDate.of(1990, 1, 1), "Via Roma 1", "marcoverdi@gmail.com",
                "3331234127");

        Map<Integer, Cliente> clienti = new HashMap<>();
        clienti.put(cliente.getCodice(), cliente);
        clienti.put(cliente2.getCodice(), cliente2);
        gym4u.setClienti(clienti);

        assertEquals(Arrays.asList(cliente), gym4u.visualizzaPrenotati());
    }

    @Test
    public void testVisualizzaPrenotati2() {
        Cliente cliente = new Cliente("Mario", "Rossi", LocalDate.of(1990, 1, 1), "Via Roma 1", "mariorossi@gmail.com",
                "3331234567");
        Lezione lezionePT = new Lezione(LocalDate.now(), LocalTime.now(), 1f, LezioneEnum.LezionePT);
        gym4u.setPersonalTrainerSelezionato(personalTrainer);
        gym4u.getPersonalTrainerSelezionato().setLezioneCorrente(lezionePT);
        Prenotazione p = new Prenotazione();
        p.setLezione(lezionePT);
        cliente.setPrenotazione(p);

        Cliente cliente2 = new Cliente("Marco", "Verdi", LocalDate.of(1990, 1, 1), "Via Roma 1", "marcoverdi@gmail.com",
                "3331234127");

        Map<Integer, Cliente> clienti = new HashMap<>();
        clienti.put(cliente.getCodice(), cliente);
        clienti.put(cliente2.getCodice(), cliente2);
        gym4u.setClienti(clienti);

        assertNotEquals(Arrays.asList(cliente, cliente2), gym4u.visualizzaPrenotati());
    }

    // UC10
    @Test
    public void testIsBadgeValido2() {
        Cliente cliente = new Cliente("Mario", "Rossi", LocalDate.of(1990, 1, 1), "Via Roma 1", "mariorossi@gmail.com",
                "3331234567");
        Map<Integer, Cliente> clienti = new HashMap<>();
        clienti.put(cliente.getCodice(), cliente);
        gym4u.setClienti(clienti);

        assertFalse(gym4u.isBadgeValido(new Random().nextInt()));
    }

    @Test
    public void testIsPrenotazioneValida() {
        Cliente cliente = new Cliente("Mario", "Rossi", LocalDate.of(1990, 1, 1), "Via Roma 1", "mariorossi@gmail.com",
                "3331234567");
        Prenotazione p = new Prenotazione();
        Lezione lezione = new Lezione(LocalDate.now(), LocalTime.now().plusMinutes(1), 1f, LezioneEnum.LezionePT);
        p.setLezione(lezione);

        cliente.setPrenotazione(p);
        gym4u.setClienteCorrente(cliente);

        assertTrue(gym4u.isPrenotazioneValida());
        assertEquals(p, gym4u.getPrenotazioneCorrente());
    }

    @Test
    public void testIsPrenotazioneValida2() {
        Cliente cliente = new Cliente("Mario", "Rossi", LocalDate.of(1990, 1, 1), "Via Roma 1", "mariorossi@gmail.com",
                "3331234567");
        Prenotazione p = new Prenotazione();
        Lezione lezione = new Lezione(LocalDate.now(), LocalTime.now().plusMinutes(50), 1f, LezioneEnum.LezionePT);
        p.setLezione(lezione);

        cliente.setPrenotazione(p);
        gym4u.setClienteCorrente(cliente);

        assertFalse(gym4u.isPrenotazioneValida());
    }

    @Test
    public void testConfermaPresenza() {
        Prenotazione p = new Prenotazione();
        Lezione lezione = new Lezione(LocalDate.now(), LocalTime.now(), 1f, LezioneEnum.LezionePT);
        p.setLezione(lezione);

        gym4u.setPrenotazioneCorrente(p);

        gym4u.confermaPresenza();
        assertTrue(p.getValidata());
    }

}
