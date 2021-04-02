package com.service.response_model;

import java.util.List;

public class ChallanDetailModel {


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
        private String net_payable;
        private String round_off;
        private String total;
        private String sgst;
        private String cgst;
        private String taxable_amt;
        private List<Product_data> product_data;
        private String description;
        private String challan_date;
        private String mode_of_transport;
        private String challan_invoice;

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

        public String getChallan_date() {
            return challan_date;
        }

        public void setChallan_date(String challan_date) {
            this.challan_date = challan_date;
        }

        public String getMode_of_transport() {
            return mode_of_transport;
        }

        public void setMode_of_transport(String mode_of_transport) {
            this.mode_of_transport = mode_of_transport;
        }

        public String getChallan_invoice() {
            return challan_invoice;
        }

        public void setChallan_invoice(String challan_invoice) {
            this.challan_invoice = challan_invoice;
        }
    }

    public static class Product_data {
        private String Final_Price;
        private String Total_Basic_price;
        private String Total_Tax_Amt;
        private String Total_SGST;
        private String Total_CGST;
        private String Quantity;
        private String Product_Name;
        private String Product_Id;

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

        public String getTotal_Tax_Amt() {
            return Total_Tax_Amt;
        }

        public void setTotal_Tax_Amt(String Total_Tax_Amt) {
            this.Total_Tax_Amt = Total_Tax_Amt;
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

        public String getQuantity() {
            return Quantity;
        }

        public void setQuantity(String Quantity) {
            this.Quantity = Quantity;
        }

        public String getProduct_Name() {
            return Product_Name;
        }

        public void setProduct_Name(String Product_Name) {
            this.Product_Name = Product_Name;
        }

        public String getProduct_Id() {
            return Product_Id;
        }

        public void setProduct_Id(String Product_Id) {
            this.Product_Id = Product_Id;
        }
    }
}
