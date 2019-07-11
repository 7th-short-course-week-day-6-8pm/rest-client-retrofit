package com.rathana.rest_client_retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.rathana.rest_client_retrofit.data.ServiceGenerator;
import com.rathana.rest_client_retrofit.data.service.CategoryService;
import com.rathana.rest_client_retrofit.model.Category;
import com.rathana.rest_client_retrofit.model.reponse.Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    CategoryService categoryService;

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

    }

    private static final String TAG = "MainActivity";
}


