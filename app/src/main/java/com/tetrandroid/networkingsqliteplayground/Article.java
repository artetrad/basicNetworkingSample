package com.tetrandroid.networkingsqliteplayground;

/**
 *  Gson model c
 */
public class Article {
    private int id;
    private String title;
    private String body;
    private String author;
    private String date_published;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishedDate() {
        return date_published;
    }

    public void setPublishedDate(String publishedDate) {
        this.date_published = publishedDate;
    }
}
