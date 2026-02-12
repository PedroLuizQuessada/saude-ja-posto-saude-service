package com.example.saudejapostosaudeservice.infrastructure.client.restclient.services;

import org.springframework.context.annotation.Profile;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
@Profile("restclient")
public class ClientResponseService {

    String getResponseBody(ClientHttpResponse response) throws IOException {
        var contentType = response.getHeaders().getContentType();
        var charset = (contentType != null && contentType.getCharset() != null)
                ? contentType.getCharset()
                : StandardCharsets.UTF_8;

        return StreamUtils.copyToString(response.getBody(), charset);
    }
}
