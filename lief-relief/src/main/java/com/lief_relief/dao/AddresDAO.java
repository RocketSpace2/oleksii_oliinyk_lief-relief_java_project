/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lief_relief.dao;

import com.lief_relief.entities.Addres;
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
public class AddresDAO {
    private final static String UNIT_NAME = "jsfcourse-mariaDS";

    // Dependency injection (no setter method is needed)
    @PersistenceContext(unitName = UNIT_NAME)
    protected EntityManager em;

    public void create(Addres addres) {
            em.persist(addres);
    }

    public Addres merge(Addres addres) {
            return em.merge(addres);
    }

    public void remove(Addres addres) {
            em.remove(em.merge(addres));
    }

    public Addres find(Object id) {
            return em.find(Addres.class, id);
    }

    public List<Addres> getList(Map<String, Object> searchParams, boolean join) {
            List<Addres> list = null;

            // 1. Build query string with parameters
            String select = "select u ";
            
            String from;
            
            if(join){
                from = "from User u LEFT JOIN FETCH u.catalogUserCollection ";
            }else{
                from = "from User u ";
            }
            
            String where = "";

            // search for surname
            String login = (String) searchParams.get("login");
            if (login != null) {
                    if (where.isEmpty()) {
                            where = "where ";
                    } else {
                            where += "and ";
                    }
                    where += "u.login like :login ";
            }

            // 2. Create query object
            Query query = em.createQuery(select + from + where);

            // 3. Set configured parameters
            if (login != null) {
                    query.setParameter("login", login+"%");
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
