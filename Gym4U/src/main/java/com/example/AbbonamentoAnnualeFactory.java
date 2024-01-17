package com.example;

public class AbbonamentoAnnualeFactory implements AbbonamentoFactory {
    @Override
    public AbbonamentoAnnuale creaAbbonamento() {
        return new AbbonamentoAnnuale();
    }

}
