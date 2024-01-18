package com.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PersonalTrainer {

    private Integer codice;
    private Map<Integer, Corso> corsi;
    private Map<Integer, Lezione> lezioni;

    public PersonalTrainer() {
        this.codice = new Random().nextInt();
        this.corsi = new HashMap<Integer, Corso>();
        this.lezioni = new HashMap<Integer, Lezione>();
    }

    public Integer getCodice() {
        return this.codice;
    }

    public Map<Integer, Corso> getCorsi() {
        return this.corsi;
    }

    public Map<Integer, Lezione> getLezioni() {
        return this.lezioni;
    }

    public void setCorso(Corso c) {
        this.corsi.put(c.getCodiceUnivoco(), c);
    }

    public void setLezione(Lezione l) {
        this.lezioni.put(l.getCodice(), l);
    }

    public String toString() {
        return "Personal Trainer: " + this.codice;
    }
}
