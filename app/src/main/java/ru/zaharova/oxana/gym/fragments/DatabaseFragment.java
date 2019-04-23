package ru.zaharova.oxana.gym.fragments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.zaharova.oxana.gym.R;
import ru.zaharova.oxana.gym.databases.DatabaseHelper;
import ru.zaharova.oxana.gym.databases.WeatherNote;
import ru.zaharova.oxana.gym.databases.WeatherTable;

public class DatabaseFragment extends Fragment {
    private Context context;
    private SQLiteDatabase database;
    private TextView textView;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.database_layout, container, false);
        database = new DatabaseHelper(context.getApplicationContext()).getWritableDatabase();
        initGUI(root);
        return root;
    }

    private void initGUI(View root) {
        textView = root.findViewById(R.id.text_view_database);
        List<WeatherNote> notesWeather = WeatherTable.getAllNotes(database);
        StringBuilder text = new StringBuilder();
        for (WeatherNote weatherNote : notesWeather) {
            text.append(weatherNote.getCity()).append(" ").append(weatherNote.getTemp()).append(" ").append(weatherNote.getHum()).append(" ").append(weatherNote.getPress()).append("\n");
        }
        textView.setText(text.toString());
    }
}