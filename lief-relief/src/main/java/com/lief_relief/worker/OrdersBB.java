/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lief_relief.worker;

import com.lief_relief.dao.OrderDAO;
import com.lief_relief.dao.StateDAO;
import com.lief_relief.entities.Order;
import com.lief_relief.entities.OrderProduct;
import com.lief_relief.entities.State;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
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
public class OrdersBB {
    private static final String PAGE_STAY_AT_THE_SAME = "orders?faces-redirect=true";
    
    @EJB
    OrderDAO orderDAO;
    @EJB
    StateDAO stateDAO;
    
    public List<Order> getOrders(){
        Map<String,Object> searchParams = new HashMap<>();
        searchParams.put("idState", 2);
        List<Order> orders = orderDAO.getList(searchParams);
        
        for(Order order : orders){
            float sum = 0;
            for(OrderProduct orderPorduct : order.getOrderProductCollection()){
                sum += orderPorduct.getProduct().getPrice() * orderPorduct.getAmount();
            }
            order.setSum((float)(Math.round(sum * 100.0) / 100.0));
        }
        
        return orders;
    }
    
    public List<OrderProduct> getOrderPorduct(Order order){
        return new ArrayList<OrderProduct>(order.getOrderProductCollection());
    }
    
    public String acceptOrder(Order order){
        order.setState(stateDAO.find(3));
        
        orderDAO.merge(order);
        
        return PAGE_STAY_AT_THE_SAME;
    }
}
