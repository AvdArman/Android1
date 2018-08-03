package com.instigatemobile.grapes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class LogIn extends AppCompatActivity {

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        setBackgroundImage();
        prefs = PreferenceManager.getDefaultSharedPreferences((getApplicationContext()));

        //Util.nickname = prefs.getString(Util.NICKNAME, null);
//        if (Util.nickname != null) {
//            finish();
//        }

        click();
    }

    private void setBackgroundImage() {
        ImageView image = findViewById(R.id.image);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            image.setImageResource(R.drawable.horizon);
        } else {
            image.setImageResource(R.drawable.vertical);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        ImageView image = findViewById(R.id.image);

        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            image.setImageResource(R.drawable.horizon);
        } else {
            image.setImageResource(R.drawable.vertical);
        }
    }


    private void click() {
        final EditText etNickname = findViewById(R.id.nickname);
        final Button submit = findViewById(R.id.submit);
        submit.setEnabled(false);
        etNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!isValid(String.valueOf(charSequence), 0)) {
                    Toast.makeText(LogIn.this, "Invalid etNickname, use only numbers and letters", Toast.LENGTH_LONG).show();
                    submit.setEnabled(false);
                } else if (isValid(String.valueOf(etNickname.getText()), 3)) {
                    submit.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        submit.setOnClickListener(
                new Button.OnClickListener() {
                    @SuppressLint("CommitPrefEdits")
                    @Override
                    public void onClick(View view) {
                        String name = etNickname.getText().toString();
                        Util.nickname = name;
                        prefs.edit().putString(Util.NICKNAME, name).apply();
                        final ProgressBar progress = findViewById(R.id.progress);
                        if (isValid(name, 3)) {
                            submit.setEnabled(false);
                            etNickname.setEnabled(false);
                            progress.setVisibility(VISIBLE);
                            new android.os.Handler().postDelayed(
                                    new Runnable() {
                                        public void run() {
                                            finish();
                                        }
                                    },
                                    1500);
                            ;
                        } else {
                            submit.setEnabled(false);
                        }
                    }
                }
        );
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public static boolean isValid(String str, int limit) {
        if (str.length() > 15 || str.length() <= limit) {
            return false;
        }
        for (int i = 0; i < str.length(); ++i) {
            if (!((str.charAt(i) >= 'A' && str.charAt(i) <= 'Z') ||
                    (str.charAt(i) >= 'a' && str.charAt(i) <= 'z') ||
                    (str.charAt(i) >= '0' && str.charAt(i) <= '9'))) {

                return false;
            }
        }
        return true;
    }
}


