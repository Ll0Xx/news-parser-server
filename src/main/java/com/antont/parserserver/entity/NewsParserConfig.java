package com.antont.parserserver.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "news_parser_configs")
public class NewsParserConfig extends BaseEntity {
    @Column(name="site_link")
    private String siteLink;
    @Column(name="body")
    private String body;
    @Column(name="headline")
    private String headline;
    @Column(name="description")
    private String description;
    @Column(name="time")
    private String time;
    @Column(name="page_link")
    private String pageLink;
    @Column(name="time_format")
    private String timeFormat;
    @Column(name="locale")
    private String locale;

    public String getSiteLink() {
        return siteLink;
    }

    public void setSiteLink(String siteLink) {
        this.siteLink = siteLink;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String bodyClass) {
        this.body = bodyClass;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headlineClass) {
        this.headline = headlineClass;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String descriptionClass) {
        this.description = descriptionClass;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String timeClass) {
        this.time = timeClass;
    }

    public String getPageLink() {
        return pageLink;
    }

    public void setPageLink(String pageLink) {
        this.pageLink = pageLink;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
