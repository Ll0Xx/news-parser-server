package com.antont.parserserver.scheduling;

import com.antont.parserserver.service.ParsingNewsService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component("ScheduledNewsParsing")
public class ScheduledNewsParsing {

    public final ParsingNewsService parsingNewsService;

    public ScheduledNewsParsing(ParsingNewsService parsingNewsService) {
        this.parsingNewsService = parsingNewsService;
    }

    @Scheduled(fixedRateString = "${schedule.news-parsing.delay-minutes}", timeUnit = TimeUnit.MINUTES)
    public void scheduleTaskUsingExternalizedCronExpression() {
        parsingNewsService.processNews();
    }
}
