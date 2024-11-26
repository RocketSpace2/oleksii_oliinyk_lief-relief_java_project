/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jsfcourse.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author oliyn
 */
@Entity
@Table(name = "addres")
@NamedQueries({
    @NamedQuery(name = "Addres.findAll", query = "SELECT a FROM Addres a"),
    @NamedQuery(name = "Addres.findByIdAddres", query = "SELECT a FROM Addres a WHERE a.idAddres = :idAddres"),
    @NamedQuery(name = "Addres.findByPostcode", query = "SELECT a FROM Addres a WHERE a.postcode = :postcode"),
    @NamedQuery(name = "Addres.findByCity", query = "SELECT a FROM Addres a WHERE a.city = :city"),
    @NamedQuery(name = "Addres.findByStreet", query = "SELECT a FROM Addres a WHERE a.street = :street"),
    @NamedQuery(name = "Addres.findByStreetNumber", query = "SELECT a FROM Addres a WHERE a.streetNumber = :streetNumber"),
    @NamedQuery(name = "Addres.findByApartmentNumber", query = "SELECT a FROM Addres a WHERE a.apartmentNumber = :apartmentNumber")})
public class Addres implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_addres")
    private Integer idAddres;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "postcode")
    private String postcode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "city")
    private String city;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "street")
    private String street;
    @Basic(optional = false)
    @NotNull
    @Column(name = "street_number")
    private int streetNumber;
    @Column(name = "apartment_number")
    private Integer apartmentNumber;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAddres")
    private Collection<User> userCollection;

    public Addres() {
    }

    public Addres(Integer idAddres) {
        this.idAddres = idAddres;
    }

    public Addres(Integer idAddres, String postcode, String city, String street, int streetNumber) {
        this.idAddres = idAddres;
        this.postcode = postcode;
        this.city = city;
        this.street = street;
        this.streetNumber = streetNumber;
    }

    public Integer getIdAddres() {
        return idAddres;
    }

    public void setIdAddres(Integer idAddres) {
        this.idAddres = idAddres;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public Integer getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(Integer apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAddres != null ? idAddres.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Addres)) {
            return false;
        }
        Addres other = (Addres) object;
        if ((this.idAddres == null && other.idAddres != null) || (this.idAddres != null && !this.idAddres.equals(other.idAddres))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jsfcourse.entities.Addres[ idAddres=" + idAddres + " ]";
    }
    
}
