package com.example;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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

    private Gym4U() {
        this.clienti = new HashMap<Integer, Cliente>();
        this.personalTrainers = new HashMap<Integer, PersonalTrainer>();
        this.corsoCorrente = null;
        this.corsoSelezionato = null;
        this.corsiDisponibili = null;
        this.corsi = new HashMap<Integer, Corso>();
        this.prenotazioni = new HashMap<Integer, Prenotazione>();
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

    public Corso getCorsoCorrente() {
        return corsoCorrente;
    }

    public Corso getCorsoSelezionato() {
        return corsoSelezionato;
    }

    public Map<Integer, Prenotazione> getPrenotazioni() {
        return prenotazioni;
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

    public void loadData() {
        // Avviamento
        Cliente cliente = new Cliente();
        System.out.println("Cliente: " + cliente.getCodice());
        cliente.createCertificatoMedico();
        cliente.createAbbonamento();
        clienti.put(cliente.getCodice(), cliente);

        PersonalTrainer personalTrainer = new PersonalTrainer();
        System.out.println("Personal Trainer: " + personalTrainer.getCodice());
        personalTrainers.put(personalTrainer.getCodice(), personalTrainer);

        Corso corso = new Corso("zumba", "Aerobica",
                Arrays.asList("lun", "mar", "gio", "ven"),
                Arrays.asList(LocalTime.of(10, 30), LocalTime.of(12, 30)),
                1.5f, 10, new ArrayList<>(getPersonalTrainers().keySet()));
        this.corsi.put(corso.getCodiceUnivoco(), corso);
    }

    public boolean verificaCliente(Integer codiceCliente) {
        Cliente cliente = clienti.get(codiceCliente);
        if (cliente != null) {
            return true;
        }
        return false;
    }

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

        System.out.println("Lezioni disponibili: ");
        if(cliente.getPrenotazioni().isEmpty()){
            for (Map.Entry<Integer, Lezione> entry : lezioni.entrySet()) {
                System.out.println(entry.getValue().toString());
                System.out.println("-----------------------------");
            }
        }else{
            List<Integer> codiciLezioniPrenotate = new ArrayList<>();
            for (Map.Entry<Integer, Prenotazione> entry : cliente.getPrenotazioni().entrySet()) {
                codiciLezioniPrenotate.add(entry.getValue().getLezione().getCodice());
            }

            for (Map.Entry<Integer, Lezione> entryLezione : lezioni.entrySet()) {
                if(!codiciLezioniPrenotate.contains(entryLezione.getKey())){
                        System.out.println(entryLezione.getValue().toString());
                        System.out.println("-----------------------------");
                    }
            }
        }




        Lezione lezioneSelezionata = null;
        do {
            System.out.print("Inserisci il codice della lezione: ");
            String inputLezione = scanner.next();
            try {
                Integer codiceLezione = Integer.parseInt(inputLezione);
                lezioneSelezionata = selezionaLezione(codiceLezione);
                creaPrenotazione();
            } catch (NumberFormatException e) {
                System.out.println("Input non valido. Inserisci un numero.");
            }
        } while (lezioneSelezionata == null);

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
        return corso.getLezioni();
    }

    public Lezione selezionaLezione(Integer codiceLezione) {
        return this.corsoSelezionato.getLezioni().get(codiceLezione);
    }

    public void creaPrenotazione() {
        this.prenotazioneCorrente = new Prenotazione();
    }

    public void confermaLezione(Prenotazione prenotazione, Cliente cliente, Lezione lezione) {
        prenotazione.setLezione(lezione);
        prenotazioni.put(prenotazione.getCodice(), prenotazione);
        cliente.setPrenotazione(prenotazione);
    }



}
