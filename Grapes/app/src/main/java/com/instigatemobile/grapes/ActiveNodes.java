package com.instigatemobile.grapes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ActiveNodes extends AppCompatActivity {
    private List<ActiveNodesShablon> nodes = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_nodes);
        LinearLayout background = findViewById(R.id.nodeBackground);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            background.setBackgroundResource(R.drawable.backgroundgrapeschange);
        } else {
            background.setBackgroundResource(R.drawable.backgroundgrapes);
        }
        fillList();
        RecyclerView recyclerView = findViewById(R.id.nodes);
        ActiveNodesAdapter myAdapter = new ActiveNodesAdapter(nodes);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myAdapter);
        ImageView back = findViewById(R.id.nodeBack);
        back.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActiveNodes.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        LinearLayout background = findViewById(R.id.nodeBackground);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            background.setBackgroundResource(R.drawable.backgroundgrapeschange);
        } else {
            background.setBackgroundResource(R.drawable.backgroundgrapes);
        }
    }

    private void fillList() {
        ArrayList<String> node = jsonToArray();
        //ActiveNodesShablon shablon = new ActiveNodesShablon(null, null);
        for(int i = 0; i < node.size(); ++i) {
            ActiveNodesShablon shablon = new ActiveNodesShablon(null, null);
            String jsonObject = node.get(i);
            try {
                JSONObject json = new JSONObject(jsonObject);
                shablon.setNickname(json.getString("name"));
                shablon.setIp(json.getString("ip"));
                nodes.add(shablon);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("jsonfile.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public ArrayList<String> jsonToArray () {

        ArrayList<String> listdata = null;
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(loadJSONFromAsset());
            listdata = new ArrayList<String>();
            JSONArray jArray = null;
            jArray = jsonObject.getJSONArray("activNodes");
            if (jArray != null) {
                for (int i = 0; i < jArray.length(); i++) {
                    listdata.add(jArray.getString(i));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listdata;
    }

}
