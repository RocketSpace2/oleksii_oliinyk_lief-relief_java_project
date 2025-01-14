/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lief_relief.dao;

import com.lief_relief.entities.Order;
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
public class OrderDAO {
    private final static String UNIT_NAME = "jsfcourse-mariaDS";

    // Dependency injection (no setter method is needed)
    @PersistenceContext(unitName = UNIT_NAME)
    protected EntityManager em;

    public void create(Order order) {
            em.persist(order);
    }

    public Order merge(Order order) {
            return em.merge(order);
    }

    public void remove(Order order) {
            em.remove(em.merge(order));
    }

    public Order find(Object id) {
            return em.find(Order.class, id);
    }

    public List<Order> getList(Map<String, Object> searchParams) {
            List<Order> list = null;

            // 1. Build query string with parameters
            String select = "select o from Order o JOIN FETCH o.orderProductCollection ";
            
            String where = "";

            // search for surname
            Integer idState = (Integer) searchParams.get("idState");
            Integer idUser = (Integer) searchParams.get("idUser");
            if (idState != null) {
                    where += "where o.state.idState = :idState ";
            }
            if (idUser != null) {
                    where += "and o.user.idUser = :idUser ";
            }

            // 2. Create query object
            Query query = em.createQuery(select + where);

            // 3. Set configured parameters
            if (idState != null) {
                    query.setParameter("idState", idState);
            }
            if (idUser != null) {
                    query.setParameter("idUser", idUser);
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
