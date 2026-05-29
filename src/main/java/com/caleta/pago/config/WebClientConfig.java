package com.caleta.pago.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient loteWebClient(WebClient.Builder builder){
        return builder.baseUrl("http://localhost:8086/api/lotes").build();
    }

    @Bean
    public WebClient CapturaWebClient(WebClient.Builder builder){
        return builder.baseUrl("http://localhost:8085/api/capturas").build();
    }
}
