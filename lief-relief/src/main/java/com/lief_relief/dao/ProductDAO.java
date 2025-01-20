package com.lief_relief.dao;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import com.lief_relief.entities.Product;

//DAO - Data Access Object for Person entity
//Designed to serve as an interface between higher layers of application and data.
//Implemented as stateless Enterprise Java bean - server side code that can be invoked even remotely.

@Stateless
public class ProductDAO {
	private final static String UNIT_NAME = "jsfcourse-mariaDS";

	// Dependency injection (no setter method is needed)
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public void create(Product product) {
		em.persist(product);
	}

	public Product merge(Product product) {
		return em.merge(product);
	}

	public void remove(Product product) {
		em.remove(em.find(Product.class, product.getIdProduct()));
	}

	public Product find(Object id) {
		return em.find(Product.class, id);
	}

	public List<Product> getList(Map<String, Object> searchParams) {
		List<Product> list = null;
                
		String select = "select p from Product p ";
		String where = "";

		// search for surname
		String productName = (String) searchParams.get("productName");
		if (productName != null) { 
			where += "where p.name like :productName ";
		}
                
                Integer productType = (Integer) searchParams.get("productType");
                if (productType != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "p.type.idType = :productType ";
		}
                
		// 2. Create query object
		Query query = em.createQuery(select + where);

		// 3. Set configured parameters
		if (productName != null) {
			query.setParameter("productName", productName + "%");
		}
                
                if (productType != null) {
			query.setParameter("productType", productType);
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
