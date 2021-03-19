package com.service.response_model;

import java.util.List;

public class BillListModel {


    private List<Data> data;
    private String message;
    private String status;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
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
        private String payment_type;
        private String customer_mobile;
        private String customer_name;
        private String updated_at;
        private String created_at;
        private String intStatus;
        private String added_by;
        private String net_payable;
        private String round_off;
        private String total;
        private String sgst;
        private String cgst;
        private String taxable_amt;
        private String discount_amt;
        private String wallet_name;
        private String transaction_date;
        private String transaction_no;
        private String payment_mode;
        private String bill_no;
        private List<Product_data> product_data;
        private String description;
        private String bill_date;
        private String customer_id;
        private String outlet_id;
        private String id;

        public String getPayment_type() {
            return payment_type;
        }

        public void setPayment_type(String payment_type) {
            this.payment_type = payment_type;
        }

        public String getCustomer_mobile() {
            return customer_mobile;
        }

        public void setCustomer_mobile(String customer_mobile) {
            this.customer_mobile = customer_mobile;
        }

        public String getCustomer_name() {
            return customer_name;
        }

        public void setCustomer_name(String customer_name) {
            this.customer_name = customer_name;
        }

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

        public String getWallet_name() {
            return wallet_name;
        }

        public void setWallet_name(String wallet_name) {
            this.wallet_name = wallet_name;
        }

        public String getTransaction_date() {
            return transaction_date;
        }

        public void setTransaction_date(String transaction_date) {
            this.transaction_date = transaction_date;
        }

        public String getTransaction_no() {
            return transaction_no;
        }

        public void setTransaction_no(String transaction_no) {
            this.transaction_no = transaction_no;
        }

        public String getPayment_mode() {
            return payment_mode;
        }

        public void setPayment_mode(String payment_mode) {
            this.payment_mode = payment_mode;
        }

        public String getBill_no() {
            return bill_no;
        }

        public void setBill_no(String bill_no) {
            this.bill_no = bill_no;
        }

        public List<Product_data> getProduct_data() {
            return product_data;
        }

        public void setProduct_data(List<Product_data> product_data) {
            this.product_data = product_data;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getBill_date() {
            return bill_date;
        }

        public void setBill_date(String bill_date) {
            this.bill_date = bill_date;
        }

        public String getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(String customer_id) {
            this.customer_id = customer_id;
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

    public static class Product_data {
        private List<String> SingleDiscountPer;
        private List<String> SingleDiscountAmt;
        private List<String> SingleTaxRatePer;
        private List<String> Final_Price;
        private List<String> Total_Basic_price;
        private List<String> Total_SGST;
        private List<String> Total_CGST;
        private List<String> Total_Tax_Amt;
        private List<String> Single_Basic_Price;
        private List<String> Single_SGST;
        private List<String> Single_CGST;
        private List<String> Single_Tax_Amt;
        private List<String> Sale_Price;
        private List<String> Quantity;
        private List<String> Batch;
        private List<String> Product_Id;

        public List<String> getSingleDiscountPer() {
            return SingleDiscountPer;
        }

        public void setSingleDiscountPer(List<String> SingleDiscountPer) {
            this.SingleDiscountPer = SingleDiscountPer;
        }

        public List<String> getSingleDiscountAmt() {
            return SingleDiscountAmt;
        }

        public void setSingleDiscountAmt(List<String> SingleDiscountAmt) {
            this.SingleDiscountAmt = SingleDiscountAmt;
        }

        public List<String> getSingleTaxRatePer() {
            return SingleTaxRatePer;
        }

        public void setSingleTaxRatePer(List<String> SingleTaxRatePer) {
            this.SingleTaxRatePer = SingleTaxRatePer;
        }

        public List<String> getFinal_Price() {
            return Final_Price;
        }

        public void setFinal_Price(List<String> Final_Price) {
            this.Final_Price = Final_Price;
        }

        public List<String> getTotal_Basic_price() {
            return Total_Basic_price;
        }

        public void setTotal_Basic_price(List<String> Total_Basic_price) {
            this.Total_Basic_price = Total_Basic_price;
        }

        public List<String> getTotal_SGST() {
            return Total_SGST;
        }

        public void setTotal_SGST(List<String> Total_SGST) {
            this.Total_SGST = Total_SGST;
        }

        public List<String> getTotal_CGST() {
            return Total_CGST;
        }

        public void setTotal_CGST(List<String> Total_CGST) {
            this.Total_CGST = Total_CGST;
        }

        public List<String> getTotal_Tax_Amt() {
            return Total_Tax_Amt;
        }

        public void setTotal_Tax_Amt(List<String> Total_Tax_Amt) {
            this.Total_Tax_Amt = Total_Tax_Amt;
        }

        public List<String> getSingle_Basic_Price() {
            return Single_Basic_Price;
        }

        public void setSingle_Basic_Price(List<String> Single_Basic_Price) {
            this.Single_Basic_Price = Single_Basic_Price;
        }

        public List<String> getSingle_SGST() {
            return Single_SGST;
        }

        public void setSingle_SGST(List<String> Single_SGST) {
            this.Single_SGST = Single_SGST;
        }

        public List<String> getSingle_CGST() {
            return Single_CGST;
        }

        public void setSingle_CGST(List<String> Single_CGST) {
            this.Single_CGST = Single_CGST;
        }

        public List<String> getSingle_Tax_Amt() {
            return Single_Tax_Amt;
        }

        public void setSingle_Tax_Amt(List<String> Single_Tax_Amt) {
            this.Single_Tax_Amt = Single_Tax_Amt;
        }

        public List<String> getSale_Price() {
            return Sale_Price;
        }

        public void setSale_Price(List<String> Sale_Price) {
            this.Sale_Price = Sale_Price;
        }

        public List<String> getQuantity() {
            return Quantity;
        }

        public void setQuantity(List<String> Quantity) {
            this.Quantity = Quantity;
        }

        public List<String> getBatch() {
            return Batch;
        }

        public void setBatch(List<String> Batch) {
            this.Batch = Batch;
        }

        public List<String> getProduct_Id() {
            return Product_Id;
        }

        public void setProduct_Id(List<String> Product_Id) {
            this.Product_Id = Product_Id;
        }
    }
}
