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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.service.adapter.BillDetailAdapter;
import com.service.adapter.OrderListAdapter;
import com.service.adapter.ReturnListAdapter;
import com.service.model.OrderListModel;
import com.service.model.ProductDetailModel;
import com.service.model.Products;
import com.service.model.ReturnListModel;
import com.service.network.ApiHelper;
import com.service.network.RetrofitClient;
import com.service.response_model.BillListModel;
import com.service.response_model.ReturnBillListResponse;
import com.service.util.PrefsHelper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReturnListActivity extends AppCompatActivity {

    Context context;
    CardView create_new_return_card;
    ImageView back_arrow, close_imageview;

    public ArrayList<ReturnListModel> return_list = new ArrayList();
    ProgressBar progressbar;
    RecyclerView return_list_recyclerview;
    private ReturnListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    SwipeRefreshLayout swipe_refresh_layout;
    private ApiHelper apiHelper;
    EditText search_edittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_list);

        init();
    }

    void init() {
        context = this;
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        create_new_return_card = findViewById(R.id.create_new_return_card);
        back_arrow = findViewById(R.id.back_arrow);
        progressbar = findViewById(R.id.progressbar);
        return_list_recyclerview = findViewById(R.id.return_list_recyclerview);
        swipe_refresh_layout = findViewById(R.id.swipe_refresh_layout);
        close_imageview = findViewById(R.id.close_imageview);
        search_edittext = findViewById(R.id.search_edittext);
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
        swipe_refresh_layout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        getReturnList();
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

        getReturnList();
    }

    private void filter(String text) {
        ArrayList<ReturnListModel> filteredList = new ArrayList<>();
        for (ReturnListModel item : return_list) {
            if (item.getReturn_invoice().toLowerCase().contains(text.toLowerCase()) || item.getReturn_date().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        mAdapter.filterList(filteredList);
    }


    private void getReturnList() {
        return_list.clear();
        swipe_refresh_layout.setRefreshing(false);
        progressbar.setVisibility(View.VISIBLE);
        Call<ReturnBillListResponse> loginCall = apiHelper.getReturnBillList(PrefsHelper.getString(context, "username"), PrefsHelper.getString(context, "password"));
        loginCall.enqueue(new Callback<ReturnBillListResponse>() {
            @Override
            public void onResponse(@NonNull Call<ReturnBillListResponse> call,
                                   @NonNull Response<ReturnBillListResponse> response) {
                progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response != null) {
                        ReturnBillListResponse m = response.body();
                        if (m != null && m.getStatus().equalsIgnoreCase("success")) {
                            ReturnListModel model;
                            Products d;
                            for (int i = 0; i < m.getData().size(); i++) {
                                model = new ReturnListModel();
                                model.setBill_id(m.getData().get(i).getBill_id());
                                model.setCustomer_id(m.getData().get(i).getCustomer_id());
                                model.setDiscount(m.getData().get(i).getDiscount_amt());
                                model.setNet_payable(m.getData().get(i).getNet_payable());
                                model.setId(m.getData().get(i).getId());
                                model.setReturn_invoice(m.getData().get(i).getReturn_invoice());
                                model.setReturn_date(m.getData().get(i).getReturn_date());
                                return_list.add(model);
                            }
                        } else {
                            Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        return_list_recyclerview.setHasFixedSize(true);
                        mLayoutManager = new LinearLayoutManager(context);
                        mAdapter = new ReturnListAdapter(context, return_list);
                        return_list_recyclerview.setLayoutManager(mLayoutManager);
                        return_list_recyclerview.setAdapter(mAdapter);
                    }
                } else {
                    progressbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ReturnBillListResponse> call,
                                  @NonNull Throwable t) {
                progressbar.setVisibility(View.GONE);
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }

}