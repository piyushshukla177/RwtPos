package com.service.rwtpos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.HashMap;
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


  /*  void getBillPdf() {
        progressbar.setVisibility(View.VISIBLE);
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, "http://192.168.29.191/rwtpos/Api/billinvoice",
                new com.android.volley.Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            progressbar.setVisibility(View.GONE);
                            Log.e("response",response.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressbar.setVisibility(View.GONE);
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("username", "RPO424843");
                params.put("password", "972739");
                params.put("bill_id", String.valueOf(45));
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                return params;
            }
        };
        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(10 * DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0, 0));
        //adding the request to volley
        Volley.newRequestQueue(context).add(volleyMultipartRequest);
    }*/
}