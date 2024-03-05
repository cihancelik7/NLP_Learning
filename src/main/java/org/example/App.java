package org.example;

import opennlp.tools.doccat.*;
import opennlp.tools.lemmatizer.LemmatizerME;
import opennlp.tools.lemmatizer.LemmatizerModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.*;
import opennlp.tools.util.model.ModelUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class App {

    private static final String DEFAULT_ANSWER = "I'm sorry, but I did not understand your message. Can you try again with a different word?";

    private static final Map<String, String> RESPONSES = new HashMap<>();

    static {
        RESPONSES.put("greetings", "Hello, how can I help you?");
    RESPONSES.put("price","our product price is : 300$");
    RESPONSES.put("bye","goodbye to you see you bye bye");
    }

    public static void main(String[] args) throws IOException {

        DoccatModel categorizerModel = getCategorizerModel();

        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("Please type your message to the bot here:");

            String userMessage = scanner.nextLine();

            String[] brokenSentences = extractSentences(userMessage);

            for (String sentence : brokenSentences) {

                String[] tokens = extractTokens(sentence);

                String[] pos = getPOSTags(tokens);

                String[] lemmas = extractLemmas(tokens, pos);

                String category = getCategory(categorizerModel,lemmas);

                String response;

                if (!RESPONSES.containsKey(category)) {
                    response = DEFAULT_ANSWER;
                    System.out.println(response);
                    continue;
                }

                response = RESPONSES.get(category);

                System.out.println(response);
            }


        }

    }

    private static DoccatModel getCategorizerModel() throws IOException {

        InputStreamFactory inputStreamFactory = new MarkableFileInputStreamFactory(new File("categorizer.txt"));
        ObjectStream<String> lineObjectStream = new PlainTextByLineStream(inputStreamFactory, StandardCharsets.UTF_8);
        ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineObjectStream);

        DoccatFactory factory = new DoccatFactory(new FeatureGenerator[]{new BagOfWordsFeatureGenerator()});

        TrainingParameters trainingParameters = ModelUtil.createDefaultTrainingParameters();

        trainingParameters.put(TrainingParameters.CUTOFF_PARAM, 0);

        DoccatModel model = DocumentCategorizerME.train("en", sampleStream, trainingParameters, factory);

        return model;

    }

    private static String[] extractSentences(String userInput) {

        String[] sentences = {};

        try (InputStream model = new FileInputStream("en-sent.bin")) {

            SentenceDetectorME sentenceDetectorME = new SentenceDetectorME(new SentenceModel(model));

            sentences = sentenceDetectorME.sentDetect(userInput);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return sentences;

    }

    private static String[] extractTokens(String sentence) {

        String[] tokens = {};

        try (InputStream model = new FileInputStream("en-token.bin")) {

            TokenizerME tokenizerME = new TokenizerME(new TokenizerModel(model));

            tokens = tokenizerME.tokenize(sentence);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tokens;
    }

    private static String[] getPOSTags(String[] tokens) {

        String[] posTags = {};

        try (InputStream model = new FileInputStream("en-pos-maxent.bin")) {

            POSTaggerME posTaggerME = new POSTaggerME(new POSModel(model));

            posTags = posTaggerME.tag(tokens);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return posTags;
    }

    private static String[] extractLemmas(String[] tokens, String[] posTags) {

        String[] lemmas = {};

        try (InputStream model = new FileInputStream("en-lemmatizer.bin")) {

            LemmatizerME lemmatizerME = new LemmatizerME(new LemmatizerModel(model));

            lemmas = lemmatizerME.lemmatize(tokens, posTags);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lemmas;

    }

    private static String getCategory(DoccatModel categorizerModel, String[] lemmas){

        DocumentCategorizerME documentCategorizerME = new DocumentCategorizerME(categorizerModel);

        double[] probabilities = documentCategorizerME.categorize(lemmas);
        return documentCategorizerME.getBestCategory(probabilities);

    }
}
