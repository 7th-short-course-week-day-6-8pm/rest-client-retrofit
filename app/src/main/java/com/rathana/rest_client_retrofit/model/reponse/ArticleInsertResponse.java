package com.rathana.rest_client_retrofit.model.reponse;

import com.google.gson.annotations.SerializedName;
import com.rathana.rest_client_retrofit.model.Article;
import com.rathana.rest_client_retrofit.model.Pagination;

import java.util.List;

public class ArticleInsertResponse {

    @SerializedName("CODE")
    private String code;
    @SerializedName("MESSAGE")
    private String message;
    @SerializedName("DATA")
    private Article articles;

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

    public Article getArticles() {
        return articles;
    }

    public void setArticles(Article articles) {
        this.articles = articles;
    }


}
