package com.antont.parserserver.rest;

import com.antont.parserserver.dto.CreateNewsDto;
import com.antont.parserserver.dto.UpdateNewsDto;
import com.antont.parserserver.entity.News;
import com.antont.parserserver.service.NewsService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("news")
public class NewsRestController {
    private final NewsService newsService;
    private static final Logger LOGGER = LoggerFactory.getLogger(NewsRestController.class);

    public NewsRestController(NewsService newsService) {
        this.newsService = newsService;
    }

    @PostMapping()
    public ResponseEntity<?> create( @Valid @RequestBody CreateNewsDto dto) {
        try {
            newsService.create(dto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOGGER.error("Unable to create news record", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<News> read(@PathVariable Integer id) {
        try {
            return ResponseEntity.of(newsService.read(id));
        } catch (Exception e) {
            LOGGER.error(String.format("Unable to read news record with id %s", id), e);
            return ResponseEntity.badRequest().build();
        }

    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody UpdateNewsDto dto) {
        try {
            newsService.update(id, dto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOGGER.error("Unable to update news record", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            newsService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOGGER.error("Unable to delete news record", e);
            return ResponseEntity.badRequest().build();
        }
    }
}
