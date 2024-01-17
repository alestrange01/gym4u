package com.example;

import java.time.LocalDate;

public class MetodoDiPagamento {
    private Integer numeroCarta;
    private LocalDate dataScadenza;

    public MetodoDiPagamento(Integer numeroCarta, LocalDate dataScadenza) {
        this.numeroCarta = numeroCarta;
        this.dataScadenza = dataScadenza;
    }

    public LocalDate getDataScadenza() {
        return this.dataScadenza;
    }

    public String toString() {
        return "Data scadenza: " + this.dataScadenza + "\n" +
                "Numero carta: " + this.numeroCarta+ "\n";
    }
}
