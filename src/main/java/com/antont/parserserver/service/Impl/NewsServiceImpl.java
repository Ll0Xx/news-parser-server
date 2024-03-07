package com.antont.parserserver.service.Impl;

import com.antont.parserserver.dto.CreateNewsDto;
import com.antont.parserserver.dto.UpdateNewsDto;
import com.antont.parserserver.entity.News;
import com.antont.parserserver.properties.NewsProperties;
import com.antont.parserserver.repository.NewsRepository;
import com.antont.parserserver.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

@Service
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;

    private final NewsProperties newsProperties;
    private static final Logger LOGGER = LoggerFactory.getLogger(NewsService.class);

    public NewsServiceImpl(NewsRepository newsRepository, NewsProperties newsProperties) {
        this.newsRepository = newsRepository;
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

    @Override
    public void update(Integer id, UpdateNewsDto dto) {
        News news = newsRepository.findById(id).orElseThrow(() ->
                new RuntimeException(String.format("News with id %s not found", id)));
        if (dto.headline() != null) {
            news.setHeadline(dto.headline());
        }
        if (dto.description() != null) {
            news.setDescription(dto.description());
        }
        if (dto.time() != null) {
            news.setTime(parseDate(dto.time()));
        }
        if (dto.link() != null) {
            news.setLink(dto.link());
        }
        newsRepository.save(news);
    }

    @Override
    public void delete(Integer id) {
        newsRepository.deleteById(id);
    }

    private LocalDateTime parseDate(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(newsProperties.getDateTimeFormat(), Locale.US);
        return LocalDateTime.parse(dateTime, formatter);
    }
}
