package com.hellomistri.hellomistriprovidern.Service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.hellomistri.hellomistriprovidern.R;
import com.hellomistri.hellomistriprovidern.View.Home;
import com.hellomistri.hellomistriprovidern.utils.AppUtils;

public class MyFirebaseMasseging extends FirebaseMessagingService {
    public static String INTENT_FILTER_ORDERS="INTENT_FILTER_ORDERS";
    MediaPlayer mp;
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        Log.d("MyFirebaseMessege","called");

        String title= message.getData().get("title");
        Log.d("MyFirebaseMessege","called"+title);

        if(title.equals("new_lead_found")){
            notifyUser(this);
        }
    }
    private void notifyUser(Context context){
        setPlayer();
        if(AppUtils.isAppRunning(context,getPackageName())){
            Intent intent1=new Intent(INTENT_FILTER_ORDERS);
            intent1.putExtra("from_notif","true");
            context.sendBroadcast(intent1);
        }
        NotificationChannel notificationChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(
                    "1234", "Lead Notifier Notification", NotificationManager.IMPORTANCE_LOW
            );
            notificationChannel.enableVibration(true);
            getSystemService(NotificationManager.class).createNotificationChannel(notificationChannel);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, Home.class), PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"1234")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("New Leads found in your area")
                .setContentText("Tap to check it out")
                .setContentIntent(contentIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat=  NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1,builder.build());

    }
    private void setPlayer(){
        mp= MediaPlayer.create(getApplicationContext(), R.raw.ring_bell);
        mp.start();
        HandlerThread handlerThread = new HandlerThread("background-thread");
        handlerThread.start();
        Handler handler=new Handler(handlerThread.getLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mp.stop();
            }
        },6000);
    }
}
