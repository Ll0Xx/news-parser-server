package com.antont.parserserver.service.Impl;

import com.antont.parserserver.TimePeriodType;
import com.antont.parserserver.dto.CreateNewsDto;
import com.antont.parserserver.dto.UpdateNewsDto;
import com.antont.parserserver.entity.News;
import com.antont.parserserver.properties.NewsProperties;
import com.antont.parserserver.repository.NewsRepository;
import com.antont.parserserver.repository.PageableNewsRepository;
import com.antont.parserserver.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Optional;

@Service
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final PageableNewsRepository pageableNewsRepository;
    private final NewsProperties newsProperties;
    private static final Logger LOGGER = LoggerFactory.getLogger(NewsService.class);

    public NewsServiceImpl(NewsRepository newsRepository, PageableNewsRepository pageableNewsRepository, NewsProperties newsProperties) {
        this.newsRepository = newsRepository;
        this.pageableNewsRepository = pageableNewsRepository;
        this.newsProperties = newsProperties;
    }

    @Override
    public void create(CreateNewsDto dto) {
        News news = new News();
        news.setHeadline(dto.headline());
        news.setDescription(dto.description());
        news.setTime(parseDate(dto.time()));
        news.setLink(dto.link());
        newsRepository.save(news);
    }

    @Override
    public Optional<News> read(Integer id) {
        return newsRepository.findById(id);
    }

    /**
     * Fetches a pageable list of {@link News} entities based on a specified time period.
     * <p>
     * This method is designed to support pagination and filtering of news articles by their published time.
     * The filtering is done based on the provided {@link TimePeriodType} enum value, which can be one of the following:
     * <ul>
     *     <li>{@link TimePeriodType#ALL} - Fetches all news articles without any time filtering.</li>
     *     <li>{@link TimePeriodType#MORNING} - Fetches news articles published between 6 AM and 12 PM.</li>
     *     <li>{@link TimePeriodType#DAY} - Fetches news articles published between 12 PM and 6 PM.</li>
     *     <li>{@link TimePeriodType#EVENING} - Fetches news articles published between 6 PM and 12 AM.</li>
     * </ul>
     * </p>
     * <p>
     * The method accepts page and size parameters for pagination. It constructs a {@link Pageable} object using these parameters
     * and delegates the fetching of news to the appropriate repository method based on the time period type.
     * </p>
     * <p>
     * The result is a {@link Page} object containing the requested page of {@link News} entities along with pagination information.
     * </p>
     *
     * @param periodType The time period type for filtering news articles.
     * @param page The zero-based page index.
     * @param size The number of items in a page.
     * @return A {@link Page} of {@link News} entities that match the specified time period and pagination criteria.
     */
    @Override
    public Page<News> readPageable(TimePeriodType periodType, Integer page, Integer size) {
        Pageable paging = PageRequest.of(page, size);
        return switch (periodType) {
            case ALL -> pageableNewsRepository.findAll(paging);
            case MORNING -> pageableNewsRepository.findByPublishedTime(6, 12, paging);
            case DAY -> pageableNewsRepository.findByPublishedTime(12, 18, paging);
            case EVENING -> pageableNewsRepository.findByPublishedTime(18, 24, paging);
        };
    }

    /**
     * Updates a news item in the repository based on the provided id and update information.
     * This method checks for null values in the DTO before updating the corresponding fields in the News object.
     * If the news item with the given id is not found, a RuntimeException is thrown.
     *
     * @param id The unique identifier of the news item to be updated.
     * @param dto The data transfer object containing the new values for the news item.
     * @throws RuntimeException if the news item with the given id is not found.
     */
    @Override
    public void update(Integer id, UpdateNewsDto dto) {
        // Retrieve the news item by its id. If not found, throw a RuntimeException.
        News news = newsRepository.findById(id).orElseThrow(() ->
                new RuntimeException(String.format("News with id %s not found", id)));

        // Update the headline of the news item if a new headline is provided.
        if (dto.headline() != null) {
            news.setHeadline(dto.headline());
        }

        // Update the description of the news item if a new description is provided.
        if (dto.description() != null) {
            news.setDescription(dto.description());
        }

        // Update the time of the news item if a new time is provided.
        if (dto.time() != null) {
            news.setTime(parseDate(dto.time()));
        }

        // Update the link of the news item if a new link is provided.
        if (dto.link() != null) {
            news.setLink(dto.link());
        }

        // Save the updated news item back to the repository.
        newsRepository.save(news);
    }

    @Override
    public void delete(Integer id) {
        newsRepository.deleteById(id);
    }

    /**
     * Parses a date-time string into a LocalDateTime object using a specified format.
     * The format is retrieved from the application's properties, ensuring consistency across the application.
     *
     * @param dateTime The date-time string to be parsed.
     * @return The parsed LocalDateTime object.
     * @throws DateTimeParseException if the date-time string cannot be parsed with the specified format.
     */
    private LocalDateTime parseDate(String dateTime) {
        // Retrieve the date-time format from the application's properties.
        // This ensures that the format is consistent across the application.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(newsProperties.getDateTimeFormat(), Locale.US);

        // Parse the date-time string into a LocalDateTime object using the retrieved format.
        // If the string cannot be parsed with the specified format, a DateTimeParseException will be thrown.
        return LocalDateTime.parse(dateTime, formatter);
    }
}
