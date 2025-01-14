/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lief_relief.authentication;

import com.lief_relief.dao.UserDAO;
import com.lief_relief.entities.CatalogUser;
import com.lief_relief.entities.User;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.simplesecurity.RemoteClient;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author oliyn
 */
@Named
@RequestScoped
public class LoginBB {
    private static final String PAGE_MAIN = "/public/main-page?faces-redirect=true";
    private static final String PAGE_STAY_AT_THE_SAME = null;
    
    @EJB
    UserDAO userDAO;

    private String login;
    private String pass;

    public String getLogin() {
            return login;
    }

    public void setLogin(String login) {
            this.login = login;
    }

    public String getPass() {
            return pass;
    }

    public void setPass(String pass) {
            this.pass = pass;
    }

    public String doLogin(String login, String pass) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            
            Map<String,Object> searchParams = new HashMap<>();
            // 1. verify login and password - get User from "database"
            searchParams.put("login", login);
            searchParams.put("pass", pass);
            List<User> users = userDAO.getList(searchParams, true);

            
            if (users.isEmpty()) {
                    ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "Niepoprawny login lub hasło", null));
                    return PAGE_STAY_AT_THE_SAME;
            }
            
            User user = users.get(0);

            // 3. if logged in: get User roles, save in RemoteClient and store it in session

            RemoteClient<User> client = new RemoteClient<>(); //create new RemoteClient
            client.setDetails(user);

            List<CatalogUser> catalogUsers = new ArrayList<>(user.getCatalogUserCollection()); //get User roles 

            for (CatalogUser catalogUser: catalogUsers) {
                if(catalogUser.getDateOfDeactivation() == null){
                    client.getRoles().add(catalogUser.getCatalog().getRoleName());
                }   
            }

            //store RemoteClient with request info in session (needed for SecurityFilter)
            HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
            client.store(request);

            // and enter the system (now SecurityFilter will pass the request)
            return PAGE_MAIN;
    }

    public String doLogout(){
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                            .getExternalContext().getSession(true);
            //Invalidate session
            // - all objects within session will be destroyed
            // - new session will be created (with new ID)
            session.invalidate();
            return PAGE_MAIN;
    }
    
    public Boolean isInRole(String role){
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
        HttpSession session = request.getSession();
        
        RemoteClient client = RemoteClient.load(session);
        if(client != null){
            return client.isInRole(role) ;            
        }
        return false;
    }
    
    public User getCurrentUser(){
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
        HttpSession session = request.getSession();
        
        RemoteClient client = RemoteClient.load(session);
        User user = null;
        if(client != null){
            user = (User) client.getDetails();
        }
        
        return user;
    }
}
