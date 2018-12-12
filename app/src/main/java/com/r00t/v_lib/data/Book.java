package com.r00t.v_lib.data;

import java.io.Serializable;
import java.util.List;

public class Book implements Serializable {

    private String weight;
    private String url;
    private String number_of_pages;
    private String cover_small;
    private String cover_medium;
    private String cover_large;
    private String publishDate;
    private List<Book> authors;
    private List<Book> excerpts;
    private List<Book> publishers;
    private List<Book> subjects;
    private List<Book> publishPlaces;

    public String toString() {
        return " " + publishers + weight + url + number_of_pages + cover_small + subjects + publishDate + authors + excerpts + publishPlaces;
    }

    public List<Book> getPublishers() {
        return publishers;
    }

    public void setPublishers(List<Book> publishers) {
        this.publishers = publishers;
    }


    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNumber_of_pages() {
        return number_of_pages;
    }

    public void setNumber_of_pages(String number_of_pages) {
        this.number_of_pages = number_of_pages;
    }

    public String getCover() {
        return cover_small;
    }

    public void setCover(String cover) {
        this.cover_small = cover;
    }

    public String getCover_middle() {
        return cover_medium;
    }

    public void setCover_middle(String cover_middle) {
        this.cover_medium = cover_middle;
    }

    public String getCover_big() {
        return cover_large;
    }

    public void setCover_big(String cover_big) {
        this.cover_large = cover_big;
    }

    public List<Book> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Book> subjects) {
        this.subjects = subjects;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public List<Book> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Book> authors) {
        this.authors = authors;
    }

    public List<Book> getExcerpts() {
        return excerpts;
    }

    public void setExcerpts(List<Book> excerpts) {
        this.excerpts = excerpts;
    }

    public List<Book> getPublishPlaces() {
        return publishPlaces;
    }

    public void setPublishPlaces(List<Book> publishPlaces) {
        this.publishPlaces = publishPlaces;
    }


    public Book(List<Book> publishers,
                String weight, String url, String number_of_pages, String cover_small, List<Book> subjects, String publishDate,
                List<Book> authors, List<Book> excerpts, List<Book> publishPlaces) {
        this.cover_small = cover_small;
        this.publishers = publishers;
        this.weight = weight;
        this.url = url;
        this.number_of_pages = number_of_pages;
        this.subjects = subjects;
        this.excerpts = excerpts;
        this.publishDate = publishDate;
        this.authors = authors;
        this.publishPlaces = publishPlaces;
    }
}
