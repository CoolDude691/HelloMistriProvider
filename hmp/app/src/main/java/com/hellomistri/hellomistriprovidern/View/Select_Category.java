package com.hellomistri.hellomistriprovidern.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hellomistri.hellomistriprovidern.Adapter.AdaprAmount;
import com.hellomistri.hellomistriprovidern.Adapter.CategoryAdapter;
import com.hellomistri.hellomistriprovidern.Model.Select_Category_Model;
import com.hellomistri.hellomistriprovidern.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Select_Category extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Select_Category_Model> catList= new ArrayList<>();
    CategoryAdapter adapter;
    private Spinner spinner;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category);


        recyclerView=findViewById(R.id.recycleview);
        getSupportActionBar().hide();
        spinner = findViewById(R.id.spinnerNew);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter= new CategoryAdapter(catList);
        recyclerView.setAdapter(adapter);

        fetchCategory();

    }

    private void fetchCategory() {


        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();


        String url ="https://hellomistri.com/provider_api/fetchCategory.php";
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                //    Log.d("responseDeviceDetails",response);
                try {

                    JSONArray leads= new JSONArray(response);

                    for (int i=0;i<leads.length();i++)

                    {

                        JSONObject jsonObject= leads.getJSONObject(i);

                         String id = jsonObject.getString("id");
                         String cat_subtitle = jsonObject.getString("cat_subtitle");
                         String cat_name = jsonObject.getString("cat_name");
                         String description = jsonObject.getString("description");
                         String cat_status =  jsonObject.getString("cat_status");
                         String cat_img = jsonObject.getString("cat_img");
                         String cat_video = jsonObject.getString("cat_video");
                         String cat_icon = jsonObject.getString("cat_icon");
                         String catbanner = jsonObject.getString("catbanner");

                         Select_Category_Model model= new Select_Category_Model
                                 (id,cat_subtitle,cat_name,description,cat_status,cat_img,cat_video,cat_icon,catbanner);
                           catList.add(model);



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
        });
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);



    }



}