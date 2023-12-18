package com.example;

import java.util.Random;

public class Abbonamento {

    private Integer codice;

    public Abbonamento() {
        this.codice = new Random().nextInt();
    }

    public Integer getCodice(){
        return this.codice;
    }

    public boolean verificaAbbonamento() {
        return true;
    }
}
