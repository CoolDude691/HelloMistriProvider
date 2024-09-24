package com.hellomistri.hellomistriprovidern.View;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.hellomistri.hellomistriprovidern.R;

public class Select_Language extends AppCompatActivity {

    ExtendedFloatingActionButton submit;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_language);
        getSupportActionBar().hide();
        submit=findViewById(R.id.submitLang);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Select_Language.this,SignIn.class);
                startActivity(intent);
                finish();
            }
        });



    }
}