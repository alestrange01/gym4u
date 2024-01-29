package com.example;

import java.time.LocalDate;
import java.util.Random;

public class MetodoDiPagamento {
    private Integer codice;
    private Integer numeroCarta;
    private LocalDate dataScadenza;

    public MetodoDiPagamento(Integer numeroCarta, LocalDate dataScadenza) {
        this.codice = new Random().nextInt(100000);
        this.numeroCarta = numeroCarta;
        this.dataScadenza = dataScadenza;
    }

    public Integer getCodice() {
        return this.codice;
    }

    public LocalDate getDataScadenza() {
        return this.dataScadenza;
    }

    public boolean verificaMetodoDiPagamento() {
        return LocalDate.now().isBefore(this.dataScadenza);
    }

    public String toString() {
        return "Metodo di pagamento: \n" +
                "Data scadenza: " + this.dataScadenza + "\n" +
                "Numero carta: " + this.numeroCarta + "\n";
    }
}
