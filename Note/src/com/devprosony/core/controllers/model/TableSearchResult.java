package com.devprosony.core.controllers.model;

/**
 * Created by proso on 4/14/2017.
 */
public class TableSearchResult {
    private String title;
    private String genre;
    private String author;

    public TableSearchResult(String title, String genre, String author) {
        this.title = title;
        this.genre = genre;
        this.author = author;
    }

    public TableSearchResult() {}

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }


    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return  title +" "+ genre;
    }
}
