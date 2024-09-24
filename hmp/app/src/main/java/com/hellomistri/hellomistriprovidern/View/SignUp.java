package com.hellomistri.hellomistriprovidern.View;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.hellomistri.hellomistriprovidern.Model.City;
import com.hellomistri.hellomistriprovidern.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SignUp extends AppCompatActivity {

    Button signUp,Getotp,SignIn;
    String getName,getEmail,getMobile,getOtp;
    String send_otp;
    EditText userName;
    EditText userEmail;
    EditText userMobile;
    EditText regotp;

    Spinner spinner;

    TextView cite;

    ArrayAdapter<String> cityAdapter;
    String inputOtp,inputUserName,inputUserEmail,inputUserNumber ;
    TextView ename,eEmail,enumber;
    LinearLayout registerotptext;
    SharedPreferences shp;

    ArrayList<String> cityList= new ArrayList<>();
    List<City> cityTypeList=new ArrayList<>();
    String cityId="";



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();
        SignIn = findViewById(R.id.signIn);
        signUp=findViewById(R.id.signUp);
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        userMobile = findViewById(R.id.userMobile);
        registerotptext=findViewById(R.id.registerotptext);
        regotp=findViewById(R.id.regotp);
        spinner=findViewById(R.id.spin);
        Getotp=findViewById(R.id.signUp1);
        ename = findViewById(R.id.eName);
        eEmail = findViewById(R.id.eEmail);
        enumber = findViewById(R.id.eNumber);
        cite = findViewById(R.id.Slecit);
        shp = getApplicationContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);


        cityList.add("Select City");

        getCities();


        cityAdapter=new ArrayAdapter<String>(SignUp.this, android.R.layout.simple_list_item_1,cityList);
        cityAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(cityAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                }else{
                    String item = adapterView.getItemAtPosition(i).toString();
                    for(int j=0;j<cityTypeList.size();j++){
                        if(String.valueOf(cityTypeList.get(j).getCname()).equals(item)){
                            Log.d("cityId",cityTypeList.get(j).getCname()+" "+cityTypeList.get(j).getId());
                            cityId=cityTypeList.get(j).getId();
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Getotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkAllFeilds())

                {
                    inputUserName = userName.getText().toString();
                    inputUserEmail = userEmail.getText().toString();
                    inputUserNumber = userMobile.getText().toString();

                    /*inputOtp = regotp.getText().toString();*/


                        registrationOtp(inputUserName,inputUserEmail,inputUserNumber);


                    /*getMobile = userMobile.getText().toString();
                    final Random myRandom = new Random();
                    getOtp= String.format("%04d", myRandom.nextInt(10000));
                    getOtp(getMobile,getOtp);

                    registerotptext.setVisibility(View.VISIBLE);
                    spinner.setVisibility(View.GONE);
                    cite.setVisibility(View.GONE);
                    ename.setVisibility(View.GONE);
                    eEmail.setVisibility(View.GONE);
                    enumber.setVisibility(View.GONE);
                    userName.setVisibility(View.GONE);
                    userMobile.setVisibility(View.GONE);
                    userEmail.setVisibility(View.GONE);
                    Getotp.setVisibility(View.GONE);
                    signUp.setVisibility(View.VISIBLE);*/// important

                }

            }
        });
        signUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                inputOtp= regotp.getText().toString();

                Log.d("both_otps",inputOtp+"  "+getOtp);
                if(inputOtp.equals(getOtp)){
                    updateOtp(shp.getString("edited_mobile",""),getOtp);

                }

            }
        });

    }


    private boolean checkAllFeilds() {


        if (userName.length() == 0) {
            userName.setError("Name is required");
            return false;
        }
        if (userEmail.length() == 0) {
            userEmail.setError("Email is required");
            return false;
        }

        if (userMobile.length() == 0) {
            userMobile.setError("Phone number is required");
            return false;
        }

        if(cityId.isEmpty()){
            Toast.makeText(this, "Please select your city.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    private void registrationOtp(String name,String email,String phone) {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Log.d("registerationOtp",cityId);

        SharedPreferences.Editor editor=shp.edit();

        String url="https://hellomistri.com/index/db/api/registration.php";
        RequestQueue requestQueue= Volley.newRequestQueue(this);

        StringRequest stringRequest=new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.d("registerationRes",response);

                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getBoolean("status")){

                        editor.putString("edited_mobile",phone);
                        editor.apply();

                        final Random myRandom = new Random();
                        getOtp= String.format("%04d", myRandom.nextInt(10000));
                        Log.d("registerationRes",phone+" " +getOtp);
                        getOtp(phone,getOtp);

                        registerotptext.setVisibility(View.VISIBLE);
                        spinner.setVisibility(View.GONE);
                        cite.setVisibility(View.GONE);
                        ename.setVisibility(View.GONE);
                        eEmail.setVisibility(View.GONE);
                        enumber.setVisibility(View.GONE);
                        userName.setVisibility(View.GONE);
                        userMobile.setVisibility(View.GONE);
                        userEmail.setVisibility(View.GONE);
                        Getotp.setVisibility(View.GONE);
                        signUp.setVisibility(View.VISIBLE);
                    }else{
                        Toast.makeText(SignUp.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }




                /*Intent intent = new Intent(SignUp.this, NewRegPage.class);
                startActivity(intent);
                finish();*/
                /*if (response.equals("Success")){

                SharedPreferences preferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("inputUserNumber", String.valueOf(inputUserNumber));
                editor.commit();
                Toast.makeText(SignUp.this, ""+response, Toast.LENGTH_SHORT).show();
                *//*  Intent intent = new Intent(SignUp.this, NewRegPage.class);
                startActivity(intent);*//*

                }
                else {

                 *//*   Toast.makeText(SignUp.this, ""+response, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUp.this, SignIn.class);
                    startActivity(intent);*//*
                }*/

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){

            protected Map<String,String> getParams(){

                Map<String,String> map=new HashMap<>();
                map.put("name", inputUserName);
                map.put("email", inputUserEmail);
                map.put("mobile", inputUserNumber);
                map.put("otp","1234");
                map.put("city",cityId);
                return map;
            }
        };
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);



    }

    private void updateOtp(String phoneNumber,String otpValue) {


        /*ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();*/

        String url="https://hellomistri.com/index/db/api/update_otp_pid.php";
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                progressDialog.dismiss();

                Log.d("updatedOtp",response);

                Intent intent = new Intent(SignUp.this, NewRegPage.class);
                startActivity(intent);
                finish();

               /* Log.d("responseDeviceDetails",response);

                SharedPreferences preferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.putString("name", String.valueOf(userName));
                editor.putString("email", String.valueOf(userEmail));
                editor.putString("mobile", String.valueOf(userMobile));
                editor.commit();

                String msg = "Success";
                if (response.equals(msg)){
                    Intent intent = new Intent(SignUp.this, NewRegPage.class);
                    startActivity(intent);
                }*/

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            protected Map<String,String> getParams(){
                Map<String,String> map=new HashMap<>();
                // Log.d("cakeon",""+email +" " +password);
                map.put("mobile", phoneNumber);
                map.put("otp",otpValue);
                return map;
            }
        };
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);



    }


    private void getOtp(String getMobile, String getOtp) {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String url="https://smsinteract.in/SMSApi/send?userid=hellomistri&password=6y2xVjjA&sendMethod=quick&mobile="+ getMobile +"&msg=OTP to verify your Hello Mistri Account is "+ getOtp +" and valid till 2 minutes. Do not share this OTP to anyone for security reasons. - Team www.hellomistri.com&senderid=HMSTRI&msgType=text&duplicatecheck=true&format=text";
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                Log.d("responseDeviceDetails",response);


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

    private void getCities() {




        String url="https://hellomistri.com/index/db/city.php";
        RequestQueue requestQueue= Volley.newRequestQueue(this);


        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("getCities",response);

                //    Toast.makeText(ServiceBooking.this, ""+response, Toast.LENGTH_SHORT).show();

                try {
                    JSONArray jsonArray= new JSONArray(response);

                    Gson gson=new Gson();
                    for (int i=0;i<jsonArray.length();i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        /*SharedPreferences shp = getApplication().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=shp.edit();
                        String id = jsonObject.getString("id");
                        String cname = jsonObject.getString("cname");

                        editor.putString("cnn",cname);
                        editor.commit();

                        // Toast.makeText(NewRegPage.this, "City"+id, Toast.LENGTH_SHORT).show();

                        editor.putString("cityId",jsonObject.getString("id"));
                        editor.commit();*/ //important
                        cityTypeList.add(gson.fromJson(String.valueOf(jsonObject),City.class));
                        cityList.add(cityTypeList.get(i).getCname());

                    }
                    cityAdapter.notifyDataSetChanged();
                } catch (JSONException e) {


                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(request);
        requestQueue.getCache().clear();

    }


}