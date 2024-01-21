package com.example;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

//Singleton GoF
public class Gym4U {

    private static Gym4U instance;
    private Map<Integer, PersonalTrainer> personalTrainers;
    private Map<Integer, Cliente> clienti;
    private Corso corsoCorrente;
    private Corso corsoSelezionato;
    private List<Corso> corsiDisponibili;
    private Map<Integer, Corso> corsi;
    private Prenotazione prenotazioneCorrente;
    private Map<Integer, Prenotazione> prenotazioni;
    private Lezione lezioneCorrente;
    private Cliente clienteCorrente;
    private PersonalTrainer personalTrainerSelezionato;
    private Abbonamento abbonamentoCorrente;
    private MetodoDiPagamento metodoDiPagamentoCorrente;
    private SchedaPersonalizzata schedaPersonalizzataCorrente;
    private Offerta offertaCorrente;
    private Map<Integer, Offerta> offerte;

    private Gym4U() {
        this.clienti = new HashMap<Integer, Cliente>();
        this.personalTrainers = new HashMap<Integer, PersonalTrainer>();
        this.corsoCorrente = null;
        this.corsoSelezionato = null;
        this.corsiDisponibili = null;
        this.corsi = new HashMap<Integer, Corso>();
        this.prenotazioni = new HashMap<Integer, Prenotazione>();
        this.prenotazioneCorrente = null;
        this.clienteCorrente = null;
        this.personalTrainerSelezionato = null;
        this.abbonamentoCorrente = null;
        this.metodoDiPagamentoCorrente = null;
        this.schedaPersonalizzataCorrente = null;
        this.offertaCorrente = null;
        this.offerte = new HashMap<Integer, Offerta>();
        loadData();
    }

    public static Gym4U getInstance() {
        if (instance == null) {
            instance = new Gym4U();
        }
        return instance;
    }

    public Map<Integer, PersonalTrainer> getPersonalTrainers() {
        return personalTrainers;
    }

    public Map<Integer, Cliente> getClienti() {
        return clienti;
    }

    public Cliente getClienteCorrente() {
        return clienteCorrente;
    }

    public Abbonamento getAbbonamentoCorrente() {
        return abbonamentoCorrente;
    }

    public MetodoDiPagamento getMetodoDiPagamentoCorrente() {
        return metodoDiPagamentoCorrente;
    }

    public Corso getCorsoCorrente() {
        return corsoCorrente;
    }

    public Corso getCorsoSelezionato() {
        return corsoSelezionato;
    }

