package com.instigatemobile.grapes;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codekidlabs.storagechooser.StorageChooser;

import org.json.JSONArray;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private StorageChooser.Builder builder;
    private StorageChooser chooser;
    private FilesAdapter fileAdapter;
    private final int numberOfColumns = 3;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setAddButtonListener(view);
        return view;
    }

    public void setAddButtonListener(View view) {

        builder.withMemoryBar(true);
        builder.allowAddFolder(true);
        builder.allowCustomPath(true);
        builder.setType(StorageChooser.FILE_PICKER);

        final RecyclerView fileRV = view.findViewById(R.id.files_recycler_view);
        fileRV.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));

        fileAdapter = new FilesAdapter(Util.listData);
        fileRV.setAdapter(fileAdapter);

        FloatingActionButton fab = view.findViewById(R.id.add_file_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooser = builder.build();
                chooser.setOnSelectListener(new StorageChooser.OnSelectListener() {
                    @Override
                    public void onSelect(String path) {
                        writeJsonInFile(path);
                        writeJsonInFile(path);
                        fileAdapter.notifyDataSetChanged();
                    }
                });

                chooser.setOnCancelListener(new StorageChooser.OnCancelListener() {
                    @Override
                    public void onCancel() {
                        Toast.makeText(getContext(), R.string.chooser_canceled, Toast.LENGTH_SHORT).show();
                    }
                });

                chooser.setOnMultipleSelectListener(new StorageChooser.OnMultipleSelectListener() {
                    @Override
                    public void onDone(ArrayList<String> selectedFilePaths) {
                    }
                });
                chooser.show();
            }
        });
    }

    private void writeJsonInFile(final String filePath) {
        final String FILE_NAME = getFileName(filePath);
        final float fileSize = getFileSize(filePath);
        final String extension = getExtension(filePath);
        final int icon = getIcon(extension);
        final String date = getLastModifiedDate(filePath);

        JSONArray fileInfo = new JSONArray();

        fileInfo.put("\"name\"" + ":" + "\"" + FILE_NAME + "\"");
        fileInfo.put("path" + ":" + filePath);
        fileInfo.put("size" + ":" + fileSize);
        fileInfo.put("extension" + ":" + extension);
        fileInfo.put("icon" + ":" + icon);
        fileInfo.put("date" + ":" + date);

        final String JSON_PATH = "files.json";
        try (FileWriter file = new FileWriter(JSON_PATH)) {
            file.write(fileInfo.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getLastModifiedDate(final String filePath) {
        File file = new File(filePath);
        return String.valueOf(file.lastModified());
    }

    private static int getIcon(final String extension) {
        //TODO set icons
        return 0;
    }

    private static String getExtension(final String filePath) {
        if (filePath.lastIndexOf(".") != -1 && filePath.lastIndexOf(".") != 0) {
            return filePath.substring(filePath.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }

    private float getFileSize(final String filePath) {
        File file = new File(filePath);
        return file.length();
    }

    private String getFileName(final String path) {
        String fileName = path.substring(path.lastIndexOf("/") + 1);
        if (fileName.indexOf(".") > 0) {
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
        }
        return fileName;
    }

    public void setBuilder(StorageChooser.Builder builder) {
        this.builder = builder;
    }
}