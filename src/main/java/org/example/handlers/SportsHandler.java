package org.example.handlers;

import org.example.nlp.NLPProcessor;

public class SportsHandler implements ResponseHandler{
    private NLPProcessor nlpProcessor;


        public SportsHandler(NLPProcessor nlpProcessor) {
            this.nlpProcessor = nlpProcessor;
        }

        @Override
        public String handleResponse(String input) {
            if (input.toLowerCase().contains("messi")) {
                if (input.toLowerCase().contains("yaş")) {
                    // Messi'nin yaşı için hesaplama yapın veya sabit bir değer döndürün
                    return "Lionel Messi'nin yaşı...";
                } else if (input.toLowerCase().contains("takım")) {
                    return "Lionel Messi şu anda Paris Saint-Germain takımında oynuyor.";
                } else if (input.toLowerCase().contains("gol sayısı")) {
                    // Gol sayısı için harici bir kaynaktan bilgi çekin veya sabit bir değer döndürün
                    return "Lionel Messi'nin kariyeri boyunca attığı gol sayısı...";
                }
                // Diğer Messi ile ilgili spesifik sorular
            }
            // Ronaldo, Neymar vb. için benzer kontrol yapıları

            // Eşleşme bulunamazsa genel bir yanıt dön
            return "Özür dilerim, bu konuda spesifik bir bilgi bulamadım.";
        }
    }


/*    @Override
    public String handleResponse(String input) {
        //input'tan alt kategoriyi belirleyin..
        String category = nlpProcessor.determineCategory(input);

        switch (category){
            case "sports.football.players.messi":
                return "Lionel Messi currently plays as a forward for Paris Saint-Germain and the Argentina national team.";
            case "sports.football.players.ronaldo.goals":
                return "Cristiano Ronaldo has scored over 700 goals in his career for club and country.";
            case "sports.football.players.neymar.style":
                return "Neymar is known for his dribbling, finishing, and ability to play with both feet, as well as his flair in beating players one-on-one.";
            case "sports.football.players.england.captain":
                return "The current captain of the England national team is Harry Kane.";
            case "sports.football.players.worldcup.final.goal":
                return "Mario Götze scored the winning goal for Germany in the 2014 FIFA World Cup final against Argentina.";
            case "sports.football.players.premierleague_record":
                return "The record for the most goals scored in a single Premier League season is held by Mohamed Salah, who scored 32 goals in the 2017-2018 season for Liverpool.";
            case "sports.football.players.most.caps":
                return "Ahmed Hassan of Egypt holds the record for the most international caps with 184 appearances for his national team.";
            case "sports.football.players.best.goalkeeper":
                return "Many consider Manuel Neuer to be one of the best goalkeepers in the world, known for his quick reflexes, excellent shot stopping abilities, and command of his area.";
            case "sports.football.players.pele.number":
                return "Pelé famously wore the number 10 jersey for Brazil and Santos FC.";
            default:
                return "I'm sorry, I can't find specific sports information about that.";
        }
    }*/
