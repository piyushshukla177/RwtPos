package com.service.response_model;

public  class CreateDemandModel {

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
        private String created_at;
        private String ondate;
        private String product_data;
        private String outlet_id;

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
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
    }
}
