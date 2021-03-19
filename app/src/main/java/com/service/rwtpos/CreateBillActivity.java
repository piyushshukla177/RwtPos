package com.service.rwtpos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.service.adapter.OrderListAdapter;
import com.service.adapter.ProductListAdapter;
import com.service.bottom_sheet.AddProductByBarcodeSheet;
import com.service.model.ProductListModel;
import com.service.network.ApiHelper;
import com.service.network.RetrofitClient;
import com.service.response_model.CommonModel;
import com.service.response_model.DashboardModel;
import com.service.response_model.ProductByBarcode;
import com.service.util.PrefsHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateBillActivity extends AppCompatActivity implements AddProductByBarcodeSheet.AddProductByBarcodeSheetListener {

    ProgressBar progressbar;
    TextView invoice_date_tv, add_product_tv;
    Context context;
    SimpleDateFormat date_format = new SimpleDateFormat("dd-MMM-yyyy");
    private ApiHelper apiHelper;
    RecyclerView item_list_recyclerview;
    ArrayList<ProductListModel> product_list = new ArrayList<>();
    private ProductListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bill);

        init();
    }

    void init() {
        context = this;
        invoice_date_tv = findViewById(R.id.invoice_date_tv);
        add_product_tv = findViewById(R.id.add_product_tv);
        progressbar = findViewById(R.id.progressbar);
        item_list_recyclerview = findViewById(R.id.item_list_recyclerview);
        invoice_date_tv.setText(date_format.format(new Date()));
        add_product_tv.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AddProductByBarcodeSheet bottomSheet = new AddProductByBarcodeSheet();
                        bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
                    }
                }
        );
    }

    @Override
    public void onBarcodeAdded(String barcode) {
        getProductBybarcode(barcode);
    }

    private void getProductBybarcode(String barcode) {
        progressbar.setVisibility(View.VISIBLE);
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        Call<ProductByBarcode> loginCall = apiHelper.getProductByBarcode(PrefsHelper.getString(context, "username"), PrefsHelper.getString(context, "password"), barcode);
        loginCall.enqueue(new Callback<ProductByBarcode>() {
            @Override
            public void onResponse(@NonNull Call<ProductByBarcode> call,
                                   @NonNull Response<ProductByBarcode> response) {
                progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response != null) {
                        ProductByBarcode m = response.body();
                        if (m.getStatus().equalsIgnoreCase("success")) {
                            ProductListModel model = new ProductListModel();
                            model.setBarcode(m.getData().getProduct_details().getBarcode());
                            model.setCgst(m.getData().getProduct_details().getCgst());
                            model.setSgst(m.getData().getProduct_details().getSgst());
                            model.setHsn(m.getData().getProduct_details().getHsn());
                            model.setId(m.getData().getProduct_details().getId());
                            model.setGst(m.getData().getProduct_details().getGst());
                            model.setIntStock(m.getData().getProduct_details().getIntStock());
                            model.setMinmum_stock(m.getData().getProduct_details().getMinmum_stock());
                            model.setPro_print_name(m.getData().getProduct_details().getPro_print_name());
                            model.setSale_price(m.getData().getProduct_details().getSale_price());
                            model.setPurchase_price(m.getData().getProduct_details().getPurchase_price());
                            model.setTax_percent(m.getData().getProduct_details().getTax_percent());
                            model.setTotal_tax(m.getData().getProduct_details().getTotal_tax());
                            model.setProduct_pic(m.getData().getProduct_details().getProduct_pic());
                            model.setStr_product(m.getData().getProduct_details().getStr_product());
                            ProductListModel.Batch b;
                            ArrayList<ProductListModel.Batch> batch_list = new ArrayList<>();
                            for (int i = 0; i < m.getData().getInventory().size(); i++) {
                                b = new ProductListModel.Batch();
                                b.setPrice(m.getData().getInventory().get(i).getPrice());
                                b.setQty(String.valueOf(m.getData().getInventory().get(i).getQty()));
                                batch_list.add(b);
                            }
                            model.setBatch_list(batch_list);
                            product_list.add(model);
                            item_list_recyclerview.setHasFixedSize(true);
                            mLayoutManager = new LinearLayoutManager(context);
                            mAdapter = new ProductListAdapter(context, product_list);
                            item_list_recyclerview.setLayoutManager(mLayoutManager);
                            item_list_recyclerview.setAdapter(mAdapter);
                        } else {
                            Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    progressbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProductByBarcode> call,
                                  @NonNull Throwable t) {
                progressbar.setVisibility(View.GONE);
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }
}