package com.example.android.wellnessjournal.Login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import static com.example.android.wellnessjournal.R.layout.activity_register;

/**
 * Created by Maryline on 9/20/2017.
 */

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_register);

        Log.d(TAG, "onCreate: created.");
    }
}
