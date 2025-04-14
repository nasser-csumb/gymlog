package com.csumb.gymlog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.csumb.gymlog.Database.Entities.User;
import com.csumb.gymlog.Database.GymLogRepository;
import com.csumb.gymlog.databinding.ActivityLoginBinding;

public class Login extends AppCompatActivity {

    ActivityLoginBinding binding;

    GymLogRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = GymLogRepository.getRepository(getApplication());

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login() {
        var user = binding.username.getText().toString();
        var pass = binding.password.getText().toString();

        User userObj = repository.loginUser(user, pass);

        if (userObj != null) {
            Intent intent = MainActivity.mainActivityFactory(getApplicationContext(), userObj.getId());
            startActivity(intent);
        }
    }

    public static Intent loginIntentFactory(Context context) {
        Intent intent = new Intent(context, Login.class);

        return intent;
    }
}