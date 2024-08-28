package com.northcoders.demospringbootapp.dao;

import com.northcoders.demospringbootapp.model.Data;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

public class CoordinatesDAO {
    WebClient webClient;

    public ResponseEntity<Data> getResponseBody(String cityName) {
        webClient = WebClient.create();

//        String uri = "https://geocoding-api.open-meteo.com/v1/search?name=London";
        String uri = "https://geocoding-api.open-meteo.com/v1/search";
        uri = uri +"?name=" + cityName;

        System.out.println("Query URI = " + uri);

        ResponseEntity<Data> coordinatesMono = webClient.get()
                .uri(uri).accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(Data.class)
                .block();

        return coordinatesMono;
    }
}
