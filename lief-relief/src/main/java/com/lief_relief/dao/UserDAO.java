/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lief_relief.dao;

import com.lief_relief.entities.User;
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
public class UserDAO {
    private final static String UNIT_NAME = "jsfcourse-mariaDS";

    // Dependency injection (no setter method is needed)
    @PersistenceContext(unitName = UNIT_NAME)
    protected EntityManager em;

    public void create(User user) {
            em.persist(user);
    }

    public User merge(User user) {
            return em.merge(user);
    }

    public void remove(User user) {
            em.remove(em.merge(user));
    }

    public User find(Object id) {
            return em.find(User.class, id);
    }

    public List<User> getList(Map<String, Object> searchParams, boolean join) {
            List<User> list = null;

            // 1. Build query string with parameters
            String select = "select u ";
            
            String from;
            
            if(join){
                from = "from User u LEFT JOIN FETCH u.catalogUserCollection ";
            }else{
                from = "from User u ";
            }
            
            String where = "";
            
            String login = (String) searchParams.get("login");
            String pass = (String) searchParams.get("pass");
            Integer idAddres = (Integer)searchParams.get("idAddres");
            // search for surname
            if (login != null || idAddres != null) {
                    if (where.isEmpty()) {
                            where = "where ";
                    } else {
                            where += "and ";
                    }
                    if(login != null){
                        where += "u.login = :login ";
                    }else{
                        where += "u.addres.idAddres = :idAddres ";
                    }
                    
            }
            
            if (pass != null) {
                    if (where.isEmpty()) {
                            where = "where u.password = :pass ";
                    } else {
                            where += "and u.password = :pass ";
                    }
            }

            // 2. Create query object
            Query query = em.createQuery(select + from + where);

            // 3. Set configured parameters
            if (login != null || idAddres != null) {
                 if(login != null){
                    query.setParameter("login", login);
                }else{
                    query.setParameter("idAddres", idAddres);
                }
            }
            if (pass != null) {
                query.setParameter("pass", pass);
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
