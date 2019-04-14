package ru.zaharova.oxana.gym.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.zaharova.oxana.gym.CompoundView;
import ru.zaharova.oxana.gym.R;

public class CalendarFragment extends Fragment {
    private Context context;

//    public CalendarFragment(Context context) {
//        super();
//        this.context = context;
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        CompoundView compoundView = new CompoundView(context);
        return compoundView;
    }
}
