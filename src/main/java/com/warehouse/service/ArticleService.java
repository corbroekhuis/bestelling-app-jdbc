package com.warehouse.service;

import com.warehouse.model.server.ArticleSER;

import java.util.List;
import java.util.Optional;

public interface ArticleService {

    List<ArticleSER> fetchAll();

    List<ArticleSER> fetchAll2();

    Optional<ArticleSER> fetchById(long id);

    Optional<ArticleSER> fetchById2(long id);
}
