package com.r00t.v_lib.data;

import java.io.Serializable;
import java.util.List;

public class Book implements Serializable {
    private String isbn;
    private String weight;
    private String urlBook;
    private String number_of_pages;
    private String publishDate;
    private String title;
    private String cover_small;
    private String cover_medium;
    private String cover_large;
    private String authors;
    private String publish_places;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getUrlBook() {
        return urlBook;
    }

    public void setUrlBook(String urlBook) {
        this.urlBook = urlBook;
    }

    public String getNumber_of_pages() {
        return number_of_pages;
    }

    public void setNumber_of_pages(String number_of_pages) {
        this.number_of_pages = number_of_pages;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover_small() {
        return cover_small;
    }

    public void setCover_small(String cover_small) {
        this.cover_small = cover_small;
    }

    public String getCover_medium() {
        return cover_medium;
    }

    public void setCover_medium(String cover_medium) {
        this.cover_medium = cover_medium;
    }

    public String getCover_large() {
        return cover_large;
    }

    public void setCover_large(String cover_large) {
        this.cover_large = cover_large;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getPublish_places() {
        return publish_places;
    }

    public void setPublish_places(String publish_places) {
        this.publish_places = publish_places;
    }
    public String toString(){
        return "ISBN:" + isbn + " Weight:" +weight+" Book URL:" +urlBook + " Number Of Pages:" + number_of_pages+
                " Publish Date:" + publishDate+" Title : " + title +" Small Cover:" +cover_small+" Medium Cover:" + cover_medium +
                " Large Cover :"+cover_large+" Authors:" + authors+" Publish Places:"+ publish_places;
    }
    public Book(){
        //for add via camera
        cover_medium ="";
        cover_small="";
        urlBook = "";
        publish_places="";
    }
    public Book(String isbn, String weight, String urlBook, String number_of_pages
            , String publishDate, String title, String cover_small, String cover_medium
                , String cover_large, String authors, String publish_places) {
        this.cover_small = cover_small;
        this.cover_medium = cover_medium;
        this.cover_large = cover_large;
        this.authors = authors;
        this.weight = weight;
        this.urlBook = urlBook;
        this.number_of_pages = number_of_pages;
        this.publishDate = publishDate;
        this.authors = authors;
        this.publish_places = publish_places;
        this.isbn = isbn;
        this.title=title;
    }
    public Book() {
        this.cover_small = cover_small;
        this.cover_medium = cover_medium;
        this.cover_large = cover_large;
        this.authors = authors;
        this.weight = weight;
        this.urlBook = urlBook;
        this.number_of_pages = number_of_pages;
        this.publishDate = publishDate;
        this.authors = authors;
        this.publish_places = publish_places;
        this.isbn = isbn;
        this.title=title;
    }
}
