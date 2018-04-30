package net.androidbootcamp.movietheatremonthlypass;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity( foreignKeys =
            {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "userID",
                        childColumns = "userID",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = SubscriptionPlan.class,
                        parentColumns = "planID",
                        childColumns = "planID",
                        onDelete = ForeignKey.RESTRICT,
                        onUpdate = ForeignKey.CASCADE)
            },
        indices =
                {@Index("userID"),
                 @Index("planID")}
    )

public class Payment {

    @PrimaryKey(autoGenerate = true)
    private long paymentID;
    @ColumnInfo(name = "ccBrand")
    private String creditCardBrand;
    @ColumnInfo(name = "ccNumber")
    private String creditCardNumber;
    @ColumnInfo(name = "ccCustomer")
    private String creditCardCustomer;
    @ColumnInfo(name = "ccExpiration")
    private Date creditCardExpiration;
    @ColumnInfo(name = "cvvNumber")
    private String creditCardCVVNumber;
    private boolean active;
    private long userID;
    private long planID;

    @Ignore
    public Payment(String creditCardBrand, String creditCardNumber, String creditCardCustomer,
                   Date creditCardExpiration, String creditCardCVVNumber, boolean active, long userID, long planID) {
        this.creditCardBrand = creditCardBrand;
        this.creditCardNumber = creditCardNumber;
        this.creditCardCustomer = creditCardCustomer;
        this.creditCardExpiration = creditCardExpiration;
        this.creditCardCVVNumber = creditCardCVVNumber;
        this.active = active;
        this.userID = userID;
        this.planID = planID;
    }

    public Payment() {
    }


    public long getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(long paymentID) {
        this.paymentID = paymentID;
    }

    public String getCreditCardBrand() {
        return creditCardBrand;
    }

    public void setCreditCardBrand(String creditCardBrand) {
        this.creditCardBrand = creditCardBrand;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getCreditCardCustomer() {
        return creditCardCustomer;
    }

    public void setCreditCardCustomer(String creditCardCustomer) {
        this.creditCardCustomer = creditCardCustomer;
    }

    public Date getCreditCardExpiration() {
        return creditCardExpiration;
    }

    public void setCreditCardExpiration(Date creditCardExpiration) {
        this.creditCardExpiration = creditCardExpiration;
    }

    public String getCreditCardCVVNumber() {
        return creditCardCVVNumber;
    }

    public void setCreditCardCVVNumber(String creditCardCVVNumber) {
        this.creditCardCVVNumber = creditCardCVVNumber;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public long getPlanID() {
        return planID;
    }

    public void setPlanID(long planID) {
        this.planID = planID;
    }
}
