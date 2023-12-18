package com.example;

import java.util.Random;

public class CertificatoMedico {

    private Integer codice;
    
    public CertificatoMedico(){
        this.codice = new Random().nextInt();
    }

    public Integer getCodice(){
        return this.codice;
    }

    public boolean verificaCertificatoMedico(){
        return true;
    }
}
