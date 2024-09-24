package com.hellomistri.hellomistriprovidern.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hellomistri.hellomistriprovidern.Adapter.AdapterWallet;
import com.hellomistri.hellomistriprovidern.Adapter.AdapterWallet;
import com.hellomistri.hellomistriprovidern.Model.ModelWallet;
import com.hellomistri.hellomistriprovidern.R;
import com.hellomistri.hellomistriprovidern.View.AddCredit;
import com.hellomistri.hellomistriprovidern.View.Home;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Wallet extends Fragment  implements onbp{

    RecyclerView recyclerView;
    TextView cred;
    AdapterWallet adapterWallet;
    CardView addMoney;
    List<ModelWallet> walletList = new ArrayList<>();


    public Wallet ()
    {

    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_wallet, container, false);

       recyclerView  = view.findViewById(R.id.recyclerQ);
        addMoney = view.findViewById(R.id.add_Money);
        cred=view.findViewById(R.id.backBtn1);
        CallApi();
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("MySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        String uid = sharedPreferences.getString("credit", "");



        wall();

        addMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Bundle bundle=new Bundle();
                DialogCredit deviceCred= new DialogCredit();
                deviceCred.setArguments(bundle);
                deviceCred.show(getChildFragmentManager(), "TAG");*/
                Intent intent = new Intent(getActivity().getApplication(), AddCredit.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterWallet= new AdapterWallet(walletList);
        recyclerView.setAdapter(adapterWallet);


        fetchAll();



        return view;

    }


    private void CallApi() {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("MySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        String uid = sharedPreferences.getString("uid", "");
        String url="https://hellomistri.com/Api/getpartnerdetails";
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.d("Prtner",response);


                try {
                    JSONObject jsonObject= new JSONObject(response);


                    if(jsonObject.equals("null")) {
                        String amount = jsonObject.getString("credit");

                     //   cred.setText(amount);

                    }else {
                    //    Toast.makeText(getContext(), "please Recharge", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
             //       throw new RuntimeException(e);
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

                map.put("id", uid);
                return map;
            }

        };;
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);

    }








    private void fetchAll() {

        ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();
        SharedPreferences shp = getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=shp.edit();

/*
     String cid= String.valueOf(1);


*/



        String proid = shp.getString("uid", "");
        String url="https://hellomistri.com/index/db/api/get_tran.php";

        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();

              // Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();

                try {

                    JSONArray transaction= new JSONArray(response);

                    for (int i=0;i<transaction.length();i++)

                    {

                        JSONObject jsonObject= transaction.getJSONObject(i);

                        String id=  jsonObject.getString("id");
                        String p_id = jsonObject.getString("p_id");
                        String p_mode = jsonObject.getString("p_mode");
                        String tid =  jsonObject.getString("tid");
                        String status = jsonObject.getString("status");
                        String amount = jsonObject.getString("amount");
                        String acredit = jsonObject.getString("acredit");
                        String date = jsonObject.getString("date");


                      /*  cred.setText(amt);
                        Toast.makeText(getContext(), ""+cred, Toast.LENGTH_SHORT).show();*/

                        ModelWallet wallet = new ModelWallet(id,p_id,p_mode,status,tid,amount,acredit,date);
                        walletList.add(wallet);

                    }
                    pd.dismiss();
                    adapterWallet.notifyDataSetChanged();


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
                map.put("pid",proid );

                return map;
            }};
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);

    }

/*
    private void onBackPressed() {

    }
*/


    private void wall() {

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();


       SharedPreferences shp = getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=shp.edit();

/*
     String cid= String.valueOf(1);
*/


        String proid = shp.getString("uid", "");


        String url="https://hellomistri.com/index/db/api/getCredit.php";
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String credit = jsonObject.getString("credit");
                    cred.setText(credit);
                } catch (JSONException e) {

                }


                Log.d("leads",response);


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
                map.put("pid",proid );

                return map;
            }

        };
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);



    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getActivity().getApplication(), Home.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        getActivity().finish();
    }




}