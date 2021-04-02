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
import com.service.model.ProductDetailModel;
import com.service.model.Products;
import com.service.network.ApiHelper;
import com.service.network.RetrofitClient;
import com.service.response_model.ChallanDetailModel;
import com.service.response_model.ViewOutletBillModel;
import com.service.util.PrefsHelper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChallanDetailsActivity extends AppCompatActivity {


    Context context;
    public ArrayList<Products> products_list = new ArrayList();
    ProgressBar progressbar;
    RecyclerView challan_detail_recyclerview;
    TextView challan_number_tv, challan_date_tv, total_items_tv;
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
        setContentView(R.layout.activity_challan_details);

        init();
    }

    void init() {
        context = this;
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        progressbar = findViewById(R.id.progressbar);
        challan_detail_recyclerview = findViewById(R.id.challan_detail_recyclerview);
        challan_number_tv = findViewById(R.id.challan_number_tv);
        challan_date_tv = findViewById(R.id.challan_date_tv);
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
                        ChallanDetailsActivity.super.onBackPressed();
                    }
                }
        );
        Intent intent = getIntent();
        products_list = (ArrayList<Products>) intent.getSerializableExtra("productlist");
        challan_number_tv.setText(intent.getStringExtra("challan_no"));
        challan_date_tv.setText(intent.getStringExtra("challan_date"));
        net_payable_tv.setText(intent.getStringExtra("net_payable"));
        round_off_tv.setText(intent.getStringExtra("round_off"));
        total_amt_tv.setText(intent.getStringExtra("total"));
        sgst_tv.setText(intent.getStringExtra("sgst"));
        cgst_tv.setText(intent.getStringExtra("cgst"));
        taxable_amount_tv.setText(intent.getStringExtra("taxable"));
        GetChallanById(intent.getStringExtra("id"));
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
        challan_detail_recyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        mAdapter = new BillDetailAdapter(context, list);
        challan_detail_recyclerview.setLayoutManager(mLayoutManager);
        challan_detail_recyclerview.setAdapter(mAdapter);
    }

    private void GetChallanById(String id) {
        progressbar.setVisibility(View.VISIBLE);
        Call<ChallanDetailModel> loginCall = apiHelper.getChallanDetails(PrefsHelper.getString(context, "username"), PrefsHelper.getString(context, "password"), id);
        loginCall.enqueue(new Callback<ChallanDetailModel>() {
            @Override
            public void onResponse(@NonNull Call<ChallanDetailModel> call,
                                   @NonNull Response<ChallanDetailModel> response) {
                progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {

                    if (response != null) {
                        ChallanDetailModel m = response.body();
                        if (m.getStatus().equalsIgnoreCase("success")) {
                            for (int i = 0; i < m.getData().size(); i++) {
                                ProductDetailModel model;
                                for (int j = 0; j < m.getData().get(i).getProduct_data().size(); j++) {
                                    model = new ProductDetailModel();
                                    model.setProduct_name(m.getData().get(i).getProduct_data().get(i).getProduct_Name());
                                    model.setQuantity(m.getData().get(i).getProduct_data().get(i).getQuantity());
                                    model.setSale_price(m.getData().get(i).getProduct_data().get(i).getTotal_Basic_price());
                                    model.setTotal(m.getData().get(i).getProduct_data().get(i).getFinal_Price());
                                    list.add(model);
                                }
                            }
                            challan_detail_recyclerview.setHasFixedSize(true);
                            mLayoutManager = new LinearLayoutManager(context);
                            mAdapter = new BillDetailAdapter(context, list);
                            challan_detail_recyclerview.setLayoutManager(mLayoutManager);
                            challan_detail_recyclerview.setAdapter(mAdapter);
                        } else {
                            Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    progressbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ChallanDetailModel> call,
                                  @NonNull Throwable t) {
                progressbar.setVisibility(View.GONE);
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }
}
