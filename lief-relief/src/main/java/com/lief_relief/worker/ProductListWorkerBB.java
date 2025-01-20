/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lief_relief.worker;

import com.lief_relief.dao.ProductDAO;
import com.lief_relief.entities.Product;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author oliyn
 */
@Named
@ViewScoped
public class ProductListWorkerBB implements Serializable{
    private static final String PAGE_STAY_AT_THE_SAME = "products-list?faces-redirect=true";
    private static final String PAGE_GO_TO_EDIT_PRODUCT = "edit-product?faces-redirect=true";

    private String productName;
    private int productType;

    @Inject
    ExternalContext extcontext;

    @EJB
    ProductDAO productDAO;
    
    @PostConstruct
    public void init() {
        HttpSession session = (HttpSession) extcontext.getSession(true);
        productName = (String) session.getAttribute("productName");
        productType = session.getAttribute("productType") != null ? (int) session.getAttribute("productType") : 0;
    }

    public String getProductName() {
            return productName;
    }

    public void setProductName(String typeName) {
            this.productName = typeName;
    }
    
    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public List<Product> getList(){
            Map<String,Object> searchParams = new HashMap<>();

            if (productName != null && productName.length() > 0){
                    searchParams.put("productName", productName);
            }
            
            if (productType != 0){
                    searchParams.put("productType", productType);
            }

            //2. Get list
            List<Product> list = productDAO.getList(searchParams);

            return list;
    }

    public String editProduct(Product product){
            HttpSession session = (HttpSession) extcontext.getSession(true);
            session.setAttribute("editProduct", product);

            return PAGE_GO_TO_EDIT_PRODUCT;
    }

    public String deleteProduct(Product product){
            productDAO.remove(product);
            return PAGE_STAY_AT_THE_SAME;
    }
    
    public String search(){
        HttpSession session = (HttpSession) extcontext.getSession(true);
        session.setAttribute("productName", productName);
        session.setAttribute("productType", productType);
        return PAGE_STAY_AT_THE_SAME;
    }
}
