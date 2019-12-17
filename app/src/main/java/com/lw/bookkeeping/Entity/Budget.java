package com.lw.bookkeeping.Entity;

import org.litepal.crud.LitePalSupport;

// 设置预算
public class Budget extends LitePalSupport {

    private int id;

    private String budget;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }
}
