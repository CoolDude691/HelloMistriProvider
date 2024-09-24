package com.hellomistri.hellomistriprovidern.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hellomistri.hellomistriprovidern.R;


public class FragSet extends Fragment {

 TextView nameProp,linkTextView;
 SharedPreferences shp;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frag_set, container, false);

        nameProp= view.findViewById(R.id.UseRName);
        linkTextView =view.findViewById(R.id.hyper);
        shp = getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=shp.edit();
        String name = shp.getString("name", "");

        nameProp.setText(name);

      //  setupHyperlink();

        return  view;
    }
}