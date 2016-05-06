package org.dfernandez.elder.service.impl;

import org.dfernandez.elder.model.Coin;
import org.dfernandez.elder.model.Product;
import org.dfernandez.elder.service.ConsumerService;
import org.dfernandez.elder.service.MaintenanceService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendingMachine implements ConsumerService,MaintenanceService{

    private Map<String,Product> productsAvailable = new HashMap<>();

    private Map<Coin, Integer> coinsAvailable = new HashMap<>();

    public VendingMachine(int numProducts, List<Coin> availableCoins) {
        // Create List of Products
        for(int i = 0; i < numProducts; i++) {
            Product product = new Product("item-" + (i+1));
            productsAvailable.put(product.getName(),product);
        }
        for(Coin coin: availableCoins) {
            if(!coinsAvailable.containsKey(coin)) {
                coinsAvailable.put(coin, 0);
            }
        }
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public int getProductQuantity(String name) {
        if(productsAvailable.containsKey(name))
            return productsAvailable.get(name).getQuantity();
        return -1;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public void setProductQuantity(String name, int quantity) throws IllegalStateException {
        if(productsAvailable.containsKey(name)) {
            Product product = productsAvailable.get(name);
            if(product.getPrice() != null) {
                product.setQuantity(quantity);
            } else {
                throw new IllegalStateException("Trying to set the quantity for " + name + " but the price is not specified");
            }
        }
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public BigDecimal getProductPrice(String name) {
        if(productsAvailable.containsKey(name))
            return productsAvailable.get(name).getPrice();
        return null;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public void setProductPrice(String name, BigDecimal price) throws IllegalArgumentException {

        if(productsAvailable.containsKey(name)) {
            Product product = productsAvailable.get(name);
            product.setPrice(price);
        } else {
            throw new IllegalArgumentException("Trying to set the price for " + name + " but the machine does not have it");
        }
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public int getCoinsAvailable(Coin coin) {
        if(coinsAvailable.containsKey(coin)) {
            return coinsAvailable.get(coin);
        }
        return -1;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public void setCoinsAvailable(Coin coin, int quantity) throws IllegalStateException{
        if(coinsAvailable.containsKey(coin)) {
            coinsAvailable.put(coin, quantity);
        } else {
            throw new IllegalStateException("Trying to set the quantity for " + coin + " but the vending machine does not support it");
        }
    }
}