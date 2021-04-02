package com.service.rwtpos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.service.network.ApiHelper;
import com.service.network.RetrofitClient;
import com.service.response_model.BillListModel;
import com.service.response_model.CommonModel;
import com.service.response_model.DashboardModel;
import com.service.util.PrefsHelper;
import com.service.util.VolleyMultipartRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    ImageView logout_imageview;
    Context context;
    private ApiHelper apiHelper;
    ProgressBar progressbar;
    TextView monthly_order_tv, today_order_tv, total_order_tv, total_demand_tv, total_challan_tv, monthly_business_tv, total_business_tv, today_business;
    RelativeLayout create_bill_relative, settings_relative, create_demand_relative, challan_list_relative, bill_list_relative;
    LinearLayout today_business_linear, monthly_business_linear, total_business_linear;
    SwipeRefreshLayout swipe_refresh_layout;

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
        create_demand_relative = findViewById(R.id.create_demand_relative);
        challan_list_relative = findViewById(R.id.challan_list_relative);
        bill_list_relative = findViewById(R.id.bill_list_relative);
        swipe_refresh_layout = findViewById(R.id.swipe_refresh_layout);
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
        create_demand_relative.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DashboardActivity.this, DemandListActivity.class);
                        startActivity(intent);
                    }
                }
        );
        challan_list_relative.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DashboardActivity.this, ChallanListActivity.class);
                        startActivity(intent);
                    }
                }
        );
        bill_list_relative.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DashboardActivity.this, BillListActivity.class);
                        startActivity(intent);
                    }
                }
        );
//      getBillPdf();
        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDashboarddata();
            }
        });
        checkPermissions();
        getDashboarddata();
    }

    private void getDashboarddata() {
        swipe_refresh_layout.setRefreshing(false);
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
//                          Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
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

    String[] appPermissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public int PERMISSION_CODE = 100;

    public boolean checkPermissions() {

        List<String> lsitPermissionsNeeded = new ArrayList<>();
        for (String perm : appPermissions) {

            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                lsitPermissionsNeeded.add(perm);
            }
        }
        //check for non granted permissions
        if (!lsitPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, lsitPermissionsNeeded.toArray(new String[lsitPermissionsNeeded.size()]), PERMISSION_CODE);
            return false;
        }
        //app have all permissions proceed ahead
        return true;
    }
}