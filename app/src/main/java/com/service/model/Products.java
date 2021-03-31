package com.service.model;

import com.service.response_model.ProductByBarcode;

import java.io.Serializable;
import java.util.ArrayList;

public class Products implements Serializable {
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
    private String barcode;
    private ArrayList<ProductListModel.Batch> inventory;

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public ArrayList<ProductListModel.Batch> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<ProductListModel.Batch> inventory) {
        this.inventory = inventory;
    }

    public String getSingleDiscountPer() {
        return SingleDiscountPer;
    }

    public void setSingleDiscountPer(String singleDiscountPer) {
        SingleDiscountPer = singleDiscountPer;
    }

    public String getSingleDiscountAmt() {
        return SingleDiscountAmt;
    }

    public void setSingleDiscountAmt(String singleDiscountAmt) {
        SingleDiscountAmt = singleDiscountAmt;
    }

    public String getSingleTaxRatePer() {
        return SingleTaxRatePer;
    }

    public void setSingleTaxRatePer(String singleTaxRatePer) {
        SingleTaxRatePer = singleTaxRatePer;
    }

    public String getFinal_Price() {
        return Final_Price;
    }

    public void setFinal_Price(String final_Price) {
        Final_Price = final_Price;
    }

    public String getTotal_Basic_price() {
        return Total_Basic_price;
    }

    public void setTotal_Basic_price(String total_Basic_price) {
        Total_Basic_price = total_Basic_price;
    }

    public String getTotal_SGST() {
        return Total_SGST;
    }

    public void setTotal_SGST(String total_SGST) {
        Total_SGST = total_SGST;
    }

    public String getTotal_CGST() {
        return Total_CGST;
    }

    public void setTotal_CGST(String total_CGST) {
        Total_CGST = total_CGST;
    }

    public String getTotal_Tax_Amt() {
        return Total_Tax_Amt;
    }

    public void setTotal_Tax_Amt(String total_Tax_Amt) {
        Total_Tax_Amt = total_Tax_Amt;
    }

    public String getSingle_Basic_Price() {
        return Single_Basic_Price;
    }

    public void setSingle_Basic_Price(String single_Basic_Price) {
        Single_Basic_Price = single_Basic_Price;
    }

    public String getSingle_SGST() {
        return Single_SGST;
    }

    public void setSingle_SGST(String single_SGST) {
        Single_SGST = single_SGST;
    }

    public String getSingle_CGST() {
        return Single_CGST;
    }

    public void setSingle_CGST(String single_CGST) {
        Single_CGST = single_CGST;
    }

    public String getSingle_Tax_Amt() {
        return Single_Tax_Amt;
    }

    public void setSingle_Tax_Amt(String single_Tax_Amt) {
        Single_Tax_Amt = single_Tax_Amt;
    }

    public String getSale_Price() {
        return Sale_Price;
    }

    public void setSale_Price(String sale_Price) {
        Sale_Price = sale_Price;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getBatch() {
        return Batch;
    }

    public void setBatch(String batch) {
        Batch = batch;
    }

    public String getProduct_Id() {
        return Product_Id;
    }

    public void setProduct_Id(String product_Id) {
        Product_Id = product_Id;
    }

    public String getProduct_Name() {
        return Product_Name;
    }

    public void setProduct_Name(String product_Name) {
        Product_Name = product_Name;
    }
}
