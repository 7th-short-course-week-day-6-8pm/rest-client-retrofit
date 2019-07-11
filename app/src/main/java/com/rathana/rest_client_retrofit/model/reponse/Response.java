package com.rathana.rest_client_retrofit.model.reponse;

import android.view.Window;

import com.google.gson.annotations.SerializedName;
import com.rathana.rest_client_retrofit.model.Category;
import com.rathana.rest_client_retrofit.model.Pagination;

import java.util.List;

public class Response {

    @SerializedName("CODE")
    private String code;
    @SerializedName("MESSAGE")
    private String message;
    @SerializedName("DATA")
    private List<Category> categories;
    @SerializedName("PAGINATION")
    private Pagination pagination;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", categories=" + categories +
                ", pagination=" + pagination +
                '}';
    }
}
