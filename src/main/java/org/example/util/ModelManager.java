package org.example.util;

import java.io.File;
import java.io.IOException;
/*
ModelManager sınıfı, kategorizasyon modelinin güncel olup
 olmadığını kontrol eder ve gerekirse modeli eğitir. Model
 dosyasının var olup olmadığını veya veri dosyasının son değişiklik
 tarihine göre modelin güncellenip güncellenmeyeceğini kontrol eder.
 Eğer model dosyası yoksa veya veri dosyası daha yeni ise, model eğitim sürecini başlatır.
 */
public class ModelManager {
    private static final String CATEGORIZER_MODEL_PATH = "en-doccat.bin";
    private static final String CATEGORIZER_DATA_PATH = "categorizer.txt";

    // Modelin güncellenmesi gerekip gerekmediğini kontrol eder ve gerekirse modeli eğitir.
    public static void ensureModelUpdated() throws IOException {
        File modelFile = new File(CATEGORIZER_MODEL_PATH);
        File dataFile = new File(CATEGORIZER_DATA_PATH);

        // Eğer model dosyası yoksa veya veri dosyası modelden daha yeni ise, modeli eğit.
        if (!modelFile.exists() || dataFile.lastModified() > modelFile.lastModified()) {
            System.out.println("Training model because it's missing or outdated...");
            ModelTrainer.trainCategorizerModel(CATEGORIZER_DATA_PATH, CATEGORIZER_MODEL_PATH, 100, 1);
            System.out.println("Model training completed.");
        }
    }
}
