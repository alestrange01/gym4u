package com.example;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Random;
import java.util.Scanner;

public class Cliente extends Observable {

    private Integer codice;
    private String password;
    private String nome;
    private String cognome;
    private LocalDate dataNascita;
    private String indirizzo;
    private String email;
    private String telefono;
    private Abbonamento abbonamento;
    private CertificatoMedico certificatoMedico;
    private Map<Integer, Corso> corsi;
    private Map<Integer, Prenotazione> prenotazioni;
    private Badge badge;
    private MetodoDiPagamento metodoDiPagamento;
    private SchedaPersonalizzata schedaPersonalizzata;
    private Abbonamento abbonamentoCorrente;
    private MetodoDiPagamento metodoDiPagamentoCorrente;

    public Cliente(String nome, String cognome, LocalDate dataNascita, String indirizzo, String email,
            String telefono) {
        this.codice = new Random().nextInt(100000);
        this.password = "0";
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.indirizzo = indirizzo;
        this.email = email;
        this.telefono = telefono;
        this.abbonamento = null;
        this.certificatoMedico = null;
        this.corsi = new HashMap<Integer, Corso>();
        this.prenotazioni = new HashMap<Integer, Prenotazione>();
        this.badge = new Badge();
        this.metodoDiPagamento = null;
        this.schedaPersonalizzata = null;
        this.abbonamentoCorrente = null;
        this.metodoDiPagamentoCorrente = null;
        new MailObserver(this);
        new SMSObserver(this);
    }

    public Integer getCodice() {
        return this.codice;
    }

    public String getPassword() {
        return this.password;
    }

    public String getNome() {
        return this.nome;
    }

    public String getCognome() {
        return this.cognome;
    }

    public LocalDate getDataNascita() {
        return this.dataNascita;
    }

    public String getIndirizzo() {
        return this.indirizzo;
    }

