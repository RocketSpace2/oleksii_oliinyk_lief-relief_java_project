package com.jsfcourse.product;

import com.jsfcourse.dao.ProductDAO;
import com.jsfcourse.entities.Product;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ejb.EJB;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.Flash;
import java.io.IOException;

@Named
@RequestScoped
public class SingleProductBB {
    private static final String PAGE_STAY_AT_THE_SAME = "productList?faces-redirect=true";
    private static final String PAGE_GO_TO_PRODUCTS_LIST = "productList?faces-redirect=true";
    
    private Product product;

    @Inject
    Flash flash;

    @EJB
    ProductDAO productDAO;
    
    public Product getProduct(){
        this.product = (Product) flash.get("product");
        return this.product;
    }
}
