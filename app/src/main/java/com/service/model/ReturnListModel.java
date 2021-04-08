package com.service.model;

public class ReturnListModel {

    private String id;
    private String customer_id;
    private String return_date;
    private String bill_id;
    private String return_invoice;
    private String net_payable;
    private String discount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getReturn_date() {
        return return_date;
    }

    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public String getReturn_invoice() {
        return return_invoice;
    }

    public void setReturn_invoice(String return_invoice) {
        this.return_invoice = return_invoice;
    }

    public String getNet_payable() {
        return net_payable;
    }

    public void setNet_payable(String net_payable) {
        this.net_payable = net_payable;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
