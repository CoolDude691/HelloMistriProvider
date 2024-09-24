package com.hellomistri.hellomistriprovidern.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hellomistri.hellomistriprovidern.R;

public class DocumentVerifAct extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    FloatingActionButton logoutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_verif);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.parseColor("#002947"));

        logoutButton=findViewById(R.id.logoutBtnDoc);

        sharedPreferences=getSharedPreferences("MySharedPref",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.clear();
                editor.commit();
                startActivity(new Intent(DocumentVerifAct.this,SignIn.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        });



    }
}