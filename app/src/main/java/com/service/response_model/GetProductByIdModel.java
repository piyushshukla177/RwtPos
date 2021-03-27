package com.service.response_model;

import java.util.List;

public abstract class GetProductByIdModel {

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
        private List<Inventory> inventory;
        private Product_details product_details;

        public List<Inventory> getInventory() {
            return inventory;
        }

        public void setInventory(List<Inventory> inventory) {
            this.inventory = inventory;
        }

        public Product_details getProduct_details() {
            return product_details;
        }

        public void setProduct_details(Product_details product_details) {
            this.product_details = product_details;
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

    public static class Product_details {
        private String updated_at;
        private String created_at;
        private String intStatus;
        private String intFeatured;
        private String intStock;
        private String intOffer;
        private String basic_price;
        private String total_tax;
        private String sgst;
        private String cgst;
        private String tax_percent;
        private String gst;
        private String product_description;
        private String str_product;
        private String Minmum_stock;
        private String intQuantity;
        private String intUnit_id;
        private String category_id;
        private String product_pic;
        private String barcode;
        private String sku;
        private String hsn;
        private String purchase_price;
        private String sale_price;
        private String group_id;
        private String pro_print_name;
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

        public String getIntFeatured() {
            return intFeatured;
        }

        public void setIntFeatured(String intFeatured) {
            this.intFeatured = intFeatured;
        }

        public String getIntStock() {
            return intStock;
        }

        public void setIntStock(String intStock) {
            this.intStock = intStock;
        }

        public String getIntOffer() {
            return intOffer;
        }

        public void setIntOffer(String intOffer) {
            this.intOffer = intOffer;
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

        public String getIntQuantity() {
            return intQuantity;
        }

        public void setIntQuantity(String intQuantity) {
            this.intQuantity = intQuantity;
        }

        public String getIntUnit_id() {
            return intUnit_id;
        }

        public void setIntUnit_id(String intUnit_id) {
            this.intUnit_id = intUnit_id;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
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

        public String getGroup_id() {
            return group_id;
        }

        public void setGroup_id(String group_id) {
            this.group_id = group_id;
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
