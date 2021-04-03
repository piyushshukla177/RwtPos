package com.service.rwtpos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.service.adapter.CustomerListAdapter;
import com.service.adapter.OrderListAdapter;
import com.service.bottom_sheet.AddCustomerSheet;
import com.service.model.CustomerList;
import com.service.model.OrderListModel;
import com.service.model.Products;
import com.service.network.ApiHelper;
import com.service.network.RetrofitClient;
import com.service.response_model.AddCustomerResponse;
import com.service.response_model.BillListModel;
import com.service.response_model.CustomerListModel;
import com.service.util.PrefsHelper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerListActivity extends AppCompatActivity implements AddCustomerSheet.AddCustomerSheetListener {

    Context context;
    RecyclerView customer_list_recyclerview;
    private CustomerListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ProgressBar progressbar;
    private ApiHelper apiHelper;
    ArrayList<CustomerList> customer_list = new ArrayList<>();
    TextView sale_tv;
    ImageView back_arrow, close_imageview;
    CardView add_new_customer_card;
    EditText search_edittext;
    SwipeRefreshLayout swipe_refresh_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        init();
    }

    AddCustomerSheet customerSheet;

    void init() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        context = this;
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        progressbar = findViewById(R.id.progressbar);
        customer_list_recyclerview = findViewById(R.id.customer_list_recyclerview);
        sale_tv = findViewById(R.id.sale_tv);
        back_arrow = findViewById(R.id.back_arrow);
        add_new_customer_card = findViewById(R.id.add_new_customer_card);
        search_edittext = findViewById(R.id.search_edittext);
        close_imageview = findViewById(R.id.close_imageview);
        swipe_refresh_layout = findViewById(R.id.swipe_refresh_layout);

        back_arrow.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CustomerListActivity.super.onBackPressed();
                    }
                }
        );
        add_new_customer_card.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        customerSheet = new AddCustomerSheet();
                        customerSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
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
                getCustomerList();
            }
        });
        getCustomerList();
    }

    private void filter(String text) {
        ArrayList<CustomerList> filteredList = new ArrayList<>();
        for (CustomerList item : customer_list) {
            if (item.getName().toLowerCase().contains(text.toLowerCase()) || item.getMobile_number().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        mAdapter.filterList(filteredList);
    }

    private void getCustomerList() {
        customer_list.clear();
        swipe_refresh_layout.setRefreshing(false);
        progressbar.setVisibility(View.VISIBLE);
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        Call<CustomerListModel> loginCall = apiHelper.getCustomerList(PrefsHelper.getString(context, "username"), PrefsHelper.getString(context, "password"));
        loginCall.enqueue(new Callback<CustomerListModel>() {
            @Override
            public void onResponse(@NonNull Call<CustomerListModel> call,
                                   @NonNull Response<CustomerListModel> response) {
                progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response != null) {
                        CustomerListModel m = response.body();
                        if (m != null && m.getStatus().equalsIgnoreCase("success")) {
                            CustomerList model;
                            for (int i = 0; i < m.getData().size(); i++) {
                                model = new CustomerList();
                                model.setCustomer_id(m.getData().get(i).getId());
                                model.setName(m.getData().get(i).getName());
                                model.setMobile_number(m.getData().get(i).getMobile());
                                model.setEmail(m.getData().get(i).getEmail());
                                model.setAddress(m.getData().get(i).getAddress());
                                customer_list.add(model);
                            }

                        } else {
                            Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        customer_list_recyclerview.setHasFixedSize(true);
                        mLayoutManager = new LinearLayoutManager(context);
                        mAdapter = new CustomerListAdapter(context, customer_list);
                        customer_list_recyclerview.setLayoutManager(mLayoutManager);
                        customer_list_recyclerview.setAdapter(mAdapter);
                    }
                } else {
                    progressbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CustomerListModel> call,
                                  @NonNull Throwable t) {
                progressbar.setVisibility(View.GONE);
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onCustomerAdded(String name, String email, String mobile, String address) {
        addCustomer(name, email, mobile, address);
    }

    private void addCustomer(String name, String email, String mobile, String address) {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Please Wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setOnCancelListener(new Dialog.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                // DO SOME STUFF HERE
            }
        });
        mProgressDialog.show();
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        Call<AddCustomerResponse> loginCall = apiHelper.addCustomer(PrefsHelper.getString(context, "username"), PrefsHelper.getString(context, "password"), name, email, mobile, address);
        loginCall.enqueue(new Callback<AddCustomerResponse>() {
            @Override
            public void onResponse(@NonNull Call<AddCustomerResponse> call,
                                   @NonNull Response<AddCustomerResponse> response) {
                progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    mProgressDialog.hide();
                    if (response != null) {
                        AddCustomerResponse m = response.body();
                        if (m.getStatus().equalsIgnoreCase("success")) {
                            customerSheet.dismiss();
                            Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                            getCustomerList();
                        } else {
                            Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    progressbar.setVisibility(View.GONE);
                    mProgressDialog.hide();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AddCustomerResponse> call,
                                  @NonNull Throwable t) {
                progressbar.setVisibility(View.GONE);
                mProgressDialog.hide();
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }
}
