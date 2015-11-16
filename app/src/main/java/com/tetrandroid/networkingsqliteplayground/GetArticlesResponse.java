package com.tetrandroid.networkingsqliteplayground;

import java.util.ArrayList;

/**
 *  Gson model class
 */
public class GetArticlesResponse {
    private ArrayList<Article> articles = new ArrayList<>();
    private int success;


    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }
}