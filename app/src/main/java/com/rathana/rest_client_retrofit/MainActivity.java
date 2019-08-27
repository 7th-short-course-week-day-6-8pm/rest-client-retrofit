package com.rathana.rest_client_retrofit;

import android.content.Intent;
import android.os.Handler;
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
import com.paginate.Paginate;
import com.paginate.recycler.LoadingListItemSpanLookup;
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

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
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
    Paginate paginate;
    int page=1;
    int totalPage=10;
    boolean isLoading=false;

    CompositeDisposable disposable=new CompositeDisposable();

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
        //getArticle();

        //getArticleWithRx(1,20);
    }

    private void initView(){
        rvArticle=findViewById(R.id.rvArticle);
        rvArticle.setLayoutManager(new LinearLayoutManager(this));
        amsAdapter=new AmsAdapter(articles,this);
        rvArticle.setAdapter(amsAdapter);

        //setup paginate
        page=1;
        isLoading=true;
        paginate =Paginate.with(rvArticle,callback)
        .setLoadingTriggerThreshold(2)
        .addLoadingListItem(true)
        .setLoadingListItemCreator(null)
        .setLoadingListItemSpanSizeLookup(new LoadingListItemSpanLookup() {
            @Override
            public int getSpanSize() {
                return 1;
            }
        }).build();

        callback.onLoadMore();
    }

    Paginate.Callbacks callback=new Paginate.Callbacks() {
        @Override
        public void onLoadMore() {
            if(isLoading){
                Log.e(TAG, "page: " +page);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                },500);

                //getArticle((long)page,15l);
                getArticleWithRx((long)page,15l);
                page++;
                isLoading=false;
            }

        }

        @Override
        public boolean isLoading() {
            return false;
        }

        @Override
        public boolean hasLoadedAllItems() {
            return page==10;
        }
    };

    private void getArticle(long page,long limit){
//        for(int i=0;i<50;i++){
//            articles.add(new Article(
//                    "Glass Wall Art Acrylic Decor Stones and Sun",
//                    "https://i.etsystatic.com/9436019/r/il/99a8b7/685227419/il_fullxfull.685227419_bgea.jpg",
//                    new SimpleDateFormat("dd-MM-yyyy").format(new Date(System.currentTimeMillis()))
//            ));
//        }
//        amsAdapter.addMoreItems(articles);
        progressBar.setVisibility(View.VISIBLE);
        Call<ArticleResponse> call = articleService.getArticles("",page,limit);
        call.enqueue(new Callback<ArticleResponse>() {
                @Override
                public void onResponse(Call<ArticleResponse> call, retrofit2.Response<ArticleResponse> response) {

                    isLoading=true;
                    totalPage=response.body().getPagination().getTotalPage();
                    Log.e(TAG, "Articles: "+response.code());
                    Log.e(TAG, "Articles: "+response.body().getArticles());
                    amsAdapter.addMoreItems(response.body().getArticles());
                    progressBar.setVisibility(View.GONE);

                    Log.e(TAG, "Total page: "+totalPage );
                }

                @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {
                    Log.e(TAG, "Articles: "+t.toString());
                    progressBar.setVisibility(View.GONE);
                    isLoading=false;
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
    protected void onDestroy() {
        super.onDestroy();
        paginate=null;
        disposable.clear();
    }

    @Override
    public void onEdit(Article article, int pos) {

    }

    private static final String TAG = "MainActivity";

    private  void getArticleWithRx(long page,long limit){
       Flowable<ArticleResponse> flowable= articleService.getArticleWithRx("",page,limit);
       disposable.add(flowable.subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<ArticleResponse>() {
                    @Override
                    public void onNext(ArticleResponse articleResponse) {
                        Log.e(TAG, "onNext: "+ articleResponse.getArticles());
                        isLoading=true;
                        amsAdapter.addMoreItems(articleResponse.getArticles());

                }

                    @Override
                    public void onError(Throwable t) {
                        Log.e(TAG, "onError: "+t.toString() );
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: " );
                    }
                }));

    }


}


