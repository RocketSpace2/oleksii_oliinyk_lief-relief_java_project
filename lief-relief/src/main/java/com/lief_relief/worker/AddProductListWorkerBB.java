/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lief_relief.worker;

import com.lief_relief.dao.ProductDAO;
import com.lief_relief.dao.TypeDAO;
import com.lief_relief.entities.Product;
import com.lief_relief.entities.Type;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author oliyn
 */
@Named
@ViewScoped
public class AddProductListWorkerBB implements Serializable{
    private static final String PAGE_GO_TO_PRODUCT_LIST = "products-list?faces-redirect=true";

    private Product product = new Product();
    private UploadedFile uploadedImage;
    private String name;
    private String description;
    private float price;
    private int idType;
    
    @EJB
    ProductDAO productDAO;
    @EJB 
    TypeDAO typeDAO;
    
    @Inject
    FacesContext context;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }
    
    public UploadedFile getUploadedImage() {
        return uploadedImage;
    }
    
    public void setUploadedImage(UploadedFile uploadedImage) {
        if(uploadedImage != null){
            this.uploadedImage = uploadedImage;
            try (InputStream inputStream = uploadedImage.getInputStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

               byte[] buffer = new byte[1024];
               int bytesRead;
               while ((bytesRead = inputStream.read(buffer)) != -1) {
                   outputStream.write(buffer, 0, bytesRead);
               }

               byte[] fileContent = outputStream.toByteArray();
               product.setImage(fileContent); 

           } catch (IOException e) {
               context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File conversion error", null));
           }
        }
    }

    public String newProduct(){
        
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        
        Map<String,Object> searchParams = new HashMap<>();
        searchParams.put("idType", idType);
        
        List<Type> type = typeDAO.getList(searchParams);
        product.setType(type.get(0));
        
        productDAO.create(product);
        
        return PAGE_GO_TO_PRODUCT_LIST;
    }
}
