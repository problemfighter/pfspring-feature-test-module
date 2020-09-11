package com.problemfighter.pfspring.module.es.example;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsBlogRepository extends ElasticsearchRepository<EsBlog, String> {
    /**
     * Fuzzy Query (Deduplication), Containing by Title, Introduction, Description and Label
     * @param title
     * @param Summary
     * @param content
     * @param tags
     * @param pageable
     * @return
     */
    Page<EsBlog> findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(String title, String Summary, String content, String tags, Pageable pageable);

    /**
     * Query Es Blog according to its id
     * @param blogId
     * @return
     */
    EsBlog findByBlogId(Long blogId);
}
