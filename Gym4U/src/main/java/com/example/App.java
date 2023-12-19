package com.example;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continua = true;
        boolean isCliente = false;
        boolean isAmmnistratore = true;
        Gym4U gym4u = Gym4U.getInstance();
        Integer codice = 0;

        do {
            do {
                isCliente = false;
                System.out.print("Inserisci il tuo codice: ");
                try {
                    codice = Integer.parseInt(scanner.next());
                    isCliente = gym4u.verificaCliente(codice);
                    // isAmmnistratore = gym4u.verificaAmministratore(codiceCliente);
                } catch (NumberFormatException e) {
                    System.out.println("Input non valido. Inserisci un numero.");
                }
            } while (!isCliente && !isAmmnistratore);
            if (isCliente) {
                System.out.println("*********************************");
                System.out.println("*                               *");
                System.out.println("*         Menu Cliente          *");
                System.out.println("* 1. Iscrizione corso           *");
                System.out.println("* 2. Prenotazione lezione corso *");
                System.out.println("* 0. Esci                       *");
                System.out.println("*                               *");
                System.out.println("*********************************");
            } else if (isAmmnistratore) {
                System.out.println("*******************************");
                System.out.println("*                             *");
                System.out.println("*      Menu Amministratore    *");
                System.out.println("* 1. Crea nuovo corso         *");
                System.out.println("* 0. Esci                     *");
                System.out.println("*                             *");
                System.out.println("*******************************");
            }

            System.out.print("Inserisci il numero dell'opzione desiderata: ");

            String input = scanner.next();

            try {
                int scelta = Integer.parseInt(input);
                switch (scelta) {
                    case 1:
                        if (isCliente)
                            gym4u.iscrizioneCorso(codice);
                        else if (isAmmnistratore)
                            gym4u.creaNuovoCorso();
                        break;
                    case 2:
                        if (isCliente)
                            gym4u.prenotazioneLezioneCorso(codice);
                        break;
                    case 0:
                        continua = false;
                        break;
                    default:
                        System.out.println("Opzione non valida. Riprova.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input non valido. Inserisci un numero.");
            }

        } while (continua);

        scanner.close();
    }

}
