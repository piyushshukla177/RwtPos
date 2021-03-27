package com.service.response_model;

import java.util.List;

public class GetEditDataOutletBill {
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
        private List<Inventory> inventory;
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
        private String Barcode;
        private String Product;

        public List<Inventory> getInventory() {
            return inventory;
        }

        public void setInventory(List<Inventory> inventory) {
            this.inventory = inventory;
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

        public String getBarcode() {
            return Barcode;
        }

        public void setBarcode(String Barcode) {
            this.Barcode = Barcode;
        }

        public String getProduct() {
            return Product;
        }

        public void setProduct(String Product) {
            this.Product = Product;
        }
    }

    public static class Inventory {
        private int qty;
        private String price;
        private String outlet_id;

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getOutlet_id() {
            return outlet_id;
        }

        public void setOutlet_id(String outlet_id) {
            this.outlet_id = outlet_id;
        }
    }
}
