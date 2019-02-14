package com.ankush.shrivastava.ankush;

public class DataModel {
    String title;
    String year;
    String directors;
    String releaseDate;
    String rating;
    String genres;
    String imageUrl;
    String plot;
    String duration;
    String actors;
    public DataModel(){
    }
    public DataModel(String title, String year,String directors, String releaseDate,String rating,String genres,String imageUrl,String plot,String duration,String actors){
        this.title=title;
        this.year=year;
        this.directors=directors;
        this.releaseDate=releaseDate;
        this.rating=rating;
        this.genres=genres;
        this.imageUrl=imageUrl;
        this.plot=plot;
        this.duration=duration;
        this.actors=actors;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getDirectors() {
        return directors;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getRating() {
        return rating;
    }

    public String getGenres() {
        return genres;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPlot() {
        return plot;
    }

    public String getDuration() {
        return duration;
    }

    public String getActors() {
        return actors;
    }

}
