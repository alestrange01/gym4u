package com.example;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continua = true;
        boolean isCliente = false;
        boolean isAmmnistratore = false;
        boolean isIngressoInPalestra = false;
        boolean isPersonalTrainer = false;
        Gym4U gym4u = Gym4U.getInstance();
        Integer codice = 0;

        do {
            do {
                System.out.print("Inserisci il tuo codice: ");
                codice = Integer.parseInt(scanner.next());
                try {
                    isCliente = gym4u.verificaCliente(codice);
                    isAmmnistratore = gym4u.verificaAmministratore(codice);
                    isIngressoInPalestra = gym4u.isIngressoInPalestra(codice);
                    isPersonalTrainer = gym4u.isPersonalTrainer(codice);
                } catch (NumberFormatException e) {
                    System.out.println("Input non valido. Inserisci un numero.");
                }
            } while (!isCliente && !isAmmnistratore && !isIngressoInPalestra && !isPersonalTrainer);
            if (isCliente) {
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
            } else if (isAmmnistratore) {
                System.out.println("**************************************");
                System.out.println("*                                    *");
                System.out.println("*        Menu Amministratore         *");
                System.out.println("* 1. Crea nuovo corso                *");
                System.out.println("* 2. Registrazione nuovo cliente     *");
                System.out.println("* 3. Inserisci offerta promozionale  *");
                System.out.println("* 0. Esci                            *");
                System.out.println("*                                    *");
                System.out.println("**************************************");
            } else if (isIngressoInPalestra) {
                System.out.println("*********************************");
                System.out.println("*                               *");
                System.out.println("*         Menu Cliente          *");
                System.out.println("* 1. Accesso in palestra        *");
                System.out.println("* 0. Esci                       *");
                System.out.println("*                               *");
                System.out.println("*********************************");
            } else if (isPersonalTrainer) {
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
                        if (isCliente)
                            gym4u.iscrizioneCorso(codice);
                        else if (isAmmnistratore)
                            gym4u.creaNuovoCorso();
                        else if (isIngressoInPalestra) {
                            System.out.print("Inserisci il tuo codice badge: ");
                            Integer codiceBadge = Integer.parseInt(scanner.next());
                            gym4u.accessoInPalestraTramiteBadge(codiceBadge);
                        } else if (isPersonalTrainer)
                            gym4u.creazioneDiUnaSchedaPersonalizzata(codice);
                        break;
                    case 2:
                        if (isCliente)
                            gym4u.prenotazioneLezioneCorso(codice);
                        else if (isAmmnistratore)
                            gym4u.registrazioneNuovoCliente();
                        else if (isPersonalTrainer)
                            gym4u.visualizzaPrenotatiDiUnaLezione(codice);
                        break;
                    case 3:
                        if (isCliente)
                            gym4u.prenotazioneLezionePT(codice);
                        else if (isAmmnistratore)
                            gym4u.creazioneDiUnOffertaPromozionale();
                        break;
                    case 4:
                        gym4u.gestioneAbbonamento(codice);
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
