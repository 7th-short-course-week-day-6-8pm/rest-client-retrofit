package com.rathana.rest_client_retrofit.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public  class Article{

    @SerializedName("ID")
    public int id;
    @SerializedName("IMAGE")
    public String image;
    @SerializedName("STATUS")
    public String status;
    @SerializedName("CATEGORY_ID")
    public int categoryId;
    @SerializedName("AUTHOR")
    public Author author;
    @SerializedName("DESCRIPTION")
    public String description;
    @SerializedName("TITLE")
    public String title;
    @SerializedName("CREATED_DATE")
    private String createDate;

    @SerializedName("CATEGORY")
    private Category category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Article(){}

    public Article(String title,String image,String createDate ) {
        this.image = image;
        this.title = title;
        this.createDate=createDate;
    }

    @Override
    public String toString() {
        return "Article{" +
                "image='" + image + '\'' +
                ", status='" + status + '\'' +
                ", categoryId=" + categoryId +
                ", author=" + author +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
