package org.example.util;

import opennlp.tools.doccat.*;
import opennlp.tools.util.*;
import opennlp.tools.util.model.ModelUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
/*
ModelTrainer sınıfı, OpenNLP kütüphanesini kullanarak belge kategorizasyonu (doccat)
modelini eğitmek için tasarlanmıştır. Bu sınıf, kullanıcının belirlediği veri setiyle
bir model eğitir ve bu modeli daha sonra kullanılmak üzere bir dosyaya kaydeder. Eğitim
süreci, maksimum entropi (MaxEnt) algoritmasını kullanarak gerçekleştirilir. Eğitim parametreleri
 olarak iterasyon sayısı ve cutoff değeri kabul eder.
 */

// ModelTrainer: OpenNLP ile belge kategorizasyonu modelini eğitmek için kullanılan sınıf.
public class ModelTrainer {

    // Categorizer modelini eğitmek ve kaydetmek için kullanılan metod.
    public static void trainCategorizerModel(String dataPath, String modelPath, int iterations, int cutoff) {
        File dataFile = new File(dataPath);
        if (!dataFile.exists()) {
            // Veri dosyası kontrolü, eğer yoksa hata fırlat.
            throw new IllegalArgumentException("Data file does not exist: " + dataPath);
        }

        ObjectStream<String> lineStream = null;
        ObjectStream<DocumentSample> sampleStream = null;

        try {
            // Veri dosyasını okumak için InputStreamFactory ve lineStream oluşturulması.
            InputStreamFactory dataIn = new MarkableFileInputStreamFactory(dataFile);
            lineStream = new PlainTextByLineStream(dataIn, StandardCharsets.UTF_8);
            sampleStream = new DocumentSampleStream(lineStream);

            // Model eğitim parametrelerinin ve feature generator'ların ayarlanması.
            DoccatFactory factory = new DoccatFactory(new FeatureGenerator[]{new BagOfWordsFeatureGenerator()});
            TrainingParameters params = ModelUtil.createDefaultTrainingParameters();
            params.put(TrainingParameters.ALGORITHM_PARAM, "MAXENT");
            params.put(TrainingParameters.ITERATIONS_PARAM, Integer.toString(iterations));
            params.put(TrainingParameters.CUTOFF_PARAM, Integer.toString(cutoff));

            // Modelin eğitilmesi ve dosyaya kaydedilmesi.
            DoccatModel model = DocumentCategorizerME.train("en", sampleStream, params, factory);
            model.serialize(new File(modelPath));

        } catch (IOException e) {
            e.printStackTrace(); // Hata durumunda hata mesajının yazdırılması.
        } finally {
            // Kaynakların temizlenmesi: sampleStream ve lineStream'in kapatılması.
            if (sampleStream != null) {
                try {
                    sampleStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (lineStream != null) {
                try {
                    lineStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
