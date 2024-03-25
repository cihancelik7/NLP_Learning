package org.example.email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {
    public static boolean sendEmail(String recipient, String subject, String content) {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", EmailConfig.SMTP_HOST);
        properties.put("mail.smtp.port", EmailConfig.SMTP_PORT);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(EmailConfig.USERNAME, EmailConfig.PASSWORD);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EmailConfig.USERNAME));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);
            System.out.println("E-posta başarıyla gönderildi.");
            return true;
        } catch (MessagingException mex) {
            mex.printStackTrace();
            return false;
        }
    }
}
