package com.lief_relief.product;

import com.lief_relief.dao.ProductDAO;
import com.lief_relief.entities.Product;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ejb.EJB;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.Flash;
import jakarta.servlet.http.HttpSession;

@Named
@RequestScoped
public class ProductListBB {
	private static final String PAGE_PERSON_EDIT = "productEdit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = "productList?faces-redirect=true";
        private static final String PAGE_GO_TO_PRODUCT = "product?faces-redirect=true";

	private String productName;
		
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	ProductDAO productDAO;
		
	public String getProductName() {
		return productName;
	}

	public void setProductName(String typeName) {
		this.productName = typeName;
	}
        
	public List<Product> getList(){
		
		//1. Prepare search params
		Map<String,Object> searchParams = new HashMap<>();
		
		if (productName != null && productName.length() > 0){
			searchParams.put("productName", productName);
		}
		
		//2. Get list
		List<Product> list = productDAO.getList(searchParams);
		
		return list;
	}

	public String newProduct(){
		Product product = new Product();
		
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash	
		flash.put("product", product);
		
		return PAGE_PERSON_EDIT;
	}

	public String editProduct(Product product){
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("person", person);
		
		//2. Pass object through flash 
		flash.put("product", product);
		
		return PAGE_PERSON_EDIT;
	}

	public String deleteProduct(Product product){
		productDAO.remove(product);
		return PAGE_STAY_AT_THE_SAME;
	}
        
        public String showProduct(Product product){
            HttpSession session = (HttpSession) extcontext.getSession(true);
            session.setAttribute("product", product);
            return PAGE_GO_TO_PRODUCT;
        }
}
