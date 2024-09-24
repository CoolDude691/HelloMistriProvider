package com.hellomistri.hellomistriprovidern.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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

public class RefIn extends Fragment {

    TextInputEditText phone,phone1,phone2,phone3,phone4,phone5;

    Button btn;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_ref_in, container, false);

        phone=view.findViewById(R.id.mobile);
        phone1=view.findViewById(R.id.mobile2);
        phone2=view.findViewById(R.id.mobile3);
        phone3=view.findViewById(R.id.mobile4);
        phone4=view.findViewById(R.id.mobile5);
        phone5=view.findViewById(R.id.mobile6);


        btn=view.findViewById(R.id.subk);




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallApi();
            }
        });




        return view;


    }

    private void CallApi() {

        ProgressDialog progressDialog = new ProgressDialog(getContext());
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
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                //    Toast.makeText(NewLead.this, ""+response, Toast.LENGTH_SHORT).show();

                Toast.makeText(getContext(), "Referral Add Successfully", Toast.LENGTH_SHORT).show();

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