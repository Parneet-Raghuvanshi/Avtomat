package com.avtomat.androidapp.detailcourse;

public class Detail_Model {

    private String maintext;
    private String desctext;

    public Detail_Model() {
    }

    public Detail_Model(String maintext, String desctext) {
        this.maintext = maintext;
        this.desctext = desctext;
    }

    public String getMaintext() {
        return maintext;
    }

    public void setMaintext(String maintext) {
        this.maintext = maintext;
    }

    public String getDesctext() {
        return desctext;
    }

    public void setDesctext(String desctext) {
        this.desctext = desctext;
    }
}
