package com.warehouse.service;

import com.warehouse.model.server.ArticleSER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    String server;
    int port;
    String baseUrl;

    @Autowired
    public ArticleServiceimpl(RestTemplate restTemplate,
                              @Value("${voorraad.api.server}") String server,
                              @Value("${voorraad.api.port}") int port
                              ) {
        this.restTemplate = restTemplate;
        this.server = server;
        this.port = port;
        this.baseUrl = String.format("http://%s:%d/api/", server, port);
        System.out.println( baseUrl);
    }

    @Override
    public List<ArticleSER> fetchAll() {

        String url = baseUrl + "articleser";

        // Fetch response as List wrapped in ResponseEntity
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
        List<ArticleSER> articleSERS = response.getBody();
        return articleSERS;
    }

    @Override
    public List<ArticleSER> fetchAll2() {

        String url = baseUrl + "articleser";

        ResponseEntity<List<ArticleSER>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ArticleSER>>() {});

        return response.getBody();
    }

    @Override
    public Optional<ArticleSER> fetchById(long id) {

        String url = baseUrl + "articleser/{id}";

        // Fetch JSON response as String wrapped in ResponseEntity
        ResponseEntity<ArticleSER> response = restTemplate.getForEntity(url, ArticleSER.class, id);

        ArticleSER articleSER = response.getBody();
        return Optional.ofNullable(articleSER);
    }

    @Override
    public Optional<ArticleSER> fetchById2(long id) {

        String url = baseUrl + "articleser/{id}";

        ArticleSER articleSER = restTemplate.getForObject( url, ArticleSER.class, id);
        return Optional.ofNullable(articleSER);
    }

    @Override
    public Optional<String> updateStockById(long id, int quantity) {

        String url = baseUrl + "updatestockbyid?id={id}&quantity={quantity}";
        ResponseEntity<String> responseEntity = restTemplate.postForEntity( url, null, String.class, id, quantity);
        return Optional.ofNullable(responseEntity.getBody());
    }
}
