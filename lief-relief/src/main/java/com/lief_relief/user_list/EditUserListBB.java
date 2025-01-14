/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lief_relief.user_list;

import com.lief_relief.dao.CatalogUserDAO;
import com.lief_relief.entities.CatalogUser;
import com.lief_relief.entities.CatalogUserPK;
import com.lief_relief.entities.User;
import jakarta.ejb.EJB;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author oliyn
 */
@Named
@ViewScoped
public class EditUserListBB implements Serializable{
    private static final String PAGE_GO_TO_USER_LIST = "user-list?faces-redirect=true";
    
    @EJB 
    CatalogUserDAO catalogUserDAO;
    @Inject
    ExternalContext extcontext;
    
    private int changeIdRole;
    private int currentIdRole;
    private CatalogUser catalogUser;
    private List<CatalogUser> catalogUsers;
    

    public int getChangeIdRole() {
        return changeIdRole;
    }

    public void setChangeIdRole(int idRole) {
        this.changeIdRole = idRole;
    }
    
    public CatalogUser getCatalogUser() {
        
        HttpSession session = (HttpSession) extcontext.getSession(true);
        User user = (User) session.getAttribute("user");
        
        HashMap<String,Object> searchParams = new HashMap<>();
        searchParams.put("login", user.getLogin());
        
        catalogUsers = catalogUserDAO.getList(searchParams);
        
        for(CatalogUser catalogUser : catalogUsers){
            if(catalogUser.getDateOfDeactivation() == null){
                this.catalogUser = catalogUser;
                changeIdRole = this.catalogUser.getCatalog().getIdRole();
                currentIdRole = changeIdRole; 
            }
        }
        
        return catalogUser;
    }
    
    public String editRole(){
        if(changeIdRole != currentIdRole){
            LocalDateTime now = LocalDateTime.now();
            Date date;
            
            for(CatalogUser catalogUser : catalogUsers){
                if(catalogUser.getDateOfDeactivation() == null && catalogUser.getCatalog().getIdRole() != 1){
                    date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
                    catalogUser.setDateOfDeactivation(date);
                    
                    catalogUserDAO.merge(catalogUser);
                }
            } 
            
            if(changeIdRole != 1){
                CatalogUser createdCatalogUser = new CatalogUser();

                CatalogUserPK createdCatalogUserPK = new CatalogUserPK();

                createdCatalogUserPK.setIdUser(catalogUsers.get(0).getUser().getIdUser());

                if(changeIdRole == 2){
                    createdCatalogUserPK.setIdRole(2);
                }else if(changeIdRole == 3){
                    createdCatalogUserPK.setIdRole(3);
                }            

                createdCatalogUser.setCatalogUserPK(createdCatalogUserPK);

                date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());

                createdCatalogUser.setDateOfActivation(date);

                catalogUserDAO.merge(createdCatalogUser);  
            }
        }
        
        return PAGE_GO_TO_USER_LIST;
    }    
}
