package com.lw.bookkeeping.Entity;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

public class DataBean implements IPickerViewData {

    /**
     * consumeWay : 收入
     * category : ["工资"]
     */
    private String consumeWay;
    private List<String> category;

    public String getConsumeWay() {
        return consumeWay;
    }

    public void setConsumeWay(String consumeWay) {
        this.consumeWay = consumeWay;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    // 实现 IPickerViewData 接口，
    // 这个用来显示在PickerView上面的字符串，
    // PickerView会通过IPickerViewData获取getPickerViewText方法显示出来。
    @Override
    public String getPickerViewText() {
        return this.consumeWay;
    }

}
