package com.antont.parserserver.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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

}
