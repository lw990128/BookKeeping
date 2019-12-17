package com.lw.bookkeeping.Util;

public class KeyValuePair {

    public String category;

    private double sum;

    public KeyValuePair(String category, double sum){
        this.category = category;
        this.sum = sum;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
}
