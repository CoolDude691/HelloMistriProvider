package com.hellomistri.hellomistriprovidern.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.hellomistri.hellomistriprovidern.Model.User;
import com.hellomistri.hellomistriprovidern.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Otp extends AppCompatActivity {
    TextInputEditText mobN,et1,et2,et3,et4;
    EditText otp;
    String str;
    TextView otpSignIn;

    String token="";
  //  SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences;
    private TextView tv_coundown;
    String loginMobile,otpInput;

    User user;



    private CountDownTimer countDownTimer;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        getSupportActionBar().hide();
       otpSignIn = findViewById(R.id.otpSignIn);
       mobN= findViewById(R.id.mobileNext);
        et1= findViewById(R.id.edit10otp);
        et2= findViewById(R.id.edit20otp);
        et3= findViewById(R.id.edit30otp);
        otp = findViewById(R.id.otpEnter);
        et4= findViewById(R.id.edit40otp);



        fireBaseInitialize();

        tv_coundown = findViewById(R.id.tv_coundown);
        countDownTimer();


        Intent intent1 = getIntent();
         str = intent1.getStringExtra("ML");
    //
        //    Toast.makeText(this, ""+str, Toast.LENGTH_SHORT).show();

      //  Toast.makeText(this, ""+mobile, Toast.LENGTH_SHORT).show();

     //   new Otp_Reciever().setEditText(otp);

        requestSMSPermission();
        SharedPreferences preferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        loginMobile = preferences.getString("ML","");
        //Intent intent1=getIntent();
        intent1.getStringExtra("ML");
        otpInput = otp.getText().toString();


      //  Toast.makeText(Otp.this, ""+loginMobile, Toast.LENGTH_SHORT).show();

        //CallApi();





        otpSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (otp==null){

                  //  Toast.makeText(Otp.this, "Enter Otp", Toast.LENGTH_SHORT).show();
                    mobN.setError("Require");

                }else {



                    loginUser();

                    /*SharedPreferences sharedPreferences=getSharedPreferences("MySharedPref",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();

                    String uid = sharedPreferences.getString("otp", "");
*/
                 /*  otp.setText(uid);*/
                   /* Intent intent= new Intent(Otp.this,Home.class);
                    startActivity(intent);*/

                }
            }
        });

        et1.addTextChangedListener(new GenericTextWatcher(et1));
        et2.addTextChangedListener(new GenericTextWatcher(et2));
        et3.addTextChangedListener(new GenericTextWatcher(et3));
        et4.addTextChangedListener(new GenericTextWatcher(et4));


    }

    private void loginUser() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();



        String send_otp = otp.getText().toString();
        sharedPreferences=getSharedPreferences("MySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        String uid = sharedPreferences.getString("otp", loginMobile);

        String url="https://hellomistri.com/index/db/api/provider_login.php";
        RequestQueue requestQueue= Volley.newRequestQueue(this);
/*
        SharedPreferences sharedPreferences=getSharedPreferences("MySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
*/

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

              //  Toast.makeText(Otp.this, ""+response, Toast.LENGTH_SHORT).show();


                    try {
                       JSONArray jsonArray= new JSONArray(response);

                       for (int i=0;i<jsonArray.length();i++)
                       {
                           Log.d("mess",response);
                           JSONObject jsonObject=jsonArray.getJSONObject(i);
                          // Toast.makeText(Otp.this, ""+jsonObject, Toast.LENGTH_SHORT).show();

                           editor.putBoolean("isLogin", true);
                           editor.commit();

                           editor.putString("uid",jsonObject.getString("id"));
                           editor.putString("name",jsonObject.getString("name"));
                           editor.putString("email",jsonObject.getString("email"));
                           editor.putString("mobile",jsonObject.getString("mobile"));
                           editor.putString("password",jsonObject.getString("password"));
                           editor.putString("ccode",jsonObject.getString("ccode"));
                           editor.putString("status",jsonObject.getString("status"));
                           editor.putString("rdate",jsonObject.getString("rdate"));
                           editor.putString("credit",jsonObject.getString("credit"));
                           editor.putString("city",jsonObject.getString("city"));
                           editor.putString("address",jsonObject.getString("address"));
                           editor.putString("category_id",jsonObject.getString("category_id"));
                           editor.putString("aprove",jsonObject.getString("aprove"));
                           editor.putString("pimg",jsonObject.getString("pimg"));
                           editor.putString("bio",jsonObject.getString("bio"));
                           editor.putString("rate",jsonObject.getString("rate"));
                           editor.putString("commission",jsonObject.getString("commission"));
                           editor.putString("otp",jsonObject.getString("otp"));
                           editor.putString("pincode",jsonObject.getString("pincode"));
                           editor.putString("distance",jsonObject.getString("distance"));
                           editor.putString("subcategory",jsonObject.getString("subcategory"));
                           editor.putString("lati",jsonObject.getString("lati"));
                           editor.putString("longi",jsonObject.getString("longi"));
                           editor.putString("gstno",jsonObject.getString("gstno"));
                           editor.putString("panno",jsonObject.getString("panno"));
                           editor.putString("adharno",jsonObject.getString("adharno"));
                           editor.putString("pan",jsonObject.getString("pan"));
                           editor.putString("aback",jsonObject.getString("aback"));
                           editor.putString("afront",jsonObject.getString("afront"));
                           editor.putString("gst",jsonObject.getString("gst"));
                           editor.putString("reg",jsonObject.getString("reg"));
                           editor.putString("decletter",jsonObject.getString("decletter"));
                           editor.putString("covid",jsonObject.getString("covid"));
                           editor.putString("higher",jsonObject.getString("higher"));
                           editor.putString("course",jsonObject.getString("course"));
                           editor.commit();

                           String gcat_id = jsonObject.getString("category_id");
                           String gaddress = jsonObject.getString("address");
                           String gcity = jsonObject.getString("city");
                           String approve = jsonObject.getString("aprove");
                           saveToken();


                           String aprove=jsonObject.getString("aprove");

                               Intent otpSingInI = new Intent(Otp.this, Home.class);

                               startActivity(otpSingInI);
                               finish();






                          /* if(gaddress.isEmpty() && gcity.isEmpty()) {


                                   Intent otpSingIn = new Intent(Otp.this, NewRegPage.class);
                                   startActivity(otpSingIn);
                                   finish();

                           }
                           else {


                           }*/
                       }



                    } catch (JSONException e) {
                   //     throw new RuntimeException(e);
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

                map.put("mobile",loginMobile );
                map.put("otp",send_otp );
                return map;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                12000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);



    }





    private void countDownTimer() {
        countDownTimer = new CountDownTimer(1000 * 60 * 2, 1000) {
            @Override
            public void onTick(long l) {
                String text = String.format(Locale.getDefault(), "%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(l) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(l) % 60);
                tv_coundown.setText(text);
            }

            @Override
            public void onFinish() {
                tv_coundown.setText("00:00");
            }
        };
        countDownTimer.start();
    }

    public class GenericTextWatcher implements TextWatcher
    {
        private View view;
        GenericTextWatcher(View view)
        {
            this.view = view;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // TODO Auto-generated method stub
            String text = editable.toString();
            switch(view.getId())
            {

                case R.id.edit10otp:
                    if(text.length()==1)
                        et2.requestFocus();
                    break;
                case R.id.edit20otp:
                    if(text.length()==1)
                        et3.requestFocus();
                    else if(text.length()==0)
                        et1.requestFocus();
                    break;
                case R.id.edit30otp:
                    if(text.length()==1)
                        et4.requestFocus();
                    else if(text.length()==0)
                        et2.requestFocus();
                    break;
                case R.id.edit40otp:
                    if(text.length()==0)
                        et3.requestFocus();
                    break;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }
    }


/*
    private void CallApi() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
        SharedPreferences sharedPreferences=getSharedPreferences("MySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        String url="https://hellomistri.com/Api/getpartnerdetails";
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.d("Prtner",response);


                try {
                    JSONObject jsonObject= new JSONObject(response);

                    Toast.makeText(Otp.this, ""+jsonObject,Toast.LENGTH_SHORT).show();

                    editor.putString("credit",jsonObject.getString("credit"));
                    editor.putString("id",jsonObject.getString("id"));
                    editor.putString("name",jsonObject.getString("name"));

                    editor.commit();
                    startActivity(new Intent(Otp.this,Home.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();




                } catch (JSONException e) {
              throw new RuntimeException(e);
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

                map.put("id", "3918");
                return map;
            }

        };;
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);

    }
*/






    private void requestSMSPermission()
    {
        String permission = Manifest.permission.RECEIVE_SMS;

        int grant = ContextCompat.checkSelfPermission(this, permission);
        if (grant != PackageManager.PERMISSION_GRANTED)
        {
            String[] permission_list = new String[1];
            permission_list[0] = permission;

            ActivityCompat.requestPermissions(this, permission_list,1);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
          //  Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }



    private void fireBaseInitialize() {
        Log.d("firebase","run");
        FirebaseApp.initializeApp(this);
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()) {
                    token = task.getResult();
                    //    Toast.makeText(SignIn.this, ""+token, Toast.LENGTH_SHORT).show();

                    Log.d("tokenValue", token);
                } else {
                    Log.d("tokenValue", task.getException().getMessage());
                }
            }
        });
    }



    private void saveToken() {



        SharedPreferences shp = getApplicationContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=shp.edit();
        String proid = shp.getString("uid", "");
        Log.d("save_token",proid+"  " +token);
      //  Toast.makeText(this, ""+proid, Toast.LENGTH_SHORT).show();
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String url="https://hellomistri.com/index/db/api/UpdateFcmToken.php";
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                Log.d("responseDevFcm",response);





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
                map.put("user_id",proid);
                map.put("fcm_token",token);
                return map;
            }
        };
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);



    }



}