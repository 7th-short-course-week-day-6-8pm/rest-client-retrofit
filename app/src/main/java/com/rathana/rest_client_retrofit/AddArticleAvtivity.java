package com.rathana.rest_client_retrofit;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rathana.rest_client_retrofit.data.ServiceGenerator;
import com.rathana.rest_client_retrofit.data.service.ArticleService;
import com.rathana.rest_client_retrofit.model.Article;
import com.rathana.rest_client_retrofit.model.Category;
import com.rathana.rest_client_retrofit.model.param.ArticleParam;
import com.rathana.rest_client_retrofit.model.reponse.ArticleInsertResponse;
import com.rathana.rest_client_retrofit.model.reponse.UploadImageRepose;
import com.rathana.rest_client_retrofit.utils.ReqestPermissionHelper;

import java.io.File;
import java.net.URI;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddArticleAvtivity extends AppCompatActivity {


    EditText title , catId,authorId,desc,image;
    Button btnSave;
    ImageView imageView;
    String imageUrl;
    ArticleService articleService;
    ProgressBar progressBar;
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

        imageView=findViewById(R.id.imageView);
        progressBar=findViewById(R.id.progressBar);
        articleService= ServiceGenerator.createService(ArticleService.class);


        ReqestPermissionHelper.checkExternalStorage(this);

        btnSave.setOnClickListener(v->{
            ArticleParam article=new ArticleParam();
            article.setTitle(title.getText().toString());
            article.setDescription(desc.getText().toString());
            article.setCategoryId(Integer.parseInt(catId.getText().toString()));
            article.setAuthor(Integer.parseInt(authorId.getText().toString()));
            //article.setImage(image.getText().toString());
            article.setImage(imageUrl!=null ?imageUrl:"");
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

        imageView.setOnClickListener(v->{
            //create intent
            Intent intent=new Intent(Intent.ACTION_PICK);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
            startActivityForResult(intent,imageCode);
        });
    }
    static final int imageCode=101;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==imageCode && resultCode==RESULT_OK){
            try {
                Uri uri= data.getData();
                String[] columnInfo =  {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(
                        uri,
                        columnInfo,
                        null,
                        null,
                        null
                );

                cursor.moveToFirst();
                int columnIndex= cursor.getColumnIndex(columnInfo[0]);
                String fileName =cursor.getString(columnIndex);
                cursor.close();
                Log.e("pickImage", "fileName: "+fileName );

                Bitmap bitmap= BitmapFactory.decodeFile(fileName);
                imageView.setImageBitmap(bitmap);

                //upload to server
                uploadImage(fileName);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    private static final String TAG = "AddArticleAvtivity";
    //upload image to server
    private void uploadImage(String fileName){
        //Log.e(TAG, "uploadImage: ");
        MultipartBody.Part image = createMultiPartBody(fileName);
        //Log.e(TAG, "uploadImage: ");
        Call<UploadImageRepose> call=null;
        call = articleService.uploadImage(image);
        Log.e(TAG, "uploadImage: ");
        progressBar.setVisibility(View.VISIBLE);
        //Log.e(TAG, "uploadImage: ");
        call.enqueue(new Callback<UploadImageRepose>() {
            @Override
            public void onResponse(Call<UploadImageRepose> call,
                                   Response<UploadImageRepose> response) {

                imageUrl=response.body().getImageUrl();
                Log.e(TAG, "onResponse: "+imageUrl);
                Log.e(TAG, "onResponse: "+response.code());

                Toast.makeText(AddArticleAvtivity.this, "success", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<UploadImageRepose> call, Throwable t) {
                Toast.makeText(AddArticleAvtivity.this, ""+t.toString(),
                        Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: "+t.getMessage() );
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    //create multiPartBody.Part
    private MultipartBody.Part createMultiPartBody(String fileName){
        Uri uri= Uri.parse(fileName);
        File file = new File(uri.getPath());
        //RequestBody requestBody=RequestBody.create(MediaType.parse("multipart/form-data"),file);
        RequestBody body=RequestBody.create(MediaType.parse("multipart/form-data"),file);
        return  MultipartBody.Part.createFormData(
                "FILE",file.getName(),body
        );
    }


}
