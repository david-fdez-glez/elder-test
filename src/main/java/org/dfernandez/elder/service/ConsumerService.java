package org.dfernandez.elder.service;

import org.dfernandez.elder.model.Coin;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public interface ConsumerService {

    /**
     * Return product price
     * @param name
     * @return BigDecimal price or null if the product does not exists on the vending machine
     */
    public BigDecimal getConsumerProductPrice (String name);

    /**
     *
     * @param name
     * @param moneyProvided
     * @return
     * @throws IllegalStateException ​if the current state of the machine does not support the operation (e.g. the item is sold out, the item doesn’t have a price)
     * @throws IllegalArgumentException ​if the parameters are invalid (non existing product slot referenced, not enough money provided)
     */
    public Collection<Coin> buyProduct (String name, List<Coin> moneyProvided) throws IllegalStateException, IllegalArgumentException;
}
