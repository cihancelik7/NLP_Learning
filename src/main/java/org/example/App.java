package org.example;

import org.example.nlp.NLPProcessor;
import org.example.util.ModelManager;
import org.example.handlers.HandlerManager;
import twitter4j.TwitterException;

import java.io.IOException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws IOException , TwitterException {
        // Modelin güncellenmesini sağlayın
        ModelManager.ensureModelUpdated();
        // Modellerin yolları
        String categorizerModelPath = "en-doccat.bin";
        String sentenceModelPath = "en-sent.bin";
        String tokenizerModelPath = "en-token.bin";
        String posModelPath = "en-pos-maxent.bin";
        String lemmatizerModelPath = "en-lemmatizer.bin";

        // Modelin güncellenmesini sağlayın
        ModelManager.ensureModelUpdated();

        // NLPProcessor ve HandlerManager nesnelerini oluşturun
        NLPProcessor nlpProcessor = new NLPProcessor(categorizerModelPath, sentenceModelPath, tokenizerModelPath, posModelPath, lemmatizerModelPath);
        HandlerManager handlerManager = new HandlerManager(nlpProcessor);  // NLPProcessor nesnesini HandlerManager'a geçirin

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please type your message to the bot here:");

        while (scanner.hasNextLine()) {
            String userInput = scanner.nextLine();

            // Kullanıcı girdisini işleyip kategoriye göre yanıt alın
            String response = handlerManager.handleRequest(userInput);
            System.out.println(response);
        }
    }
}
