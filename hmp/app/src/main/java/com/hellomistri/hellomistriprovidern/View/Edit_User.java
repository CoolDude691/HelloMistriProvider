package com.hellomistri.hellomistriprovidern.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.hellomistri.hellomistriprovidern.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Edit_User extends AppCompatActivity {

    ImageView imgProfile;

    FusedLocationProviderClient fusedLocationProviderClient;
    TextInputEditText name,phone,email,address,pincode,aa,servicecat,km;
     Button update;

     String Longitude,Lattitude;
     TextView ProviderId;
    String pcity, pcategory;
     ImageView front,back;

    SharedPreferences shp;

    private  final  static int REQUEST_CODE=100;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_edit_user);

        name= findViewById(R.id.userName);
        phone= findViewById(R.id.userPhone);
        email= findViewById(R.id.userEmail);
        address= findViewById(R.id.userAddress);
        pincode= findViewById(R.id.userPincode);
        servicecat= findViewById(R.id.userSubCtegory);
        front = findViewById(R.id.img_aaback1);
        back=findViewById(R.id.img_aaback);
        ProviderId = findViewById(R.id.ProviderId);
        imgProfile = findViewById(R.id.provider_profile_image);
        update=findViewById(R.id.btn_update);
        aa= findViewById(R.id.aadhar);
        km= findViewById(R.id.userDis);
        shp = getApplicationContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=shp.edit();

        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
//        getLastLocation();



        String aadf = shp.getString("afront","");
        String aadb = shp.getString("aback","");






        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CallApi();
                Intent intent = new Intent(Edit_User.this,EditPropUpdate.class);
                startActivity(intent);
                /*finish();*/
            }
        });

        String name1 = shp.getString("name", "");
        String email1 = shp.getString("email", "");
        String mobileq = shp.getString("mobile", "");
        String address1 = shp.getString("address", "");
        String pincq = shp.getString("pincode", "");
        String scq = shp.getString("subcategory", "");
        String aad = shp.getString("adharno", "");
        String liko = shp.getString("distance", "");



       // Toast.makeText(this, ""+liko, Toast.LENGTH_SHORT).show();
        getProDetails();

    }


    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(Edit_User.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent=new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,"image"),111);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);





    }




    private void getProDetails() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
        SharedPreferences shp = Edit_User.this.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=shp.edit();
        String proid = shp.getString("uid", "");

        String url="https://hellomistri.com/index/db/api/get_profile.php";
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.d("profile_details",response);


                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String pid = jsonObject.getString("id");
                        String pname = jsonObject.getString("name");
                        String pemail = jsonObject.getString("email");
                        String pmobile = jsonObject.getString("mobile");
                        pcategory = jsonObject.getString("category_id");
                        String paddress = jsonObject.getString("address");
                        pcity = jsonObject.getString("city");
                        String pccode = jsonObject.getString("ccode");
                        String padharno = jsonObject.getString("adharno");
                        String paback = jsonObject.getString("aback");
                        String pafront = jsonObject.getString("afront");
                        String paprove = jsonObject.getString("aprove");
                        String pimg = jsonObject.getString("pimg");
                        String pdis = jsonObject.getString("distance");


                        if (paprove.equals("0")){

                            update.setVisibility(View.VISIBLE);

                        }else
                        {

                            update.setVisibility(View.GONE);
                        }

                        getProCity();
                        getProCategroy();
                        ProviderId.setText("HMP"+pid);
                        name.setText(pname);
                        email.setText(pemail);
                        phone.setText(pmobile);
                        address.setText(paddress);
                        aa.setText(padharno);
                        km.setText(pdis);
                        String url1="https://hellomistri.com/index/db/api/uploads/";
                        Glide.with(Edit_User.this).load(url1+pafront).into(back);
                        Glide.with(Edit_User.this).load(url1+paback).into(front);


                        if (!pimg.equals(null))
                        {
                            String url12="https://hellomistri.com/index/db/api/uploads/";
                            Glide.with(imgProfile).load(url12+pimg).into(imgProfile);
                        }
                        else {
                            Toast.makeText(Edit_User.this, "Profile not Available", Toast.LENGTH_SHORT).show();
                        }


                      //  Toast.makeText(Edit_User.this, "" + pimg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
//                    throw new RuntimeException(e);
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
                map.put("id",proid );

                return map;
            }

        };
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);

    }



    private void getProCity() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
        SharedPreferences shp = Edit_User.this.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=shp.edit();
        String proid = shp.getString("uid", "");

        String url="https://hellomistri.com/index/db/api/get_city.php";
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();



                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String pcid = jsonObject.getString("id");
                        String pcname = jsonObject.getString("cname");

                        pincode.setText(pcname);
                       // Toast.makeText(Edit_User.this, ""+pcname, Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
              //      throw new RuntimeException(e);
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
                map.put("id",pcity );

                return map;
            }

        };
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);

    }


    private void getProCategroy() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
        SharedPreferences shp = Edit_User.this.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=shp.edit();
        String proid = shp.getString("uid", "");

        String url="https://hellomistri.com/index/db/api/get_cat.php";
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();



                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String pcid = jsonObject.getString("id");
                        String cat_name = jsonObject.getString("cat_name");
                        servicecat.setText(cat_name);
                       // Toast.makeText(Edit_User.this, ""+pcname, Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                 //   throw new RuntimeException(e);
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
                map.put("id",pcategory );

                return map;
            }

        };
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);

    }

    /*private void getLastLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location !=null) {
                                Geocoder geocoder=new Geocoder(Edit_User.this, Locale.getDefault());
                                List<Address> addresses= null;
                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                    *//*      latitude.setText("Lagitude :" +addresses.get(0).getLatitude());
                                    longitude.setText("Longitude :"+addresses.get(0).getLongitude());*//*

                                    Lattitude = String.valueOf(location.getLatitude());
                                    Longitude = String.valueOf(location.getLongitude());

                                    address.setText(""+addresses.get(0).getAddressLine(0));
                                    // addressView.setText("Address :"+addresses.get(0).getAddressLine(0));



                                } catch (IOException e) {

                                    e.printStackTrace();

                                }
                            }
                        }
                    });
        }
        else{

            askPermission();

        }
    }*/


    private void askPermission() {
        ActivityCompat.requestPermissions(Edit_User.this, new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent= new Intent(Edit_User.this,Home.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }


}