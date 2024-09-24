package com.hellomistri.hellomistriprovidern.BottomSheetDialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.hellomistri.hellomistriprovidern.R;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class DialogCredit extends BottomSheetDialogFragment  implements PaymentResultListener {

    Button submit;
    Context activity;

    String Name,Mobile;

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        super.setupDialog(dialog, style);



        
        View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_wallet, null);
        submit= view.findViewById(R.id.Submit);


        SharedPreferences shp = getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=shp.edit();


        Name = shp.getString("name","");
        Mobile = shp.getString("mobile","");
       String email = shp.getString("email","");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Activity activity = getActivity();
                Checkout.preload(getContext());


             //   Toast.makeText(getActivity(), "Hello Submit", Toast.LENGTH_SHORT).show();

                   int payAmount = 1;


                    payAmount = payAmount * 100;
                    final String pay = String.valueOf(payAmount);

                    Checkout checkout = new Checkout();
                    checkout.setKeyID("rzp_live_ZS2nUuoGAbZ986");

                    checkout.setImage(R.drawable.hllo);





                    try {

                        JSONObject options = new JSONObject();
                        options.put("name", "Hello Mistri Provider");

                        options.put("theme.color", "#3399cc");
                        options.put("currency", "INR");
                        options.put("amount", pay);
                        options.put("prefill.name",Name);
                        options.put("prefill.email",email);
                        options.put("prefill.contact",Mobile);

                        JSONObject retryObj = new JSONObject();
                        retryObj.put("enabled", true);
                        retryObj.put("max_count", 4);
                        options.put("retry", retryObj);

                        checkout.open(activity, options);

                    } catch(Exception e) {
                        Log.e("TAG", "Error in starting Razorpay Checkout", e);
                    }
                }

        });
        dialog.setContentView(view);

    }


    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(activity.getApplicationContext(), "Payment Successfull"+s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {

        Toast.makeText(activity.getApplicationContext(), ""+s, Toast.LENGTH_SHORT).show();
        Log.d("Razorpay","razorpay");

    }
}
