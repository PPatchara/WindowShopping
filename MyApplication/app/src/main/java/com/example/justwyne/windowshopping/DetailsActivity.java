package com.example.justwyne.windowshopping;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.justwyne.windowshopping.models.Product;
import com.example.justwyne.windowshopping.models.ProductList;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailsActivity extends BaseActivity {
    private static final int REQ_CODE = 002;

    private ProductList productList;
    private Product product;
    private TextView name;
    private TextView details;
    private TextView price;
    private TextView description;
    private ImageView image;
    private LinearLayout linearLayout;
    private LinearLayout.LayoutParams params;
    private Button btnColor;
    private Button btnAddToCart;
    private Button btnViewCart;
    private String currentColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Log.d("Intent", "Details");
        initInstances();
        setupData();

    }

    private JSONObject getProduct() throws JSONException {
        Intent intent = getIntent();
        String json_raw = intent.getStringExtra("product");
        return new JSONObject(json_raw);
    }

    private void initInstances(){
        name = (TextView) findViewById(R.id.tvProductName);
        details = (TextView) findViewById(R.id.tvProductDetail);
        price = (TextView) findViewById(R.id.tvPrice);
        description = (TextView) findViewById(R.id.tvDescription);
        image = (ImageView) findViewById(R.id.ivImage);
//        btnAddToCart = (Button) findViewById(R.id.btnAddToCart);
//        btnAddToCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String dataMockup =
//                        "{'id':'CD001','color':'snow','size':'S','quantity':'2'}";
//                webSocket.send(dataMockup);
//            }
//        });

        btnViewCart = (Button) findViewById(R.id.btnViewCart);
        btnViewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendIntent();
            }
        });

        linearLayout = (LinearLayout) findViewById(R.id.colorLayout);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private void setupData(){
        productList = new ProductList();
        productList.loadData(getResources().openRawResource(R.raw.product));
        renderProduct();
    }

    private void renderProduct(){
        try {
            JSONObject productObject = getProduct();
            product = productList.getProduct(productObject.getString("product_id"));

            name.setText(product.getName());
            details.setText(product.getDetails());
            price.setText("$ " + product.getPrice());

            String imageName = product.getColorByName(productObject.getString("color")).getImageNameList().get(1);
            int res = getResources().getIdentifier(imageName, "drawable", getPackageName());
            image.setImageResource(res);

            description.setText(product.getDescription());
            createColorChoices();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void createColorChoices(){
        for(int index=0; index < product.getColorList().size(); index++)
        {
            // Create LinearLayout
            LinearLayout btnColorLayout = new LinearLayout(this);
            btnColorLayout.setOrientation(LinearLayout.HORIZONTAL);

            // Create Button
            btnColor = new Button(this);
            // Give button an ID
            btnColor.setId(index+1);
            btnColor.setBackgroundColor(Color.parseColor(product.getColorList().get(index).getCode()));
            btnColor.setText(product.getColorList().get(index).getName());
            btnColor.setTextSize(0);
            params.setMargins(0, 0, 20, 0);
            Log.d("btnColor ", btnColor.getText().toString() + btnColor.getId());

            // set the layoutParams on the button
            btnColor.setLayoutParams(params);

            final int btnIndex = index;
            // Set click listener for button
            btnColor.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String imageName = product.getColorList().get(btnIndex).getImageNameList().get(1);
                    int res = getResources().getIdentifier(imageName, "drawable", getPackageName());
                    image.setImageResource(res);

                    String colorName = product.getColorList().get(btnIndex).getName().toString();
                    Toast.makeText(getApplicationContext(), "You choose " + colorName + " color.",
                            Toast.LENGTH_SHORT).show();
                    Log.d("btnColorOnClick ", btnColor.getText().toString() + btnColor.getId());

                }
            });

            //Add button to LinearLayout
            btnColorLayout.addView(btnColor);
            //Add button to LinearLayout defined in XML
            linearLayout.addView(btnColorLayout);
        }
    }

    private void sendIntent(){
        Intent intent;
        intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }


}
