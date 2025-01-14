/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lief_relief.authentication;

import com.lief_relief.dao.AddresDAO;
import com.lief_relief.dao.CatalogUserDAO;
import com.lief_relief.dao.CatalogUserPKDAO;
import com.lief_relief.dao.UserDAO;
import com.lief_relief.entities.Addres;
import com.lief_relief.entities.CatalogUser;
import com.lief_relief.entities.CatalogUserPK;
import com.lief_relief.entities.User;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author oliyn
 */
@Named
@RequestScoped
public class RegistrationBB {
    private static final String PAGE_MAIN = "/public/main-page?faces-redirect=true";
    private static final String PAGE_STAY_AT_THE_SAME = null;
    
    @EJB
    UserDAO userDAO;
    @EJB
    AddresDAO addresDAO;
    @EJB
    CatalogUserDAO catalogUserDAO;
    @EJB
    CatalogUserPKDAO catalogUserPKDAO;
    @Inject
    LoginBB loginBB;
    @Inject
    FacesContext context;
    
    

    private String login;
    private String pass;
    private String city;
    private String postcode;
    private String street;
    private int streetNumber;
    private int apartmentNumber;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public int getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(int apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    

    public String getLogin() {
            return login;
    }

    public void setLogin(String login) {
        Map<String,Object> searchParams = new HashMap<>();
        searchParams.put("login", login);
        
        
        if(userDAO.getList(searchParams, false).isEmpty()){
            this.login = login;
        }else{
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Podany login już istnieje", null)); 
        }
    }

    public String getPass() {
            return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    

    public String registrate() {
        if(login != null){
            User user = new User();
            user.setLogin(login);
            user.setPassword(pass);

            Addres addres = new Addres();
            addres.setCity(city);
            addres.setPostcode(postcode);
            addres.setStreet(street);
            addres.setStreetNumber(streetNumber);
            addres.setApartmentNumber(apartmentNumber);

            addresDAO.create(addres);

            user.setAddres(addres);

            userDAO.create(user);

            CatalogUserPK catalogUserPK = new CatalogUserPK();
            catalogUserPK.setIdRole(1);
            catalogUserPK.setIdUser(user.getIdUser());

            CatalogUser catalogUser = new CatalogUser();
            catalogUser.setCatalogUserPK(catalogUserPK);

            LocalDateTime now = LocalDateTime.now();
            Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());

            catalogUser.setDateOfActivation(date);

            catalogUserDAO.create(catalogUser);

            loginBB.doLogin(login, pass);

            return PAGE_MAIN;
        }
        
        return null;
    }
}
