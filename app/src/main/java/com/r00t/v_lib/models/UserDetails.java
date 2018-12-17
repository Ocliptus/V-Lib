package com.r00t.v_lib.models;

import java.io.Serializable;

public class UserDetails implements Serializable {
    private String id;
    private String nameAndSurname;
    private String eMail;
    private String books;
    private String followers;
    private String followed;
    private String posts;
    private int postCount;

    public int getBookCount() {
        return bookCount;
    }

    public void setBookCount(int bookCount) {
        this.bookCount = bookCount;
    }

    private int bookCount;

    public UserDetails(String id, String nameAndSurname, String eMail, String books,
                       String followers, String followed, String posts, int postCount,int bookCount) {

        this.id = id;
        this.nameAndSurname = nameAndSurname;
        this.eMail = eMail;
        this.books = books;
        this.followers = followers;
        this.followed = followed;
        this.posts = posts;
        this.postCount = postCount;
    }

    public UserDetails() {
        this.id = id;
        this.nameAndSurname = nameAndSurname;
        this.eMail = eMail;
        this.books = books;
        this.followers = followers;
        this.followed = followed;
        this.posts = posts;
        this.postCount = postCount;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public String getId() {
        return id;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getBooks() {
        return books;
    }

    public void setBooks(String books) {
        this.books = books;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getFollowed() {
        return followed;
    }

    public void setFollowed(String followed) {
        this.followed = followed;
    }

    public String getPosts() {
        return posts;
    }

    public void setPosts(String posts) {
        this.posts = posts;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameAndSurname() {
        return nameAndSurname;
    }

    public void setNameAndSurname(String nameAndSurname) { this.nameAndSurname = nameAndSurname;}
}
