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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.service.adapter.OrderListAdapter;
import com.service.adapter.SaleReturnAdapter;
import com.service.model.ReturnProductListModel;
import com.service.network.ApiHelper;
import com.service.network.RetrofitClient;
import com.service.response_model.BillByBillNoModel;
import com.service.response_model.CreateBillModel;
import com.service.response_model.SaleReturnResponse;
import com.service.util.PrefsHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalesReturnActivity extends AppCompatActivity {

    Context context;
    RecyclerView sale_return_recyclerview;
    ImageView back_arrow;
    EditText invoice_number_et;
    Button search_btn, return_items_btn;
    private SaleReturnAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ApiHelper apiHelper;
    ArrayList<ReturnProductListModel> return_product_list = new ArrayList<>();
    ProgressBar progressbar;
    TextView bill_no_tv, bill_date_tv, customer_name_tv, customer_mobile_tv, discount_amount_tv, taxable_amount_tv, cgst_tv, sgst_tv, total_amt_tv, round_off_tv, net_payable_tv;
    RelativeLayout bill_no_relative, customer_name_relative;
    LinearLayout total_linear;
    private static DecimalFormat df2 = new DecimalFormat("#.##");

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
        discount_amount_tv = findViewById(R.id.discount_amount_tv);
        taxable_amount_tv = findViewById(R.id.taxable_amount_tv);
        cgst_tv = findViewById(R.id.cgst_tv);
        sgst_tv = findViewById(R.id.sgst_tv);
        total_amt_tv = findViewById(R.id.total_amt_tv);
        round_off_tv = findViewById(R.id.round_off_tv);
        net_payable_tv = findViewById(R.id.net_payable_tv);
        total_linear = findViewById(R.id.total_linear);
        return_items_btn = findViewById(R.id.return_items_btn);
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
        return_items_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SaveReturn();
                    }
                }
        );
    }

    String bill_id = "", customer_id = "";

    private void getReturnList(String bill_no) {
        return_product_list.clear();
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
                        BillByBillNoModel m = response.body();

                        if (m.getData() != null) {
                            bill_no_relative.setVisibility(View.VISIBLE);
                            customer_name_relative.setVisibility(View.VISIBLE);
                            total_linear.setVisibility(View.VISIBLE);
                            return_items_btn.setVisibility(View.VISIBLE);
                            bill_no_tv.setText(m.getData().getBill_no());
                            bill_id = m.getData().getBill_id();
                            bill_date_tv.setText(m.getData().getBill_date());
                            customer_id = m.getData().getCustomer_details().getId();
                            customer_name_tv.setText(m.getData().getCustomer_details().getName());
                            customer_mobile_tv.setText(m.getData().getCustomer_details().getMobile());
                            bill_date_tv.setText(m.getData().getBill_date());
//                            discount_amount_tv.setText(m.getData().getDiscount_amt());
//                            taxable_amount_tv.setText(m.getData().getTaxable_amt());
//                            cgst_tv.setText(m.getData().getCgst());
//                            sgst_tv.setText(m.getData().getSgst());
//                            total_amt_tv.setText(m.getData().getTotal());
//                            round_off_tv.setText(m.getData().getRound_off());
//                            net_payable_tv.setText(m.getData().getNet_payable());

                            for (int i = 0; i < m.getData().getProduct_data().size(); i++) {
                                model = new ReturnProductListModel();
                                model.setProduct_Id(m.getData().getProduct_data().get(i).getProduct_Id());
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
                                model.setSelected_return_qty(String.valueOf(0));
                                return_product_list.add(model);
                            }
                            sale_return_recyclerview.setHasFixedSize(true);
                            mLayoutManager = new LinearLayoutManager(context);
                            mAdapter = new SaleReturnAdapter(context, return_product_list);
                            sale_return_recyclerview.setLayoutManager(mLayoutManager);
                            sale_return_recyclerview.setAdapter(mAdapter);
                        } else {
                            Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
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

    NumberFormat formatter = new DecimalFormat("##.00");

    public void setTotal(float sum_discount_amount, float tax_total, float sum_taxable, float round_off, float net_payable) {
        discount_amount_tv.setText(String.valueOf(df2.format(sum_discount_amount)));
        cgst_tv.setText(String.valueOf(df2.format(tax_total / 2)));
        sgst_tv.setText(String.valueOf(df2.format(tax_total / 2)));
        total_amt_tv.setText(String.valueOf(df2.format(net_payable)));
        net_payable_tv.setText(String.valueOf(Math.round(net_payable)));
        taxable_amount_tv.setText(String.valueOf(df2.format(sum_taxable)));
        round_off_tv.setText(String.valueOf(df2.format(round_off)));
    }

    private void SaveReturn() {
        JSONArray array = new JSONArray();
        JSONObject obj = null;
        int return_quantity = 0;
        try {
            int i = 0;
            while (i < mAdapter.sale_return_list.size()) {
                if (Integer.parseInt(mAdapter.sale_return_list.get(i).getSelected_return_qty()) > 0) {
                    obj = new JSONObject();

                    return_quantity = return_quantity + Integer.parseInt(mAdapter.sale_return_list.get(i).getSelected_return_qty());
                    obj.put("Product_Id", mAdapter.sale_return_list.get(i).getProduct_Id());
                    obj.put("Batch", mAdapter.sale_return_list.get(i).getBatch());
                    obj.put("Quantity", mAdapter.sale_return_list.get(i).getQuantity());
                    obj.put("Sale_Price", mAdapter.sale_return_list.get(i).getSale_Price());
                    obj.put("Single_Tax_Amt", mAdapter.sale_return_list.get(i).getSingle_Tax_Amt());
                    obj.put("Single_CGST", String.valueOf(Float.parseFloat(mAdapter.sale_return_list.get(i).getSingle_CGST())));
                    obj.put("Single_SGST", String.valueOf(Float.parseFloat(mAdapter.sale_return_list.get(i).getSingle_SGST())));
                    obj.put("Single_Basic_Price", mAdapter.sale_return_list.get(i).getSingle_Basic_Price());
                    obj.put("Total_Tax_Amt", (Float.parseFloat(mAdapter.sale_return_list.get(i).getTotal_Tax_Amt())));
                    obj.put("Total_CGST", (Float.parseFloat(mAdapter.sale_return_list.get(i).getTotal_CGST())));
                    obj.put("Total_SGST", (Float.parseFloat(mAdapter.sale_return_list.get(i).getTotal_SGST())));
                    obj.put("Total_Basic_price", String.valueOf(Float.parseFloat(mAdapter.sale_return_list.get(i).getTotal_Basic_price())));
                    obj.put("Final_Price", String.valueOf(mAdapter.sale_return_list.get(i).getFinal_Price()));
                    obj.put("SingleTaxRatePer", mAdapter.sale_return_list.get(i).getSingleTaxRatePer());
                    obj.put("SingleDiscountAmt", Float.parseFloat(mAdapter.sale_return_list.get(i).getSingleDiscountAmt()));
                    obj.put("SingleDiscountPer", mAdapter.sale_return_list.get(i).getSingleDiscountPer());
//                   float return_tax = Float.parseFloat(mAdapter.sale_return_list.get(i).getSingle_Tax_Amt()) * Integer.parseInt(mAdapter.sale_return_list.get(i).getSelected_return_qty());
                    float sale_Price = Float.parseFloat(mAdapter.sale_return_list.get(i).getSale_Price());
                    int tax_percentage = Integer.parseInt(mAdapter.sale_return_list.get(i).getSingleTaxRatePer());
                    float basic_price = ((sale_Price) / (100 + tax_percentage)) * 100;
                    float total_basic = basic_price * Integer.parseInt(mAdapter.sale_return_list.get(i).getSelected_return_qty());
                    float discount_amount = ((total_basic * Integer.parseInt(mAdapter.sale_return_list.get(i).getSingleDiscountPer())) / 100);

                    float total_taxable_amount = total_basic - discount_amount;
                    float total_tax_amount = (total_taxable_amount * Integer.parseInt(mAdapter.sale_return_list.get(i).getSingleTaxRatePer())) / 100;

                    obj.put("Single_Return_Tax_Amt", df2.format(total_tax_amount));
                    obj.put("Single_Return_Total_CGST", df2.format(total_tax_amount / 2));
                    obj.put("Single_Return_Total_SGST", df2.format(total_tax_amount / 2));
                    obj.put("Single_Return_Total_Basic_price", df2.format(total_basic));
                    obj.put("Single_Return_Final_Price", df2.format(total_taxable_amount + total_tax_amount));
                    obj.put("Single_Return_Quantity", mAdapter.sale_return_list.get(i).getSelected_return_qty());
                    obj.put("Single_Return_Discount_Amt", df2.format(discount_amount));
                    array.put(obj);
                }
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

        Call<ResponseBody> loginCall = apiHelper.saleReturn(PrefsHelper.getString(context, "username"), PrefsHelper.getString(context, "password"), bill_id, customer_id, discount_amount_tv.getText().toString()
                , taxable_amount_tv.getText().toString(), cgst_tv.getText().toString(), sgst_tv.getText().toString(), total_amt_tv.getText().toString(), round_off_tv.getText().toString(), net_payable_tv.getText().toString()
                , String.valueOf(return_quantity), array.toString());
        loginCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call,
                                   @NonNull Response<ResponseBody> response) {
                progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    mProgressDialog.hide();
                    if (response != null) {
                        ResponseBody m = response.body();
                        try {
                            JSONObject obj = new JSONObject(response.body().string());
                            if (obj.getString("status").equals("success")) {
                                Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    progressbar.setVisibility(View.GONE);
                    mProgressDialog.hide();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call,
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
