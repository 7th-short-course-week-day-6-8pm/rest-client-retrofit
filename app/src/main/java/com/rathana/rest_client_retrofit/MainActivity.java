package com.rathana.rest_client_retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.rathana.rest_client_retrofit.adapter.AmsAdapter;
import com.rathana.rest_client_retrofit.data.ServiceGenerator;
import com.rathana.rest_client_retrofit.data.service.CategoryService;
import com.rathana.rest_client_retrofit.model.Category;
import com.rathana.rest_client_retrofit.model.reponse.Article;
import com.rathana.rest_client_retrofit.model.reponse.Response;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    CategoryService categoryService;
    RecyclerView rvArticle;
    AmsAdapter amsAdapter;
    List<Article> articles=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        categoryService = ServiceGenerator.createService(CategoryService.class);

        //request to server
        Call<Response> call = categoryService.getCategories();

        //synchorous
        //call.execute();

        //asynchronous
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                List<Category> categories= response.body().getCategories();
                response.body().getPagination();
                Log.e("activity",""+categories);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.toString());
            }
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
        for(int i=0;i<50;i++){
            articles.add(new Article(
                    "Glass Wall Art Acrylic Decor Stones and Sun",
                    "https://i.etsystatic.com/9436019/r/il/99a8b7/685227419/il_fullxfull.685227419_bgea.jpg",
                    1,
                    new SimpleDateFormat("dd-MM-yyyy").format(new Date(System.currentTimeMillis()))
            ));
        }
        amsAdapter.addMoreItems(articles);
    }

    private static final String TAG = "MainActivity";
}


