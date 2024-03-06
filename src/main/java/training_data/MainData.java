package training_data;

import opennlp.tools.langdetect.*;
import opennlp.tools.util.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MainData {

    private final static String TRAINING_DATA_FILE_NAME = "TrainingData/DoccatSample.txt";
    public static void main(String[] args) throws FileNotFoundException {
        LanguageDetectorModel languageDetectorModel = null;
        // 1-) Load the training data in to a stream

        LanguageDetectorSampleStream languageDetectorSampleStream;
        InputStreamFactory inputStreamFactory = new MarkableFileInputStreamFactory(new File("TrainingData"+File.separator+TRAINING_DATA_FILE_NAME));
        try(ObjectStream<String> stream = new PlainTextByLineStream(inputStreamFactory, StandardCharsets.UTF_8)) {
            languageDetectorSampleStream = new LanguageDetectorSampleStream(stream);
            TrainingParameters trainingParameters = new TrainingParameters();
            trainingParameters.put(TrainingParameters.ITERATIONS_PARAM,100);
            trainingParameters.put(TrainingParameters.CUTOFF_PARAM,5);
            trainingParameters.put("DataIndexer","TwoPass");
            trainingParameters.put(TrainingParameters.ALGORITHM_PARAM,"NAIVEBAYES");

            // 2- Train the model
            languageDetectorModel = LanguageDetectorME.train(languageDetectorSampleStream,trainingParameters,new LanguageDetectorFactory());


        } catch (IOException e) {
            e.printStackTrace();
        }
        if (languageDetectorModel == null){
            throw new IllegalStateException("The language detector model is null or couldn't ne successfully initialized");
        }

        // 3 - Load the trained model
        LanguageDetector languageDetector = new LanguageDetectorME(languageDetectorModel);

    }

}
