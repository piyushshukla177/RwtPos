package com.service.rwtpos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.service.adapter.ProductListAdapter;
import com.service.bottom_sheet.AddProductByBarcodeSheet;
import com.service.bottom_sheet.EditDemandSheet;
import com.service.model.ProductListModel;
import com.service.network.ApiHelper;
import com.service.network.RetrofitClient;
import com.service.response_model.CreateDemandModel;
import com.service.response_model.ProductByBarcodeResponse;
import com.service.util.PrefsHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateDemandActivity extends AppCompatActivity implements AddProductByBarcodeSheet.AddProductByBarcodeSheetListener, EditDemandSheet.EditDemandSheetListener {

    Button add_items_btn;
    Context context;
    ProgressBar progressbar;
    TextView add_product_tv, total_items_tv;
    ArrayList<ProductListModel> product_list = new ArrayList<>();
    private ApiHelper apiHelper;
    RecyclerView item_list_recyclerview;
    private ProductListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    CardView create_demand_cardview;
    ImageView back_image, scan_barcode_imageview;
    public static CreateDemandActivity cdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_demand);

        init();
    }

    void init() {
        cdd = this;
        context = this;
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        add_items_btn = findViewById(R.id.add_items_btn);
        progressbar = findViewById(R.id.progressbar);
        add_product_tv = findViewById(R.id.add_product_tv);
        total_items_tv = findViewById(R.id.total_items_tv);
        item_list_recyclerview = findViewById(R.id.item_list_recyclerview);
        create_demand_cardview = findViewById(R.id.create_demand_cardview);
        back_image = findViewById(R.id.back_image);
        scan_barcode_imageview = findViewById(R.id.scan_barcode_imageview);
        add_items_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AddProductByBarcodeSheet bottomSheet = new AddProductByBarcodeSheet();
                        bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
                    }
                }
        );
        add_product_tv.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AddProductByBarcodeSheet bottomSheet = new AddProductByBarcodeSheet();
                        bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
                    }
                }
        );
        create_demand_cardview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (product_list.size() > 0) {
                            CreateDemand();
                        }
                    }
                }
        );
        back_image.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CreateDemandActivity.super.onBackPressed();
                    }
                }
        );
        scan_barcode_imageview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CreateDemandActivity.this, ScanBarcodeActivity.class);
                        intent.putExtra("from_activity", "CreateDemandActivity");
                        startActivity(intent);
                    }
                }
        );
        mAdapter = new ProductListAdapter(context, product_list);
    }

    @Override
    public void onBarcodeAdded(String barcode) {
//        if (checkBarcodeAlready(barcode)) {
        getProductBybarcode(barcode);
//        } else {
//            Toast.makeText(context, "Barcode Already Added", Toast.LENGTH_SHORT).show();
//        }
    }

    public void getProductBybarcode(String barcode) {
        if (checkBarcodeAlready(barcode)) {
            progressbar.setVisibility(View.VISIBLE);
            apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
            Call<ProductByBarcodeResponse> loginCall = apiHelper.getProductByBarcode(PrefsHelper.getString(context, "username"), PrefsHelper.getString(context, "password"), barcode);
            loginCall.enqueue(new Callback<ProductByBarcodeResponse>() {
                @Override
                public void onResponse(@NonNull Call<ProductByBarcodeResponse> call,
                                       @NonNull Response<ProductByBarcodeResponse> response) {
                    progressbar.setVisibility(View.GONE);
                    if (response.isSuccessful()) {
                        if (response != null) {
                            ProductByBarcodeResponse m = response.body();
                            if (m.getStatus().equalsIgnoreCase("success")) {
                                ProductListModel model = null;
                                try {
                                    model = new ProductListModel();
                                } catch (Exception ex) {
                                    Toast.makeText(context, "Product Not Found", Toast.LENGTH_SHORT).show();
                                    ex.printStackTrace();
                                }
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
                                model.setBasic_price(m.getData().getProduct_details().getBasic_price());
                                model.setTotal_discount_amount(String.valueOf(0));
                                model.setSelected_batch(m.getData().getProduct_details().getSale_price());
                                model.setSelected_qty(String.valueOf(1));
                                model.setSingle_discount_amount(String.valueOf(0));
                                model.setSingle_tax(m.getData().getProduct_details().getTotal_tax());
                                model.setDiscount_percentage(String.valueOf(0));

                                ProductListModel.Batch b;
                                ArrayList<ProductListModel.Batch> batch_list = new ArrayList<>();
                                for (int i = 0; i < m.getData().getInventory().size(); i++) {
                                    b = new ProductListModel.Batch();
                                    b.setPrice(m.getData().getInventory().get(i).getPrice());
                                    b.setQty(String.valueOf(m.getData().getInventory().get(i).getQty()));
                                    batch_list.add(b);
                                }
                                model.setBatch_list(batch_list);
                                if (checkBarcodeAlready(barcode)) {
                                    product_list.add(model);
                                }
                                if (product_list.size() > 0) {
                                    add_items_btn.setVisibility(View.GONE);
                                    add_product_tv.setVisibility(View.VISIBLE);
                                    total_items_tv.setText("ITEMS ( " + product_list.size() + " )");
                                }
                                if (product_list.size() == 1) {
                                    item_list_recyclerview.setHasFixedSize(true);
                                    mLayoutManager = new LinearLayoutManager(context);
                                    item_list_recyclerview.setLayoutManager(mLayoutManager);
                                    item_list_recyclerview.setAdapter(mAdapter);
                                } else {
                                    mAdapter.notifyDataSetChanged();
                                }
                            } else {
                                Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        progressbar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ProductByBarcodeResponse> call,
                                      @NonNull Throwable t) {
                    progressbar.setVisibility(View.GONE);
                    if (!call.isCanceled()) {
                    }
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(context, "Product Already Added", Toast.LENGTH_SHORT).show();
        }
    }

    boolean checkBarcodeAlready(String barcode) {
        boolean b = true;
        int i = 0;
        while (i < product_list.size()) {
            ProductListModel m = product_list.get(i);
            String x = barcode;
            String y = m.getBarcode();
            boolean z = x.equals(y);
            if (m.getBarcode().equals(barcode)) {
                b = false;
                return b;
            }
            i++;
        }
        return b;
    }

    public void hideAdditionalFields() {
        add_items_btn.setVisibility(View.VISIBLE);
        total_items_tv.setVisibility(View.GONE);
        add_product_tv.setVisibility(View.GONE);
        total_items_tv.setText("ITEMS ( " + product_list.size() + " )");
    }

    @Override
    public void onEditDemand(int index, String quantity) {
        product_list.get(index).setSelected_qty(quantity);
        mAdapter.notifyDataSetChanged();
    }

    private void CreateDemand() {
        JSONArray array = new JSONArray();
        JSONObject obj = null;
        try {
            int i = 0;
            while (i < product_list.size()) {
                obj = new JSONObject();
                obj.put("Product_Id", product_list.get(i).getId());
                obj.put("Quantity", product_list.get(i).getSelected_qty());
                array.put(obj);
                i++;
            }
            Log.e("json_array", array.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Please Wait...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setOnCancelListener(new Dialog.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // DO SOME STUFF HERE
            }
        });
        mProgressDialog.show();
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        Call<CreateDemandModel> loginCall = apiHelper.addDemand(PrefsHelper.getString(context, "username"), PrefsHelper.getString(context, "password"), array.toString()
        );
        loginCall.enqueue(new Callback<CreateDemandModel>() {
            @Override
            public void onResponse(@NonNull Call<CreateDemandModel> call,
                                   @NonNull Response<CreateDemandModel> response) {
                progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    mProgressDialog.hide();
                    if (response != null) {
                        CreateDemandModel m = response.body();
                        if (m.getStatus().equalsIgnoreCase("success")) {
                            Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    progressbar.setVisibility(View.GONE);
                    mProgressDialog.hide();
                }
            }

            @Override
            public void onFailure(@NonNull Call<CreateDemandModel> call,
                                  @NonNull Throwable t) {
                progressbar.setVisibility(View.GONE);
                mProgressDialog.hide();
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }
}