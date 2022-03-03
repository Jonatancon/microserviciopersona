package com.pragma.microserviciopersona.adapters.rest.client;

import com.pragma.microserviciopersona.domain.input_ports.client.PersonPort;
import com.pragma.microserviciopersona.domain.models.client.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping(PersonResources.PERSON)
public class PersonResources {

    static final String PERSON = "/api/v1/client";

    private final PersonPort personPort;

    @Autowired
    public PersonResources(PersonPort personPort) {
        this.personPort = personPort;
    }

    @GetMapping
    public Stream<Person> readAll() {
        return this.personPort.readAll();
    }

    @PostMapping
    public Person create(@RequestBody Person person) {
        return this.personPort.create(person);
    }

    @PutMapping
    public Person update(@RequestBody Person person) {
        return this.personPort.update(person);
    }

    @GetMapping("/{id}")
    public Optional<Person> read(@PathVariable Long id) {
        return this.personPort.read(id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        return this.personPort.delete(id);
    }

}
