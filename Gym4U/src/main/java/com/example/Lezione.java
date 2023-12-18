package com.example;

import java.time.LocalTime;
import java.util.Random;

public class Lezione {

    private Integer codice;
    private String giorno;
    private LocalTime orario;

    public Lezione(String giorno, LocalTime orario) {
        this.codice = new Random().nextInt();
        this.giorno = giorno;
        this.orario = orario;
    }

    public Integer getCodice() {
        return this.codice;
    }

    public String toString() {
        return "Codice: " + this.codice + "\n" +
                "Giorno: " + this.giorno + "\n" +
                "Orario: " + this.orario + "\n";
    }
}
