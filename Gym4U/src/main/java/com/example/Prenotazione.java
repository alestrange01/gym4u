package com.example;

import java.util.Random;

public class Prenotazione {

    private Integer codice;
    private Lezione lezione;
    
    public Prenotazione(){
        this.codice = new Random().nextInt();
        this.lezione = null;
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
}
