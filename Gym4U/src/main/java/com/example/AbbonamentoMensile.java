package com.example;

import java.time.LocalDate;

public class AbbonamentoMensile extends Abbonamento {

    public AbbonamentoMensile() {
        super(0f, LocalDate.now().plusDays(30));
    }

    @Override
    public String toString() {
        return "Abbonamento mensile\n" + super.toString();
    }
    
}
