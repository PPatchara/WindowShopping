package com.example.justwyne.windowshopping.models;

import java.util.ArrayList;

/**
 * Created by Justwyne on 8/9/16 AD.
 */
public class Product {

    private String category;
    private String subcategory;
    private String id;
    private String name;
    private String description;
    private String details;
    private String price;
    private int quantity;
    private ArrayList<String> sizeList;
    private ArrayList<Color> colorList;

    public Product() {
        sizeList = new ArrayList<>();
        colorList = new ArrayList<>();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ArrayList<String> getSizeList() {
        return sizeList;
    }

    public void setSizeList(ArrayList<String> sizeList) {
        this.sizeList = sizeList;
    }

    public ArrayList<Color> getColorList() {
        return colorList;
    }

    public void setColorList(ArrayList<Color> colorList) {
        this.colorList = colorList;
    }

    public void addColor(Color color) {
        this.colorList.add(color);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Color getColorByName(String name) {
        for (Color color : colorList) {
            if( color.getName().equalsIgnoreCase(name)) {
                return color;
            }
        }
        return null;
    }
}
