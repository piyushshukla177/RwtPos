package com.service.rwtpos;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.service.adapter.OrderListAdapter;
import com.service.model.OrderListModel;
import com.service.model.Products;
import com.service.network.ApiHelper;
import com.service.network.RetrofitClient;
import com.service.response_model.BillListModel;
import com.service.util.PrefsHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.O)
public class OrderListActivity extends AppCompatActivity {
    SwipeRefreshLayout swipeContainer;

    Context context;
    RecyclerView order_list_recyclerview;
    private OrderListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ProgressBar progressbar;
    private ApiHelper apiHelper;
    ArrayList<OrderListModel> order_list = new ArrayList<>();
    TextView sale_tv;
    ImageView back_arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        init();
    }

    String sale_type;

    void init() {
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        context = this;
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        progressbar = findViewById(R.id.progressbar);
        order_list_recyclerview = findViewById(R.id.order_list_recyclerview);
        sale_tv = findViewById(R.id.sale_tv);
        back_arrow = findViewById(R.id.back_arrow);
        swipeContainer = findViewById(R.id.swipe_refresh_layout);

        Intent intent = getIntent();
        sale_type = intent.getStringExtra("sale");
        if (sale_type.equals("today")) {
            sale_tv.setText("Today Sale");
        } else if (sale_type.equals("monthly")) {
            sale_tv.setText("Monthly Sale");
        } else {
            sale_tv.setText("Sale");
        }
        back_arrow.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OrderListActivity.super.onBackPressed();
                    }
                }
        );

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getOrderList();
            }
        });

        getOrderList();
    }

    //    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

  /*  private void getOrderList() {
        order_list.clear();
        swipeContainer.setRefreshing(false);
        progressbar.setVisibility(View.VISIBLE);
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        Call<BillListModel> loginCall = apiHelper.getBillingList(PrefsHelper.getString(context, "username"), PrefsHelper.getString(context, "password"));
        loginCall.enqueue(new Callback<BillListModel>() {
            @Override
            public void onResponse(@NonNull Call<BillListModel> call,
                                   @NonNull Response<BillListModel> response) {
                progressbar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    if (response != null) {
                        BillListModel m = response.body();
                        if (m.getStatus().equalsIgnoreCase("success")) {
                            OrderListModel model;
                            for (int i = 0; i < m.getData().size(); i++) {
                                model = new OrderListModel();
                                model.setBill_no(m.getData().get(i).getBill_no());
                                model.setBill_date(m.getData().get(i).getBill_date());
                                model.setCustomer_name(m.getData().get(i).getCustomer_name());
                                model.setCustomer_mobile(m.getData().get(i).getCustomer_mobile());
                                model.setCgst(m.getData().get(i).getCgst());
                                model.setSgst(m.getData().get(i).getSgst());
                                model.setNet_payable(m.getData().get(i).getNet_payable());
                                model.setTotal(m.getData().get(i).getTotal());
                                model.setPayment_type(m.getData().get(i).getPayment_type());
                                model.setCustomer_id(m.getData().get(i).getCustomer_id());
                                model.setOutlet_id(m.getData().get(i).getOutlet_id());
                                if (sale_type.equals("today")) {
                                    //filter daily data
//                                    LocalDate date1 = LocalDate.parse(m.getData().get(i).getBill_date(), dtf);
                                    Date date1 = null;
                                    try {
                                        date1 = new SimpleDateFormat("yyyy-MM-dd").parse(m.getData().get(i).getBill_date());
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    Log.e("isToday", isToday(date1) + "");
                                    if (isToday(date1)) {
                                        order_list.add(model);
                                    }
                                } else if (sale_type.equals("monthly")) {
                                    //filter monthly data
                                    String txt = m.getData().get(i).getBill_date();
                                    Date date = null;
                                    try {
                                        date = sdf.parse(txt);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    Calendar cal1 = Calendar.getInstance();
                                    Calendar cal2 = Calendar.getInstance();

//set the given date in one of the instance and current date in the other
                                    cal1.setTime(date);
                                    cal2.setTime(new Date());

//now compare the dates using methods on Calendar
                                    if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) {
                                        if (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) {
                                            // the date falls in current month
                                            order_list.add(model);
                                        }
                                    }
                                } else if (sale_type.equals("total")) {
                                    order_list.add(model);
                                }
                            }
//                            Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        order_list_recyclerview.setHasFixedSize(true);
                        mLayoutManager = new LinearLayoutManager(context);
                        mAdapter = new OrderListAdapter(context, order_list);
                        order_list_recyclerview.setLayoutManager(mLayoutManager);
                        order_list_recyclerview.setAdapter(mAdapter);
                    }
                } else {
                    progressbar.setVisibility(View.GONE);
                    swipeContainer.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BillListModel> call,
                                  @NonNull Throwable t) {
                progressbar.setVisibility(View.GONE);
                swipeContainer.setRefreshing(false);
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }*/

    private void getOrderList() {
        order_list.clear();
        swipeContainer.setRefreshing(false);
        progressbar.setVisibility(View.VISIBLE);
        apiHelper = RetrofitClient.getInstance().create(ApiHelper.class);
        Call<BillListModel> loginCall = apiHelper.getBillingList(PrefsHelper.getString(context, "username"), PrefsHelper.getString(context, "password"));
        loginCall.enqueue(new Callback<BillListModel>() {
            @Override
            public void onResponse(@NonNull Call<BillListModel> call,
                                   @NonNull Response<BillListModel> response) {
                progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response != null) {
                        BillListModel m = response.body();
                        if (m != null && m.getStatus().equalsIgnoreCase("success")) {
                            OrderListModel model;
                            ArrayList<Products> array_list;
                            Products d;
                            for (int i = 0; i < m.getData().size(); i++) {
                                array_list = new ArrayList<>();
                                model = new OrderListModel();
                                model.setBill_no(m.getData().get(i).getBill_no());
                                model.setBill_date(m.getData().get(i).getBill_date());
                                model.setCustomer_name(m.getData().get(i).getCustomer_name());
                                model.setCustomer_mobile(m.getData().get(i).getCustomer_mobile());
                                model.setCgst(m.getData().get(i).getCgst());
                                model.setSgst(m.getData().get(i).getSgst());
                                model.setNet_payable(m.getData().get(i).getNet_payable());
                                model.setTotal(m.getData().get(i).getTotal());
                                model.setPayment_mode(m.getData().get(i).getPayment_mode());
                                model.setPayment_type(m.getData().get(i).getPayment_type());
                                model.setCustomer_id(m.getData().get(i).getCustomer_id());
                                model.setOutlet_id(m.getData().get(i).getOutlet_id());
                                model.setRound_off(m.getData().get(i).getRound_off());
                                model.setTaxable(m.getData().get(i).getTaxable_amt());
                                model.setTotal_discount(m.getData().get(i).getDiscount_amt());
                                model.setId(m.getData().get(i).getId());

                                for (int j = 0; j < m.getData().get(i).getProduct_data().size(); j++) {
                                    BillListModel.Product_data p = m.getData().get(i).getProduct_data().get(j);
                                    d = new Products();
                                    d.setBatch(p.getBatch());
                                    d.setSingleDiscountAmt(p.getSingleDiscountAmt());
                                    d.setSingleDiscountPer(p.getSingleDiscountPer());
                                    d.setSingleTaxRatePer(p.getSingleTaxRatePer());
                                    d.setFinal_Price(p.getFinal_Price());
                                    d.setTotal_Basic_price(p.getTotal_Basic_price());
                                    d.setTotal_CGST(p.getTotal_CGST());
                                    d.setTotal_SGST(p.getTotal_SGST());
                                    d.setTotal_Tax_Amt(p.getTotal_Tax_Amt());
                                    d.setSingle_Basic_Price(p.getSingle_Basic_Price());
                                    d.setSingle_CGST(p.getSingle_CGST());
                                    d.setSingle_SGST(p.getSingle_SGST());
                                    d.setSingle_Tax_Amt(p.getSingle_Tax_Amt());
                                    d.setQuantity(p.getQuantity());
                                    d.setSale_Price(p.getSale_Price());
                                    d.setProduct_Id(p.getProduct_Id());

                                    array_list.add(d);
                                }
                                Log.e("size", array_list.size() + "");
                                model.setProduct_list(array_list);

                                if (sale_type.equals("today")) {
                                    //filter daily data
//                                    LocalDate date1 = LocalDate.parse(m.getData().get(i).getBill_date(), dtf);
                                    Date date1 = null;
                                    try {
                                        date1 = new SimpleDateFormat("yyyy-MM-dd").parse(m.getData().get(i).getBill_date());
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    Log.e("isToday", isToday(date1) + "");
                                    if (isToday(date1)) {
                                        order_list.add(model);
                                    }
                                } else if (sale_type.equals("monthly")) {
                                    //filter monthly data
                                    String txt = m.getData().get(i).getBill_date();
                                    Date date = null;
                                    try {
                                        date = sdf.parse(txt);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    Calendar cal1 = Calendar.getInstance();
                                    Calendar cal2 = Calendar.getInstance();
//set the given date in one of the instance and current date in the other
                                    cal1.setTime(date);
                                    cal2.setTime(new Date());

//now compare the dates using methods on Calendar
                                    if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) {
                                        if (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) {
                                            // the date falls in current month
                                            order_list.add(model);
                                        }
                                    }
                                } else if (sale_type.equals("total")) {
                                    order_list.add(model);
                                }
//                                order_list.add(model);
                            }
                        } else {
                            Toast.makeText(context, m.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        order_list_recyclerview.setHasFixedSize(true);
                        mLayoutManager = new LinearLayoutManager(context);
                        mAdapter = new OrderListAdapter(context, order_list);
                        order_list_recyclerview.setLayoutManager(mLayoutManager);
                        order_list_recyclerview.setAdapter(mAdapter);
                        Log.e("list_size", order_list.size() + "");
                    }
                } else {
                    progressbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BillListModel> call,
                                  @NonNull Throwable t) {
                progressbar.setVisibility(View.GONE);
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }

    public static boolean isToday(Date date) {
        return org.apache.commons.lang3.time.DateUtils.isSameDay(Calendar.getInstance().getTime(), date);
    }
}
