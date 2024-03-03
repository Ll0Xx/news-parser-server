package com.antont.parserserver.repository;

import com.antont.parserserver.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Integer> {
}
