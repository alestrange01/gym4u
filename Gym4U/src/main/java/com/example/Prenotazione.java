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

}
