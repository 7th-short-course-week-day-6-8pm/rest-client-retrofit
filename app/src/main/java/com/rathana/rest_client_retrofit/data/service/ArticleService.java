package com.rathana.rest_client_retrofit.data.service;

import com.google.gson.JsonObject;
import com.rathana.rest_client_retrofit.model.param.ArticleParam;
import com.rathana.rest_client_retrofit.model.reponse.ArticleInsertResponse;
import com.rathana.rest_client_retrofit.model.reponse.ArticleResponse;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ArticleService {

    @GET("/v1/api/articles")
    Call<ArticleResponse>  getArticles(@Query("title") String title,
                                       @Query("page") long page,
                                       @Query("limit") long limit);

    @DELETE("/v1/api/articles/{id}")
    Call<Response<JsonObject>> deleteArticle(@Path("id") int id);

    @POST("/v1/api/articles")
    Call<ArticleInsertResponse> create(@Body ArticleParam article);


}
