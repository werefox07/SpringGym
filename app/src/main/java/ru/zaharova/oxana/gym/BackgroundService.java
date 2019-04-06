package ru.zaharova.oxana.gym;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

public class BackgroundService extends IntentService {
    int messageId = 0;
    float prevValueTemp = 0;
    float prevValueHum = 0;
    float e = 1;


    public BackgroundService() {
        super("BackgroundService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        Sensor sensorTemp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        if(sensorTemp != null) {
            sensorManager.registerListener(listenerTemp, sensorTemp, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            makeNote(getString(R.string.sensor_temp_error));
        }

        Sensor sensorHum = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        if(sensorHum != null) {
            sensorManager.registerListener(listenerHum, sensorHum, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            makeNote(getString(R.string.sensor_hum_error));
        }
    }

    private void makeNote(String message){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Main service notification")
                .setContentText(message);
        Intent resultIntent = new Intent(this, BackgroundService.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        builder.setContentIntent(resultPendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(messageId++, builder.build());
    }

    private boolean checkDiff(float val, float prevVal) {
        return Math.abs(val - prevVal) > e;
    }

    SensorEventListener listenerTemp = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (checkDiff(event.values[0], prevValueTemp)) {
                makeNote("Temperature sensor value: " + String.valueOf(event.values[0]));
                prevValueTemp = event.values[0];
            }
        }
    };

    SensorEventListener listenerHum = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (checkDiff(event.values[0], prevValueHum)) {
                makeNote("Humidity sensor value = " + String.valueOf(event.values[0]));
                prevValueHum = event.values[0];
            }
        }
    };
}
