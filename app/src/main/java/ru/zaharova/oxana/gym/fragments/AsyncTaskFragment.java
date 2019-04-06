package ru.zaharova.oxana.gym.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import ru.zaharova.oxana.gym.R;

public class AsyncTaskFragment extends Fragment {
    Button buttonCalc;
    TextView textViewRes;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_async_task, container, false);
        buttonCalc = root.findViewById(R.id.button_calc);
        textViewRes = root.findViewById(R.id.text_view_res);
        progressBar = root.findViewById(R.id.progress_bar);

        buttonCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalculateNumAsync task = new CalculateNumAsync();
                task.execute(new Void[0]);
            }
        });

        return root;
    }

    private class CalculateNumAsync extends AsyncTask<Void, Integer, Double> {
        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected Double doInBackground(Void... voids) {
            return calculate();
        }

        @Override
        protected void onPostExecute(Double aDouble) {
            textViewRes.setText(String.valueOf(aDouble));
        }

        private double calculate() {
            double r = 1;
            for (double j = 0; j < 10000; j++) {
                r = j * 0.01 + r / 0.01;
                publishProgress((int)(j * 100 / 10000));
            }
            return r;
        }
    }
}

