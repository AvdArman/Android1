package com.example.arman.grapes;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import static android.view.View.VISIBLE;

public class LogIn extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        ImageView image = findViewById(R.id.image);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            image.setImageResource(R.drawable.horizon);
        } else {
            image.setImageResource(R.drawable.vertical);
        }
        click();
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
        final EditText nickname = findViewById(R.id.nickname);
        final Button submit = findViewById(R.id.submit);
        submit.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String var = nickname.getText().toString();
                        ProgressBar progress = findViewById(R.id.progress);
                        if (isValid(var)) {
                            submit.setEnabled(false);
                            nickname.setEnabled(false);
                            progress.setVisibility(VISIBLE);
                        } else {
                            Toast.makeText(LogIn.this, "Invalid Nickname", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
        );
    }

    public static boolean isValid(String str) {
        if(str.length() > 15 || str.length() <= 3) {
            return false;
        }
        for(int i = 0; i < str.length(); ++i) {
            if (!((str.charAt(i) >= 'A' && str.charAt(i) <= 'Z') ||
                    (str.charAt(i) >= 'a' && str.charAt(i) <= 'z') ||
                    (str.charAt(i) >= '0' && str.charAt(i) <= '9'))) {

                return false;
            }
        }
        return true;
    }
}
