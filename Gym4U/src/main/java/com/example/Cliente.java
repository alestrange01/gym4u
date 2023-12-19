package com.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Cliente {

    private Integer codice;
    private Abbonamento abbonamento;
    private CertificatoMedico certificatoMedico;
    private Map<Integer, Corso> corsi;
    private Map<Integer, Prenotazione> prenotazioni;

    public Cliente() {
        this.codice = new Random().nextInt();
        this.corsi = new HashMap<Integer, Corso>();
        this.prenotazioni = new HashMap<Integer, Prenotazione>();
        this.certificatoMedico = null;
        this.abbonamento = null;
    }

    public Integer getCodice() {
        return this.codice;
    }

    public Map<Integer, Corso> getCorsi() {
        return this.corsi;
    }

    public Map<Integer, Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }

    public void createCertificatoMedico() {
        setCertificatoMedico(new CertificatoMedico());
    }

    public void createAbbonamento() {
        setAbbonamento(new Abbonamento());
    }

    public void setAbbonamento(Abbonamento abbonamento) {
        this.abbonamento = abbonamento;
    }

    public void setCertificatoMedico(CertificatoMedico certificatoMedico) {
        this.certificatoMedico = certificatoMedico;
    }

    public void setPrenotazione(Prenotazione p) {
        this.prenotazioni.put(p.getCodice(), p);
    }

    public void setCorso(Corso c) {
        this.corsi.put(c.getCodiceUnivoco(), c);
    }

    public boolean verificaCertificatoMedico() {
        return this.certificatoMedico.verificaCertificatoMedico();
    }

    public boolean verificaAbbonamento() {
        return this.abbonamento.verificaAbbonamento();
    }

}
