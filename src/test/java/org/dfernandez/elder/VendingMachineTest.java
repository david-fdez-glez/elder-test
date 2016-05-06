package org.dfernandez.elder;

import static org.junit.Assert.*;

import org.dfernandez.elder.model.Coin;
import org.dfernandez.elder.model.Product;
import org.dfernandez.elder.service.impl.VendingMachine;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class VendingMachineTest {

    VendingMachine vendingMachine;

    @Before
    public void init() {
        vendingMachine = new VendingMachine(2, Arrays.asList(Coin.ONE_POUND, Coin.FIFTY_PENCE,Coin.TEN_PENCE)) ;
    }


    @Test
    public void getItemZeroQuantity() {
        assertEquals(0, vendingMachine.getProductQuantity("item-1"));
    }

    @Test
    public void getNullPrice() {
        assertEquals(null, vendingMachine.getProductPrice("item-1"));
    }

    @Test
    public void getZeroOnePoundCoins() {
        assertEquals(0, vendingMachine.getCoinsAvailable(Coin.ONE_POUND));
    }
}
