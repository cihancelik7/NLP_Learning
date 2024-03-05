

## Java Chatbot - Apache OpenNLP Kullanımı

Selam! Bu projede Java ile geliştirdiğim basit bir chatbot'tan bahsedeceğim. Chatbot'um, Apache OpenNLP'nin gücünden yararlanarak insanlarla sohbet edebiliyor. Kullanıcıların mesajlarını anlamak ve mantıklı yanıtlar vermek için birkaç NLP tekniği kullanıyoruz:

- **Cümle Saptama:** Kullanıcının ne dediğini cümle cümle anlıyoruz.
- **Tokenizasyon:** Cümleleri kelimelere ayırıyoruz ki ne demek istediklerini daha iyi anlayalım.
- **POS Tagging:** Her kelimenin cümledeki görevini (isim, fiil vs.) buluyoruz.
- **Lemmatization:** Kelimelerin köklerini bulup, anlamlarını daha net kavrayabiliyoruz.

Yanıtları ise belirli kategorilere göre önceden tanımlıyor ve kullanıcıya sunuyoruz. Projeyi çalıştırmak ve kendi chatbot'una merhaba demek için bilmen gereken her şey burada!

Başlamadan önce, `categorizer.txt`, `en-sent.bin`, `en-token.bin`, `en-pos-maxent.bin`, ve `en-lemmatizer.bin` gibi model dosyalarını indirmeyi unutma. Bu dosyalar olmadan chatbot'um biraz sessiz kalabilir. ;)

Herkesin kolayca deneyebilmesi için adım adım kurulum ve çalıştırma talimatları da ekledim. Haydi, NLP dünyasına bir adım atalım!

Unutma, kodları kendi projelerinde kullanabilir, geliştirebilir ve bu muhteşem NLP yolculuğuna ortak olabilirsin. Herhangi bir sorun olursa ya da sohbet etmek istersen, bana ulaşmaktan çekinme. Bol şanslar!
