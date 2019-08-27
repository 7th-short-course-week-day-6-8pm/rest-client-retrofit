package com.rathana.rest_client_retrofit.model;

import com.google.gson.annotations.SerializedName;

public class Pagination{

    @SerializedName("PAGE")
    private int page;
    @SerializedName("LIMIT")
    private int limit;
    @SerializedName("TOTAL_COUNT")
    private int totalCont;
    @SerializedName("TOTAL_PAGES")
    private int totalPage;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotalCont() {
        return totalCont;
    }

    public void setTotalCont(int totalCont) {
        this.totalCont = totalCont;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    @Override
    public String toString() {
        return "Pagination{" +
                "page=" + page +
                ", limit=" + limit +
                ", totalCont=" + totalCont +
                ", totalPage=" + totalPage +
                '}';
    }
}

