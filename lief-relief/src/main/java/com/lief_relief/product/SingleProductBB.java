package com.lief_relief.product;

import com.lief_relief.authentication.LoginBB;
import com.lief_relief.dao.OrderDAO;
import com.lief_relief.dao.OrderProductDAO;
import com.lief_relief.dao.ProductDAO;
import com.lief_relief.dao.StateDAO;
import com.lief_relief.entities.Order;
import com.lief_relief.entities.OrderProduct;
import com.lief_relief.entities.OrderProductPK;
import com.lief_relief.entities.Product;
import com.lief_relief.shoping_cart.ShopingCartBB;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ejb.EJB;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;

@Named
@RequestScoped
public class SingleProductBB {
    private static final String PAGE_GO_TO_PRODUCTS_LIST = "product-list?faces-redirect=true";
    private static final String PAGE_GO_TO_LOGIN = "login?faces-redirect=true";
    
    private Product product;
    private int amount;


    @Inject
    LoginBB loginBB;
    @Inject
    ShopingCartBB shopingCartBB;
    @Inject
    ExternalContext extcontext;
    @Inject
    FacesContext context;

    @EJB
    ProductDAO productDAO;
    @EJB
    OrderProductDAO orderProductDAO;
    @EJB
    OrderDAO orderDAO;
    @EJB
    StateDAO stateDAO;
    
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    public Product getProduct(){
        HttpSession session = (HttpSession) extcontext.getSession(true);
        return (Product) session.getAttribute("product");
    }
    
    public String addProduct(){
        if(loginBB.isInRole("user")){
            Order order = shopingCartBB.getOrder();
            HttpSession session = (HttpSession) extcontext.getSession(true);
            product = (Product) session.getAttribute("product");
            if(order != null){
                OrderProductPK orderProductPK = new OrderProductPK();
                orderProductPK.setIdOrder(order.getIdOrder());
                orderProductPK.setIdProduct(product.getIdProduct());
                
                OrderProduct orderProduct = new OrderProduct();
                
                orderProduct.setOrderProductPK(orderProductPK);
                orderProduct.setAmount(amount);
                
                orderProductDAO.create(orderProduct);
            }else{
                order = new Order();
                order.setUser(loginBB.getCurrentUser());
                order.setState(stateDAO.find(1));
                
                orderDAO.create(order);
                
                OrderProductPK orderProductPK = new OrderProductPK();
                orderProductPK.setIdOrder(order.getIdOrder());
                
                
                
                orderProductPK.setIdProduct(product.getIdProduct());
                
                OrderProduct orderProduct = new OrderProduct();
                
                orderProduct.setOrderProductPK(orderProductPK);
                orderProduct.setAmount(amount);
                
                orderProductDAO.create(orderProduct);
            }
            return PAGE_GO_TO_PRODUCTS_LIST;
        }
        return PAGE_GO_TO_LOGIN;
    }
}
