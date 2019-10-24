package com.lw.bookkeeping.Entity;

import org.litepal.crud.LitePalSupport;

public class Record extends LitePalSupport {

    private int id;

    private String consumeWay;

    private Category category;

    private float sum;

    private String payWay;

    private String date;

    private String remark;

    private String photoPath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getConsumeWay() {
        return consumeWay;
    }

    public void setConsumeWay(String consumeWay) {
        this.consumeWay = consumeWay;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
