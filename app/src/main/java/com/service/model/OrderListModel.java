package com.service.model;

import java.util.ArrayList;

public class OrderListModel {

    private String outlet_id;
    private String customer_id;
    private String bill_date;
    private String bill_no;
    private String payment_type;
    private String payment_mode;
    private String customer_name;
    private String customer_mobile;
    private String net_payable;
    private String total;
    private String cgst;
    private String sgst;
    private String round_off;
    private ArrayList<Products> product_list;
    private String taxable;
    private String total_discount;
    private String id;

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRound_off() {
        return round_off;
    }

    public String getTaxable() {
        return taxable;
    }

    public void setTaxable(String taxable) {
        this.taxable = taxable;
    }

    public String getTotal_discount() {
        return total_discount;
    }

    public void setTotal_discount(String total_discount) {
        this.total_discount = total_discount;
    }

    public void setRound_off(String round_off) {
        this.round_off = round_off;
    }

    public ArrayList<Products> getProduct_list() {
        return product_list;
    }

    public void setProduct_list(ArrayList<Products> product_list) {
        this.product_list = product_list;
    }

    public String getOutlet_id() {
        return outlet_id;
    }

    public void setOutlet_id(String outlet_id) {
        this.outlet_id = outlet_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getBill_date() {
        return bill_date;
    }

    public void setBill_date(String bill_date) {
        this.bill_date = bill_date;
    }

    public String getBill_no() {
        return bill_no;
    }

    public void setBill_no(String bill_no) {
        this.bill_no = bill_no;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_mobile() {
        return customer_mobile;
    }

    public void setCustomer_mobile(String customer_mobile) {
        this.customer_mobile = customer_mobile;
    }

    public String getNet_payable() {
        return net_payable;
    }

    public void setNet_payable(String net_payable) {
        this.net_payable = net_payable;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCgst() {
        return cgst;
    }

    public void setCgst(String cgst) {
        this.cgst = cgst;
    }

    public String getSgst() {
        return sgst;
    }

    public void setSgst(String sgst) {
        this.sgst = sgst;
    }

/*
    public static class Products implements Serializable {
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
        private String Product_Id;
        private String Product_Name;

        public String getProduct_Name() {
            return Product_Name;
        }

        public void setProduct_Name(String product_Name) {
            Product_Name = product_Name;
        }

        public String getSingleDiscountPer() {
            return SingleDiscountPer;
        }

        public void setSingleDiscountPer(String SingleDiscountPer) {
            this.SingleDiscountPer = SingleDiscountPer;
        }

        public String getSingleDiscountAmt() {
            return SingleDiscountAmt;
        }

        public void setSingleDiscountAmt(String SingleDiscountAmt) {
            this.SingleDiscountAmt = SingleDiscountAmt;
        }

        public String getSingleTaxRatePer() {
            return SingleTaxRatePer;
        }

        public void setSingleTaxRatePer(String SingleTaxRatePer) {
            this.SingleTaxRatePer = SingleTaxRatePer;
        }

        public String getFinal_Price() {
            return Final_Price;
        }

        public void setFinal_Price(String Final_Price) {
            this.Final_Price = Final_Price;
        }

        public String getTotal_Basic_price() {
            return Total_Basic_price;
        }

        public void setTotal_Basic_price(String Total_Basic_price) {
            this.Total_Basic_price = Total_Basic_price;
        }

        public String getTotal_SGST() {
            return Total_SGST;
        }

        public void setTotal_SGST(String Total_SGST) {
            this.Total_SGST = Total_SGST;
        }

        public String getTotal_CGST() {
            return Total_CGST;
        }

        public void setTotal_CGST(String Total_CGST) {
            this.Total_CGST = Total_CGST;
        }

        public String getTotal_Tax_Amt() {
            return Total_Tax_Amt;
        }

        public void setTotal_Tax_Amt(String Total_Tax_Amt) {
            this.Total_Tax_Amt = Total_Tax_Amt;
        }

        public String getSingle_Basic_Price() {
            return Single_Basic_Price;
        }

        public void setSingle_Basic_Price(String Single_Basic_Price) {
            this.Single_Basic_Price = Single_Basic_Price;
        }

        public String getSingle_SGST() {
            return Single_SGST;
        }

        public void setSingle_SGST(String Single_SGST) {
            this.Single_SGST = Single_SGST;
        }

        public String getSingle_CGST() {
            return Single_CGST;
        }

        public void setSingle_CGST(String Single_CGST) {
            this.Single_CGST = Single_CGST;
        }

        public String getSingle_Tax_Amt() {
            return Single_Tax_Amt;
        }

        public void setSingle_Tax_Amt(String Single_Tax_Amt) {
            this.Single_Tax_Amt = Single_Tax_Amt;
        }

        public String getSale_Price() {
            return Sale_Price;
        }

        public void setSale_Price(String Sale_Price) {
            this.Sale_Price = Sale_Price;
        }

        public String getQuantity() {
            return Quantity;
        }

        public void setQuantity(String Quantity) {
            this.Quantity = Quantity;
        }

        public String getBatch() {
            return Batch;
        }

        public void setBatch(String Batch) {
            this.Batch = Batch;
        }

        public String getProduct_Id() {
            return Product_Id;
        }

        public void setProduct_Id(String Product_Id) {
            this.Product_Id = Product_Id;
        }
    }*/
}