package com.lw.bookkeeping.Entity;

import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

public class ConsumeWay extends LitePalSupport {

    private int id;

    private String consumeName;

    private List<ConsumeWay> consumeWayList = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConsumeName() {
        return consumeName;
    }

    public void setConsumeName(String consumeName) {
        this.consumeName = consumeName;
    }

    public List<ConsumeWay> getConsumeWayList() {
        return consumeWayList;
    }

    public void setConsumeWayList(List<ConsumeWay> consumeWayList) {
        this.consumeWayList = consumeWayList;
    }
}
