package org.example.email;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import java.util.Properties;
import java.util.regex.Pattern;

public class EmailReader {
    public static void readEmails() {
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imaps.host", EmailConfig.IMAP_HOST);
        properties.put("mail.imaps.port", EmailConfig.IMAP_PORT);

        try {
            Session emailSession = Session.getDefaultInstance(properties);
            Store store = emailSession.getStore("imaps");
            store.connect(EmailConfig.IMAP_HOST, EmailConfig.USERNAME, EmailConfig.PASSWORD);

            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            Message[] messages = emailFolder.getMessages();
            int numberOfMessagesToRead = Math.min(messages.length, 10); // To prevent IndexOutOfBoundsException

            for (int i = messages.length - 1; i >= messages.length - numberOfMessagesToRead; i--) {
                Message message = messages[i];
                System.out.println("-------------------------");
                System.out.println("Email Number: " + (i + 1));
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);

                // Process the content more carefully to avoid displaying raw HTML
                String content = getTextFromMessage(message);
                System.out.println("Content: " + content.trim()); // Trim to remove leading/trailing whitespace
            }
            emailFolder.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Extract text from the message content (handling both text and HTML parts)
    private static String getTextFromMessage(Message message) throws Exception {
        if (message.isMimeType("text/plain")) {
            return message.getContent().toString().replaceAll("\\s+", " ");
        } else if (message.isMimeType("text/html")) {
            String html = (String) message.getContent();
            String textOnly = html.replaceAll("<[^>]*>", ""); // Strip out HTML tags
            return textOnly.replaceAll("\\s+", " "); // Replace multiple spaces with a single space
        } else if (message.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) message.getContent();
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                String partContent = getTextFromMessage(bodyPart);
                if (partContent != null) {
                    return partContent.replaceAll("\\s+", " "); // Normalize space in parts content
                }
            }
        }
        return "";
    }

    // Helper method to handle BodyPart as Message
    private static String getTextFromMessage(BodyPart bodyPart) throws Exception {
        if (bodyPart.isMimeType("text/plain")) {
            return ((String) bodyPart.getContent()).replaceAll("\\s+", " ");
        } else if (bodyPart.isMimeType("text/html")) {
            String html = (String) bodyPart.getContent();
            String textOnly = html.replaceAll("<[^>]*>", ""); // Strip out HTML tags
            return textOnly.replaceAll("\\s+", " "); // Normalize space
        }
        return null;
    }
}
