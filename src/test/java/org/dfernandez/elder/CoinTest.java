package org.dfernandez.elder;

import org.dfernandez.elder.model.Coin;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class CoinTest {

    @Before
    public void init() {}


    @Test
    public void testCoinValueOnePound() {
        assertEquals(Coin.ONE_POUND.getValue(), new BigDecimal("1.00"));
    }

    @Test
    public void testCoinValueFiftyPence() {
        assertEquals(Coin.FIFTY_PENCE.getValue(), new BigDecimal("0.50"));
    }

    @Test
    public void testCoinValueTwentyPence() {
        assertEquals(Coin.TWENTY_PENCE.getValue(), new BigDecimal("0.20"));
    }

    @Test
    public void testCoinValueTenPence() {
        assertEquals(Coin.TEN_PENCE.getValue(), new BigDecimal("0.10"));
    }

    @Test
    public void testCoinValueFivePence() {
        assertEquals(Coin.FIVE_PENCE.getValue(), new BigDecimal("0.05"));
    }

    @Test
    public void testCoinValueTwoPence() {
        assertEquals(Coin.TWO_PENCE.getValue(), new BigDecimal("0.02"));
    }

    @Test
    public void testCoinValueOnePenny() {
        assertEquals(Coin.ONE_PENNY.getValue(), new BigDecimal("0.01"));
    }
}
