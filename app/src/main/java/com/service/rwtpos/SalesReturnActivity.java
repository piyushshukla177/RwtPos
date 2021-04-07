package com.service.rwtpos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.service.adapter.OrderListAdapter;
import com.service.model.ReturnProductListModel;
import com.service.network.ApiHelper;
import com.service.network.RetrofitClient;
import com.service.response_model.BillByBillNoModel;
import com.service.util.PrefsHelper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalesReturnActivity extends AppCompatActivity {

    Context context;
    RecyclerView sale_return_recyclerview;
    ImageView back_arrow;
    EditText invoice_number_et;
    Button search_btn;
    private OrderListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ApiHelper apiHelper;
    ArrayList<ReturnProductListModel> return_product_list = new ArrayList<>();
    ProgressBar progressbar;
    TextView bill_no_tv, bill_date_tv, customer_name_tv, customer_mobile_tv;
    RelativeLayout bill_no_relative, customer_name_relative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_return);
        init();
    }

    void init() {
        context = this;
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        back_arrow = findViewById(R.id.back_arrow);
        sale_return_recyclerview = findViewById(R.id.sale_return_recyclerview);
        invoice_number_et = findViewById(R.id.invoice_number_et);
        search_btn = findViewById(R.id.search_btn);
        progressbar = findViewById(R.id.progressbar);
        bill_no_tv = findViewById(R.id.bill_no_tv);
        bill_date_tv = findViewById(R.id.bill_date_tv);
        customer_name_tv = findViewById(R.id.customer_name_tv);
        customer_mobile_tv = findViewById(R.id.customer_mobile_tv);
        bill_no_relative = findViewById(R.id.bill_no_relative);
        customer_name_relative = findViewById(R.id.customer_name_relative);
        back_arrow.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SalesReturnActivity.super.onBackPressed();
                    }
                }
        );
        search_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!invoice_number_et.getText().toString().isEmpty()) {
                            getReturnList(invoice_number_et.getText().toString());
                        } else {
                            Toast.makeText(context, "Enter Invoice Number", Toast.LENGTH_SHORT).show();
                            invoice_number_et.requestFocus();
                        }
                    }
                }
        );
    }

    private void getReturnList(String bill_no) {
        progressbar.setVisibility(View.VISIBLE);
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        Call<BillByBillNoModel> loginCall = apiHelper.getBillByBillNo(PrefsHelper.getString(context, "username"), PrefsHelper.getString(context, "password"), bill_no);
        loginCall.enqueue(new Callback<BillByBillNoModel>() {
            @Override
            public void onResponse(@NonNull Call<BillByBillNoModel> call,
                                   @NonNull Response<BillByBillNoModel> response) {
                progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response != null) {
                        ReturnProductListModel model;

                        bill_no_relative.setVisibility(View.VISIBLE);
                        customer_name_relative.setVisibility(View.VISIBLE);
                        BillByBillNoModel m = response.body();
                        bill_no_tv.setText(m.getData().getBill_no());
                        bill_date_tv.setText(m.getData().getBill_date());
                        customer_name_tv.setText(m.getData().getCustomer_details().getName());
                        customer_mobile_tv.setText(m.getData().getCustomer_details().getMobile());
                        bill_date_tv.setText(m.getData().getBill_date());
                        for (int i = 0; i < m.getData().getProduct_data().size(); i++) {
                            model = new ReturnProductListModel();
                            model.setBatch(m.getData().getProduct_data().get(i).getBatch());
                            model.setFinal_Price(m.getData().getProduct_data().get(i).getFinal_Price());
                            model.setProduct(m.getData().getProduct_data().get(i).getProduct());
                            model.setQuantity(m.getData().getProduct_data().get(i).getQuantity());
                            model.setSale_Price(m.getData().getProduct_data().get(i).getSale_Price());
                            model.setSingle_Basic_Price(m.getData().getProduct_data().get(i).getSingle_Basic_Price());
                            model.setSingle_CGST(m.getData().getProduct_data().get(i).getSingle_CGST());
                            model.setSingle_SGST(m.getData().getProduct_data().get(i).getSingle_SGST());
                            model.setSingle_Tax_Amt(m.getData().getProduct_data().get(i).getSingle_Tax_Amt());
                            model.setSingleDiscountAmt(m.getData().getProduct_data().get(i).getSingleDiscountAmt());
                            model.setSingleDiscountPer(m.getData().getProduct_data().get(i).getSingleDiscountPer());
                            model.setSingleTaxRatePer(m.getData().getProduct_data().get(i).getSingleTaxRatePer());
                            model.setTotal_Basic_price(m.getData().getProduct_data().get(i).getTotal_Basic_price());
                            model.setTotal_CGST(m.getData().getProduct_data().get(i).getTotal_CGST());
                            model.setTotal_SGST(m.getData().getProduct_data().get(i).getTotal_SGST());
                            model.setTotal_Tax_Amt(m.getData().getProduct_data().get(i).getTotal_Tax_Amt());
                            return_product_list.add(model);
                        }
                    }
                } else {
                    progressbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BillByBillNoModel> call,
                                  @NonNull Throwable t) {
                progressbar.setVisibility(View.GONE);
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }
}
