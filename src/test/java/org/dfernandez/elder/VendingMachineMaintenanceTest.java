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
    public void getQuantityProductZero() {
        assertEquals(0, vendingMachine.getProductQuantity("item-1"));
    }

    @Test
    public void getQuantityNotExistingProduct() {
        assertEquals(-1, vendingMachine.getProductQuantity("item-not-existing"));
    }

    @Test
    public void getQuantityProductFive(){
        vendingMachine.setProductPrice("item-2", new BigDecimal(1.10));
        vendingMachine.setProductQuantity("item-2", 5);
        assertEquals(5, vendingMachine.getProductQuantity("item-2"));
    }

    @Test
    public void setQuantityTwoProduct() {
        vendingMachine.setProductPrice("item-3", new BigDecimal(0.85));
        vendingMachine.setProductQuantity("item-3", 2);
        assertEquals(2, vendingMachine.getProductQuantity("item-3"));
    }

    @Test(expected = IllegalStateException.class)
    public void setQuantityProductNoPriceSpecified() {
        vendingMachine.setProductQuantity("item-4",4);
    }

    @Test
    public void getPriceProductNoSpecified() {
        assertEquals(null, vendingMachine.getProductPrice("item-5"));
    }

    @Test
    public void getPriceProductNotExisting() {
        assertEquals(null, vendingMachine.getProductPrice("item-not-existing"));
    }

    @Test
    public void setPriceProductFiftyPence() {
        vendingMachine.setProductPrice("item-6",new BigDecimal(0.5));
        assertEquals(new BigDecimal(0.5), vendingMachine.getProductPrice("item-6"));
    }

    @Test
    public void getPriceProductOnePound() {
        vendingMachine.setProductPrice("item-7", new BigDecimal(1.0));
        assertEquals(new BigDecimal(1.0), vendingMachine.getProductPrice("item-7"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void setPriceProductNotExistingOnePound() {
        vendingMachine.setProductPrice("item-not-existing", new BigDecimal(1.0));
    }

    @Test
    public void getZeroOnePoundCoins() {
        assertEquals(0, vendingMachine.getCoinsAvailable(Coin.ONE_POUND));
    }

    @Test
    public void getNotAvailableOneTwentyPenceCoins() {
        assertEquals(-1, vendingMachine.getCoinsAvailable(Coin.TWENTY_PENCE));
    }

    @Test
    public void setCoinsFiftyPence() {
        vendingMachine.setCoinsAvailable(Coin.FIFTY_PENCE, 20);
        assertEquals(20, vendingMachine.getCoinsAvailable(Coin.FIFTY_PENCE));
    }

    @Test(expected = IllegalStateException.class)
    public void setCoinsNotAcceptedByVendingMachine() {
        vendingMachine.setCoinsAvailable(Coin.ONE_PENNY,12);
    }
}
