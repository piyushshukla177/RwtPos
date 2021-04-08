package com.service.network;

import com.service.response_model.AddCustomerResponse;
import com.service.response_model.BillByBillNoModel;
import com.service.response_model.ChallanDetailModel;
import com.service.response_model.CustomerListModel;
import com.service.response_model.GetEditDataOutletBill;
import com.service.response_model.GetInventoryResponse;
import com.service.response_model.SaleReturnResponse;
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
import com.service.response_model.ProductByBarcodeResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Streaming;

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
    Call<ProductByBarcodeResponse> getProductByBarcode(@Field("username") String username, @Field("password") String password, @Field("barcode") String barcode);

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
                                     @Field("net_payable") String net_payable, @Field("trans_date") String trans_date, @Field("trans_no") String trans_no, @Field("wallet") String wallet
    );

    @FormUrlEncoded
    @POST("updateoutlet_bill")
    Call<CreateBillModel> EditOutletBill(@Field("username") String username, @Field("password") String password, @Field("edit_id") String edit_id, @Field("cust_mobile") String cust_mobile,
                                         @Field("cust_name") String cust_name, @Field("cust_id") String cust_id, @Field("pay_mode") String pay_mode,
                                         @Field("bill_date") String bill_date, @Field("product_list") String product_list, @Field("dis_amt") String dis_amt,
                                         @Field("taxable_amt") String taxable_amt, @Field("round_off") String round_off, @Field("total") String total, @Field("cgst") String cgst,
                                         @Field("sgst") String sgst,
                                         @Field("net_payable") String net_payable, @Field("trans_date") String trans_date, @Field("trans_no") String trans_no, @Field("wallet") String wallet
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
    @POST("challandetailsdata")
    Call<ChallanDetailModel> getChallanDetails(@Field("username") String username, @Field("password") String password, @Field("challan_id") String challan_id);

    @FormUrlEncoded
    @POST("customer_list")
    Call<CustomerListModel> getCustomerList(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("ouletcustomerbillinglist")
    Call<BillListModel> getCustomerBills(@Field("username") String username, @Field("password") String password, @Field("customer_id") String customer_id);

    @Streaming
    @FormUrlEncoded
    @POST("billinvoice")
    Call<ResponseBody> downloadInvoice(@Field("username") String username, @Field("password") String password, @Field("bill_id") String bill_id);

    @FormUrlEncoded
    @POST("getbilldetailsbybillno")
    Call<BillByBillNoModel> getBillByBillNo(@Field("username") String username, @Field("password") String password, @Field("invoice_no") String invoice_no);

    @FormUrlEncoded
    @POST("outletinventory")
    Call<GetInventoryResponse> getInventory(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("outletinventory")
    Call<ResponseBody> saleReturn(@Field("username") String username, @Field("password") String password, @Field("bill_id") String bill_id, @Field("cust_id") String cust_id, @Field("dis_amt") String dis_amt

            , @Field("taxable_amt") String taxable_amt
            , @Field("cgst") String cgst
            , @Field("sgst") String sgst
            , @Field("total") String total
            , @Field("round_off") String round_off
            , @Field("net_payable") String net_payable
            , @Field("returnqty") String returnqty
            , @Field("product_list") String product_list

    );
}
