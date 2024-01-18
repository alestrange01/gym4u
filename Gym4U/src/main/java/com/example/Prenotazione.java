package com.example;

import java.util.Random;

public class Prenotazione {

    private Integer codice;
    private Lezione lezione;
    private Boolean validata;
    
    public Prenotazione(){
        this.codice = new Random().nextInt();
        this.lezione = null;
        this.validata = false;
    }

    public Integer getCodice(){
        return this.codice;
    }

    public Lezione getLezione(){
        return this.lezione;
    }

    public void setLezione(Lezione l){
        this.lezione = l;
    }

    public void setValidata(){
        this.validata = true;
    }
}
