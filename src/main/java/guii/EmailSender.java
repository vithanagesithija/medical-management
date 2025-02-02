package guii;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailSender {
    public static void sendEmail(String recipientEmail, String subject, String body) {
        // SMTP Server Information
        String host = "smtp.gmail.com";  // Change if using another SMTP provider
        String port = "587"; // Use "465" for SSL
        final String username = "sithijasulank789@gmail.com"; // Your Gmail username
        final String password = "qcom resh mjyp rhgv"; // Use an App Password if using Gmail

        // SMTP Server Properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // Enable TLS security
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // Authenticate and start a session
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(body);

            // Send the email
            Transport.send(message);
            System.out.println("Email sent successfully to " + recipientEmail);

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Failed to send email.");
        }
    }
}