    public Map<Integer, Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }

    public Lezione getLezioneCorrente() {
        return this.lezioneCorrente;
    }

    public void setClienti(Map<Integer, Cliente> clienti) {
        this.clienti = clienti;
    }

    public void setCorsoSelezionato(Corso corsoSelezionato) {
        this.corsoSelezionato = corsoSelezionato;
    }

    public void setCorsiDisponibili(List<Corso> corsiDisponibili) {
        this.corsiDisponibili = corsiDisponibili;
    }

    public void setPersonalTrainerSelezionato(PersonalTrainer personalTrainer) {
        this.personalTrainerSelezionato = personalTrainer;
    }

    public PersonalTrainer getPersonalTrainerSelezionato() {
        return this.personalTrainerSelezionato;
    }

    public void setPersonalTrainers(Map<Integer, PersonalTrainer> personalTrainers) {
        this.personalTrainers = personalTrainers;
    }

    public void setLezioneCorrente(Lezione lezione) {
        this.lezioneCorrente = lezione;
    }

    public void setCorsi(Map<Integer, Corso> corsi) {
        this.corsi = corsi;
    }

    public Prenotazione getPrenotazioneCorrente() {
        return this.prenotazioneCorrente;
    }

    public void setPrenotazioneCorrente(Prenotazione prenotazione) {
        this.prenotazioneCorrente = prenotazione;
    }

    public void setClienteCorrente(Cliente cliente) {
        this.clienteCorrente = cliente;
    }

    public Offerta getOffertaCorrente() {
        return this.offertaCorrente;
    }

    public void setOffertaCorrente(Offerta offerta) {
        this.offertaCorrente = offerta;
    }

    public Map<Integer, Offerta> getOfferte() {
        return this.offerte;
    }

    public SchedaPersonalizzata getSchedaPersonalizzataCorrente() {
        return this.schedaPersonalizzataCorrente;
    }

    public void setSchedaPersonalizzataCorrente(SchedaPersonalizzata schedaPersonalizzata) {
        this.schedaPersonalizzataCorrente = schedaPersonalizzata;
    }

    // public Map<Integer, Offerta> getOfferte(){
    // return this.offerte;
    // }

    public void loadData() {
        // Avviamento
        AbbonamentoAnnualeFactory abbonamentoAnnualeFactory = new AbbonamentoAnnualeFactory();
        Cliente cliente = new Cliente("Mario", "Rossi", LocalDate.of(1990, 1, 1), "Via Roma 1",
                "mariorossi@gmail.com", "3394309876");
        System.out.println("Cliente: " + cliente.getCodice());
        System.out.println("Badge: " + cliente.getBadge().getCodice());
        cliente.setAbbonamento(abbonamentoAnnualeFactory.creaAbbonamento());
        cliente.setCertificatoMedico(new CertificatoMedico(LocalDate.now().plusDays(365)));
        cliente.associaMetodoDiPagamento(1234567890, LocalDate.of(2022, 1, 1));
        clienti.put(cliente.getCodice(), cliente);

        PersonalTrainer personalTrainer = new PersonalTrainer();
        System.out.println("Personal Trainer: " + personalTrainer.getCodice());
        personalTrainers.put(personalTrainer.getCodice(), personalTrainer);

        nuovoCorso("zumba", "Aerobica",
                Arrays.asList("Monday", "Tuesday", "Thursday", "Friday"),
                Arrays.asList(LocalTime.of(10, 30), LocalTime.of(12, 30)),
                1.5f, 10, new ArrayList<>(getPersonalTrainers().keySet()));
        confermaNuovoCorso();

        nuovoCorso("pilates", "Funzionale",
                Arrays.asList("Tuesday", "Thursday", "Friday"),
                Arrays.asList(LocalTime.of(12, 30), LocalTime.of(19, 30)),
                1.5f, 10, new ArrayList<>(getPersonalTrainers().keySet()));
        confermaNuovoCorso();

        nuovoCorso("crossfit", "Funzionale",
                Arrays.asList("Monday", "Tuesday", "Thursday"),
                Arrays.asList(LocalTime.of(10, 30), LocalTime.of(18, 30)),
                1.5f, 10, new ArrayList<>(getPersonalTrainers().keySet()));
        confermaNuovoCorso();
    }

    private void pulisciCorrentiESelezionati() {
        this.corsoCorrente = null;
        this.corsoSelezionato = null;
        this.corsiDisponibili = null;
        this.prenotazioneCorrente = null;
        this.lezioneCorrente = null;
        this.clienteCorrente = null;
        this.personalTrainerSelezionato = null;
        this.abbonamentoCorrente = null;
        this.metodoDiPagamentoCorrente = null;
        this.schedaPersonalizzataCorrente = null;
        this.offertaCorrente = null;
    }

    public boolean verificaCliente(Integer codiceCliente) {
        Cliente cliente = clienti.get(codiceCliente);
        if (cliente != null) {
            return true;
        }
        return false;
    }

    public boolean verificaAmministratore(Integer codice) {
        if (codice == 0) {
            return true;
        }
        return false;
    }

    public boolean isIngressoInPalestra(Integer codice) {
        if (codice == 1) {
            return true;
        }
        return false;
    }

    public boolean isPersonalTrainer(Integer codice) {
        PersonalTrainer personalTrainer = personalTrainers.get(codice);
        if (personalTrainer != null) {
            return true;
        }
        return false;
    }

    // UC1
    public void iscrizioneCorso(Integer codiceCliente) {

        List<Corso> corsi = visualizzaCorsi(codiceCliente);
        System.out.println("Corsi disponibili: ");
        for (Corso corso : corsi) {
            System.out.println(corso.toString());
        }
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.print("Inserisci il codice del corso: ");
            String inputCorso = scanner.next();
            try {
                Integer codiceCorso = Integer.parseInt(inputCorso);
                selezionaCorso(codiceCorso);
            } catch (NumberFormatException e) {
                System.out.println("Input non valido. Inserisci un numero.");
            }
        } while (this.corsoSelezionato == null);

        System.out.println("Corso selezionato: ");
        System.out.println(this.corsoSelezionato.toString());
        boolean number = false;
        do {
            System.out.print("Seleziona 1 per confermare, 0 per annullare: ");
            Integer codiceCorso = scanner.nextInt();
            try {
                switch (codiceCorso) {
                    case 0:
                        return;
                    case 1:
                        number = true;
                        confermaIscrizione(codiceCliente);
                        break;
                    default:
                        System.out.println("Inserisci un numero tra 0 e 1.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Input non valido. Inserisci un numero.");
            }
        } while (!number);
        pulisciCorrentiESelezionati();
        System.out.println("Iscrizione al corso effettuata con successo.");
    }

    public List<Corso> visualizzaCorsi(Integer codiceCliente) {
        Cliente cliente = clienti.get(codiceCliente);
        if (!cliente.verificaCertificatoMedico() || !cliente.verificaAbbonamento()) {
            throw new RuntimeException("Certificato medico o/e abbonamento del cliente non valido.");
        }
        this.corsiDisponibili = getCorsiDisponibili(codiceCliente);
        return this.corsiDisponibili;
    }

    public List<Corso> getCorsiDisponibili(Integer codiceCliente) {
        List<Corso> corsiDisponibili = new ArrayList<Corso>();
        Cliente cliente = clienti.get(codiceCliente);
        for (Map.Entry<Integer, Corso> entry : this.corsi.entrySet()) {
            Corso corso = entry.getValue();
            if (corso.getDisponibilità() > 0 && !cliente.getCorsi().containsKey(corso.getCodiceUnivoco()))
                corsiDisponibili.add(corso);
        }
        return corsiDisponibili;
    }

    public Corso selezionaCorso(Integer codiceUnivoco) {
        for (Corso corso : corsiDisponibili) {
            if (corso.getCodiceUnivoco().equals(codiceUnivoco)) {
                this.corsoSelezionato = corso;
                return corso;
            }
        }
        return null;
    }

    public void confermaIscrizione(Integer codiceCliente) {
        Cliente cliente = clienti.get(codiceCliente);
        cliente.setCorso(corsoSelezionato);
        corsoSelezionato.diminuisciDisponibilità();
        corsoSelezionato = null;
    }

    // UC2
    public void prenotazioneLezioneCorso(Integer codiceCliente) {
        Cliente cliente = clienti.get(codiceCliente);
        visualizzaCorsiCliente(cliente);
        Scanner scanner = new Scanner(System.in);

        Map<Integer, Lezione> lezioni = null;
        do {

            System.out.print("Inserisci il codice del corso: ");
            String inputCorso = scanner.next();
            try {
                Integer codiceCorso = Integer.parseInt(inputCorso);
                lezioni = selezionaCorsoRestituisciLezioni(codiceCorso);
            } catch (NumberFormatException e) {
                System.out.println("Input non valido. Inserisci un numero.");
            }
        } while (this.corsoSelezionato == null);

        // rimuovo lezioni prima di oggi e ordino cronologicamente
        lezioni.entrySet().removeIf(entry -> entry.getValue().getGiorno().isBefore(LocalDate.now()));
        lezioni = lezioni.entrySet().stream()
                .sorted(Map.Entry.comparingByValue((l1, l2) -> l1.getGiorno().compareTo(l2.getGiorno())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        System.out.println("Lezioni disponibili: ");
        if (cliente.getPrenotazioni().isEmpty()) {
            for (Map.Entry<Integer, Lezione> entry : lezioni.entrySet()) {
                System.out.println(entry.getValue().toString());
                System.out.println("-----------------------------");
            }
        } else {
            List<Integer> codiciLezioniPrenotate = new ArrayList<>();
            for (Map.Entry<Integer, Prenotazione> entry : cliente.getPrenotazioni().entrySet()) {
                codiciLezioniPrenotate.add(entry.getValue().getLezione().getCodice());
            }

            for (Map.Entry<Integer, Lezione> entryLezione : lezioni.entrySet()) {
                if (!codiciLezioniPrenotate.contains(entryLezione.getKey())) {
                    System.out.println(entryLezione.getValue().toString());
                    System.out.println("-----------------------------");
                }
            }
        }

        Lezione lezioneSelezionata = null;
        boolean prenotazionePossibile = false;
        do {
            System.out.print("Inserisci il codice della lezione: ");
            String inputLezione = scanner.next();
            try {
                Integer codiceLezione = Integer.parseInt(inputLezione);
                lezioneSelezionata = selezionaLezione(codiceLezione);
                if (prenotazionePossibile = prenotazionePossibile(lezioneSelezionata, cliente)) {
                    creaPrenotazione();
                }
            } catch (NumberFormatException e) {
                System.out.println("Input non valido. Inserisci un numero.");
            }
        } while (lezioneSelezionata == null || !prenotazionePossibile);

        System.out.println("Lezione selezionata: ");
        System.out.println(lezioneSelezionata.toString());

        System.out.print("Seleziona 1 per confermare, 0 per annullare: ");
        Integer conferma = scanner.nextInt();
        try {
            switch (conferma) {
                case 0:
                    scanner.close();
                    return;
                case 1:
                    confermaLezione(this.prenotazioneCorrente, cliente, lezioneSelezionata);
                    break;
                default:
                    System.out.println("Inserisci un numero tra 0 e 1.");
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("Input non valido. Inserisci un numero.");
        }

        System.out.println("Prenotazione alla lezione effettuata con successo.");
        pulisciCorrentiESelezionati();
    }

    public void visualizzaCorsiCliente(Cliente cliente) {
        if (!cliente.verificaCertificatoMedico() || !cliente.verificaAbbonamento()) {
            throw new RuntimeException("Certificato medico o/e abbonamento del cliente non valido.");
        }
        System.out.println("Corsi disponibili: ");
        for (Map.Entry<Integer, Corso> entry : cliente.getCorsi().entrySet()) {
            System.out.println(entry.getValue().toString());
        }
        this.corsiDisponibili = new ArrayList<>(cliente.getCorsi().values());
    }

    public Map<Integer, Lezione> selezionaCorsoRestituisciLezioni(Integer codiceUnivoco) {
        Corso corso = this.corsi.get(codiceUnivoco);
        this.corsoSelezionato = corso;
        corso.loadLezioni();
        return corso.getLezioni();
    }

    public Lezione selezionaLezione(Integer codiceLezione) {
        return this.corsoSelezionato.getLezioni().get(codiceLezione);
    }

    public boolean prenotazionePossibile(Lezione lezione, Cliente cliente) {
        // controllo che il cliente non abbia una lezione prenotata lo stesso giorno
        for (Map.Entry<Integer, Prenotazione> entry : cliente.getPrenotazioni().entrySet()) {
            if (entry.getValue().getLezione().getGiorno().equals(lezione.getGiorno())) {
                System.out.println("Hai già prenotato una lezione per questo giorno.");
                return false;
            }
        }
        return true;
    }

    public void creaPrenotazione() {
        this.prenotazioneCorrente = new Prenotazione();
    }

    public void confermaLezione(Prenotazione prenotazione, Cliente cliente, Lezione lezione) {
        prenotazione.setLezione(lezione);
        prenotazioni.put(prenotazione.getCodice(), prenotazione);
        cliente.setPrenotazione(prenotazione);
    }

    // UC3
    public void creaNuovoCorso() {
        infoNuovoCorso();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Riepilogo informazioni inserite: ");
        System.out.println(corsoCorrente.getTotalInfo());
        System.out.println("Personal Trainer selezionati: ");
        for (Integer codicePersonalTrainer : corsoCorrente.getIdsPersonalTrainer()) {
            PersonalTrainer personalTrainer = personalTrainers.get(codicePersonalTrainer);
            System.out.println(personalTrainer.toString());
        }

        System.out.print("Seleziona 1 per confermare, 0 per annullare: ");
        Integer conferma = scanner.nextInt();
        try {
            switch (conferma) {
                case 0:
                    System.out.println("Creazione nuovo corso annullata.");
                    return;
                case 1:
                    confermaNuovoCorso();
                    break;
                default:
                    System.out.println("Inserisci un numero tra 0 e 1.");
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("Input non valido. Inserisci un numero.");
        }

        System.out.println("Corso creato con successo.");
        pulisciCorrentiESelezionati();
    }

    public void infoNuovoCorso() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Inserisci il nome del corso: ");
        String nome = scanner.nextLine();

        System.out.print("Inserisci la descrizione del corso: ");
        String descrizione = scanner.nextLine();

        System.out.print("Inserisci i giorni (Monday->Sunday) disponibili del corso separati da una virgola: ");
        List<String> giorniDisponibili = Arrays.asList(scanner.nextLine().split(","));

        System.out.print("Inserisci gli orari (HH:mm) disponibili del corso separati da una virgola: ");
        String[] orariInput = scanner.nextLine().split(",");
        List<LocalTime> orariDisponibili = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm"); // Formato degli orari (ad esempio, "13:30")
        for (String orario : orariInput) {
            LocalTime time = LocalTime.parse(orario.trim(), formatter);
            orariDisponibili.add(time);
        }

        System.out.print("Inserisci la durata delle lezioni del corso: ");
        float durataLezione = scanner.nextFloat();

        System.out.print("Inserisci il numero di posti disponibili del corso: ");
        Integer postiDisponibili = scanner.nextInt();

        System.out.print("Associa uno o più Personal Trainer: ");
        List<Integer> codiciPersonalTrainer = Arrays.stream(scanner.next().split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        nuovoCorso(nome, descrizione, giorniDisponibili, orariDisponibili, durataLezione, postiDisponibili,
                codiciPersonalTrainer);

    }

    public void nuovoCorso(String nome, String descrizione, List<String> giorniDisponibili,
            List<LocalTime> orariDisponibili, float durataLezione, Integer postiDisponibili,
            List<Integer> codiciPersonalTrainer) {
        Corso corso = new Corso(nome, descrizione, giorniDisponibili, orariDisponibili,
                durataLezione, postiDisponibili, codiciPersonalTrainer);
        this.corsoCorrente = corso;
    }

    public void confermaNuovoCorso() {
        associaPersonalTrainer(corsoCorrente.getIdsPersonalTrainer());
        corsi.put(corsoCorrente.getCodiceUnivoco(), corsoCorrente);
        corsoCorrente = null;
    }

    public void associaPersonalTrainer(List<Integer> codiciPersonalTrainer) {
        for (Integer codicePersonalTrainer : codiciPersonalTrainer) {
            PersonalTrainer personalTrainer = personalTrainers.get(codicePersonalTrainer);
            personalTrainer.setCorso(corsoCorrente);
            for (Lezione l : corsoCorrente.getLezioni().values()) {
                personalTrainer.setLezione(l);
            }
        }
    }

    // UC4
    public void registrazioneNuovoCliente() {
        if (!infoNuovoCliente())
            return;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Riepilogo informazioni inserite: ");
        System.out.print(this.clienteCorrente.toString());

        // certificato medico
        LocalDate dataScadenzaCertificatoMedico = null;
        do {
            try {
                System.out.print("Inserisci la data di scadenza del Certificato Medico (yyyy-MM-dd): ");
                String dataScadenzaCertificatoMedicoInput = scanner.nextLine();
                dataScadenzaCertificatoMedico = LocalDate.parse(dataScadenzaCertificatoMedicoInput);
            } catch (DateTimeParseException e) {
                System.out.println("Data non valida. Inserisci una data valida.");
            }
        } while (dataScadenzaCertificatoMedico == null);
        if (LocalDate.now().isAfter(dataScadenzaCertificatoMedico)) {
            System.out.println("Il certificato medico è scaduto.");
            System.out.println("Registrazione nuovo cliente annullata.");
            return;
        }
        associaCertificatoMedico(dataScadenzaCertificatoMedico);

        // abbonamento
        Integer tipologiaAbbonamento = null;
        do {
            System.out.print("Inserire la tipologia di abbonamento:\n" +
                    "1. Abbonamento mensile\n" +
                    "2. Abbonamento semestrale\n" +
                    "3. Abbonamento annuale\n" +
                    "Inserisci il numero corrispondente: ");
            String tipologiaAbbonamentoInput = scanner.nextLine();
            tipologiaAbbonamento = Integer.parseInt(tipologiaAbbonamentoInput);
        } while (tipologiaAbbonamento != 1 && tipologiaAbbonamento != 2 && tipologiaAbbonamento != 3);
        associaAbbonamento(tipologiaAbbonamento);

        // metodo di pagamento
        LocalDate dataScadenzaCarta = null;
        Integer numeroCarta = null;
        do {
            try {
                System.out.println("Inserisci il metodo di pagamento:");
                System.out.print("Numero carta: ");
                String numeroCartaInput = scanner.nextLine();
                numeroCarta = Integer.parseInt(numeroCartaInput);
                System.out.print("Data scadenza carta (yyyy-MM-dd): ");
                String dataScadenzaCartaInput = scanner.nextLine();
                dataScadenzaCarta = LocalDate.parse(dataScadenzaCartaInput);
                if (LocalDate.now().isAfter(dataScadenzaCarta)) {
                    System.out.println("La carta è scaduta.");
                    dataScadenzaCarta = null;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Data non valida. Inserisci una data valida.");
            }
        } while (dataScadenzaCarta == null);
        associaMetodoDiPagamento(numeroCarta, dataScadenzaCarta);

        // riepilogo informazioni inserite
        System.out.println("Riepilogo informazioni inserite: ");
        System.out.println(clienteCorrente.toString());
        System.out.println(clienteCorrente.getCertificatoMedico().toString());
        System.out.println(clienteCorrente.getAbbonamento().toString());
        System.out.println(clienteCorrente.getMetodoDiPagamento().toString());

        System.out.print("Seleziona 1 per confermare, 0 per annullare: ");
        Integer conferma = scanner.nextInt();
        Badge badge = null;
        try {
            switch (conferma) {
                case 0:
                    System.out.println("Registrazione nuovo cliente annullata.");
                    return;
                case 1:
                    badge = confermaCliente();
                    break;
                default:
                    System.out.println("Inserisci un numero tra 0 e 1.");
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("Input non valido. Inserisci un numero.");
        }

        System.out.println("Registrazione nuovo cliente effettuata con successo.");
        System.out.println("Badge associato al cliente: " + badge.getCodice());
        pulisciCorrentiESelezionati();
    }

    public boolean infoNuovoCliente() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Inserisci il nome del cliente: ");
        String nome = scanner.nextLine();

        System.out.print("Inserisci il cognome del cliente: ");
        String cognome = scanner.nextLine();

        LocalDate dataNascita = null;
        do {
            try {
                System.out.print("Inserisci la data di nascita del cliente (yyyy-MM-dd): ");
                String dataNascitaInput = scanner.nextLine();
                dataNascita = LocalDate.parse(dataNascitaInput);
            } catch (DateTimeParseException e) {
                System.out.println("Data non valida. Inserisci una data valida.");
            }
        } while (dataNascita == null);
        if (LocalDate.now().minusYears(18).isBefore(dataNascita)) {
            System.out.println("Il cliente deve essere maggiorenne.");
            System.out.println("Registrazione nuovo cliente annullata.");
            // finisce l'esecuzione del programma
            return false;
        }

        System.out.print("Inserisci l'indirizzo del cliente: ");
        String indirizzo = scanner.nextLine();

        System.out.print("Inserisci email del cliente: ");
        String email = scanner.nextLine();
        while (!email.contains("@")) {
            System.out.print("Inserisci un'email valida: ");
            email = scanner.nextLine();
        }

        System.out.print("Inserisci il numero di telefono del cliente: ");
        String numeroTelefono = scanner.nextLine();
        while (numeroTelefono.length() != 10) {
            System.out.print("Inserisci un numero di telefono valido: ");
            numeroTelefono = scanner.nextLine();
        }

        nuovoCliente(nome, cognome, dataNascita, indirizzo, email, numeroTelefono);
        return true;
    }

    public void nuovoCliente(String nome, String cognome, LocalDate dataNascita, String indirizzo, String email,
            String numeroTelefono) {
        Cliente cliente = new Cliente(nome, cognome, dataNascita, indirizzo, email, numeroTelefono);
        this.clienteCorrente = cliente;
    }

    public void associaCertificatoMedico(LocalDate dataScadenzaCertificatoMedico) {
        this.clienteCorrente.associaCertificatoMedico(dataScadenzaCertificatoMedico);
    }

    public void associaAbbonamento(Integer tipologiaAbbonamento) {
        this.clienteCorrente.associaAbbonamento(tipologiaAbbonamento);
    }

    public void associaMetodoDiPagamento(Integer numeroCarta, LocalDate dataScadenzaCarta) {
        this.clienteCorrente.associaMetodoDiPagamento(numeroCarta, dataScadenzaCarta);
    }

    public Badge confermaCliente() {
        this.clienteCorrente.creaBadge();
        this.clienti.put(this.clienteCorrente.getCodice(), this.clienteCorrente);
        Badge badge = this.clienteCorrente.getBadge();
        this.clienteCorrente = null;
        return badge;
    }

    // UC5
    public void gestioneAbbonamento(Integer codiceCliente) {
        Cliente cliente = clienti.get(codiceCliente);

        System.out.println("Abbonamento attuale: ");
        System.out.println(cliente.getAbbonamento().toString());
        System.out.println("Metodo di pagamento attuale: ");
        System.out.println(cliente.getMetodoDiPagamento().toString());

        Scanner scanner = new Scanner(System.in);
        System.out.print("Inserisci il numero dell'opzione desiderata:\n" +
                "1. Modifica abbonamento\n" +
                "2. Modifica metodo di pagamento\n" +
                "0. Esci\n" +
                "Inserisci il numero corrispondente: ");
        Integer scelta = Integer.parseInt(scanner.nextLine());
        try {
            switch (scelta) {
                case 0:
                    System.out.println("Gestione abbonamento annullata.");
                    return;
                case 1:
                    Integer tipologiaAbbonamento = null;
                    do {
                        System.out.print("Inserire la tipologia di abbonamento desiderata:\n" +
                                "1. Abbonamento mensile\n" +
                                "2. Abbonamento semestrale\n" +
                                "3. Abbonamento annuale\n" +
                                "Inserisci il numero corrispondente: ");
                        String tipologiaAbbonamentoInput = scanner.nextLine();
                        tipologiaAbbonamento = Integer.parseInt(tipologiaAbbonamentoInput);
                    } while (tipologiaAbbonamento != 1 && tipologiaAbbonamento != 2 && tipologiaAbbonamento != 3);
                    modificaAbbonamento(tipologiaAbbonamento, cliente);
                    System.out.println("Abbonamento selezionato: ");
                    System.out.println(this.abbonamentoCorrente.toString());

                    System.out.print("Seleziona 1 per confermare, 0 per annullare: ");
                    Integer conferma = scanner.nextInt();
                    try {
                        switch (conferma) {
                            case 0:
                                System.out.println("Modifica abbonamento annullata.");
                                return;
                            case 1:
                                confermaModificaAbbonamento(cliente);
                                System.out.println("Modifiche avvenute con successo.");
                                break;
                            default:
                                System.out.println("Inserisci un numero tra 0 e 1.");
                                break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Input non valido. Inserisci un numero.");
                    }

                    break;
                case 2:
                    LocalDate dataScadenzaCarta = null;
                    Integer numeroCarta = null;
                    do {
                        try {
                            System.out.println("Inserisci il metodo di pagamento:");
                            System.out.print("Numero carta: ");
                            String numeroCartaInput = scanner.nextLine();
                            numeroCarta = Integer.parseInt(numeroCartaInput);
                            System.out.print("Data scadenza carta (yyyy-MM-dd): ");
                            String dataScadenzaCartaInput = scanner.nextLine();
                            dataScadenzaCarta = LocalDate.parse(dataScadenzaCartaInput);
                            if (LocalDate.now().isAfter(dataScadenzaCarta)) {
                                System.out.println("La carta è scaduta.");
                                dataScadenzaCarta = null;
                            }
                        } catch (DateTimeParseException e) {
                            System.out.println("Data non valida. Inserisci una data valida.");
                        }
                    } while (dataScadenzaCarta == null);
                    modificaMetodoDiPagamento(numeroCarta, dataScadenzaCarta, cliente);
                    System.out.println("Metodo di pagamento inserito: ");
                    System.out.println(this.metodoDiPagamentoCorrente.toString());

                    System.out.print("Seleziona 1 per confermare, 0 per annullare: ");
                    conferma = scanner.nextInt();
                    try {
                        switch (conferma) {
                            case 0:
                                System.out.println("Modifica metodo di pagamento annullata.");
                                return;
                            case 1:
                                confermaModificaMetodoDiPagamento(cliente);
                                System.out.println("Modifiche avvenute con successo.");
                                break;
                            default:
                                System.out.println("Inserisci un numero tra 0 e 1.");
                                break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Input non valido. Inserisci un numero.");
                    }

                    break;
                default:
                    System.out.println("Inserisci un numero tra 0 e 3.");
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("Input non valido. Inserisci un numero.");
        }
        pulisciCorrentiESelezionati();
    }

    public void modificaAbbonamento(Integer tipologiaAbbonamento, Cliente cliente) {
        this.abbonamentoCorrente = cliente.modificaAbbonamento(tipologiaAbbonamento);
    }

    public void confermaModificaAbbonamento(Cliente cliente) {
        cliente.setAbbonamento(this.abbonamentoCorrente);
        this.abbonamentoCorrente = null;
    }

    public void modificaMetodoDiPagamento(Integer numeroCarta, LocalDate dataScadenzaCarta, Cliente cliente) {
        this.metodoDiPagamentoCorrente = cliente.modificaMetodoDiPagamento(numeroCarta, dataScadenzaCarta);
    }

    public void confermaModificaMetodoDiPagamento(Cliente cliente) {
        cliente.setMetodoDiPagamento(this.metodoDiPagamentoCorrente);
        this.metodoDiPagamentoCorrente = null;
    }

    // UC6
    public void prenotazioneLezionePT(Integer codiceCliente) {
        Cliente cliente = clienti.get(codiceCliente);
        this.clienteCorrente = cliente;

        Map<Integer, PersonalTrainer> personalTrainers = visualizzaPersonalTrainers(cliente);
        System.out.println("Personal trainer disponibili: ");
        for (Map.Entry<Integer, PersonalTrainer> entry : personalTrainers.entrySet()) {
            System.out.println(entry.getValue().toString());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Inserisci il codice del personal trainer: ");
        String codicePersonalTrainer = scanner.nextLine();

        System.out.print("Inserisci il giorno della lezione (Monday->Sunday): ");
        String giorno = scanner.nextLine();

        System.out.print("Inserisci l'orario della lezione(HH:mm): ");
        String ora = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time = LocalTime.parse(ora.trim(), formatter);

        selezionaPersonalTrainer(codicePersonalTrainer, giorno, time, 1f);

        System.out.print("Seleziona 1 per confermare, 0 per annullare: ");
        Integer conferma = scanner.nextInt();
        try {
            switch (conferma) {
                case 0:
                    System.out.println("Prenotazione alla lezionePT annullata.");
                    return;
                case 1:
                    confermaPrenotazione();
                    break;
                default:
                    System.out.println("Inserisci un numero tra 0 e 1.");
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("Input non valido. Inserisci un numero.");
        }

        System.out.println("Prenotazione alla lezionePT effettuata con successo.");
        pulisciCorrentiESelezionati();
    }

    public Map<Integer, PersonalTrainer> visualizzaPersonalTrainers(Cliente cliente) {
        if (!cliente.verificaCertificatoMedico() || !cliente.verificaAbbonamento()) {
            throw new RuntimeException(
                    "Impossibile effettuare la prenotazione, cliente con certificato medico/abbonamento non valido.");
        }
        return this.personalTrainers;
    }

    public void selezionaPersonalTrainer(String codicePersonalTrainer, String giorno, LocalTime orarioLezione,
            Float durataLezione) {
        this.personalTrainerSelezionato = personalTrainers.get(Integer.valueOf(codicePersonalTrainer));

        DayOfWeek giornoDaAggiungere = DayOfWeek.valueOf(giorno.toUpperCase());
        int giorniDiDifferenza = (giornoDaAggiungere.getValue() - LocalDate.now().getDayOfWeek().getValue() + 7) % 7;
        LocalDate dataLezione = LocalDate.now().plusDays(giorniDiDifferenza);

        Boolean personalTrainerDisponibile = isPersonalTrainerDisponibile(dataLezione, orarioLezione, durataLezione);
        if (!personalTrainerDisponibile) {
            throw new RuntimeException("Personal Trainer già impegnato durante l'orario selezionato.");
        }
        this.lezioneCorrente = new Lezione(dataLezione, orarioLezione, durataLezione, LezioneEnum.LezionePT);
    }

    public boolean isPersonalTrainerDisponibile(LocalDate dataLezione, LocalTime orarioLezione, Float durataLezione) {
        System.out.println(this.personalTrainerSelezionato);
        Map<Integer, Lezione> lezioniPersonalTrainer = this.personalTrainerSelezionato.getLezioni();
        for (Lezione lezione : lezioniPersonalTrainer.values()) {
            if (lezione.getGiorno().equals(dataLezione)) {
                LocalTime inizioLezione = lezione.getOrario();
                LocalTime fineLezione = inizioLezione.plusMinutes(lezione.getDurata().longValue() * 60);

                LocalTime orarioLezioneFine = orarioLezione.plusMinutes(durataLezione.longValue() * 60);
                if ((orarioLezione.isAfter(inizioLezione) && orarioLezione.isBefore(fineLezione))
                        || ((orarioLezione.isBefore(inizioLezione) || orarioLezione.equals(inizioLezione))
                                && orarioLezioneFine.isAfter(inizioLezione))) {
                    return false;
                }
            }
        }
        return true;
    }

    public void confermaPrenotazione() {
        Prenotazione p = new Prenotazione();
        confermaLezione(p, clienti.get(this.clienteCorrente.getCodice()), this.lezioneCorrente);
        personalTrainers.get(this.personalTrainerSelezionato.getCodice()).setLezione(this.lezioneCorrente);
    }

    // UC7
    public void creazioneDiUnOffertaPromozionale() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Inserisci la percentuale di sconto: (ex. 10)");
        Integer sconto = scanner.nextInt();

        LocalDate dataInizio = null;
        LocalDate dataFine = null;
        scanner.nextLine();
        do {
            try {
                System.out.print("Inserisci la data di inizio dell’offerta (yyyy-MM-dd): ");
                String dataInizioInput = scanner.nextLine();
                dataInizio = LocalDate.parse(dataInizioInput);

                System.out.print("Inserisci la data di fine dell’offerta (yyyy-MM-dd): ");
                String dataFineInput = scanner.nextLine();
                dataFine = LocalDate.parse(dataFineInput);

                if (dataFine.isBefore(dataInizio)) {
                    System.out.println("La data di fine deve essere successiva alla data di inizio. Riprova.");
                    dataFine = null;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Data non valida. Inserisci una data valida.");
            }
        } while (dataInizio == null || dataFine == null);

        Offerta offerta = inserisciOfferta((sconto / 100f), dataInizio, dataFine);

        System.out.println("Offerta inserita: ");
        System.out.println(this.offertaCorrente.toString());

        System.out.print("Seleziona 1 per confermare, 0 per annullare: ");
        Integer conferma = scanner.nextInt();
        try {
            switch (conferma) {
                case 0:
                    System.out.println("Creazione dell'offerta promozionale annullata.");
                    return;
                case 1:
                    confermaOfferta();
                    break;
                default:
                    System.out.println("Inserisci un numero tra 0 e 1.");
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("Input non valido. Inserisci un numero.");
        }

        System.out.println("Creazione dell'offerta promozionale effettuata con successo.");
        pulisciCorrentiESelezionati();
    }

    public Offerta inserisciOfferta(Float sconto, LocalDate dataInizio, LocalDate dataFine) {
        Offerta offerta = new Offerta(sconto, dataInizio, dataFine);

        this.offertaCorrente = offerta;

        return offerta;
    }

    public void confermaOfferta() {
        this.offerte.put(this.offertaCorrente.getCodice(), this.offertaCorrente);
    }

    // UC8
    public void creazioneDiUnaSchedaPersonalizzata(Integer codicePersonalTrainer) {
        this.personalTrainerSelezionato = personalTrainers.get(codicePersonalTrainer);

        List<String> esercizi = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);

        Integer confermaEsercizio;
        do {
            System.out.print("Seleziona 1 per inserire un esercizio in lista, 0 per continuare: ");
            confermaEsercizio = scanner.nextInt();
            scanner.nextLine();
            try {
                switch (confermaEsercizio) {
                    case 0:
                        break;
                    case 1:
                        System.out.print("Inserisci la descrizione di un esercizio: ");
                        String esercizio = scanner.nextLine();
                        esercizi.add(esercizio);
                        break;
                    default:
                        System.out.println("Inserisci un numero tra 0 e 1.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Input non valido. Inserisci un numero.");
            }
        } while (confermaEsercizio != 0);

        LocalDate dataFine = null;
        do {
            try {
                System.out.print("Inserisci la data di fine dell’offerta (yyyy-MM-dd): ");
                String dataFineInput = scanner.nextLine();
                dataFine = LocalDate.parse(dataFineInput);
                if (dataFine.isBefore(LocalDate.now()))
                    dataFine = null;
            } catch (DateTimeParseException e) {
                System.out.println("Data non valida. Inserisci una data valida.");
            }
        } while (dataFine == null);

        Map<Integer, Cliente> clienti = inserisciSchedaPersonalizzata(esercizi, dataFine);

        System.out.println("Clienti disponibili: ");
        for (Cliente cliente : clienti.values()) {
            System.out.println(cliente.toString());
        }

        System.out.print("Inserisci il codice cliente a cui associare la scheda personalizzata: ");
        Integer codiceCliente = scanner.nextInt();
        scanner.nextLine();

        selezionaCliente(codiceCliente);

        System.out.print("Seleziona 1 per confermare, 0 per annullare: ");
        Integer conferma = scanner.nextInt();
        scanner.nextLine();
        try {
            switch (conferma) {
                case 0:
                    System.out.println("Creazione della scheda personalizzata annullata.");
                    return;
                case 1:
                    confermaSchedaPersonalizzata();
                    break;
                default:
                    System.out.println("Inserisci un numero tra 0 e 1.");
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("Input non valido. Inserisci un numero.");
        }

        System.out.println("Creazione della scheda personalizzata effettuata con successo.");
        pulisciCorrentiESelezionati();
    }

    public Map<Integer, Cliente> inserisciSchedaPersonalizzata(List<String> esercizi, LocalDate dataFine) {
        SchedaPersonalizzata schedaPersonalizzata = new SchedaPersonalizzata(esercizi, dataFine);
        this.schedaPersonalizzataCorrente = schedaPersonalizzata;

        return this.clienti;
    }

    public Cliente selezionaCliente(Integer codiceCliente) {
        Cliente cliente = clienti.get(codiceCliente);
        if (cliente == null) {
            throw new RuntimeException("Non esiste alcun cliente associato al codice inserito.");
        }
        this.clienteCorrente = cliente;
        return cliente;
    }

    public void confermaSchedaPersonalizzata() {
        this.clienteCorrente.setSchedaPersonalizzata(this.schedaPersonalizzataCorrente);
        this.personalTrainerSelezionato.setSchedaPersonalizzata(this.schedaPersonalizzataCorrente);
    }

    // UC9
    public void visualizzaPrenotatiDiUnaLezione(Integer codicePersonalTrainer) {
        this.personalTrainerSelezionato = personalTrainers.get(codicePersonalTrainer);

        List<Lezione> lezioniPT = visualizzaLezioniPT();
        for (Lezione l : lezioniPT) {
            System.out.println(l.toString());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Inserisci il codice della lezione di cui vuoi vedere gli iscritti: ");
        Integer codiceLezione = scanner.nextInt();

        selezionaLezionePT(codiceLezione);

        List<Cliente> clientiPrenotati = visualizzaPrenotati();
        if (clientiPrenotati.size() == 0) {
            System.out.println("Non è presente alcun cliente prenotato a questa lezione.");
        }
        for (Cliente c : clientiPrenotati) {
            System.out.println(c.toString());
        }

        System.out.println("Visualizzazione effettuata con successo.");
        pulisciCorrentiESelezionati();
    }

    public List<Lezione> visualizzaLezioniPT() {
        return new ArrayList<>(personalTrainerSelezionato.getLezioni().values());
    }

    public void selezionaLezionePT(Integer codiceLezione) {
        this.lezioneCorrente = this.personalTrainerSelezionato.getLezioni().get(codiceLezione);
    }

    public List<Cliente> visualizzaPrenotati() {
        List<Cliente> clientiPrenotati = new ArrayList<>();
        for (Cliente c : this.clienti.values()) {
            Collection<Prenotazione> prenotazioni = c.getPrenotazioni().values();
            for (Prenotazione p : prenotazioni) {
                if (p.getLezione().equals(this.lezioneCorrente)) {
                    clientiPrenotati.add(c);
                }
            }
        }
        return clientiPrenotati;
    }

    // UC10
    public void accessoInPalestraTramiteBadge(Integer codiceBadge) {
        Prenotazione prenotazione = accessoInPalestra(codiceBadge);

        confermaPresenza();

        System.out.println("Accesso in palestra acconsentito.");
        pulisciCorrentiESelezionati();
    }

    public Prenotazione accessoInPalestra(Integer codiceBadge) {
        if (!isBadgeValido(codiceBadge)) {
            throw new RuntimeException("Il badge inserito non appartiene a nessun cliente.");
        }

        if (!isPrenotazioneValida()) {
            throw new RuntimeException("Non esiste alcuna prenotazione valida associata a questo cliente.");
        }

        return this.prenotazioneCorrente;
    }

    public Boolean isBadgeValido(Integer codiceBadge) {
        for (Cliente cliente : this.clienti.values()) {
            if (cliente.getBadge().getCodice().equals(codiceBadge)) {
                this.clienteCorrente = cliente;
                return true;
            }
        }
        return false;
    }

    public Boolean isPrenotazioneValida() {
        for (Prenotazione p : this.clienteCorrente.getPrenotazioni().values()) {
            if (p.getLezione().getGiorno().isEqual(LocalDate.now())) {
                if (LocalTime.now().isAfter(p.getLezione().getOrario().minusMinutes(30))
                        && (LocalTime.now().isBefore(p.getLezione().getOrario())
                                || LocalTime.now().equals(p.getLezione().getOrario()))) {
                    this.prenotazioneCorrente = p;
                    return true;
                }
            }
        }
        return false;
    }

    public void confermaPresenza() {
        this.prenotazioneCorrente.setValidata();
    }

}
