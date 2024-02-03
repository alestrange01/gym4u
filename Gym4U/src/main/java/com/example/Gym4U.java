package com.example;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.InputMismatchException;
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
    private Amministratore admin;
    private Corso corsoCorrente;
    private Corso corsoSelezionato;
    private List<Corso> corsiDisponibili;
    private Map<Integer, Corso> corsi;
    private Prenotazione prenotazioneCorrente;
    private Map<Integer, Prenotazione> prenotazioni;
    private Cliente clienteCorrente;
    private PersonalTrainer personalTrainerSelezionato;
    private Offerta offertaCorrente;
    private Map<Integer, Offerta> offerte;

    private Gym4U() {
        this.personalTrainers = new HashMap<Integer, PersonalTrainer>();
        this.clienti = new HashMap<Integer, Cliente>();
        this.admin = null;
        this.corsoCorrente = null;
        this.corsoSelezionato = null;
        this.corsiDisponibili = null;
        this.corsi = new HashMap<Integer, Corso>();
        this.prenotazioneCorrente = null;
        this.prenotazioni = new HashMap<Integer, Prenotazione>();
        this.clienteCorrente = null;
        this.personalTrainerSelezionato = null;
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
        return this.personalTrainers;
    }

    public Map<Integer, Cliente> getClienti() {
        return this.clienti;
    }

    public Corso getCorsoCorrente() {
        return this.corsoCorrente;
    }

    public Corso getCorsoSelezionato() {
        return this.corsoSelezionato;
    }

    public Prenotazione getPrenotazioneCorrente() {
        return this.prenotazioneCorrente;
    }

    public Map<Integer, Prenotazione> getPrenotazioni() {
        return this.prenotazioni;
    }

    public Cliente getClienteCorrente() {
        return this.clienteCorrente;
    }

    public PersonalTrainer getPersonalTrainerSelezionato() {
        return this.personalTrainerSelezionato;
    }

    public Offerta getOffertaCorrente() {
        return this.offertaCorrente;
    }

    public Map<Integer, Offerta> getOfferte() {
        return this.offerte;
    }

    public void setPersonalTrainers(Map<Integer, PersonalTrainer> personalTrainers) {
        this.personalTrainers = personalTrainers;
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

    public void setCorsi(Map<Integer, Corso> corsi) {
        this.corsi = corsi;
    }

    public void setPrenotazioneCorrente(Prenotazione prenotazione) {
        this.prenotazioneCorrente = prenotazione;
    }

    public void setClienteCorrente(Cliente cliente) {
        this.clienteCorrente = cliente;
    }

    public void setPersonalTrainerSelezionato(PersonalTrainer personalTrainer) {
        this.personalTrainerSelezionato = personalTrainer;
    }

    public void setOffertaCorrente(Offerta offerta) {
        this.offertaCorrente = offerta;
    }

    public void loadData() {
        AbbonamentoAnnualeFactory abbonamentoAnnualeFactory = new AbbonamentoAnnualeFactory();
        Cliente cliente = new Cliente("Mario", "Rossi", LocalDate.of(1990, 1, 1), "Via Roma 1",
                "mariorossi@gmail.com", "3394309876");
        System.out.println("Cliente: " + cliente.getCodice());
        System.out.println("Badge: " + cliente.getBadge().getCodice());
        cliente.setAbbonamento(abbonamentoAnnualeFactory.creaAbbonamento());
        cliente.setCertificatoMedico(new CertificatoMedico(LocalDate.now().plusDays(365)));
        cliente.associaMetodoDiPagamento(1234567890, LocalDate.of(2022, 1, 1));
        this.clienti.put(cliente.getCodice(), cliente);

        Cliente cliente2 = new Cliente("Alice", "Bianchi", LocalDate.of(2001, 1, 1), "Via Napoli 1",
                "alicebianchi@gmail.com", "3394309876");
        System.out.println("Cliente2: " + cliente2.getCodice());
        System.out.println("Badge2: " + cliente2.getBadge().getCodice());
        cliente2.setAbbonamento(abbonamentoAnnualeFactory.creaAbbonamento());
        cliente2.setCertificatoMedico(new CertificatoMedico(LocalDate.now().plusDays(365)));
        cliente2.associaMetodoDiPagamento(12345890, LocalDate.of(2022, 1, 1));
        this.clienti.put(cliente2.getCodice(), cliente2);

        PersonalTrainer personalTrainer = new PersonalTrainer("Andrea", "Presti");
        System.out.println("Personal Trainer: " + personalTrainer.getCodice());
        this.personalTrainers.put(personalTrainer.getCodice(), personalTrainer);

        nuovoCorso("zumba", "Aerobica",
                Arrays.asList("Monday", "Thursday", "Friday"),
                Arrays.asList(LocalTime.of(10, 30), LocalTime.of(15, 30)),
                1.5f, 10, new ArrayList<>(getPersonalTrainers().keySet()));
        confermaNuovoCorso();

        nuovoCorso("pilates", "Funzionale",
                Arrays.asList("Tuesday", "Thursday", "Friday"),
                Arrays.asList(LocalTime.of(8, 30), LocalTime.of(19, 30)),
                1.5f, 10, new ArrayList<>(getPersonalTrainers().keySet()));
        confermaNuovoCorso();

        nuovoCorso("crossfit", "Funzionale",
                Arrays.asList("Tuesday", "Saturday"),
                Arrays.asList(LocalTime.of(10, 30), LocalTime.of(18, 30)),
                1.5f, 10, new ArrayList<>(getPersonalTrainers().keySet()));
        confermaNuovoCorso();
        this.admin = new Amministratore();
    }

    public Integer verificaUtente(Integer codice, String password) {
        if (verificaCliente(codice, password)) {
            return 1;
        } else if (verificaAmministratore(codice, password)) {
            return 2;
        } else if (verificaIngressoInPalestra(codice)) {
            return 3;
        } else if (verificaPersonalTrainer(codice, password)) {
            return 4;
        } else {
            return 0;
        }
    }

    public boolean verificaCliente(Integer codice, String password) {
        Cliente cliente = this.clienti.get(codice);
        if (cliente != null && cliente.verificaPassword(password)) {
            return true;
        }
        return false;
    }

    public boolean verificaAmministratore(Integer codice, String password) {
        if (codice == this.admin.getCodice() && this.admin.verificaPassword(password)) {
            return true;
        }
        return false;
    }

    public boolean verificaPersonalTrainer(Integer codice, String password) {
        PersonalTrainer personalTrainer = this.personalTrainers.get(codice);
        if (personalTrainer != null && personalTrainer.verificaPassword(password)) {
            return true;
        }
        return false;
    }

    public boolean verificaIngressoInPalestra(Integer codice) {
        if (codice == 1) {
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
                        System.out.println("Iscrizione al corso annullata.");
                        this.corsoSelezionato = null;
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
        System.out.println("Iscrizione al corso effettuata con successo.");
    }

    public List<Corso> visualizzaCorsi(Integer codiceCliente) {
        Cliente cliente = this.clienti.get(codiceCliente);
        if (!cliente.verificaCertificatoMedico() || !cliente.verificaAbbonamento()) {
            throw new RuntimeException("Certificato medico o/e abbonamento del cliente non valido.");
        }
        this.corsiDisponibili = getCorsiDisponibili(codiceCliente);
        return this.corsiDisponibili;
    }

    public List<Corso> getCorsiDisponibili(Integer codiceCliente) {
        List<Corso> corsiDisponibili = new ArrayList<Corso>();
        Cliente cliente = this.clienti.get(codiceCliente);
        for (Map.Entry<Integer, Corso> entry : this.corsi.entrySet()) {
            Corso corso = entry.getValue();
            if (corso.getDisponibilità() > 0 && !cliente.getCorsi().containsKey(corso.getCodiceUnivoco()))
                corsiDisponibili.add(corso);
        }
        return corsiDisponibili;
    }

    public Corso selezionaCorso(Integer codiceUnivoco) {
        for (Corso corso : this.corsiDisponibili) {
            if (corso.getCodiceUnivoco().equals(codiceUnivoco)) {
                this.corsoSelezionato = corso;
                return corso;
            }
        }
        return null;
    }

    public void confermaIscrizione(Integer codiceCliente) {
        Cliente cliente = this.clienti.get(codiceCliente);
        cliente.setCorso(this.corsoSelezionato);
        this.corsoSelezionato.diminuisciDisponibilità();
        this.corsoSelezionato = null;
        this.corsiDisponibili = null;
    }

    // UC2
    public void prenotazioneLezioneCorso(Integer codiceCliente) {
        Cliente cliente = this.clienti.get(codiceCliente);
        if (!visualizzaCorsiCliente(cliente))
            return;
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
                    System.out.println("Prenotazione alla lezione annullata.");
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
    }

    public boolean visualizzaCorsiCliente(Cliente cliente) {
        if (!cliente.verificaCertificatoMedico() || !cliente.verificaAbbonamento()) {
            throw new RuntimeException("Certificato medico o/e abbonamento del cliente non valido.");
        }
        if (cliente.getCorsi().isEmpty()) {
            System.out.println("Non sei iscritto a nessun corso.");
            return false;
        }
        System.out.println("Corsi disponibili: ");
        for (Map.Entry<Integer, Corso> entry : cliente.getCorsi().entrySet()) {
            System.out.println(entry.getValue().toString());
        }
        this.corsiDisponibili = new ArrayList<>(cliente.getCorsi().values());
        return true;
    }

    public Map<Integer, Lezione> selezionaCorsoRestituisciLezioni(Integer codiceUnivoco) {
        Corso corso = this.corsi.get(codiceUnivoco);
        if (corso == null) {
            return null;
        }
        this.corsoSelezionato = corso;
        corso.loadLezioni();
        return corso.getLezioni();
    }

    public Lezione selezionaLezione(Integer codiceLezione) {
        return this.corsoSelezionato.getLezioni().get(codiceLezione);
    }

    public boolean prenotazionePossibile(Lezione lezione, Cliente cliente) {
        if(lezione != null){
            for (Map.Entry<Integer, Prenotazione> entry : cliente.getPrenotazioni().entrySet()) {
                if (entry.getValue().getLezione().getGiorno().equals(lezione.getGiorno())) {
                    System.out.println("Hai già prenotato una lezione per questo giorno.");
                    return false;
                }
            }
        }
        return true;
    }

    public void creaPrenotazione() {
        this.prenotazioneCorrente = new Prenotazione();
    }

    public void confermaLezione(Prenotazione prenotazione, Cliente cliente, Lezione lezione) {
        prenotazione.setLezione(lezione);
        this.prenotazioni.put(prenotazione.getCodice(), prenotazione);
        cliente.setPrenotazione(prenotazione);
        this.corsoSelezionato = null;
        this.prenotazioneCorrente = null;
        this.corsiDisponibili = null;
    }

    // UC3
    public void creaNuovoCorso() {
        if (!infoNuovoCorso())
            return;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Riepilogo informazioni inserite: ");
        System.out.println(this.corsoCorrente.getTotalInfo());
        System.out.println("Personal Trainer selezionati: ");
        for (Integer codicePersonalTrainer : this.corsoCorrente.getIdsPersonalTrainer()) {
            PersonalTrainer personalTrainer = this.personalTrainers.get(codicePersonalTrainer);
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
    }

    public boolean infoNuovoCorso() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Inserisci il nome del corso: ");
        String nome = scanner.nextLine();

        System.out.print("Inserisci la descrizione del corso: ");
        String descrizione = scanner.nextLine();

        System.out.print("Inserisci i giorni (Monday->Sunday) disponibili del corso separati da una virgola: ");
        List<String> giorniDisponibili = Arrays.stream(scanner.nextLine().split(","))
                .map(String::trim)
                .collect(Collectors.toList());

        System.out.print("Inserisci gli orari (HH:mm) disponibili del corso separati da una virgola: ");
        String[] orariInput = scanner.nextLine().split(",");
        List<LocalTime> orariDisponibili = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        for (String orario : orariInput) {
            LocalTime time = LocalTime.parse(orario.trim(), formatter);
            orariDisponibili.add(time);
        }

        System.out.print("Inserisci la durata delle lezioni del corso (ex 1,5): ");
        float durataLezione = -1;
        try {
            durataLezione = scanner.nextFloat();
            if (durataLezione <= 0) {
                System.out.println("Input non valido. Inserisci un numero maggiore di 0.");
                return false;
            }
        } catch (InputMismatchException e) {
            System.out.println("Input non valido. Inserisci un numero.");
            return false;
        }

        Integer postiDisponibili = -1;
        System.out.print("Inserisci il numero di posti disponibili del corso: ");
        try {
            postiDisponibili = scanner.nextInt();
            if (postiDisponibili <= 0) {
                System.out.println("Input non valido. Inserisci un numero maggiore di 0.");
                return false;
            }
        } catch (InputMismatchException e) {
            System.out.println("Input non valido. Inserisci un numero.");
            return false;
        }

        System.out.print("Associa uno o più Personal Trainer: ");
        List<Integer> codiciPersonalTrainer = Arrays.stream(scanner.next().split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        for (Integer ptId : codiciPersonalTrainer) {
            PersonalTrainer pt = this.personalTrainers.get(ptId);
            if (pt.getCorsi() != null && pt.getCorsi().size() == 5) {
                System.out.println(
                        "Impossibile creare il nuovo corso, è presente un personal trainer che insegna già 5 corsi.");
                return false;
            }
        }

        nuovoCorso(nome, descrizione, giorniDisponibili, orariDisponibili, durataLezione, postiDisponibili,
                codiciPersonalTrainer);
        return true;
    }

    public void nuovoCorso(String nome, String descrizione, List<String> giorniDisponibili,
            List<LocalTime> orariDisponibili, float durataLezione, Integer postiDisponibili,
            List<Integer> codiciPersonalTrainer) {
        Corso corso = new Corso(nome, descrizione, giorniDisponibili, orariDisponibili,
                durataLezione, postiDisponibili, codiciPersonalTrainer);
        this.corsoCorrente = corso;
    }

    public void confermaNuovoCorso() {
        associaPersonalTrainer(this.corsoCorrente.getIdsPersonalTrainer());
        this.corsi.put(this.corsoCorrente.getCodiceUnivoco(), this.corsoCorrente);
        this.corsoCorrente = null;
    }

    public void associaPersonalTrainer(List<Integer> codiciPersonalTrainer) {
        for (Integer codicePersonalTrainer : codiciPersonalTrainer) {
            PersonalTrainer personalTrainer = this.personalTrainers.get(codicePersonalTrainer);
            personalTrainer.setCorso(this.corsoCorrente);
            for (Lezione l : this.corsoCorrente.getLezioni().values()) {
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

        System.out.println("Riepilogo informazioni inserite: ");
        System.out.println(this.clienteCorrente.toString());
        System.out.println(this.clienteCorrente.getCertificatoMedico().toString());
        System.out.println(this.clienteCorrente.getAbbonamento().toString());
        System.out.println(this.clienteCorrente.getMetodoDiPagamento().toString());

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
        Offerta offertaAttiva = verificaOffertaAttiva();
        if (offertaAttiva != null)
            this.clienteCorrente.getAbbonamento().setOfferta(offertaAttiva);
    }

    public Offerta verificaOffertaAttiva() {
        for (Offerta offerta : this.offerte.values()) {
            if ((LocalDate.now().isAfter(offerta.getDataInizio()) || LocalDate.now().equals(offerta.getDataInizio()))
                    && (LocalDate.now().isBefore(offerta.getDataFine())
                            || LocalDate.now().equals(offerta.getDataFine()))) {
                return offerta;
            }
        }
        return null;
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
        Cliente cliente = this.clienti.get(codiceCliente);

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
                    System.out.println(cliente.getAbbonamentoCorrente().toString());

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
                    System.out.println(cliente.getMedotoDiPagamentoCorrente().toString());

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
    }

    public void modificaAbbonamento(Integer tipologiaAbbonamento, Cliente cliente) {
        cliente.setAbbonamentoCorrente(cliente.modificaAbbonamento(tipologiaAbbonamento));
        Offerta offertaAttiva = verificaOffertaAttiva();
        if (offertaAttiva != null)
            cliente.getAbbonamentoCorrente().setOfferta(offertaAttiva);
    }

    public void confermaModificaAbbonamento(Cliente cliente) {
        cliente.setAbbonamento(cliente.getAbbonamentoCorrente());
        cliente.setAbbonamentoCorrente(null);
    }

    public void modificaMetodoDiPagamento(Integer numeroCarta, LocalDate dataScadenzaCarta, Cliente cliente) {
        cliente.setMedotoDiPagamentoCorrente(cliente.modificaMetodoDiPagamento(numeroCarta, dataScadenzaCarta));
    }

    public void confermaModificaMetodoDiPagamento(Cliente cliente) {
        cliente.setMetodoDiPagamento(cliente.getMedotoDiPagamentoCorrente());
        cliente.setMedotoDiPagamentoCorrente(null);
    }

    // UC6
    public void prenotazioneLezionePT(Integer codiceCliente) {
        Cliente cliente = this.clienti.get(codiceCliente);
        this.clienteCorrente = cliente;

        Map<Integer, PersonalTrainer> personalTrainers = visualizzaPersonalTrainers(cliente);
        System.out.println("Personal trainer disponibili: ");
        for (Map.Entry<Integer, PersonalTrainer> entry : personalTrainers.entrySet()) {
            System.out.println(entry.getValue().toString());
        }

        Scanner scanner = new Scanner(System.in);
        
        String codicePersonalTrainer = null;
        String giorno = null;
        LocalTime time = null;
        do {
            System.out.print("Inserisci il codice del personal trainer: ");
            codicePersonalTrainer = scanner.nextLine();
            
            System.out.print("Inserisci il giorno della lezione (Monday->Sunday): ");
            giorno = scanner.nextLine();

            System.out.print("Inserisci l'orario della lezione(HH:mm): ");
            String ora = scanner.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            time = LocalTime.parse(ora.trim(), formatter);
        } while (!selezionaPersonalTrainer(codicePersonalTrainer, giorno, time, 1f));

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
    }

    public Map<Integer, PersonalTrainer> visualizzaPersonalTrainers(Cliente cliente) {
        if (!cliente.verificaCertificatoMedico() || !cliente.verificaAbbonamento()) {
            throw new RuntimeException(
                    "Impossibile effettuare la prenotazione, cliente con certificato medico/abbonamento non valido.");
        }
        return this.personalTrainers;
    }

    public boolean selezionaPersonalTrainer(String codicePersonalTrainer, String giorno, LocalTime orarioLezione,
            Float durataLezione) {
        this.personalTrainerSelezionato = this.personalTrainers.get(Integer.valueOf(codicePersonalTrainer));
        if(this.personalTrainerSelezionato == null){
            return false;
        }

        DayOfWeek giornoDaAggiungere = DayOfWeek.valueOf(giorno.toUpperCase());
        int giorniDiDifferenza = (giornoDaAggiungere.getValue() - LocalDate.now().getDayOfWeek().getValue() + 7) % 7;
        LocalDate dataLezione = LocalDate.now().plusDays(giorniDiDifferenza);

        Boolean personalTrainerDisponibile = isPersonalTrainerDisponibile(dataLezione, orarioLezione, durataLezione);
        if (!personalTrainerDisponibile) {
            System.out.println("Personal Trainer già impegnato durante l'orario selezionato.");
            return false;
        }
        this.personalTrainerSelezionato
                .setLezioneCorrente(new Lezione(dataLezione, orarioLezione, durataLezione, LezioneEnum.LezionePT));
        return true;
    }

    public boolean isPersonalTrainerDisponibile(LocalDate dataLezione, LocalTime orarioLezione, Float durataLezione) {
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
        confermaLezione(p, this.clienti.get(this.clienteCorrente.getCodice()),
                this.personalTrainerSelezionato.getLezioneCorrente());
        this.personalTrainers.get(this.personalTrainerSelezionato.getCodice())
                .setLezione(this.personalTrainerSelezionato.getLezioneCorrente());
        this.personalTrainerSelezionato.setLezioneCorrente(null);
        this.personalTrainerSelezionato = null;
        this.clienteCorrente = null;
    }

    // UC7
    public void creazioneDiUnOffertaPromozionale() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Inserisci la percentuale di sconto (ex. 10): ");
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

        for (Offerta offerta : this.offerte.values()) {
            if ((dataInizio.isAfter(offerta.getDataInizio()) || dataInizio.equals(offerta.getDataInizio())) &&
                    (dataInizio.isBefore(offerta.getDataFine()) || dataInizio.equals(offerta.getDataFine()))
                    || (dataFine.isAfter(offerta.getDataInizio()) || dataFine.equals(offerta.getDataInizio())) &&
                            (dataFine.isBefore(offerta.getDataFine()) || dataFine.equals(offerta.getDataFine()))) {
                System.out.println("Offerta coincidente con un'offerta già esistente.");
                return;
            }
        }

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
    }

    public Offerta inserisciOfferta(Float sconto, LocalDate dataInizio, LocalDate dataFine) {
        Offerta offerta = new Offerta(sconto, dataInizio, dataFine);

        this.offertaCorrente = offerta;

        return offerta;
    }

    public void confermaOfferta() {
        this.offerte.put(this.offertaCorrente.getCodice(), this.offertaCorrente);
        this.offertaCorrente = null;
    }

    // UC8
    public void creazioneDiUnaSchedaPersonalizzata(Integer codicePersonalTrainer) {
        this.personalTrainerSelezionato = this.personalTrainers.get(codicePersonalTrainer);

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
                System.out.print("Inserisci la data di fine della scheda (yyyy-MM-dd): ");
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
    }

    public Map<Integer, Cliente> inserisciSchedaPersonalizzata(List<String> esercizi, LocalDate dataFine) {
        SchedaPersonalizzata schedaPersonalizzata = new SchedaPersonalizzata(esercizi, dataFine);
        this.personalTrainerSelezionato.setSchedaPersonalizzataCorrente(schedaPersonalizzata);

        return this.clienti;
    }

    public Cliente selezionaCliente(Integer codiceCliente) {
        Cliente cliente = this.clienti.get(codiceCliente);
        if (cliente == null) {
            throw new RuntimeException("Non esiste alcun cliente associato al codice inserito.");
        }
        this.clienteCorrente = cliente;
        return cliente;
    }

    public void confermaSchedaPersonalizzata() {
        this.clienteCorrente.setSchedaPersonalizzata(this.personalTrainerSelezionato.getSchedaPersonalizzataCorrente());
        this.personalTrainerSelezionato
                .setSchedaPersonalizzata(this.personalTrainerSelezionato.getSchedaPersonalizzataCorrente());
        this.clienteCorrente = null;
        this.personalTrainerSelezionato.setSchedaPersonalizzataCorrente(null);
        this.personalTrainerSelezionato = null;
    }

    // UC9
    public void visualizzaPrenotatiDiUnaLezione(Integer codicePersonalTrainer) {
        this.personalTrainerSelezionato = this.personalTrainers.get(codicePersonalTrainer);

        List<Lezione> lezioniPT = visualizzaLezioniPT();
        if (lezioniPT.size() == 0) {
            System.out.println("Non è presente alcuna lezione associata a questo personal trainer.");
        }else{
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
        }
        
    }

    public List<Lezione> visualizzaLezioniPT() {
        return new ArrayList<>(this.personalTrainerSelezionato.getLezioni().entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.comparing(Lezione::getGiorno)))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList()));
    }

    public void selezionaLezionePT(Integer codiceLezione) {
        this.personalTrainerSelezionato
                .setLezioneCorrente(this.personalTrainerSelezionato.getLezioni().get(codiceLezione));
    }

    public List<Cliente> visualizzaPrenotati() {
        List<Cliente> clientiPrenotati = new ArrayList<>();
        for (Cliente c : this.clienti.values()) {
            Collection<Prenotazione> prenotazioni = c.getPrenotazioni().values();
            for (Prenotazione p : prenotazioni) {
                if (p.getLezione().equals(this.personalTrainerSelezionato.getLezioneCorrente())) {
                    clientiPrenotati.add(c);
                }
            }
        }
        this.personalTrainerSelezionato.setLezioneCorrente(null);
        this.personalTrainerSelezionato = null;
        return clientiPrenotati;
    }

    // UC10
    public void accessoInPalestraTramiteBadge(Integer codiceBadge) {
        Prenotazione prenotazione = accessoInPalestra(codiceBadge);

        confermaPresenza();

        System.out.println("Accesso in palestra acconsentito.");
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
        this.prenotazioneCorrente = null;
        this.clienteCorrente = null;
    }

    public void visualizzaSchedaPersonalizzata(Integer codiceCliente) {
        Cliente cliente = this.clienti.get(codiceCliente);
        SchedaPersonalizzata schedaPersonalizzata = cliente.getSchedaPersonalizzata();
        if (schedaPersonalizzata == null) {
            System.out.println("Non hai ancora alcuna scheda personalizzata.");
        } else {
            System.out.println(schedaPersonalizzata.toString());
        }
    }


    public void modificaClienteAdmin(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Clienti disponibili: ");
        for (Cliente cliente : clienti.values()) {
            System.out.println(cliente.toString());
        }
        System.out.print("Inserisci il codice cliente da modificare: ");
        Integer codiceCliente = scanner.nextInt();
        scanner.nextLine();

        modificaCliente(codiceCliente);
    }

    public void modificaAccountPassword(Integer codiceCliente){
        System.out.print("Inserisci il numero dell'opzione desiderata:\n" +
                "1. Modifica Dati Account\n" +
                "2. Modifica Password\n" +
                "0. Esci\n" +
                "Inserisci il numero corrispondente: ");
        Scanner scanner = new Scanner(System.in);
        Integer scelta = Integer.parseInt(scanner.nextLine());
        try {
            switch (scelta) {
                case 0:
                    System.out.println("Modifica account annullata.");
                    return;
                case 1:
                    modificaCliente(codiceCliente);
                    break;
                case 2:
                    modificaPassword(codiceCliente);
                    break;
                default:
                    System.out.println("Inserisci un numero tra 0 e 2.");
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("Input non valido. Inserisci un numero.");
        }
    }

    public void modificaCliente(Integer codiceCliente){
        Cliente cliente = selezionaCliente(codiceCliente);
        cliente.modificaCliente();
        this.clienteCorrente = null;
    }

    public void modificaPassword(Integer codiceCliente){
        Cliente cliente = this.clienti.get(codiceCliente);
        cliente.modificaPassword();
    }

    public void creaPersonalTrainer(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Inserisci il nome del personal trainer: ");
        String nome = scanner.nextLine();

        System.out.print("Inserisci il cognome del personal trainer: ");
        String cognome = scanner.nextLine();

        PersonalTrainer pt = new PersonalTrainer(nome, cognome);
        this.personalTrainerSelezionato = pt;

        System.out.println("Personal trainer inserito: ");
        System.out.println(this.personalTrainerSelezionato.toString());
        Integer conferma = 0;
        System.out.print("Seleziona 1 per confermare, 0 per annullare: ");
        conferma = scanner.nextInt();
        scanner.nextLine();
        if(conferma == 1){
            this.personalTrainers.put(this.personalTrainerSelezionato.getCodice(), this.personalTrainerSelezionato);
            System.out.println("Personal trainer inserito con successo.");
        }
        else{
            System.out.println("Creazione personal trainer annullata.");
        }
    }

    public void visualizzaCorsi(){
        System.out.println("Corsi offerti: ");
        for (Corso c : this.corsi.values()) {
            System.out.println(c.getTotalInfo());
        }
    }

}
