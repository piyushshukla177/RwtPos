package com.service.rwtpos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.service.adapter.OrderListAdapter;
import com.service.model.OrderListModel;
import com.service.model.Products;
import com.service.network.ApiHelper;
import com.service.network.RetrofitClient;
import com.service.response_model.BillListModel;
import com.service.util.PrefsHelper;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillListActivity extends AppCompatActivity {

    Context context;
    RecyclerView bill_list_recyclerview;
    private OrderListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ProgressBar progressbar;
    private ApiHelper apiHelper;
    ArrayList<OrderListModel> order_list = new ArrayList<>();
    TextView sale_tv;
    ImageView back_arrow;
    CardView create_new_bill_card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_list);
        init();
    }

    void init() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        context = this;
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        progressbar = findViewById(R.id.progressbar);
        bill_list_recyclerview = findViewById(R.id.bill_list_recyclerview);
        sale_tv = findViewById(R.id.sale_tv);
        back_arrow = findViewById(R.id.back_arrow);
        create_new_bill_card = findViewById(R.id.create_new_bill_card);

        back_arrow.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BillListActivity.super.onBackPressed();
                    }
                }
        );
        create_new_bill_card.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(BillListActivity.this, CreateBillActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );
        getOrderList();
    }

//    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private void getOrderList() {
        progressbar.setVisibility(View.VISIBLE);
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        Call<BillListModel> loginCall = apiHelper.getBillingList(PrefsHelper.getString(context, "username"), PrefsHelper.getString(context, "password"));
        loginCall.enqueue(new Callback<BillListModel>() {
            @Override
            public void onResponse(@NonNull Call<BillListModel> call,
                                   @NonNull Response<BillListModel> response) {
                progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response != null) {
                        BillListModel m = response.body();
                        if (m.getStatus().equalsIgnoreCase("success")) {
                            OrderListModel model;
                            ArrayList<Products> array_list = new ArrayList<>();
                            Products d;
                            for (int i = 0; i < m.getData().size(); i++) {
                                array_list.clear();
                                model = new OrderListModel();
                                model.setBill_no(m.getData().get(i).getBill_no());
                                model.setBill_date(m.getData().get(i).getBill_date());
                                model.setCustomer_name(m.getData().get(i).getCustomer_name());
                                model.setCustomer_mobile(m.getData().get(i).getCustomer_mobile());
                                model.setCgst(m.getData().get(i).getCgst());
                                model.setSgst(m.getData().get(i).getSgst());
                                model.setNet_payable(m.getData().get(i).getNet_payable());
                                model.setTotal(m.getData().get(i).getTotal());
                                model.setPayment_type(m.getData().get(i).getPayment_type());
                                model.setCustomer_id(m.getData().get(i).getCustomer_id());
                                model.setOutlet_id(m.getData().get(i).getOutlet_id());
                                model.setRound_off(m.getData().get(i).getRound_off());
                                model.setTaxable(m.getData().get(i).getTaxable_amt());
                                model.setTotal_discount(m.getData().get(i).getDiscount_amt());
                                model.setId(m.getData().get(i).getId());

                                for (int j = 0; j < m.getData().get(i).getProduct_data().size(); j++) {
                                    BillListModel.Product_data p = m.getData().get(i).getProduct_data().get(j);
                                    d = new Products();
                                    d.setBatch(p.getBatch());
                                    d.setSingleDiscountAmt(p.getSingleDiscountAmt());
                                    d.setSingleDiscountPer(p.getSingleDiscountPer());
                                    d.setSingleTaxRatePer(p.getSingleTaxRatePer());
                                    d.setFinal_Price(p.getFinal_Price());
                                    d.setTotal_Basic_price(p.getTotal_Basic_price());
                                    d.setTotal_CGST(p.getTotal_CGST());
                                    d.setTotal_SGST(p.getTotal_SGST());
                                    d.setTotal_Tax_Amt(p.getTotal_Tax_Amt());
                                    d.setSingle_Basic_Price(p.getSingle_Basic_Price());
                                    d.setSingle_CGST(p.getSingle_CGST());
                                    d.setSingle_SGST(p.getSingle_SGST());
                                    d.setSingle_Tax_Amt(p.getSingle_Tax_Amt());
                                    d.setQuantity(p.getQuantity());
                                    d.setSale_Price(p.getSale_Price());
                                    d.setProduct_Id(p.getProduct_Id());
                                    array_list.add(d);
                                }
                                Log.e("size", array_list.size() + "");
                                model.setProduct_list(array_list);
                                order_list.add(model);
                            }
                        } else {
                            Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        bill_list_recyclerview.setHasFixedSize(true);
                        mLayoutManager = new LinearLayoutManager(context);
                        mAdapter = new OrderListAdapter(context, order_list);
                        bill_list_recyclerview.setLayoutManager(mLayoutManager);
                        bill_list_recyclerview.setAdapter(mAdapter);
                    }
                } else {
                    progressbar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(@NonNull Call<BillListModel> call,
                                  @NonNull Throwable t) {
                progressbar.setVisibility(View.GONE);
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }
}
