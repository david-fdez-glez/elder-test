package org.dfernandez.elder.service;

import java.math.BigDecimal;

public interface ConsumerService {

    /**
     * Return product price
     * @param name
     * @return BigDecimal price or null if the product does not exists on the vending machine
     */
    public BigDecimal getConsumerProductPrice (String name);

}
