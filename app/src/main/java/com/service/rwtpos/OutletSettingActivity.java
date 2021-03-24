package com.service.rwtpos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.service.network.ApiHelper;
import com.service.network.RetrofitClient;
import com.service.response_model.AddCustomerResponse;
import com.service.response_model.OutletSettingModel;
import com.service.util.PrefsHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OutletSettingActivity extends AppCompatActivity {

    Context context;
    EditText owner_name_et, store_name_et, email_et, mobile_et, address_et;
    SwitchCompat header_switch, footer_switch;
    Button save_btn;
    ProgressBar progressbar;
    private ApiHelper apiHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlet_setting);
        init();
    }

    void init() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        context = this;
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        owner_name_et = findViewById(R.id.owner_name_et);
        store_name_et = findViewById(R.id.store_name_et);
        email_et = findViewById(R.id.email_et);
        mobile_et = findViewById(R.id.mobile_et);
        header_switch = findViewById(R.id.header_switch);
        footer_switch = findViewById(R.id.footer_switch);
        address_et = findViewById(R.id.address_et);
        save_btn = findViewById(R.id.save_btn);
        progressbar = findViewById(R.id.progressbar);

        save_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (check()) {
                            SaveSettings();
                        }
                    }
                }
        );
    }

    boolean check() {
        boolean b = true;
        if (owner_name_et.getText().toString().isEmpty()) {
            b = false;
            Toast.makeText(context, "Enter Owner Name", Toast.LENGTH_SHORT).show();
            owner_name_et.requestFocus();
            return b;
        } else if (store_name_et.getText().toString().isEmpty()) {
            b = false;
            Toast.makeText(context, "Enter Store Name", Toast.LENGTH_SHORT).show();
            store_name_et.requestFocus();
            return b;
        } else if (email_et.getText().toString().isEmpty()) {
            b = false;
            Toast.makeText(context, "Enter Email Address", Toast.LENGTH_SHORT).show();
            email_et.requestFocus();
            return b;
        } else if (mobile_et.getText().toString().isEmpty()) {
            b = false;
            Toast.makeText(context, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
            mobile_et.requestFocus();
            return b;
        } else if (address_et.getText().toString().isEmpty()) {
            b = false;
            Toast.makeText(context, "Enter Address", Toast.LENGTH_SHORT).show();
            address_et.requestFocus();
            return b;
        }
        return b;
    }

    private void SaveSettings() {
        String header = String.valueOf(0);
        String footer = String.valueOf(0);
        if (header_switch.isChecked()) {
            header = String.valueOf(1);
        }
        if (footer_switch.isChecked()) {
            footer = String.valueOf(1);
        }
        progressbar.setVisibility(View.VISIBLE);
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        Call<OutletSettingModel> loginCall = apiHelper.setOutletSettings(PrefsHelper.getString(context, "username"),
                PrefsHelper.getString(context, "password"), owner_name_et.getText().toString(), store_name_et.getText().toString(),
                email_et.getText().toString(), mobile_et.getText().toString(), address_et.getText().toString(), header, footer);
        loginCall.enqueue(new Callback<OutletSettingModel>() {
            @Override
            public void onResponse(@NonNull Call<OutletSettingModel> call,
                                   @NonNull Response<OutletSettingModel> response) {
                progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {

                    if (response != null) {
                        OutletSettingModel m = response.body();
                        if (m.getStatus().equalsIgnoreCase("success")) {

                            Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    progressbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<OutletSettingModel> call,
                                  @NonNull Throwable t) {
                progressbar.setVisibility(View.GONE);
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }
}
