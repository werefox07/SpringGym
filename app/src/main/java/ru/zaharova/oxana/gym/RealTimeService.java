package ru.zaharova.oxana.gym;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class RealTimeService extends IntentService {

    public RealTimeService() {
        super("RealTimeService");
    }

    @Override
    protected void onHandleIntent(@NonNull Intent intent) {
        while (true) {
            Intent broadcastIntent = new Intent("RealTime");
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            Date date = new Date();
            broadcastIntent.putExtra("Real_time_message", dateFormat.format(date));
            sendBroadcast(broadcastIntent);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
