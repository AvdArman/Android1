package com.instigatemobile.grapes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.FileViewHolder>{
    private List<FileData> fileData;

    public FilesAdapter(List<FileData> fileData) {
        this.fileData = fileData;
    }

    @NonNull
    @Override
    public FilesAdapter.FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_item, parent, false);
        return new FilesAdapter.FileViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return fileData.size();
    }

    class FileViewHolder extends RecyclerView.ViewHolder {

        private ImageView fileIcon;
        private TextView fileName;

        FileViewHolder(@NonNull View itemView) {
            super(itemView);
            fileIcon = itemView.findViewById(R.id.file_icon);
            fileName = itemView.findViewById(R.id.list_file_name);
        }
    }
}
