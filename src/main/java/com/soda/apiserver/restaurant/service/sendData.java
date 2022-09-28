package com.soda.apiserver.restaurant.service;

import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;
import java.util.HashMap;

@RestController
@RequestMapping("test")
public class sendData {

    @Test
    public void test_getForEntity() {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "https://jihyunmeta.pythonanywhere.com/";
        ResponseEntity<String> response
                = restTemplate.getForEntity(fooResourceUrl , String.class);
        System.out.println(response.getBody());
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

    }
}
