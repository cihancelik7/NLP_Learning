package org.example.data;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Cities {
    public static final Set<String> cityList = new HashSet<>(Arrays.asList(
            "new york", "los angeles", "london", "paris", "tokyo", "bursa","antalya",
            "beijing", "moscow", "istanbul", "dubai", "singapore","mersin","adana",
            "barcelona", "lisbon", "rio de janeiro", "buenos aires", "cape town",
            "mumbai", "bangkok", "cairo", "sydney", "rome", "eskisehir",
            // Bu listeyi popüler şehirlerle genişletin
            "berlin", "amsterdam", "madrid", "athens", "budapest",
            "prague", "vienna", "seoul", "mexico city", "las vegas",
            "miami", "san francisco", "toronto", "vancouver", "montreal",
            "sao paulo", "lima", "santiago", "bogota", "caracas",
            "hong kong", "shanghai", "manila", "jakarta", "kuala lumpur",
            "hanoi", "bangalore", "tel aviv", "jerusalem", "beirut",
            "kuwait city", "doha", "dublin", "copenhagen", "stockholm",
            "oslo", "helsinki", "reykjavik", "tallinn", "riga",
            "vilnius", "warsaw", "krakow", "bucharest", "sofia",
            "belgrade", "sarajevo", "zagreb", "ljubljana", "bratislava",
            "ankara", "alexandria", "tehran", "baghdad", "riyadh",
            "jeddah", "mecca", "medina", "amman", "beirut",
            "damascus", "jerusalem", "tel aviv", "dubai", "abu dhabi",
            "doha", "manama", "kuwait city", "muscat", "sana'a",
            "tripoli", "tunis", "algiers", "rabat", "casablanca",
            "marrakesh", "cairo", "alexandria", "khartoum", "addis ababa",
            "nairobi", "mogadishu", "djibouti", "kampala", "dar es salaam"
    ));

    public static boolean constainsKey(String query){
        return cityList.contains(query.toLowerCase());
    }
}
