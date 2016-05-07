package org.dfernandez.elder.service.impl;

import org.dfernandez.elder.model.Coin;
import org.dfernandez.elder.model.Product;
import org.dfernandez.elder.service.ConsumerService;
import org.dfernandez.elder.service.MaintenanceService;

import java.math.BigDecimal;
import java.util.*;

public class VendingMachine implements ConsumerService,MaintenanceService{

    private Map<String,Product> productsAvailable = new HashMap<>();

    private Map<Coin, Integer> coinsAvailable = new HashMap<>();


    public VendingMachine(int numProducts, List<Coin> availableCoins) {
        // Create List of Products
        for(int i = 0; i < numProducts; i++) {
            Product product = new Product("item-" + (i+1));
            productsAvailable.put(product.getName(),product);
        }
        for(Coin coin: availableCoins) {
            if(!coinsAvailable.containsKey(coin)) {
                coinsAvailable.put(coin, 0);
            }
        }
    }

    /**
     * Maintenance Service
     */

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public synchronized int getProductQuantity(String name) {
        if(productsAvailable.containsKey(name))
            return productsAvailable.get(name).getQuantity();
        return -1;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public synchronized void setProductQuantity(String name, int quantity) throws IllegalStateException {
        if(productsAvailable.containsKey(name)) {
            Product product = productsAvailable.get(name);
            if(product.getPrice() != null) {
                product.setQuantity(quantity);
            } else {
                throw new IllegalStateException("Trying to set the quantity for " + name + " but the price is not specified");
            }
        }
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public synchronized BigDecimal getProductPrice(String name) {
        if(productsAvailable.containsKey(name))
            return productsAvailable.get(name).getPrice();
        return null;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public synchronized void setProductPrice(String name, BigDecimal price) throws IllegalArgumentException {
        if(productsAvailable.containsKey(name)) {
            Product product = productsAvailable.get(name);
            product.setPrice(price);
        } else {
            throw new IllegalArgumentException("Trying to set the price for " + name + " but the machine does not have it");
        }
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public synchronized int getCoinsAvailable(Coin coin) {
        if(coinsAvailable.containsKey(coin)) {
            return coinsAvailable.get(coin);
        }
        return -1;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public synchronized void setCoinsAvailable(Coin coin, int quantity) throws IllegalStateException{
        if(coinsAvailable.containsKey(coin)) {
            coinsAvailable.put(coin, quantity);
        } else {
            throw new IllegalStateException("Trying to set the quantity for " + coin + " but the vending machine does not support it");
        }
    }


    /**
     * Consumer Service
     */


    /**
     *
     * {@inheritDoc}
     */
    @Override
    public synchronized BigDecimal getConsumerProductPrice(String name) {
        if(productsAvailable.containsKey(name))
            return productsAvailable.get(name).getPrice();
        return null;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public synchronized Collection<Coin> buyProduct(String name, List<Coin> moneyProvided) throws IllegalStateException, IllegalArgumentException {

        List<Coin> changeReturn = new ArrayList<>();
        StringBuilder errorMessage = new StringBuilder();
        // Items to buy
        final int productItems = 1;

        // Check IllegalStateException
        if(getIllegalState(name, productItems, errorMessage)) {
            throw new IllegalStateException(errorMessage.toString());
        }

        // Check    IllegalArgumentException
        if(getIllegalArgument(name, moneyProvided, errorMessage)) {
            throw  new IllegalArgumentException(errorMessage.toString());
        }

        // Temp add Coins to Machines Coins
        Map<Coin, Integer> tempCoinsAdded = tempAddCoinsToMachine(moneyProvided);

        // Check if the vending machine has enough coins to return change
        if(getChange(moneyProvided, getProductPriceInternal(name), true, changeReturn)) {
            // update Products
            updateProductsQuantity(name,-productItems);
            getChange(moneyProvided, getProductPriceInternal(name), false, changeReturn);
            return changeReturn;
        }

        // error, remove coins added to Machine
        tempRemoveCoinsFromMachine(tempCoinsAdded);

        throw  new IllegalStateException("Insufficient Coinage ");

    }

    /**
     * Check if the item is sold out or if the item doesn't have a price
     * @param name
     * @return
     */
    private boolean getIllegalState(String name, int quantity, StringBuilder errorMessage) {
          if(productsAvailable.containsKey(name)) {
              Product product = productsAvailable.get(name);
              if(product.getPrice() == null) {
                  errorMessage.append("Item doesn't have a price: " + name);
                  return true;
              }
              if( (product.getQuantity() - quantity) <= 0  ) {
                  errorMessage.append("Item is sold out: " + name);
                  return true;
              }

           }
          return false;
    }


    /**
     * check if on existing  product slot referenced or not enough money provided
     * @param name
     * @param moneyProvided
     * @param errorMessage
     * @return
     */
    private boolean getIllegalArgument(String name, List<Coin> moneyProvided, StringBuilder errorMessage) {

        if(!productsAvailable.containsKey(name)) {
            errorMessage.append("Non existing Product Slot Referenced: " + name);
            return true;
        }

        if(productsAvailable.get(name).getPrice().compareTo(getSumMoneyProvided(moneyProvided)) > 0) {
            errorMessage.append("Not enough money provided");
            return true;
        }

        return false;
    }




    /**
     *   get the change for a given number of pence based on a limited supply of coins
     * @param moneyProvided
     * @param productPrice
     * @param isMock
     * @param changeList
     * @return  true if  the system is able to return the exact change, and if is able to, it will update outList
     */
    private boolean getChange(List<Coin> moneyProvided,BigDecimal productPrice, boolean isMock, List<Coin> changeList) {

        BigDecimal amountLeft = getSumMoneyProvided(moneyProvided).subtract(productPrice);

        for(Coin coin: Coin.values()) {
            if(coinsAvailable.containsKey(coin)) {

                while((amountLeft.compareTo(coin.getValue()) >= 0) && getCoinAmount(coin) > 0) {

                    amountLeft = amountLeft.subtract(coin.getValue());
                     if(!isMock) {
                         changeList.add(coin);
                         updateCoinAmount(coin, -1);
                     }

                }
            }

        }

        return amountLeft.compareTo(BigDecimal.ZERO) == 0;
    }

    /**
     * Change quantity times product name
     * @param name
     * @param quantity
     */
    private void updateProductsQuantity(String name, int quantity) {
        if(productsAvailable.containsKey(name)) {
            Product product = productsAvailable.get(name);
            if(product.getPrice() != null) {
                product.setQuantity(product.getQuantity() + quantity);
            } else {
                throw new IllegalStateException("Trying to set the quantity for " + name + " but the price is not specified");
            }
        }
    }

    /**
     * return available Coin coin
     * @param coin
     * @return
     */
    private int getCoinAmount(Coin coin) {
        return coinsAvailable.get(coin);
    }

    /**
     * update number of coins
     * @param coin
     * @param amount
     */
    private void updateCoinAmount(Coin coin, int amount) {
        coinsAvailable.put(coin, amount + getCoinAmount(coin));
    }

    /**
     * get Product Price
     * @param name
     * @return
     */
    private BigDecimal getProductPriceInternal(String name) {
        return productsAvailable.get(name).getPrice();
    }

    /**
     * get Sum all of provided coins
     * @param moneyProvided
     * @return
     */
    private BigDecimal getSumMoneyProvided(List<Coin> moneyProvided) {

        BigDecimal sum = new BigDecimal(0);
        for(Coin coin:moneyProvided) {
            sum = sum.add(coin.getValue());
        }

        return sum;
    }


    /**
     * Add Coins provided to Machine Coins
     * @param moneyProvided
     * @return
     */
    private Map<Coin, Integer> tempAddCoinsToMachine(List<Coin> moneyProvided) {
        Map<Coin, Integer>  map = new HashMap<>();

        for(Coin coin: moneyProvided) {
             if(coinsAvailable.containsKey(coin)) {
                 // Add 1 Coin
                 coinsAvailable.put(coin, coinsAvailable.get(coin) +1);
                 // Save in a temp map coins added
                 if(!map.containsKey(coin)) {
                   map.put(coin, 1);
                 } else {
                     map.put(coin, map.get(coin) + 1);
                 }
             }
        }
         return map;

    }

    /**
     *  Remove tmp coins added to Machine
     * @param map
     */
    private void tempRemoveCoinsFromMachine( Map<Coin, Integer> map) {

        for(Map.Entry<Coin, Integer> entry: map.entrySet()) {
            Coin coin = entry.getKey();
            if(coinsAvailable.containsKey(coin)) {
                // Remove coins
                coinsAvailable.put(coin, coinsAvailable.get(coin)- entry.getValue());
            }
        }
    }
}