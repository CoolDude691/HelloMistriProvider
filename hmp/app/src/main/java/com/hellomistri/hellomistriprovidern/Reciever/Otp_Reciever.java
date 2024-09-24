package com.hellomistri.hellomistriprovidern.Reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.EditText;

public class Otp_Reciever  extends BroadcastReceiver {


    private static EditText editText;

    public void setEditText(EditText editText)
    {
        Otp_Reciever.editText = editText;
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);

        for (SmsMessage sms : messages)
        {
            String message = sms.getMessageBody();
            String otp = message.split(": ")[1];
            editText.setText(otp);

        }
    }
}
