package com.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PersonalTrainer {

    private Integer codice;
    private String password;
    private Map<Integer, Corso> corsi;
    private Map<Integer, Lezione> lezioni;
    private Map<Integer, SchedaPersonalizzata> schedePersonalizzate;

    public PersonalTrainer() {
        this.codice = new Random().nextInt(0, 100000);
        this.password = "0";
        this.corsi = new HashMap<Integer, Corso>();
        this.lezioni = new HashMap<Integer, Lezione>();
        this.schedePersonalizzate = new HashMap<Integer, SchedaPersonalizzata>();
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


    public Map<Integer, SchedaPersonalizzata> getSchedePersonalizzate(){
        return this.schedePersonalizzate;
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

    public Boolean verificaPassword(String password) {
        if(this.password.equals(password)){
            return true;
        }
        return false;
    }

    public String toString() {
        return "Personal Trainer: " + this.codice;
    }
}
