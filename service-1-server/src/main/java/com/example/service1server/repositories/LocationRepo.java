package com.example.service1server.repositories;

import com.example.service1server.model.Location;
import com.example.service1server.model.Product;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Stateless
public class LocationRepo {

    @Inject
    private EntityManagerProvider entityManagerProvider;

    public Location save(Location location) {
        entityManagerProvider.getEntityManager().persist(location);
        entityManagerProvider.getEntityManager().flush();
        return location;
    }
    @Transactional
    public Location findFirstByXAndYAndZ(int x, Double y, double z) {
        Query q = entityManagerProvider.getEntityManager().createQuery("SELECT l FROM Location l WHERE l.x =:x AND l.y =:y AND l.z =:z");
        q.setParameter("x", x);
        q.setParameter("y", y);
        q.setParameter("z", z);
        try {
            return (Location) q.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
