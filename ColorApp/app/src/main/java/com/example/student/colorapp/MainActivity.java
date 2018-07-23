package com.example.student.colorapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    private int seekR, seekG, seekB;
    LinearLayout mScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SeekBar sbR =  findViewById(R.id.seek1);
        final SeekBar sbG =  findViewById(R.id.seek2);
        final SeekBar sbB =  findViewById(R.id.seek3);

        seekR = sbR.getProgress();
        seekG = sbG.getProgress();
        seekB = sbB.getProgress();


        sbR.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        seekR = sbR.getProgress();
                        sbR.setBackgroundColor(Color.rgb(seekR,0,0));

                        doSomethingWithColor();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );

        sbG.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        seekG = sbG.getProgress();
                        sbG.setBackgroundColor(Color.rgb(0,seekG,0));
                        doSomethingWithColor();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );
        sbB.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        seekB = sbB.getProgress();
                        sbB.setBackgroundColor(Color.rgb(0,0,seekB));

                        doSomethingWithColor();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );


    }

    private void doSomethingWithColor() {
        int color = Color.rgb(seekR, seekG, seekB);
        LinearLayout mainLayout = findViewById(R.id.background);
        mainLayout.setBackgroundColor(color);
    }
}