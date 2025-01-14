package com.lief_relief.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ejb.EJB;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.Flash;

import com.lief_relief.dao.TypeDAO;
import com.lief_relief.entities.Type;

@Named
@RequestScoped
public class TypeListBB {
	private static final String PAGE_PERSON_EDIT = "typeEdit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String typeName;
		
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	TypeDAO typeDAO;
		
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public List<Type> getFullList(){
		return typeDAO.getFullList();
	}

	public List<Type> getList(){
		List<Type> list = null;
		
		//1. Prepare search params
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (typeName != null && typeName.length() > 0){
			searchParams.put("typeName", typeName);
		}
		
		//2. Get list
		list = typeDAO.getList(searchParams);
		
		return list;
	}

	public String newType(){
		Type type = new Type();
		
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash	
		flash.put("type", type);
		
		return PAGE_PERSON_EDIT;
	}

	public String editType(Type type){
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash 
		flash.put("type", type);
		
		return PAGE_PERSON_EDIT;
	}

	public String deleteType(Type type){
		typeDAO.remove(type);
		return PAGE_STAY_AT_THE_SAME;
	}
}
