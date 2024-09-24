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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompletedLead extends AppCompatActivity  {

    final public
    List<Leads>  leadsList = new ArrayList<>();
    ImageView back;
    RecyclerView recyclerView;
    MyAdapter adapter;
    SharedPreferences shp;

    LinearLayoutManager linearLayoutManager;
    LinearLayout nodata;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_lead);
       back= findViewById(R.id.backBtn);
        nodata=findViewById(R.id.nodata2);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        recyclerView=findViewById(R.id.recyclerViewC);
        getSupportActionBar().hide();

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
                        Intent intent = new Intent(CompletedLead.this,CompDet.class);
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

/*
    private void CallApi() {




        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();


        shp = getApplicationContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=shp.edit();
        String  cid = shp.getString("category_id","");
        String uid = shp.getString("uid","");

        String url="https://hellomistri.com/provider_api/CompleteLead.php?uid=3918&cid=1&status=Completed";
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();


                try {

                    JSONArray leads= new JSONArray(response);

                    for (int i=0;i<leads.length();i++)

                    {

                        if (leads.length()==0)
                        {

                            nodata.setVisibility(View.VISIBLE);

                        }

                        JSONObject jsonObject= leads.getJSONObject(i);


                        String id = jsonObject.getString("id");
                        String address= jsonObject.getString("address");
                        String odate = jsonObject.getString("odate");
                        String time = jsonObject.getString("time");
                        String o_status = jsonObject.getString("o_status");

                        // String status = jsonObject.getString("status");

                        ModelComplete modelComplete = new ModelComplete(id,address,odate,time,o_status);
                        completeAdapterList.add(modelComplete);

                    }
                    adapter.notifyDataSetChanged();


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
                Log.d("cakeon",""+cid +" " +uid);
                map.put("cid", cid);
                map.put("id", uid);
                map.put("status", "Completed");
                return map;
            }
            };

        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);



    }
*/

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



        String url="https://hellomistri.com/Api/getUserpartnerServicedetails";
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                Log.d("leads",response);

                try {


                    JSONArray leads= new JSONArray(response);


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
                        String  lattitude = jsonObject.getString("Lattitude");
                        String  longitude = jsonObject.getString("Longitude");




                      /*  editor.putString("orderid",jsonObject.getString("id"));
                        editor.commit();*/

                        // String status = jsonObject.getString("status");

                        Leads leads1=new Leads(id,customer_id,provider_id,c_atname,sub_cat_name,service_name,city_name,message,or_date,o_status,or_address,longitude,lattitude);
                        leadsList.add(leads1);

                        leads1.setLattitude(lattitude);
                        leads1.setLongitude(longitude);


                        if(!jsonObject.getString("customer_id").equals("null")) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject("customer_id");
                            Log.d("JSON:", "" + jsonObject1);

                            String js=jsonObject1.getString("cname");

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
                map.put("status", "Completed");
                map.put("pid", proid);
                return map;
            }

        };
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);



    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent= new Intent(CompletedLead.this,Home.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }


}