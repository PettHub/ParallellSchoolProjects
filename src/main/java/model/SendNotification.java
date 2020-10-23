package main.java.model;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

/**
 * @author Christian Lind
 * Used by EmployeeSorter.
 * Sends an email with provided information
 * @since 2020-10-15
 */

public class SendNotification {

    /**
     * Sends a normal email with the provided information
     *
     * @param host     The host to send from eg smtp.gmail.com for gmail
     * @param to       The reciver of the mail eg random@gmail.com
     * @param from     The sender of the mail eg random@gmail.com
     * @param password The password of the sender
     * @param subject  The Subject line of the email
     * @param text     The text of the email
     */
    public SendNotification(String host, String to, String from, String password, String subject, String text) {

        Properties properties = setProperties(host);

        Session session = createSession(properties, from, password);

        sendMessage(session, from, to, subject, text);
    }

    /**
     * Sends a email of missing workshifts
     *
     * @param host                The host to send from eg smtp.gmail.com for gmail
     * @param to                  The reciver of the mail eg random@gmail.com
     * @param from                The sender of the mail eg random@gmail.com
     * @param password            The password of the sender
     * @param subject             The Subject line of the email
     * @param workshiftsNotFilled A list of workshift not filled
     */
    public SendNotification(String host, String to, String from, String password, String subject, List<WorkShift> workshiftsNotFilled) {

        Properties properties = setProperties(host);

        Session session = createSession(properties, from, password);

        StringBuilder sb = new StringBuilder();

        sb.append("There are ").append(workshiftsNotFilled.size()).append(" Workshift(s) not filled with ids");

        for (WorkShift ws : workshiftsNotFilled) {
            sb.append("\n").append(ws.ID);
        }

        sb.append("\nSincerly EmployeeSorter");
        sendMessage(session, from, to, subject, sb.toString());
    }

    private Session createSession(Properties properties, String from, String password) {
        // Get the Session object.// and pass username and password
        return Session.getInstance(properties, new Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(from, password);

            }

        });
    }

    private Properties setProperties(String host) {
        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        return properties;
    }

    private void sendMessage(Session session, String from, String to, String subject, String text) {
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setText(text);

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

}


