package org.dfernandez.elder;

import static org.junit.Assert.*;

import org.dfernandez.elder.model.Coin;
import org.dfernandez.elder.service.impl.VendingMachine;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

public class VendingMachineConsumerTest {

    VendingMachine vendingMachine;

    @Before
    public void init() {
        vendingMachine = new VendingMachine(20, Arrays.asList(Coin.values())) ;
    }

    @Test
    public void getPriceProductNoSpecified() {
        assertEquals(null, vendingMachine.getConsumerProductPrice("item-1"));
    }

    @Test
    public void getPriceProductNotExisting() {
        assertEquals(null, vendingMachine.getConsumerProductPrice("item-not-existing"));
    }

    @Test
    public void getPriceProductOnePound() {
        vendingMachine.setProductPrice("item-2", new BigDecimal(1.0));
        assertEquals(new BigDecimal(1.0), vendingMachine.getConsumerProductPrice("item-2"));
    }


}
