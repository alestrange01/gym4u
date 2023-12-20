package com.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PersonalTrainer {

    private Integer codice;
    private Map<Integer, Corso> corsi;

    public PersonalTrainer() {
        this.codice = new Random().nextInt();
        this.corsi = new HashMap<Integer, Corso>();
    }

    public Integer getCodice(){
        return this.codice;
    }

    public Map<Integer, Corso> getCorsi(){
        return this.corsi;
    }

    public void setCorso(Corso c) {
        this.corsi.put(c.getCodiceUnivoco(), c);
    }

    public String toString(){
        return "Personal Trainer: " + this.codice;
    }
}
