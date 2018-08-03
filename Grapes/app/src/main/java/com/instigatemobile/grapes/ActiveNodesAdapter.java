package com.instigatemobile.grapes;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class ActiveNodesAdapter extends RecyclerView.Adapter<ActiveNodesAdapter.MyViewHolder> {
    private List<ActiveNodesShablon> activeNodes;

    public ActiveNodesAdapter(List<ActiveNodesShablon> activeNodes) {
        this.activeNodes = activeNodes;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_active_nodes_shablon, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ActiveNodesShablon nodes = activeNodes.get(position);
        holder.nickname.setText(nodes.getNickname());
        holder.ip.setText(nodes.getIp());


    }

    @Override
    public int getItemCount() {
        return activeNodes.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nickname;
        TextView ip;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nickname = itemView.findViewById(R.id.nicknameNode);
            ip = itemView.findViewById(R.id.ipNode);
        }
    }
}
