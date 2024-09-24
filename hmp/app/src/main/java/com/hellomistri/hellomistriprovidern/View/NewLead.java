package com.hellomistri.hellomistriprovidern.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.hellomistri.hellomistriprovidern.Adapter.MyAdapter;
import com.hellomistri.hellomistriprovidern.Model.Leads;
import com.hellomistri.hellomistriprovidern.Model.User;
import com.hellomistri.hellomistriprovidern.R;
import com.hellomistri.hellomistriprovidern.Service.MyFirebaseMasseging;
import com.hellomistri.hellomistriprovidern.Traker.GpsTracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class NewLead extends AppCompatActivity {

    TextView address1,date,time1,mor;
    double latitude;
    double longitude;
    List<Leads> leadsList = new ArrayList<>();
    RecyclerView recyclerView;
    MyAdapter adapter;
    LinearLayout nodata;
    LinearLayoutManager linearLayoutManager;
    FusedLocationProviderClient fusedLocationProviderClient;
    ImageView back;
    TextView noDataFound;

    SharedPreferences shp;
    ProgressDialog progressDialog;

    String amount;
    BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            newLeads();
        }
    };


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_lead);
//        address1=findViewById(R.id.addressI);
        //date=findViewById(R.id.dateI);
        //  time1=findViewById(R.id.timeI);
        back=findViewById(R.id.backBtn2);
        noDataFound=findViewById(R.id.noDataFound);
        nodata=findViewById(R.id.nodata1);

        registerReceiver(broadcastReceiver,new IntentFilter(MyFirebaseMasseging.INTENT_FILTER_ORDERS));

        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);

        getLastLocation(this);

        Leads object= (Leads) getIntent().getSerializableExtra("device_object");

        Bundle bundle=new Bundle();

        bundle.putSerializable("deviceObject",object);

        User object1= (User) getIntent().getSerializableExtra("device_object1");

        Bundle bundle1 = new Bundle();

        bundle1.putSerializable("device_object1",object1);




        creditk();

        recyclerView=findViewById(R.id.recyclerView);
        getSupportActionBar().hide();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        recyclerView.setHasFixedSize(true);
        linearLayoutManager= new LinearLayoutManager(this);

        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter= new MyAdapter(leadsList, new MyAdapter.ItemClickListner() {
            @Override
            public void onItemClick(TextView textView, int pos) {
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(NewLead.this,LeadDetaills.class);
                        intent.putExtra("device_object",leadsList.get(pos));
                        intent.putExtra("device_object1",leadsList.get(pos));
                       startActivity(intent);
                       finish();

                    }
                });
            }

            @Override
            public void onItemSelection(View view, ConstraintLayout constraintLayout, int pos) {

            }
        });
        recyclerView.setAdapter(adapter);



    }

    private void newLeads() {


        leadsList.clear();


        shp = getApplicationContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=shp.edit();
/*
     String cid= String.valueOf(1);
*/


        String category_id = shp.getString("category_id", "");
        String cit = shp.getString("city", "");

        Log.d("newleadsCall",category_id+" "+lat);

        String url="https://hellomistri.com/index/db/new-lead.php";
//        String url="https://hellomistri.com/Api/getUserpartnerServicedetails";
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();


                Log.d("leads",response);

                try {


                    JSONArray leads= new JSONArray(response);

                   /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                      //  showNotification();
                    }*/

                    for (int i=0;i<leads.length();i++)
                    {


                        JSONObject jsonObject= leads.getJSONObject(i);



                        String id = jsonObject.getString("id");
                        String customer_id = jsonObject.getString("customer_id");
                        String provider_id= jsonObject.getString("provider_id");
                        String c_atname = jsonObject.getString("cat_name");
                        String sub_cat_name = jsonObject.getString("sub_cat_name");
                        String service_name = jsonObject.getString("service_name");
                        String city_name = jsonObject.getString("city_name");
                        String message = jsonObject.getString("message");
                        String or_date = jsonObject.getString("or_date");
                        String o_status = jsonObject.getString("o_status");
                        String or_address = jsonObject.getString("or_address");
                        String latitude = jsonObject.getString("Lattitude");
                        String longitude = jsonObject.getString("Longitude");


                        editor.putString("orderid",jsonObject.getString("id"));
                        editor.commit();
                        // String status = jsonObject.getString("status");

                        Leads leads1=new Leads(id,customer_id,provider_id,c_atname,sub_cat_name,service_name,city_name,message,or_date,o_status,or_address,longitude,latitude);

                        leadsList.add(leads1);
                        /*if(!latitude.equals("null") && !longitude.equals("null")
                                    && !latitude.equals("") && !longitude.equals("")){
                            double dist= distance(Double.parseDouble(latitude),Double.parseDouble(longitude),lat,lon);
                            Log.d("newLeadsCheck2",latitude+" "+longitude+" "+dist+" "+lat+" "+lon);
                            if(dist<10){

                            *//*    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    showNotification();
                                }*//*
                            }
                            dist=0;
                        }*/



                        if(!jsonObject.getString("customer_id").equals("null")) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject("customer_id");
                            Log.d("JSON:", "" + jsonObject1);

                            String js=jsonObject1.getString("name");
                            String jm=jsonObject1.getString("mobile");
                            leads1.setMobile(jm);

                            leads1.setCname(js);



                            shp = getApplicationContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=shp.edit();


                        }

                        if(!jsonObject.getString("provider_id").equals("null") && !jsonObject.getString("provider_id").equals("0") ) {
                            JSONObject jsonObject2 = jsonObject.getJSONObject("provider_id");
                            Log.d("JSON:", "" + jsonObject2);

                            String pname = jsonObject2.getString("name");

                            leads1.setPame(pname);

                        }

                        if(!jsonObject.getString("cat_name").equals("null")) {
                            JSONObject jsonObject2 = jsonObject.getJSONObject("cat_name");
                            Log.d("JSON:", "" + jsonObject2);

                            String cat_name = jsonObject2.getString("cat_name");

                            leads1.setCat_name(cat_name);

                        }

                        if(!jsonObject.getString("sub_cat_name").equals("null")) {
                            JSONObject jsonObject2 = jsonObject.getJSONObject("sub_cat_name");
                            Log.d("JSON:", "" + jsonObject2);

                            String subtitle = jsonObject2.getString("title");

                            leads1.setSubtitle(subtitle);

                        }

                        if(!jsonObject.getString("child_cat_name").equals("null")) {
                            JSONObject jsonObject2 = jsonObject.getJSONObject("child_cat_name");
                            Log.d("JSON:", "" + jsonObject2);

                            String childtitle = jsonObject2.getString("title");

                            leads1.setChildtitle(childtitle);

                        }

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
                 progressDialog.dismiss();

            }
        }){
            protected Map<String,String> getParams() {
                Map<String, String> map = new HashMap<>();

                map.put("cid", category_id);
                map.put("lati",String.valueOf(lat));
                map.put("longi",String.valueOf(lon));


                return map;
            }

        };
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);



    }
    double lat;
    double lon;
    boolean firstAtt=false;

    private void getYourLocation() {
        progressDialog =new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        GpsTracker gpsTracker=new GpsTracker(this){
            @Override
            public void onLocationChanged(Location location) {
                super.onLocationChanged(location);
                lat=location.getLatitude();
                lon=location.getLongitude();
                if(!firstAtt){
                    firstAtt=true;
                    newLeads();
                }

            }
        };
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


    private void wall() {

        ProgressDialog progressDialog = new ProgressDialog(NewLead.this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();


        SharedPreferences shp = this.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=shp.edit();

/*
     String cid= String.valueOf(1);
*/


        String proid = shp.getString("uid", "");
        //  String category_id = shp.getString("category_id", "");



        //Toast.makeText(this, ""+category_id, Toast.LENGTH_SHORT).show();

        String url="https://hellomistri.com/index/db/api/getCredit.php";
        RequestQueue requestQueue= Volley.newRequestQueue(NewLead.this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String credit = jsonObject.getString("credit");
                    if (credit == ("0"))
                    {
                        Toast.makeText(NewLead.this, "Please Recharge", Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(NewLead.this, Wallet.class);
                        startActivity(intent);
                        finish();
                    }
                    // cred.setText(credit);
                    //  Toast.makeText(getContext(), "cred"+credit, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Log.d("leads",response);
                //
                //
                //   Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();


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

    private void creditk() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
        SharedPreferences sharedPreferences=this.getSharedPreferences("MySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        String uid = sharedPreferences.getString("uid", "");
        String url="https://hellomistri.com/Api/getpartnerdetails";
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.d("Prtner",response);


                try {
                    JSONObject jsonObject= new JSONObject(response);



                    String amount = jsonObject.getString("credit");


                    //   cred.setText(amount);




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

        };
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);

    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showNotification()
    {
        MediaPlayer mPlayer = MediaPlayer.create(this, R.raw.bell);
        mPlayer.start();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(NewLead.this, "default_notification_channel_id" )
                .setSmallIcon(R.drawable. hllo )
                .setContentTitle( "Hello Mistri Provider" )
                .setContentText( "New Lead Generated in your Category Please pic the lead" ) ;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context. NOTIFICATION_SERVICE );
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes. CONTENT_TYPE_SONIFICATION )
                    .setUsage(AudioAttributes. USAGE_ALARM )
                    .build() ;
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new NotificationChannel( "NOTIFICATION_CHANNEL_ID" , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            notificationChannel.enableLights( true ) ;
            notificationChannel.setLightColor(Color. RED ) ;
            notificationChannel.enableVibration( true ) ;
            notificationChannel.setVibrationPattern( new long []{ 100 , 200 , 300 , 400 , 500 , 400 , 300 , 200 , 400 }) ;

            mBuilder.setChannelId( "NOTIFICATION_CHANNEL_ID" ) ;
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel) ;
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(( int ) System. currentTimeMillis () ,
                mBuilder.build()) ;
    }







    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    public void onBackPressed() {
        super.onBackPressed();

        Intent intent= new Intent(NewLead.this,Home.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
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

                                    newLeads();

                                } catch (IOException e) {

                                    e.printStackTrace();

                                }
                            }
                        }
                    });
        }


    }






}