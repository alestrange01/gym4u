package com.example;

import java.util.Random;

public class Badge {
    Integer codice;

    public Badge() {
        this.codice = new Random().nextInt(0, 100000);
    }

    public Integer getCodice() {
        return this.codice;
    }

    public String toString() {
        return "Badge numero: " + this.codice;
    }
}
