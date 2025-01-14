/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lief_relief.shoping_cart;

import com.lief_relief.authentication.LoginBB;
import com.lief_relief.dao.OrderDAO;
import com.lief_relief.dao.OrderProductDAO;
import com.lief_relief.dao.StateDAO;
import com.lief_relief.entities.Order;
import com.lief_relief.entities.OrderProduct;
import com.lief_relief.entities.Product;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author oliyn
 */
@Named
@RequestScoped
public class ShopingCartBB {
    private Order order;
    
    @EJB
    OrderDAO orderDAO;
    @EJB
    StateDAO stateDAO;
    @EJB
    OrderProductDAO orderProductDAO;
    
    @Inject
    LoginBB loginBB;
    
    public Order getOrder(){
        Map<String,Object> searchParams = new HashMap<>();
        if(loginBB.getCurrentUser() != null){
            searchParams.put("idUser", loginBB.getCurrentUser().getIdUser());
            searchParams.put("idState", 1);

            if(!orderDAO.getList(searchParams).isEmpty()){
                order = orderDAO.getList(searchParams).get(0);

                float sum = 0;
                for(OrderProduct orderPorduct : order.getOrderProductCollection()){
                    sum += orderPorduct.getProduct().getPrice() * orderPorduct.getAmount();
                }

                order.setSum((float)(Math.round(sum * 100.0) / 100.0));

                return order;
            }
        }
        
        return null;
    }
    
    public List<OrderProduct> getOrderPorduct(){
        return new ArrayList<>(order.getOrderProductCollection());
    }
    
    public void decreaseAmount(OrderProduct orderProduct){
        if(orderProduct.getAmount() > 1){
            int amount = orderProduct.getAmount();
            orderProduct.setAmount(--amount);
        }
        
        orderProductDAO.merge(orderProduct);
    }  
    
    public void increaseAmount(OrderProduct orderProduct){
        int amount = orderProduct.getAmount();
        orderProduct.setAmount(++amount);
        
        orderProductDAO.merge(orderProduct);
    }  
    
    public void deleteProduct(OrderProduct orderProduct){
        order.getOrderProductCollection().remove(orderProduct);
        orderDAO.merge(order);
        
        Map<String,Object> searchParams = new HashMap<>();
        searchParams.put("idOrder", orderProduct.getOrder().getIdOrder());
        
        if(orderProductDAO.getList(searchParams).isEmpty()){
            orderDAO.remove(orderDAO.find(order.getIdOrder()));
        }
    }
    
    public void placeOrder(){
        order.setState(stateDAO.find(2));
        orderDAO.merge(order);
    }
}
