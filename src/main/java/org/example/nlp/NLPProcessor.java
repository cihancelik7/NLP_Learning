package org.example.nlp;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.lemmatizer.LemmatizerME;
import opennlp.tools.lemmatizer.LemmatizerModel;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class NLPProcessor {

    private DoccatModel categorizerModel;
    private SentenceModel sentenceModel;
    private TokenizerModel tokenizerModel;
    private POSModel posModel;
    private LemmatizerModel lemmatizerModel;
    private TokenNameFinderModel nameFinderModel;

    public NLPProcessor(String categorizerPath, String sentenceModelPath,String nameFinderModelPath, String tokenizerModelPath, String posModelPath, String lemmatizerModelPath) {
        this.categorizerModel = loadModel(categorizerPath, DoccatModel.class);
        this.sentenceModel = loadModel(sentenceModelPath, SentenceModel.class);
        this.tokenizerModel = loadModel(tokenizerModelPath, TokenizerModel.class);
        this.posModel = loadModel(posModelPath, POSModel.class);
        this.lemmatizerModel = loadModel(lemmatizerModelPath, LemmatizerModel.class);
    }
    private <T> T loadModel(String modelPath, Class<T> clazz) {
        try (InputStream modelIn = Files.newInputStream(Paths.get(modelPath))) {
            return clazz.getConstructor(InputStream.class).newInstance(modelIn);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load model: " + modelPath, e);
        }
    }

    public String[] tokenize(String sentence) {
        TokenizerME tokenizer = new TokenizerME(tokenizerModel);
        return tokenizer.tokenize(sentence);
    }

    public String[] detectSentences(String text) {
        SentenceDetectorME sentenceDetector = new SentenceDetectorME(sentenceModel);
        return sentenceDetector.sentDetect(text);
    }

    public String[] posTag(String[] tokens) {
        POSTaggerME posTagger = new POSTaggerME(posModel);
        return posTagger.tag(tokens);
    }

    public String[] lemmatize(String[] tokens, String[] posTags) {
        LemmatizerME lemmatizer = new LemmatizerME(lemmatizerModel);
        return lemmatizer.lemmatize(tokens, posTags);
    }

    public String categorize(String[] tokens) {
        DocumentCategorizerME categorizer = new DocumentCategorizerME(categorizerModel);
        double[] outcomes = categorizer.categorize(tokens);
        return categorizer.getBestCategory(outcomes);
    }

    public String determineCategory(String userInput) {
        String[] tokens = tokenize(userInput);
        return categorize(tokens);
    }
}
