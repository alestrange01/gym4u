package com.example;

import java.time.LocalDate;
import java.util.Random;

public class Offerta {
    private Integer codice;
    private Float sconto;
    private LocalDate dataInizio;
    private LocalDate dataFine;

    public Offerta(Float sconto, LocalDate dataInizio, LocalDate dataFine) {
        this.codice = new Random().nextInt(100000);
        this.sconto = sconto;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }

    public Integer getCodice() {
        return this.codice;
    }

    public Float getSconto() {
        return this.sconto;
    }

    public LocalDate getDataInizio() {
        return this.dataInizio;
    }

    public LocalDate getDataFine() {
        return this.dataFine;
    }

    public String toString() {
        return "Sconto " + String.format("%.0f%%\n", this.sconto * 100) +
                "Data inizio: " + this.dataInizio + "\n" +
                "Data scadenza: " + this.dataFine + "\n";
    }
}
