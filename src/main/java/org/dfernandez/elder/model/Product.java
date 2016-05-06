package org.dfernandez.elder.model;

import java.math.BigDecimal;

public class Product {

    private String name;

    private int quantity;

    private BigDecimal price;


    public Product(String name) {
        this.name = name;
        price = null;
        quantity = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
