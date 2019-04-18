package com.tensquare.search.dao;

import com.tensquare.search.pojo.Article;
import entity.PageResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleSearchDao extends ElasticsearchRepository<Article,String> {

    Page<Article> findByTitleLikeOrContentLike(String title, String Content, Pageable pageable);
}
