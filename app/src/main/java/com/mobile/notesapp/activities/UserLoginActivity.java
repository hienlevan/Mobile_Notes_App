package com.mobile.notesapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mobile.notesapp.R;

public class UserLoginActivity extends AppCompatActivity {

    TextView txtSwitchToRegister;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        txtSwitchToRegister = findViewById(R.id.txtSwitchToRegister);
        txtSwitchToRegister.setOnClickListener(view -> {
            context = UserLoginActivity.this;
            Intent intent = new Intent(context, UserRegisterActivity.class);
            startActivity(intent);
        });
    }
}