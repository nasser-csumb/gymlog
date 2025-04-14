/**
 * Title: GymLog Main Activity
 * Author: Nasser Akhter
 */

package com.csumb.gymlog;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.csumb.gymlog.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
    }
}