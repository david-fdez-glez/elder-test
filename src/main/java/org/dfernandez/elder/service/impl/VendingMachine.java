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
        return productsAvailable.get(name).getQuantity();
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public BigDecimal getProductPrice(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public int getCoinsAvailable(Coin coin) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
