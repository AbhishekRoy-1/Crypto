package com.example.crypto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class CurrencyModel {
    private String name;
    private String symbol;
    private double price;
    private int cmc_rank;
    private String last_updated;
    SimpleDateFormat dateFormat=new SimpleDateFormat();

    public CurrencyModel(String name, String symbol,int cmc_rank, double price, String last_updated) {
        this.name = name;
        this.symbol = symbol;
        this.cmc_rank=cmc_rank;
        this.price = price;
        this.last_updated=last_updated;
    }

    public String getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(String last_updated) {
        this.last_updated = last_updated;
    }

    public int getCmc_rank() {
        return cmc_rank;
    }

    public void setCmc_rank(int cmc_rank) {
        this.cmc_rank = cmc_rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
