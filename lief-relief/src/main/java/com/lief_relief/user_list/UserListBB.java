/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lief_relief.user_list;

import com.lief_relief.authentication.LoginBB;
import com.lief_relief.dao.AddresDAO;
import com.lief_relief.dao.CatalogUserDAO;
import com.lief_relief.dao.UserDAO;
import com.lief_relief.entities.Addres;
import com.lief_relief.entities.CatalogUser;
import com.lief_relief.entities.User;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
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
    private static final String PAGE_GO_TO_EDIT_USER_LIST = "edit-user-list?faces-redirect=true";
    
    @EJB 
    CatalogUserDAO catalogUserDAO;
    @EJB
    UserDAO userDAO;
    @EJB
    AddresDAO addresDAO;
    @Inject
    LoginBB loginBB;
    @Inject
    ExternalContext extcontext;
    
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
        
        return catalogUsers;
    }
    
    
    public void searchLogin(){
        deactivated = false;
        activated = false;
        
        searchParams = new HashMap<>();
		
        if (login != null && login.length() > 0){
                searchParams.put("login", login);
        }
    }
    
    
    public void deleteUser(CatalogUser catalogUser){
        User user =  catalogUser.getUser();
        
        Addres addres = user.getAddres();
        
        searchParams = new HashMap<>();
        searchParams.put("idAddres", addres.getIdAddres());
        
        List<User> users = userDAO.getList(searchParams, false);
        
        userDAO.remove(user);
        
        if(users.size() == 1){
            addresDAO.remove(addres);
        }        
    }
    
    
    public String editUser(User user){
        HttpSession session = (HttpSession) extcontext.getSession(true);
        session.setAttribute("user", user);
        
        return PAGE_GO_TO_EDIT_USER_LIST;
    }
    
}
