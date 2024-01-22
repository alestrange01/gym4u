package com.example;

import java.time.DayOfWeek;
import java.time.LocalDate;
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
        this.codiceUnivoco = new Random().nextInt(100000);
        this.nome = nome;
        this.descrizione = descrizione;
        this.giorniDisponibili = giorniDisponibili;
        this.orariDisponibili = orariDisponibili;
        this.durataLezione = durataLezione;
        this.postiDisponibili = postiDisponibili;
        loadLezioni();
        this.idsPersonalTrainer = idsPersonalTrainer;
    }

    public void loadLezioni() {
        if (this.lezioni == null) {
            this.lezioni = new LinkedHashMap<>();
        }
        LocalDate oggi = LocalDate.now();

        for (String giorno : this.giorniDisponibili) {
            DayOfWeek giornoDaAggiungere = DayOfWeek.valueOf(giorno.toUpperCase());

            int giorniDiDifferenza = (giornoDaAggiungere.getValue() - oggi.getDayOfWeek().getValue() + 7) % 7;
            LocalDate dataLezione = oggi.plusDays(giorniDiDifferenza);
            if (this.lezioni.entrySet().stream().anyMatch(entry -> entry.getValue().getGiorno().equals(dataLezione))) {
                continue;
            }

            for (LocalTime ora : this.orariDisponibili) {
                Lezione lezione = new Lezione(dataLezione, ora, durataLezione, LezioneEnum.LezioneCorso);
                this.lezioni.put(lezione.getCodice(), lezione);
            }

        }
    }

    public Corso getCorso() {
        return this;
    }

    public Integer getCodiceUnivoco() {
        return this.codiceUnivoco;
    }

    public Map<Integer, Lezione> getLezioni() {
        return this.lezioni;
    }

    public Integer getDisponibilità() {
        return this.postiDisponibili;
    }

    public List<Integer> getIdsPersonalTrainer() {
        return this.idsPersonalTrainer;
    }

    public void diminuisciDisponibilità() {
        if (this.postiDisponibili > 0)
            this.postiDisponibili--;
    }
    
    public Lezione selezionaLezione(Integer codice) {
        return this.lezioni.get(codice);
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