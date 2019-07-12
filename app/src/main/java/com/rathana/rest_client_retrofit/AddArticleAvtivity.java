package com.rathana.rest_client_retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rathana.rest_client_retrofit.data.ServiceGenerator;
import com.rathana.rest_client_retrofit.data.service.ArticleService;
import com.rathana.rest_client_retrofit.model.Article;
import com.rathana.rest_client_retrofit.model.Category;
import com.rathana.rest_client_retrofit.model.param.ArticleParam;
import com.rathana.rest_client_retrofit.model.reponse.ArticleInsertResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddArticleAvtivity extends AppCompatActivity {


    EditText title , catId,authorId,desc,image;
    Button btnSave;

    ArticleService articleService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article_avtivity);

        title =findViewById(R.id.title);
        catId=findViewById(R.id.catId);
        authorId=findViewById(R.id.authorId) ;
        desc=findViewById(R.id.desc);
        image=findViewById(R.id.image);
        btnSave=findViewById(R.id.btnSave);

        articleService= ServiceGenerator.createService(ArticleService.class);

        btnSave.setOnClickListener(v->{
            ArticleParam article=new ArticleParam();
            article.setTitle(title.getText().toString());
            article.setDescription(desc.getText().toString());
            article.setCategoryId(Integer.parseInt(catId.getText().toString()));
            article.setAuthor(Integer.parseInt(authorId.getText().toString()));
            article.setImage(image.getText().toString());
            articleService.create(article)
                    .enqueue(new Callback<ArticleInsertResponse>() {
                        @Override
                        public void onResponse(Call<ArticleInsertResponse> call, Response<ArticleInsertResponse> response) {
                            Toast.makeText(AddArticleAvtivity.this, "created", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<ArticleInsertResponse> call, Throwable t) {
                            Toast.makeText(AddArticleAvtivity.this, "error", Toast.LENGTH_SHORT).show();
                        }
                    });


        });

    }
}
