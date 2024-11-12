package com.warehouse.service;

import com.warehouse.model.server.ArticleSER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceimpl implements ArticleService {

    RestTemplate restTemplate;

    @Autowired
    public ArticleServiceimpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<ArticleSER> fetchAll() {
        return null;
    }

    @Override
    public List<ArticleSER> fetchAll2() {
        return null;
    }

    @Override
    public Optional<ArticleSER> fetchById(long id) {
        return Optional.empty();
    }

    @Override
    public Optional<ArticleSER> fetchById2(long id) {
        return Optional.empty();
    }
}
