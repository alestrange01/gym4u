package com.example;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Cliente {

    private Integer codice;
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

    public Cliente(String nome, String cognome, LocalDate dataNascita, String indirizzo, String email,
            String telefono) {
        this.codice = new Random().nextInt();
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
    }

    public Integer getCodice() {
        return this.codice;
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
        return prenotazioni;
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
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }

    public void setSchedaPersonalizzata(SchedaPersonalizzata sp) {
        this.schedaPersonalizzata = sp;
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
