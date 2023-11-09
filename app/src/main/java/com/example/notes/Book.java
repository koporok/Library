package com.example.notes;

import java.io.Serializable;

public class Book implements Serializable {
    private String title;
    private String genre;
    private String Author;
    private String review;
    Book(String title, String genre, String Author, String review){
        this.title = title;
        this.genre = genre;
        this.Author = Author;
        this.review = review;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}