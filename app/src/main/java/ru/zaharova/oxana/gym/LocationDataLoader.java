package ru.zaharova.oxana.gym;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LocationDataLoader {
    private static final String YA_MAPS_API_URL = "https://geocode-maps.yandex.ru/1.x/?geocode=%s,%s&format=json&results=1&kind=locality&lang=en_RU";

    public static String getJSONData(Context context, String longitude, String latitude) {
        try {
            URL url = new URL(String.format(YA_MAPS_API_URL, longitude, latitude));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder rawData = new StringBuilder(1024);
            String tempVariable;

            while ((tempVariable = reader.readLine()) != null) {
                rawData.append(tempVariable).append("\n");
            }

            reader.close();

            JSONObject jsonObject = new JSONObject(rawData.toString());
            return getLocationFromResponse(jsonObject);

        } catch (Exception exc) {
            exc.printStackTrace();
            return null;
        }
    }

    private static String getLocationFromResponse(JSONObject jsonObject) {
        try {
            return jsonObject.getJSONObject("response")
                    .getJSONObject("GeoObjectCollection")
                    .getJSONArray("featureMember")
                    .getJSONObject(0)
                    .getJSONObject("GeoObject")
                    .getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
