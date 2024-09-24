package com.hellomistri.hellomistriprovidern.View;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.hellomistri.hellomistriprovidern.R;

import java.util.HashMap;
import java.util.Map;

public class RegisterDet extends AppCompatActivity {
    TextInputEditText category,address,city,distance,aadhar,aa,servicecat,km;

    SharedPreferences shp;

    Button button;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_det);

        category= findViewById(R.id.userName);
        city= findViewById(R.id.userPhone);
        address= findViewById(R.id.userEmail);
       distance= findViewById(R.id.userAddress);
     //   aadhar= findViewById(R.id.userPincode);
        button=findViewById(R.id.btn_update);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallApi();
            }
        });


    /*    servicecat= findViewById(R.id.userSubCtegory);
        front = findViewById(R.id.img_aaback1);
        back=findViewById(R.id.img_aaback);*/
    }



    private void CallApi() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();


        shp = getApplicationContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=shp.edit();

/*
     String cid= String.valueOf(1);
*/


        String proid = shp.getString("uid", "");
        String category_id = shp.getString("category_id", "");

        String name1 = category.getText().toString();
        String phone1 =city .getText().toString();
        String email1 = address.getText().toString();
        String address1 = distance.getText().toString();
      //  String pincode1 = aadhar.getText().toString();
     //   String Service1 = servicecat.getText().toString();
       // String km1  = km.getText().toString();
      //  String aadhar = aa.getText().toString();


        //Toast.makeText(this, ""+category_id, Toast.LENGTH_SHORT).show();

        String url="https://hellomistri.com/index/db/api/update_reg.php";
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();



                Log.d("leads",response);
               // Toast.makeText(Edit_User.this, ""+response, Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        }){
            protected Map<String,String> getParams() {
                Map<String, String> map = new HashMap<>();
//               Log.d("cakeon",""+cid);


                map.put("address", email1);
                map.put("city", phone1);
                map.put("distance", "232323");
                map.put("aadharno", "345678");
                map.put("aback", "jjj.jpg");
                map.put("afront", "jjj.jpg");
                map.put("mobile", "9500");
                map.put("cid", "1");
                return map;
            }

        };
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);



    }

}