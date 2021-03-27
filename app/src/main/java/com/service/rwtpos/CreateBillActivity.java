package com.service.rwtpos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.service.adapter.ProductListAdapter;
import com.service.bottom_sheet.AddCustomerSheet;
import com.service.bottom_sheet.AddProductByBarcodeSheet;
import com.service.bottom_sheet.DiscardChangesSheet;
import com.service.bottom_sheet.EditItemSheet;
import com.service.model.ProductListModel;
import com.service.model.Products;
import com.service.network.ApiHelper;
import com.service.network.RetrofitClient;
import com.service.response_model.AddCustomerResponse;
import com.service.response_model.CreateBillModel;
import com.service.response_model.GetCustomerModel;
import com.service.response_model.GetEditDataOutletBill;
import com.service.response_model.ProductByBarcode;
import com.service.util.PrefsHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateBillActivity extends AppCompatActivity implements AddProductByBarcodeSheet.AddProductByBarcodeSheetListener, EditItemSheet.EditItemSheetListener, AddCustomerSheet.AddCustomerSheetListener, DiscardChangesSheet.DiscardChangesListener {

    ProgressBar progressbar;
    TextView round_off_tv, invoice_date_tv, add_product_tv, total_items_tv, discount_amount_tv, taxable_amount_tv, cgst_tv, sgst_tv, total_amt_tv, net_payable_tv, customer_name_tv;
    Context context;
    SimpleDateFormat date_format = new SimpleDateFormat("dd-MMM-yyyy");
    private ApiHelper apiHelper;
    RecyclerView item_list_recyclerview;
    ArrayList<ProductListModel> product_list = new ArrayList<>();
    public ArrayList<Products> edit_tem_list = new ArrayList();
    private ProductListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ImageView back_image, add_customer_imageview, scan_barcode_imageview;
    Spinner payment_mode_spinner;
    TextInputLayout transaction_id_input_layout, wallet_name_layout, transaction_date_input_layout;
    ArrayList paymentMode_list = new ArrayList();
    Button add_items_btn;
    RelativeLayout dicount_relative, taxable_relative, cgst_relative, sgst_relative, total_relative, net_payable_relative, round_off_relative;
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    CardView save_cardview;
    EditText transaction_id_et, transaction_date_et, wallet_name_et, customer_mobile_et;
    LinearLayout customer_name_linear;
    public static CreateBillActivity cbb;

    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    String todate = null, fromdate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bill);
        cbb = this;
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
        save_cardview = findViewById(R.id.save_cardview);

        dicount_relative = findViewById(R.id.dicount_relative);
        taxable_relative = findViewById(R.id.taxable_relative);
        cgst_relative = findViewById(R.id.cgst_relative);
        sgst_relative = findViewById(R.id.sgst_relative);
        total_relative = findViewById(R.id.total_relative);
        net_payable_relative = findViewById(R.id.net_payable_relative);
        round_off_relative = findViewById(R.id.round_off_relative);
        discount_amount_tv = findViewById(R.id.discount_amount_tv);
        taxable_amount_tv = findViewById(R.id.taxable_amount_tv);
        cgst_tv = findViewById(R.id.cgst_tv);
        sgst_tv = findViewById(R.id.sgst_tv);
        total_amt_tv = findViewById(R.id.total_amt_tv);
        net_payable_tv = findViewById(R.id.net_payable_tv);
        scan_barcode_imageview = findViewById(R.id.scan_barcode_imageview);
        transaction_id_et = findViewById(R.id.transaction_id_et);
        transaction_date_et = findViewById(R.id.transaction_date_et);
        wallet_name_et = findViewById(R.id.wallet_name_et);
        customer_mobile_et = findViewById(R.id.customer_mobile_et);
        customer_name_tv = findViewById(R.id.customer_name_tv);
        round_off_tv = findViewById(R.id.round_off_tv);
        customer_name_linear = findViewById(R.id.customer_name_linear);

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
        customer_mobile_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                int length = s.toString().length();
                boolean b = (length == 10);
                if (s != null && b) {

                    getCustomerByMobile(s.toString());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });
        transaction_date_et.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datePicker(transaction_date_et);
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
        scan_barcode_imageview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CreateBillActivity.this, ScanBarcodeActivity.class);
                        startActivity(intent);
                    }
                }
        );
        save_cardview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (check()) {
                            SaveBill();
                        }
                    }
                }
        );
        Intent intent = getIntent();
        String bill_type = intent.getStringExtra("bill_type");
        if (bill_type.equals("edit")) {
            customer_mobile_et.setText(intent.getStringExtra("customer_mobile"));
            customer_name_tv.setText(intent.getStringExtra("customer_name"));
            invoice_date_tv.setText(intent.getStringExtra("date"));
            edit_tem_list = (ArrayList<Products>) intent.getSerializableExtra("productlist");
            GetBIllById(intent.getStringExtra("bill_id"));

            net_payable_tv.setText(intent.getStringExtra("net_payable"));
            total_amt_tv.setText(intent.getStringExtra("total"));
            round_off_tv.setText(intent.getStringExtra("round_off"));
            sgst_tv.setText(intent.getStringExtra("sgst"));
            cgst_tv.setText(intent.getStringExtra("cgst"));
            taxable_amount_tv.setText(intent.getStringExtra("taxable_amt"));
            discount_amount_tv.setText(intent.getStringExtra("discount_amt"));
        }
    }

    @Override
    public void onBarcodeAdded(String barcode) {
        if (checkBarcodeAlready(barcode)) {
            getProductBybarcode(barcode);
        } else {
            Toast.makeText(CreateBillActivity.this, "Barcode Already Added", Toast.LENGTH_SHORT).show();
        }
    }

    public void getProductBybarcode(String barcode) {
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
            float single_tax = (sale_Price - basic_price);
            float total_basic = basic_price * Integer.parseInt(qty);
            float discount_amount = ((total_basic * Integer.parseInt(discount)) / 100);
            float single_discount_amount = ((basic_price * Integer.parseInt(discount)) / 100);

            float total_taxable_amount = total_basic - discount_amount;
            float total_tax_amount = (total_taxable_amount * Integer.parseInt(product_list.get(index).getTax_percent())) / 100;
            float cgst = total_tax_amount / 2;
            float sgst = total_tax_amount / 2;

            product_list.get(index).setSingle_tax(String.valueOf(single_tax));
            product_list.get(index).setSelected_qty(qty);
            product_list.get(index).setSale_price(salePrice);
            product_list.get(index).setDiscount_percentage(discount);
            product_list.get(index).setSelected_batch(selectedbatch);
            product_list.get(index).setBasic_price(String.valueOf(basic_price));
            product_list.get(index).setTotal_tax(String.valueOf(total_tax_amount));
            product_list.get(index).setCgst(String.valueOf(cgst));
            product_list.get(index).setSgst(String.valueOf(sgst));
            product_list.get(index).setTotal_discount_amount(String.valueOf(discount_amount));
            product_list.get(index).setSingle_discount_amount(String.valueOf(single_discount_amount));
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

    String customer_id;

    private void getCustomerByMobile(String mobile) {
//      progressbar.setVisibility(View.VISIBLE);
//        final ProgressDialog mProgressDialog = new ProgressDialog(this);
//        mProgressDialog.setIndeterminate(true);
//        mProgressDialog.setMessage("Please Wait...");
//        mProgressDialog.setCancelable(false);
//        mProgressDialog.setCanceledOnTouchOutside(false);
//        mProgressDialog.setOnCancelListener(new Dialog.OnCancelListener() {
//
//            @Override
//            public void onCancel(DialogInterface dialog) {
//                // DO SOME STUFF HERE
//            }
//        });
//        mProgressDialog.show();
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        Call<GetCustomerModel> loginCall = apiHelper.getCustomerByMobile(PrefsHelper.getString(context, "username"), PrefsHelper.getString(context, "password"), mobile);
        loginCall.enqueue(new Callback<GetCustomerModel>() {
            @Override
            public void onResponse(@NonNull Call<GetCustomerModel> call,
                                   @NonNull Response<GetCustomerModel> response) {
                progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
//                    mProgressDialog.hide();
                    if (response != null) {
                        customer_name_linear.setVisibility(View.VISIBLE);
                        GetCustomerModel m = response.body();
                        if (m.getStatus().equalsIgnoreCase("success")) {
                            String customername = m.getData().getName();
                            customer_name_tv.setText(customername);
                            customer_id = m.getData().getId();
//                            Toast.makeText(CreateBillActivity.this, m.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CreateBillActivity.this, m.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    progressbar.setVisibility(View.GONE);
//                    mProgressDialog.hide();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetCustomerModel> call,
                                  @NonNull Throwable t) {
                progressbar.setVisibility(View.GONE);
//                mProgressDialog.hide();
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }

    private void SaveBill() {
        JSONArray array = new JSONArray();
        JSONObject obj = null;
        float total_cgst = 0;
        float total_sgst = 0;
        try {
            int i = 0;
            while (i < product_list.size()) {
                obj = new JSONObject();
                obj.put("Product_Id", product_list.get(i).getId());
                obj.put("Batch", product_list.get(i).getSelected_batch());
                obj.put("Quantity", product_list.get(i).getSelected_qty());
                obj.put("Sale_Price", product_list.get(i).getSelected_batch());
                if (product_list.get(i).getSingle_tax() != null) {
                    obj.put("Single_Tax_Amt", product_list.get(i).getSingle_tax());
                } else {
                    obj.put("Single_Tax_Amt", String.valueOf(0));
                }
                if (product_list.get(i).getSingle_tax() != null) {
                    obj.put("Single_CGST", String.valueOf(Float.parseFloat(product_list.get(i).getSingle_tax()) / 2));
                } else {
                    obj.put("Single_CGST", String.valueOf(0));
                }
                if (product_list.get(i).getSingle_tax() != null) {
                    obj.put("Single_SGST", String.valueOf(Float.parseFloat(product_list.get(i).getSingle_tax()) / 2));
                } else {
                    obj.put("Single_SGST", String.valueOf(0));
                }
                if (product_list.get(i).getBasic_price() != null) {
                    obj.put("Single_Basic_Price", product_list.get(i).getBasic_price());
                } else {
                    obj.put("Single_Basic_Price", String.valueOf(0));
                }
                if (product_list.get(i).getTotal_tax() != null) {
                    obj.put("Total_Tax_Amt", product_list.get(i).getTotal_tax());
                } else {
                    obj.put("Total_Tax_Amt", String.valueOf(0));
                }
                if (product_list.get(i).getCgst() != null) {
                    obj.put("Total_CGST", df2.format(Float.parseFloat(product_list.get(i).getCgst())));
                    total_cgst = total_cgst + Float.parseFloat(product_list.get(i).getCgst());
                } else {
                    obj.put("Total_CGST", String.valueOf(0));
                }
                if (product_list.get(i).getSgst() != null) {
                    obj.put("Total_SGST", df2.format(Float.parseFloat(product_list.get(i).getSgst())));
                    total_sgst = total_sgst + Float.parseFloat(product_list.get(i).getSgst());
                } else {
                    obj.put("Total_SGST", String.valueOf(0));
                }
                if (product_list.get(i).getBasic_price() != null && product_list.get(i).getSelected_qty() != null) {
                    float total_basic_price = Float.parseFloat(product_list.get(i).getBasic_price()) * Integer.parseInt(product_list.get(i).getSelected_qty()) - Float.parseFloat(product_list.get(i).getTotal_discount_amount());
                    obj.put("Total_Basic_price", String.valueOf(df2.format(total_basic_price)));
                } else {
                    obj.put("Total_Basic_price", String.valueOf(0));
                }
                if (net_payable_tv.getText().toString() != null) {
                    float final_price = (Integer.parseInt(product_list.get(i).getSelected_qty()) * Float.parseFloat(product_list.get(i).getBasic_price()) + Float.parseFloat(product_list.get(i).getTotal_tax())) - Float.parseFloat(product_list.get(i).getTotal_discount_amount());
                    obj.put("Final_Price", String.valueOf(final_price));
                } else {
                    obj.put("Final_Price", String.valueOf(0));
                }
                if (product_list.get(i).getTax_percent() != null) {
                    obj.put("SingleTaxRatePer", product_list.get(i).getTax_percent());
                } else {
                    obj.put("SingleTaxRatePer", String.valueOf(0));
                }
                if (product_list.get(i).getSingle_discount_amount() != null) {
                    obj.put("SingleDiscountAmt", df2.format(Float.parseFloat(product_list.get(i).getSingle_discount_amount())));
                } else {
                    obj.put("SingleDiscountAmt", String.valueOf(0));
                }
                if (product_list.get(i).getDiscount_percentage() != null) {
                    obj.put("SingleDiscountPer", product_list.get(i).getDiscount_percentage());
                } else {
                    obj.put("SingleDiscountPer", String.valueOf(0));
                }
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
//        Log.e("request_data", PrefsHelper.getString(context, "username") + "," + PrefsHelper.getString(context, "password") + "," + customer_mobile_et.getText().toString() + "," + customer_name_tv.getText().toString() + "," + customer_id + "," + String.valueOf(payment_mode_spinner.getSelectedItemPosition() + 1) + "," + invoice_date_tv.getText().toString() +
//                ", " + array.toString() + "," + discount_amount_tv.getText().toString() + "," + taxable_amount_tv.getText().toString());
        Call<CreateBillModel> loginCall = apiHelper.CreateBill(PrefsHelper.getString(context, "username"), PrefsHelper.getString(context, "password"), customer_mobile_et.getText().toString(), customer_name_tv.getText().toString(), customer_id, String.valueOf(payment_mode_spinner.getSelectedItemPosition()), invoice_date_tv.getText().toString(), array.toString()
                , discount_amount_tv.getText().toString(), taxable_amount_tv.getText().toString(), round_off_tv.getText().toString(), net_payable_tv.getText().toString(), String.valueOf(total_cgst), String.valueOf(total_sgst)
                , net_payable_tv.getText().toString()
        );
        loginCall.enqueue(new Callback<CreateBillModel>() {
            @Override
            public void onResponse(@NonNull Call<CreateBillModel> call,
                                   @NonNull Response<CreateBillModel> response) {
                progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    mProgressDialog.hide();
                    if (response != null) {
                        CreateBillModel m = response.body();
                        if (m.getStatus().equalsIgnoreCase("success")) {
                            Toast.makeText(CreateBillActivity.this, m.getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
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
            public void onFailure(@NonNull Call<CreateBillModel> call,
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
        dicount_relative.setVisibility(View.GONE);
        taxable_relative.setVisibility(View.GONE);
        sgst_relative.setVisibility(View.GONE);
        cgst_relative.setVisibility(View.GONE);
        net_payable_relative.setVisibility(View.GONE);
        round_off_relative.setVisibility(View.GONE);
        total_relative.setVisibility(View.GONE);
        total_items_tv.setText("ITEMS ( " + product_list.size() + " )");
        calculate_Total();
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

    NumberFormat formatter = new DecimalFormat("##.00");

    void calculate_Total() {
        dicount_relative.setVisibility(View.VISIBLE);
        cgst_relative.setVisibility(View.VISIBLE);
        sgst_relative.setVisibility(View.VISIBLE);
        net_payable_relative.setVisibility(View.VISIBLE);
        round_off_relative.setVisibility(View.VISIBLE);
        total_relative.setVisibility(View.VISIBLE);
        taxable_relative.setVisibility(View.VISIBLE);

        float sum_discount_amount = 0;
        float sum_taxable = 0;
        float tax_total = 0;
        float net_payable = 0;
        int i = 0;
        while (i < product_list.size()) {
            sum_discount_amount = sum_discount_amount + Float.parseFloat(product_list.get(i).getTotal_discount_amount());
            sum_taxable = sum_taxable + ((Float.parseFloat(product_list.get(i).getBasic_price())) * Integer.parseInt(product_list.get(i).getSelected_qty()));
            tax_total = tax_total + Float.parseFloat(product_list.get(i).getTotal_tax());
            i++;
        }
        sum_taxable = sum_taxable - sum_discount_amount;
        net_payable = (sum_taxable) + tax_total;
        float round_off = Float.parseFloat(String.valueOf(Math.round(net_payable))) - Float.parseFloat(formatter.format(net_payable));
        discount_amount_tv.setText(String.valueOf(df2.format(sum_discount_amount)));
        cgst_tv.setText(String.valueOf(df2.format(tax_total / 2)));
        sgst_tv.setText(String.valueOf(df2.format(tax_total / 2)));
        total_amt_tv.setText(String.valueOf(formatter.format(net_payable)));
        net_payable_tv.setText(String.valueOf(Math.round(net_payable)));
        taxable_amount_tv.setText(String.valueOf(df2.format(sum_taxable)));
        round_off_tv.setText(String.valueOf(round_off));
    }

    boolean check() {
        boolean b = true;
        if (payment_mode_spinner.getSelectedItemPosition() == 0) {
            b = false;
            return b;
        } else if (payment_mode_spinner.getSelectedItemPosition() == 2) {
            if (transaction_id_et.getText().toString().isEmpty()) {
                b = false;
                Toast.makeText(context, "Enter Transaction Id", Toast.LENGTH_SHORT).show();
                transaction_id_et.requestFocus();
                return b;
            } else if (transaction_date_et.getText().toString().isEmpty()) {
                b = false;
                Toast.makeText(context, "Select Transaction Date", Toast.LENGTH_SHORT).show();
                transaction_date_et.requestFocus();
                return b;
            } else if (wallet_name_et.getText().toString().isEmpty()) {
                b = false;
                Toast.makeText(context, "Enter Wallet Name", Toast.LENGTH_SHORT).show();
                wallet_name_et.requestFocus();
                return b;
            }
        } else if (payment_mode_spinner.getSelectedItemPosition() == 3 | payment_mode_spinner.getSelectedItemPosition() == 4 | payment_mode_spinner.getSelectedItemPosition() == 5) {
            if (transaction_id_et.getText().toString().isEmpty()) {
                b = false;
                Toast.makeText(context, "Enter Transaction Id", Toast.LENGTH_SHORT).show();
                transaction_id_et.requestFocus();
                return b;
            } else if (transaction_date_et.getText().toString().isEmpty()) {
                b = false;
                Toast.makeText(context, "Select Transaction Date", Toast.LENGTH_SHORT).show();
                transaction_id_et.requestFocus();
                return b;
            }
        } else if (customer_mobile_et.getText().toString().isEmpty()) {
            b = false;
            Toast.makeText(context, "Enter Customer Mobile", Toast.LENGTH_SHORT).show();
            customer_mobile_et.requestFocus();
            return b;
        } else if (customer_name_tv.getText().toString().isEmpty()) {
            b = false;
            Toast.makeText(context, "Enter Registered Mobile", Toast.LENGTH_SHORT).show();
            return b;
        }
        return b;
    }

    public void datePicker(final EditText date) {
        calendar = Calendar.getInstance();
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String value = convertMonth(month);
                        if (value != (null)) {
                            String edit = String.valueOf(date);

                            String[] separated = edit.split("/");
                            String variable = separated[1];

                            if (variable.equals("edt_to_date}")) {
                                todate = (year + "-" + month + "-" + day);
                            } else {
                                fromdate = (year + "-" + month + "-" + day);
                            }
                            //date.setText(day + "-" + month + 1  + "-" + year);
                            transaction_date_et.setText(day + "-" + value + "-" + year);

                            if ((fromdate != null) && (todate != null)) {
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                                Date date1 = null;
                                Date date2 = null;
                                try {
                                    date1 = format.parse(fromdate);
                                    date2 = format.parse(todate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }, year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    public String convertMonth(int month) {
        String value = null;
        if (month == 1) {
            value = "Jan";
        } else if (month == 2) {
            value = "Feb";
        } else if (month == 3) {
            value = "Mar";
        } else if (month == 4) {
            value = "Apr";
        } else if (month == 5) {
            value = "May";
        } else if (month == 6) {
            value = "Jun";
        } else if (month == 7) {
            value = "Jul";
        } else if (month == 8) {
            value = "Aug";
        } else if (month == 9) {
            value = "Sep";
        } else if (month == 10) {
            value = "Oct";
        } else if (month == 11) {
            value = "Nov";
        } else if (month == 12) {
            value = "Dec";
        }
        return value;
    }

    private void GetBIllById(String id) {
        Call<GetEditDataOutletBill> loginCall = apiHelper.geteditdataoutletbill(PrefsHelper.getString(context, "username"), PrefsHelper.getString(context, "password"), id);
        loginCall.enqueue(new Callback<GetEditDataOutletBill>() {
            @Override
            public void onResponse(@NonNull Call<GetEditDataOutletBill> call,
                                   @NonNull Response<GetEditDataOutletBill> response) {
                progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {

                    if (response != null) {
                        GetEditDataOutletBill m = response.body();
                        if (m.getStatus().equalsIgnoreCase("success")) {
                            ArrayList<ProductByBarcode.Inventory> inventory_list;

                            for (int i = 0; i < m.getData().size(); i++) {
                                inventory_list = new ArrayList<>();
                                ProductByBarcode.Inventory inventory_model;
                                edit_tem_list.get(i).setProduct_Name(m.getData().get(i).getProduct());
                                edit_tem_list.get(i).setBarcode(m.getData().get(i).getBarcode());
                                for (int j = 0; j < m.getData().get(i).getInventory().size(); j++) {
                                    inventory_model = new ProductByBarcode.Inventory();
                                    inventory_model.setOutlet_id(m.getData().get(i).getInventory().get(j).getOutlet_id());
                                    inventory_model.setPrice(m.getData().get(i).getInventory().get(j).getPrice());
                                    inventory_model.setQty(m.getData().get(i).getInventory().get(j).getQty());
                                    inventory_list.add(inventory_model);
                                }
                                edit_tem_list.get(i).setInventory(inventory_list);
                            }
                            setEditList();
                        } else {
                            Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    progressbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetEditDataOutletBill> call,
                                  @NonNull Throwable t) {
                progressbar.setVisibility(View.GONE);
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }

    void setEditList() {
        ProductListModel model;
        int i = 0;
        while (i < edit_tem_list.size()) {
            model = new ProductListModel();
            Products d = edit_tem_list.get(i);
            model.setBarcode(d.getBarcode());
            model.setCgst(d.getSingle_CGST());
            model.setSgst(d.getSingle_SGST());
            model.setHsn("");
            model.setId(d.getProduct_Id());
            model.setGst(d.getSingle_Tax_Amt());
            model.setIntStock(String.valueOf(0));
            model.setMinmum_stock(String.valueOf(0));
            model.setPro_print_name(d.getProduct_Name());
            model.setSale_price(d.getSale_Price());
//          model.setPurchase_price(d.getP);
            model.setTax_percent(d.getSingleTaxRatePer());
            model.setTotal_tax(d.getTotal_Tax_Amt());
//            model.setProduct_pic(m.getData().getProduct_details().getProduct_pic());
            model.setStr_product(d.getProduct_Name());
            model.setBasic_price(d.getSingle_Basic_Price());
            model.setTotal_discount_amount(d.getSingleDiscountAmt());
            model.setSelected_batch(d.getBatch());
            model.setSelected_qty(d.getQuantity());
            model.setSingle_discount_amount(d.getSingleDiscountAmt());
            model.setSingle_tax(d.getSingle_Tax_Amt());
            model.setDiscount_percentage(d.getSingleDiscountPer());
            product_list.add(model);
            i++;
        }
        item_list_recyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        item_list_recyclerview.setLayoutManager(mLayoutManager);
        item_list_recyclerview.setAdapter(mAdapter);

        dicount_relative.setVisibility(View.VISIBLE);
        cgst_relative.setVisibility(View.VISIBLE);
        sgst_relative.setVisibility(View.VISIBLE);
        net_payable_relative.setVisibility(View.VISIBLE);
        round_off_relative.setVisibility(View.VISIBLE);
        total_relative.setVisibility(View.VISIBLE);
        taxable_relative.setVisibility(View.VISIBLE);
    }
}
