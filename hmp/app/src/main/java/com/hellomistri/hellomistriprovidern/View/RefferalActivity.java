package com.hellomistri.hellomistriprovidern.View;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class RefferalActivity extends AppCompatActivity {

    TextInputEditText phone,phone1,phone2,phone3,phone4,phone5;

    Button btn;

    TextView skip;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refferal);
        phone=findViewById(R.id.mobile);
        phone1=findViewById(R.id.mobile2);
        phone2=findViewById(R.id.mobile3);
        phone3=findViewById(R.id.mobile4);
        phone4=findViewById(R.id.mobile5);
        phone5=findViewById(R.id.mobile6);

        skip= findViewById(R.id.skip);
        btn=findViewById(R.id.subk);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(RefferalActivity.this,SignIn.class);
                startActivity(intent);*/
                Intent intent = new Intent(RefferalActivity.this,Home.class);
                startActivity(intent);
                finish();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallApi();
            }
        });

        getSupportActionBar().hide();
    }



    private void CallApi() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();


      String user1 = phone.getText().toString();
      String num1 = phone1.getText().toString();
      String user2 = phone2.getText().toString();
      String num2 = phone3.getText().toString();
      String user3 = phone4.getText().toString();
      String num3 = phone5.getText().toString();



        String url="https://hellomistri.com/index/db/api/provider_referal_api.php";
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                //    Toast.makeText(NewLead.this, ""+response, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(RefferalActivity.this,SignIn.class);
                startActivity(intent);
                finish();

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

                map.put("ref_f_name", user1);
                map.put("ref_f_num", num1);
                map.put("ref_s_name", user2);
                map.put("ref_s_num", num2);
                map.put("ref_t_name", user3);
                map.put("ref_t_name", num3);

                return map;
            }

        };
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);



    }

}