package com.example.justwyne.windowshopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.justwyne.windowshopping.models.Cart;
import com.example.justwyne.windowshopping.models.Color;
import com.example.justwyne.windowshopping.models.Product;
import com.example.justwyne.windowshopping.models.ProductOrder;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    private Button btnCheckout;
    private LinearLayout linearLayoutOrder;
    private LinearLayout.LayoutParams paramsOrder;
    private TextView tvProductOrder;
    private Cart cart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cart = Cart.getInstance();
        initInstances();
        createProductOrder();

    }

    private void initInstances(){
        linearLayoutOrder = (LinearLayout) findViewById(R.id.cartLayout);
        paramsOrder = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        btnCheckout = (Button) findViewById(R.id.btnCheckout);
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendIntent();
            }
        });
    }

    private void createProductOrder(){
        for (ProductOrder productOrder : cart.getProductList()) {

            Product product = productOrder.getProduct();
            Color color = productOrder.getColor();
            String size = productOrder.getSize();
            int quantity = productOrder.getQuantity();

            LinearLayout orderLayout = new LinearLayout(this);
            orderLayout.setOrientation(LinearLayout.HORIZONTAL);

            tvProductOrder = new TextView(this);
            tvProductOrder.setText(String.format("Product ID: %s\nProduct: %s\nColor: %s\nSize: %s\nQuantity %s\n", product.getId(), product.getName(), color.getName(), size, quantity));
            tvProductOrder.setLayoutParams(paramsOrder);

            orderLayout.addView(tvProductOrder);
            linearLayoutOrder.addView(orderLayout);
        }
    }

    private void sendIntent(){
        Intent intent;
        intent = new Intent(this, ShippingAddressActivity.class);
        startActivity(intent);
    }
}
