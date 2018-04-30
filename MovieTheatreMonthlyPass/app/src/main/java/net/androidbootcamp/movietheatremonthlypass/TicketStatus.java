package net.androidbootcamp.movietheatremonthlypass;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class TicketStatus {

    @PrimaryKey(autoGenerate = true)
    private long ticketStatusID;
    private String name;
    private String description;
    private double fee;

    @Ignore
    public static final String CONFIRMED = "CONFIRMED";
    @Ignore
    public static final String CONSUMED = "CONSUMED";
    @Ignore
    public static final String NO_SHOW = "NO-SHOW";
    @Ignore
    public static final String CANCELED = "CANCELED";

    @Ignore
    public TicketStatus(String name, String description, double fee) {
        this.name = name;
        this.description = description;
        this.fee = fee;
    }

    public TicketStatus() {
    }


    public long getTicketStatusID() {
        return ticketStatusID;
    }

    public void setTicketStatusID(long ticketStatusID) {
        this.ticketStatusID = ticketStatusID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
}
