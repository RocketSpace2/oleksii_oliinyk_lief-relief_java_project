/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jfcourse.user_list;

import com.jfcourse.authentication.LoginBB;
import com.jsfcourse.dao.CatalogUserDAO;
import com.jsfcourse.entities.CatalogUser;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author oliyn
 */
@Named
@RequestScoped
public class UserListBB {
    private static final String PAGE_STAY_AT_THE_SAME = null;
    
    @EJB 
    CatalogUserDAO catalogUserDAO;
    @Inject
    LoginBB loginBB;
    
    private boolean activated = false;
    private boolean deactivated = false;
    private String login;
    private List<CatalogUser> catalogUsers;
    private Map<String,Object> searchParams;
    
    
    public boolean getActivated() {
        return activated;
    }

    public void setActivated() {
        this.activated = true;
        this.deactivated = false;
    }

    public boolean getDeactivated() {
        return deactivated;
    }

    public void setDeactivated() {
        this.deactivated = true;
        this.activated = false;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<CatalogUser> getCatalogUsers() {
        setCatalogUsers();
//        if(!activated && !deactivated){
//            
//        }else if(activated){
//        
//        }else{
//        
//        }
        
        return catalogUsers;
    }
    
    public void setCatalogUsers(){
        catalogUsers = catalogUserDAO.getList(searchParams);
        
        searchParams = null;
        
        Iterator<CatalogUser> iterator = this.catalogUsers.iterator();

        while (iterator.hasNext()) {
            CatalogUser catalogUser = iterator.next();
            if (catalogUser.getUser().getLogin().equals(loginBB.getCurrentUser().getLogin())) {
                iterator.remove();
            }
        }
        
        if(activated){
            iterator = this.catalogUsers.iterator();
            
            while (iterator.hasNext()) {
            CatalogUser catalogUser = iterator.next();
                if (catalogUser.getDateOfDeactivation() != null) {
                    iterator.remove();
                }
            }
        }else if(deactivated){
            iterator = this.catalogUsers.iterator();
            
            while (iterator.hasNext()) {
            CatalogUser catalogUser = iterator.next();
                if (catalogUser.getDateOfDeactivation() == null) {
                    iterator.remove();
                }
            }   
        }
    }
    
    public void searchLogin(){
        deactivated = false;
        activated = false;
        
        searchParams = new HashMap<String, Object>();
		
        if (login != null && login.length() > 0){
                searchParams.put("login", login);
        }
    }

    public void setCatalogUser(List<CatalogUser> catalogUser) {
        this.catalogUsers = catalogUser;
    }
}
