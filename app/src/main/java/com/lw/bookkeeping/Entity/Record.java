package com.lw.bookkeeping.Entity;

import org.litepal.crud.LitePalSupport;

// LitePal 数据库 Record
public class Record extends LitePalSupport {
    // id
    private int id;
    // 消费方式
    private String consumeWay;
    // 消费类型
    private String category;
    // 消费金额
    private String sum;
    // 支付方式
    private String payWay;
    // 消费事件
    private String date;
    // 备注
    private String remark;
    // 图标id
    private int iconId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
}
