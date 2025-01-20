/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lief_relief.lazy;

import com.lief_relief.entities.Product;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;
import java.util.Map;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

/**
 *
 * @author oliyn
 */
@Stateless
public class LazyCustomerDataModel extends LazyDataModel<Product>{
    private final static String UNIT_NAME = "jsfcourse-mariaDS";
    
    @PersistenceContext(unitName = UNIT_NAME)
    protected EntityManager em;
    
    @Override
    public List<Product> load(int offset, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        
        List<Product> list = null;
        String select = "select p from Product p ";

        // 2. Create query object
        Query query = em.createQuery(select);

        // 4. Execute query and retrieve list of Person objects
        try {
            query.setFirstResult(offset);
            query.setMaxResults(pageSize);
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public int count(Map<String, FilterMeta> map) {
        List<Product> list = null;
        
        String select = "select count(p) from Product p";
        Query query = em.createQuery(select);
        try {
            return ((Long) query.getSingleResult()).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
}
