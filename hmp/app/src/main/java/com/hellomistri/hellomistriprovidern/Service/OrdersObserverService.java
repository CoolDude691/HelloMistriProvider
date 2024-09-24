package com.hellomistri.hellomistriprovidern.Service;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.hellomistri.hellomistriprovidern.Model.Leads;
import com.hellomistri.hellomistriprovidern.Model.Leads;
import com.hellomistri.hellomistriprovidern.R;
import com.hellomistri.hellomistriprovidern.View.Home;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrdersObserverService  extends Service {

    Handler handler;

    SharedPreferences shp;
    public static String INTENT_FILTER_ORDERS="INTENT_FILTER_ORDERS";





    List<Leads> leadsList = new ArrayList<>();

    String Channel_Id = "orderNotif";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {




        NotificationChannel notificationChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(
                    Channel_Id, "Heads up Notification", NotificationManager.IMPORTANCE_LOW
            );
            getSystemService(NotificationManager.class).createNotificationChannel(notificationChannel);
            NotificationManager manager=getSystemService(NotificationManager.class);

            Log.d("nullchk", "notificationChannel " + (notificationChannel == null));
            Log.d("nullchk", "manager " + (manager == null));
            manager.createNotificationChannel(notificationChannel);
        }

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, Home.class), PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_MUTABLE);
      Notification notification = new NotificationCompat.Builder(this, Channel_Id)
                .setContentTitle("Hello Mistri Provider")
                .setContentText("Running in background")
                .setSmallIcon(R.drawable.hllo)
                .setAutoCancel(false)
                .setContentIntent(contentIntent)
                .setSound(null)
                .setDefaults(0).build();
        notification.defaults=0;

        generateLocation2(getApplicationContext());
        startForeground(1,notification);
        return START_NOT_STICKY;
    }

    private void checkIfYouAreEligible(LatLng location){
        LatLng latLng=null;
        boolean orderFound=false;
        List<String> orderIds=new ArrayList<>();
        Log.d("LeadsCheck2","called");
        for(int i=0;i<leadsList.size();i++){
            if(!leadsList.get(i).getLattitude().equals("null") && !leadsList.get(i).getLongitude().equals("null")){
                latLng=new LatLng(Double.parseDouble(leadsList.get(i).getLattitude()),Double.parseDouble(leadsList.get(i).getLongitude()));
                if(latLng!=null){
//                Log.d("LeadsCheck2","called3");
                    Log.d("LeadsLoc",latLng.latitude+" "+latLng.longitude+" "+location.latitude+" "+location.longitude);
                    double dist= distance(latLng.latitude,latLng.longitude,location.latitude,location.longitude);
                    Log.d("LeadDistance",dist+"");
                    if(dist<=5){
                        Log.d("LeadsCheck2","called4");
                        orderIds.add(leadsList.get(i).getId());
                    }
                }
            }

        }
        if(orderIds.size()>0){
            Log.d("LeadsCheck2",orderIds.get(0));
            checkIfTherAnyChangeInLeads(orderIds);
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    generateLocation2(getApplicationContext());
                }
            },10000);
        }

    }
    private void checkIfTherAnyChangeInLeads(List<String> orderIds){
        Log.d("order_id_size",orderIds.size()+" "+shp.getInt("pending_orders",0));
        if(orderIds.size()>shp.getInt("pending_orders",0)){
            shp.edit().putInt("pending_orders",orderIds.size()).commit();
          /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                showNotification();
            }*/
        }else{
            shp.edit().putInt("pending_orders",orderIds.size()).commit();
            Log.d("mob","pending");

        }
        orderIds.clear();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                generateLocation2(getApplicationContext());
            }
        },10000);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showNotification()
    {
        MediaPlayer mPlayer = MediaPlayer.create(this, R.raw.bell);
        mPlayer.start();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(OrdersObserverService.this, "default_notification_channel_id" )
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


    private void newLeads(LatLng location) {
        leadsList.clear();




        shp = getApplicationContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=shp.edit();



        String category_id = shp.getString("category_id", "");
        Log.d("category_id_value",category_id);
        String cit = shp.getString("city", "");

        String url="https://hellomistri.com/Api/getUserpartnerServicedetails";
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("LeadsCheck",response);

                try {


                    JSONArray leads= new JSONArray(response);

                    for (int i=0;i<leads.length();i++)
                    {


                        JSONObject jsonObject= leads.getJSONObject(i);



                        String id = jsonObject.getString("id");
                        String customer_id = jsonObject.getString("customer_id");
                        String provider_id= jsonObject.getString("provider_id");
                        String c_atname = jsonObject.getString("c_atname");
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


                        Leads leads1=new Leads(id,customer_id,provider_id,c_atname,sub_cat_name,service_name,city_name,message,or_date,o_status,or_address,latitude,longitude);
                        leadsList.add(leads1);




                        if(!jsonObject.getString("customer_id").equals("null")) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject("customer_id");
                            Log.d("JSON:", "" + jsonObject1);

                            String js=jsonObject1.getString("cname");
                            String jm=jsonObject1.getString("mobile");
                            leads1.setMobile(jm);

                            leads1.setCname(js);



                            shp = getApplicationContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=shp.edit();


                        }

                        if(!jsonObject.getString("provider_id").equals("null")) {
                            JSONObject jsonObject2 = jsonObject.getJSONObject("provider_id");
                            Log.d("JSON:", "" + jsonObject2);

                            String pname = jsonObject2.getString("pname");

                            leads1.setPame(pname);

                        }

                        if(!jsonObject.getString("c_atname").equals("null")) {
                            JSONObject jsonObject2 = jsonObject.getJSONObject("c_atname");
                            Log.d("JSON:", "" + jsonObject2);

                            String cat_name = jsonObject2.getString("categoryname");

                            leads1.setCat_name(cat_name);

                        }

                        if(!jsonObject.getString("sub_cat_name").equals("null")) {


                            JSONObject jsonObject2 = jsonObject.getJSONObject("sub_cat_name");
                            Log.d("JSON:", "" + jsonObject2);

                            String subtitle = jsonObject2.getString("subtitle");
                            leads1.setSubtitle(subtitle);


                        }

                        if(!jsonObject.getString("child_cat_name").equals("null")) {
                            JSONObject jsonObject2 = jsonObject.getJSONObject("child_cat_name");
                            Log.d("JSON:", "" + jsonObject2);

                            String childtitle = jsonObject2.getString("childtitle");

                            leads1.setChildtitle(childtitle);
                        }

                    }
                    checkIfYouAreEligible(location);



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
                map.put("status", "Pending");
                map.put("cid", category_id);

                return map;
            }

        };
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);



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


    void generateLocation2(Context context) {
        LocationRequest create = LocationRequest.create();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.getFusedLocationProviderClient((Context) this).requestLocationUpdates(create, (LocationCallback) new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationResult.getLocations().size() > 0) {
                    Log.d("RESULT", "True");
                    int size = locationResult.getLocations().size() - 1;
                    LatLng location=new LatLng(locationResult.getLocations().get(size).getLatitude(),locationResult.getLocations().get(size).getLongitude());
                    newLeads(location);
//                    updateLocation(String.valueOf(locationResult.getLocations().get(size).getLatitude()),String.valueOf(locationResult.getLocations().get(size).getLongitude()));
                    return;
                }
            }
        }, Looper.getMainLooper());
    }



}
