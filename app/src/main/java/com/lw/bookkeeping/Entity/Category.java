package com.lw.bookkeeping.Entity;

import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

public class Category extends LitePalSupport {

    private int id;

    private String categoryName;

    private List<Category> categoryList = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }
}
