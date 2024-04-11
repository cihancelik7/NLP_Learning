
package org.example.nlp;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.lemmatizer.LemmatizerME;
import opennlp.tools.lemmatizer.LemmatizerModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
/*
        NLPProcessor sınıfı, metin işleme ve doğal dil işleme (NLP) görevleri için OpenNLP kütüphanesini kullanır.
        Bu sınıf, metin kategorizasyonu, cümle tespiti, tokenizasyon, kelime türü etiketleme (POS tagging),
        ve lemmatizasyon işlevlerini sağlar. Her bir işlev, ilgili OpenNLP modelini kullanarak gerçekleştirilir.
        Kullanıcı girişlerini işleyerek, belirli kelimeleri içeriyorsa önceden belirlenmiş kategorilere ayırmak
        için bir yöntem içerir.
 */

public class NLPProcessor {
    private static final Logger logger = LoggerFactory.getLogger(NLPProcessor.class);
    // Model değişkenlerinin tanımlanması
    private DoccatModel categorizerModel;
    private SentenceModel sentenceModel;
    private TokenizerModel tokenizerModel;
    private POSModel posModel;
    private LemmatizerModel lemmatizerModel;

    // Constructor metod: Model dosya yollarını alır ve modelleri yükler
    public NLPProcessor(String categorizerPath, String sentenceModelPath, String tokenizerModelPath, String posModelPath, String lemmatizerModelPath) {
        this.categorizerModel = loadModel(categorizerPath, DoccatModel.class);
        this.sentenceModel = loadModel(sentenceModelPath, SentenceModel.class);
        this.tokenizerModel = loadModel(tokenizerModelPath, TokenizerModel.class);
        this.posModel = loadModel(posModelPath, POSModel.class);
        this.lemmatizerModel = loadModel(lemmatizerModelPath, LemmatizerModel.class);
        logger.debug("Initializing NLPProcessor with model paths: {}", categorizerPath);
    }

    // Model yükleme işlevi: Verilen yoldan modeli yükler
    private <T> T loadModel(String modelPath, Class<T> clazz) {
        try (InputStream modelIn = Files.newInputStream(Paths.get(modelPath))) {
            return clazz.getConstructor(InputStream.class).newInstance(modelIn);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load model: " + modelPath, e);
        }
    }

    // Cümle tokenizasyonu: Bir cümleyi tokenlara ayırır
    public String[] tokenize(String sentence) {
        TokenizerME tokenizer = new TokenizerME(tokenizerModel);
        return tokenizer.tokenize(sentence);
    }

    // Cümle tespiti: Metindeki cümleleri tespit eder
    public String[] detectSentences(String text) {
        SentenceDetectorME sentenceDetector = new SentenceDetectorME(sentenceModel);
        return sentenceDetector.sentDetect(text);
    }

    // Kelime türü etiketleme: Tokenların kelime türlerini etiketler
    public String[] posTag(String[] tokens) {
        POSTaggerME posTagger = new POSTaggerME(posModel);
        return posTagger.tag(tokens);
    }

    // Lemmatizasyon: Tokenların köklerini bulur
    public String[] lemmatize(String[] tokens, String[] posTags) {
        LemmatizerME lemmatizer = new LemmatizerME(lemmatizerModel);
        return lemmatizer.lemmatize(tokens, posTags);
    }

    // Kategori tahmini: Tokenlar kullanılarak metnin kategorisini tahmin eder
    public String categorize(String[] tokens) {
        DocumentCategorizerME categorizer = new DocumentCategorizerME(categorizerModel);
        double[] outcomes = categorizer.categorize(tokens);
        return categorizer.getBestCategory(outcomes);
    }

    // Kullanıcı girişine göre kategori belirleme: Özel anahtar kelimelere göre kategori döndürür
    public String determineCategory(String userInput) {
        logger.info("Determining category for userInput: {}", userInput);

        // Özel anahtar kelimelere göre kategori belirleme
        if (userInput.toLowerCase().contains("time")) {
            return "time";
        } else if (userInput.toLowerCase().contains("date")) {
            return "date";
        } else if (userInput.toLowerCase().contains("sports")) {
            return "sports";
        } else if (userInput.toLowerCase().contains("tweet") || userInput.toLowerCase().contains("twitter"))
            return "twitter";
        else if (userInput.toLowerCase().contains("email"))
            return "email";

        // Anahtar kelime eşleşmesi olmadığında, metni tokenize edip kategori tahmini yapar
        String[] tokens = tokenize(userInput);
        String category = categorize(tokens);
        logger.info("Determined category: {}", category); // Tahmin edilen kategoriyi loglar
        return category;
    }

}
