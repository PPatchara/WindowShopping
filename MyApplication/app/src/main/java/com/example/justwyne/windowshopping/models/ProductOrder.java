package com.example.justwyne.windowshopping.models;

import android.util.Log;

/**
 * Created by Justwyne on 8/24/16 AD.
 */
public class ProductOrder {
    private Product product;
    private Color color;
    private String size;
    private int quantity = 1;

    public ProductOrder(Product product,Color color,String size){
        this.product = product;
        this.color = color;
        this.size = size;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void incrementQuantity() {
        quantity += 1;
    }

    public double getSubtotal() {
        return quantity * product.getPrice();
    }

    public boolean equals(Product product,Color color,String size){
        return this.product.equals(product) && this.color.equals(color) && this.size.equals(size);
    }
}
