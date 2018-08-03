package com.instigatemobile.grapes;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.codekidlabs.storagechooser.Content;
import com.codekidlabs.storagechooser.StorageChooser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences prefs;
    public List<FileData> files = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //read json file add files list, and add sorted lists
        fillList();
        Util.listData = files;
        Util.sortByName = Util.sortBy(files, "name");
        Util.sortBySize = Util.sortBy(files, "size");
        Util.sortByDate = Util.sortBy(files, "date");
        Util.filterByMusics = Util.filter(files, "mp3");
        Util.filterByVideos = Util.filter(files, ".mp4");
        Util.filterByImages = Util.filter(files, ".jpg");
        prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        Util.nickname = prefs.getString(Util.NICKNAME, null);
        if (Util.nickname == null) {
            startActivity(new Intent(MainActivity.this, LogIn.class));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 199);
        }
        setDrawerLayout(getToolbar());

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView symb = navigationView.getHeaderView(0).findViewById(R.id.name_circle);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.ic_exit) {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    prefs.edit().putString(Util.NICKNAME, null);
                }
                return false;
            }
        });

        Util.nickname = prefs.getString(Util.NICKNAME, null);
        if (Util.nickname != null) {
            symb.setText(String.valueOf(Util.nickname.charAt(0)));
        }

        final ViewPager viewPager = setViewPagersHomeAndRemote();
        setTabLayout(viewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private Toolbar getToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    private void setDrawerLayout(Toolbar toolbar) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setTabLayout(ViewPager viewPager) {
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager, true);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_remote);
    }

    @NonNull
    private ViewPager setViewPagersHomeAndRemote() {
        final ViewPager viewPager = findViewById(R.id.pager);
        MainFragmentsAdapter adapter = new MainFragmentsAdapter(getSupportFragmentManager());
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setBuilder(getBuilder());
        adapter.addFragment(homeFragment);
        adapter.addFragment(new RemoteFragment());
        viewPager.setAdapter(adapter);
        return viewPager;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_active_nodes) {
            Intent intent = new Intent(this, ActiveNodes.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private StorageChooser.Builder getBuilder() {
        Content c = new Content();
        c.setCreateLabel("Create");
        c.setInternalStorageText("My Storage");
        c.setCancelLabel("Cancel");
        c.setSelectLabel("Select");
        c.setOverviewHeading("Choose Drive");

        StorageChooser.Builder builder = new StorageChooser.Builder();
        builder.withActivity(this)
                .withFragmentManager(getFragmentManager())
                .setMemoryBarHeight(1.5f)
                .disableMultiSelect()
                .withContent(c);

        return builder;
    }

    private void fillList() {
        ArrayList<String> node = jsonToArray();
        if (!node.isEmpty()) {
            for (int i = 0; i < node.size(); ++i) {
                FileData data = new FileData(0, 0, null, null, null, null);
                String jsonObject = node.get(i);
                try {
                    JSONObject json = new JSONObject(jsonObject);
                    data.setSize(Float.parseFloat(json.getString("size")));
                    data.setIcon(Integer.parseInt(json.getString("icon")));
                    data.setName(json.getString("name"));
                    data.setPath(json.getString("path"));
                    data.setDate(json.getString("date"));
                    data.setExtension(json.getString("extension"));
                    files.add(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String loadJSONFromAsset() {
        String json;
        try {
            InputStream is = this.getAssets().open("files.json");
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

    public ArrayList<String> jsonToArray() {

        ArrayList<String> listdata = null;
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(loadJSONFromAsset());
            listdata = new ArrayList<>();
            JSONArray jArray = jsonObject.getJSONArray("files");
            if (jArray != null) {
                for (int i = 0; i < jArray.length(); i++) {
                    listdata.add(jArray.getString(i));
                }
            }
        } catch (JSONException | NullPointerException e) {
            e.printStackTrace();
        }
        return listdata;
    }

}
