package com.example.justwyne.windowshopping.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Justwyne on 8/9/16 AD.
 */
public class ProductList {

    private ArrayList<Product> products;
    private static ProductList instance;

    private ProductList(){
        products = new ArrayList<>();
    }

    public static ProductList getInstance() {
        if (instance == null) {
            instance = new ProductList();
        }
        return instance;
    }

    public void loadData(InputStream inputStream){
        Scanner scan = new Scanner(inputStream);
        String jsonString = "";
        while (scan.hasNext()){
            jsonString += scan.nextLine();
        }
        scan.close();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray productList = jsonObject.getJSONArray("product_list");
            for (int index=0; index < productList.length(); index++){
                JSONObject productTmp = productList.getJSONObject(index);

                Product product = new Product();
                product.setCategory(productTmp.getString("category"));
                product.setSubcategory(productTmp.getString("subcategory"));
                product.setId(productTmp.getString("id"));
                product.setName(productTmp.getString("name"));
                double price = Double.parseDouble(productTmp.getString("price"));
                product.setPrice(price);
                product.setStock(productTmp.getInt("stock"));
                product.setDescription(productTmp.getString("description"));
                product.setDetails(productTmp.getString("details"));

                ArrayList<String> sizeList = extractSizes(productTmp.getJSONArray("sizes"));
                product.setSizeList(sizeList);

                ArrayList<Color> colorList = extractColors(productTmp.getJSONArray("colors"));
                product.setColorList(colorList);

                products.add(product);

            }
            Log.d("products", products.toString());
        }catch (JSONException ex){
            Log.e("JsonParser",ex.getMessage());
        }

    }

    private ArrayList<String> extractSizes(JSONArray sizeJSONArray) throws JSONException {
        ArrayList<String> sizeList = new ArrayList<>();
        for (int index=0; index < sizeJSONArray.length(); index++){
            sizeList.add(sizeJSONArray.getString(index));
        }
        return sizeList;
    }

    private ArrayList<Color> extractColors(JSONArray colorJSONArray) throws JSONException {
        ArrayList<Color> colorList = new ArrayList<>();

        for (int index=0; index < colorJSONArray.length(); index++){
            JSONObject colorTmp = colorJSONArray.getJSONObject(index);

            Color color = new Color();
            color.setName(colorTmp.getString("name"));
            color.setCode(colorTmp.getString("code"));

            JSONArray imageArrayTmp = colorTmp.getJSONArray("image");

            ArrayList<String> imageNameList = new ArrayList<>();
            imageNameList.add(imageArrayTmp.getString(0));
            imageNameList.add(imageArrayTmp.getString(1));

            color.setImageNameList(imageNameList);

            colorList.add(color);
        }
        return colorList;
    }

    public Product getProduct(String productID){
        for (int index=0; index < length(); index++){
            String id = products.get(index).getId();
            if (id.equalsIgnoreCase(productID)){
                return products.get(index);
            }
        }
        return null;
    }



    public int length(){
        return products.size();
    }
}
