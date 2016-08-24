package com.example.justwyne.windowshopping.models;

import java.util.ArrayList;

/**
 * Created by Justwyne on 8/21/16 AD.
 */
public class Cart {
    private ArrayList<ProductOrder> productList;

    public Cart() {
        productList = new ArrayList<>();
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
            total += productOrder.getTotal();
        }
        return total;
    }
}
