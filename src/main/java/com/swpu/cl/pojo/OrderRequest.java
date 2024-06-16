package com.swpu.cl.pojo;

import com.swpu.cl.pojo.OrderItem;

import java.util.List;

public class OrderRequest {
    private String orderUsername;
    private String orderAddress;
    private List<OrderItem> items;

    // Getters and Setters
    public String getOrderUsername() {
        return orderUsername;
    }

    public void setOrderUsername(String orderUsername) {
        this.orderUsername = orderUsername;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}
