package com.service.response_model;

import java.util.List;

public class ChallanListModel {

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
        private String updated_at;
        private String created_at;
        private String intStatus;
        private String generate_challan;
        private String net_payable;
        private String round_off;
        private String total;
        private String sgst;
        private String cgst;
        private String taxable_amt;
        private String product_data;
        private String description;
        private String challan_date;
        private String mode_of_transport;
        private String challan_invoice;
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

        public String getGenerate_challan() {
            return generate_challan;
        }

        public void setGenerate_challan(String generate_challan) {
            this.generate_challan = generate_challan;
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

        public String getProduct_data() {
            return product_data;
        }

        public void setProduct_data(String product_data) {
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
