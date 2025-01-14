/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lief_relief.dao;

import com.lief_relief.entities.OrderProduct;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;
import java.util.Map;

/**
 *
 * @author oliyn
 */
@Stateless
public class OrderProductDAO {
    private final static String UNIT_NAME = "jsfcourse-mariaDS";

    // Dependency injection (no setter method is needed)
    @PersistenceContext(unitName = UNIT_NAME)
    protected EntityManager em;

    public void create(OrderProduct order) {
            em.persist(order);
    }

    public OrderProduct merge(OrderProduct order) {
            return em.merge(order);
    }

    public void remove(OrderProduct order) {
            em.remove(em.merge(order));
    }

    public OrderProduct find(Object id) {
            return em.find(OrderProduct.class, id);
    }

    public List<OrderProduct> getList(Map<String, Object> searchParams) {
            List<OrderProduct> list = null;

            // 1. Build query string with parameters
            String select = "select op from OrderProduct op ";
            
            String where = "";

            // search for surname
            Integer idOrder = (Integer) searchParams.get("idOrder");
            if (idOrder != null) {
                    where += "where op.order.idOrder = :idOrder ";
            }

            // 2. Create query object
            Query query = em.createQuery(select + where);

            // 3. Set configured parameters
            if (idOrder != null) {
                    query.setParameter("idOrder", idOrder);
            }

            // 4. Execute query and retrieve list of Person objects
            try {
                    list = query.getResultList();
            } catch (Exception e) {
                    e.printStackTrace();
            }

            return list;
    }
}
