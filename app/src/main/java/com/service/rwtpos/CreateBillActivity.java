package com.service.rwtpos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.service.adapter.OrderListAdapter;
import com.service.adapter.ProductListAdapter;
import com.service.bottom_sheet.AddCustomerSheet;
import com.service.bottom_sheet.AddProductByBarcodeSheet;
import com.service.bottom_sheet.DiscardChangesSheet;
import com.service.bottom_sheet.EditItemSheet;
import com.service.model.OrderListModel;
import com.service.model.ProductListModel;
import com.service.network.ApiHelper;
import com.service.network.RetrofitClient;
import com.service.response_model.AddCustomerResponse;
import com.service.response_model.BillListModel;
import com.service.response_model.CommonModel;
import com.service.response_model.DashboardModel;
import com.service.response_model.ProductByBarcode;
import com.service.util.PrefsHelper;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateBillActivity extends AppCompatActivity implements AddProductByBarcodeSheet.AddProductByBarcodeSheetListener, EditItemSheet.EditItemSheetListener, AddCustomerSheet.AddCustomerSheetListener, DiscardChangesSheet.DiscardChangesListener {

    ProgressBar progressbar;
    TextView invoice_date_tv, add_product_tv, total_items_tv, discount_amount_tv, taxable_amount_tv, cgst_tv, sgst_tv, total_amt_tv, net_payable_tv;
    Context context;
    SimpleDateFormat date_format = new SimpleDateFormat("dd-MMM-yyyy");
    private ApiHelper apiHelper;
    RecyclerView item_list_recyclerview;
    ArrayList<ProductListModel> product_list = new ArrayList<>();
    private ProductListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ImageView back_image, add_customer_imageview;
    Spinner payment_mode_spinner;
    TextInputLayout transaction_id_input_layout, wallet_name_layout, transaction_date_input_layout;
    ArrayList paymentMode_list = new ArrayList();
    Button add_items_btn;
    RelativeLayout dicount_relative, taxable_relative, cgst_relative, sgst_relative, total_relative, net_payable_relative;
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bill);

        init();
    }

    AddCustomerSheet customerSheet;

    void init() {
        context = this;
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        invoice_date_tv = findViewById(R.id.invoice_date_tv);
        back_image = findViewById(R.id.back_image);
        add_product_tv = findViewById(R.id.add_product_tv);
        progressbar = findViewById(R.id.progressbar);
        item_list_recyclerview = findViewById(R.id.item_list_recyclerview);
        add_customer_imageview = findViewById(R.id.add_customer_imageview);
        payment_mode_spinner = findViewById(R.id.payment_mode_spinner);
        transaction_id_input_layout = findViewById(R.id.transaction_id_input_layout);
        wallet_name_layout = findViewById(R.id.wallet_name_layout);
        transaction_date_input_layout = findViewById(R.id.transaction_date_input_layout);
        total_items_tv = findViewById(R.id.total_items_tv);
        add_items_btn = findViewById(R.id.add_items_btn);

        dicount_relative = findViewById(R.id.dicount_relative);
        taxable_relative = findViewById(R.id.taxable_relative);
        cgst_relative = findViewById(R.id.cgst_relative);
        sgst_relative = findViewById(R.id.sgst_relative);
        total_relative = findViewById(R.id.total_relative);
        net_payable_relative = findViewById(R.id.net_payable_relative);
        discount_amount_tv = findViewById(R.id.discount_amount_tv);
        taxable_amount_tv = findViewById(R.id.taxable_amount_tv);
        cgst_tv = findViewById(R.id.cgst_tv);
        sgst_tv = findViewById(R.id.sgst_tv);
        total_amt_tv = findViewById(R.id.total_amt_tv);
        net_payable_tv = findViewById(R.id.net_payable_tv);

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
        back_image.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (product_list.size() > 0) {
                            DiscardChangesSheet bottomSheet = new DiscardChangesSheet();
                            bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
                        } else {
                            CreateBillActivity.super.onBackPressed();
                        }
                    }
                }
        );
        add_customer_imageview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        customerSheet = new AddCustomerSheet();
                        customerSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
                    }
                }
        );
        mAdapter = new ProductListAdapter(context, product_list);

        paymentMode_list.add("-- Select Payment Mode --");
        paymentMode_list.add("Cash");
        paymentMode_list.add("Wallet");
        paymentMode_list.add("NEFT/RTGS");
        paymentMode_list.add("UPI");
        paymentMode_list.add("IMPS");

        ArrayAdapter<String> adapter = new ArrayAdapter(context,
                R.layout.spinner_layout, paymentMode_list);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_layout);
        payment_mode_spinner.setAdapter(adapter);

        payment_mode_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position == 0 | position == 1) {
                    transaction_id_input_layout.setVisibility(View.GONE);
                    wallet_name_layout.setVisibility(View.GONE);
                    transaction_date_input_layout.setVisibility(View.GONE);
                } else if (position == 2) {
                    transaction_id_input_layout.setVisibility(View.VISIBLE);
                    wallet_name_layout.setVisibility(View.VISIBLE);
                    transaction_date_input_layout.setVisibility(View.VISIBLE);
                } else if (position == 3 | position == 4 | position == 5) {
                    transaction_id_input_layout.setVisibility(View.VISIBLE);
                    wallet_name_layout.setVisibility(View.GONE);
                    transaction_date_input_layout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
        add_items_btn.setOnClickListener(
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
        barcode = "35345345345353";
        if (checkBarcodeAlready(barcode)) {
            getProductBybarcode(barcode);
        } else {
            Toast.makeText(CreateBillActivity.this, "Barcode Already Added", Toast.LENGTH_SHORT).show();
        }
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
                            model.setSelected_qty(String.valueOf(1));
                            model.setProduct_pic(m.getData().getProduct_details().getProduct_pic());
                            model.setStr_product(m.getData().getProduct_details().getStr_product());
                            model.setBasic_price(m.getData().getProduct_details().getBasic_price());
                            model.setTotal_discount_amount(String.valueOf(0));
                            model.setSelected_batch(m.getData().getProduct_details().getSale_price());
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
                            if (product_list.size() > 0) {
                                payment_mode_spinner.setVisibility(View.VISIBLE);
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
                            calculate_Total();
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

    @Override
    public void onItemEdited(int index, String salePrice, String qty, String discount, String selectedbatch) {
        try {
            float sale_Price = Float.parseFloat(salePrice);
            int tax_percentage = Integer.parseInt(product_list.get(index).getTax_percent());
            float basic_price = ((sale_Price) / (100 + tax_percentage)) * 100;
            float tax_amount = (sale_Price - basic_price) * Integer.parseInt(qty);
            float cgst = tax_amount / 2;
            float sgst = tax_amount / 2;

            float total_basic = basic_price * Integer.parseInt(qty);
            float discount_amount = ((total_basic * Integer.parseInt(discount)) / 100) * Integer.parseInt(qty);
            product_list.get(index).setSelected_qty(qty);
            product_list.get(index).setSale_price(salePrice);
            product_list.get(index).setDiscount_percentage(discount);
            product_list.get(index).setSelected_batch(selectedbatch);
            product_list.get(index).setBasic_price(String.valueOf(basic_price));
            product_list.get(index).setTotal_tax(String.valueOf(tax_amount));
            product_list.get(index).setCgst(String.valueOf(cgst));
            product_list.get(index).setSgst(String.valueOf(sgst));
            product_list.get(index).setTotal_discount_amount(String.valueOf(discount_amount));
            mAdapter.notifyDataSetChanged();
            calculate_Total();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onCustomerAdded(String name, String email, String mobile, String address) {
        addCustomer(name, email, mobile, address);
    }

    private void addCustomer(String name, String email, String mobile, String address) {
//      progressbar.setVisibility(View.VISIBLE);
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
        Call<AddCustomerResponse> loginCall = apiHelper.addCustomer(PrefsHelper.getString(context, "username"), PrefsHelper.getString(context, "password"), name, email, mobile, address);
        loginCall.enqueue(new Callback<AddCustomerResponse>() {
            @Override
            public void onResponse(@NonNull Call<AddCustomerResponse> call,
                                   @NonNull Response<AddCustomerResponse> response) {
                progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    mProgressDialog.hide();
                    if (response != null) {
                        AddCustomerResponse m = response.body();
                        if (m.getStatus().equalsIgnoreCase("success")) {
                            customerSheet.dismiss();
                            Toast.makeText(CreateBillActivity.this, m.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CreateBillActivity.this, m.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    progressbar.setVisibility(View.GONE);
                    mProgressDialog.hide();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AddCustomerResponse> call,
                                  @NonNull Throwable t) {
                progressbar.setVisibility(View.GONE);
                mProgressDialog.hide();
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }

    public void hideAdditionalFields() {
        payment_mode_spinner.setVisibility(View.GONE);
        add_items_btn.setVisibility(View.VISIBLE);
        total_items_tv.setVisibility(View.GONE);
        add_product_tv.setVisibility(View.GONE);
        transaction_date_input_layout.setVisibility(View.GONE);
        transaction_id_input_layout.setVisibility(View.GONE);
        wallet_name_layout.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (product_list.size() > 0) {
            DiscardChangesSheet bottomSheet = new DiscardChangesSheet();
            bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onDiscardBill() {
        CreateBillActivity.this.finish();
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

    void calculate_Total() {
        dicount_relative.setVisibility(View.VISIBLE);
        cgst_relative.setVisibility(View.VISIBLE);
        sgst_relative.setVisibility(View.VISIBLE);
        net_payable_relative.setVisibility(View.VISIBLE);
        total_relative.setVisibility(View.VISIBLE);
        taxable_relative.setVisibility(View.VISIBLE);

        float sum_discount_amount = 0;
        float sum_taxable = 0;
        float tax_total = 0;
        float net_payable = 0;
        int i = 0;
        while (i < product_list.size()) {
            sum_discount_amount = sum_discount_amount + Float.parseFloat(product_list.get(i).getTotal_discount_amount());
            sum_taxable = sum_taxable + (Float.parseFloat(product_list.get(i).getBasic_price()));
            tax_total = tax_total + Float.parseFloat(product_list.get(i).getTotal_tax());
            i++;
        }
        net_payable = (sum_taxable - sum_discount_amount) + tax_total;
        discount_amount_tv.setText(String.valueOf(df2.format(sum_discount_amount)));
        cgst_tv.setText(String.valueOf(df2.format(tax_total / 2)));
        sgst_tv.setText(String.valueOf(df2.format(tax_total / 2)));
        total_amt_tv.setText(String.valueOf(df2.format(sum_taxable - sum_discount_amount)));
        net_payable_tv.setText(String.valueOf(df2.format(net_payable)));
        taxable_amount_tv.setText(String.valueOf(df2.format(sum_taxable)));
    }
}