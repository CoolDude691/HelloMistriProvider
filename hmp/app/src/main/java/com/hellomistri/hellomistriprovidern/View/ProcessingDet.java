package com.hellomistri.hellomistriprovidern.View;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

public class ProcessingDet extends AppCompatActivity {
    TextView location,call,txttitle,time,Day,ss,aboutser,Accept,Can,txtMobile,add_copy,subc,child,service;
    SharedPreferences shp;
    String pid ,userNumber;
    String orId;
    ClipboardManager clipboardManager ;

    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processing_det);
        location= findViewById(R.id.txt_location);
        call=findViewById(R.id.txt_call);
        txtMobile = findViewById(R.id.txtMobile);
        txtMobile.setVisibility(View.VISIBLE);
        ss = findViewById(R.id.serv);
        aboutser = findViewById(R.id.abtsrv);
        Accept = findViewById(R.id.accept);
        Can = findViewById(R.id.Cancel);
        add_copy = findViewById(R.id.copy_add);
        subc = findViewById(R.id.subcat);
        child = findViewById(R.id.cc);
        service = findViewById(R.id.cc1);





        Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallApi();
              //  Toast.makeText(ProcessingDet.this, "Completed", Toast.LENGTH_SHORT).show();
            }
        });

        Can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            Cancel();
            //   Toast.makeText(ProcessingDet.this, "Cancelled", Toast.LENGTH_SHORT).show();

            }
        });

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
        txtMobile.setText(""+object.getMobile());
        location.setText(""+object.getOr_address());
        time.setText(""+object.getOr_date());
//         Day.setText(""+object.getTime());
        ss.setText(""+object.getCat_name());
        aboutser.setText(""+object.getMessage());
        subc.setText(""+object.getSubtitle());
        child.setText(""+object.getChildtitle());
        service.setText(""+object.getService_name());

        String longitud = object.getLongitude();
        String latitud = object.getLattitude();

        userNumber=object.getMobile();

        add_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri gmmIntentUri = Uri.parse("google.navigation:q="+latitud+","+longitud+"&mode=l");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
               /* clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData;

                String add = location.getText().toString();
                clipData = ClipData.newPlainText("text",add);
               clipboardManager.setPrimaryClip(clipData);*/

             //   Toast.makeText(ProcessingDet.this, "Copied"+""+add, Toast.LENGTH_SHORT).show();
            }
        });








        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+userNumber));//change the number
                startActivity(callIntent);

            }
        });

        /*Leads object= (Leads) getIntent().getSerializableExtra("leaddet");

        txttitle.setText(""+object.getName());*/


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

        String url="https://hellomistri.com/Api/Statusupdatecomplete";
        RequestQueue requestQueue= Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("BookNow",""+response);
                progressDialog.dismiss();
                Toast.makeText(ProcessingDet.this, "Completed", Toast.LENGTH_SHORT).show();

                //  Toast.makeText(BookNow.this, ""+response, Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(ProcessingDet.this,CompletedLead.class);
                startActivity(intent);
                finish();


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
                map.put("orderid", orId);

                // Toast.makeText(LeadDetaills.this, ""+orId, Toast.LENGTH_SHORT).show();
                return map;
            }
        };

        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();


    }


    private void Cancel() {

        SharedPreferences sharedPreferences=getSharedPreferences("MySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        String uid = sharedPreferences.getString("uid", "");
        String oid = sharedPreferences.getString("orderid", "");

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String url="https://hellomistri.com/Api/Statusupdatecancle";
        RequestQueue requestQueue= Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("BookNow",""+response);
                progressDialog.dismiss();


                Toast.makeText(ProcessingDet.this, "Cancelled", Toast.LENGTH_SHORT).show();

                //  Toast.makeText(BookNow.this, ""+response, Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(ProcessingDet.this,CancledLead.class);
                startActivity(intent);


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
                map.put("orderid", orId);

                // Toast.makeText(LeadDetaills.this, ""+orId, Toast.LENGTH_SHORT).show();
                return map;
            }
        };

        requestQueue.add(stringRequest);
        requestQueue.getCache().clear();


    }

    public void onBackPressed() {
        super.onBackPressed();

        Intent intent= new Intent(ProcessingDet.this,Home.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }



}