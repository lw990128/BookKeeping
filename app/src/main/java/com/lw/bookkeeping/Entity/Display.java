package com.lw.bookkeeping.Entity;

// RecyclerView 显示的内容
public class Display {

    // 图片id
    private int imageId;
    // 消费类型
    private String category;
    //消费金额
    private String sum;
    // 消费事件
    private String time;

    // Display 构造方法
    public Display(int imageId, String category, String sum, String time ){
        this.imageId = imageId;
        this.category = category;
        this.sum = sum;
        this.time = time;
    }

    public String getSum(){
        return sum;
    }

    public int getImageId(){
        return imageId;
    }

    public String getCategory(){
        return category;
    }

    public String getTime() {
        return time;
    }

}
