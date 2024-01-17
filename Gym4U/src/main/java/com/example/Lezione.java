package com.example;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

public class Lezione {

    private Integer codice;
    private LocalDate giorno;
    private LocalTime orario;
    private LezioneEnum tipologiaLezione;


    public Lezione(LocalDate giorno, LocalTime orario, LezioneEnum tipologiaLezione) {
        this.codice = new Random().nextInt();
        this.giorno = giorno;
        this.orario = orario;
        this.tipologiaLezione = tipologiaLezione;
    }

    public Integer getCodice() {
        return this.codice;
    }

    public LocalDate getGiorno() {
        return this.giorno;
    }

    public String toString() {
        return "Codice: " + this.codice + "\n" +
                "Giorno: " + this.giorno + " (" + this.giorno.getDayOfWeek() + ")\n" +
                "Orario: " + this.orario + "\n" + 
                "Tipologia: " + this.tipologiaLezione + "\n";

    }
}
