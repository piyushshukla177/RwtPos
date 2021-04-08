package com.service.model;

public class InventoryModel {

    private String str_product;
    private String sale_price;
    private String purchase_price;
    private String sku;
    private String hsn;
    private String barcode;
    private String product_pic;
    private String Minmum_stock;
    private String gst;
    private String tax_percent;
    private String availablestock;
    private String groupname;
    private String category_name;
    private String product_description;

    public String getStr_product() {
        return str_product;
    }

    public void setStr_product(String str_product) {
        this.str_product = str_product;
    }

    public String getSale_price() {
        return sale_price;
    }

    public void setSale_price(String sale_price) {
        this.sale_price = sale_price;
    }

    public String getPurchase_price() {
        return purchase_price;
    }

    public void setPurchase_price(String purchase_price) {
        this.purchase_price = purchase_price;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
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

    public String getProduct_pic() {
        return product_pic;
    }

    public void setProduct_pic(String product_pic) {
        this.product_pic = product_pic;
    }

    public String getMinmum_stock() {
        return Minmum_stock;
    }

    public void setMinmum_stock(String minmum_stock) {
        Minmum_stock = minmum_stock;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getTax_percent() {
        return tax_percent;
    }

    public void setTax_percent(String tax_percent) {
        this.tax_percent = tax_percent;
    }

    public String getAvailablestock() {
        return availablestock;
    }

    public void setAvailablestock(String availablestock) {
        this.availablestock = availablestock;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }
}
