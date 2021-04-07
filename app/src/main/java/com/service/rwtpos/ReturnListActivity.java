package com.service.rwtpos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ReturnListActivity extends AppCompatActivity {

    Context context;
    CardView create_new_return_card;
    ImageView back_arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_list);

        init();
    }

    void init() {
        context = this;
        create_new_return_card = findViewById(R.id.create_new_return_card);
        back_arrow = findViewById(R.id.back_arrow);
        create_new_return_card.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ReturnListActivity.this, SalesReturnActivity.class);
                        startActivity(intent);
                    }
                }
        );

        back_arrow.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ReturnListActivity.super.onBackPressed();
                    }
                }
        );
    }
}