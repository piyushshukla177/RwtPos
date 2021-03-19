package com.service.model;

import java.util.ArrayList;

public class ProductListModel {
    private String pro_print_name;
    private String sale_price;
    private String hsn;
    private String barcode;
    private String purchase_price;
    private String id;
    private String product_pic;
    private String str_product;
    private String cgst;
    private String sgst;
    private String total_tax;
    private String gst;
    private String Minmum_stock;
    private String tax_percent;
    private String intStock;
    private ArrayList<Batch> batch_list;

    public String getPro_print_name() {
        return pro_print_name;
    }

    public void setPro_print_name(String pro_print_name) {
        this.pro_print_name = pro_print_name;
    }

    public String getSale_price() {
        return sale_price;
    }

    public void setSale_price(String sale_price) {
        this.sale_price = sale_price;
    }

    public String getHsn() {
        return hsn;
    }

    public void setHsn(String hsn) {
        this.hsn = hsn;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getPurchase_price() {
        return purchase_price;
    }

    public void setPurchase_price(String purchase_price) {
        this.purchase_price = purchase_price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_pic() {
        return product_pic;
    }

    public void setProduct_pic(String product_pic) {
        this.product_pic = product_pic;
    }

    public String getStr_product() {
        return str_product;
    }

    public void setStr_product(String str_product) {
        this.str_product = str_product;
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

    public String getTotal_tax() {
        return total_tax;
    }

    public void setTotal_tax(String total_tax) {
        this.total_tax = total_tax;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getMinmum_stock() {
        return Minmum_stock;
    }

    public void setMinmum_stock(String minmum_stock) {
        Minmum_stock = minmum_stock;
    }

    public String getTax_percent() {
        return tax_percent;
    }

    public void setTax_percent(String tax_percent) {
        this.tax_percent = tax_percent;
    }

    public String getIntStock() {
        return intStock;
    }

    public void setIntStock(String intStock) {
        this.intStock = intStock;
    }

    public ArrayList<Batch> getBatch_list() {
        return batch_list;
    }

    public void setBatch_list(ArrayList<Batch> batch_list) {
        this.batch_list = batch_list;
    }

    public static class Batch {
        private String price;
        private String qty;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }
    }
}
