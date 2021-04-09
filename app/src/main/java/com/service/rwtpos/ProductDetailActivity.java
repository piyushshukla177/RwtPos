package com.service.rwtpos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ProductDetailActivity extends AppCompatActivity {

    Context context;
    ImageView back_arrow, product_imageview;
    TextView prduct_name_tv, category_tv, group_tv, sale_price_tv, purchase_price_tv, available_stock_tv, minimum_stock_tv, tax_percentage_tv, product_sku_tv, description_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        init();
    }

    void init() {
        context = this;

        back_arrow = findViewById(R.id.back_arrow);
        product_imageview = findViewById(R.id.product_imageview);
        prduct_name_tv = findViewById(R.id.prduct_name_tv);
        category_tv = findViewById(R.id.category_tv);
        group_tv = findViewById(R.id.group_tv);
        sale_price_tv = findViewById(R.id.sale_price_tv);
        purchase_price_tv = findViewById(R.id.purchase_price_tv);
        available_stock_tv = findViewById(R.id.available_stock_tv);
        minimum_stock_tv = findViewById(R.id.minimum_stock_tv);
        tax_percentage_tv = findViewById(R.id.tax_percentage_tv);
        product_sku_tv = findViewById(R.id.product_sku_tv);
        description_tv = findViewById(R.id.description_tv);
        back_arrow.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProductDetailActivity.super.onBackPressed();
                    }
                }
        );
        Intent intent = getIntent();
        Picasso.get().load(intent.getStringExtra("product_pic")).resize(400, 400).into(product_imageview);
        prduct_name_tv.setText(intent.getStringExtra("product_name"));
        category_tv.setText(intent.getStringExtra("category"));
        group_tv.setText(intent.getStringExtra("group"));
        sale_price_tv.setText("₹ " + intent.getStringExtra("sale_price"));
        purchase_price_tv.setText("₹ " + intent.getStringExtra("purchase_price"));
        available_stock_tv.setText(intent.getStringExtra("available_qty"));
        minimum_stock_tv.setText(intent.getStringExtra("minimum_qty"));
        tax_percentage_tv.setText(intent.getStringExtra("tax_percent"));
        product_sku_tv.setText(intent.getStringExtra("sku"));
        description_tv.setText(intent.getStringExtra("description"));
    }
}