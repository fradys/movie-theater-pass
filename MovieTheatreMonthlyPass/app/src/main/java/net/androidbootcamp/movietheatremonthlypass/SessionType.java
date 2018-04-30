package net.androidbootcamp.movietheatremonthlypass;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class SessionType {

    @PrimaryKey(autoGenerate = true)
    private long sessionTypeID;
    private String type;
    private String description;

    @Ignore
    public static final String _2D = "2D";
    @Ignore
    public static final String _3D = "3D";
    @Ignore
    public static final String IMAX = "IMAX";

    @Ignore
    public SessionType(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public SessionType() {
    }


    public long getSessionTypeID() {
        return sessionTypeID;
    }

    public void setSessionTypeID(long sessionTypeID) {
        this.sessionTypeID = sessionTypeID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
