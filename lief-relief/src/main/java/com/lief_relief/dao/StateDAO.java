package com.lief_relief.dao;

import com.lief_relief.entities.State;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

//DAO - Data Access Object for Person entity
//Designed to serve as an interface between higher layers of application and data.
//Implemented as stateless Enterprise Java bean - server side code that can be invoked even remotely.

@Stateless
public class StateDAO {
	private final static String UNIT_NAME = "jsfcourse-mariaDS";

	// Dependency injection (no setter method is needed)
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public void create(State state) {
		em.persist(state);
	}

	public State merge(State state) {
		return em.merge(state);
	}

	public void remove(State state) {
		em.remove(em.merge(state));
	}

	public State find(Object id) {
		return em.find(State.class, id);
	}

}
