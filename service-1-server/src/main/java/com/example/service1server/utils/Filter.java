package com.example.service1server.utils;

import com.example.service1server.model.Product;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Filter {
    private static final List<String> columnsNames = new ArrayList<>();

    static {
        for (Field f : Product.class.getDeclaredFields()) {
            columnsNames.add(f.getName());
        }
    }

    public static List<javax.persistence.criteria.Order> getOrdersByNames(CriteriaBuilder cb, Root<Product> cr, Map<String, String> namePropertyMap) {
        if (namePropertyMap == null || namePropertyMap.isEmpty()) {
            return Collections.emptyList();
        }

        List<javax.persistence.criteria.Order> orders = new ArrayList<>();
        namePropertyMap.entrySet().stream()
                .filter(e -> columnsNames.contains(e.getKey()))
                .forEach(e -> {
                    if (e.getValue().equalsIgnoreCase("desc")) {
                        orders.add(cb.desc(cr.get(e.getKey())));
                    } else {
                        orders.add(cb.asc(cr.get(e.getKey())));
                    }
                });

        return orders;
    }
}
