package com.example.saudejapostosaudeservice.infrastructure.client.restclient.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
@Profile("restclient")
public class RestClientConfig {

    @Value("${connect-timeout}")
    private int connectTimeout;

    @Value("${read-timeout}")
    private int readTimeout;

    @Bean
    RestClient restClient(RestClient.Builder builder) {
        var requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(connectTimeout);
        requestFactory.setReadTimeout(readTimeout);

        return builder
                .requestFactory(requestFactory)
                .build();
    }

}
