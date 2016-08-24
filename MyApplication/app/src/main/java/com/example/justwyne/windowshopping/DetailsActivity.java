package com.example.justwyne.windowshopping;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.justwyne.windowshopping.models.Cart;
import com.example.justwyne.windowshopping.models.Color;
import com.example.justwyne.windowshopping.models.Product;
import com.example.justwyne.windowshopping.models.ProductList;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class DetailsActivity extends BaseActivity {
    private static final int REQ_CODE = 002;

    private ProductList productList;
    private Product product;
    private TextView tvName;
    private TextView tvDetails;
    private TextView tvPrice;
    private TextView tvDescription;
    private TextView tvColor;
    private ImageView ivImage;
    private LinearLayout linearLayoutColor;
    private LinearLayout linearLayoutSize;
    private LinearLayout.LayoutParams paramsColor;
    private LinearLayout.LayoutParams paramsSize;
    private Button btnColor;
    private Button btnAddToCart;
    private Button btnViewCart;
    private Spinner spinner;
    private String imageName;
    private String colorName;
    private String size;
    private Cart cart;
    private Color color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Log.d("Intent", "Details");
        initInstances();
        setupData();
        renderProduct();
    }

    private JSONObject getProduct() throws JSONException {
        Intent intent = getIntent();
        String json_raw = intent.getStringExtra("product");
        return new JSONObject(json_raw);
    }

    private void initInstances(){
        tvName = (TextView) findViewById(R.id.tvProductName);
        tvDetails = (TextView) findViewById(R.id.tvProductDetail);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        ivImage = (ImageView) findViewById(R.id.ivImage);
        tvColor = (TextView) findViewById(R.id.tvColorName);
        spinner = new Spinner(this);
        cart = new Cart();
        btnAddToCart = (Button) findViewById(R.id.btnAddToCart);
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                * 1. Get product object
                * 2. Get color object
                * 3. Get size
                * 4. Put product object, color object and size as arguments by using cart.add(product, color, size)
                * 5. Finish!
                */
                size = spinner.getSelectedItem().toString();
                Log.d("Adding", String.format("%s %s %s", product.getName(),size,color.getName()));
                cart.add(product,color,size);
            }
        });

        btnViewCart = (Button) findViewById(R.id.btnViewCart);
        btnViewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendIntent();
            }
        });

        linearLayoutColor = (LinearLayout) findViewById(R.id.colorLayout);
        paramsColor = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        linearLayoutSize = (LinearLayout) findViewById(R.id.sizeLayout);
        paramsSize = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    }

    private void setupData(){
        productList = new ProductList();
        productList.loadData(getResources().openRawResource(R.raw.product));
    }

    private void renderProduct(){
        try {
            JSONObject productObject = getProduct();
            product = productList.getProduct(productObject.getString("product_id"));

            tvName.setText(product.getName());
            tvDetails.setText(product.getDetails());
            tvPrice.setText("$ " + product.getPrice());

            String imageName = product.getColorByName(productObject.getString("color")).getImageNameList().get(1);
            int res = getResources().getIdentifier(imageName, "drawable", getPackageName());
            ivImage.setImageResource(res);

            tvDescription.setText(product.getDescription());
            tvColor.setText(product.getColorByName(productObject.getString("color")).getName());
            createColorChoices();
            createSizeChoices();
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
            btnColor.setBackgroundColor(android.graphics.Color.parseColor(product.getColorList().get(index).getCode()));
            btnColor.setText(product.getColorList().get(index).getName());
            btnColor.setTextSize(0);
            paramsColor.setMargins(0, 0, 20, 0);

            // set the layoutParams on the button
            btnColor.setLayoutParams(paramsColor);

            final int btnIndex = index;
            // Set click listener for button
            btnColor.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    imageName = product.getColorList().get(btnIndex).getImageNameList().get(1);
                    int res = getResources().getIdentifier(imageName, "drawable", getPackageName());
                    ivImage.setImageResource(res);

                    colorName = product.getColorList().get(btnIndex).getName().toString();
                    color = product.getColorByName(colorName);
                    tvColor.setText(colorName);
                    sendProduct(product.getId().toString(),colorName);
                }
            });

            //Add button to LinearLayout
            btnColorLayout.addView(btnColor);
            //Add button to LinearLayout defined in XML
            linearLayoutColor.addView(btnColorLayout);
        }
    }

    private void createSizeChoices(){
        LinearLayout sizeLayout = new LinearLayout(this);
        sizeLayout.setOrientation(LinearLayout.HORIZONTAL);

        ArrayList<String> spinnerArray = new ArrayList<>();
        for(int index=0; index < product.getSizeList().size(); index++){
            spinnerArray.add(product.getSizeList().get(index));
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setLayoutParams(paramsSize);

        sizeLayout.addView(spinner);
        linearLayoutSize.addView(sizeLayout);
    }

    private void sendIntent(){
        Intent intent;
        intent = new Intent(this, CartActivity.class);
//        intent.putExtra("cart", (Serializable) cart);
        startActivity(intent);
    }

    private void sendProduct(String productId,String colorName){
        String dataMockup = "{\"color\":" +
                colorName +
                ",\"product_id\":" +
                productId +
                ",\"action\":" +
                "\"current_product\"" +
                "}";

        webSocket.send(dataMockup);
    }

}
