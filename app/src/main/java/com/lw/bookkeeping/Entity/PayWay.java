package com.lw.bookkeeping.Entity;

import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

public class PayWay extends LitePalSupport {

    private int id;

    private String payName;

    private List<PayWay> payWayList = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public List<PayWay> getPayWayList() {
        return payWayList;
    }

    public void setPayWayList(List<PayWay> payWayList) {
        this.payWayList = payWayList;
    }
}
