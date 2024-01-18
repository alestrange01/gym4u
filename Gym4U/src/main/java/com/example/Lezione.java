package com.example;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

public class Lezione {

    private Integer codice;
    private LocalDate giorno;
    private LocalTime orario;
    private Float durata;
    private LezioneEnum tipologiaLezione;

    public Lezione(LocalDate giorno, LocalTime orario, Float durata, LezioneEnum tipologiaLezione) {
        this.codice = new Random().nextInt();
        this.giorno = giorno;
        this.orario = orario;
        this.durata = durata;
        this.tipologiaLezione = tipologiaLezione;
    }

    public Integer getCodice() {
        return this.codice;
    }

    public LocalDate getGiorno() {
        return this.giorno;
    }

    public LocalTime getOrario() {
        return this.orario;
    }

    public Float getDurata() {
        return this.durata;
    }

    public String toString() {
        return "Codice: " + this.codice + "\n" +
                "Giorno: " + this.giorno + " (" + this.giorno.getDayOfWeek() + ")\n" +
                "Orario: " + this.orario + "\n" +
                "Durata: " + this.durata + "\n" +
                "Tipologia: " + this.tipologiaLezione + "\n";

    }
}
