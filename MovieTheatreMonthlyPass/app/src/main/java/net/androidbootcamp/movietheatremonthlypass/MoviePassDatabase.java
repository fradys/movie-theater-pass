package net.androidbootcamp.movietheatremonthlypass;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

@Database(entities =
        {
                Movie.class,
                User.class,
                SubscriptionPlan.class,
                Payment.class,
                TicketStatus.class,
                Ticket.class,
                MovieTheater.class,
                DisplayTime.class,
                SessionType.class,
                MovieSession.class
        }, version = 3)

@TypeConverters({Converters.class})
public abstract class MoviePassDatabase extends RoomDatabase {

    public abstract MovieDAO movieDAO();
}
