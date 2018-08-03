package com.instigatemobile.grapes;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class Util {
    public static final String IS_FIRST_RUN = "isFirstRun";
    public static final String NICKNAME = "nickname";
    public static List<FileData> sortByName;
    public static List<FileData> sortBySize;
    public static List<FileData> sortByDate;
    public static List<FileData> filterByBooks;
    public static List<FileData> filterByMusics;
    public static List<FileData> filterByVideos;
    public static List<FileData> filterByImages;
    public static List<FileData> listData;
    public static String nickname;


    public static List<FileData> filter(List<FileData> list, String ext) {
        List<FileData> filterList = new ArrayList<>();
        for(int i = 0; i < list.size(); ++i) {
            if(ext.equals(list.get(i).getExtension())) {
                filterList.add(list.get(i));
            }
        }
        return filterList;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<FileData> sortBy(List<FileData> list, String sortBy) {
        int size = list.size();
        List<String> listValue = new ArrayList<>();
        List<FileData> sortedList = new ArrayList<>();
        switch (sortBy) {
            case "name":
                for(int i = 0; i < size; ++i) {
                    listValue.add(list.get(i).getName());
                }
                listValue.sort(String.CASE_INSENSITIVE_ORDER);
                for(int i = 0; i < size; ++i) {
                    for(int j = 0; i < size; ++i) {
                        if(listValue.get(i).equals(list.get(j).getName())) {
                            sortedList.add(list.get(j));
                        }
                    }
                }
                break;
            case "date":
                for(int i = 0; i < size; ++i) {
                    listValue.add(list.get(i).getDate());
                }
                listValue.sort(String.CASE_INSENSITIVE_ORDER);
                for(int i = 0; i < size; ++i) {
                    for(int j = 0; i < size; ++i) {
                        if(listValue.get(i).equals(list.get(j).getDate())) {
                            sortedList.add(list.get(j));
                        }
                    }
                }
                break;
            case "size":
                for(int i = 0; i < size; ++i) {
                    listValue.add(String.valueOf(list.get(i).getSize()));
                }
                listValue.sort(String.CASE_INSENSITIVE_ORDER);
                for(int i = 0; i < size; ++i) {
                    for(int j = 0; i < size; ++i) {
                        if(listValue.get(i).equals(String.valueOf(list.get(j).getSize()))) {
                            sortedList.add(list.get(j));
                        }
                    }
                }
                break;
            default:
                break;
        }
        return sortedList;
    }
}
