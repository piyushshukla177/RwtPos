package com.service.rwtpos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.service.adapter.BillDetailAdapter;
import com.service.background_services.BackgroundNotificationService;
import com.service.bottom_sheet.PreviewInvoiceSheet;
import com.service.bottom_sheet.PreviewInvoiceSheet.PreviewInvoiceSheetListener;
import com.service.model.ProductDetailModel;
import com.service.model.Products;
import com.service.network.ApiHelper;
import com.service.network.RetrofitClient;
import com.service.response_model.ViewOutletBillModel;
import com.service.util.PrefsHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillDetailsAcitvity extends AppCompatActivity implements PreviewInvoiceSheetListener {

    Context context;
    public ArrayList<Products> products_list = new ArrayList();
    ProgressBar progressbar;
    RecyclerView bill_detail_recyclerview;
    TextView bill_no_tv, bill_date_tv, customer_name_tv, customer_mobile_tv, total_items_tv;
    ImageView back_arrow, download_invoice_imageview;
    ArrayList<ProductDetailModel> list = new ArrayList<>();
    private BillDetailAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    LinearLayout total_linear;
    TextView net_payable_tv, round_off_tv, total_amt_tv, sgst_tv, cgst_tv, taxable_amount_tv, discount_amount_tv;
    private ApiHelper apiHelper;
    public static final String PROGRESS_UPDATE = "progress_update";
    private static final int PERMISSION_REQUEST_CODE = 1;
    String bill_id = "";

    private File pdfFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        init();
    }

    void init() {
        context = this;
//      this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
                        BillDetailsAcitvity.super.onBackPressed();
                    }
                }
        );
        Intent intent = getIntent();
        products_list = (ArrayList<Products>) intent.getSerializableExtra("productlist");
        bill_no_tv.setText(intent.getStringExtra("bill_no"));
        bill_date_tv.setText(intent.getStringExtra("bill_date"));
        customer_name_tv.setText(intent.getStringExtra("customer_name"));
        customer_mobile_tv.setText(intent.getStringExtra("customer_mobile"));
        net_payable_tv.setText(intent.getStringExtra("net_payable"));
        round_off_tv.setText(intent.getStringExtra("round_off"));
        total_amt_tv.setText(intent.getStringExtra("total"));
        sgst_tv.setText(intent.getStringExtra("sgst"));
        cgst_tv.setText(intent.getStringExtra("cgst"));
        taxable_amount_tv.setText(intent.getStringExtra("taxable"));
        discount_amount_tv.setText(intent.getStringExtra("discount"));
        GetBIllById(intent.getStringExtra("id"));
        bill_id = intent.getStringExtra("id");
        download_invoice_imageview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                      startImageDownload(bill_id);
                        downloadInvoiceByRetrofit(bill_id);
                    }
                }
        );
        registerReceiver();
        try {
            requestPermissionForReadExtertalStorage();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void setData() {
        int i = 0;
        total_linear.setVisibility(View.VISIBLE);
        total_items_tv.setText("ITEMS(" + products_list.size() + ")");
        ProductDetailModel m;
        while (i < products_list.size()) {
            m = new ProductDetailModel();
            Products d = products_list.get(i);
            m.setProduct_name(d.getProduct_Name());
            m.setSale_price(d.getSale_Price());
            m.setQuantity(d.getQuantity());
            float total = Integer.parseInt(d.getQuantity()) * Float.parseFloat(d.getSale_Price());
            m.setTotal(String.valueOf(total));
            list.add(m);
            i++;
        }
        bill_detail_recyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        mAdapter = new BillDetailAdapter(context, list);
        bill_detail_recyclerview.setLayoutManager(mLayoutManager);
        bill_detail_recyclerview.setAdapter(mAdapter);
    }

    private void GetBIllById(String id) {
        progressbar.setVisibility(View.VISIBLE);
        Call<ViewOutletBillModel> loginCall = apiHelper.ViewOutletBill(PrefsHelper.getString(context, "username"), PrefsHelper.getString(context, "password"), id);
        loginCall.enqueue(new Callback<ViewOutletBillModel>() {
            @Override
            public void onResponse(@NonNull Call<ViewOutletBillModel> call,
                                   @NonNull Response<ViewOutletBillModel> response) {
                progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {

                    if (response != null) {
                        ViewOutletBillModel m = response.body();
                        if (m.getStatus().equalsIgnoreCase("success")) {
                            Log.e("id", "list size " + products_list.size() + " id =" + id + " data szie " + m.getData().size());
                            for (int j = 0; j < m.getData().size(); j++) {
                                products_list.get(j).setProduct_Name(m.getData().get(j).getProduct());
                            }
                            setData();
                        } else {
                            Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    progressbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ViewOutletBillModel> call,
                                  @NonNull Throwable t) {
                progressbar.setVisibility(View.GONE);
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }

    private void registerReceiver() {
        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PROGRESS_UPDATE);
        bManager.registerReceiver(mBroadcastReceiver, intentFilter);
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(PROGRESS_UPDATE)) {

                boolean downloadComplete = intent.getBooleanExtra("downloadComplete", false);
                //Log.d("API123", download.getProgress() + " current progress");

                if (downloadComplete) {

//                    Toast.makeText(getApplicationContext(), "File download completeded", Toast.LENGTH_SHORT).show();
                    PreviewInvoiceSheet preview_sheet = new PreviewInvoiceSheet();
                    preview_sheet.show(getSupportFragmentManager(), "exampleBottomSheet");
//                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + File.separator +
//                            "journaldev-image-downloaded.jpg");
//                    Picasso.get().load(file).into(imageView);
                }
            }
        }
    };

    private void startImageDownload(String bill_id) {
        Intent intent = new Intent(this, BackgroundNotificationService.class);
        intent.putExtra("bill_id", bill_id);
        startService(intent);
    }

   /* @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    startImageDownload(bill_id);
                } else {

                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }*/

    @Override
    public void onInvoicePreview(String text) {
        if (text.equals("whatsapp")) {
            File docsFolder = new File(Environment.getExternalStorageDirectory() + "/RwtBills");

            String pdfname = bill_id + ".pdf";
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
    }


    private void previewPdf() {
        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/RwtBills");

        String pdfname = bill_id + ".pdf";
        pdfFile = new File(docsFolder.getAbsolutePath(), pdfname);

        Uri uri = Uri.fromFile(pdfFile);
        PackageManager packageManager = context.getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        context.startActivity(intent);
//        Toast.makeText(context, "Download a PDF Viewer to see the generated PDF", Toast.LENGTH_SHORT).show();
    }

    public void requestPermissionForReadExtertalStorage() throws Exception {
        try {
            ActivityCompat.requestPermissions((Activity) this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    10);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void downloadInvoiceByRetrofit(String bill_id) {
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
    }

    private void downloadImage(ResponseBody body, String bill_id) throws IOException {
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
    }
}
