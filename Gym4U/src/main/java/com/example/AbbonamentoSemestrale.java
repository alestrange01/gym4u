package com.example;

import java.time.LocalDate;

public class AbbonamentoSemestrale extends Abbonamento {

    public AbbonamentoSemestrale() {
        super(5f, LocalDate.now().plusMonths(6));
    }

    @Override
    public String toString() {
        return "Abbonamento semestrale\n" + super.toString();
    }

}
