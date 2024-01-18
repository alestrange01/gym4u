package com.example;

import java.time.LocalDate;

public class AbbonamentoMensile extends Abbonamento {

    public AbbonamentoMensile() {
        super(0f, LocalDate.now().plusMonths(1));
    }

    @Override
    public String toString() {
        return "Abbonamento mensile\n" + super.toString();
    }
    
}
