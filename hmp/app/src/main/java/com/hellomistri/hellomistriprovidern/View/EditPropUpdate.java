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
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

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
import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.hellomistri.hellomistriprovidern.Model.User;
import com.hellomistri.hellomistriprovidern.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditPropUpdate extends AppCompatActivity {
    CircleImageView imageView;
    Toolbar toolbar;
    ImageView uploadA,uploadP,uploadS;

    FusedLocationProviderClient fusedLocationProviderClient;
    String Longitude,Lattitude;
    EditText editText,editText1,e2,e4,e5,e6,e7,signature;

    Spinner e1,e3;
    ExtendedFloatingActionButton submit;

    ArrayList<String> cityList= new ArrayList<>();
    ArrayList<String> catlist= new ArrayList<>();

    ArrayAdapter<String> cityAdapter,catAdapter;

    User userData;
    Bitmap profileImageBitmap,aadharBitmap,panBitmap,signatureBitmap;
    AlertDialog.Builder builder;
    RequestQueue requestQueue;
    String profilePhotoPath,aadharPhotoPath,panPhotoPath;
    SharedPreferences shp;
    boolean isImageAdded = false;
    EditText permanentAddressEdit;
    String action="";
    ImageView backButton;

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
        permanentAddressEdit=findViewById(R.id.permanentAddress);
        backButton=findViewById(R.id.backBtnuyp);
//        back= findViewById(R.id.imageView);
        signature=findViewById(R.id.edit8);
        uploadS=findViewById(R.id.imgv2);
        getSupportActionBar().hide();

        BookService();
        updateLocation();

        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

     //   initToolbar();
        shp=getSharedPreferences("MySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor editor= shp.edit();

        Intent intent=getIntent();
        action= intent.getStringExtra("actionOfCustomer");


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        uploadS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder= new AlertDialog.Builder(EditPropUpdate.this);

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
               /* Intent intent = new Intent();
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



        getProDetails();

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
    private void updateCustomer(){


        shp = getApplicationContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=shp.edit();


        Log.d("updateCustomer",Lattitude+"  "+Longitude);
        String proid = shp.getString("uid", "");
        String mobile = shp.getString("mobile","");


        String city = shp.getString("city","");
        Log.d("updateCustomer",Lattitude+"  "+Longitude+"  "+mobile+"  "+city);
        String add = e2.getText().toString();
        String sc = e3.getSelectedItem().toString();
        String dis = e4.getText().toString();
        String ahar = e5.getText().toString();
        Toast.makeText(this, ""+sc, Toast.LENGTH_SHORT).show();
//        String profileImage= userData.getProfileimage();
        String aadharImage= e6.getText().toString();
        String pancardImage= e7.getText().toString();
        String signature1= signature.getText().toString();

        String url="https://hellomistri.com/index/db/api/api_update_provider_profile.php";
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        VolleyMultipartRequest volleyMultipartRequest=new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String result= new String(response.data);
                Log.d("updating_customer",result);


                Intent intent = new Intent(EditPropUpdate.this,Home.class);
                startActivity(intent);
                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            protected Map<String,String> getParams(){
                Map<String,String> map=new HashMap<>();
                Log.d("media_values",aadharImage+"  "+pancardImage);

                map.put("mobile",mobile);


                    map.put("city", city);
                    map.put("address",add);
                map.put("cid", sc);
                map.put("distance",dis);
                map.put("aadharno",ahar);
                /*map.put("afront",aadharImage);


                    map.put("aback", pancardImage);


                    map.put("pimg", signature1);*/
                    map.put("lati",Lattitude);
                    map.put("longi",Longitude);



                return map;
            }
            protected Map<String,DataPart> getByteData(){
                Map<String,DataPart> map=new HashMap<>();
                long current  = new Date().getTime();

                if (aadharBitmap != null) {
                    Log.d("afront_card","not_null");
                    map.put("afront",new DataPart("aadhaarfr"+current+".jpg",getFileDataFromDrawable(EditPropUpdate.this,aadharBitmap),"image/jpeg"));
                }
                if (panBitmap != null) {
                    Log.d("aback_card","not_null");
                    map.put("aback",new DataPart("aadhaarB"+current+".jpg",getFileDataFromDrawable(EditPropUpdate.this,panBitmap),"image/jpeg"));
                }
                if(signatureBitmap!=null){
                    Log.d("pimg_card","not_null");
                    map.put("pimg",new DataPart("profile"+current+".jpg",getFileDataFromDrawable(EditPropUpdate.this,signatureBitmap),"image/jpeg"));
                }
                return map;
            }
        };
        volleyMultipartRequest.setShouldCache(false);
        requestQueue.add(volleyMultipartRequest);

    }



    private final int requestcode = 123;
    private final int requestcode1 = 125;




    private boolean CheckAllFields() {

       /* if (profileImageBitmap == null && !action.equals("edit")){
            Toast.makeText(EditPropUpdate.this, "Please provide profile picture", Toast.LENGTH_SHORT).show();
            return false;
        }*/
       /* if (e1.length() == 0) {
            e1.setError("Name is required");
            return false;
        }*/
        if (e2.length() == 0) {
            e2.setError("Phone is required");
            return false;
        }
      /*  if (e3.length() == 0) {
            e3.setError("Email is required");
            return false;
        }*/
        if (e4.length() == 0) {
            e4.setError("Address is required");
            return false;
        } else if (e5.length() ==0) {
            e5.setError("Pincode is required");
            return false;
        }
        if (signatureBitmap == null ){
            Toast.makeText(EditPropUpdate.this, "Please provide signature", Toast.LENGTH_SHORT).show();
            return false;
        }

        // after all validation return true.
        return true;
    }



    private void askpermission() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent,requestcode);
    }
    private void askpermissionP() {
        Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
        intent1.setType("*/*");
        startActivityForResult(intent1,requestcode1);
    }
    private File createImageFile(int number) throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        switch (number){
            case 1:
                profilePhotoPath=image.getAbsolutePath();
                break;
            case 2:
                aadharPhotoPath=image.getAbsolutePath();
                break;
            case 3:
                panPhotoPath=image.getAbsolutePath();
                break;
        }
        return image;
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

                    for (int i=0;i<jsonArray.length();i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        SharedPreferences shp = getApplication().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=shp.edit();
                        String id = jsonObject.getString("id");
                        String cname = jsonObject.getString("cname");

                        editor.putString("cnn",cname);
                        editor.commit();

                        // Toast.makeText(NewRegPage.this, "City"+id, Toast.LENGTH_SHORT).show();

                        editor.putString("cityId",jsonObject.getString("id"));
                        editor.commit();

                        cityList.add(cname);
                        cityAdapter=new ArrayAdapter<String>(EditPropUpdate.this, android.R.layout.simple_list_item_1,cityList);
                        cityAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        e1.setAdapter(cityAdapter);



                    }
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

                    for (int i=0;i<jsonArray.length();i++)
                    {
                        SharedPreferences shp = getApplication().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=shp.edit();
                        JSONObject jsonObject= jsonArray.getJSONObject(i);


                        String id = jsonObject.getString("id");
                        String cat_name = jsonObject.getString("cat_name");
                        Log.d("bookservice",jsonObject.toString());
                        /*editor.putString("uid",jsonObject.getString("id"));*/

                        editor.commit();


                        catlist.add(cat_name);
                        catAdapter=new ArrayAdapter<String>(EditPropUpdate.this, android.R.layout.simple_list_item_1,catlist);
                        catAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        e3.setAdapter(catAdapter);


                        editor.putString("id",jsonObject.getString("id"));
                        editor.commit();


                    }
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
        requestQueue.getCache().clear();

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
    private void imageDialog(int cameraCode, int galleryCode) {
        if (ContextCompat.checkSelfPermission(EditPropUpdate.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(EditPropUpdate.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(EditPropUpdate.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Log.d("camera_permission", "called intent");
            Toast.makeText(EditPropUpdate.this, "Allow camera & gallery permissions first", Toast.LENGTH_SHORT).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(EditPropUpdate.this, Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(EditPropUpdate.this, new String[]{Manifest.permission.CAMERA}, 107);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(EditPropUpdate.this);
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

    private void getLastLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location !=null) {
                                Geocoder geocoder=new Geocoder(EditPropUpdate.this, Locale.getDefault());
                                List<Address> addresses= null;
                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                    /*      latitude.setText("Lagitude :" +addresses.get(0).getLatitude());
                                    longitude.setText("Longitude :"+addresses.get(0).getLongitude());*/

                                    Lattitude = String.valueOf(location.getLatitude());
                                    Longitude = String.valueOf(location.getLongitude());

                                    e2.setText(""+addresses.get(0).getAddressLine(0));
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
    }

    private boolean checkAllFeilds() {

        if (editText.length() == 0) {
            editText.setError("required");
            return false;
        }
        if (editText1.length() == 0) {
            editText1.setError("required");
            return false;
        }

        if (e2.length() == 0) {
            e2.setError("required");
            return false;

        }
        if (e4.length() == 0) {
            e4.setError("required");
            return false;

        }
        if (e5.length() == 0) {
            e5.setError("required");
            return false;

        }
        if (e6.length() == 0) {
            e6.setError("required");
            return false;

        }

        if (e7.length() == 0) {
            e7.setError("required");
            return false;

        }
        if (signature.length() == 0) {
            signature.setError("required");
            return false;

        }


        return true;
    }



    private void askPermission() {
        ActivityCompat.requestPermissions(EditPropUpdate.this, new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
    }

    private void getProDetails() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
        SharedPreferences shp = EditPropUpdate.this.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=shp.edit();
        String proid = shp.getString("uid", "");

        String url="https://hellomistri.com/index/db/api/get_profile.php";
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.d("profile_details",response);


                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String pid = jsonObject.getString("id");
                        String pname = jsonObject.getString("name");
                        String pemail = jsonObject.getString("email");
                        String pmobile = jsonObject.getString("mobile");
//                        pcategory = jsonObject.getString("category_id");
                        String paddress = jsonObject.getString("address");
//                        pcity = jsonObject.getString("city");
                        String pccode = jsonObject.getString("ccode");
                        String padharno = jsonObject.getString("adharno");
                        String paback = jsonObject.getString("aback");
                        String pafront = jsonObject.getString("afront");
                        String paprove = jsonObject.getString("aprove");
                        String pimg = jsonObject.getString("pimg");
                        String pdis = jsonObject.getString("distance");
                        String permanentAddress= jsonObject.getString("parmanent_address");

                        e4.setText(""+pdis);
                        e5.setText(""+padharno);
                        e6.setText(""+pafront);
                        e7.setText(""+paback);
                        permanentAddressEdit.setText(""+permanentAddress);









                        //  Toast.makeText(Edit_User.this, "" + pimg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
//                    throw new RuntimeException(e);
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
                map.put("id",proid );

                return map;
            }

        };
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);

    }

}
