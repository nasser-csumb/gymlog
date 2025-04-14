/**
 * Title: GymLog Main Activity
 * Author: Nasser Akhter
 */

package com.csumb.gymlog;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.csumb.gymlog.Database.Entities.GymLog;
import com.csumb.gymlog.Database.GymLogRepository;
import com.csumb.gymlog.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    GymLogRepository repository;

    private static final String TAG = "GYMLOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = GymLogRepository.getRepository(getApplication());


        binding.logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveEntry();
            }
        });

        refreshDisplay();
    }

    private void saveEntry() {
        String exerciseName = "";
        float weight = 0.0F;
        int reps = 0;

        exerciseName = binding.exerciseName.getText().toString();

        try {
            weight = Float.parseFloat(binding.weight.getText().toString());
            reps = Integer.parseInt(binding.reps.getText().toString());

            GymLog log = new GymLog(exerciseName, weight, reps);

            repository.insertGymLog(log);
        } catch (Exception e) {
            Log.d(TAG, "Error while parsing weight or reps");
        }


        refreshDisplay();
    }

    private void refreshDisplay() {
        ArrayList<GymLog> logs = repository.getAllLogs();

        for (var log : logs) {
            TextView tv = new TextView(getApplicationContext());
            tv.setText(
                    "Log: " + log.getDate() + "\n" +
                            "\tExercise: " + log.getExercise() + "\n" +
                            "\tWeight: " + log.getWeight() + "\n" +
                            "\tReps: " + log.getReps()
            );
            binding.entriesList.addView(tv);
        }
    }
}