package com.example.arman.grapes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showPreview();

    }

    private void showPreview() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getBoolean("isFirstRun", true)) {
            prefs.edit().putBoolean("isFirstRun", false).apply();
            Intent intent = new Intent(MainActivity.this, PreviewActivity.class);
            startActivity(intent);
        } else  {
            Intent intent = new Intent(this, LogIn.class);
            startActivity(intent);

        }
    }
}
