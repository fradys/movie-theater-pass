package net.androidbootcamp.movietheatremonthlypass;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class SubscriptionPlan {

    @PrimaryKey(autoGenerate = true)
    private long planID;
    private String name;
    private String Description;
    private String type;
    private double price;

    @Ignore
    public SubscriptionPlan(String name, String description, String type, double price) {
        this.name = name;
        Description = description;
        this.type = type;
        this.price = price;
    }

    public SubscriptionPlan() {
    }


    public long getPlanID() {
        return planID;
    }

    public void setPlanID(long planID) {
        this.planID = planID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
