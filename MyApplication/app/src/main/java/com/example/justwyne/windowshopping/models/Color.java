package com.example.justwyne.windowshopping.models;

import java.util.ArrayList;

/**
 * Created by Justwyne on 8/10/16 AD.
 */
public class Color {
    private String name;
    private String code;
    private ArrayList<String> imageNameList;

    public Color(){
        imageNameList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<String> getImageNameList() {
        return imageNameList;
    }

    public void setImageNameList(ArrayList<String> imageNameList) {
        this.imageNameList = imageNameList;
    }

}
