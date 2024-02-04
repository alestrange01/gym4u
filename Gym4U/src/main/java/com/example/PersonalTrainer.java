package com.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PersonalTrainer {

    private Integer codice;
    private String nome;
    private String cognome;
    private String password;
    private Map<Integer, Corso> corsi;
    private Map<Integer, Lezione> lezioni;
    private Map<Integer, SchedaPersonalizzata> schedePersonalizzate;
    private Lezione lezioneCorrente;
    private SchedaPersonalizzata schedaPersonalizzataCorrente;

    public PersonalTrainer(String nome, String cognome) {
        this.codice = new Random().nextInt(100000);
        this.nome = nome;
        this.cognome = cognome;
        this.password = "0";
        this.corsi = new HashMap<Integer, Corso>();
        this.lezioni = new HashMap<Integer, Lezione>();
        this.schedePersonalizzate = new HashMap<Integer, SchedaPersonalizzata>();
        this.lezioneCorrente = null;
        this.schedaPersonalizzataCorrente = null;
    }

    public Integer getCodice() {
        return this.codice;
    }

    public String getNome() {
        return this.nome;
    }

    public String getCognome() {
        return this.cognome;
    }

    public Map<Integer, Corso> getCorsi() {
        return this.corsi;
    }

    public Map<Integer, Lezione> getLezioni() {
        return this.lezioni;
    }

    public Map<Integer, SchedaPersonalizzata> getSchedePersonalizzate() {
        return this.schedePersonalizzate;
    }

    public Lezione getLezioneCorrente() {
        return this.lezioneCorrente;
    }

    public SchedaPersonalizzata getSchedaPersonalizzataCorrente() {
        return this.schedaPersonalizzataCorrente;
    }

    public void setCorso(Corso c) {
        this.corsi.put(c.getCodiceUnivoco(), c);
    }

    public void setLezione(Lezione l) {
        this.lezioni.put(l.getCodice(), l);
    }

    public void setSchedaPersonalizzata(SchedaPersonalizzata sp) {
        this.schedePersonalizzate.put(sp.getCodice(), sp);
    }

    public void setLezioneCorrente(Lezione lezione) {
        this.lezioneCorrente = lezione;
    }

    public void setSchedaPersonalizzataCorrente(SchedaPersonalizzata schedaPersonalizzata) {
        this.schedaPersonalizzataCorrente = schedaPersonalizzata;
    }

    public Boolean verificaPassword(String password) {
        if (this.password.equals(password)) {
            return true;
        }
        return false;
    }

    public String toString() {
        return "Personal Trainer: " + this.nome + " " + this.cognome + " - Codice: " + this.codice;
    }
}
