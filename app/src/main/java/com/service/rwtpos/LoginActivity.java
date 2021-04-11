package com.service.rwtpos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.service.network.ApiHelper;
import com.service.network.RetrofitClient;
import com.service.response_model.CommonModel;
import com.service.response_model.DashboardModel;
import com.service.response_model.LoginModel;
import com.service.util.PrefsHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Context context;
    RelativeLayout login_relative;
    EditText email_id_et, password_et;
    ProgressBar progressbar;
    private ApiHelper apiHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    void init() {
        context = this;
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        progressbar = findViewById(R.id.progressbar);
        email_id_et = findViewById(R.id.email_id_et);
        password_et = findViewById(R.id.password_et);
        login_relative = findViewById(R.id.login_relative);
        login_relative.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (check()) {
                            Login();
                        }
                    }
                }
        );
    }

    boolean check() {
        boolean b = true;
        if (email_id_et.getText().toString().isEmpty()) {
            b = false;
            return b;
        } else if (password_et.getText().toString().isEmpty()) {
            b = false;
            return b;
        }
        return b;
    }

    private void Login() {
        progressbar.setVisibility(View.VISIBLE);
        Call<CommonModel<LoginModel>> loginCall = apiHelper.getLogin(email_id_et.getText().toString(), password_et.getText().toString());
        loginCall.enqueue(new Callback<CommonModel<LoginModel>>() {
            @Override
            public void onResponse(@NonNull Call<CommonModel<LoginModel>> call,
                                   @NonNull Response<CommonModel<LoginModel>> response) {
                progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {

                    try {
                        if (response != null) {
                            CommonModel<LoginModel> m = response.body();
                            if (m.getStatus().equalsIgnoreCase("success")) {
                                PrefsHelper.putString(context, "username", email_id_et.getText().toString());
                                PrefsHelper.putString(context, "password", password_et.getText().toString());
                                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    progressbar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(@NonNull Call<CommonModel<LoginModel>> call,
                                  @NonNull Throwable t) {
                progressbar.setVisibility(View.GONE);
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }
}