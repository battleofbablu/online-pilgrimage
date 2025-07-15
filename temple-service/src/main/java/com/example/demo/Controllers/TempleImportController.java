package com.example.demo.Controllers;

import com.example.demo.Model.Temple;
import com.example.demo.Service.TempleService;
import com.example.demo.Service.WikipediaTempleScraper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/temples")
public class TempleImportController {

    @Autowired
    private TempleService templeService;

    @PostMapping("/import")
    public ResponseEntity<?> importTemplesFromWikipedia() {
        try {
            List<Map<String, String>> data = WikipediaTempleScraper.scrapeTemples();
            System.out.println("✅ Scraped count: " + data.size());

            List<Temple> savedTemples = new ArrayList<>();

            for (Map<String, String> t : data) {
                Temple temple = new Temple();
                temple.setTemple_name(t.get("temple_name"));
                temple.setLocation(t.get("location"));
                temple.setDetails(t.get("details"));
                temple.setImage_url(t.get("image_url"));
                temple.setOpening_time(t.get("opening_time"));
                temple.setClosing_time(t.get("closing_time"));
                temple.setImage1(t.get("image1"));
                temple.setImage2(t.get("image2"));
                temple.setImage3(t.get("image3"));

                Temple saved = templeService.save(temple);
                System.out.println("💾 Saved: " + saved.getTemple_name());
                savedTemples.add(saved);
            }

            return ResponseEntity.ok(savedTemples);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("❌ Import failed: " + e.getMessage());
        }
    }


    @GetMapping("/test-scrape")
    public ResponseEntity<?> testScrape() {
        try {
            List<Map<String, String>> scraped = WikipediaTempleScraper.scrapeTemples();
            return ResponseEntity.ok(scraped);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Scraper failed: " + e.getMessage());
        }
    }
}
