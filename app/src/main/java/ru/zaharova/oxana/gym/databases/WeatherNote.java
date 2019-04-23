package ru.zaharova.oxana.gym.databases;

import android.support.annotation.NonNull;

public class WeatherNote {
    private String city;
    private float temp;
    private int hum;
    private int press;

    WeatherNote(String city, float temp, int hum, int press) {
        this.city = city;
        this.temp = temp;
        this.hum = hum;
        this.press = press;
    }

    @NonNull
    public String getCity() {
        return city;
    }

    public float getTemp() {
        return temp;
    }

    public int getHum() {
        return hum;
    }

    public int getPress() {
        return press;
    }
}
