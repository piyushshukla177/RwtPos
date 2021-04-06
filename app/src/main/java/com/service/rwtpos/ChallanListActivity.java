package com.service.rwtpos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.service.adapter.ChallanListAdapter;
import com.service.adapter.DemandListAdapter;
import com.service.model.ChallanList;
import com.service.model.DemandList;
import com.service.network.ApiHelper;
import com.service.network.RetrofitClient;
import com.service.response_model.ChallanListModel;
import com.service.response_model.DemandListModel;
import com.service.util.PrefsHelper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChallanListActivity extends AppCompatActivity {

    Context context;
    RecyclerView challan_list_recyclerview;
    private ChallanListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ProgressBar progressbar;
    private ApiHelper apiHelper;
    ArrayList<ChallanList> challan_list = new ArrayList<>();

    ImageView back_arrow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challan_list);
        init();
    }

    void init() {
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        context = this;
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        progressbar = findViewById(R.id.progressbar);
        challan_list_recyclerview = findViewById(R.id.challan_list_recyclerview);
        back_arrow = findViewById(R.id.back_arrow);

        back_arrow.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ChallanListActivity.super.onBackPressed();
                    }
                }
        );
        getChallanList();
    }

    private void getChallanList() {
        progressbar.setVisibility(View.VISIBLE);
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        Call<ChallanListModel> getChallanCall = apiHelper.getChallanList(PrefsHelper.getString(context, "username"), PrefsHelper.getString(context, "password"));
        getChallanCall.enqueue(new Callback<ChallanListModel>() {
            @Override
            public void onResponse(@NonNull Call<ChallanListModel> call,
                                   @NonNull Response<ChallanListModel> response) {
                progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response != null) {
                        ChallanListModel m = response.body();
                        if (m.getStatus().equalsIgnoreCase("success")) {
                            ChallanList model;
                            for (int i = 0; i < m.getData().size(); i++) {
                                model = new ChallanList();
                                model.setChallan_invoice(m.getData().get(i).getChallan_invoice());
                                model.setChallan_date(m.getData().get(i).getChallan_date());
                                model.setCgst(m.getData().get(i).getCgst());
                                model.setSgst(m.getData().get(i).getSgst());
                                model.setDescription(m.getData().get(i).getDescription());
                                model.setIntStatus(m.getData().get(i).getIntStatus());
                                model.setMode_of_transport(m.getData().get(i).getMode_of_transport());
                                model.setNet_payable(m.getData().get(i).getNet_payable());
                                model.setRound_off(m.getData().get(i).getRound_off());
                                model.setTaxable_amt(m.getData().get(i).getTaxable_amt());
                                model.setTotal(m.getData().get(i).getTotal());
                                model.setId(m.getData().get(i).getId());
                                challan_list.add(model);
                            }
//                          Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        challan_list_recyclerview.setHasFixedSize(true);
                        mLayoutManager = new LinearLayoutManager(context);
                        mAdapter = new ChallanListAdapter(context, challan_list);
                        challan_list_recyclerview.setLayoutManager(mLayoutManager);
                        challan_list_recyclerview.setAdapter(mAdapter);
                    }
                } else {
                    progressbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ChallanListModel> call,
                                  @NonNull Throwable t) {
                progressbar.setVisibility(View.GONE);
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }
}