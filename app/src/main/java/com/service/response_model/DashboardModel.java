package com.service.response_model;

public class DashboardModel {

    private String total_business;
    private int today_business;
    private String monthly_business;
    private int total_challan;
    private int total_demand;
    private int total_order;
    private int today_order;
    private int monthly_order;

    public String getTotal_business() {
        return total_business;
    }

    public void setTotal_business(String total_business) {
        this.total_business = total_business;
    }

    public int getToday_business() {
        return today_business;
    }

    public void setToday_business(int today_business) {
        this.today_business = today_business;
    }

    public String getMonthly_business() {
        return monthly_business;
    }

    public void setMonthly_business(String monthly_business) {
        this.monthly_business = monthly_business;
    }

    public int getTotal_challan() {
        return total_challan;
    }

    public void setTotal_challan(int total_challan) {
        this.total_challan = total_challan;
    }

    public int getTotal_demand() {
        return total_demand;
    }

    public void setTotal_demand(int total_demand) {
        this.total_demand = total_demand;
    }

    public int getTotal_order() {
        return total_order;
    }

    public void setTotal_order(int total_order) {
        this.total_order = total_order;
    }

    public int getToday_order() {
        return today_order;
    }

    public void setToday_order(int today_order) {
        this.today_order = today_order;
    }

    public int getMonthly_order() {
        return monthly_order;
    }

    public void setMonthly_order(int monthly_order) {
        this.monthly_order = monthly_order;
    }
}
