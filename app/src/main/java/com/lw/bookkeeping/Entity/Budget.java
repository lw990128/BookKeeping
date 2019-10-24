package com.lw.bookkeeping.Entity;

import org.litepal.crud.LitePalSupport;

public class Budget extends LitePalSupport {

    private int id;

    private long budget;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getBudget() {
        return budget;
    }

    public void setBudget(long budget) {
        this.budget = budget;
    }
}
