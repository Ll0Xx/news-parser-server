package com.antont.parserserver.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
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

    @Override
    public int compareTo(@NotNull News o) {
        if (getTime() == null || o.getTime() == null)
            return 0;
        return getTime().compareTo(o.getTime());
    }
}
