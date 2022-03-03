package com.pragma.microserviciopersona.domain.services.client;

import com.pragma.microserviciopersona.domain.exceptions.ConflictException;
import com.pragma.microserviciopersona.domain.exceptions.NotFountException;
import com.pragma.microserviciopersona.domain.input_ports.client.PersonPort;
import com.pragma.microserviciopersona.domain.models.client.Person;
import com.pragma.microserviciopersona.domain.persistence_ports.client.ImagesPersistence;
import com.pragma.microserviciopersona.domain.persistence_ports.client.PersonPersistence;
import com.pragma.microserviciopersona.domain.util.ValidatorsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class PersonService implements PersonPort {

    private final PersonPersistence personPersistence;
    private final ImagesPersistence imagesPersistence;

    @Autowired
    public PersonService(@Qualifier("personPersistence")PersonPersistence personPersistence,
                         ImagesPersistence imagesPersistence) {
        this.personPersistence = personPersistence;
        this.imagesPersistence = imagesPersistence;
    }

    @Override
    @Transactional(readOnly = true)
    public Stream<Person> readAll() {
        return this.personPersistence.readAll();
    }

    @Override
    @Transactional
    public Person create(Person person) {
        this.verifyPersonInfo(person);
        this.isFineEmail(person.getEmail());
        this.existEmail(person.getEmail());

        if (this.existPerson(person.getId())){
            throw new ConflictException("This user is register");
        }

        return this.personPersistence.create(person);
    }

    @Override
    @Transactional
    public Person update(Person person) {
        if (!this.existPerson(person.getId())){
            throw new NotFountException("This user is not register in the database");
        }
        this.verifyPersonInfo(person);
        this.isFineEmail(person.getEmail());

        return this.personPersistence.update(person);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Person> read(Long id) {
        if (!this.existPerson(id)){
            throw new NotFountException("This user is not register in the database");
        }
        return this.personPersistence.read(id);
    }

    @Override
    @Transactional
    public String delete(Long id) {
        if (!this.existPerson(id)){
            throw new NotFountException("Not found Person with id: "+ id);
        }
        this.imagesPersistence.deleteAll(id);
        return this.personPersistence.delete(id);
    }

    public boolean existPerson(Long id) {
        return personPersistence.existPersonById(id);
    }

    public void existEmail(String email) {
        if (personPersistence.existPersonByEmail(email)) {
            throw new ConflictException("This email is already register");
        }
    }

    public void isFineEmail(String email) {
        if (!ValidatorsUtil.isValidEmail(email)){
            throw new ConflictException("This Email is invalid");
        }
    }

    public void verifyPersonInfo(Person person) {
        if (ValidatorsUtil.isNullOrEmpty(person.getName()) || ValidatorsUtil.isNullOrEmpty(person.getLastName()) ||
                ValidatorsUtil.isNullOrEmpty(person.getEmail()) || ValidatorsUtil.isNullOrEmpty(person.getPhone()) ||
                ValidatorsUtil.isNullOrEmpty(person.getAddress()) ||
                ValidatorsUtil.isNullOrEmpty(person.getLocation())) {

            throw new ConflictException("The data is incorrect");
        }
    }
}
