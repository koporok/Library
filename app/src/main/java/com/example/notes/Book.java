package com.example.notes;

import java.io.Serializable;

public class Book implements Serializable {
    private String title;
    private String txt;
    private String Author;
    private String FeedBack;
    Book(String title, String txt, String Author, String feedBack){
        this.title = title;
        this.txt = txt;
        this.Author = Author;
        this.FeedBack = feedBack;
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

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getFeedBack() {
        return FeedBack;
    }

    public void setFeedBack(String feedBack) {
        FeedBack = feedBack;
    }
}