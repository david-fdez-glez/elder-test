package org.dfernandez.elder;

import static org.junit.Assert.*;

import org.dfernandez.elder.model.Coin;
import org.dfernandez.elder.service.impl.VendingMachine;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

public class VendingMachineMaintenanceTest {

    VendingMachine vendingMachine;

    @Before
    public void init() {
        vendingMachine = new VendingMachine(10, Arrays.asList(Coin.ONE_POUND, Coin.FIFTY_PENCE,Coin.TEN_PENCE)) ;
    }


    @Test
    public void getItemZeroQuantity() {
        assertEquals(0, vendingMachine.getProductQuantity("item-1"));
    }

    @Test
    public void getQuantityNotExistingProduct() {
        assertEquals(-1, vendingMachine.getProductQuantity("item-not-existing"));
    }
    @Test
    public void getNullPrice() {
        assertEquals(null, vendingMachine.getProductPrice("item-1"));
    }

    @Test
    public void getPriceNotExistingProduct() {
        assertEquals(null, vendingMachine.getProductPrice("item-not-existing"));
    }

    @Test
    public void getZeroOnePoundCoins() {
        assertEquals(0, vendingMachine.getCoinsAvailable(Coin.ONE_POUND));
    }

    @Test
    public void setPriceFiftyPenceProduct() {
        vendingMachine.setProductPrice("item-2",new BigDecimal(0.45));
        assertEquals(new BigDecimal(0.45), vendingMachine.getProductPrice("item-2"));
    }

    @Test
    public void getPriceOnePoundItem3() {
        vendingMachine.setProductPrice("item-3", new BigDecimal(1.0));
        assertEquals(new BigDecimal(1.0), vendingMachine.getProductPrice("item-3"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void setPriceOnePoundOnNotExistingProduct() {
        vendingMachine.setProductPrice("item-not-existing", new BigDecimal(1.0));
    }

    @Test
    public void getMinusOneTwentyPenceCoins() {
        assertEquals(-1, vendingMachine.getCoinsAvailable(Coin.TWENTY_PENCE));
    }

    @Test
    public void setTwoItemsItem4() {
        vendingMachine.setProductPrice("item-4", new BigDecimal(0.85));
        vendingMachine.setProductQuantity("item-4", 2);
        assertEquals(2, vendingMachine.getProductQuantity("item-4"));
    }

    @Test(expected = IllegalStateException.class)
    public void setFiveItemsItem5NoPriceSpecified() {
        vendingMachine.setProductQuantity("item-5",5);
    }
}
