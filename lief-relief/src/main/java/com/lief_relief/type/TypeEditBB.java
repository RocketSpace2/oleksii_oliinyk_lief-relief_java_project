package com.lief_relief.type;

import java.io.IOException;
import java.io.Serializable;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import com.lief_relief.dao.TypeDAO;
import com.lief_relief.entities.Type;

@Named
@ViewScoped
public class TypeEditBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_PERSON_LIST = "typeList?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private Type type = new Type();
	private Type loaded = null;

	@EJB
	TypeDAO productDAO;

	@Inject
	FacesContext context;

	@Inject
	Flash flash;

	public Type getType() {
		return type;
	}

	public void onLoad() throws IOException {
		// 1. load person passed through session
		// HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		// loaded = (Person) session.getAttribute("person");

		// 2. load person passed through flash
		loaded = (Type) flash.get("type");

		// cleaning: attribute received => delete it from session
		if (loaded != null) {
			type = loaded;
			// session.removeAttribute("person");
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));
			// if (!context.isPostback()) { //possible redirect
			// context.getExternalContext().redirect("personList.xhtml");
			// context.responseComplete();
			// }
		}

	}

	public String saveData() {
		// no Person object passed
		if (loaded == null) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (type.getIdType() == null) {
				// new record
				productDAO.create(type);
			} else {
				// existing record
				productDAO.merge(type);
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_PERSON_LIST;
	}
}
