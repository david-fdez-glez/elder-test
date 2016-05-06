package org.dfernandez.elder.service;

import org.dfernandez.elder.model.Coin;

import java.math.BigDecimal;

public interface MaintenanceService {

    /**
     * Return number of items for a product slot
     * @param name
     * @return number of items or -1 if the product does not exists on the vending machine
     */
    public int getProductQuantity(String name);

    /**
     *
     * @param name
     * @param quantity
     * @throws IllegalStateException ​if setting the quantity of items for a product slot with no price specified
     */
    public void setProductQuantity(String name, int quantity) throws IllegalStateException;

    /**
     * Return product price
     * @param name
     * @return BigDecimal price or null if the product does not exists on the vending machine
     */
    public BigDecimal getProductPrice(String name);

    /**
     *  Set price for a given product slot
     * @param name
     * @param price
     * @throws IllegalArgumentException if trying to set the price for a product slot that the vending machine
     * does not have
     */
    public void setProductPrice(String name, BigDecimal price) throws IllegalArgumentException;


    /**
     * Return number of coins available =
     * @param coin
     * @return  number of coins available, or -1 if the machine does not have coin
     */
    public int getCoinsAvailable(Coin coin);


}
