package com.rathana.rest_client_retrofit.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

public class ReqestPermissionHelper {

    public static void checkExternalStorage(Context context){
        if(ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE)!=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((AppCompatActivity)context,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},99);
        }

    }
}
