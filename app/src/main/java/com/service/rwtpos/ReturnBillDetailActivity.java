package com.service.rwtpos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.service.adapter.BillDetailAdapter;
import com.service.bottom_sheet.PreviewInvoiceSheet;
import com.service.model.ProductDetailModel;
import com.service.model.Products;
import com.service.network.ApiHelper;
import com.service.network.RetrofitClient;
import com.service.response_model.ReturnBillDetailResponse;
import com.service.response_model.ViewOutletBillModel;
import com.service.util.PrefsHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReturnBillDetailActivity extends AppCompatActivity implements PreviewInvoiceSheet.PreviewInvoiceSheetListener {

    String retrurn_invoice_id;
    private ApiHelper apiHelper;
    Context context;
    // public ArrayList<Products> products_list = new ArrayList();
    ProgressBar progressbar;
    RecyclerView bill_detail_recyclerview;
    TextView bill_no_tv, bill_date_tv, customer_name_tv, customer_mobile_tv, total_items_tv;
    ImageView back_arrow, download_invoice_imageview;
    ArrayList<ProductDetailModel> list = new ArrayList<>();
    private BillDetailAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    LinearLayout total_linear;
    TextView net_payable_tv, round_off_tv, total_amt_tv, sgst_tv, cgst_tv, taxable_amount_tv, discount_amount_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_bill_detail);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        init();
    }

    void init() {
        context = this;
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        progressbar = findViewById(R.id.progressbar);
        bill_detail_recyclerview = findViewById(R.id.bill_detail_recyclerview);
        bill_no_tv = findViewById(R.id.bill_no_tv);
        bill_date_tv = findViewById(R.id.bill_date_tv);
        customer_name_tv = findViewById(R.id.customer_name_tv);
        customer_mobile_tv = findViewById(R.id.customer_mobile_tv);
        back_arrow = findViewById(R.id.back_arrow);
        net_payable_tv = findViewById(R.id.net_payable_tv);
        round_off_tv = findViewById(R.id.round_off_tv);
        total_amt_tv = findViewById(R.id.total_amt_tv);
        sgst_tv = findViewById(R.id.sgst_tv);
        cgst_tv = findViewById(R.id.cgst_tv);
        discount_amount_tv = findViewById(R.id.discount_amount_tv);
        taxable_amount_tv = findViewById(R.id.taxable_amount_tv);
        total_items_tv = findViewById(R.id.total_items_tv);
        total_linear = findViewById(R.id.total_linear);
        download_invoice_imageview = findViewById(R.id.download_invoice_imageview);
        back_arrow.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ReturnBillDetailActivity.super.onBackPressed();
                    }
                }
        );
        Intent intent = getIntent();
        retrurn_invoice_id = intent.getStringExtra("retrurn_invoice_id");
        download_invoice_imageview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        downloadInvoiceByRetrofit(retrurn_invoice_id);
                    }
                }
        );
        GetBIllById(retrurn_invoice_id);
    }

    private void GetBIllById(String id) {
        progressbar.setVisibility(View.VISIBLE);
        Call<ReturnBillDetailResponse> loginCall = apiHelper.getReturnBillDetail(PrefsHelper.getString(context, "username"), PrefsHelper.getString(context, "password"), id);
        loginCall.enqueue(new Callback<ReturnBillDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<ReturnBillDetailResponse> call,
                                   @NonNull Response<ReturnBillDetailResponse> response) {
                progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {

                    try {
                        if (response != null) {
                            ReturnBillDetailResponse m = response.body();
                            if (m.getStatus().equalsIgnoreCase("success")) {
                                total_linear.setVisibility(View.VISIBLE);
                                ProductDetailModel model;
                                bill_no_tv.setText(m.getData().getReturn_no());
                                bill_date_tv.setText(m.getData().getReturn_date());
                                customer_name_tv.setText(m.getData().getCustomer_details().getName());
                                customer_mobile_tv.setText(m.getData().getCustomer_details().getMobile());
                                discount_amount_tv.setText(m.getData().getDiscount_amt());
                                taxable_amount_tv.setText(m.getData().getTaxable_amt());
                                cgst_tv.setText(m.getData().getCgst());
                                sgst_tv.setText(m.getData().getSgst());
                                net_payable_tv.setText(m.getData().getNet_payable());
                                total_amt_tv.setText(m.getData().getTotal());
                                round_off_tv.setText(m.getData().getRound_off());

                                for (int j = 0; j < m.getData().getProduct_data().size(); j++) {
                                    model = new ProductDetailModel();
                                    model.setProduct_name(m.getData().getProduct_data().get(j).getProduct());
                                    model.setSale_price(m.getData().getProduct_data().get(j).getBatch());
                                    model.setQuantity(m.getData().getProduct_data().get(j).getSingle_Return_Quantity());
                                    model.setTotal(String.valueOf(m.getData().getProduct_data().get(j).getSingle_Return_Final_Price()));
                                    list.add(model);
                                }
                                Log.e("list_size", list.size() + "");
                                bill_detail_recyclerview.setHasFixedSize(true);
                                mLayoutManager = new LinearLayoutManager(context);
                                mAdapter = new BillDetailAdapter(context, list);
                                bill_detail_recyclerview.setLayoutManager(mLayoutManager);
                                bill_detail_recyclerview.setAdapter(mAdapter);
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
            public void onFailure(@NonNull Call<ReturnBillDetailResponse> call,
                                  @NonNull Throwable t) {
                progressbar.setVisibility(View.GONE);
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }

    public void downloadInvoiceByRetrofit(String bill_id) {
        try {
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

            Call<ResponseBody> loginCall = apiHelper.downloadInvoice(PrefsHelper.getString(context, "username"), PrefsHelper.getString(context, "password"), "2");
            loginCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call,
                                       @NonNull Response<ResponseBody> response) {
                    progressbar.setVisibility(View.GONE);
                    if (response.isSuccessful()) {
                        ResponseBody body = response.body();
                        try {
                            mProgressDialog.hide();
                            downloadImage(body, bill_id);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        mProgressDialog.hide();
//                    progressbar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call,
                                      @NonNull Throwable t) {
                    mProgressDialog.hide();
                    progressbar.setVisibility(View.GONE);
                    if (!call.isCanceled()) {
                    }
                    t.printStackTrace();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void downloadImage(ResponseBody body, String bill_id) throws IOException {
        try {
            if (body != null) {
                String state = "";
                state = Environment.getExternalStorageState();
                if (Environment.MEDIA_MOUNTED.equals(state)) {
                    File direct = new File(Environment.getExternalStorageDirectory()
                            + "/RwtBills");
                    if (!direct.exists()) {
                        direct.mkdirs();
                    }
                    File myFile = new File(direct, bill_id + ".pdf");
                    FileOutputStream fstream = new FileOutputStream(myFile);
                    fstream.write(body.bytes());
                    fstream.close();
                    PreviewInvoiceSheet preview_sheet = new PreviewInvoiceSheet();
                    preview_sheet.show(getSupportFragmentManager(), "exampleBottomSheet");
//                Toast.makeText(context, "Invoice Saved", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "External Storage Not Found", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private File pdfFile;

    @Override
    public void onInvoicePreview(String text) {
        try {
            if (text.equals("whatsapp")) {
                File docsFolder = new File(Environment.getExternalStorageDirectory() + "/RwtBills");
                String pdfname = retrurn_invoice_id + ".pdf";
                pdfFile = new File(docsFolder.getAbsolutePath(), pdfname);

                Uri uri = Uri.fromFile(pdfFile);
                Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.setType("application/pdf");
                share.putExtra(Intent.EXTRA_STREAM, uri);
                share.setPackage("com.whatsapp");
                startActivity(share);
            } else if (text.equals("open")) {
                previewPdf();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void previewPdf() {
        try {
            File docsFolder = new File(Environment.getExternalStorageDirectory() + "/RwtBills");

            String pdfname = retrurn_invoice_id + ".pdf";
            pdfFile = new File(docsFolder.getAbsolutePath(), pdfname);

            Uri uri = Uri.fromFile(pdfFile);
            PackageManager packageManager = context.getPackageManager();
            Intent testIntent = new Intent(Intent.ACTION_VIEW);
            testIntent.setType("application/pdf");
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/pdf");
            context.startActivity(intent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//      Toast.makeText(context, "Download a PDF Viewer to see the generated PDF", Toast.LENGTH_SHORT).show();
    }
}
