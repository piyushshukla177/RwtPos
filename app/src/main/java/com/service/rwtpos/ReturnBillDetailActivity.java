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
import com.service.response_model.ReturnBillDetailResponse;
import com.service.response_model.ViewOutletBillModel;
import com.service.util.PrefsHelper;

import java.io.File;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReturnBillDetailActivity extends AppCompatActivity {

    String retrurn_invoice_id;
    private ApiHelper apiHelper;
    Context context;
    public ArrayList<Products> products_list = new ArrayList();
    ProgressBar progressbar;
    RecyclerView bill_detail_recyclerview;
    TextView bill_no_tv, bill_date_tv, customer_name_tv, customer_mobile_tv, total_items_tv;
    ImageView back_arrow, download_invoice_imageview;
    ArrayList<ProductDetailModel> list = new ArrayList<>();
    private BillDetailAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    LinearLayout total_linear;
    TextView net_payable_tv, round_off_tv, total_amt_tv, sgst_tv, cgst_tv, taxable_amount_tv, discount_amount_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_bill_detail);
        init();
    }

    void init() {
        context = this;
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
        download_invoice_imageview = findViewById(R.id.download_invoice_imageview);
        back_arrow.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ReturnBillDetailActivity.super.onBackPressed();
                    }
                }
        );
        Intent intent = getIntent();
        retrurn_invoice_id = intent.getStringExtra("retrurn_invoice_id");
//        bill_no_tv.setText(intent.getStringExtra("bill_no"));
//        bill_date_tv.setText(intent.getStringExtra("bill_date"));
//        customer_name_tv.setText(intent.getStringExtra("customer_name"));
//        customer_mobile_tv.setText(intent.getStringExtra("customer_mobile"));
//        net_payable_tv.setText(intent.getStringExtra("net_payable"));
//        round_off_tv.setText(intent.getStringExtra("round_off"));
//        total_amt_tv.setText(intent.getStringExtra("total"));
//        sgst_tv.setText(intent.getStringExtra("sgst"));
//        cgst_tv.setText(intent.getStringExtra("cgst"));
//        taxable_amount_tv.setText(intent.getStringExtra("taxable"));
//        discount_amount_tv.setText(intent.getStringExtra("discount"));
        GetBIllById(retrurn_invoice_id);
    }

    private void GetBIllById(String id) {
        progressbar.setVisibility(View.VISIBLE);
        Call<ReturnBillDetailResponse> loginCall = apiHelper.getReturnBillDetail(PrefsHelper.getString(context, "username"), PrefsHelper.getString(context, "password"), id);
        loginCall.enqueue(new Callback<ReturnBillDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<ReturnBillDetailResponse> call,
                                   @NonNull Response<ReturnBillDetailResponse> response) {
                progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {

                    if (response != null) {
                        ReturnBillDetailResponse m = response.body();
                        if (m.getStatus().equalsIgnoreCase("success")) {
                            total_linear.setVisibility(View.VISIBLE);
                            ProductDetailModel model;
                            bill_no_tv.setText(m.getData().getReturn_no());
                            bill_date_tv.setText(m.getData().getReturn_date());
                            customer_name_tv.setText(m.getData().getCustomer_details().getName());
                            customer_mobile_tv.setText(m.getData().getCustomer_details().getMobile());
                            discount_amount_tv.setText(m.getData().getDiscount_amt());
                            taxable_amount_tv.setText(m.getData().getTaxable_amt());
                            cgst_tv.setText(m.getData().getCgst());
                            sgst_tv.setText(m.getData().getSgst());
                            net_payable_tv.setText(m.getData().getNet_payable());
                            total_amt_tv.setText(m.getData().getTotal());
                            round_off_tv.setText(m.getData().getRound_off());

                            for (int j = 0; j < m.getData().getProduct_data().size(); j++) {
                                model = new ProductDetailModel();
                                model.setProduct_name(m.getData().getProduct_data().get(j).getProduct());
                                model.setSale_price(m.getData().getProduct_data().get(j).getBatch());
                                model.setQuantity(m.getData().getProduct_data().get(j).getSingle_Return_Quantity());
                                model.setTotal(String.valueOf(m.getData().getProduct_data().get(j).getSingle_Return_Final_Price()));
                                list.add(model);
                            }
                            Log.e("list_size", list.size() + "");
                            bill_detail_recyclerview.setHasFixedSize(true);
                            mLayoutManager = new LinearLayoutManager(context);
                            mAdapter = new BillDetailAdapter(context, list);
                            bill_detail_recyclerview.setLayoutManager(mLayoutManager);
                            bill_detail_recyclerview.setAdapter(mAdapter);
                        } else {
                            Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    progressbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ReturnBillDetailResponse> call,
                                  @NonNull Throwable t) {
                progressbar.setVisibility(View.GONE);
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }
}
