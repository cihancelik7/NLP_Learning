package org.example.util;

import java.io.File;
import java.io.IOException;

public class ModelManager {
    private static final String CATEGORIZER_MODEL_PATH = "en-doccat.bin";
    private static final String CATEGORIZER_DATA_PATH = "categorizer.txt";

    public static void ensureModelUpdated() throws IOException {
        File modelFile = new File(CATEGORIZER_MODEL_PATH);
        File dataFile = new File(CATEGORIZER_DATA_PATH);

        // model dosyasi yoksa ve ya veri dosyasi model dosyasindan daha yeni degil ise modeli egit
        if (!modelFile.exists() || dataFile.lastModified() > modelFile.lastModified()) {
            System.out.println("Training model because it's missing or outdated...");
            ModelTrainer.trainCategorizerModel(CATEGORIZER_DATA_PATH, CATEGORIZER_MODEL_PATH, 100, 1);
            System.out.println("Model training completed.");
        }
    }
}
