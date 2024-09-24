package com.hellomistri.hellomistriprovidern.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hellomistri.hellomistriprovidern.BuildConfig;
import com.hellomistri.hellomistriprovidern.R;


public class ShareApp extends Fragment {



    Button button;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share_app, container, false);

        button= view.findViewById(R.id.shsr);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String shareMessage= "\nInstall this application this is helpful for you.\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT,shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "Share"));
                    getActivity().finish();
                } catch(Exception e)
                {

                }
            }
        });
        return view;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().finish();
    }
}
