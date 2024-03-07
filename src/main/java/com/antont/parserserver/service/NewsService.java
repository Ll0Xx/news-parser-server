package com.antont.parserserver.service;

import com.antont.parserserver.TimePeriodType;
import com.antont.parserserver.dto.CreateNewsDto;
import com.antont.parserserver.dto.UpdateNewsDto;
import com.antont.parserserver.entity.News;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface NewsService {

    void create(CreateNewsDto dto);
    Optional<News> read(Integer id);
    Page<News> readPageable(TimePeriodType periodType, Integer page, Integer size);
    void update(Integer id, UpdateNewsDto dto);
    void delete(Integer id);
}
