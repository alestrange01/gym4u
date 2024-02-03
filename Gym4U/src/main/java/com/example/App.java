package com.example;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = null;
        Gym4U gym4u = Gym4U.getInstance();
        Integer cod = 0;
        Integer codiceUtente = -1;

        do {
            do {
                if(cod == 0){
                    scanner = new Scanner(System.in);
                    do{
                        codiceUtente = -1;
                        System.out.print("Inserisci il tuo codice (q per uscire): ");
                        String input = scanner.nextLine();
                        if(input.equals("q")){
                            System.out.println("Arrivederci!");
                            scanner.close();
                            System.exit(0);
                        }
                        try {
                            codiceUtente = Integer.parseInt(input);
                        } catch (NumberFormatException e) {
                            System.out.println("Input non valido. Inserisci un numero.");
                        }
                    }while(codiceUtente == -1);
                    String password = "";
                    if (codiceUtente != 1){
                        System.out.print("Inserisci la tua password: ");
                        password = scanner.nextLine();
                    }
                    try {
                        cod = gym4u.verificaUtente(codiceUtente, password);
                    } catch (NumberFormatException e) {
                        System.out.println("Input non valido. Inserisci un numero.");
                    }
                }
                if (cod == 0) {
                    System.out.println("Codice o password errati. Riprova.");
                }
            } while (cod == 0);
            if (cod == 1) {
                System.out.println("*****************************************");
                System.out.println("*                                       *");
                System.out.println("*         Menu Cliente                  *");
                System.out.println("* 1. Iscrizione Corso                   *");
                System.out.println("* 2. Prenotazione Lezione Corso         *");
                System.out.println("* 3. Prenotazione Lezione PT            *");    
                System.out.println("* 4. Gestione Abbonamento               *");
                System.out.println("* 5  Visualizza Scheda Personalizzata   *");
                System.out.println("* 6. Visualizza Corsi Palestra          *");
                System.out.println("* 7. Modifica Account e Password        *");
                System.out.println("* 0. Esci                               *");
                System.out.println("*                                       *");
                System.out.println("*****************************************");
            } else if (cod == 2) {
                System.out.println("**************************************");
                System.out.println("*                                    *");
                System.out.println("*        Menu Amministratore         *");
                System.out.println("* 1. Crea nuovo Corso                *");
                System.out.println("* 2. Registrazione nuovo Cliente     *");
                System.out.println("* 3. Inserisci Offerta Promozionale  *");
                System.out.println("* 4. Modifica Cliente                *");
                System.out.println("* 5. Aggiungi nuovo Personal Trainer *");
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
                System.out.println("*                                         *");
                System.out.println("*          Menu Personal Trainer          *");
                System.out.println("* 1. Inserisci Scheda Personalizzata      *");
                System.out.println("* 2. Visualizza prenotati ad una Lezione  *");
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
                        else System.out.println("Opzione non valida. Riprova.");
                        break;
                    case 3:
                        if (cod == 1)
                            gym4u.prenotazioneLezionePT(codiceUtente);
                        else if (cod == 2)
                            gym4u.creazioneDiUnOffertaPromozionale();
                        else System.out.println("Opzione non valida. Riprova.");

                        break;
                    case 4:
                        if (cod == 1)
                            gym4u.gestioneAbbonamento(codiceUtente);
                        else if (cod == 2)
                            gym4u.modificaClienteAdmin();
                        else System.out.println("Opzione non valida. Riprova.");
                        break;
                    case 5:
                        if (cod == 1)
                            gym4u.visualizzaSchedaPersonalizzata(codiceUtente);
                        else if (cod == 2)
                            gym4u.creaPersonalTrainer();
                        else System.out.println("Opzione non valida. Riprova.");
                        break;
                    case 6:
                        if (cod == 1)
                            gym4u.visualizzaCorsi();
                        else System.out.println("Opzione non valida. Riprova.");
                        break;
                    case 7:
                        if (cod == 1)
                            gym4u.modificaAccountPassword(codiceUtente);
                        else System.out.println("Opzione non valida. Riprova.");
                        break;
                        case 8:
                        if (cod == 1)
                            gym4u.modificaCliente(codiceUtente);
                        else System.out.println("Opzione non valida. Riprova.");
                        break;
                    case 0:
                        cod = 0;
                        break;
                    default:
                        System.out.println("Opzione non valida. Riprova.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input non valido. Inserisci un numero.");
            }
        } while (true);
    }
}
