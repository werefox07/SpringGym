package ru.zaharova.oxana.gym.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.zaharova.oxana.gym.R;

public class RealTimeFragment extends Fragment {
    private Context context;
    private TextView textView;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void setCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date date = new Date();
        String time = dateFormat.format(date);
        textView.setText(time);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_real_time, container, false);
        textView = root.findViewById(R.id.real_time_text_view);
        setCurrentTime();

        BroadcastReceiver br = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                setCurrentTime();
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        context.registerReceiver(br, intentFilter);
        return root;
    }
}