    public String getEmail() {
        return this.email;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public Abbonamento getAbbonamento() {
        return this.abbonamento;
    }

    public CertificatoMedico getCertificatoMedico() {
        return this.certificatoMedico;
    }

    public Map<Integer, Corso> getCorsi() {
        return this.corsi;
    }

    public Map<Integer, Prenotazione> getPrenotazioni() {
        return this.prenotazioni;
    }

    public Badge getBadge() {
        return this.badge;
    }

    public MetodoDiPagamento getMetodoDiPagamento() {
        return this.metodoDiPagamento;
    }

    public SchedaPersonalizzata getSchedaPersonalizzata() {
        return this.schedaPersonalizzata;
    }

    public Abbonamento getAbbonamentoCorrente() {
        return this.abbonamentoCorrente;
    }

    public MetodoDiPagamento getMedotoDiPagamentoCorrente() {
        return this.metodoDiPagamentoCorrente;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;

    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;

    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;

    }

    public void setAbbonamento(Abbonamento abbonamento) {
        this.abbonamento = abbonamento;
    }

    public void setCertificatoMedico(CertificatoMedico certificatoMedico) {
        this.certificatoMedico = certificatoMedico;
    }

    public void setCorso(Corso c) {
        this.corsi.put(c.getCodiceUnivoco(), c);
    }

    public void setPrenotazione(Prenotazione p) {
        this.prenotazioni.put(p.getCodice(), p);
        setChanged();
        notifyObservers(p);
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }

    public void setSchedaPersonalizzata(SchedaPersonalizzata sp) {
        this.schedaPersonalizzata = sp;
    }

    public void setAbbonamentoCorrente(Abbonamento abbonamento) {
        this.abbonamentoCorrente = abbonamento;
    }

    public void setMedotoDiPagamentoCorrente(MetodoDiPagamento metodoDiPagamento) {
        this.metodoDiPagamentoCorrente = metodoDiPagamento;
    }

    public void associaAbbonamento(Integer tipologiaAbbonamento) {
        switch (tipologiaAbbonamento) {
            case 1:
                AbbonamentoMensileFactory abbonamentoMensileFactory = new AbbonamentoMensileFactory();
                AbbonamentoMensile abbonamentoMensile = abbonamentoMensileFactory.creaAbbonamento();
                setAbbonamento(abbonamentoMensile);
                break;
            case 2:
                AbbonamentoSemestraleFactory abbonamentoSemestraleFactory = new AbbonamentoSemestraleFactory();
                AbbonamentoSemestrale abbonamentoSemestrale = abbonamentoSemestraleFactory.creaAbbonamento();
                setAbbonamento(abbonamentoSemestrale);
                break;
            case 3:
                AbbonamentoAnnualeFactory abbonamentoAnnualeFactory = new AbbonamentoAnnualeFactory();
                AbbonamentoAnnuale abbonamentoAnnuale = abbonamentoAnnualeFactory.creaAbbonamento();
                setAbbonamento(abbonamentoAnnuale);
                break;
            default:
                break;
        }
    }

    public Abbonamento modificaAbbonamento(Integer tipologiaAbbonamento) {
        switch (tipologiaAbbonamento) {
            case 1:
                AbbonamentoMensileFactory abbonamentoMensileFactory = new AbbonamentoMensileFactory();
                AbbonamentoMensile abbonamentoMensile = abbonamentoMensileFactory.creaAbbonamento();
                return abbonamentoMensile;
            case 2:
                AbbonamentoSemestraleFactory abbonamentoSemestraleFactory = new AbbonamentoSemestraleFactory();
                AbbonamentoSemestrale abbonamentoSemestrale = abbonamentoSemestraleFactory.creaAbbonamento();
                return abbonamentoSemestrale;
            case 3:
                AbbonamentoAnnualeFactory abbonamentoAnnualeFactory = new AbbonamentoAnnualeFactory();
                AbbonamentoAnnuale abbonamentoAnnuale = abbonamentoAnnualeFactory.creaAbbonamento();
                return abbonamentoAnnuale;
            default:
                return null;
        }
    }

    public boolean verificaAbbonamento() {
        return this.abbonamento.verificaAbbonamento();
    }

    public void associaCertificatoMedico(LocalDate dataScadenza) {
        CertificatoMedico certificatoMedico = new CertificatoMedico(dataScadenza);
        setCertificatoMedico(certificatoMedico);
    }

    public boolean verificaCertificatoMedico() {
        return this.certificatoMedico.verificaCertificatoMedico();
    }

    public void setMetodoDiPagamento(MetodoDiPagamento metodoDiPagamento) {
        this.metodoDiPagamento = metodoDiPagamento;
    }

    public void associaMetodoDiPagamento(Integer numeroCarta, LocalDate dataScadenzaCarta) {
        MetodoDiPagamento metodoDiPagamento = new MetodoDiPagamento(numeroCarta, dataScadenzaCarta);
        setMetodoDiPagamento(metodoDiPagamento);
    }

    public MetodoDiPagamento modificaMetodoDiPagamento(Integer numeroCarta, LocalDate dataScadenzaCarta) {
        MetodoDiPagamento metodoDiPagamento = new MetodoDiPagamento(numeroCarta, dataScadenzaCarta);
        return metodoDiPagamento;
    }

    public void creaBadge() {
        Badge badge = new Badge();
        setBadge(badge);
    }

    public Boolean verificaPassword(String password) {
        if (this.password.equals(password)) {
            return true;
        }
        return false;
    }

    public void modificaPassword(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Inserisci la vecchia password: ");
        String oldPassword = scanner.nextLine();
        if(getPassword().equals(oldPassword)){
            System.out.print("Inserisci la nuova password: ");
            String newPassword = scanner.nextLine();
            setPassword(newPassword);
            System.out.println("Password modificata con successo.");
        }
        else{
            System.out.println("Password errata.");
        }
    }

    public void modificaCliente(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Riepilogo informazioni cliente: ");
        System.out.println(toString());

        Integer scelta = 0;
        do{
            System.out.println("Seleziona il campo da moficare: ");
            System.out.println("1. Nome");
            System.out.println("2. Cognome");
            System.out.println("3. Data di nascita");
            System.out.println("4. Indirizzo");
            System.out.println("5. Email");
            System.out.println("6. Telefono");
            System.out.println("0. Esci");
            System.out.print("Inserisci il numero corrispondente: ");
            scelta = scanner.nextInt();
            scanner.nextLine();

            Integer conferma = 0;
            switch (scelta) {
                case 1:
                    System.out.println("Nome attuale: " + getNome());
                    System.out.print("Inserisci il nuovo nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Seleziona 1 per confermare, 0 per annullare: ");
                    conferma = scanner.nextInt();
                    scanner.nextLine();
                    if(conferma == 1){
                        setNome(nome);
                    }
                    break;
                case 2:
                    System.out.println("Cognome attuale: " + getCognome());
                    System.out.print("Inserisci il nuovo cognome: ");
                    String cognome = scanner.nextLine();
                    System.out.print("Seleziona 1 per confermare, 0 per annullare: ");
                    conferma = scanner.nextInt();
                    scanner.nextLine();
                    if(conferma == 1){
                        setCognome(cognome);
                    }
                    break;
                case 3: 
                    System.out.println("Data di nascita attuale: " + getDataNascita());
                    LocalDate dataNascita = null;
                    do {
                        try {
                            System.out.print("Inserisci la nuova data di nascita del cliente (yyyy-MM-dd): ");
                            String dataNascitaInput = scanner.nextLine();
                            dataNascita = LocalDate.parse(dataNascitaInput);
                        } catch (DateTimeParseException e) {
                            System.out.println("Data non valida. Inserisci una data valida.");
                        }
                    } while (dataNascita == null);
                    if (LocalDate.now().minusYears(18).isBefore(dataNascita)) {
                        System.out.println("Il cliente deve essere maggiorenne.");
                        System.out.println("Modifica cliente annullata.");
                        return;
                    }
                    System.out.print("Seleziona 1 per confermare, 0 per annullare: ");
                    conferma = scanner.nextInt();
                    scanner.nextLine();
                    if(conferma == 1){
                        setDataNascita(dataNascita);
                    }
                    break;
                case 4:
                    System.out.println("Indirizzo attuale: " + getIndirizzo());
                    System.out.print("Inserisci il nuovo indirizzo: ");
                    String indirizzo = scanner.nextLine();
                    System.out.print("Seleziona 1 per confermare, 0 per annullare: ");
                    conferma = scanner.nextInt();
                    scanner.nextLine();
                    if(conferma == 1){
                        setIndirizzo(indirizzo);
                    }
                    break;
                case 5: 
                    System.out.println("Email attuale: " + getEmail());
                    System.out.print("Inserisci la nuova email: ");
                    String email = scanner.nextLine();
                    while (!email.contains("@")) {
                        System.out.print("Inserisci un'email valida: ");
                        email = scanner.nextLine();
                    }
                    System.out.print("Seleziona 1 per confermare, 0 per annullare: ");
                    conferma = scanner.nextInt();
                    scanner.nextLine();
                    if(conferma == 1){
                        setEmail(email);
                    }
                    break;
                case 6:
                    System.out.println("Numero di telefono attuale: " + getTelefono());
                    System.out.print("Inserisci il nuovo numero di telefono: ");
                    String numeroTelefono = scanner.nextLine();
                    while (numeroTelefono.length() != 10) {
                        System.out.print("Inserisci un numero di telefono valido: ");
                        numeroTelefono = scanner.nextLine();
                    }
                    System.out.print("Seleziona 1 per confermare, 0 per annullare: ");
                    conferma = scanner.nextInt();
                    scanner.nextLine();
                    if(conferma == 1){
                        setTelefono(numeroTelefono);
                    }
                    break;
                case 0:
                    System.out.println("Modifica cliente terminata.");
                    break;
                default:
                    break;
            }
        }
        while (scelta != 0);
    }

    public String toString() {
        return "Cliente: " + this.codice + "\n" +
                "Nome: " + this.nome + "\n" +
                "Cognome: " + this.cognome + "\n" +
                "Data di nascita: " + this.dataNascita + "\n" +
                "Indirizzo: " + this.indirizzo + "\n" +
                "Email: " + this.email + "\n" +
                "Telefono: " + this.telefono + "\n";
    }

}
