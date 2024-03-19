package org.example.util;

import opennlp.tools.doccat.*;
import opennlp.tools.util.*;
import opennlp.tools.util.model.ModelUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ModelTrainer {

    public static void trainCategorizerModel(String dataPath, String modelPath, int iterations, int cutoff) {
        File dataFile = new File(dataPath);
        if (!dataFile.exists()) {
            throw new IllegalArgumentException("Data file does not exist: " + dataPath);
        }

        ObjectStream<String> lineStream = null;
        ObjectStream<DocumentSample> sampleStream = null;

        try {
            // InputStreamFactory kullanarak lineStream ve sampleStream oluştur
            InputStreamFactory dataIn = new MarkableFileInputStreamFactory(dataFile);
            lineStream = new PlainTextByLineStream(dataIn, StandardCharsets.UTF_8);
            sampleStream = new DocumentSampleStream(lineStream);

            // Model eğitim parametrelerini ve factory'yi ayarlayın
            DoccatFactory factory = new DoccatFactory(new FeatureGenerator[]{new BagOfWordsFeatureGenerator()});
            TrainingParameters params = ModelUtil.createDefaultTrainingParameters();
            params.put(TrainingParameters.ALGORITHM_PARAM, "MAXENT");
            params.put(TrainingParameters.ITERATIONS_PARAM, Integer.toString(iterations));
            params.put(TrainingParameters.CUTOFF_PARAM, Integer.toString(cutoff));

            // Modeli eğitin ve kaydedin
            DoccatModel model = DocumentCategorizerME.train("en", sampleStream, params, factory);
            model.serialize(new File(modelPath));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // sampleStream ve lineStream'i manuel olarak kapat
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
