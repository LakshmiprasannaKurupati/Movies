package com.example.a.mymoviesapp.roomdatabase;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "favourite_movies")
public class FavourateMovies {
  @PrimaryKey
          @NonNull
   public String id;
   public String title_movie;
   public String poster_movie;
   public String rating_Movie;
   public String releaseDate_movie;
   public String description_movie;

    public FavourateMovies(@NonNull String id, String title_movie, String poster_movie, String rating_Movie, String releaseDate_movie, String description_movie) {
        this.id = id;
        this.title_movie = title_movie;
        this.poster_movie = poster_movie;
        this.rating_Movie = rating_Movie;
        this.releaseDate_movie = releaseDate_movie;
        this.description_movie = description_movie;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getTitle_movie() {
        return title_movie;
    }

    public void setTitle_movie(String title_movie) {
        this.title_movie = title_movie;
    }

    public String getPoster_movie() {
        return poster_movie;
    }

    public void setPoster_movie(String poster_movie) {
        this.poster_movie = poster_movie;
    }

    public String getRating_Movie() {
        return rating_Movie;
    }

    public void setRating_Movie(String rating_Movie) {
        this.rating_Movie = rating_Movie;
    }

    public String getReleaseDate_movie() {
        return releaseDate_movie;
    }

    public void setReleaseDate_movie(String releaseDate_movie) {
        this.releaseDate_movie = releaseDate_movie;
    }

    public String getDescription_movie() {
        return description_movie;
    }

    public void setDescription_movie(String description_movie) {
        this.description_movie = description_movie;
    }
}
