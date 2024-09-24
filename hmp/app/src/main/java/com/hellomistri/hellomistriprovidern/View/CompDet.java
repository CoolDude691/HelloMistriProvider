package com.hellomistri.hellomistriprovidern.View;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hellomistri.hellomistriprovidern.Model.Leads;
import com.hellomistri.hellomistriprovidern.R;

import java.util.HashMap;
import java.util.Map;

public class CompDet extends AppCompatActivity {
    TextView location,call,txttitle,time,Day,ss,aboutser,Accept,txtMobile,add_copy,subc,child,service;
    SharedPreferences shp;
    String pid;
    String orId;

    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comp_det);
        location= findViewById(R.id.txt_location);
        call=findViewById(R.id.txt_call);
        ss = findViewById(R.id.serv);
        aboutser = findViewById(R.id.abtsrv);
        Accept = findViewById(R.id.accept);

        add_copy = findViewById(R.id.copy_add);
        subc = findViewById(R.id.subcat);
        child = findViewById(R.id.cc);
        service = findViewById(R.id.cc1);
        txtMobile = findViewById(R.id.txtMobile);

      /*  Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallApi();
            }
        });*/

        txttitle=findViewById(R.id.txt_title);
        time = findViewById(R.id.txt_time);

        Day = findViewById(R.id.txt_date);

        // CallApi();

        Leads object= (Leads) getIntent().getSerializableExtra("device_object");


        orId = object.getId();

      /*  shp = getApplicationContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=shp.edit();
        String uid1 = shp.getString("name", "");*/

        txttitle.setText(""+object.getCname());
        location.setText(""+object.getOr_address());
        time.setText(""+object.getOr_date());
        ss.setText(""+object.getService_name());
        aboutser.setText(""+object.getMessage());
        subc.setText(""+object.getSubtitle());
        child.setText(""+object.getChildtitle());
        service.setText(""+object.getService_name());




        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number= String.valueOf(123);

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+number));
                startActivity(callIntent);
                finish();

            }
        });




    }

    private void CallApi() {

        SharedPreferences sharedPreferences=getSharedPreferences("MySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        String uid = sharedPreferences.getString("uid", "");
        String oid = sharedPreferences.getString("orderid", "");

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String url="https://hellomistri.com/Api/orderStatusupdate";
        RequestQueue requestQueue= Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("BookNow",""+response);
                progressDialog.dismiss();
             //   Toast.makeText(CompDet.this, ""+response, Toast.LENGTH_SHORT).show();

                //  Toast.makeText(BookNow.this, ""+response, Toast.LENGTH_SHORT).show();





            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            protected Map<String,String> getParams() {
                Map<String, String> map = new HashMap<>();
//               Log.d("cakeon",""+cid);
                map.put("partnerid",uid );
                map.put("orderid", oid);

                // Toast.makeText(LeadDetaills.this, ""+orId, Toast.LENGTH_SHORT).show();
                return map;
            }
        };

        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();


    }


    public void onBackPressed() {
        super.onBackPressed();

        Intent intent= new Intent(CompDet.this,Home.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}