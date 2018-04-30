package net.androidbootcamp.movietheatremonthlypass;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Movie {

    @PrimaryKey (autoGenerate = true)
    private long movieId;
    private String id;
    private String title;
    private String imageUrl;
    private ArrayList<String> genre = new ArrayList<>();
    private String sinopse;
    private Date releaseDate;
    private double voteAverage;
    private String runtime;


    @Ignore
    public Movie(String id, String title, String sinopse, String imageUrl, List<String> genre, Date releaseDate,
                 double rating, String runtime) {
        super();
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.genre.addAll(genre);
        this.sinopse = sinopse;
        this.releaseDate = releaseDate;
        this.voteAverage = rating;
        this.runtime = runtime;
    }

    @Ignore
    public Movie(String id) {
        super();
        this.id = id;
    }

    public Movie() {
    }

//    public void printMovie() {
//        System.out.println("ID: " + this.id);
//        System.out.println("Title: " + this.title);
//        System.out.println("Image URL: " + this.imageUrl);
//        System.out.println("Genres: ");
//        for (int x = 0; x < this.genre.size(); x++)
//            System.out.println("	" + this.genre.get(x));
//
//        System.out.println("Sinopse: " + this.sinopse);
//        System.out.println("Release Date: " + this.releaseDate.toString());
//        System.out.println("Vote Average: " + this.voteAverage);
//
//
//    }


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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ArrayList<String> getGenre() {
        return genre;
    }

    public void setGenre(ArrayList<String> genre) {
        this.genre = genre;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getRating() {
        return voteAverage;
    }

    public void setRating(double rating) {
        this.voteAverage = rating;
    }

    public String getRuntime() { return runtime; }

    public void setRuntime(String runtime) { this.runtime = runtime; }

    public long getMovieId() { return movieId; }

    public void setMovieId(long movieId) { this.movieId = movieId; }

    public double getVoteAverage() { return voteAverage; }

    public void setVoteAverage(double voteAverage) { this.voteAverage = voteAverage; }

}

