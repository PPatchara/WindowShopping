package com.example.justwyne.windowshopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.justwyne.windowshopping.models.Cart;

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

//        Log.d("Intent", "Cart");
//        cart = (Cart) getIntent().getSerializableExtra("cart");
        initInstances();
//        Log.d("Cart", String.format("%s %s", cart.size(),cart.getTotal()));
//        createProductOrder();
        cart = Cart.getInstance();
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
        LinearLayout orderLayout = new LinearLayout(this);
        orderLayout.setOrientation(LinearLayout.VERTICAL);


        tvProductOrder = new TextView(this);

        tvProductOrder.setText(cart.size());
        tvProductOrder.setLayoutParams(paramsOrder);

        orderLayout.addView(tvProductOrder);
        linearLayoutOrder.addView(orderLayout);
    }

    private void sendIntent(){
        Intent intent;
        intent = new Intent(this, ShippingAddressActivity.class);
        startActivity(intent);
    }
}
