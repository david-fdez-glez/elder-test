package org.dfernandez.elder.service;

import org.dfernandez.elder.model.Coin;

import java.math.BigDecimal;

public interface MaintenanceService {

    /**
     * Return number of items for a product slot
     * @param name
     * @return
     */
    public int getProductQuantity(String name);

    /**
     * Return product price
     * @param name
     * @return
     */
    public BigDecimal getProductPrice(String name);

    /**
     * Return number of coins available =
     * @param coin
     * @return
     */
    public int getCoinsAvailable(Coin coin);
}
