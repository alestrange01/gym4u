package com.example;

public class AbbonamentoSemestraleFactory implements AbbonamentoFactory {

    @Override
    public AbbonamentoSemestrale creaAbbonamento() {
        return new AbbonamentoSemestrale();
    }

}
