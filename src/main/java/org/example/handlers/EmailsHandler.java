package org.example.handlers;

import org.example.email.EmailReader;
import org.example.email.EmailService;

import java.util.Scanner;

public class EmailsHandler implements ResponseHandler{
    private enum State{
        INITIAL,
        AWAITING_RECIPIENT,
        AWAITING_SUBJECT,
        AWAITING_CONTENT,
        CONFIRMATION,
        READ_EMAILS,
        COMPLETED
    }
    private State currentState = State.INITIAL;
    private String recipient = "";
    private String subject = "";
    private String content = "";
    @Override
    public String handleResponse(String input) {
        Scanner scanner = new Scanner(System.in);

        while (currentState != State.COMPLETED){
            switch (currentState){
                case INITIAL:
                    System.out.println("Do you want to send an email or read your emails? (send,read): ");
                    String action = scanner.nextLine();
                    if("send".equalsIgnoreCase(action)){
                        currentState = State.AWAITING_RECIPIENT;
                    }else if ("read".equalsIgnoreCase(action)){
                        currentState = State.READ_EMAILS;
                    }

                    break;
                case READ_EMAILS:
                    EmailReader.readEmails();
                    currentState = State.COMPLETED;
                    break;
                case AWAITING_RECIPIENT:
                    System.out.println("please enter the recipient's email address: ");
                    recipient = scanner.nextLine();
                    currentState = State.AWAITING_SUBJECT;
                    break;
                case AWAITING_SUBJECT:
                    System.out.println("Please enter the email's subject: ");
                    subject = scanner.nextLine();
                    currentState = State.AWAITING_CONTENT;
                    break;
                case AWAITING_CONTENT:
                    System.out.println("Please enter the email content: ");
                    content = scanner.nextLine()+" this e-mail sent from chatBot, it was developed for Marmara University MIS Project";
                    currentState = State.CONFIRMATION;
                    break;
                case CONFIRMATION:
                    System.out.println("Email to be sent: \nRecipient: "+recipient+"\nSubject: "+subject+"\nContent:\n"+content);
                    System.out.println("Type 'yes' to send the mail, or any other key to cancel.");
                     String confirmation = scanner.nextLine();
                     if ("yes".equalsIgnoreCase(confirmation)){
                         boolean isSent = EmailService.sendEmail(recipient,subject,content);
                         if (isSent){
                             System.out.println("Email was successfully sent.");
                         }else {
                             System.out.println("Email could not be sent.");
                         }
                     }else {
                         System.out.println("Email sending was cancelled.");
                     }
                     currentState = State.COMPLETED;
                     break;

            }
        }
        if (currentState == State.COMPLETED){
            currentState = State.INITIAL;
            recipient = "";
            subject = "";
            content = "";
        }
        return "Email sending process completed.";

    }
}
