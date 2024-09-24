package com.hellomistri.hellomistriprovidern.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hellomistri.hellomistriprovidern.R;

public class Wallet extends AppCompatActivity {

    CardView addMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);



        CardView selectAmount = (CardView) findViewById(R.id.selectAmount);
        Button submit = (Button)  findViewById(R.id.Submit);

        selectAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(Wallet.this, "Amount Selected", Toast.LENGTH_SHORT).show();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Toast.makeText(Wallet.this, "Submit Successfully", Toast.LENGTH_SHORT).show();
            }
        });




    }
}