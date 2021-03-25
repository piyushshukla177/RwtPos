package com.service.response_model;

public class GetOutLetSettings {

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
        private String updated_at;
        private String created_at;
        private String intStatus;
        private String is_footer;
        private String is_header;
        private String joining_date;
        private String passwordString;
        private String password;
        private String address;
        private String mobile;
        private String email;
        private String store_name;
        private String owner_name;
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

        public String getJoining_date() {
            return joining_date;
        }

        public void setJoining_date(String joining_date) {
            this.joining_date = joining_date;
        }

        public String getPasswordString() {
            return passwordString;
        }

        public void setPasswordString(String passwordString) {
            this.passwordString = passwordString;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
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
