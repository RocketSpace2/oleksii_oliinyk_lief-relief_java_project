/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lief_relief.dao;

import com.lief_relief.entities.CatalogUser;
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
public class CatalogUserDAO {
    private final static String UNIT_NAME = "jsfcourse-mariaDS";

    // Dependency injection (no setter method is needed)
    @PersistenceContext(unitName = UNIT_NAME)
    protected EntityManager em;

    public void create(CatalogUser catalogUser) {
            em.persist(catalogUser);
    }

    public CatalogUser merge(CatalogUser catalogUser) {
            return em.merge(catalogUser);
    }

    public void remove(CatalogUser catalogUser) {
            em.remove(em.merge(catalogUser));
    }

    public CatalogUser find(Object id) {
            return em.find(CatalogUser.class, id);
    }

    public List<CatalogUser> getList(Map<String, Object> searchParams) {
            List<CatalogUser> list = null;

            // 1. Build query string with parameters
            String select = "select cu ";
            
            String from = "from CatalogUser cu ";
            
            String where = "";
            
            String orderBy = "order by cu.user.idUser, cu.catalog.idRole";
            // search for surname
            String login = null;
            if(searchParams != null){
                
                login = (String) searchParams.get("login");
                
                if (login != null) {
                        if (where.isEmpty()) {
                                where = "where ";
                        } else {
                                where += "and ";
                        }
                        where += "cu.user.login like :login ";
                }
            }
            

            // 2. Create query object
            Query query = em.createQuery(select + from + where + orderBy);

            // 3. Set configured parameters
            if (login != null) {
                    query.setParameter("login", login + "%");
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
