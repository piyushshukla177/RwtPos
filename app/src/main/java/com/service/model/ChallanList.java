package com.service.model;

import java.util.ArrayList;

public class ChallanList {

    private String challan_invoice;
    private String mode_of_transport;
    private String challan_date;
    private String description;
    private String taxable_amt;
    private String cgst;
    private String sgst;
    private String total;
    private String round_off;
    private String net_payable;
    private String intStatus;

    public String getChallan_invoice() {
        return challan_invoice;
    }

    public void setChallan_invoice(String challan_invoice) {
        this.challan_invoice = challan_invoice;
    }

    public String getMode_of_transport() {
        return mode_of_transport;
    }

    public void setMode_of_transport(String mode_of_transport) {
        this.mode_of_transport = mode_of_transport;
    }

    public String getChallan_date() {
        return challan_date;
    }

    public void setChallan_date(String challan_date) {
        this.challan_date = challan_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTaxable_amt() {
        return taxable_amt;
    }

    public void setTaxable_amt(String taxable_amt) {
        this.taxable_amt = taxable_amt;
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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getRound_off() {
        return round_off;
    }

    public void setRound_off(String round_off) {
        this.round_off = round_off;
    }

    public String getNet_payable() {
        return net_payable;
    }

    public void setNet_payable(String net_payable) {
        this.net_payable = net_payable;
    }

    public String getIntStatus() {
        return intStatus;
    }

    public void setIntStatus(String intStatus) {
        this.intStatus = intStatus;
    }
}
