package com.northcoders.demospringbootapp.dao;

import com.northcoders.demospringbootapp.model.Data;
import com.northcoders.demospringbootapp.model.Results;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

public class SunriseSunsetDAO {
    WebClient webClient;

    public ResponseEntity<Results> getResponseBody(Double latitude, Double longitude) {
        webClient = WebClient.create();

        String uri = "https://api.sunrisesunset.io/json";
        uri = uri +"?lat=" + latitude + "&lng=" + longitude;

        System.out.println("Query URI = " + uri);

        ResponseEntity<Results> suntimesMono = webClient.get()
                .uri(uri).accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(Results.class)
                .block();

        return suntimesMono;
    }
}
