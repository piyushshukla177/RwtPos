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
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.service.adapter.DemandListAdapter;
import com.service.model.DemandList;
import com.service.network.ApiHelper;
import com.service.network.RetrofitClient;
import com.service.response_model.DemandListModel;
import com.service.response_model.GetInventoryResponse;
import com.service.util.PrefsHelper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InventoryListActivity extends AppCompatActivity {

    Context context;
    RecyclerView inventory_list_recyclerview;
    private DemandListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ProgressBar progressbar;
    private ApiHelper apiHelper;
    ArrayList<DemandList> inventory_list = new ArrayList<>();

    ImageView back_arrow;
    String store_name, outlet_name;
    SwipeRefreshLayout swipe_refresh_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_list);

        init();
    }


    void init() {
        context = this;
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        progressbar = findViewById(R.id.progressbar);
        inventory_list_recyclerview = findViewById(R.id.inventory_list_recyclerview);
        back_arrow = findViewById(R.id.back_arrow);
        swipe_refresh_layout = findViewById(R.id.swipe_refresh_layout);

        back_arrow.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        InventoryListActivity.super.onBackPressed();
                    }
                }
        );

        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getInventoryList();
            }
        });
        getInventoryList();
    }

    private void getInventoryList() {
        inventory_list.clear();
        swipe_refresh_layout.setRefreshing(false);
        progressbar.setVisibility(View.VISIBLE);
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        Call<GetInventoryResponse> loginCall = apiHelper.getInventory(PrefsHelper.getString(context, "username"), PrefsHelper.getString(context, "password"));
        loginCall.enqueue(new Callback<GetInventoryResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetInventoryResponse> call,
                                   @NonNull Response<GetInventoryResponse> response) {
                progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response != null) {
                        GetInventoryResponse m = response.body();
                        if (m.getStatus().equalsIgnoreCase("success")) {
                            DemandList model;
                            for (int i = 0; i < m.getData().size(); i++) {
                                model = new DemandList();
                                model.setOutlet_name(outlet_name);
                                model.setOwner_name(store_name);
                                inventory_list.add(model);
                            }
                        } else {
                            Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        inventory_list_recyclerview.setHasFixedSize(true);
                        mLayoutManager = new LinearLayoutManager(context);
                        mAdapter = new DemandListAdapter(context, inventory_list);
                        inventory_list_recyclerview.setLayoutManager(mLayoutManager);
                        inventory_list_recyclerview.setAdapter(mAdapter);
                    }
                } else {
                    progressbar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(@NonNull Call<GetInventoryResponse> call,
                                  @NonNull Throwable t) {
                progressbar.setVisibility(View.GONE);
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }
}