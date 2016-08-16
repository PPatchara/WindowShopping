package com.example.justwyne.windowshopping;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.justwyne.windowshopping.models.Product;
import com.example.justwyne.windowshopping.models.ProductList;

public class DetailsActivity extends AppCompatActivity {

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

    public DetailsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Log.d("Intent", "Details");
        setupView();
        setupData();

    }

    private void setupView(){
        name = (TextView) findViewById(R.id.tvProductName);
        details = (TextView) findViewById(R.id.tvProductDetail);
        price = (TextView) findViewById(R.id.tvPrice);
        description = (TextView) findViewById(R.id.tvDescription);
        image = (ImageView) findViewById(R.id.ivImage);
        btnAddToCart = (Button) findViewById(R.id.btnAddToCart);
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        product = productList.getProduct("CD001");
        name.setText(product.getName());
        details.setText(product.getDetails());
        price.setText("$ " + product.getPrice());
        description.setText(product.getDescription());
        createColorChoices();
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
            params.setMargins(0, 0, 20, 0);

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

                }
            });

            //Add button to LinearLayout
            btnColorLayout.addView(btnColor);
            //Add button to LinearLayout defined in XML
            linearLayout.addView(btnColorLayout);
        }
    }

    private void addToCart(){

    }

}
