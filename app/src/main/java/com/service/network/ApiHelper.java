package com.service.network;

import com.service.response_model.AddCustomerResponse;
import com.service.response_model.BillListModel;
import com.service.response_model.CommonModel;
import com.service.response_model.DashboardModel;
import com.service.response_model.LoginModel;
import com.service.response_model.ProductByBarcode;

import java.util.Map;
import java.util.Objects;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface ApiHelper {

    @FormUrlEncoded
    @POST("loginapi")
    Call<CommonModel<LoginModel>> getLogin(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("dashboard")
    Call<CommonModel<DashboardModel>> getDashboardData(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("ouletbillinglist")
    Call<BillListModel> getBillingList(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("getProductByBarcode")
    Call<ProductByBarcode> getProductByBarcode(@Field("username") String username, @Field("password") String password, @Field("barcode") String barcode);

    @FormUrlEncoded
    @POST("addcustomer")
    Call<AddCustomerResponse> addCustomer(@Field("username") String username, @Field("password") String password, @Field("name") String name,
                                          @Field("email") String email, @Field("mobile") String mobile, @Field("address") String address
    );
}
