package com.warehouse.config;

import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;

@Configuration
public class RestConfig {

    private String server;
    private int port;
    private String user;
    private String password;

    @Value("${client.ssl.trust-store}")
    private Resource trustStore;
    @Value("${client.ssl.key-store}")
    private Resource keyStore;
    @Value("${client.ssl.trust-store-password}")
    private String trustStorePassword;
    @Value("${client.ssl.key-store-password}")
    private String keyStorePassword;

    public RestConfig(
            @Value("${voorraad.api.server}") String server,
            @Value("${voorraad.api.port}") int port,
            @Value("${voorraad.api.user}") String user,
            @Value("${voorraad.api.password}") String password,
            @Value("${client.ssl.trust-store}") Resource trustStore,
            @Value("${client.ssl.key-store}") Resource keyStore,
            @Value("${client.ssl.trust-store-password}") String trustStorePassword,
            @Value("${client.ssl.key-store-password}")String keyStorePassword
    ){

        this.server = server;
        this.port = port;
        this.user = user;
        this.password = password;
        this.trustStore = trustStore;
        this.keyStore = keyStore;
        this.trustStorePassword = trustStorePassword;
        this.keyStorePassword = keyStorePassword;
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
        // mTls
        SSLConnectionSocketFactory sslConFactory = new SSLConnectionSocketFactory(sslContext(), new NoopHostnameVerifier());

        HttpClientConnectionManager cm = PoolingHttpClientConnectionManagerBuilder.create()
                .setSSLSocketFactory(sslConFactory)
                .build();

        // Basic authentication
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();

        credentialsProvider.setCredentials(new AuthScope(server, port),
                new UsernamePasswordCredentials(user, password.toCharArray()));

        CloseableHttpClient client = HttpClientBuilder
                .create()
                .setConnectionManager(cm)
                .setDefaultCredentialsProvider(credentialsProvider)
                .build();
        return client;
    }

    @Bean
    public SSLContext sslContext() {

        try {
            SSLContext sslContext = new SSLContextBuilder()
                    .loadKeyMaterial(
                            keyStore.getURL(),
                            keyStorePassword.toCharArray(),
                            keyStorePassword.toCharArray()
                    )
                    .loadTrustMaterial(
                            trustStore.getURL(),
                            trustStorePassword.toCharArray()
                    ).build();

            return  sslContext;

        }catch ( Exception e){
            throw new RuntimeException("Unable to create SSLContext");
        }
    }
}
