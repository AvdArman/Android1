package com.example.arman.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView img;
    CheckBox showImage;
    Button btnFirst;
    Button btnSecond;
    Button btnSave;
    EditText edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setListeners();
    }

    public void findViews() {
        img = findViewById(R.id.img);
        showImage = findViewById(R.id.chk_show);
        btnFirst = findViewById(R.id.btn_first);
        btnSecond = findViewById(R.id.btn_second);
        btnSave = findViewById(R.id.btn_save);
        edt = findViewById(R.id.et_1);
    }

    public void setListeners() {
        btnFirst.setOnClickListener(this);
        btnSecond.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    public void onClick(View v) {
        CheckBox tmp = (CheckBox) v;
        switch (v.getId()) {
            case R.id.chk_show:
                if (tmp.isChecked()) {
                    img.setVisibility(View.VISIBLE);
                } else {
                    img.setVisibility(View.GONE);
                }
                break;
            case R.id.btn_first:
                img.setImageResource(R.mipmap.img1);
                break;
            case R.id.btn_second:
                img.setImageResource(R.mipmap.img2);
                break;
            case R.id.btn_save:
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra(edt.getText().toString(), 1);
                startActivity(intent);
                break;
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}