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
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.service.adapter.DemandListAdapter;
import com.service.adapter.OrderListAdapter;
import com.service.model.DemandList;
import com.service.model.OrderListModel;
import com.service.network.ApiHelper;
import com.service.network.RetrofitClient;
import com.service.response_model.BillListModel;
import com.service.response_model.DemandListModel;
import com.service.response_model.GetOutLetSettings;
import com.service.util.PrefsHelper;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DemandListActivity extends AppCompatActivity {

    Context context;
    RecyclerView demand_list_recyclerview;
    private DemandListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ProgressBar progressbar;
    private ApiHelper apiHelper;
    ArrayList<DemandList> demand_list = new ArrayList<>();

    ImageView back_arrow;
    String store_name, outlet_name;
    CardView create_new_demand_card;
    SwipeRefreshLayout swipe_refresh_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demand_list);
        init();
    }

    void init() {
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        context = this;
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        progressbar = findViewById(R.id.progressbar);
        demand_list_recyclerview = findViewById(R.id.demand_list_recyclerview);
        back_arrow = findViewById(R.id.back_arrow);
        create_new_demand_card = findViewById(R.id.create_new_demand_card);
        swipe_refresh_layout = findViewById(R.id.swipe_refresh_layout);

        back_arrow.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DemandListActivity.super.onBackPressed();
                    }
                }
        );
        create_new_demand_card.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, CreateDemandActivity.class);
                        startActivity(intent);
                    }
                }
        );
        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getSettings();
            }
        });
        getSettings();
    }

    private void getDemandList() {
        demand_list.clear();
        swipe_refresh_layout.setRefreshing(false);
        progressbar.setVisibility(View.VISIBLE);
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        Call<DemandListModel> loginCall = apiHelper.getDemandList(PrefsHelper.getString(context, "username"), PrefsHelper.getString(context, "password"));
        loginCall.enqueue(new Callback<DemandListModel>() {
            @Override
            public void onResponse(@NonNull Call<DemandListModel> call,
                                   @NonNull Response<DemandListModel> response) {
                progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response != null) {
                        DemandListModel m = response.body();
                        if (m.getStatus().equalsIgnoreCase("success")) {
                            DemandList model;
                            for (int i = 0; i < m.getData().size(); i++) {
                                model = new DemandList();
                                model.setOutlet_name(outlet_name);
                                model.setOwner_name(store_name);
                                model.setOn_date(m.getData().get(i).getOndate());
                                model.setIntStatus(m.getData().get(i).getDelivery_status());
                                model.setId(m.getData().get(i).getId());
                                model.setOutlet_id(m.getData().get(i).getOutlet_id());
                                demand_list.add(model);
                            }
//                          Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        demand_list_recyclerview.setHasFixedSize(true);
                        mLayoutManager = new LinearLayoutManager(context);
                        mAdapter = new DemandListAdapter(context, demand_list);
                        demand_list_recyclerview.setLayoutManager(mLayoutManager);
                        demand_list_recyclerview.setAdapter(mAdapter);
                    }
                } else {
                    progressbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<DemandListModel> call,
                                  @NonNull Throwable t) {
                progressbar.setVisibility(View.GONE);
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }

    private void getSettings() {
        progressbar.setVisibility(View.VISIBLE);
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        Call<GetOutLetSettings> loginCall = apiHelper.getOutLetSettings(PrefsHelper.getString(context, "username"),
                PrefsHelper.getString(context, "password"));
        loginCall.enqueue(new Callback<GetOutLetSettings>() {
            @Override
            public void onResponse(@NonNull Call<GetOutLetSettings> call,
                                   @NonNull Response<GetOutLetSettings> response) {
                progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {

                    if (response != null) {
                        GetOutLetSettings m = response.body();
                        if (m.getStatus().equalsIgnoreCase("success")) {
                            outlet_name = m.getData().getOwner_name();
                            store_name = m.getData().getStore_name();
                            getDemandList();
                        } else {
                            Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    progressbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetOutLetSettings> call,
                                  @NonNull Throwable t) {
                progressbar.setVisibility(View.GONE);
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }
}