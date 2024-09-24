package com.hellomistri.hellomistriprovidern.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hellomistri.hellomistriprovidern.Adapter.MyAdapter;
import com.hellomistri.hellomistriprovidern.Model.Leads;
import com.hellomistri.hellomistriprovidern.R;
import com.hellomistri.hellomistriprovidern.RecyclerViewInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessingLead extends AppCompatActivity  implements RecyclerViewInterface {

    RecyclerView recyclerView;
    MyAdapter adapter;
    List<Leads>  leadsList = new ArrayList<>();    ImageView back;
    LinearLayout nodata;

    SharedPreferences shp;
    LinearLayoutManager linearLayoutManager;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processing_lead);

        getSupportActionBar().hide();



        Leads object= (Leads) getIntent().getSerializableExtra("device_object");

        Bundle bundle=new Bundle();

        bundle.putSerializable("deviceObject",object);

        recyclerView=findViewById(R.id.recyclerViewP);
        back=findViewById(R.id.backBtn1);
        nodata=findViewById(R.id.nodata4);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        recyclerView.setHasFixedSize(true);
        linearLayoutManager= new LinearLayoutManager(this);

        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter= new MyAdapter(leadsList, new MyAdapter.ItemClickListner() {
            @Override
            public void onItemClick(TextView textView, int pos) {
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ProcessingLead.this,ProcessingDet.class);
                        intent.putExtra("device_object",leadsList.get(pos));
                        startActivity(intent);
                        finish();

                    }
                });
            }

            @Override
            public void onItemSelection(View view, ConstraintLayout constraintLayout, int pos) {

            }
        });

        recyclerView.setAdapter(adapter);

        CallApi();
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


        String url="https://hellomistri.com/index/db/api/cancle_lead.php";
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                Log.d("leads",response);

                try {
                    JSONObject jsonObject0=new JSONObject(response);

                    if(jsonObject0.getBoolean("status")){
                        JSONArray leads= jsonObject0.getJSONArray("data");


                        for (int i=0;i<leads.length();i++)
                        {

                            JSONObject jsonObject= leads.getJSONObject(i);

                            String id = jsonObject.getString("id");
                            String customer_id = jsonObject.getString("customer_id");
                            String provider_id= jsonObject.getString("provider_id");
                            String c_atname = jsonObject.getString("c_atname");
                            String sub_cat_name = jsonObject.getString("sub_cat_name");
                            String service_name = jsonObject.getString("service_name");
                            String city_name = jsonObject.getString("city_name");
                            String message = jsonObject.getString("message");
                            String or_date = jsonObject.getString("or_date");
                            String o_status = jsonObject.getString("o_status");
                            String or_address = jsonObject.getString("or_address");
                            String latitude = jsonObject.getString("Lattitude");
                            String longitude = jsonObject.getString("Longitude");

                            Leads leads1=new Leads(id,customer_id,provider_id,c_atname,sub_cat_name,service_name,city_name,message,or_date,o_status,or_address,latitude,longitude);
                            leadsList.add(leads1);


                            if(!jsonObject.getString("customer_id").equals("null")) {
                                JSONObject jsonObject1 = jsonObject.getJSONObject("customer_id");
                                Log.d("JSON:", "" + jsonObject1);

                                String js=jsonObject1.getString("cname");
                                String jm=jsonObject1.getString("mobile");
                                leads1.setMobile(jm);

                                leads1.setCname(js);



                                shp = getApplicationContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor=shp.edit();




                            }


                            if(!jsonObject.getString("provider_id").equals("null")) {
                                JSONObject jsonObject2 = jsonObject.getJSONObject("provider_id");
                                Log.d("JSON:", "" + jsonObject2);

                                String pname = jsonObject2.getString("pname");

                                leads1.setPame(pname);

                            }

                            if(!jsonObject.getString("c_atname").equals("null")) {
                                JSONObject jsonObject2 = jsonObject.getJSONObject("c_atname");
                                Log.d("JSON:", "" + jsonObject2);

                                String cat_name = jsonObject2.getString("categoryname");

                                leads1.setCat_name(cat_name);

                            }

                            if(!jsonObject.getString("sub_cat_name").equals("null")) {
                                JSONObject jsonObject2 = jsonObject.getJSONObject("sub_cat_name");
                                Log.d("JSON:", "" + jsonObject2);

                                String subtitle = jsonObject2.getString("subtitle");

                                leads1.setSubtitle(subtitle);

                            }

                            if(!jsonObject.getString("child_cat_name").equals("null")) {
                                JSONObject jsonObject2 = jsonObject.getJSONObject("child_cat_name");
                                Log.d("JSON:", "" + jsonObject2);

                                String childtitle = jsonObject2.getString("childtitle");
                                leads1.setChildtitle(childtitle);

                            }




                        }
                        adapter.notifyDataSetChanged();
                    }





                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                map.put("status", "Processing");
                map.put("pid", proid);
                return map;
            }

        };
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);



    }







    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(ProcessingLead.this,LeadDetaills.class);
       // intent.putExtra("loc", String.valueOf(proLeadsList.get(position)));
        startActivity(intent);
        finish();

    }

    public void onBackPressed() {
        super.onBackPressed();

        Intent intent= new Intent(ProcessingLead.this,Home.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}