package net.androidbootcamp.movietheatremonthlypass;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class DisplayTime {

    @PrimaryKey(autoGenerate = true)
    private long displayTimeID;
    private String startTime;
    private String endTime;
    private boolean active;


    @Ignore
    public DisplayTime(String startTime, String endTime, boolean active) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.active = active;
    }

    public DisplayTime() {
    }


    public long getDisplayTimeID() {
        return displayTimeID;
    }

    public void setDisplayTimeID(long displayTimeID) {
        this.displayTimeID = displayTimeID;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
