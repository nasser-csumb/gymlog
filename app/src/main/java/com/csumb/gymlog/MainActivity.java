/**
 * Title: GymLog Main Activity
 * Author: Nasser Akhter
 */

package com.csumb.gymlog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.csumb.gymlog.Database.Entities.GymLog;
import com.csumb.gymlog.Database.Entities.User;
import com.csumb.gymlog.Database.GymLogRepository;
import com.csumb.gymlog.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String MAIN_ACTIVITY_USER_ID = "MainActivityUserId";
    ActivityMainBinding binding;

    GymLogRepository repository;

    private static final String TAG = "GYMLOG";

    private int userId = -1;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, -1);
        repository = GymLogRepository.getRepository(getApplication());

        if (userId == -1) {
            Intent intent = Login.loginIntentFactory(getApplicationContext());

            startActivity(intent);
        } else {
            user = repository.getUserById(userId);
        }


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

            if (exerciseName.length() <= 0 || weight <= 0 || reps <= 0) {
                CharSequence text = "Enter some values to save them.";
                Toast.makeText(getApplicationContext(),text, Toast.LENGTH_SHORT).show();
                throw new Error("Blank!");
            }

            GymLog log = new GymLog(exerciseName, weight, reps);
            log.setId(userId);

            repository.insertGymLog(log);
        } catch (Exception e) {
            Log.d(TAG, "Error while parsing weight or reps");
        }


        refreshDisplay();
    }

    private void refreshDisplay() {
        ArrayList<GymLog> logs = repository.getAllLogs(userId);

        for (var log : logs) {
            TextView tv = new TextView(getApplicationContext());
            tv.setText(log.toString());
            binding.entriesList.addView(tv);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.logoutMenuItem);
        item.setVisible(true);

        if (user != null) {
            item.setTitle(user.getUsername());
        } else {
            item.setTitle("TestUser");
        }



        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                showLogoutAlert();
                return false;
            }
        });

        return true;
    }

    private void showLogoutAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.this.finish();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });


        AlertDialog dialog = builder.create();
        dialog.setTitle("Logout?!");

        dialog.show();
    }

    public static Intent mainActivityFactory(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);

        return intent;
    }
}