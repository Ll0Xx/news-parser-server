package com.antont.parserserver.repository;

import com.antont.parserserver.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Integer> {

    @Query("SELECT  n.time FROM News n ORDER BY n.time DESC limit 1")
    Optional<LocalDateTime> findTimeOfLatestNews();

}
