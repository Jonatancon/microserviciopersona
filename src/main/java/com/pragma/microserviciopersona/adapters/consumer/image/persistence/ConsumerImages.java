package com.pragma.microserviciopersona.adapters.consumer.image.persistence;

import com.pragma.microserviciopersona.domain.persistence_ports.client.ImagesPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository("imagesPersistence")
public class ConsumerImages implements ImagesPersistence {

    private final RestTemplate restTemplate;
    private static final String URL = "http://localhost:7000/imagenes/api/v1/image";


    @Autowired
    public ConsumerImages(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void deleteAll(Long id) {
        try {
            this.restTemplate.delete(URL+"/{id}", id);
        }catch (Exception e) {
            System.out.println("error " + e);
        }
    }
}
