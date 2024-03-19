package org.example.util;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.tokenize.TokenizerModel;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

public class ModelLoader {

    public static DoccatModel loadCategorizerModel(String modelPath) {
        try (InputStream modelIn = ModelLoader.class.getResourceAsStream(modelPath)) {
            if (modelIn == null) {
                throw new IOException("Model file not found: " + modelPath);
            }
            return new DoccatModel(modelIn);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Doccat model could not be loaded from: " + modelPath, e);
        }
    }

    public static TokenizerModel loadTokenizerModel(String modelPath) {
        try (InputStream modelIn = ModelLoader.class.getResourceAsStream(modelPath)) {
            if (modelIn == null) {
                throw new IOException("Model file not found: " + modelPath);
            }
            return new TokenizerModel(modelIn);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Tokenizer model could not be loaded from: " + modelPath, e);
        }
    }
}