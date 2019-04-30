package ru.zaharova.oxana.gym.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.zaharova.oxana.gym.CircleTransformation;
import ru.zaharova.oxana.gym.LocationDataLoader;
import ru.zaharova.oxana.gym.R;
import ru.zaharova.oxana.gym.databases.DatabaseHelper;
import ru.zaharova.oxana.gym.databases.WeatherNote;
import ru.zaharova.oxana.gym.databases.WeatherTable;
import ru.zaharova.oxana.gym.rest.OpenWeatherRepo;
import ru.zaharova.oxana.gym.rest.entites.WeatherRequestRestModel;

public class WeatherRetrofitFragment extends Fragment {
    private TextView textTemp;
    private TextView textHum;
    private TextView textPress;
    private TextInputEditText editCity;
    private ImageView imageView;
    private Button button;

    private SQLiteDatabase database;
    private SharedPreferences sharedPref;
    private String TEXT_KEY_CITY_RETROFIT = "cityName";

    private Context context;

    WeatherRequestRestModel model = new WeatherRequestRestModel();
    private LocationManager locationManager;
    private String provider;
    private static final int PERMISSION_REQUEST_CODE = 10;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_weather_retrofit, container, false);
        sharedPref = ((Activity)context).getPreferences(Context.MODE_PRIVATE);
        initGui(root);
        initEvents();
        loadImage();
        initDB();

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            requestLocation();
        } else {
            requestLocationPermissions();
            requestLocation();
        }
        return root;
    }

    private void initDB() {
        database = new DatabaseHelper(context.getApplicationContext()).getWritableDatabase();
    }


    private void loadImage() {
        Picasso.get()
                .load("https://www.stihi.ru/pics/2017/12/28/6746.jpg")
                .resize(400, 400)
                .transform(new CircleTransformation())
                .into(imageView);
    }

    private void initGui(View root) {
        textTemp = root.findViewById(R.id.temp_text_view_retrofit);
        textHum = root.findViewById(R.id.hum_text_view_retrofit);
        textPress = root.findViewById(R.id.press_text_view_retrofit);
        editCity = root.findViewById(R.id.edit_city_retrofit);
        imageView = root.findViewById(R.id.image_view_retrofit);
        button = root.findViewById(R.id.button_get_city_retrofit);
    }

    private void initEvents(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferences();
                String text = Objects.requireNonNull(editCity.getText()).toString();
                requestRetrofit(text);
            }
        });
    }

    private String loadCityName() {
        return sharedPref.getString(TEXT_KEY_CITY_RETROFIT, "Moscow");
    }

    private void requestRetrofit(final String city) {
        OpenWeatherRepo.getSingleton().getAPI().loadWeather(city,
                "762ee61f52313fbd10a4eb54ae4d4de2", "metric")
                .enqueue(new Callback<WeatherRequestRestModel>() {
                    @Override
                    public void onResponse(@NonNull Call<WeatherRequestRestModel> call,
                                           @NonNull Response<WeatherRequestRestModel> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            model = response.body();
                            editCity.setText(city);
                            setTemperature();
                            setHumidity();
                            setPressure();
                            saveToBD(city);
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<WeatherRequestRestModel> call, @NonNull Throwable t) {
                        textTemp.setText(R.string.error_text);
                    }
                });
    }

    private void saveToBD(String city) {
        List<WeatherNote> weatherNotes = WeatherTable.getNote(database, city);
        if (weatherNotes.isEmpty()) {
            WeatherTable.addNote(city, model.main.temp, model.main.humidity, model.main.pressure, database);
        } else {
            WeatherTable.editNote(city, model.main.temp, model.main.humidity, model.main.pressure, database);
        }
    }

    private void setTemperature() {
        String text = getString(R.string.temperature) + ": " + model.main.temp + "\u2103";
        textTemp.setText(text);
    }

    private void setHumidity() {
        String text = getString(R.string.humidity) + ": " + model.main.humidity + " %";
        textHum.setText(text);
    }

    private void setPressure() {
        String text = getString(R.string.pressure) + ": " + model.main.pressure + " hPa";
        textPress.setText(text);
    }

    private void savePreferences() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(TEXT_KEY_CITY_RETROFIT, Objects.requireNonNull(editCity.getText()).toString());
        editor.apply();
    }

    private void requestLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
            return;
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            getWeatherByLocation(location);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("MapDemoActivity", "Error trying to get last GPS location");
                    }
                });
    }

    private void getWeatherByLocation(Location location) {
        final String latitude = Double.toString(location.getLatitude());
        final String longitude = Double.toString(location.getLongitude());
        new Thread() {
            @Override
            public void run() {
                String city = LocationDataLoader.getJSONData(context, longitude, latitude);
                if (city != null) {
                    requestRetrofit(city);
                }
            }
        }.start();
    }

    private void requestLocationPermissions() {
        ActivityCompat.requestPermissions((Activity) context,
                new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                },
                PERMISSION_REQUEST_CODE);
    }
}
