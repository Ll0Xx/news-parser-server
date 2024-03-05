package com.antont.parserserver.service.Impl;


import com.antont.parserserver.entity.News;
import com.antont.parserserver.entity.NewsParserConfig;
import com.antont.parserserver.repository.NewsParserConfigRepository;
import com.antont.parserserver.repository.NewsRepository;
import com.antont.parserserver.service.ParsingNewsService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class ParsingNewsServiceImpl implements ParsingNewsService {

    private final NewsRepository newsRepository;
    private final NewsParserConfigRepository newsParserConfigRepository;

    public ParsingNewsServiceImpl(NewsRepository newsRepository, NewsParserConfigRepository newsParserConfigRepository) {
        this.newsRepository = newsRepository;
        this.newsParserConfigRepository = newsParserConfigRepository;
    }

    @Override
    public void processNews() {
        List<News> newsList = filterDuplicateNews(parseNews());
        newsRepository.saveAll(newsList);
    }

    private List<News> parseNews() {
        List<NewsParserConfig> configs = newsParserConfigRepository.findAll();
        return new java.util.ArrayList<>(configs.parallelStream().flatMap(it -> {
            try {
                Document doc = Jsoup.connect(it.getSiteLink()).get();
                DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                        .appendPattern(it.getTimeFormat())
                        .parseDefaulting(ChronoField.YEAR, LocalDateTime.now().getYear())
                        .toFormatter(new Locale(it.getLocale()));
                return doc.select(it.getBody()).parallelStream().map(element -> {
                    String headline = selectElement(element, it.getHeadline()).text();
                    String description = selectElement(element, it.getDescription()).text();
                    String timeString = selectElement(element, it.getTime()).text();
                    LocalDateTime time = LocalDateTime.parse(timeString, formatter);
                    String link = selectElement(element, it.getPageLink()).attr("href");
                    if (!link.startsWith(it.getSiteLink())) {
                        link = getBaseUrl(it.getSiteLink()).concat(link);
                    }
                    return new News(headline, description, time, link);
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).toList());
    }

    private List<News> filterDuplicateNews(List<News> newsList) {
        Optional<LocalDateTime> latestNewsDate = newsRepository.findTimeOfLatestNews();
        return newsList.stream()
                .filter(item -> latestNewsDate.map(date -> item.getTime().isAfter(date)).orElse(true))
                .toList();
    }

    private Element selectElement(Element element, String selector) {
        return Optional.ofNullable(element.selectFirst(selector))
                .orElseThrow(() -> new RuntimeException(selector + " is not present"));
    }

    private String getBaseUrl(String urlString) {
        try {
            URL url = URI.create(urlString).toURL();
            return url.getProtocol() + "://" + url.getAuthority();
        } catch (MalformedURLException e) {
            throw new RuntimeException("Unable to extract base URL", e);
        }
    }
}
