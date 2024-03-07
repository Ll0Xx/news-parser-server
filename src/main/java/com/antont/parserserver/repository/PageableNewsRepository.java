package com.antont.parserserver.repository;

import com.antont.parserserver.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface PageableNewsRepository extends PagingAndSortingRepository<News, Integer> {
    // Defines a custom query to fetch a page of News entities based on their published time.
    // The query selects News entities where the hour part of their published time falls within a specified range.
    // The result is a Page<News> object, which includes the requested page of News entities along with pagination information.
    @Query("SELECT n FROM News n WHERE FUNCTION('HOUR', n.time) BETWEEN :startHour AND :endHour")
    Page<News> findByPublishedTime(@Param("startHour") int startHour, @Param("endHour") int endHour, Pageable pageable);
}
