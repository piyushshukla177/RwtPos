package com.service.response_model;

import java.util.List;

public class GetInventoryResponse {

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
        private String unit;
        private String category_name;
        private String groupname;
        private int availablestock;
        private String basic_price;
        private String total_tax;
        private String sgst;
        private String cgst;
        private String tax_percent;
        private String gst;
        private String product_description;
        private String str_product;
        private String Minmum_stock;
        private String product_pic;
        private String barcode;
        private String sku;
        private String hsn;
        private String purchase_price;
        private String sale_price;
        private String pro_print_name;
        private String id;

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public String getGroupname() {
            return groupname;
        }

        public void setGroupname(String groupname) {
            this.groupname = groupname;
        }

        public int getAvailablestock() {
            return availablestock;
        }

        public void setAvailablestock(int availablestock) {
            this.availablestock = availablestock;
        }

        public String getBasic_price() {
            return basic_price;
        }

        public void setBasic_price(String basic_price) {
            this.basic_price = basic_price;
        }

        public String getTotal_tax() {
            return total_tax;
        }

        public void setTotal_tax(String total_tax) {
            this.total_tax = total_tax;
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

        public String getTax_percent() {
            return tax_percent;
        }

        public void setTax_percent(String tax_percent) {
            this.tax_percent = tax_percent;
        }

        public String getGst() {
            return gst;
        }

        public void setGst(String gst) {
            this.gst = gst;
        }

        public String getProduct_description() {
            return product_description;
        }

        public void setProduct_description(String product_description) {
            this.product_description = product_description;
        }

        public String getStr_product() {
            return str_product;
        }

        public void setStr_product(String str_product) {
            this.str_product = str_product;
        }

        public String getMinmum_stock() {
            return Minmum_stock;
        }

        public void setMinmum_stock(String Minmum_stock) {
            this.Minmum_stock = Minmum_stock;
        }

        public String getProduct_pic() {
            return product_pic;
        }

        public void setProduct_pic(String product_pic) {
            this.product_pic = product_pic;
        }

        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
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

        public String getPurchase_price() {
            return purchase_price;
        }

        public void setPurchase_price(String purchase_price) {
            this.purchase_price = purchase_price;
        }

        public String getSale_price() {
            return sale_price;
        }

        public void setSale_price(String sale_price) {
            this.sale_price = sale_price;
        }

        public String getPro_print_name() {
            return pro_print_name;
        }

        public void setPro_print_name(String pro_print_name) {
            this.pro_print_name = pro_print_name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
