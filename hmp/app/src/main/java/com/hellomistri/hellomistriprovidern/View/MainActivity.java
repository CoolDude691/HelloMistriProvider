package com.hellomistri.hellomistriprovidern.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hellomistri.hellomistriprovidern.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String add,ct;
    SharedPreferences shp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);


        getSupportActionBar().hide();
        shp = getApplicationContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (shp.contains("isLogin")) {


                    add = shp.getString("address","");
                    ct = shp.getString("city","");
                    if (add.isEmpty() && ct.isEmpty())
                    {
                        Intent intent = new Intent(MainActivity.this, NewRegPage.class);
                        startActivity(intent);
                        finish();
                    }
                    else {

                        Log.d("called_what","second_condition");
                        fetchUserDetails();

                    }


                } else {

                    if(Build.VERSION.SDK_INT>32){
                        if(askHomePermission(Manifest.permission.POST_NOTIFICATIONS) && askHomePermission(Manifest.permission.ACCESS_FINE_LOCATION) && askHomePermission(Manifest.permission.CAMERA)){
                            startActivity(new Intent(MainActivity.this,Login.class));
                            finish();
                        }else{
                            startActivity(new Intent(MainActivity.this,PermissionPageAct.class).putExtra("toWhere","login"));
                            finish();
                        }
                    }else{
                        if(askHomePermission(Manifest.permission.ACCESS_FINE_LOCATION) && askHomePermission(Manifest.permission.CAMERA) ){
                            startActivity(new Intent(MainActivity.this,Login.class));
                            finish();
                        }else{
                            startActivity(new Intent(MainActivity.this,PermissionPageAct.class).putExtra("toWhere","login"));
                            finish();
                        }
                    }

                }

            }
        },2500);

    }

    private  void fetchUserDetails(){
        String url="https://hellomistri.com/index/db/api/get_profile.php";
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("fetchUserDetails",response);
                try{
                    try{
                        JSONArray jsonArray=new JSONArray(response);
                        if(jsonArray.length()>0){
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject userObj= jsonArray.getJSONObject(i);
                                String paprove = userObj.getString("aprove");


                                    if(Build.VERSION.SDK_INT>32){
                                        if(askHomePermission(Manifest.permission.POST_NOTIFICATIONS) && askHomePermission(Manifest.permission.ACCESS_FINE_LOCATION) && askHomePermission(Manifest.permission.CAMERA) && askHomePermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                                            startActivity(new Intent(MainActivity.this,Home.class));
                                            finish();
                                        }else{
                                            startActivity(new Intent(MainActivity.this,PermissionPageAct.class).putExtra("toWhere","home"));
                                            finish();
                                        }
                                    }else{
                                        if(askHomePermission(Manifest.permission.ACCESS_FINE_LOCATION) && askHomePermission(Manifest.permission.CAMERA) && askHomePermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                                            startActivity(new Intent(MainActivity.this,Home.class));
                                            finish();
                                        }else{
                                            startActivity(new Intent(MainActivity.this,PermissionPageAct.class).putExtra("toWhere","home"));
                                            finish();
                                        }
                                    }

                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            protected Map<String,String> getParams(){
                Map<String,String> map=new HashMap<>();
                map.put("id",shp.getString("uid",""));
                return map;
            }
        };
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }
    private boolean askHomePermission(String permission){
        if(ContextCompat.checkSelfPermission(MainActivity.this, permission)!= PackageManager.PERMISSION_GRANTED){
            return false;
        }else{
            return true;
        }
    }
}