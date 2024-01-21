package com.example;

import java.time.LocalDate;
import java.util.Random;

public abstract class Abbonamento {

    private Integer codice;
    private Float prezzoMensile;
    private Float scontoMensile;
    private LocalDate dataScadenza;
    private Offerta offertaApplicata;

    public Abbonamento(Float scontoMensile, LocalDate dataScadenza) {
        this.codice = new Random().nextInt();
        this.prezzoMensile = 30f;
        this.scontoMensile = scontoMensile;
        this.dataScadenza = dataScadenza;
        this.offertaApplicata = null;
    }

    public Integer getCodice() {
        return this.codice;
    }

    public void setOfferta(Offerta offerta) {
        this.offertaApplicata = offerta;
    }

    public boolean verificaAbbonamento() {
        if (this.dataScadenza.isBefore(LocalDate.now())) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "Prezzo mensile: " + (this.prezzoMensile - this.scontoMensile - ((this.offertaApplicata != null) ? (this.offertaApplicata.getSconto() * (this.prezzoMensile- this.scontoMensile)) : 0)) + "\n" +
                "Data scadenza: " + this.dataScadenza + "\n";
    }
}
