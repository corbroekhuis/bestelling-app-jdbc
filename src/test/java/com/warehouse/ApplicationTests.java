package com.warehouse;

import com.warehouse.model.server.ArticleSER;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@SpringBootTest
class ApplicationTests {

    @Autowired
    RestTemplate restTemplate;

	@Test
	void BasicAuthTest() {


        String url = "http://localhost:8082/api/articleser/{id}";

        // Fetch JSON response as String wrapped in ResponseEntity
        ResponseEntity<ArticleSER> response = restTemplate.getForEntity(url, ArticleSER.class, 1000002);

        ArticleSER articleSER = response.getBody();

	}

}
