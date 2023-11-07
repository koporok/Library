package com.example.notes;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DataBase {
    static DataBase instance;
    static SharedPreferences sharedPref;
    private DataBase(Activity t){
        sharedPref = t.getPreferences(Context.MODE_PRIVATE);
    }

    static DataBase createInstance(Activity t){
        if(instance == null){
            instance = new DataBase(t);
        }
        return instance;
    }

    static Boolean info(){
        if(sharedPref.contains("listOfItem")){
            return true;
        } else {
            return false;
        }
    }

    void addItem(ArrayList<Book> list){
        Gson gson = new Gson();
        String jsonList = gson.toJson(list);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("listOfItem", jsonList);
        editor.apply();
    }

    ArrayList<Book> getItem(String name){
        String jsonList = sharedPref.getString("listOfItem", null);
        if (jsonList != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Book>>() {}.getType();
            ArrayList<Book> list = gson.fromJson(jsonList, type);
            return list;
        }
        return null;
    }

    static DataBase getInstance(){
        return instance;
    }
}