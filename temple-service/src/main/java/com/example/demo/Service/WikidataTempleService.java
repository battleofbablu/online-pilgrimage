package com.example.demo.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class WikidataTempleService {

    private final RestTemplate rest = new RestTemplate();

    public List<Map<String, String>> fetchTemples(int limit) {
        List<Map<String, String>> temples = new ArrayList<>();

        try {
            String sparql = "SELECT DISTINCT ?item ?itemLabel ?locationLabel WHERE { " +
                    "?item wdt:P31/wdt:P279* wd:Q80192. " +
                    "OPTIONAL { ?item wdt:P276 ?location. } " +
                    "SERVICE wikibase:label { bd:serviceParam wikibase:language \"en\". } } " +
                    "LIMIT " + limit;


            String url = "https://query.wikidata.org/sparql?query=" +
                    UriUtils.encode(sparql, StandardCharsets.UTF_8);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "application/sparql-results+json");
            HttpEntity<?> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = rest.exchange(url, HttpMethod.GET, entity, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response.getBody());
            ArrayNode bindings = (ArrayNode) root.path("results").path("bindings");

            for (JsonNode b : bindings) {
                Map<String, String> temple = new HashMap<>();
                temple.put("temple_name", b.path("itemLabel").path("value").asText());
                temple.put("location", b.path("locationLabel").path("value").asText(""));
                temple.put("details", "Fetched from Wikidata.");
                temple.put("image_url", "https://upload.wikimedia.org/wikipedia/commons/6/6b/HinduTemple.jpg");
                temple.put("opening_time", "06:00 AM");
                temple.put("closing_time", "09:00 PM");
                temple.put("image1", "");
                temple.put("image2", "");
                temple.put("image3", "");

                temples.add(temple);
            }

        } catch (Exception e) {
            System.out.println("❌ Error fetching temple data: " + e.getMessage());
        }

        System.out.println("📊 Total temples fetched: " + temples.size());
        return temples;
    }
}
