package com.js.tmdb.Model;

public class Model {
    String id;
    String title;
    String overview;
    String vote_average;
    String poster_path;

    public Model() {
    }

    public Model(String id, String title, String overview, String vote_average, String poster_path) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.vote_average = vote_average;
        this.poster_path = poster_path;
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

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }
}
