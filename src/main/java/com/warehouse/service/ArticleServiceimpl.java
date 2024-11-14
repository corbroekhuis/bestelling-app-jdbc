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

        String url = "http://localhost:8081/api/articleser";

        // Fetch response as List wrapped in ResponseEntity
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
        List<ArticleSER> articleSERS = response.getBody();
        return articleSERS;
    }

    @Override
    public List<ArticleSER> fetchAll2() {

        String url = "http://localhost:8081/api/articleser";

        ResponseEntity<List<ArticleSER>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ArticleSER>>() {});

        return response.getBody();
    }

    @Override
    public Optional<ArticleSER> fetchById(long id) {

        String url = "http://localhost:8081/api/articleser/" + id;

        // Fetch JSON response as String wrapped in ResponseEntity
        ResponseEntity<ArticleSER> response = restTemplate.getForEntity(url, ArticleSER.class);

        ArticleSER articleSER = response.getBody();
        return Optional.ofNullable(articleSER);
    }

    @Override
    public Optional<ArticleSER> fetchById2(long id) {

        String url = "http://localhost:8081/api/articleser/" + id;
        ArticleSER articleSER = restTemplate.getForObject( url, ArticleSER.class);
        return Optional.ofNullable(articleSER);
    }

    @Override
    public Optional<ArticleSER> updateStockById(long id, int quantity) {

        String url = "http://localhost:8081/api/updatestockbyid?id=" + id +"&quantity=" + quantity;
        ResponseEntity<ArticleSER> articleSER = restTemplate.postForEntity( url, null, ArticleSER.class);
        return Optional.ofNullable(articleSER.getBody());
    }
}
