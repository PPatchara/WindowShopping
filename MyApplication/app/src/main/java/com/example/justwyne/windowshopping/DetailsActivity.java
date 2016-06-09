package com.example.justwyne.windowshopping;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {
    ImageView testImage;
    private ArrayList<Integer> itemColor;
    private ArrayList<String> itemSize;
    private ArrayList<Integer> itemQuantity;
    private ArrayList<Object> itemDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Log.d("Intent", "Details");

        itemColor = new ArrayList<>();
        itemSize = new ArrayList<>();
        itemQuantity = new ArrayList<>();
        itemDetails = new ArrayList<>();

        itemColor.add(R.drawable.fresh_white);
        itemColor.add(R.drawable.black);
        itemColor.add(R.drawable.lemon_souffle);

        itemSize.add("XS");
        itemSize.add("S");
        itemSize.add("M");
        itemSize.add("L");
        itemSize.add("XL");

        itemQuantity.add(3);
        itemQuantity.add(5);
        itemQuantity.add(10);
        itemQuantity.add(20);
        itemQuantity.add(100);

        itemDetails.add(itemColor);
        itemDetails.add(itemSize);
        itemDetails.add(itemQuantity);

        testImage = (ImageView) findViewById(R.id.testImage);

        for(int i = 0;i < itemColor.size();i++){
                if(itemColor.get(i) == R.drawable.fresh_white){
                    System.out.println("a");
                    testImage.setImageResource(R.drawable.fresh_white);
                }if(itemColor.get(i) == R.drawable.black){
                    System.out.println("b");
                    testImage.setImageResource(R.drawable.black);
                }else if(itemColor.get(i) == R.drawable.lemon_souffle){
                    System.out.println("c");
                    testImage.setImageResource(R.drawable.lemon_souffle);
                }
        }

//        for(Object object: itemDetails){
//            if(object instanceof Integer){
//                Integer int_obj = (Integer) object;
//                Log.d("Item",int_obj.toString());
//            }if(object instanceof String){
//                String str_obj = (String) object;
//                Log.d("Item",str_obj);
//            }
//        }
    }
}
