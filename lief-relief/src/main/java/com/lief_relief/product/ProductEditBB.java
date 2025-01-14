package com.lief_relief.product;

import com.lief_relief.dao.ProductDAO;
import com.lief_relief.dao.TypeDAO;
import java.io.IOException;
import java.io.Serializable;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;

import com.lief_relief.entities.Product;
import jakarta.faces.event.ValueChangeEvent;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import org.primefaces.model.file.UploadedFile;

@Named
@ViewScoped
public class ProductEditBB implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String PAGE_PERSON_LIST = "productList?faces-redirect=true";
    private static final String PAGE_STAY_AT_THE_SAME = null;

    private Product product = new Product();
    private Product loaded = null;
    private Integer selectedIdType;
    private UploadedFile uploadedImage;

    @EJB
    TypeDAO typeDAO; 

    @EJB
    ProductDAO productDAO;

    @Inject
    FacesContext context;

    @Inject
    Flash flash;

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

    public Integer getSelectedIdType() {
        return selectedIdType;
    }

    public void setSelectedIdType(Integer selectedIdType) {
        this.selectedIdType = selectedIdType;

        product.setType(typeDAO.find(this.selectedIdType));
    }

    public Product getProduct() {
            return product;
    }

    public void onLoad() throws IOException {
            // 1. load person passed through session
            // HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
            // loaded = (Person) session.getAttribute("person");

            // 2. load person passed through flash
            loaded = (Product) flash.get("product");

            // cleaning: attribute received => delete it from session
            if (loaded != null) {
                    product = loaded;
                    // session.removeAttribute("person");
            } else {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));
                    // if (!context.isPostback()) { //possible redirect
                    // context.getExternalContext().redirect("personList.xhtml");
                    // context.responseComplete();
                    // }
            }

    }

    public String saveData() {


        // no Person object passed
        if (loaded == null) {
                return PAGE_STAY_AT_THE_SAME;
        }

        try {
                if (product.getIdProduct() == null) {
                        // new record
                        productDAO.create(product);
                } else {
                        // existing record
                        productDAO.merge(product);
                }
        } catch (Exception e) {
                e.printStackTrace();
                context.addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
                return PAGE_STAY_AT_THE_SAME;
        }

        return PAGE_PERSON_LIST;
    }
       
}
