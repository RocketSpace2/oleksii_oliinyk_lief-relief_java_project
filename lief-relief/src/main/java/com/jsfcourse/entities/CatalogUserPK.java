/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jsfcourse.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *
 * @author oliyn
 */
@Embeddable
public class CatalogUserPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "id_role")
    private int idRole;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_user")
    private int idUser;

    public CatalogUserPK() {
    }

    public CatalogUserPK(int idRole, int idUser) {
        this.idRole = idRole;
        this.idUser = idUser;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idRole;
        hash += (int) idUser;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CatalogUserPK)) {
            return false;
        }
        CatalogUserPK other = (CatalogUserPK) object;
        if (this.idRole != other.idRole) {
            return false;
        }
        if (this.idUser != other.idUser) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jsfcourse.entities.CatalogUserPK[ idRole=" + idRole + ", idUser=" + idUser + " ]";
    }
    
}
