package com.example;

public class AbbonamentoMensileFactory implements AbbonamentoFactory {

    @Override
    public AbbonamentoMensile creaAbbonamento() {
        return new AbbonamentoMensile();
    }

}
