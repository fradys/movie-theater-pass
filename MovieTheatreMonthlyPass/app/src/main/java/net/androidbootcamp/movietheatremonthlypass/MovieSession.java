package net.androidbootcamp.movietheatremonthlypass;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity( foreignKeys =
            {
                @ForeignKey(
                        entity = MovieTheater.class,
                        parentColumns = "movieTheaterID",
                        childColumns = "movieTheaterID",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = DisplayTime.class,
                        parentColumns = "displayTimeID",
                        childColumns = "displayTimeID",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = Movie.class,
                        parentColumns = "movieId",
                        childColumns = "movieID",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = SessionType.class,
                        parentColumns = "sessionTypeID",
                        childColumns = "sessionTypeID",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE)
            },
             indices =
            {
                 @Index("movieTheaterID"),
                 @Index("displayTimeID"),
                 @Index("sessionTypeID"),
                 @Index("movieID")
            }
    )
public class MovieSession {

    @PrimaryKey(autoGenerate = true)
    private long movieSessionID;
    private long movieTheaterID;
    private long displayTimeID;
    private long movieID;
    private long sessionTypeID;
    private boolean active;

    @Ignore
    public MovieSession(long movieTheaterID, long displayTimeID, long movieID, long sessionTypeID, boolean active) {
        this.movieTheaterID = movieTheaterID;
        this.displayTimeID = displayTimeID;
        this.movieID = movieID;
        this.sessionTypeID = sessionTypeID;
        this.active = active;
    }

    public MovieSession() {
    }


    public long getMovieSessionID() {
        return movieSessionID;
    }

    public void setMovieSessionID(long movieSessionID) {
        this.movieSessionID = movieSessionID;
    }

    public long getMovieTheaterID() {
        return movieTheaterID;
    }

    public void setMovieTheaterID(long movieTheaterID) {
        this.movieTheaterID = movieTheaterID;
    }

    public long getDisplayTimeID() {
        return displayTimeID;
    }

    public void setDisplayTimeID(long displayTimeID) {
        this.displayTimeID = displayTimeID;
    }

    public long getMovieID() {
        return movieID;
    }

    public void setMovieID(long movieID) {
        this.movieID = movieID;
    }

    public long getSessionTypeID() {
        return sessionTypeID;
    }

    public void setSessionTypeID(long sessionTypeID) {
        this.sessionTypeID = sessionTypeID;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
