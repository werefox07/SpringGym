package ru.zaharova.oxana.gym.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.zaharova.oxana.gym.R;
import ru.zaharova.oxana.gym.interfaces.OnListItemClickListener;
import ru.zaharova.oxana.gym.list.WorkoutAdapter;


public class WorkoutListFragment extends Fragment {
    private RecyclerView recyclerView;
    public static final String TAG = "WorkoutListActivityLog";
    private OnListItemClickListener listener;

    @Override
    public void onAttach(Context context) {
        Log.i("asd", "yahooo");
        if (context instanceof OnListItemClickListener) {
            Log.i("asd", "inside");
            listener = (OnListItemClickListener) context;
        }
        super.onAttach(context);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_workout_list, container, false);
        recyclerView = root.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new WorkoutAdapter(listener));

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Вызван onCreate()");

    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.workout_list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                Workout workout = new Workout("Новое упражнение");
                workout.setDescription("Описание нового упражнения");
                workout.setRecordDate(new Date());
                workout.setRecordRepsCount(0);
                workout.setRecordWeight(0);
                adapter.addWorkout(workout);
                recreate();
                return true;
            case R.id.action_quit:
                Toast.makeText(this, "Quit", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    } */
}
