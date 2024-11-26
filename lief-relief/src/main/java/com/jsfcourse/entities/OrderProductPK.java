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
public class OrderProductPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "id_product")
    private int idProduct;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_order")
    private int idOrder;

    public OrderProductPK() {
    }

    public OrderProductPK(int idProduct, int idOrder) {
        this.idProduct = idProduct;
        this.idOrder = idOrder;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idProduct;
        hash += (int) idOrder;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderProductPK)) {
            return false;
        }
        OrderProductPK other = (OrderProductPK) object;
        if (this.idProduct != other.idProduct) {
            return false;
        }
        if (this.idOrder != other.idOrder) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jsfcourse.entities.OrderProductPK[ idProduct=" + idProduct + ", idOrder=" + idOrder + " ]";
    }
    
}
