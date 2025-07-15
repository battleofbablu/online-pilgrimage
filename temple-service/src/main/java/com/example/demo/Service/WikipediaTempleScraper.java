package com.example.demo.Service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

public class WikipediaTempleScraper {

    public static List<Map<String, String>> scrapeTemples() throws Exception {
        String url = "https://en.wikipedia.org/wiki/List_of_Hindu_temples_in_India";

        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 TempleScraperBot/1.0")
                .timeout(15000)
                .get();

        Element content = doc.selectFirst("div.mw-parser-output");

        if (content == null) {
            System.out.println("❌ Could not find main content container.");
            return Collections.emptyList();
        }

        List<Map<String, String>> templeList = new ArrayList<>();
        String currentState = "";

        for (Element element : content.children()) {
            String tag = element.tagName();

            // Detect state/region header
            if (tag.equals("h2") || tag.equals("h3")) {
                Element span = element.selectFirst(".mw-headline");
                if (span != null) {
                    currentState = span.text().replace("[edit]", "").trim();
                }
            }

            // Detect temple list under that state
            else if (tag.equals("ul") && !currentState.isEmpty()) {
                for (Element li : element.select("li")) {
                    String templeName = li.text().trim();
                    if (templeName.isEmpty() || templeName.contains("edit")) continue;

                    Map<String, String> temple = new HashMap<>();
                    temple.put("temple_name", templeName);
                    temple.put("location", currentState);
                    temple.put("details", "Information not available.");
                    temple.put("image_url", "https://upload.wikimedia.org/wikipedia/commons/6/6b/HinduTemple.jpg");
                    temple.put("opening_time", "06:00 AM");
                    temple.put("closing_time", "09:00 PM");
                    temple.put("image1", "");
                    temple.put("image2", "");
                    temple.put("image3", "");

                    templeList.add(temple);
                    System.out.println("✅ " + templeName + " | " + currentState);
                }
            }
        }

        System.out.println("📊 Total temples scraped: " + templeList.size());
        return templeList;
    }
}
