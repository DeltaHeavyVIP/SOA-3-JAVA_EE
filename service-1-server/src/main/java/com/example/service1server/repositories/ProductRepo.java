package com.example.service1server.repositories;

import com.example.objects.common.FilterDto;
import com.example.service1server.model.Person;
import com.example.service1server.model.Product;
import com.example.service1server.utils.Filter;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
public class ProductRepo {

    @Inject
    private EntityManagerProvider entityManagerProvider;

    public Product getById(Integer id) {
        return entityManagerProvider.getEntityManager().find(Product.class, id);
    }

    public Product save(Product product) {
        entityManagerProvider.getEntityManager().persist(product);
        entityManagerProvider.getEntityManager().flush();
        return product;
    }

    @Transactional
    public void deleteById(Integer id) {
        entityManagerProvider.getEntityManager().remove(entityManagerProvider.getEntityManager().find(Product.class, id));
    }

    @Transactional
    public List<Product> findAll(FilterDto filterRequestDto) {
        CriteriaBuilder cb = entityManagerProvider.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Product> cr = cb.createQuery(Product.class);
        Root<Product> root = cr.from(Product.class);
        cr.select(root);
        cr.orderBy(Filter.getOrdersByNames(cb, root, filterRequestDto.getOrderBy()));

        Query query = entityManagerProvider.getEntityManager().createQuery(cr);
        return query.getResultList();
    }

    @Transactional
    public Long countAllByPriceAfter(Long price) {
        Query q = entityManagerProvider.getEntityManager().createQuery("SELECT count(c) from Product as c\n" +
                "where c.price > :price");
        q.setParameter("price", price);
        try {
            return (Long) q.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Transactional
    public List<Product> findAllByNameContaining(String substring) {
        CriteriaBuilder builder = entityManagerProvider.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Product> query = builder.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);
        query.where(builder.like(root.get("name"), "%" + substring + "%"));
        try {
            return entityManagerProvider.getEntityManager().createQuery(query).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

}
