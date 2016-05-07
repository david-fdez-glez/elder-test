package org.dfernandez.elder;

import static org.junit.Assert.*;

import org.dfernandez.elder.model.Product;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class ProductTest {
    
    Product productTest;


    @Before
    public void init() {
    }
    
    
    @Test
    public void createProductTest() {
        productTest = new Product("testProduct");
        assertTrue("testProduct".equals(productTest.getName()));
    }

    @Test
    public void getProduct1Item() {
        productTest = new Product("1");
        productTest.setQuantity(1);
        assertEquals(1,productTest.getQuantity());
    }

    @Test
    public void getProductPrice() {
        productTest = new Product("1");
        productTest.setPrice(new BigDecimal("20"));
        assertEquals(new BigDecimal("20"),productTest.getPrice());
    }


    @Test
    public void getProductPriceFiftyPence() {
        productTest = new Product("1");
        productTest.setPrice(new BigDecimal("0.5"));
        assertEquals(new BigDecimal("0.5"),productTest.getPrice());
    }

}
