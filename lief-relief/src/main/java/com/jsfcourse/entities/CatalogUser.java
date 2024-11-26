/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jsfcourse.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author oliyn
 */
@Entity
@Table(name = "catalog_user")
@NamedQueries({
    @NamedQuery(name = "CatalogUser.findAll", query = "SELECT c FROM CatalogUser c"),
    @NamedQuery(name = "CatalogUser.findByIdRole", query = "SELECT c FROM CatalogUser c WHERE c.catalogUserPK.idRole = :idRole"),
    @NamedQuery(name = "CatalogUser.findByIdUser", query = "SELECT c FROM CatalogUser c WHERE c.catalogUserPK.idUser = :idUser"),
    @NamedQuery(name = "CatalogUser.findByDateOfActivation", query = "SELECT c FROM CatalogUser c WHERE c.dateOfActivation = :dateOfActivation"),
    @NamedQuery(name = "CatalogUser.findByDateOfDeactivation", query = "SELECT c FROM CatalogUser c WHERE c.dateOfDeactivation = :dateOfDeactivation")})
public class CatalogUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CatalogUserPK catalogUserPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_of_activation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfActivation;
    @Column(name = "date_of_deactivation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfDeactivation;
    @JoinColumn(name = "id_role", referencedColumnName = "id_role", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Catalog catalog;
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;

    public CatalogUser() {
    }

    public CatalogUser(CatalogUserPK catalogUserPK) {
        this.catalogUserPK = catalogUserPK;
    }

    public CatalogUser(CatalogUserPK catalogUserPK, Date dateOfActivation) {
        this.catalogUserPK = catalogUserPK;
        this.dateOfActivation = dateOfActivation;
    }

    public CatalogUser(int idRole, int idUser) {
        this.catalogUserPK = new CatalogUserPK(idRole, idUser);
    }

    public CatalogUserPK getCatalogUserPK() {
        return catalogUserPK;
    }

    public void setCatalogUserPK(CatalogUserPK catalogUserPK) {
        this.catalogUserPK = catalogUserPK;
    }

    public Date getDateOfActivation() {
        return dateOfActivation;
    }

    public void setDateOfActivation(Date dateOfActivation) {
        this.dateOfActivation = dateOfActivation;
    }

    public Date getDateOfDeactivation() {
        return dateOfDeactivation;
    }

    public void setDateOfDeactivation(Date dateOfDeactivation) {
        this.dateOfDeactivation = dateOfDeactivation;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (catalogUserPK != null ? catalogUserPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CatalogUser)) {
            return false;
        }
        CatalogUser other = (CatalogUser) object;
        if ((this.catalogUserPK == null && other.catalogUserPK != null) || (this.catalogUserPK != null && !this.catalogUserPK.equals(other.catalogUserPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jsfcourse.entities.CatalogUser[ catalogUserPK=" + catalogUserPK + " ]";
    }
    
}
