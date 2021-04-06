package com.service.rwtpos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

public class SalesReturnActivity extends AppCompatActivity {

    Context context;
    RecyclerView sale_return_recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_return);
        init();
    }

    void init() {
        context = this;
        sale_return_recyclerview = findViewById(R.id.sale_return_recyclerview);
    }
}
