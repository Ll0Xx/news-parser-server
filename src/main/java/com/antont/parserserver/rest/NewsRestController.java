package com.antont.parserserver.rest;

import com.antont.parserserver.TimePeriodType;
import com.antont.parserserver.dto.CreateNewsDto;
import com.antont.parserserver.dto.UpdateNewsDto;
import com.antont.parserserver.entity.News;
import com.antont.parserserver.service.NewsService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * RESTful API controller for managing news items.
 * This controller provides endpoints for creating, reading, updating, and deleting news records.
 * It uses a {@link NewsService} to perform the actual business logic.
 */
@RestController
@RequestMapping("news")
public class NewsRestController {
    private final NewsService newsService;
    private static final Logger LOGGER = LoggerFactory.getLogger(NewsRestController.class);

    public NewsRestController(NewsService newsService) {
        this.newsService = newsService;
    }

    /**
     * Endpoint for creating a new news record.
     * This method accepts a {@link CreateNewsDto} object, validates it, and uses the {@link NewsService} to create a new news record.
     *
     * @param dto The data transfer object containing the details of the new news record.
     * @return A {@link ResponseEntity} indicating the success or failure of the operation.
     */
    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody CreateNewsDto dto) {
        try {
            newsService.create(dto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOGGER.error("Unable to create news record", e);
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Endpoint for reading a specific news record by its ID.
     * This method uses the {@link NewsService} to retrieve the news record with the specified ID.
     *
     * @param id The ID of the news record to be retrieved.
     * @return A {@link ResponseEntity} containing the retrieved news record or an error response.
     */
    @GetMapping("/{id}")
    public ResponseEntity<News> read(@PathVariable Integer id) {
        try {
            return ResponseEntity.of(newsService.read(id));
        } catch (Exception e) {
            LOGGER.error(String.format("Unable to read news record with id %s", id), e);
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Endpoint for reading a pageable list of news records.
     * This method accepts pagination parameters and a time period type, and uses the {@link NewsService} to retrieve a pageable list of news records that match the criteria.
     *
     * @param page The page number to retrieve.
     * @param size The number of records per page.
     * @param time The time period type to filter the news records.
     * @return A {@link ResponseEntity} containing the pageable list of news records or an error response.
     */
    @GetMapping("/pageable")
    public ResponseEntity<Page<News>> readPageable(@RequestParam(defaultValue = "0") Integer page,
                                                   @RequestParam(defaultValue = "10") Integer size,
                                                   @RequestParam(defaultValue = "all") TimePeriodType time) {
        try {
            return ResponseEntity.ok(newsService.readPageable(time, page, size));
        } catch (Exception e) {
            LOGGER.error(String.format("Unable to read news page %s with page size %s", page, size), e);
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Endpoint for updating a specific news record by its ID.
     * This method accepts a {@link UpdateNewsDto} object, validates it, and uses the {@link NewsService} to update the news record with the specified ID.
     *
     * @param id The ID of the news record to be updated.
     * @param dto The data transfer object containing the updated details of the news record.
     * @return A {@link ResponseEntity} indicating the success or failure of the operation.
     */
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

    /**
     * Endpoint for deleting a specific news record by its ID.
     * This method uses the {@link NewsService} to delete the news record with the specified ID.
     *
     * @param id The ID of the news record to be deleted.
     * @return A {@link ResponseEntity} indicating the success or failure of the operation.
     */
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
