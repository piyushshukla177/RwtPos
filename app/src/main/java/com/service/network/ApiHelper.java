package com.service.network;

import com.service.response_model.AddCustomerResponse;
import com.service.response_model.GetEditDataOutletBill;
import com.service.response_model.ViewOutletBillModel;
import com.service.response_model.BillListModel;
import com.service.response_model.ChallanListModel;
import com.service.response_model.CommonModel;
import com.service.response_model.CreateBillModel;
import com.service.response_model.CreateDemandModel;
import com.service.response_model.DashboardModel;
import com.service.response_model.DemandListModel;
import com.service.response_model.GetCustomerModel;
import com.service.response_model.GetOutLetSettings;
import com.service.response_model.LoginModel;
import com.service.response_model.OutletSettingModel;
import com.service.response_model.ProductByBarcode;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiHelper {

    @FormUrlEncoded
    @POST("loginapi")
    Call<CommonModel<LoginModel>> getLogin(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("dashboard")
    Call<DashboardModel> getDashboardData(@Field("username") String username, @Field("password") String password);

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

    @FormUrlEncoded
    @POST("addoutletbill")
    Call<CreateBillModel> CreateBill(@Field("username") String username, @Field("password") String password, @Field("cust_mobile") String cust_mobile,
                                     @Field("cust_name") String cust_name, @Field("cust_id") String cust_id, @Field("pay_mode") String pay_mode,
                                     @Field("bill_date") String bill_date, @Field("product_list") String product_list, @Field("dis_amt") String dis_amt,
                                     @Field("taxable_amt") String taxable_amt, @Field("round_off") String round_off, @Field("total") String total, @Field("cgst") String cgst,
                                     @Field("sgst") String sgst,
                                     @Field("net_payable") String net_payable
    );

    @FormUrlEncoded
    @POST("adddemand")
    Call<CreateDemandModel> addDemand(@Field("username") String username, @Field("password") String password, @Field("product_list") String product_list);


    @FormUrlEncoded
    @POST("getoutletcustomerbymobile")
    Call<GetCustomerModel> getCustomerByMobile(@Field("username") String username, @Field("password") String password, @Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("outletsetting")
    Call<OutletSettingModel> setOutletSettings(@Field("username") String username, @Field("password") String password, @Field("owner_name")
            String owner_name, @Field("store_name") String store_name, @Field("email") String email, @Field("mobile") String mobile,
                                               @Field("address") String address,
                                               @Field("is_header") String is_header,
                                               @Field("is_footer") String is_footer
    );

    @FormUrlEncoded
    @POST("getoutletsetting")
    Call<GetOutLetSettings> getOutLetSettings(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("demandlist")
    Call<DemandListModel> getDemandList(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("challanlist")
    Call<ChallanListModel> getChallanList(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("viewoutletbill")
    Call<ViewOutletBillModel> ViewOutletBill(@Field("username") String username, @Field("password") String password, @Field("view_id") String view_id);

    @FormUrlEncoded
    @POST("getProductByid")
    Call<ViewOutletBillModel> getProductByid(@Field("username") String username, @Field("password") String password, @Field("pro_id") String pro_id);

    @FormUrlEncoded
    @POST("editdataoutletbill")
    Call<GetEditDataOutletBill> geteditdataoutletbill(@Field("username") String username, @Field("password") String password, @Field("view_id") String pro_id);

    @FormUrlEncoded
    @POST("editoutletbill")
    Call<GetEditDataOutletBill> editInvoice(@Field("username") String username, @Field("password") String password, @Field("edit_id") String pro_id);
}
