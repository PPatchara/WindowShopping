package com.example.justwyne.windowshopping.models;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Justwyne on 8/21/16 AD.
 */
public class Cart implements Serializable {
    private static final long serialVersionUID = -7060210544600464481L;
    private static Cart instance;
    private ArrayList<ProductOrder> productList;

    private Cart() {
        productList = new ArrayList<>();
    }

    public static Cart getInstance() {
        if(instance == null) {
            instance = new Cart();
        }
        return instance;
    }

    public int size(){
        return productList.size();
    }

    public void add(Product product,Color color,String size){
        for (ProductOrder productOrder : productList) {
            if (productOrder.equals(product, color, size)) {
                productOrder.incrementQuantity();
                return;
            }
        }

        ProductOrder productOrder = new ProductOrder(product,color,size);
        productList.add(productOrder);
    }

    public double getTotal() {
        double total = 0;
        for (ProductOrder productOrder: productList) {
            total += productOrder.getSubtotal();
        }
        return total;
    }

    public void summary() {
        for (ProductOrder productOrder : productList) {
            Product product = productOrder.getProduct();
            Log.v("Cart-Product", product.toString() + "\t" + productOrder.getSize() + " " + productOrder.getColor().getName());
        }
    }

    public ArrayList<ProductOrder> getProductList() {
        return productList;
    }
}
