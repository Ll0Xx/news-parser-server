package com.antont.parserserver.service;

import com.antont.parserserver.dto.CreateNewsDto;
import com.antont.parserserver.dto.UpdateNewsDto;
import com.antont.parserserver.entity.News;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface NewsService {

    void create(CreateNewsDto dto);
    Optional<News> read(Integer id);
    void update(Integer id, UpdateNewsDto dto);
    void delete(Integer id);
}
