/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lief_relief.worker;

import com.lief_relief.dao.ProductDAO;
import com.lief_relief.entities.Product;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author oliyn
 */
@Named
@ViewScoped
public class EditProductListWorkerBB implements Serializable{
    private static final String PAGE_GO_TO_PRODUCT_LIST = "products-list?faces-redirect=true";

    private Product product;
    private UploadedFile uploadedImage;
    
    @EJB
    ProductDAO productDAO;

    @Inject
    ExternalContext extcontext;
    @Inject
    FacesContext context;
    
    public Product getProduct() {
        HttpSession session = (HttpSession) extcontext.getSession(true);
        product = (Product) session.getAttribute("editProduct");
        
        return product;
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

    public String editProduct(){
        
        productDAO.merge(product);

        return PAGE_GO_TO_PRODUCT_LIST;
    }
}
