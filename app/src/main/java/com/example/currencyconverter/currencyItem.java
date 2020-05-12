package com.example.currencyconverter;

public class currencyItem {
    private String base;
    private double value;

    public currencyItem(String base, double value){
        this.base = base;
        this.value = value;
    }

    public String getBase() {
        return this.base;
    }

    public double getValue() {
        return this.value;
    }
}
