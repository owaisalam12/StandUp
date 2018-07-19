package com.example.oalam.standup;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    private static final String ACTION_NOTIFY =
            "com.example.oalam.standup.ACTION_NOTIFY";
    ToggleButton toggleButton;
    NotificationManager mNotificationManager;
    int NOTIFICATION_ID=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNotificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Intent notifyIntent = new Intent(ACTION_NOTIFY);
        boolean alarmUp = (PendingIntent.getBroadcast(this, NOTIFICATION_ID, notifyIntent,
                PendingIntent.FLAG_NO_CREATE) != null);
        toggleButton=(ToggleButton)findViewById(R.id.alarmToggle);
        toggleButton.setChecked(alarmUp);
      

        final PendingIntent notifyPendingIntent = PendingIntent.getBroadcast
                (this, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                String toastMessage;
                if(isChecked){
                    //deliverNotification(MainActivity.this);
                    //Set the toast message for the "on" case
                    toastMessage = getString(R.string.alarm_on_toast);
                    //If the Toggle is turned on, set the repeating alarm with a 15 minute interval
                    alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                            SystemClock.elapsedRealtime()+AlarmManager.INTERVAL_FIFTEEN_MINUTES
                            ,AlarmManager.INTERVAL_FIFTEEN_MINUTES,notifyPendingIntent);

                } else {
                   mNotificationManager.cancelAll();  //Cancel notification if the alarm is turned off
                    alarmManager.cancel(notifyPendingIntent);
                    //Set the toast message for the "off" case
                    toastMessage = getString(R.string.alarm_off_toast);
                }

//Show a toast to say the alarm is turned on or off
                Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_SHORT)
                        .show();


            }
        });



    }
    private void deliverNotification(Context context){

    }
}
