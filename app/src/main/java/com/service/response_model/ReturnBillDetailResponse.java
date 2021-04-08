package com.service.response_model;

import java.util.List;

public class ReturnBillDetailResponse {

    private Data data;
    private String message;
    private String status;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class Data {
        private String net_payable;
        private String round_off;
        private String total;
        private String sgst;
        private String cgst;
        private String taxable_amt;
        private String discount_amt;
        private String return_no;
        private String return_date;
        private String return_id;
        private List<Product_data> product_data;
        private Customer_details customer_details;

        public String getNet_payable() {
            return net_payable;
        }

        public void setNet_payable(String net_payable) {
            this.net_payable = net_payable;
        }

        public String getRound_off() {
            return round_off;
        }

        public void setRound_off(String round_off) {
            this.round_off = round_off;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getSgst() {
            return sgst;
        }

        public void setSgst(String sgst) {
            this.sgst = sgst;
        }

        public String getCgst() {
            return cgst;
        }

        public void setCgst(String cgst) {
            this.cgst = cgst;
        }

        public String getTaxable_amt() {
            return taxable_amt;
        }

        public void setTaxable_amt(String taxable_amt) {
            this.taxable_amt = taxable_amt;
        }

        public String getDiscount_amt() {
            return discount_amt;
        }

        public void setDiscount_amt(String discount_amt) {
            this.discount_amt = discount_amt;
        }

        public String getReturn_no() {
            return return_no;
        }

        public void setReturn_no(String return_no) {
            this.return_no = return_no;
        }

        public String getReturn_date() {
            return return_date;
        }

        public void setReturn_date(String return_date) {
            this.return_date = return_date;
        }

        public String getReturn_id() {
            return return_id;
        }

        public void setReturn_id(String return_id) {
            this.return_id = return_id;
        }

        public List<Product_data> getProduct_data() {
            return product_data;
        }

        public void setProduct_data(List<Product_data> product_data) {
            this.product_data = product_data;
        }

        public Customer_details getCustomer_details() {
            return customer_details;
        }

        public void setCustomer_details(Customer_details customer_details) {
            this.customer_details = customer_details;
        }
    }

    public static class Product_data {
        private String Single_Return_Discount_Amt;
        private String Single_Return_Quantity;
        private String Single_Return_Final_Price;
        private String Single_Return_Total_Basic_price;
        private String Single_Return_Total_SGST;
        private String Single_Return_Total_CGST;
        private String Single_Return_Tax_Amt;
        private String SingleDiscountPer;
        private String SingleDiscountAmt;
        private String SingleTaxRatePer;
        private String Final_Price;
        private String Total_Basic_price;
        private String Total_SGST;
        private String Total_CGST;
        private String Total_Tax_Amt;
        private String Single_Basic_Price;
        private String Single_SGST;
        private String Single_CGST;
        private String Single_Tax_Amt;
        private String Sale_Price;
        private String Quantity;
        private String Batch;
        private String Product;
        private String Product_Id;

        public String getSingle_Return_Discount_Amt() {
            return Single_Return_Discount_Amt;
        }

        public void setSingle_Return_Discount_Amt(String single_Return_Discount_Amt) {
            Single_Return_Discount_Amt = single_Return_Discount_Amt;
        }

        public String getSingle_Return_Quantity() {
            return Single_Return_Quantity;
        }

        public void setSingle_Return_Quantity(String single_Return_Quantity) {
            Single_Return_Quantity = single_Return_Quantity;
        }

        public String getSingle_Return_Final_Price() {
            return Single_Return_Final_Price;
        }

        public void setSingle_Return_Final_Price(String single_Return_Final_Price) {
            Single_Return_Final_Price = single_Return_Final_Price;
        }

        public String getSingle_Return_Total_Basic_price() {
            return Single_Return_Total_Basic_price;
        }

        public void setSingle_Return_Total_Basic_price(String single_Return_Total_Basic_price) {
            Single_Return_Total_Basic_price = single_Return_Total_Basic_price;
        }

        public String getSingle_Return_Total_SGST() {
            return Single_Return_Total_SGST;
        }

        public void setSingle_Return_Total_SGST(String single_Return_Total_SGST) {
            Single_Return_Total_SGST = single_Return_Total_SGST;
        }

        public String getSingle_Return_Total_CGST() {
            return Single_Return_Total_CGST;
        }

        public void setSingle_Return_Total_CGST(String single_Return_Total_CGST) {
            Single_Return_Total_CGST = single_Return_Total_CGST;
        }

        public String getSingle_Return_Tax_Amt() {
            return Single_Return_Tax_Amt;
        }

        public void setSingle_Return_Tax_Amt(String single_Return_Tax_Amt) {
            Single_Return_Tax_Amt = single_Return_Tax_Amt;
        }

        public String getSingleDiscountPer() {
            return SingleDiscountPer;
        }

        public void setSingleDiscountPer(String singleDiscountPer) {
            SingleDiscountPer = singleDiscountPer;
        }

        public String getSingleDiscountAmt() {
            return SingleDiscountAmt;
        }

        public void setSingleDiscountAmt(String singleDiscountAmt) {
            SingleDiscountAmt = singleDiscountAmt;
        }

        public String getSingleTaxRatePer() {
            return SingleTaxRatePer;
        }

        public void setSingleTaxRatePer(String singleTaxRatePer) {
            SingleTaxRatePer = singleTaxRatePer;
        }

        public String getFinal_Price() {
            return Final_Price;
        }

        public void setFinal_Price(String final_Price) {
            Final_Price = final_Price;
        }

        public String getTotal_Basic_price() {
            return Total_Basic_price;
        }

        public void setTotal_Basic_price(String total_Basic_price) {
            Total_Basic_price = total_Basic_price;
        }

        public String getTotal_SGST() {
            return Total_SGST;
        }

        public void setTotal_SGST(String total_SGST) {
            Total_SGST = total_SGST;
        }

        public String getTotal_CGST() {
            return Total_CGST;
        }

        public void setTotal_CGST(String total_CGST) {
            Total_CGST = total_CGST;
        }

        public String getTotal_Tax_Amt() {
            return Total_Tax_Amt;
        }

        public void setTotal_Tax_Amt(String total_Tax_Amt) {
            Total_Tax_Amt = total_Tax_Amt;
        }

        public String getSingle_Basic_Price() {
            return Single_Basic_Price;
        }

        public void setSingle_Basic_Price(String single_Basic_Price) {
            Single_Basic_Price = single_Basic_Price;
        }

        public String getSingle_SGST() {
            return Single_SGST;
        }

        public void setSingle_SGST(String single_SGST) {
            Single_SGST = single_SGST;
        }

        public String getSingle_CGST() {
            return Single_CGST;
        }

        public void setSingle_CGST(String single_CGST) {
            Single_CGST = single_CGST;
        }

        public String getSingle_Tax_Amt() {
            return Single_Tax_Amt;
        }

        public void setSingle_Tax_Amt(String single_Tax_Amt) {
            Single_Tax_Amt = single_Tax_Amt;
        }

        public String getSale_Price() {
            return Sale_Price;
        }

        public void setSale_Price(String sale_Price) {
            Sale_Price = sale_Price;
        }

        public String getQuantity() {
            return Quantity;
        }

        public void setQuantity(String quantity) {
            Quantity = quantity;
        }

        public String getBatch() {
            return Batch;
        }

        public void setBatch(String batch) {
            Batch = batch;
        }

        public String getProduct() {
            return Product;
        }

        public void setProduct(String product) {
            Product = product;
        }

        public String getProduct_Id() {
            return Product_Id;
        }

        public void setProduct_Id(String product_Id) {
            Product_Id = product_Id;
        }
    }

    public static class Customer_details {
        private String updated_at;
        private String created_at;
        private String intStatus;
        private String added_by;
        private String address;
        private String email;
        private String mobile;
        private String name;
        private String outlet_id;
        private String id;

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getIntStatus() {
            return intStatus;
        }

        public void setIntStatus(String intStatus) {
            this.intStatus = intStatus;
        }

        public String getAdded_by() {
            return added_by;
        }

        public void setAdded_by(String added_by) {
            this.added_by = added_by;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOutlet_id() {
            return outlet_id;
        }

        public void setOutlet_id(String outlet_id) {
            this.outlet_id = outlet_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
