package com.hellomistri.hellomistriprovidern.Fragments;

import android.Manifest;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.hellomistri.hellomistriprovidern.Model.User;
import com.hellomistri.hellomistriprovidern.Service.OrdersObserverService;
import com.hellomistri.hellomistriprovidern.Traker.GpsTracker;
import com.hellomistri.hellomistriprovidern.View.Home;
import com.hellomistri.hellomistriprovidern.View.NewLead;
import com.hellomistri.hellomistriprovidern.View.NewRegPage;
import com.hellomistri.hellomistriprovidern.View.ProcessingLead;
import com.hellomistri.hellomistriprovidern.R;
import com.hellomistri.hellomistriprovidern.View.CancledLead;
import com.hellomistri.hellomistriprovidern.View.CompletedLead;
import com.hellomistri.hellomistriprovidern.View.Wallet;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HomeFragment extends Fragment implements homeBackpress {
    
    CardView newLead,processing,completed,cancle,userProfile,wallet;
    TextView UserName,emailName;

    TextView newlCount,prolCount,complcon,canlcom;
    SharedPreferences shp;
    FusedLocationProviderClient fusedLocationProviderClient;
    String aprove;
    List<User> list = new ArrayList<>();
    String apr , aproved;

    String ii = "1";
    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        newLead = view.findViewById(R.id.newLeadCard);
        processing = view.findViewById(R.id.processingLeadCard);
        completed = view.findViewById(R.id.completedLeadCard);
        cancle = view.findViewById(R.id.cancleLeadCard);
        wallet = view.findViewById(R.id.walletLeadCard);
        UserName=view.findViewById(R.id.userN);
        emailName=view.findViewById(R.id.emailId);
        newlCount = view.findViewById(R.id.newCount);
        prolCount = view.findViewById(R.id.processingCount);
        complcon = view.findViewById(R.id.completedCount);
        canlcom = view.findViewById(R.id.cancelCount);

        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(getContext());

        if(lat==0.0 && lon==0.0){
            getLastLocation(getContext());
        }



        /*if(!isMyServiceRunning(OrdersObserverService.class)){
            Intent intent= new Intent(getContext(), OrdersObserverService.class);
            getActivity().startService(intent);
        }*/  //important



        shp = getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=shp.edit();



        String name = shp.getString("name", "");
        String email = shp.getString("email", "");

        UserName.setText(name);
        emailName.setText(email);


        aprovalProfile("not_clicked");

//       String ii= getArguments().getString("name");
     /*String oo=  getArguments().getString("email");
    */


        newLead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(aproved==null){
                    aprovalProfile("clicked");
                }else{
                    navigateToNewLead("clicked");
                }



            }
        });
        processing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent processing = new Intent(getContext(), ProcessingLead.class);
                startActivity(processing);
                getActivity().finish();
                if (aproved.equals(ii)){

                }
                else
                {
                    Toast.makeText(getContext(), "Your Account Is not Approved", Toast.LENGTH_SHORT).show();
                }
            }
        });
        completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (aproved.equals(ii)){

                    Intent complete = new Intent(getContext(), CompletedLead.class);
                    startActivity(complete);
                    getActivity().finish();

                }
                else
                {
                    Toast.makeText(getContext(), "Your Account Is not Approved", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (aproved.equals(ii)){

                    Intent cancled = new Intent(getContext(), CancledLead.class);
                    startActivity(cancled);
                    getActivity().finish();

                }
                else
                {
                    Toast.makeText(getContext(), "Your Account Is not Approved", Toast.LENGTH_SHORT).show();
                }
            }
        });

        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent wallets = new Intent(getContext(), Wallet.class);
                startActivity(wallets);
                getActivity().finish();
            }
        });

        return view;
    }


    private void navigateToNewLead(String type){
        Log.d("approved_value","called"+aproved);
        if(type.equals("clicked")){
            if (aproved.equals(ii) ){
                Intent newlead = new Intent(getContext(), NewLead.class);
                startActivity(newlead);
                getActivity().finish();
            }
            else
            {
                Toast.makeText(getContext(), "Your Account Is not Approved", Toast.LENGTH_SHORT).show();
            }
        }

    }
    private void aprovalProfile(String type) {

        Log.d("aprovalProfile",shp.getString("uid", "")+"");

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
        SharedPreferences shp = getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=shp.edit();
        String proid = shp.getString("uid", "");

        String url="https://hellomistri.com/index/db/api/aproval.php";
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("aprovalProfile",response);
                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String aproval = jsonObject.getString("aprove");

                    aproved = aproval;
                    navigateToNewLead(type);
                } catch (JSONException e) {
                //    throw new RuntimeException(e);
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
            }

        };
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);



    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private void countPlus(Context context) {



        SharedPreferences.Editor editor=shp.edit();
        String proid = shp.getString("uid", "");
        String category = shp.getString("category_id", "");
        String cityid = shp.getString("city", "");
        Log.d("countplus",proid+" "+category+" "+cityid);

        String url="https://hellomistri.com/index/db/api/getAllCatCount.php";
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

              //  Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();

                Log.d("HelloLead",""+response);

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String pen = jsonObject.getString("pending");
                    String process = jsonObject.getString("processing");
                    String com = jsonObject.getString("completed");
                    String canc = jsonObject.getString("cancelled");


                    newlCount.setText(pen);
                    prolCount.setText(process);
                    complcon.setText(com);
                    canlcom.setText(canc);



                } catch (JSONException e) {
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();

            }
        }){
            protected Map<String,String> getParams() {
                Map<String, String> map = new HashMap<>();
//               Log.d("cakeon",""+cid);
                map.put("pid",proid );
                map.put("cid",category );
                map.put("cityid",cityid );
                map.put("lati",String.valueOf(lat));
                map.put("longi",String.valueOf(lon));

                return map;
            }

        };
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);


    }
    double lat,lon;
    boolean firstAtt=false;
    private void getYourLocation(Context context) {
        Log.d("getYourLocation",lat+"  "+lon);
        /*progressDialog =new ProgressDialog(getContext());
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        progressDialog.setCancelable(false);*/
        GpsTracker gpsTracker=new GpsTracker(getContext()){
            @Override
            public void onLocationChanged(Location location) {
                super.onLocationChanged(location);
                lat=location.getLatitude();
                lon=location.getLongitude();

                    countPlus(context);
                    Log.d("getYourLocation","called"+lat);



            }
        };
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getActivity().getApplication(), Home.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        getActivity().finish();

    }
    private void getLastLocation(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Please wait..");
            progressDialog.show();
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location !=null) {
                                Geocoder geocoder=new Geocoder(context, Locale.getDefault());
                                List<Address> addresses= null;
                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                    /*      latitude.setText("Lagitude :" +addresses.get(0).getLatitude());
                                    longitude.setText("Longitude :"+addresses.get(0).getLongitude());*/

                                    lat= location.getLatitude();
                                    lon= location.getLongitude();
                                    // addressView.setText("Address :"+addresses.get(0).getAddressLine(0));

                                    countPlus(context);

                                } catch (IOException e) {

                                    e.printStackTrace();

                                }
                            }
                        }
                    });
        }


    }


}