package com.warehouse.controller;

import com.warehouse.model.server.ArticleSER;
import com.warehouse.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api")
public class ArticleController {

    int rep = 0;
    ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }
    
    // GET: http:/<port>/api/article
    @GetMapping( value = "/article", produces = "application/json")
    public ResponseEntity<List<ArticleSER>> findAll(){

        List<ArticleSER> articleSERS = articleService.fetchAll();
        List<ArticleSER> articleSERS2 = articleService.fetchAll2();

        return ResponseEntity.ok( articleSERS2);
    }

    // GET: http:/<port>/api/article/2/
    @GetMapping( value = "/article/{id}", produces = "application/json")
    public ResponseEntity<ArticleSER> findById( @PathVariable("id") Long id){

        rep++;
        Optional<ArticleSER> articleSER = articleService.fetchById( id);
        Optional<ArticleSER> articleSER2 = articleService.fetchById2( id);

        if(articleSER.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }else{
            System.out.println(rep);
            return ResponseEntity.ok(articleSER2.get());
        }
    }

}
