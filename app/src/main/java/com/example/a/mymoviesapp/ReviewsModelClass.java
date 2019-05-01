package com.example.a.mymoviesapp;

import java.io.Serializable;

public class ReviewsModelClass implements Serializable{
    String author,content;

    public ReviewsModelClass(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
