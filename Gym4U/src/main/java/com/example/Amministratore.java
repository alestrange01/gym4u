package com.example;

public class Amministratore {
    private Integer codice;
    private String password;
    
    public Amministratore(){
        this.codice = 0;
        this.password = "000000";
    }

    public Integer getCodice() {
        return this.codice;
    }

    public Boolean verificaPassword(String password) {
        if(this.password.equals(password)){
            return true;
        }
        return false;
    }
}
