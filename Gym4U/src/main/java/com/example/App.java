package com.example;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Gym4U gym4u = Gym4U.getInstance();
        Boolean continua = true;
        Integer cod = 0;
        Integer codiceUtente = 0;

        do {
            do {
                System.out.print("Inserisci il tuo codice: ");
                codiceUtente = Integer.parseInt(scanner.next());
                System.out.print("Inserisci la tua password: ");
                String password = scanner.nextLine();
                try {
                    cod = gym4u.verificaUtente(codiceUtente, password);
                } catch (NumberFormatException e) {
                    System.out.println("Input non valido. Inserisci un numero.");
                }
            } while (cod == 0);
            if (cod == 1) {
                System.out.println("*********************************");
                System.out.println("*                               *");
                System.out.println("*         Menu Cliente          *");
                System.out.println("* 1. Iscrizione corso           *");
                System.out.println("* 2. Prenotazione lezione corso *");
                System.out.println("* 3. Prenotazione lezione PT    *");
                System.out.println("* 4. Gestione Abbonamento       *");
                System.out.println("* 0. Esci                       *");
                System.out.println("*                               *");
                System.out.println("*********************************");
            } else if (cod == 2) {
                System.out.println("**************************************");
                System.out.println("*                                    *");
                System.out.println("*        Menu Amministratore         *");
                System.out.println("* 1. Crea nuovo corso                *");
                System.out.println("* 2. Registrazione nuovo cliente     *");
                System.out.println("* 3. Inserisci offerta promozionale  *");
                System.out.println("* 0. Esci                            *");
                System.out.println("*                                    *");
                System.out.println("**************************************");
            } else if (cod == 3) {
                System.out.println("*********************************");
                System.out.println("*                               *");
                System.out.println("*         Menu Cliente          *");
                System.out.println("* 1. Accesso in palestra        *");
                System.out.println("* 0. Esci                       *");
                System.out.println("*                               *");
                System.out.println("*********************************");
            } else if (cod == 4) {
                System.out.println("*******************************************");
                System.out.println("*                                       *");
                System.out.println("*          Menu Personal Trainer          *");
                System.out.println("* 1. Inserisci scheda personalizzata      *");
                System.out.println("* 2. Visualizza prenotati ad una lezione  *");
                System.out.println("* 0. Esci                                 *");
                System.out.println("*                                         *");
                System.out.println("*******************************************");
            }
            System.out.print("Inserisci il numero dell'opzione desiderata: ");

            String input = scanner.next();

            try {
                int scelta = Integer.parseInt(input);
                switch (scelta) {
                    case 1:
                        if (cod == 1)
                            gym4u.iscrizioneCorso(codiceUtente);
                        else if (cod == 2)
                            gym4u.creaNuovoCorso();
                        else if (cod == 3) {
                            System.out.print("Inserisci il tuo codice badge: ");
                            Integer codiceBadge = Integer.parseInt(scanner.next());
                            gym4u.accessoInPalestraTramiteBadge(codiceBadge);
                        } else if (cod == 4)
                            gym4u.creazioneDiUnaSchedaPersonalizzata(codiceUtente);
                        break;
                    case 2:
                        if (cod == 1)
                            gym4u.prenotazioneLezioneCorso(codiceUtente);
                        else if (cod == 2)
                            gym4u.registrazioneNuovoCliente();
                        else if (cod == 4)
                            gym4u.visualizzaPrenotatiDiUnaLezione(codiceUtente);
                        break;
                    case 3:
                        if (cod == 1)
                            gym4u.prenotazioneLezionePT(codiceUtente);
                        else if (cod == 2)
                            gym4u.creazioneDiUnOffertaPromozionale();
                        break;
                    case 4:
                        if (cod == 1)
                            gym4u.gestioneAbbonamento(codiceUtente);
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
