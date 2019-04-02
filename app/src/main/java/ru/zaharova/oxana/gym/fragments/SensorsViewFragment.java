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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import ru.zaharova.oxana.gym.R;

import static android.support.v4.content.ContextCompat.getSystemService;

@SuppressLint("ValidFragment")
public class SensorsViewFragment extends Fragment {
    private TextView textConsole;
    private TextView textLight;
    private SensorManager sensorManager;
    private List<Sensor> sensors;
    private Sensor sensorLight;
    private Context context;

    public SensorsViewFragment(Context context) {
        super();
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sensors_view, container, false);
        textConsole = root.findViewById(R.id.textConsole);
        textLight = root.findViewById(R.id.textLight);
        // Менеджер датчиков
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        // Получить все датчики, какие есть
        sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        // Датчик освещенности (он есть на многих моделях)
        sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        // Регистрируем слушатель датчика освещенности
        sensorManager.registerListener(listenerLight, sensorLight, SensorManager.SENSOR_DELAY_NORMAL);
        // Показать все сенсоры, какие есть
        showSensors();
        return root;
    }

    // Вывод всех сенсоров
    private void showSensors() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Sensor sensor : sensors) {
            stringBuilder.append("name = ").append(sensor.getName())
                    .append(", type = ").append(sensor.getType())
                    .append("\n")
                    .append("vendor = ").append(sensor.getVendor())
                    .append(" ,version = ").append(sensor.getVersion())
                    .append("\n")
                    .append("max = ").append(sensor.getMaximumRange())
                    .append(", resolution = ").append(sensor.getResolution())
                    .append("\n").append("--------------------------------------").append("\n");
        }
        textConsole.setText(stringBuilder);
    }

    // Вывод датчика освещенности
    private void showLightSensors(SensorEvent event){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Light Sensor value = ").append(event.values[0])
                .append("\n").append("=======================================").append("\n");
        textLight.setText(stringBuilder);
    }

    // Слушатель датчика освещенности
    SensorEventListener listenerLight = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            showLightSensors(event);
        }
    };



}


