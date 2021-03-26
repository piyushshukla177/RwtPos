package com.service.rwtpos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.service.adapter.BillDetailAdapter;
import com.service.adapter.OrderListAdapter;
import com.service.model.OrderListModel;
import com.service.model.ProductDetailModel;
import com.service.model.Products;
import com.service.network.ApiHelper;
import com.service.network.RetrofitClient;
import com.service.response_model.BillByIdModel;
import com.service.response_model.BillListModel;
import com.service.response_model.CommonModel;
import com.service.response_model.LoginModel;
import com.service.util.PrefsHelper;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillDetailsAcitvity extends AppCompatActivity {

    Context context;
    public ArrayList<Products> products_list = new ArrayList();
    ProgressBar progressbar;
    RecyclerView bill_detail_recyclerview;
    TextView bill_no_tv, bill_date_tv, customer_name_tv, customer_mobile_tv, total_items_tv;
    ImageView back_arrow;
    ArrayList<ProductDetailModel> list = new ArrayList<>();
    private BillDetailAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    LinearLayout total_linear;
    TextView net_payable_tv, round_off_tv, total_amt_tv, sgst_tv, cgst_tv, taxable_amount_tv, discount_amount_tv;
    private ApiHelper apiHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);
        init();
    }

    void init() {
        context = this;
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        progressbar = findViewById(R.id.progressbar);
        bill_detail_recyclerview = findViewById(R.id.bill_detail_recyclerview);
        bill_no_tv = findViewById(R.id.bill_no_tv);
        bill_date_tv = findViewById(R.id.bill_date_tv);
        customer_name_tv = findViewById(R.id.customer_name_tv);
        customer_mobile_tv = findViewById(R.id.customer_mobile_tv);
        back_arrow = findViewById(R.id.back_arrow);
        net_payable_tv = findViewById(R.id.net_payable_tv);
        round_off_tv = findViewById(R.id.round_off_tv);
        total_amt_tv = findViewById(R.id.total_amt_tv);
        sgst_tv = findViewById(R.id.sgst_tv);
        cgst_tv = findViewById(R.id.cgst_tv);
        discount_amount_tv = findViewById(R.id.discount_amount_tv);
        taxable_amount_tv = findViewById(R.id.taxable_amount_tv);
        total_items_tv = findViewById(R.id.total_items_tv);
        total_linear = findViewById(R.id.total_linear);
        back_arrow.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BillDetailsAcitvity.super.onBackPressed();
                    }
                }
        );
        Intent intent = getIntent();
        products_list = (ArrayList<Products>) intent.getSerializableExtra("productlist");
        bill_no_tv.setText(intent.getStringExtra("bill_no"));
        bill_date_tv.setText(intent.getStringExtra("bill_date"));
        customer_name_tv.setText(intent.getStringExtra("customer_name"));
        customer_mobile_tv.setText(intent.getStringExtra("customer_mobile"));
        net_payable_tv.setText(intent.getStringExtra("net_payable"));
        round_off_tv.setText(intent.getStringExtra("round_off"));
        total_amt_tv.setText(intent.getStringExtra("total"));
        sgst_tv.setText(intent.getStringExtra("sgst"));
        cgst_tv.setText(intent.getStringExtra("cgst"));
        taxable_amount_tv.setText(intent.getStringExtra("taxable"));
        discount_amount_tv.setText(intent.getStringExtra("discount"));
        GetBIllById(intent.getStringExtra("id"));
    }

    void setData() {
        int i = 0;
        total_linear.setVisibility(View.VISIBLE);
        total_items_tv.setText("ITEMS(" + products_list.size() + ")");
        ProductDetailModel m;
        while (i < products_list.size()) {
            m = new ProductDetailModel();
            Products d = products_list.get(i);
            m.setProduct_name(d.getProduct_Name());
            m.setSale_price(d.getSale_Price());
            m.setQuantity(d.getQuantity());
            float total = Integer.parseInt(d.getQuantity()) * Float.parseFloat(d.getSale_Price());
            m.setTotal(String.valueOf(total));
            list.add(m);
            i++;
        }
        bill_detail_recyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        mAdapter = new BillDetailAdapter(context, list);
        bill_detail_recyclerview.setLayoutManager(mLayoutManager);
        bill_detail_recyclerview.setAdapter(mAdapter);
    }

    private void GetBIllById(String id) {
        Call<BillByIdModel> loginCall = apiHelper.getBillById(PrefsHelper.getString(context, "username"), PrefsHelper.getString(context, "password"), id);
        loginCall.enqueue(new Callback<BillByIdModel>() {
            @Override
            public void onResponse(@NonNull Call<BillByIdModel> call,
                                   @NonNull Response<BillByIdModel> response) {
                progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {

                    if (response != null) {
                        BillByIdModel m = response.body();
                        if (m.getStatus().equalsIgnoreCase("success")) {
                            Log.e("id", "list size " + products_list.size() + " id =" + id + " data szie " + m.getData().size());
                            for (int j = 0; j < m.getData().size(); j++) {
                                products_list.get(j).setProduct_Name(m.getData().get(j).getProduct());
                            }
                            setData();
                        } else {
                            Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    progressbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BillByIdModel> call,
                                  @NonNull Throwable t) {
                progressbar.setVisibility(View.GONE);
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }
}
