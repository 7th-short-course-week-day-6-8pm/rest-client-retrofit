package com.rathana.rest_client_retrofit.data.service;

import com.google.gson.JsonObject;
import com.rathana.rest_client_retrofit.model.param.ArticleParam;
import com.rathana.rest_client_retrofit.model.reponse.ArticleInsertResponse;
import com.rathana.rest_client_retrofit.model.reponse.ArticleResponse;
import com.rathana.rest_client_retrofit.model.reponse.UploadImageRepose;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @Multipart
    @POST("/v1/api/uploadfile/single")
    Call<UploadImageRepose> uploadImage(@Part MultipartBody.Part file);

    //Rx


    @GET("/v1/api/articles")
    Flowable<ArticleResponse> getArticleWithRx(
            @Query("title") String title,
            @Query("page") long page,
            @Query("limit") long limit
    );

}
