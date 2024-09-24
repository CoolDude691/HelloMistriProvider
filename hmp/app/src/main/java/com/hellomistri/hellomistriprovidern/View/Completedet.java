package com.hellomistri.hellomistriprovidern.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hellomistri.hellomistriprovidern.R;

public class Completedet extends AppCompatActivity {
    TextView location,call,txttitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comp_det);
        location= findViewById(R.id.txt_location);
        call=findViewById(R.id.txt_call);

        txttitle=findViewById(R.id.txt_title);



        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number= String.valueOf(123);

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+number));//change the number
                startActivity(callIntent);
                finish();


            }
        });

        /*Leads object= (Leads) getIntent().getSerializableExtra("leaddet");

        txttitle.setText(""+object.getName());*/













    }
}