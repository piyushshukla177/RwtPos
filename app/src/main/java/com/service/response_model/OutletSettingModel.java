package com.service.response_model;

public class OutletSettingModel {

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
        private String is_footer;
        private String is_header;
        private String address;
        private String mobile;
        private String email;
        private String store_name;
        private String owner_name;

        public String getIs_footer() {
            return is_footer;
        }

        public void setIs_footer(String is_footer) {
            this.is_footer = is_footer;
        }

        public String getIs_header() {
            return is_header;
        }

        public void setIs_header(String is_header) {
            this.is_header = is_header;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public String getOwner_name() {
            return owner_name;
        }

        public void setOwner_name(String owner_name) {
            this.owner_name = owner_name;
        }
    }
}
