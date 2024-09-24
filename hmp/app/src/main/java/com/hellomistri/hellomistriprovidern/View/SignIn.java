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
import android.text.TextUtils;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hellomistri.hellomistriprovidern.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SignIn extends AppCompatActivity {

    TextInputEditText mobileno;
    Button Login,Signup;
    String loginmobile;
    String otp;
    FirebaseAuth mAuth;
    String id = "";

    String verificationID;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();



//        phoneNumber =getIntent().getStringExtra("mobile").toString();
        mobileno = findViewById(R.id.Mobile);
        Login = findViewById(R.id.signInButton);
        Signup=findViewById(R.id.signUpButton);
        mAuth = FirebaseAuth.getInstance();

        SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sharedPref.edit();
        id= sharedPref.getString("uid","");



        //sendOtp();


        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignIn.this,SignUp.class);
                startActivity(intent);
            }
        });


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(TextUtils.isEmpty(mobileno.getText().toString()))
                {
                   // Toast.makeText(SignIn.this, "Enter Valid Phone No.", Toast.LENGTH_SHORT).show();
                }
                else {

                    loginmobile =  mobileno.getText().toString();
                    final Random myRandom = new Random();
                    otp= String.format("%04d", myRandom.nextInt(10000));

                    // getOtp(loginmobile,otp);
                    updateOtp(otp);

                }



            }
        });
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            askPermission();
        }

    }

    private void askPermission() {
        ActivityCompat.requestPermissions(SignIn.this, new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION},103);
    }


    private void updateOtp(String otp) {


        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String url="https://hellomistri.com/index/db/api/update_otp.php";
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.d("responseDeviceDetails",response);
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getBoolean("status")){

                        //Toast.makeText(SignIn.this, ""+response, Toast.LENGTH_SHORT).show();

                        SharedPreferences preferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.putString("ML",loginmobile);
                        editor.putString("otp",otp);
                        editor.commit();
                        getOtp(loginmobile,otp);
                    }else{
                        if(jsonObject.getString("message").equals("not_found")){
                            Intent intent = new Intent(SignIn.this,SignUp.class);
                            startActivity(intent);
                            Toast.makeText(SignIn.this, "Please register yourself", Toast.LENGTH_SHORT).show();
                        }
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
                // Log.d("cakeon",""+email +" " +password);
                map.put("mobile",loginmobile);
                map.put("otp",otp);
                return map;
            }
        };
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);



    }



    private void getOtp(String loginmobile, String otp) {

        // textGenerateNumber.setText();


        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String url="https://smsinteract.in/SMSApi/send?userid=hellomistri&password=6y2xVjjA&sendMethod=quick&mobile="+ this.loginmobile +"&msg=OTP to verify your Hello Mistri Account is "+ this.otp +" and valid till 2 minutes. Do not share this OTP to anyone for security reasons. - Team www.hellomistri.com&senderid=HMSTRI&msgType=text&duplicatecheck=true&format=text";
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                Intent otppage = new Intent(SignIn.this,Otp.class);
                startActivity(otppage);
                finish();

                Log.d("responseDeviceDetails",response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();

            }
        });
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);



    }


    private void sendverificationcode(String phoneNumber)
    {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+phoneNumber)  // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential)
        {
            final String code = credential.getSmsCode();
            if(code!=null)
            {
                verifycode(code);
            }
        }
        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            //   Toast.makeText(SignIn.this, "Verification Failed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String s,
                               @NonNull PhoneAuthProvider.ForceResendingToken token)
        {
            super.onCodeSent(s, token);
            verificationID = s;
            //  Toast.makeText(SignIn.this, "Code sent", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(SignIn.this,Otp.class);
            startActivity(intent);
            // btnverify.setEnabled(true);
            //bar.setVisibility(View.INVISIBLE);
        }};


    private void verifycode(String Code)
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID,Code);
        signinbyCredentials(credential);
    }

    private void signinbyCredentials(PhoneAuthCredential credential)
    {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(SignIn.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignIn.this, Home.class));
                        }

                    }
                });}

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser!=null)
        {
            startActivity(new Intent(SignIn.this, Home.class));
            finish();
        }}








}






