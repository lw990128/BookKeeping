package com.lw.bookkeeping.Entity;

public class HomeRecord {

    private String amount;

    private int imageId;

    private String text;

    public HomeRecord(){

    }

    public HomeRecord(String amount, int imageId){
        this.amount = amount;
        this.imageId = imageId;
    }

    public HomeRecord(String amount, int imageId, String text){
        this.amount = amount;
        this.imageId = imageId;
        this.text = text;
    }

    public String getAmount(){
        return amount;
    }

    public int getImageId(){
        return imageId;
    }

    public String getText(){
        return text;
    }
}
