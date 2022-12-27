package com.example.service1server.repositories;

import com.example.service1server.model.Person;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.ArrayList;

@Stateless
public class PersonRepo {

    @Inject
    private EntityManagerProvider entityManagerProvider;

    public Person save(Person person) {
        entityManagerProvider.getEntityManager().persist(person);
        entityManagerProvider.getEntityManager().flush();
        return person;
    }

    public Person findFirstByPassportID(String id) {
        return entityManagerProvider.getEntityManager().find(Person.class, id);
    }

    public ArrayList<String> findAllPersonUniqueName() {
        Query q = entityManagerProvider.getEntityManager().createQuery("select distinct p.name from Person as p");
        try {
            return (ArrayList<String>) q.getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

}
