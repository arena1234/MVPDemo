package com.wq.demo.rxjava.weather.bean;
public class Uv {
    private String brf;

    private String txt;

    public void setBrf(String brf){
        this.brf = brf;
    }
    public String getBrf(){
        return this.brf;
    }
    public void setTxt(String txt){
        this.txt = txt;
    }
    public String getTxt(){
        return this.txt;
    }

}