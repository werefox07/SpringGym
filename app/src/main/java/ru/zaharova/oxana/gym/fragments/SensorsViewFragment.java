package ru.zaharova.oxana.gym.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.zaharova.oxana.gym.R;

public class SensorsViewFragment extends Fragment {
    private TextView textHum;
    private TextView textTemp;
    private Context context;
    private SensorManager sensorManager;
    private Sensor sensorTemp;
    private Sensor sensorHum;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sensors_view, container, false);
        textTemp = root.findViewById(R.id.textTemp);
        textHum = root.findViewById(R.id.textHum);

        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        sensorTemp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        if(sensorTemp != null) {
            sensorManager.registerListener(listenerTemp, sensorTemp, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            textTemp.setText(getString(R.string.sensor_temp_error));
        }

        sensorHum = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        if(sensorHum != null) {
            sensorManager.registerListener(listenerHum, sensorHum, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            textHum.setText(getString(R.string.sensor_hum_error));
        }
        return root;
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listenerTemp, sensorTemp);
        sensorManager.unregisterListener(listenerHum, sensorHum);
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(listenerTemp, sensorTemp, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(listenerHum, sensorHum, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void showSensorValue(SensorEvent event, String title, TextView textView){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(title).append(event.values[0])
                .append("\n").append("=======================================").append("\n");
        textView.setText(stringBuilder);
    }

    SensorEventListener listenerTemp = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            showSensorValue(event, "Temperature Sensor value = ", textTemp);
        }
    };

    SensorEventListener listenerHum = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            showSensorValue(event, "Humidity Sensor value = ", textHum);
        }
    };
}


