package net.androidbootcamp.movietheatremonthlypass;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MovieDAO {

    //Movie
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie... movie);

    @Query("SELECT * FROM Movie")
    List<Movie> getMovies();

    @Query("SELECT * FROM Movie WHERE Movie.movieId = :movieID")
    List<Movie> getMovieById(long movieID);

    @Query("SELECT * FROM Movie WHERE Movie.id = :ID")
    List<Movie> getMovieById(String ID);

    @Delete
    void deleteMovies(Movie movie);

    //Users
    @Insert (onConflict = OnConflictStrategy.ROLLBACK)
    long[] insertUser(User... user);

    @Query("SELECT * FROM User")
    List<User> getUsers();

    @Query("SELECT * FROM User WHERE User.userID = :userId")
    List<User> getUserById(long userId);

    @Query("SELECT * FROM User WHERE User.email LIKE :userEmail")
    List<User> getUserByEmail(String userEmail);

    @Delete
    void deleteUser(User user);

    @Update
    void updateUser(User user);

    //SubscriptionPlan
    @Insert
    void insertPlan(SubscriptionPlan... plan);

    @Query("SELECT * FROM SubscriptionPlan")
    List<SubscriptionPlan> getPlans();

    @Query("SELECT * FROM SubscriptionPlan WHERE SubscriptionPlan.planID = :planId")
    List<SubscriptionPlan> getPlanById(int planId);


    //Payment
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertPayment(Payment... payment);

    @Query("SELECT * FROM Payment")
    List<Payment> getPayments();

    @Query("SELECT * FROM Payment WHERE Payment.paymentID = :paymentId")
    List<Payment> getPaymentById(long paymentId);

    @Query("SELECT Payment.*, SubscriptionPlan.* FROM Payment " +
            "INNER JOIN SubscriptionPlan ON Payment.planID = SubscriptionPlan.planID " +
            "INNER JOIN User ON Payment.userID = :userID " +
            "WHERE Payment.active = 1")
    List<Payment> getPaymentOptionByUserId(long userID);

    //TicketStatus
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTicketStatus(TicketStatus... ticketStatus);

    @Query("SELECT * FROM TicketStatus")
    List<TicketStatus> getTicketStatus();

    @Query("SELECT * FROM TicketStatus WHERE TicketStatus.name = :name")
    List<TicketStatus> getTicketStatusByName(String name);

    @Query("SELECT * FROM TicketStatus WHERE TicketStatus.ticketStatusID = :ticketStatusId")
    List<TicketStatus> getTicketStatusById(long ticketStatusId);

    //Ticket
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertTicket(Ticket... ticket);

    @Query("SELECT * FROM Ticket")
    List<Ticket> getTickets();

    @Query("SELECT * FROM Ticket WHERE Ticket.ticketID = :ticketId")
    List<Ticket> getTicketById(long ticketId);

    @Query("SELECT * FROM Ticket WHERE Ticket.userID = :userId ORDER BY Ticket.purchaseDate AND Ticket.ticketStatusID DESC")
    List<Ticket> getTicketsByUser(long userId);

    @Query("SELECT * FROM Ticket " +
            "WHERE Ticket.userID = :userId AND Ticket.ticketStatusID = :ticketStatusId ")
    List<Ticket> getTicketByUserAndStatus(long userId, long ticketStatusId);

    @Update
    void updateTicket(Ticket ticket);

    //MovieTheater
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovieTheater(MovieTheater... movieTheater);

    @Query("SELECT * FROM MovieTheater")
    List<MovieTheater> getMovieTheaters();

    @Query("SELECT * FROM MovieTheater WHERE MovieTheater.name LIKE :name")
    List<MovieTheater> getMovieTheaterByName(String name);

    @Query("SELECT * FROM MovieTheater WHERE MovieTheater.movieTheaterID = :movieTheaterId")
    List<MovieTheater> getMovieTheaterById(long movieTheaterId);

    @Delete
    void deleteMovieTheater(MovieTheater movieTheater);


    //DisplayTime
    @Insert
    void insertDisplayTime(DisplayTime... displayTime);

    @Query("SELECT * FROM DisplayTime")
    List<DisplayTime> getDisplayTimes();

    @Query("SELECT * FROM DisplayTime WHERE DisplayTime.displayTimeID = :displayTimeId")
    List<DisplayTime> getDisplayTimeById(long displayTimeId);

    //SessionType
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSessionType(SessionType... sessionType);

    @Query("SELECT * FROM SessionType")
    List<SessionType> getSessionTypes();

    @Query("SELECT * FROM SessionType WHERE SessionType.sessionTypeID = :sessionTypeId")
    List<SessionType> getSessionTypeById(long sessionTypeId);

    @Query("SELECT * FROM SessionType WHERE SessionType.type = :type")
    List<SessionType> getSessionTypeByType(String type);

    //MovieSession
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertMovieSession(MovieSession... movieSession);

    @Query("SELECT * FROM MovieSession")
    List<MovieSession> getMovieSessions();

    @Query("SELECT * FROM MovieSession WHERE MovieSession.movieSessionID = :movieSessionId")
    List<MovieSession> getMovieSessionById(long movieSessionId);

    @Query("SELECT MovieSession.* FROM MovieSession " +
            "INNER JOIN Movie ON MovieSession.movieID = Movie.movieId " +
            "WHERE Movie.id = :movieID")
    List<MovieSession> getMovieSessionByMovieId(String movieID);

    @Delete
    void deleteMovieSession(MovieSession... movieSession);



























}
