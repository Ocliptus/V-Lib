package com.r00t.v_lib.data;

import java.io.Serializable;

public class Book implements Serializable {
    private String title,author,date,ISBN;

    public Book(String title, String author, String date, String ISBN) {
        this.title = title;
        this.author = author;
        this.date = date;
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
}
