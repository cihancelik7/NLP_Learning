package org.example.util;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.tokenize.TokenizerModel;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;


/*
            ModelLoader sınıfı, NLP işlemleri için gerekli olan OpenNLP model dosyalarını yüklemekten sorumludur.
             Bu sınıf, belirli bir model dosyasını yüklemek için statik yöntemler sunar. Model dosyaları, cümle tespiti,
             tokenizasyon, kategorizasyon gibi farklı NLP görevleri için kullanılır. Her yöntem, belirtilen yoldan ilgili
             modeli yükler ve bu modeli geri döndürür. Eğer model dosyası bulunamazsa veya başka bir I/O hatası meydana gelirse,
              bir hata mesajı yazdırılır ve bir RuntimeException fırlatılır.
 */


// ModelLoader: OpenNLP model dosyalarını yüklemek için kullanılan sınıf.
public class ModelLoader {

    // Kategorizasyon modelini yüklemek için kullanılan metod.
    public static DoccatModel loadCategorizerModel(String modelPath) {
        try (InputStream modelIn = ModelLoader.class.getResourceAsStream(modelPath)) {
            if (modelIn == null) {
                // Model dosyası bulunamazsa hata fırlat.
                throw new IOException("Model file not found: " + modelPath);
            }
            return new DoccatModel(modelIn); // Modeli döndür.
        } catch (IOException e) {
            e.printStackTrace();// Hata mesajını yazdır.
            throw new RuntimeException("Doccat model could not be loaded from: " + modelPath, e);
        }
    }

    // Tokenizer modelini yüklemek için kullanılan metod.
    public static TokenizerModel loadTokenizerModel(String modelPath) {
        try (InputStream modelIn = ModelLoader.class.getResourceAsStream(modelPath)) {
            if (modelIn == null) {
                // Model dosyası bulunamazsa hata fırlat.
                throw new IOException("Model file not found: " + modelPath);
            }
            return new TokenizerModel(modelIn);// Modeli döndür.
        } catch (IOException e) {
            e.printStackTrace();// Hata mesajını yazdır.
            throw new RuntimeException("Tokenizer model could not be loaded from: " + modelPath, e);
        }
    }
}