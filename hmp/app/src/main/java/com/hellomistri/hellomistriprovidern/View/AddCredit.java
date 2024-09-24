package com.hellomistri.hellomistriprovidern.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.hellomistri.hellomistriprovidern.Adapter.AdaprAmount;
import com.hellomistri.hellomistriprovidern.Model.RechargeModel;
import com.hellomistri.hellomistriprovidern.R;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddCredit extends AppCompatActivity implements PaymentResultListener {

    Button submit;

    int payAmount,payAmount1 ;
    String s;
    String Name, Mobile, email;

    List<RechargeModel> rechargeModelList= new ArrayList<>();

    AdaprAmount adaprAmount;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    RechargeModel rechargeModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_credit);

        getSupportActionBar().hide();
        Checkout.preload(getApplicationContext());
        submit = findViewById(R.id.Submit);
        recyclerView =findViewById(R.id.recyclerView);

        linearLayoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager
                (new LinearLayoutManager(this
                        ,LinearLayoutManager.VERTICAL,false));

        GridLayoutManager layoutManager=new GridLayoutManager(this,2);

        recyclerView.setLayoutManager(layoutManager);

        adaprAmount=new AdaprAmount(rechargeModelList, new AdaprAmount.ItemClickListner() {
            @Override
            public void onItemClick(CardView cardView, int pos) {

                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {




                         rechargeModel=rechargeModelList.get(pos);
                        Toast.makeText(AddCredit.this, "Selected:- "+rechargeModel.getCredit_amt(), Toast.LENGTH_SHORT).show();





                    }
                });
            }

            @Override
            public void onItemSelection(ImageView imageView, ConstraintLayout constraintLayout, int pos) {



            }
        });

        recyclerView.setAdapter(adaprAmount);



        ramount();

        SharedPreferences shp = this.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shp.edit();


        Name = shp.getString("name", "");
        Mobile = shp.getString("mobile", "");
        email = shp.getString("email", "");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(rechargeModel!=null){
                    payNow(rechargeModel.getCredit_amt());
                }else{
                    Toast.makeText(AddCredit.this, "Please select amount to proceed.", Toast.LENGTH_SHORT).show();
                }
                

            }

        });


    }

    private void payNow(String s) {

        final Activity activity = this;


        //   Toast.makeText(getActivity(), "Hello Submit", Toast.LENGTH_SHORT).show();

        payAmount = Integer.parseInt(rechargeModel.getCredit_amt());


        payAmount1 = payAmount * 100;
        final String pay = String.valueOf(payAmount1);

        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_live_ZS2nUuoGAbZ986");

        checkout.setImage(R.drawable.hllo);

        try {

            JSONObject options = new JSONObject();
            options.put("name", "Hello Mistri Provider");

            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", pay);
            options.put("prefill.name", Name);
            options.put("prefill.email", email);
            options.put("prefill.contact", Mobile);

            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch (Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }


    @Override
    public void onPaymentSuccess(String s) {
        Log.e("Respo2", "" + s);
        Toast.makeText(getApplicationContext(), "Payment Successful", Toast.LENGTH_SHORT).show();

        addCred(s);

    }

    @Override
    public void onPaymentError(int i, String s) {

        Toast.makeText(getApplicationContext(), "Payment Failed", Toast.LENGTH_SHORT).show();

        Log.e("Respo", "" + s);


    }

    private void addCred(String s) {




        SharedPreferences shp = this.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shp.edit();




        String proid = shp.getString("uid", "");


        String url = "https://hellomistri.com/index/db/api/addCreditPymt.php";
        RequestQueue requestQueue = Volley.newRequestQueue(AddCredit.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // progressDialog.dismiss();

                Toast.makeText(AddCredit.this, ""+response, Toast.LENGTH_SHORT).show();

                Log.d("leads1", response);
                //

                //
                //   Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
//               Log.d("cakeon",""+cid);
                map.put("pid", proid);
                map.put("amount", String.valueOf(payAmount));

                map.put("tid", s);
            //    Log.e("seeds",""+s);

                return map;
            }

        };
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);





    }





    private void ramount() {




        SharedPreferences shp = this.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shp.edit();




        String proid = shp.getString("uid", "");


        String url = "https://hellomistri.com/index/db/api/walletRecharceAmount.php";
        RequestQueue requestQueue = Volley.newRequestQueue(AddCredit.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // progressDialog.dismiss();

              //  Toast.makeText(AddCredit.this, ""+response, Toast.LENGTH_SHORT).show();

                Log.d("leads1", response);

                try {
                    JSONArray jsonArray=new JSONArray(response);

                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject= jsonArray.getJSONObject(i);

                        String id = jsonObject.getString("id");

                        String credit_amt = jsonObject.getString("credit_amt");

                     //   Toast.makeText(AddCredit.this, ""+credit_amt, Toast.LENGTH_SHORT).show();

                        RechargeModel rechargeModel= new RechargeModel(id,credit_amt);

                        rechargeModelList.add(rechargeModel);
                    }
                    adaprAmount.notifyDataSetChanged();

                } catch (JSONException e) {
                }
                //

                //
                //   Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();





            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        }) ;
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);





    }




    public void onBackPressed() {
        super.onBackPressed();

        Intent intent= new Intent(AddCredit.this,Home.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }









}





