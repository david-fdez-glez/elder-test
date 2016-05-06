package org.dfernandez.elder.model;

import java.math.BigDecimal;

public enum  Coin {

    ONE_POUND(new BigDecimal(1.0)),
    FIFTY_PENCE(new BigDecimal(0.50)),
    TWENTY_PENCE(new BigDecimal(0.20)),
    TEN_PENCE(new BigDecimal(0.10)),
    FIVE_PENCE(new BigDecimal(0.05)),
    TWO_PENCE(new BigDecimal(0.02)),
    ONE_PENNY(new BigDecimal(0.01));

    private BigDecimal value;

    private Coin(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }
}
