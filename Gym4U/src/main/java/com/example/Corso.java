package com.example;

import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Corso {

    private Integer codiceUnivoco;
    private String nome;
    private String descrizione;
    private List<String> giorniDisponibili;
    private List<LocalTime> orariDisponibili;
    private float durataLezione;
    private Integer postiDisponibili;
    private Map<Integer, Lezione> lezioni;
    private List<Integer> idsPersonalTrainer;

    public Corso(String nome, String descrizione,
            List<String> giorniDisponibili, List<LocalTime> orariDisponibili,
            float durataLezione, Integer postiDisponibili, List<Integer> idsPersonalTrainer) {
        this.codiceUnivoco = new Random().nextInt();
        this.nome = nome;
        this.descrizione = descrizione;
        this.giorniDisponibili = giorniDisponibili;
        this.orariDisponibili = orariDisponibili;
        this.durataLezione = durataLezione;
        this.postiDisponibili = postiDisponibili;
        this.lezioni = loadLezioni(this.giorniDisponibili, this.orariDisponibili);
        this.idsPersonalTrainer = idsPersonalTrainer;
    }

    private Map<Integer, Lezione> loadLezioni(List<String> giorniDisponibili, List<LocalTime> orariDisponibili) {
        Map<Integer, Lezione> lezioni = new LinkedHashMap<Integer, Lezione>();
        for (String giorno : giorniDisponibili) {
            for (LocalTime localTime : orariDisponibili) {
                Lezione lezione = new Lezione(giorno, localTime);
                lezioni.put(lezione.getCodice(), lezione);
            }
        }
        return lezioni;
    }

    public Integer getCodiceUnivoco() {
        return this.codiceUnivoco;
    }

    public Corso getCorso() {
        return this;
    }

    public Map<Integer, Lezione> getLezioni() {
        return this.lezioni;
    }

    public Lezione selezionaLezione(Integer codice) {
        return this.lezioni.get(codice);
    }

    public List<Integer> getIdsPersonalTrainer() {
        return this.idsPersonalTrainer;
    }

    public Integer getDisponibilità() {
        return this.postiDisponibili;
    }

    public void diminuisciDisponibilità() {
        if (this.postiDisponibili > 0)
            this.postiDisponibili--;
    }

    public String toString() {
        return this.codiceUnivoco + " " + this.nome + " " + this.descrizione;
    }

    public String getTotalInfo() {
        return "Codice: " + this.codiceUnivoco + "\n" +
                "Nome: " + this.nome + "\n" +
                "Descrizione: " + this.descrizione + "\n" +
                "Giorni disponibili: " + this.giorniDisponibili + "\n" +
                "Orari disponibili: " + this.orariDisponibili + "\n" +
                "Durata lezione: " + this.durataLezione + "\n" +
                "Posti disponibili: " + this.postiDisponibili + "\n";

    }
}