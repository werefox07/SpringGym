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

import ru.zaharova.oxana.gym.R;

public class RealTimeFragment extends Fragment {
    private Context context;
    private TextView textView;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_real_time, container, false);
        textView = root.findViewById(R.id.real_time_text_view);
        BroadcastReceiver br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
               String realTime = intent.getStringExtra("Real_time_message");
               textView.setText(realTime);
            }
        };
        IntentFilter intFilt = new IntentFilter("RealTime");
        context.registerReceiver(br, intFilt);
        return root;
    }
}
