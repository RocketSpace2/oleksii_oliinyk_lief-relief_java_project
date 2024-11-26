package com.jsfcourse.dao;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import com.jsfcourse.entities.Product;
import com.jsfcourse.entities.Type;

//DAO - Data Access Object for Person entity
//Designed to serve as an interface between higher layers of application and data.
//Implemented as stateless Enterprise Java bean - server side code that can be invoked even remotely.

@Stateless
public class TypeDAO {
	private final static String UNIT_NAME = "jsfcourse-mariaDS";

	// Dependency injection (no setter method is needed)
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public void create(Type type) {
		em.persist(type);
	}

	public Type merge(Type type) {
		return em.merge(type);
	}

	public void remove(Type type) {
		em.remove(em.merge(type));
	}

	public Type find(Object id) {
		return em.find(Type.class, id);
	}

	public List<Type> getFullList() {
		List<Type> list = null;

		Query query = em.createQuery("select t from Type t");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<Type> getList(Map<String, Object> searchParams) {
		List<Type> list = null;

		// 1. Build query string with parameters
		String select = "select t ";
		String from = "from Type t ";
		String where = "";

		// search for surname
		String typeName = (String) searchParams.get("typeName");
		if (typeName != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "t.typeName like :name ";
		}
                
		// 2. Create query object
		Query query = em.createQuery(select + from + where);

		// 3. Set configured parameters
		if (typeName != null) {
			query.setParameter("name", typeName+"%");
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
