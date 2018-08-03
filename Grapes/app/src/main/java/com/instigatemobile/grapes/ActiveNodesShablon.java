package com.instigatemobile.grapes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ActiveNodesShablon extends AppCompatActivity {
    private String nickname;

    private String ip;

    public ActiveNodesShablon(String nickname, String ip) {
        this.nickname = nickname;
        this.ip = ip;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_nodes_shablon);
    }
}
