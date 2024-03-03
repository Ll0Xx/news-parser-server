package com.antont.parserserver.repository;

import com.antont.parserserver.entity.NewsParserConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsParserConfigRepository extends JpaRepository<NewsParserConfig, Integer> {
}
