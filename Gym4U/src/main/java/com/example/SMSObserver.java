package com.example;

import java.util.Observable;
import java.util.Observer;

public class SMSObserver implements Observer {
    private Cliente cliente;

    public SMSObserver(Cliente cliente) {
        this.cliente = cliente;
        this.cliente.addObserver(this);
    }

    @Override
    public void update(Observable cliente, Object arg) {
        System.out.println("Invio sms non ancora implementati.");
    }
}
