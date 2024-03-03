package com.antont.parserserver.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "news")
public class News extends BaseEntity implements Comparable<News>{
    @Column(name="headline")
    private String headline;
    @Column(name="description")
    private String description;
    @Column(name="time")
    private LocalDateTime time;
    @Column(name="link")
    private String link;

    public News() {
    }

    public News(String headline, String description, LocalDateTime time, String link) {
        this.headline = headline;
        this.description = description;
        this.time = time;
        this.link = link;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public int compareTo(@NotNull News o) {
        if (getTime() == null || o.getTime() == null)
            return 0;
        return getTime().compareTo(o.getTime());
    }
}
