package com.rathana.rest_client_retrofit.data.service;

import com.rathana.rest_client_retrofit.model.reponse.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CategoryService {

    @GET("/v1/api/categories")
    Call<Response> getCategories();

    @GET("/v1/api/categories/{id}/articles/")
    Call<retrofit2.Response> getCategoryByArticleId(@Path("id") int articleId);


}
