package com.example;

import java.time.LocalDate;
import java.util.Random;

public class CertificatoMedico {

    private Integer codice;
    private LocalDate dataScadenza;

    public CertificatoMedico(LocalDate dataScadenza) {
        this.codice = new Random().nextInt();
        this.dataScadenza = dataScadenza;
    }

    public Integer getCodice() {
        return this.codice;
    }

    public boolean verificaCertificatoMedico() {
        if (dataScadenza.isBefore(LocalDate.now())) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "Data scadenza certificato medico: " + this.dataScadenza + "\n";
    }
}
