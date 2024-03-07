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

    /**
     * Parses news items from various websites based on configurations stored in a repository.
     * The method returns a list of News objects representing the parsed news items.
     *
     * @return A list of News objects representing the parsed news items from the configured websites.
     * @throws RuntimeException if an IOException occurs during the fetching or parsing process.
     */
    private List<News> parseNews() {
        // Retrieve all news parser configurations from the repository.
        List<NewsParserConfig> configs = newsParserConfigRepository.findAll();

        // For each configuration, fetch the HTML content of the specified website, parse the content,
        // and construct News objects based on the extracted data.
        return new java.util.ArrayList<>(configs.parallelStream().flatMap(it -> {
            try {
                // Fetch the HTML content of the website.
                Document doc = Jsoup.connect(it.getSiteLink()).get();

                // Construct a DateTimeFormatter based on the configuration's time format and locale.
                DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                        .appendPattern(it.getTimeFormat())
                        .parseDefaulting(ChronoField.YEAR, LocalDateTime.now().getYear())
                        .toFormatter(new Locale(it.getLocale()));

                // Parse the HTML content to extract news items.
                return doc.select(it.getBody()).parallelStream().map(element -> {
                    // Extract the headline, description, time, and link of each news item.
                    String headline = selectElement(element, it.getHeadline()).text();
                    String description = selectElement(element, it.getDescription()).text();
                    String timeString = selectElement(element, it.getTime()).text();
                    LocalDateTime time = LocalDateTime.parse(timeString, formatter);
                    String link = selectElement(element, it.getPageLink()).attr("href");

                    // If the link does not start with the site link, prepend the base URL to the link.
                    if (!link.startsWith(it.getSiteLink())) {
                        link = getBaseUrl(it.getSiteLink()).concat(link);
                    }

                    // Construct a News object with the extracted data.
                    return new News(headline, description, time, link);
                });
            } catch (IOException e) {
                // If an IOException occurs, throw a RuntimeException.
                throw new RuntimeException(e);
            }
        }).toList());
    }

    /**
     * Filters out duplicate news items from a list, keeping only those that are newer than the latest news item in the repository.
     *
     * @param newsList The list of news items to be filtered.
     * @return A list of news items that are not duplicates, based on their time being after the latest news item's time.
     */
    private List<News> filterDuplicateNews(List<News> newsList) {
        // Retrieve the time of the latest news item from the repository.
        Optional<LocalDateTime> latestNewsDate = newsRepository.findTimeOfLatestNews();

        // Filter the provided list of news items, keeping only those whose time is after the latest news item's time.
        // If the latest news item's time cannot be determined, all news items in the provided list are kept.
        return newsList.stream()
                .filter(item -> latestNewsDate.map(date -> item.getTime().isAfter(date)).orElse(true))
                .toList();
    }

    private Element selectElement(Element element, String selector) {
        return Optional.ofNullable(element.selectFirst(selector))
                .orElseThrow(() -> new RuntimeException(selector + " is not present"));
    }

    /**
     * Extracts and returns the base URL from a given URL string.
     * This method creates a URL object from the provided string and then concatenates the protocol and authority parts of the URL to form the base URL.
     * If the URL string is malformed, a RuntimeException is thrown with a message indicating that the base URL could not be extracted.
     *
     * @param urlString The URL string from which the base URL is to be extracted.
     * @return The base URL, consisting of the protocol and authority parts of the URL.
     * @throws RuntimeException if the URL string is malformed and the base URL cannot be extracted.
     */
    private String getBaseUrl(String urlString) {
        try {
            URL url = URI.create(urlString).toURL();
            return url.getProtocol() + "://" + url.getAuthority();
        } catch (MalformedURLException e) {
            throw new RuntimeException("Unable to extract base URL", e);
        }
    }
}
