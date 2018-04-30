package net.androidbootcamp.movietheatremonthlypass;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import java.util.Date;

@Entity
public class User {

    @PrimaryKey (autoGenerate = true)
    private long userID;
    private String name;
    private Date birthdayDate;
    private String email;
    private String password;


    @Ignore
    public User(String name, Date birthdayDate, String email, String password) {
        this.name = name;
        this.birthdayDate = birthdayDate;
        this.email = email;
        this.password = password;
    }

    public User() {
    }


    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthdayDate() {
        return birthdayDate;
    }

    public void setBirthdayDate(Date birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
