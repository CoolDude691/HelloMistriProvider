package com.hellomistri.hellomistriprovidern.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.hellomistri.hellomistriprovidern.R;


public class User_Profile extends Fragment {

    ImageView edtProfile;
    TextView subc;
   TextInputEditText nameU,email,mobole,address,pincode,edSer;
    SharedPreferences shp;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view= inflater.inflate(R.layout.fragment_user__profile, container, false);

        nameU= view.findViewById(R.id.userName);
        email = view.findViewById(R.id.userEmail);
        mobole = view.findViewById(R.id.userPhone);
        address = view.findViewById(R.id.userAddress);
        pincode = view.findViewById(R.id.userPincode);




        shp = getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=shp.edit();


        String name = shp.getString("name", "");
        String email1 = shp.getString("email", "");
        String mobile = shp.getString("mobile", "");
        String address1 = shp.getString("address", "");
        String pinc = shp.getString("pincode", "");
        String sc = shp.getString("subcategory", "");
      //  String pinc = shp.getString("pincode", "");
        //  String pinc = shp.getString("pincode", "");


        nameU.setText(name);
        email.setText(email1);
        mobole.setText(mobile);
        address.setText(address1);
        pincode.setText(pinc);
      /*  subc.setText(sc);
        edSer.setText("Digital Marketing Services");*/


      //  edtProfile=view.findViewById(R.id.edtProp);

      /*  edtProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Edit_User.class);
                startActivity(intent);
            }
        });*/

        return  view;

    }

}