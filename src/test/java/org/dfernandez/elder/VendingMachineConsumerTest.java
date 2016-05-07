package org.dfernandez.elder;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.equalTo;

import org.dfernandez.elder.model.Coin;
import org.dfernandez.elder.service.impl.VendingMachine;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VendingMachineConsumerTest {

    VendingMachine vendingMachine;

    List<Coin> expectedChange;
    List<Coin> moneyProvided;
    @Before
    public void init() {
        vendingMachine = new VendingMachine(20, Arrays.asList(Coin.values()));
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


    @Test
    public void buyProductOnePoundMoneyProvidedOnePound() {
        vendingMachine.setProductPrice("item-3",new BigDecimal("1.00"));
        vendingMachine.setProductQuantity("item-3",10);

        expectedChange = new ArrayList<>();
        moneyProvided = Arrays.asList(Coin.ONE_POUND);
        assertThat(expectedChange, equalTo(vendingMachine.buyProduct("item-3", moneyProvided)));
        assertEquals(9, vendingMachine.getProductQuantity("item-3"));
    }

    @Test
    public void buyProductOnePoundThirtyPenceMoneyProvidedOnePoundTwentyPence() {

        vendingMachine.setProductPrice("item-4",new BigDecimal("1.30"));
        vendingMachine.setProductQuantity("item-4",2);
        vendingMachine.setCoinsAvailable(Coin.ONE_POUND, 0);
        vendingMachine.setCoinsAvailable(Coin.FIFTY_PENCE, 5);
        vendingMachine.setCoinsAvailable(Coin.TWENTY_PENCE, 5);
        vendingMachine.setCoinsAvailable(Coin.TEN_PENCE, 5);
        vendingMachine.setCoinsAvailable(Coin.FIVE_PENCE, 0);
        vendingMachine.setCoinsAvailable(Coin.TWO_PENCE, 0);
        vendingMachine.setCoinsAvailable(Coin.ONE_PENNY, 0);


        moneyProvided = Arrays.asList(Coin.ONE_POUND, Coin.ONE_POUND);

        expectedChange =  Arrays.asList(Coin.FIFTY_PENCE, Coin.TWENTY_PENCE);
        assertThat(expectedChange, equalTo(vendingMachine.buyProduct("item-4", moneyProvided)));
        assertEquals(4, vendingMachine.getCoinsAvailable(Coin.FIFTY_PENCE));

    }

    @Test
    public void buyProductOnePoundEightyPenceMoneyProvidedOnePoundFiftyPence() {

        vendingMachine.setProductPrice("item-5",new BigDecimal("1.80"));
        vendingMachine.setProductQuantity("item-5",2);
        vendingMachine.setCoinsAvailable(Coin.ONE_POUND, 0);
        vendingMachine.setCoinsAvailable(Coin.FIFTY_PENCE, 0);
        vendingMachine.setCoinsAvailable(Coin.TWENTY_PENCE, 0);
        vendingMachine.setCoinsAvailable(Coin.TEN_PENCE, 0);
        vendingMachine.setCoinsAvailable(Coin.FIVE_PENCE, 5);
        vendingMachine.setCoinsAvailable(Coin.TWO_PENCE, 0);
        vendingMachine.setCoinsAvailable(Coin.ONE_PENNY, 0);

        moneyProvided = Arrays.asList(Coin.ONE_POUND, Coin.ONE_POUND);

        expectedChange =  Arrays.asList(Coin.FIVE_PENCE, Coin.FIVE_PENCE, Coin.FIVE_PENCE, Coin.FIVE_PENCE);
        assertThat(expectedChange, equalTo(vendingMachine.buyProduct("item-5", moneyProvided)));
        assertEquals(1, vendingMachine.getCoinsAvailable(Coin.FIVE_PENCE));

    }
    
    
}
