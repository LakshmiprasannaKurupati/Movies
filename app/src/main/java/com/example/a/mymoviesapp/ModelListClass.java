package com.example.a.mymoviesapp;

import java.io.Serializable;

public class ModelListClass implements Serializable {
    String id;
    String title;
    String poster;
    String rating;
    String overview;
    String relasedate;

    public ModelListClass(String id, String title, String poster, String rating, String overview, String relasedate) {
        this.id = id;
        this.title = title;
        this.poster = poster;
        this.rating = rating;
        this.overview = overview;
        this.relasedate = relasedate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return "https://image.tmdb.org/t/p/w500/"+poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelasedate() {
        return relasedate;
    }

    public void setRelasedate(String relasedate) {
        this.relasedate = relasedate;
    }
}
