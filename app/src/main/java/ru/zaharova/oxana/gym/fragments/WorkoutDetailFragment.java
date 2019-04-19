package ru.zaharova.oxana.gym.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.Objects;

import ru.zaharova.oxana.gym.model.Workout;
import ru.zaharova.oxana.gym.model.WorkoutList;
import ru.zaharova.oxana.gym.R;

public class WorkoutDetailFragment extends Fragment {
    private static final String WORKOUT_INDEX = "workoutIndex";
    private TextView recordDate;
    private TextView recordRepsCount;
    private TextView recordWeight;
    private TextView weight;
    private SeekBar weightSeekBar;
    private EditText repsCountEditText;
    private Button saveRecordButton;
    private Workout workout;
    private Button shareButton;
    public static final String TAG = "WorkoutDetailFragment";

    public static WorkoutDetailFragment initFragment(int workoutIndex) {
        WorkoutDetailFragment fragment = new WorkoutDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(WORKOUT_INDEX, workoutIndex);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_workout_detail, container, false);
        workout = WorkoutList.getInstance().getWorkouts().get(Objects.requireNonNull(getArguments()).getInt(WORKOUT_INDEX));
        initGUI(root, workout);
        addListeners();
        return root;
    }

    private void addListeners() {
        weightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                weight.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        saveRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String respCountString = repsCountEditText.getText().toString();
                int respCount;
                if (!respCountString.equals("")) {
                    respCount = Integer.parseInt(respCountString);
                    int newWeight = Integer.parseInt(weight.getText().toString());
                    if (respCount > workout.getRecordRepsCount() || newWeight > workout.getRecordWeight()) {
                        workout.setRecordRepsCount(respCount);
                        workout.setRecordWeight(newWeight);
                        workout.setRecordDate(new Date());
                        setValues();
                        Toast toast = Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(),
                                getResources().getString(R.string.saved_record_message),
                                Toast.LENGTH_SHORT
                        );
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else {
                        Toast toast = Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(),
                                getResources().getString(R.string.not_saved_record_message),
                                Toast.LENGTH_SHORT
                        );
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                } else {
                    Toast toast = Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(),
                            getResources().getString(R.string.number_count_message),
                            Toast.LENGTH_SHORT
                    );
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String textToSend = getString(R.string.share_hello_record_text) +
                        "\n" + getString(R.string.exercise_share_text) + " " + workout.getTitle() +
                        "\n" + getString(R.string.weight_text) + " " + workout.getRecordWeight() +
                        "\n" + getString(R.string.reps_count_text) + " " + workout.getRecordRepsCount();
                intent.putExtra(Intent.EXTRA_TEXT, textToSend);
                try {
                    startActivity(Intent.createChooser(intent, getString(R.string.share_record_text)));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(),
                            getString(R.string.error_text), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setValues() {
        recordDate.setText(workout.getFormattedRecordDate());
        recordRepsCount.setText(String.valueOf(workout.getRecordRepsCount()));
        recordWeight.setText(String.valueOf(workout.getRecordWeight()));
    }

    private void initGUI(View view, Workout workout) {
        TextView title = view.findViewById(R.id.workout_detail_title);
        title.setText(workout.getTitle());
        recordDate = view.findViewById(R.id.workout_detail_record_date);
        recordRepsCount = view.findViewById(R.id.workout_detail_record_reps_count);
        recordWeight = view.findViewById(R.id.workout_detail_record_weight);

        weight = view.findViewById(R.id.workout_detail_weight);
        weightSeekBar = view.findViewById(R.id.workout_detail_seek_bar);
        repsCountEditText = view.findViewById(R.id.workout_detail_reps_count_edit_text);
        saveRecordButton = view.findViewById(R.id.workout_detail_save_button);
        shareButton = view.findViewById(R.id.share_button);
        setValues();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        savedInstanceState.putInt("repsCount", workout.getRecordRepsCount());
        savedInstanceState.putInt("weight", workout.getRecordWeight());
        savedInstanceState.putString("date", workout.getFormattedRecordDate());
        Log.d(TAG, "Вызван onSaveInstanceState");
        super.onSaveInstanceState(savedInstanceState);
    }
}

