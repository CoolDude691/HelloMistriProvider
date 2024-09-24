package com.hellomistri.hellomistriprovidern.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hellomistri.hellomistriprovidern.R;

public class PermissionPageAct extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    CheckBox locationRadio,notificationRadio,cameraRadio,galleryRadio;
    SharedPreferences sharedPreferences;
    String toWhere="";
    LinearLayout notificationLayout;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_page);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.parseColor("#ffffff"));

        floatingActionButton=findViewById(R.id.nextfloat);
        locationRadio=findViewById(R.id.locationRadio);
        notificationLayout=findViewById(R.id.notificationPermissionLayout);
        notificationRadio=findViewById(R.id.notifRadio);
        cameraRadio=findViewById(R.id.cameraRadio);
        galleryRadio=findViewById(R.id.galleryRadio);


        if(Build.VERSION.SDK_INT>32){
            notificationLayout.setVisibility(View.VISIBLE);
        }


        Intent intent= getIntent();
        toWhere= intent.getStringExtra("toWhere");

        sharedPreferences=getSharedPreferences("MySharedPref",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        int locround=sharedPreferences.getInt("locround",0);
        int notifRound= sharedPreferences.getInt("notifRound",0);
        int cameraRound= sharedPreferences.getInt("cameraRound",0);
        int galleryRound= sharedPreferences.getInt("galleryRound",0);


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED ){locationRadio.setChecked(true);}
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.POST_NOTIFICATIONS)==PackageManager.PERMISSION_GRANTED){notificationRadio.setChecked(true);}
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED){cameraRadio.setChecked(true);}
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){galleryRadio.setChecked(true);}


        locationRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sharedPreferences.getInt("locround",0)>=1 && ContextCompat.checkSelfPermission(PermissionPageAct.this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED ){
                    Intent intent=new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Log.d("ifcondn",String.valueOf(sharedPreferences.getInt("locround",0)));
                    Uri uri=Uri.fromParts("package",getPackageName(),null);
                    intent.setData(uri);
                    startActivity(intent);
                }else{
                    if(locationRadio.isChecked()){
                        locationRadio.setChecked(false);
                        Log.d("elsecondn",String.valueOf(sharedPreferences.getInt("locround",0)));

                        ActivityCompat.requestPermissions(PermissionPageAct.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},111);
                        editor.putInt("locround",locround+1);
                        editor.commit();
                    }else{
                        locationRadio.setChecked(true);
                    }
                }

            }
        });
        notificationRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sharedPreferences.getInt("notifRound",0)>=1 && ContextCompat.checkSelfPermission(PermissionPageAct.this,Manifest.permission.POST_NOTIFICATIONS)!=PackageManager.PERMISSION_GRANTED){
                    Intent intent=new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Log.d("ifcondn",String.valueOf(sharedPreferences.getInt("notifRound",0)));
                    Uri uri=Uri.fromParts("package",getPackageName(),null);
                    intent.setData(uri);
                    startActivity(intent);
                }else{
                    if(notificationRadio.isChecked()){
                        notificationRadio.setChecked(false);
                        ActivityCompat.requestPermissions(PermissionPageAct.this,new String[]{Manifest.permission.POST_NOTIFICATIONS},112);
                        editor.putInt("notifRound",notifRound+1);
                        editor.commit();
                    }else{
                        notificationRadio.setChecked(true);
                    }
                }
            }
        });
        cameraRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sharedPreferences.getInt("cameraRound",0)>=1 && ContextCompat.checkSelfPermission(PermissionPageAct.this,Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
                    Intent intent=new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Log.d("ifcondn",String.valueOf(sharedPreferences.getInt("cameraRound",0)));
                    Uri uri=Uri.fromParts("package",getPackageName(),null);
                    intent.setData(uri);
                    startActivity(intent);
                }else{
                    if(cameraRadio.isChecked()){
                        cameraRadio.setChecked(false);
                        Log.d("camera","second_conditon");
                        ActivityCompat.requestPermissions(PermissionPageAct.this,new String[]{Manifest.permission.CAMERA},113);
                        editor.putInt("cameraRound",cameraRound+1);
                        editor.commit();
                    }else{
                        cameraRadio.setChecked(true);
                    }
                }
            }
        });
        galleryRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sharedPreferences.getInt("galleryRound",0)>=1 && ContextCompat.checkSelfPermission(PermissionPageAct.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                    Intent intent=new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Log.d("ifcondn",String.valueOf(sharedPreferences.getInt("galleryRound",0)));
                    Uri uri=Uri.fromParts("package",getPackageName(),null);
                    intent.setData(uri);
                    startActivity(intent);
                }else{
                    if(galleryRadio.isChecked()){
                        galleryRadio.setChecked(false);
                        ActivityCompat.requestPermissions(PermissionPageAct.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},114);
                        editor.putInt("galleryRound",galleryRound+1);
                        editor.commit();
                    }else{
                        galleryRadio.setChecked(true);
                    }
                }
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }
        });

    }
    private void check() {
        if(Build.VERSION.SDK_INT>32){
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.POST_NOTIFICATIONS)==PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED){
                proceedToApp();
            }else{
                Toast.makeText(this, "Allow these Permissions first", Toast.LENGTH_SHORT).show();
            }
        }else{
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED){
                proceedToApp();
            }else{
                Toast.makeText(this, "Allow these Permissions first", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void proceedToApp(){
        if(toWhere.equals("login")){
            startActivity(new Intent(PermissionPageAct.this,Login.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }else if(toWhere.equals("home")){
            startActivity(new Intent(PermissionPageAct.this,Home.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==111){
            if(grantResults.length==0 || grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                Log.d("user","User denied");
            }else{
                locationRadio.setChecked(true);
            }
        }else if(requestCode==112){
            if(grantResults.length==0 || grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                Log.d("notif","user denied");
            } else{
                notificationRadio.setChecked(true);
            }
        }else if(requestCode==113){
            if(grantResults.length==0 || grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                Log.d("camera","user_denied");
            }else{
                cameraRadio.setChecked(true);
            }
        }else if(requestCode==114){
            if(grantResults.length==0 || grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                Log.d("camera","user_denied");
            }else{
                galleryRadio.setChecked(true);
            }
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if(ContextCompat.checkSelfPermission(PermissionPageAct.this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            locationRadio.setChecked(false);
        }else{
            locationRadio.setChecked(true);
        }
        if(ContextCompat.checkSelfPermission(PermissionPageAct.this,Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
            cameraRadio.setChecked(false);
        }else{
            cameraRadio.setChecked(true);
        }
        if(ContextCompat.checkSelfPermission(PermissionPageAct.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            galleryRadio.setChecked(false);
        }else{
            galleryRadio.setChecked(true);
        }
        if(Build.VERSION.SDK_INT>32) {
            if (ContextCompat.checkSelfPermission(PermissionPageAct.this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                notificationRadio.setChecked(false);
            }else{
                notificationRadio.setChecked(true);
            }

        }
    }

}