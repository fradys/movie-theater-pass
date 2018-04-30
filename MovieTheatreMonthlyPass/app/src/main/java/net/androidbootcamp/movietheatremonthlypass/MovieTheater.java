package net.androidbootcamp.movietheatremonthlypass;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class MovieTheater {

    @PrimaryKey(autoGenerate = true)
    private long movieTheaterID;
    private String name;
    private String address;
    private int numProjectionRooms;

    @Ignore
    public MovieTheater(String name, String address, int numProjectionRooms) {
        this.name = name;
        this.address = address;
        this.numProjectionRooms = numProjectionRooms;
    }

    public MovieTheater() {
    }


    public long getMovieTheaterID() {
        return movieTheaterID;
    }

    public void setMovieTheaterID(long movieTheaterID) {
        this.movieTheaterID = movieTheaterID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNumProjectionRooms() {
        return numProjectionRooms;
    }

    public void setNumProjectionRooms(int numProjectionRooms) {
        this.numProjectionRooms = numProjectionRooms;
    }
}
