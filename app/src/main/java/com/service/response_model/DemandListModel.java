package com.service.response_model;

import java.util.List;

public  class DemandListModel {


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
        private String delivery_status;
        private String ondate;
        private String product_data;
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

        public String getDelivery_status() {
            return delivery_status;
        }

        public void setDelivery_status(String delivery_status) {
            this.delivery_status = delivery_status;
        }

        public String getOndate() {
            return ondate;
        }

        public void setOndate(String ondate) {
            this.ondate = ondate;
        }

        public String getProduct_data() {
            return product_data;
        }

        public void setProduct_data(String product_data) {
            this.product_data = product_data;
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
