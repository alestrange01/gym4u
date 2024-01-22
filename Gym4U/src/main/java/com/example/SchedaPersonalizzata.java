package com.example;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class SchedaPersonalizzata {
    private Integer codice;
    private List<String> esercizi;
    private LocalDate dataInizio;
    private LocalDate dataFine;

    public SchedaPersonalizzata(List<String> esercizi, LocalDate dataFine){
        this.codice = new Random().nextInt(100000);
        this.esercizi = esercizi;
        this.dataInizio = LocalDate.now();
        this.dataFine = dataFine;
    }

    public Integer getCodice(){
        return this.codice;
    }


    public List<String> getEsercizi(){
        return this.esercizi;
    }


    public LocalDate getDataInizio(){
        return this.dataInizio;
    }


    public LocalDate getDataFine(){
        return this.dataFine;
    }

    public String toString() {
        return "Esercizi: " + (this.esercizi.toString()) + "\n" +
                "Data inizio: " + this.dataInizio + "\n"+ 
                "Data scadenza: " + this.dataFine + "\n";
    }
}
