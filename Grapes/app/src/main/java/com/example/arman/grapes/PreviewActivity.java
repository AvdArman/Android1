package com.example.arman.grapes;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class PreviewActivity extends AppCompatActivity {

    private List<PreviewObjects> previewObjectsList = new LinkedList<>();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(PreviewActivity.this, LogIn.class);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        setObjectList();
        CustomPagerAdapter adapter = new CustomPagerAdapter(previewObjectsList);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.preview_tab_layout);
        tabLayout.setupWithViewPager(viewPager, true);
        Button btnSkip = findViewById(R.id.btn_skip);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void setObjectList() {
        previewObjectsList.add(new PreviewObjects(R.drawable.grapes_logo, getString(R.string.first_page_text), getString(R.string.grapes)));
        previewObjectsList.add(new PreviewObjects(R.drawable.second_page_logo, getString(R.string.second_page_text), null));
        previewObjectsList.add(new PreviewObjects(R.drawable.third_page_logo, getString(R.string.third_page_text), null));
    }
}