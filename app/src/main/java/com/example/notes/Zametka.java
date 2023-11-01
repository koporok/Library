package com.example.notes;

import java.io.Serializable;

public class Zametka implements Serializable {
    private String title;
    private String txt;
    Zametka(String title, String txt){
        this.title = title;
        this.txt = txt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}