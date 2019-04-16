package ru.zaharova.oxana.gym.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.zaharova.oxana.gym.R;
import ru.zaharova.oxana.gym.rest.OpenWeatherRepo;
import ru.zaharova.oxana.gym.rest.entites.WeatherRequestRestModel;

public class WeatherRetrofitFragment extends Fragment {
    private TextView textTemp;
    private TextInputEditText editCity;
    private ImageView imageView;
    private Button button;

    private Context context;

    WeatherRequestRestModel model = new WeatherRequestRestModel();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_weather_retrofit, container, false);
        initGui(root);
        initEvents();
        return root;
    }

    private void initGui(View root) {
        textTemp = root.findViewById(R.id.temp_text_view_retrofit);
        editCity = root.findViewById(R.id.edit_city_retrofit);
        imageView = root.findViewById(R.id.image_view_retrofit);
        button = root.findViewById(R.id.button_get_city_retrofit);
    }

    private void initEvents(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = Objects.requireNonNull(editCity.getText()).toString();
                requestRetrofit(text);
            }
        });
    }

    private void requestRetrofit(String city) {
        OpenWeatherRepo.getSingleton().getAPI().loadWeather(city,
                "762ee61f52313fbd10a4eb54ae4d4de2", "metric")
                .enqueue(new Callback<WeatherRequestRestModel>() {
                    @Override
                    public void onResponse(@NonNull Call<WeatherRequestRestModel> call,
                                           @NonNull Response<WeatherRequestRestModel> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            model = response.body();
                            setTemperature();
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherRequestRestModel> call, Throwable t) {
                        textTemp.setText(R.string.error_text);
                    }
                });
    }

    private void setTemperature() {
        String text = getString(R.string.temperature) + ": " + String.valueOf(model.main.temp);
        textTemp.setText(text);
    }
}
