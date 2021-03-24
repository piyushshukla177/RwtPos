package com.service.rwtpos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.service.network.ApiHelper;
import com.service.network.RetrofitClient;
import com.service.response_model.BillListModel;
import com.service.response_model.CommonModel;
import com.service.response_model.DashboardModel;
import com.service.util.PrefsHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    ImageView logout_imageview;
    Context context;
    private ApiHelper apiHelper;
    ProgressBar progressbar;
    TextView monthly_order_tv, today_order_tv, total_order_tv, total_demand_tv, total_challan_tv, monthly_business_tv, total_business_tv, today_business;
    RelativeLayout create_bill_relative, settings_relative;
    LinearLayout today_business_linear, monthly_business_linear, total_business_linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        init();
    }

    void init() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        context = this;
        progressbar = findViewById(R.id.progressbar);
        monthly_order_tv = findViewById(R.id.monthly_order_tv);
        today_order_tv = findViewById(R.id.today_order_tv);
        total_order_tv = findViewById(R.id.total_order_tv);
        total_demand_tv = findViewById(R.id.total_demand_tv);
        total_challan_tv = findViewById(R.id.total_challan_tv);
        monthly_business_tv = findViewById(R.id.monthly_business_tv);
        total_business_tv = findViewById(R.id.total_business_tv);
        today_business = findViewById(R.id.today_business);
        logout_imageview = findViewById(R.id.logout_imageview);
        today_business_linear = findViewById(R.id.today_business_linear);
        monthly_business_linear = findViewById(R.id.monthly_business_linear);
        total_business_linear = findViewById(R.id.total_business_linear);
        create_bill_relative = findViewById(R.id.create_bill_relative);
        settings_relative = findViewById(R.id.settings_relative);
        logout_imageview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new AlertDialog.Builder(context)
                                // .setTitle("Delete entry")
                                .setMessage("Are you sure you want to logout !!")

                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                // The dialog is automatically dismissed when a dialog button is clicked.
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Continue with delete operation
                                        PrefsHelper.remove(context, "username");
                                        PrefsHelper.remove(context, "password");
                                        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                })

                                // A null listener allows the button to dismiss the dialog and take no further action.
                                .setNegativeButton(android.R.string.no, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                }
        );
        today_business_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DashboardActivity.this, OrderListActivity.class);
                        intent.putExtra("sale", "today");
                        startActivity(intent);
                    }
                }
        );
        monthly_business_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DashboardActivity.this, OrderListActivity.class);
                        intent.putExtra("sale", "monthly");
                        startActivity(intent);
                    }
                }
        );
        total_business_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DashboardActivity.this, OrderListActivity.class);
                        intent.putExtra("sale", "total");
                        startActivity(intent);
                    }
                }
        );
        create_bill_relative.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DashboardActivity.this, CreateBillActivity.class);
                        startActivity(intent);
                    }
                }
        );
        settings_relative.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DashboardActivity.this, OutletSettingActivity.class);
                        startActivity(intent);
                    }
                }
        );
        getDashboarddata();
    }

    private void getDashboarddata() {
        progressbar.setVisibility(View.VISIBLE);
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        Call<DashboardModel> loginCall = apiHelper.getDashboardData(PrefsHelper.getString(context, "username"), PrefsHelper.getString(context, "password"));
        loginCall.enqueue(new Callback<DashboardModel>() {
            @Override
            public void onResponse(@NonNull Call<DashboardModel> call,
                                   @NonNull Response<DashboardModel> response) {
                progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response != null) {
                        DashboardModel m = response.body();
                        if (m.getStatus().equalsIgnoreCase("success")) {
                            monthly_order_tv.setText(m.getData().getMonthly_order() + "");
                            today_order_tv.setText(m.getData().getToday_order() + "");
                            total_order_tv.setText(m.getData().getTotal_order() + "");
                            total_demand_tv.setText(m.getData().getTotal_demand() + "");
                            total_challan_tv.setText(m.getData().getTotal_challan() + "");
                            monthly_business_tv.setText("₹ " + m.getData().getMonthly_business() + "");
                            total_business_tv.setText("₹ " + m.getData().getTotal_business() + "");
                            today_business.setText("₹ " + m.getData().getToday_business() + "");
//                            Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    progressbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<DashboardModel> call,
                                  @NonNull Throwable t) {
                progressbar.setVisibility(View.GONE);
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }
}