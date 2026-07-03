package com.caleta.pago.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient loteWebClient(){
        return WebClient.builder()
            .baseUrl("https://lote-services.onrender.com")
            .build();
    }

    @Bean
    public WebClient CapturaWebClient(){
        return WebClient.builder()
            .baseUrl("https://captura-services.onrender.com")
            .build();
    }
}
