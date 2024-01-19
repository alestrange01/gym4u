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

    public boolean verificaMetodoDiPagamento() {
        return LocalDate.now().isBefore(this.dataScadenza);
    }

    public String toString() {
        return "Metodo di pagamento: \n" +
                "Data scadenza: " + this.dataScadenza + "\n" +
                "Numero carta: " + this.numeroCarta+ "\n";
    }
}
