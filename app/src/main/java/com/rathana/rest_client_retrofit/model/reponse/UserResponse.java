package com.rathana.rest_client_retrofit.model.reponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public abstract class UserResponse {


    @SerializedName("PAGINATION")
    private PaginationEntity pagination;
    @SerializedName("DATA")
    private List<User> data;
    @SerializedName("MESSAGE")
    private String message;
    @SerializedName("CODE")
    private String code;

    public PaginationEntity getPagination() {
        return pagination;
    }

    public void setPagination(PaginationEntity pagination) {
        this.pagination = pagination;
    }

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class PaginationEntity {
        @SerializedName("TOTAL_PAGES")
        private int totalPages;
        @SerializedName("TOTAL_COUNT")
        private int totalCount;
        @SerializedName("LIMIT")
        private int limit;
        @SerializedName("PAGE")
        private int page;

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }
    }

    public static class User {
        @SerializedName("IMAGE_URL")
        private String imageUrl;
        @SerializedName("FACEBOOK_ID")
        private String facebookId;
        @SerializedName("STATUS")
        private String status;
        @SerializedName("TELEPHONE")
        private String telephone;
        @SerializedName("GENDER")
        private String gender;
        @SerializedName("EMAIL")
        private String email;
        @SerializedName("NAME")
        private String name;
        @SerializedName("ID")
        private int id;

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getFacebookId() {
            return facebookId;
        }

        public void setFacebookId(String facebookId) {
            this.facebookId = facebookId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
