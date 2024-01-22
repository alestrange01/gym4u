package com.example;

import java.util.Observable;
import java.util.Observer;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailObserver implements Observer {

    private Cliente cliente;
    Session session;

    public MailObserver(Cliente cliente) {
        this.cliente = cliente;
        this.cliente.addObserver(this);
        String host = "smtp.gmail.com";
        String port = "587";
        String username = "gym4unict@gmail.com";
        String password = "hbwfpifvxmbwkzwt";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        this.session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(username, password);
            }
        });
    }

    @Override
    public void update(Observable c, Object arg) {
        Cliente cliente = (Cliente) c;
        Prenotazione prenotazione = (Prenotazione) arg;

        try {
            Message message = new MimeMessage(this.session);
            message.setFrom(new InternetAddress("gym4unict@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(cliente.getEmail()));
            message.setSubject("Nuova prenotazione avvenuta con successo");
            message.setText("Gentile " + cliente.getNome() + " " + cliente.getCognome()+ ",\n" + "è stata confermata la sua prenotazione per la seguente lezione: \n" + prenotazione.getLezione().toString());

            Transport.send(message);

            System.out.println("Mail inviata con successo!");

        } catch (MessagingException e) {
            System.out.println("Errore durante l'invio dell'email.");
            e.printStackTrace();
        }
    }

}
