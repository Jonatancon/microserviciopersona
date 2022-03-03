package com.pragma.microserviciopersona.domain.persistence_ports.client;

import org.springframework.stereotype.Repository;

@Repository
public interface ImagesPersistence {

    void deleteAll(Long id);
}
