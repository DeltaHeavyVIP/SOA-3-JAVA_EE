package com.example.service1server.repositories;

import com.example.service1server.model.Coordinates;
import com.example.service1server.model.Location;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Stateless
public class CoordinatesRepo {

    @Inject
    private EntityManagerProvider entityManagerProvider;

    public Coordinates save(Coordinates coordinates) {
        entityManagerProvider.getEntityManager().persist(coordinates);
        entityManagerProvider.getEntityManager().flush();
        return coordinates;
    }

    @Transactional
    public Coordinates findFirstByXAndY(float x, Float y) {
        Query q = entityManagerProvider.getEntityManager().createQuery("SELECT c FROM Coordinates c WHERE c.x =:x AND c.y =:y");
        q.setParameter("x", x);
        q.setParameter("y", y);
        try {
            return (Coordinates) q.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

}
