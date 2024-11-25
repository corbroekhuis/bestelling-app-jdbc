package com.warehouse.config;

import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

    String server;
    int port;
    String user;
    String password;

    public RestConfig(
            @Value("${voorraad.api.server}") String server,
            @Value("${voorraad.api.port}") int port,
            @Value("${voorraad.api.user}") String user,
            @Value("${voorraad.api.password}") String password
            ){

        this.server = server;
        this.port = port;
        this.user = user;
        this.password = password;

    }

    @Bean
    public RestTemplate restTemplate(){

        RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());

        return restTemplate;
    }

    @Bean
    public HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory()
    {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
                = new HttpComponentsClientHttpRequestFactory();

        clientHttpRequestFactory.setHttpClient(httpClient());

        return clientHttpRequestFactory;
    }

    @Bean
    public CloseableHttpClient httpClient()
    {
        // Basic authentication
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();

        credentialsProvider.setCredentials(new AuthScope(server, port),
                new UsernamePasswordCredentials(user, password.toCharArray()));

        CloseableHttpClient client = HttpClientBuilder
                .create()
                .setDefaultCredentialsProvider(credentialsProvider)
                .build();
        return client;
    }

}
