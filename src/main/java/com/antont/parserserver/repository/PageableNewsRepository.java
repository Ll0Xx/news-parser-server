package com.antont.parserserver.repository;

import com.antont.parserserver.entity.News;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PageableNewsRepository extends PagingAndSortingRepository<News, Integer> {
}
