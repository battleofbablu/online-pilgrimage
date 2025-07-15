package com.example.demo.Controllers;

import com.example.demo.Service.WikidataTempleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/temples")
public class TempleControllerWiki {

    @Autowired
    private WikidataTempleService wikidata;

    @GetMapping("/wikidata")
    public List<Map<String, String>> fetchFromWikidata() {
        return wikidata.fetchTemples(200);
    }
}

