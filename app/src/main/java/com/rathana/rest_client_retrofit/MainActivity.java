package com.rathana.rest_client_retrofit;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.rathana.rest_client_retrofit.adapter.AmsAdapter;
import com.rathana.rest_client_retrofit.data.ServiceGenerator;
import com.rathana.rest_client_retrofit.data.service.ArticleService;
import com.rathana.rest_client_retrofit.data.service.CategoryService;
import com.rathana.rest_client_retrofit.model.Category;
import com.rathana.rest_client_retrofit.model.Article;
import com.rathana.rest_client_retrofit.model.reponse.ArticleResponse;
import com.rathana.rest_client_retrofit.model.reponse.Response;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity implements AmsAdapter.AmsCallback {

    CategoryService categoryService;
    ArticleService articleService;
    RecyclerView rvArticle;
    AmsAdapter amsAdapter;
    ProgressBar progressBar;
    FloatingActionButton btnAdd;
    List<Article> articles=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        categoryService = ServiceGenerator.createService(CategoryService.class);
        articleService= ServiceGenerator.createService(ArticleService.class);
        //request to server
        Call<Response> call = categoryService.getCategories();

        progressBar=findViewById(R.id.progressbar);
        btnAdd=findViewById(R.id.btnAdd);

        //synchorous
        //call.execute();

        //asynchronous
//        call.enqueue(new Callback<Response>() {
//            @Override
//            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
//                List<Category> categories= response.body().getCategories();
//                response.body().getPagination();
//                Log.e("activity",""+categories);
//            }
//
//            @Override
//            public void onFailure(Call<Response> call, Throwable t) {
//                Log.e(TAG, "onFailure: "+t.toString());
//            }
//        });

        btnAdd.setOnClickListener(v->{
            startActivity(new Intent(this,AddArticleAvtivity.class));
        });

        initView();
        getArticle();
    }

    private void initView(){
        rvArticle=findViewById(R.id.rvArticle);
        rvArticle.setLayoutManager(new LinearLayoutManager(this));
        amsAdapter=new AmsAdapter(articles,this);
        rvArticle.setAdapter(amsAdapter);
    }

    private void getArticle(){
//        for(int i=0;i<50;i++){
//            articles.add(new Article(
//                    "Glass Wall Art Acrylic Decor Stones and Sun",
//                    "https://i.etsystatic.com/9436019/r/il/99a8b7/685227419/il_fullxfull.685227419_bgea.jpg",
//                    new SimpleDateFormat("dd-MM-yyyy").format(new Date(System.currentTimeMillis()))
//            ));
//        }
//        amsAdapter.addMoreItems(articles);
        progressBar.setVisibility(View.VISIBLE);
        Call<ArticleResponse> call = articleService.getArticles("",1l,25l);
        call.enqueue(new Callback<ArticleResponse>() {
                @Override
                public void onResponse(Call<ArticleResponse> call, retrofit2.Response<ArticleResponse> response) {
                    Log.e(TAG, "Articles: "+response.body().getArticles());
                    Log.e(TAG, "Articles: "+response.code());
                    amsAdapter.addMoreItems(response.body().getArticles());
                    progressBar.setVisibility(View.GONE);
                }

                @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {
                    Log.e(TAG, "Articles: "+t.toString());
                    progressBar.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public void onDelete(Article article, int pos) {
        progressBar.setVisibility(View.VISIBLE);
        articleService.deleteArticle(article.getId())
                .enqueue(new Callback<retrofit2.Response<JsonObject>>() {
                    @Override
                    public void onResponse(Call<retrofit2.Response<JsonObject>> call, retrofit2.Response<retrofit2.Response<JsonObject>> response) {
                        progressBar.setVisibility(View.GONE);
                        articles.remove(article);
                        amsAdapter.notifyItemRemoved(pos);
                    }

                    @Override
                    public void onFailure(Call<retrofit2.Response<JsonObject>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "cannot delete this article", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onEdit(Article article, int pos) {

    }

    private static final String TAG = "MainActivity";
}


