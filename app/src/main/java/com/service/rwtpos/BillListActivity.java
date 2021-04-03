package com.service.rwtpos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
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
    ImageView back_arrow, close_imageview;
    CardView create_new_bill_card;
    EditText search_edittext;
    SwipeRefreshLayout swipe_refresh_layout;
    String customer_id;

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
        search_edittext = findViewById(R.id.search_edittext);
        close_imageview = findViewById(R.id.close_imageview);
        swipe_refresh_layout = findViewById(R.id.swipe_refresh_layout);

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
                        intent.putExtra("bill_type", "newbill");
                        startActivity(intent);
                        finish();
                    }
                }
        );

        search_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().isEmpty()) {
                    close_imageview.setVisibility(View.GONE);
                } else {
                    close_imageview.setVisibility(View.VISIBLE);
                }
                filter(editable.toString());
            }
        });
        close_imageview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        search_edittext.setText("");
                    }
                }
        );
        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!customer_id.isEmpty()) {
                    getCustomerBills();
                } else {
                    getOrderList();
                }
            }
        });
        Intent intent = getIntent();
        if (intent.hasExtra("customer_id")) {
            customer_id = intent.getStringExtra("customer_id");
        }
        if (!customer_id.isEmpty()) {
            getCustomerBills();
        } else {
            getOrderList();
        }
    }

    private void filter(String text) {
        ArrayList<OrderListModel> filteredList = new ArrayList<>();
        for (OrderListModel item : order_list) {
            if (item.getBill_no().toLowerCase().contains(text.toLowerCase()) || item.getBill_date().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        mAdapter.filterList(filteredList);
    }

//    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private void getOrderList() {
        order_list.clear();
        swipe_refresh_layout.setRefreshing(false);
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
                        if (m != null && m.getStatus().equalsIgnoreCase("success")) {
                            OrderListModel model;
                            ArrayList<Products> array_list;
                            Products d;
                            for (int i = 0; i < m.getData().size(); i++) {
                                array_list = new ArrayList<>();
                                model = new OrderListModel();
                                model.setBill_no(m.getData().get(i).getBill_no());
                                model.setBill_date(m.getData().get(i).getBill_date());
                                model.setCustomer_name(m.getData().get(i).getCustomer_name());
                                model.setCustomer_mobile(m.getData().get(i).getCustomer_mobile());
                                model.setCgst(m.getData().get(i).getCgst());
                                model.setSgst(m.getData().get(i).getSgst());
                                model.setNet_payable(m.getData().get(i).getNet_payable());
                                model.setTotal(m.getData().get(i).getTotal());
                                model.setPayment_mode(m.getData().get(i).getPayment_mode());
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
                        Log.e("list_size", order_list.size() + "");
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

    private void getCustomerBills() {
        order_list.clear();
        swipe_refresh_layout.setRefreshing(false);
        progressbar.setVisibility(View.VISIBLE);
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        Call<BillListModel> customerBillsCall = apiHelper.getCustomerBills(PrefsHelper.getString(context, "username"), PrefsHelper.getString(context, "password"), customer_id);
        customerBillsCall.enqueue(new Callback<BillListModel>() {
            @Override
            public void onResponse(@NonNull Call<BillListModel> call,
                                   @NonNull Response<BillListModel> response) {
                progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response != null) {
                        BillListModel m = response.body();
                        if (m != null && m.getStatus().equalsIgnoreCase("success")) {
                            OrderListModel model;
                            ArrayList<Products> array_list;
                            Products d;
                            for (int i = 0; i < m.getData().size(); i++) {
                                array_list = new ArrayList<>();
                                model = new OrderListModel();
                                model.setBill_no(m.getData().get(i).getBill_no());
                                model.setBill_date(m.getData().get(i).getBill_date());
                                model.setCustomer_name(m.getData().get(i).getCustomer_name());
                                model.setCustomer_mobile(m.getData().get(i).getCustomer_mobile());
                                model.setCgst(m.getData().get(i).getCgst());
                                model.setSgst(m.getData().get(i).getSgst());
                                model.setNet_payable(m.getData().get(i).getNet_payable());
                                model.setTotal(m.getData().get(i).getTotal());
                                model.setPayment_mode(m.getData().get(i).getPayment_mode());
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
                        Log.e("list_size", order_list.size() + "");
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
