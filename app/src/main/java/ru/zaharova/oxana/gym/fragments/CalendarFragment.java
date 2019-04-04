package ru.zaharova.oxana.gym.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.zaharova.oxana.gym.CompoundView;

@SuppressLint("ValidFragment")
public class CalendarFragment extends Fragment {
    private final Context context;

    public CalendarFragment(Context context) {
        super();
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return new CompoundView(context);
    }
}
