package com.instigatemobile.grapes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.LinkedList;
import java.util.List;

import static java.lang.Thread.sleep;

public class PreviewActivity extends AppCompatActivity {

    private List<PreviewObjects> previewObjectsList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        if (!prefs.getBoolean(Util.IS_FIRST_RUN, true)) {
            startActivity(new Intent(PreviewActivity.this, MainActivity.class));
            finish();
        }
        prefs.edit().putBoolean(Util.IS_FIRST_RUN, false).apply();

        setObjectList();
        PreviewPagerAdapter adapter = new PreviewPagerAdapter(previewObjectsList);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.preview_tab_layout);
        tabLayout.setupWithViewPager(viewPager, true);
        Button btnSkip = findViewById(R.id.btn_skip);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PreviewActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //TODO
    }

    public void setObjectList() {
        previewObjectsList.add(new PreviewObjects(R.drawable.grapes_logo, getString(R.string.first_page_text), getString(R.string.grapes)));
        previewObjectsList.add(new PreviewObjects(R.drawable.second_page_logo, getString(R.string.second_page_text), null));
        previewObjectsList.add(new PreviewObjects(R.drawable.third_page_logo, getString(R.string.third_page_text), null));
    }
}