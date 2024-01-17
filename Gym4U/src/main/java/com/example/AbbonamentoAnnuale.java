package com.example;

import java.time.LocalDate;

public class AbbonamentoAnnuale extends Abbonamento{
    public AbbonamentoAnnuale() {
        super(10f, LocalDate.now().plusYears(1));
    }
}
