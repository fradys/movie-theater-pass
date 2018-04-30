package net.androidbootcamp.movietheatremonthlypass;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity ( foreignKeys =
            {
                @ForeignKey(
                    entity = TicketStatus.class,
                    parentColumns = "ticketStatusID",
                    childColumns = "ticketStatusID",
                    onDelete = ForeignKey.RESTRICT,
                    onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = MovieSession.class,
                        parentColumns = "movieSessionID",
                        childColumns = "movieSessionID",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "userID",
                        childColumns = "userID",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE)
            },
          indices =
          {
                @Index("movieSessionID"),
                @Index("ticketStatusID"),
                @Index("userID")
          }
      )
public class Ticket {

    @PrimaryKey(autoGenerate = true)
    private long ticketID;
    private long movieSessionID;
    private Date purchaseDate;
    private long ticketStatusID;
    private String QRCodeInfo;
    private long userID;

    @Ignore
    public Ticket(long movieSessionID, Date purchaseDate, long ticketStatusID, String QRCodeInfo, long userID) {
        this.movieSessionID = movieSessionID;
        this.purchaseDate = purchaseDate;
        this.ticketStatusID = ticketStatusID;
        this.QRCodeInfo = QRCodeInfo;
        this.userID = userID;
    }

    public Ticket() {
    }



    public long getTicketID() {
        return ticketID;
    }

    public void setTicketID(long ticketID) {
        this.ticketID = ticketID;
    }

    public long getMovieSessionID() {
        return movieSessionID;
    }

    public void setMovieSessionID(long movieSessionID) {
        this.movieSessionID = movieSessionID;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public long getTicketStatusID() {
        return ticketStatusID;
    }

    public void setTicketStatusID(long ticketStatusID) {
        this.ticketStatusID = ticketStatusID;
    }

    public String getQRCodeInfo() {
        return QRCodeInfo;
    }

    public void setQRCodeInfo(String QRCodeInfo) {
        this.QRCodeInfo = QRCodeInfo;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

}
