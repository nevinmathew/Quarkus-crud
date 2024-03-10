package org.acme.repository;

import org.acme.model.Person;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PersonRepository implements PanacheRepository<Person> {
 Person findByName(String name) {
     return find("name", name).firstResult();
 }
}
