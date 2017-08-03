package com.wq.demo.bean;

/**
 * Created by qiangwang on 7/25/17.
 */

public class Item {
    private String img;
    private String name;

    public Item() {
        this.img = "";
        this.name = "";
    }

    public Item(String img, String name) {
        this.img = img;
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
