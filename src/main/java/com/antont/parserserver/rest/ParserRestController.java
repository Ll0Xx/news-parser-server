package com.antont.parserserver.rest;

import com.antont.parserserver.service.ParsingNewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("parser")
public class ParserRestController {

    private final ParsingNewsService parsingNewsService;

    public ParserRestController(ParsingNewsService parsingNewsService) {
        this.parsingNewsService = parsingNewsService;
    }

    @GetMapping()
    public ResponseEntity<String> index() {
        parsingNewsService.processNews();
        return ResponseEntity.ok().build();
    }
}
