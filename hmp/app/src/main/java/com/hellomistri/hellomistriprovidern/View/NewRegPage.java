package com.hellomistri.hellomistriprovidern.View;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.hellomistri.hellomistriprovidern.Model.Category;
import com.hellomistri.hellomistriprovidern.Model.City;
import com.hellomistri.hellomistriprovidern.Model.User;
import com.hellomistri.hellomistriprovidern.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewRegPage extends AppCompatActivity {
    CircleImageView imageView;
    Toolbar toolbar;
    ImageView uploadA,uploadP,back,uploadS;
    EditText editText,editText1,e2,e4,e5,e6,e7,signature,address,permanentAddress;

    Spinner e1,e3;


    ExtendedFloatingActionButton submit;
    String Lattitude,Longitude;

     FusedLocationProviderClient fusedLocationProviderClient;

    ArrayList<String> cityList= new ArrayList<>();
    List<City> cityTypeList=new ArrayList<>();
    String cityId="";
    ArrayList<String> catlist= new ArrayList<>();

    ArrayList<String> categoryList= new ArrayList<>();
    ArrayList<Category> categoryTypeList=new ArrayList<>();
    String categoryId="";
    ArrayAdapter<String> cityAdapter,catAdapter;

    User userData;
    Bitmap profileImageBitmap,aadharBitmap,panBitmap,signatureBitmap;
    AlertDialog.Builder builder;
    RequestQueue requestQueue;
    String profilePhotoPath,aadharPhotoPath,panPhotoPath;
    SharedPreferences shp;
    boolean isImageAdded = false;
    String action="";
    String token="";


    private  int request_code= 100;

    private  final  static int REQUEST_CODE=100;







    @SuppressLint({"WrongViewCast", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_prop_update);
//        getSupportActionBar().hide();
        requestQueue = Volley.newRequestQueue(this);
        //  imageView = findViewById(R.id.profile);
        uploadA= findViewById(R.id.imgv);
        uploadP = findViewById(R.id.imgv1);
        editText = findViewById(R.id.edit6);
        editText1=findViewById(R.id.edit7);
        submit=findViewById(R.id.btnsub);
        e1=findViewById(R.id.spinner_city);
        e2=findViewById(R.id.edit2);
        e3=findViewById(R.id.edit3);
        e4=findViewById(R.id.edit4);
        e5=findViewById(R.id.edit5);
        e6=findViewById(R.id.edit6);
        e7=findViewById(R.id.edit7);
        permanentAddress=findViewById(R.id.permanentAddress);
//        back= findViewById(R.id.imageView);
        signature=findViewById(R.id.edit8);
        uploadS=findViewById(R.id.imgv2);
        address = findViewById(R.id.edit2);


        BookService();
        updateLocation();

        cityList.add("Select City");
        catlist.add("Select Category");


        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

        //   initToolbar();
        shp=getSharedPreferences("MySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor editor= shp.edit();

        Intent intent=getIntent();
        action= intent.getStringExtra("actionOfCustomer");


        cityAdapter=new ArrayAdapter<String>(NewRegPage.this, android.R.layout.simple_list_item_1,cityList);
        cityAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        e1.setAdapter(cityAdapter);

        catAdapter=new ArrayAdapter<String>(NewRegPage.this, android.R.layout.simple_list_item_1,catlist);
        catAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        e3.setAdapter(catAdapter);

        uploadS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder= new AlertDialog.Builder(NewRegPage.this);

                builder.setTitle("terms & conditions");

                builder.setMessage("I agree with the above terms & condition")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                /*Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent,""),11);*/
                                imageDialog(211,111);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();

                            }
                        });
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.show();

            }
        });

        /*back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(add_customer.this, customer.class);
                startActivity(intent);
            }
        });*/

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckAllFields()) {

                    updateCustomer();

                }

            }
        });
        uploadA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,""),112);*/
                imageDialog(212,112);
            }
        });

        uploadP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,""),113);*/
                imageDialog(213,113);
            }
        });



        e1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        e3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                }else{
                    String item = adapterView.getItemAtPosition(i).toString();
                    for(int j=0;j<categoryTypeList.size();j++){
                        if(String.valueOf(categoryTypeList.get(j).getCat_name()).equals(item)){
                            Log.d("categoryId",categoryTypeList.get(j).getCat_name()+" "+categoryTypeList.get(j).getId());
                            categoryId=categoryTypeList.get(j).getId();
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




    }
    @SuppressLint("Range")
    public String getRealPathFromURI(Uri uri) {
        String res = null;
        if (uri.getScheme().equals("content")){
            Cursor cursor = getApplicationContext().getContentResolver().query(uri,null,null,null,null);

            try {
                if (cursor != null && cursor.moveToFirst())
                {
                    res = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));

                }
            }finally {
                cursor.close();
            }
            if (res == null)
            {
                res = uri.getPath();
                int cutt = res.lastIndexOf('/');
                if (cutt != -1)
                {
                    res = res.substring(cutt +1);
                }

            }
        }
        return res;
    }

    public byte[] getFileDataFromDrawable(Context context, Bitmap bitmap) {
//        Bitmap bitmap = photo;
//        bitmap=rotateImage(bitmap,90);
//        bitmap= getResizedBitmap(bitmap,1920);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    private void getLastLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location !=null) {
                                Geocoder geocoder=new Geocoder(NewRegPage.this, Locale.getDefault());
                                List<Address> addresses= null;
                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                    /*      latitude.setText("Lagitude :" +addresses.get(0).getLatitude());
                                    longitude.setText("Longitude :"+addresses.get(0).getLongitude());*/

                                    Lattitude = String.valueOf(location.getLatitude());
                                    Longitude = String.valueOf(location.getLongitude());

                                    address.setText(""+addresses.get(0).getAddressLine(0));
                                    // addressView.setText("Address :"+addresses.get(0).getAddressLine(0));



                                } catch (IOException e) {

                                    e.printStackTrace();

                                }
                            }
                        }
                    });
        }
        else{

            askPermission();

        }
        fireBaseInitialize();
    }


    private void askPermission() {
        ActivityCompat.requestPermissions(NewRegPage.this, new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
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
    private void updateCustomer(){


        shp = getApplicationContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=shp.edit();


      //  String proid = shp.getString("uid", "");





        String mobile = shp.getString("edited_mobile","");
        Log.d("mobile_value_reg",mobile+" "+cityId);
        String city = e1.getSelectedItem().toString();
        String add = e2.getText().toString();
        String sc = e3.getSelectedItem().toString();
        String dis = e4.getText().toString();
        String ahar = e5.getText().toString();



        String aadharImage= e6.getText().toString();
        String pancardImage= e7.getText().toString();
        String signature1= signature.getText().toString();

        Log.d("updating_customer",categoryId);

        String url="https://hellomistri.com/index/db/api/image_upload.php";
        RequestQueue requestQueue=Volley.newRequestQueue(this);
       VolleyMultipartRequest volleyMultipartRequest=new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String result= new String(response.data);
                Log.d("updating_customer",result);

                try{
                    JSONObject jsonObject= new JSONObject(result);
                    if(jsonObject.getBoolean("status")){
                        JSONObject userObj= jsonObject.getJSONObject("data");
                        editor.putBoolean("isLogin", true);
                        editor.putString("uid",userObj.getString("id"));
                        editor.putString("name",userObj.getString("name"));
                        editor.putString("email",userObj.getString("email"));
                        editor.putString("mobile",userObj.getString("mobile"));
                        editor.putString("city",userObj.getString("city"));
                        editor.putString("address",userObj.getString("address"));
                        editor.putString("password",userObj.getString("password"));
                        editor.putString("category_id",userObj.getString("category_id"));
                        editor.commit();
                        Intent intent = new Intent(NewRegPage.this,RefferalActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(NewRegPage.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                map.put("mobile",mobile);
                Log.d("mob",""+mobile);
//                map.put("city", cityId);
                Log.d("mob",""+cityId);
                map.put("address",add);
                Log.d("mob",""+add);
                map.put("cid", categoryId);
                Log.d("mob",""+sc);
                map.put("distance",dis);
                Log.d("mob",""+dis);
                map.put("aadharno",ahar);
                Log.d("mob",""+ahar);
                map.put("afront",aadharImage);
                Log.d("mob",""+aadharImage);
                map.put("aback", pancardImage);
                Log.d("mob",""+pancardImage);
                map.put("pimg", signature1);
                Log.d("mob",""+signature1);
                map.put("longi", Longitude);
                Log.d("mob",""+Longitude);
                map.put("lati", Lattitude);
                map.put("fcm_token",token);
                Log.d("mob",""+Lattitude);
                map.put("permanent_address",permanentAddress.getText().toString());



                return map;
            }
            protected Map<String,DataPart> getByteData(){
                Map<String,DataPart> map=new HashMap<>();
                long current  = new Date().getTime();

                if (aadharBitmap != null) {
                    map.put("afront",new DataPart("aadhaarfr"+current+".jpg",getFileDataFromDrawable(NewRegPage.this,aadharBitmap),"image/jpeg"));
                }
                if (panBitmap != null) {
                    map.put("aback",new DataPart("aadhaarB"+current+".jpg",getFileDataFromDrawable(NewRegPage.this,panBitmap),"image/jpeg"));
                }
                if(signatureBitmap!=null){
                    map.put("pimg",new DataPart("profile"+current+".jpg",getFileDataFromDrawable(NewRegPage.this,signatureBitmap),"image/jpeg"));
                }
                return map;
            }
        };
        volleyMultipartRequest.setShouldCache(false);
        requestQueue.add(volleyMultipartRequest);

    }



    private final int requestcode = 123;
    private final int requestcode1 = 125;


    private void imageDialog(int cameraCode, int galleryCode) {
        if (ContextCompat.checkSelfPermission(NewRegPage.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(NewRegPage.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(NewRegPage.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Log.d("camera_permission", "called intent");
            Toast.makeText(NewRegPage.this, "Allow camera & gallery permissions first", Toast.LENGTH_SHORT).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(NewRegPage.this, Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(NewRegPage.this, new String[]{Manifest.permission.CAMERA}, 107);
            } else {
                Intent i = new Intent();
                i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                i.addCategory(Intent.CATEGORY_DEFAULT);
                i.setData(Uri.parse("package:" + getPackageName()));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(i);
            }

        } else {
            selectImage(cameraCode, galleryCode);
        }
    }
    private void selectImage(int cameraCode, int galleryCode) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(NewRegPage.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, cameraCode);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "image"), galleryCode);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    private boolean CheckAllFields() {



        if (e2.length() == 0) {
            e2.setError("Phone is required");
            return false;
        }

        if (e4.length() == 0) {
            e4.setError("Address is required");
            return false;
        } else if (e5.length() ==0) {
            e5.setError("Pincode is required");
            return false;
        } else if(TextUtils.isEmpty(permanentAddress.getText().toString())){
           permanentAddress.setError("Permanent address is required");
           return false;
        }
        if (signatureBitmap == null && !action.equals("edit")){
            Toast.makeText(NewRegPage.this, "Please provide signature", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }


    private void updateLocation() {




        String url="https://hellomistri.com/index/db/city.php";
        RequestQueue requestQueue= Volley.newRequestQueue(this);


        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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

                        // Toast.makeText(NewRegPage.this, "City"+id, Toast.LENGTH_SHORT).show();

                        editor.putString("cityId",jsonObject.getString("id"));
                        editor.commit();

                        cityList.add(cname);*/
//                        cityList.add(id);

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



    private void BookService() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();


        String url="https://hellomistri.com/Api/getCategory";
        RequestQueue requestQueue= Volley.newRequestQueue(this);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();



                try {
                    JSONArray jsonArray = new JSONArray(response);
                    Gson gson=new Gson();
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject= jsonArray.getJSONObject(i);
                        categoryTypeList.add(gson.fromJson(String.valueOf(jsonObject),Category.class));
                        /*SharedPreferences shp = getApplication().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=shp.edit();


                        String id = jsonObject.getString("id");
                        String cat_name = jsonObject.getString("cat_name");
                        editor.putString("uid",jsonObject.getString("id"));

                        editor.commit();*/


                        catlist.add(categoryTypeList.get(i).cat_name);



                        /*editor.putString("id",jsonObject.getString("id"));
                        editor.commit();*/

                    }
                    catAdapter.notifyDataSetChanged();




                } catch (JSONException e) {
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }

        });


        requestQueue.add(stringRequest);
        requestQueue.getCache().

                clear();




    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode==REQUEST_CODE){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }
            else {
                //      Toast.makeText(this, "Required Permission", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }









    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode==0  && data!=null)
        {


            Bundle b = data.getExtras();
            profileImageBitmap = (Bitmap) b.get("data");

            imageView.setImageBitmap(profileImageBitmap);
        }


        /*if (requestCode==111   && data!=null)
        {
            Uri imageUri = data.getData();

            try {
                profileImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                imageView.setImageBitmap(profileImageBitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

        if(requestCode==212 && data!=null){
            editText.setText("Image added");
            Bundle b = data.getExtras();
            aadharBitmap = (Bitmap) b.get("data");
        }
        if(requestCode==213 && data!=null){
            editText1.setText("Image Added");
            Bundle b = data.getExtras();
            panBitmap = (Bitmap) b.get("data");
        }
        if(requestCode==211 && data!=null){
            Bundle b = data.getExtras();
            signatureBitmap = (Bitmap) b.get("data");
            signature.setText("Image added");
        }
        if (requestCode == 112 && data!=null)
        {
            Uri imageUri = data.getData();
            editText.setText(getRealPathFromURI(imageUri));


            try {
                aadharBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == 113 && data!=null)
        {
            Uri imageUri = data.getData();
            editText1.setText(getRealPathFromURI(imageUri));
            isImageAdded =true;
            try {
                panBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == 111 && data!=null)
        {
            Uri imageUri = data.getData();
            signature.setText(getRealPathFromURI(imageUri));

            isImageAdded =true;
            try {
                signatureBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }





}
