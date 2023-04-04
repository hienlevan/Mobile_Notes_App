package com.mobile.notesapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.mobile.notesapp.R;

public class UserRegisterActivity extends AppCompatActivity {

    TextView txtSwitchToLogin;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        txtSwitchToLogin = findViewById(R.id.txtSwitchToLogin);
        txtSwitchToLogin.setOnClickListener(view -> {
            context = UserRegisterActivity.this;
            Intent intent = new Intent(context, UserLoginActivity.class);
            startActivity(intent);
        });

    }
}