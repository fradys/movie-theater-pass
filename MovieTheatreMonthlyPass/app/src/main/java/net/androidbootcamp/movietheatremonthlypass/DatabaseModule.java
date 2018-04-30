package net.androidbootcamp.movietheatremonthlypass;


import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseModule {

    private final MoviePassDatabase moviePassDatabase;

    public DatabaseModule(Context context){

        moviePassDatabase = Room.databaseBuilder(context, MoviePassDatabase.class, "moviepassdb")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries().build();
    }


    public boolean saveUser(User user){
        if(user == null)
            return false;

        long[] lastID = moviePassDatabase.movieDAO().insertUser(user);

        if(lastID == null || lastID.length == 0)
            return false;

        return true;
    }

    public long saveMovieSession(MovieSession movieSession){
        long result = -1;

        if(movieSession == null)
            return result;

        long[] lastID = moviePassDatabase.movieDAO().insertMovieSession(movieSession);

        if(lastID != null && lastID.length > 0)
            result = lastID[0];

        return result;
    }

    public boolean saveTicket(Ticket ticket){
        if(ticket == null)
            return false;

        long[] lastID = moviePassDatabase.movieDAO().insertTicket(ticket);

        if(lastID == null || lastID.length == 0)
            return false;

        return true;
    }

    public ArrayList<User> getUsers(){
        List<User> allUsersList =  moviePassDatabase.movieDAO().getUsers();
        ArrayList<User> allUsers = null;
        if(allUsersList != null)
            allUsers.addAll(allUsersList);

        return allUsers;
    }

    public void deleteAllUsers(){
        List<User> users = moviePassDatabase.movieDAO().getUsers();
        if(users != null){
            for (User userItem : users) {
                moviePassDatabase.movieDAO().deleteUser(userItem);
            }
        }
    }

    public User getUserByEmail(String email){

        List<User> user = moviePassDatabase.movieDAO().getUserByEmail(email);

        if(user != null && user.size() > 0)
            return user.get(0);

        return null;
    }

    public boolean isUserLoginCorrect(String email, String pass){

        User user = getUserByEmail(email);
        if(user != null && user.getPassword().equals(pass))
            return true;

        return false;
    }

    public boolean savePayment(Payment payment){

        if(payment == null)
            return false;

        long[] lastID = moviePassDatabase.movieDAO().insertPayment(payment);

        if(lastID == null || lastID.length == 0)
            return false;

        return true;
    }

    public boolean updateUser(User user){
        if(user == null)
            return false;

        moviePassDatabase.movieDAO().updateUser(user);

        return true;
    }

    public List<MovieTheater> getMovieTheaters(){
        return moviePassDatabase.movieDAO().getMovieTheaters();
    }

    public List<MovieTheater> getMovieTheaterByName(String name){
        return moviePassDatabase.movieDAO().getMovieTheaterByName(name);
    }

    public List<MovieSession> getMovieSessionByMovieId(String movieID){
        return moviePassDatabase.movieDAO().getMovieSessionByMovieId(movieID);
    }

    public List<DisplayTime> getDisplayTimeById(long displayTimeID){
        return moviePassDatabase.movieDAO().getDisplayTimeById(displayTimeID);
    }

    public List<DisplayTime> getDisplayTimes(){
        return moviePassDatabase.movieDAO().getDisplayTimes();
    }

    public String getSessionTypeById(long sessionTypeID){
        if(sessionTypeID < 0)
            return null;

        List<SessionType> allSessionType = moviePassDatabase.movieDAO().getSessionTypeById(sessionTypeID);

        if(allSessionType == null || allSessionType.size() == 0)
            return null;

        return allSessionType.get(0).getType();
    }

    public List<SessionType> getSessionTypes(){
        return moviePassDatabase.movieDAO().getSessionTypes();
    }

    public List<SessionType> getSessionTypeByName(String type){
        return moviePassDatabase.movieDAO().getSessionTypeByType(type);
    }

    public void deleteAllMovieSession(MovieSession... movieSession){
        moviePassDatabase.movieDAO().deleteMovieSession(movieSession);
    }

    public List<MovieSession> getMovieSessionById(long movieSessionID){
        return moviePassDatabase.movieDAO().getMovieSessionById(movieSessionID);
    }

    public List<MovieSession> getMovieSessions(){
        return moviePassDatabase.movieDAO().getMovieSessions();
    }

    public List<Movie> getMovies(){
        return moviePassDatabase.movieDAO().getMovies();
    }

    public List<Movie> getMovieById(long movieID){
        return moviePassDatabase.movieDAO().getMovieById(movieID);
    }

    public List<MovieTheater> getMovieTheaterById(long movieTheaterID){
        return moviePassDatabase.movieDAO().getMovieTheaterById(movieTheaterID);
    }

    public List<Movie> getMovieById(String ID){
        return moviePassDatabase.movieDAO().getMovieById(ID);
    }

    public List<TicketStatus> getTicketStatusById(long ticketStatusID){
        return moviePassDatabase.movieDAO().getTicketStatusById(ticketStatusID);
    }

    public void deleteAllMovies(){
        List<Movie> movies = moviePassDatabase.movieDAO().getMovies();
        if(movies != null){
            for(Movie item : movies)
                moviePassDatabase.movieDAO().deleteMovies(item);
        }
    }

    public List<Ticket> getTicketsByUser(long userID){
        return moviePassDatabase.movieDAO().getTicketsByUser(userID);
    }


    public TicketStatus getTicketStatusByName(String name){
        if(name == null || name.trim().length() == 0)
            return null;

        List<TicketStatus> allTicketStatus = moviePassDatabase.movieDAO().getTicketStatusByName(name);

        if(allTicketStatus == null || allTicketStatus.size() == 0)
            return null;

        return allTicketStatus.get(0);
    }

    public boolean isTicketReservedForUser(long userID){
        if(userID < 0)
            return false;
        final long CONFIRMED = 1; //"Confirmed" Ticket Status ID

        List<Ticket> allTickets = moviePassDatabase.movieDAO().getTicketByUserAndStatus(userID, CONFIRMED);

        if(allTickets == null || allTickets.size() == 0)
            return false;

        return true;
    }

    public void updateTicket(Ticket ticket){
        if(ticket == null)
            return;

        moviePassDatabase.movieDAO().updateTicket(ticket);
    }

    public List<Ticket> getTicketById(long ticketID){
        return moviePassDatabase.movieDAO().getTicketById(ticketID);
    }


    public void updateMovieFromInternet(){

        ArrayList<Movie> movies = new MovieWebService().getMoviesInTheater();
        if(movies == null || movies.size() == 0)
            return;

        for (Movie item : movies) {
            moviePassDatabase.movieDAO().insertMovie(item);
        }
    }


    //Populate Database - Initial Script
    public void populateDatabase() {

        //Create Monthly Plans
        SubscriptionPlan[] plans = new SubscriptionPlan[]{
                new SubscriptionPlan("Basic", "2D Movies only", "", 14.99),
                new SubscriptionPlan("Premium", "2D, 3D, IMAX Available", "", 21.99)

        };
        moviePassDatabase.movieDAO().insertPlan(plans);

        //Get movies from WEB Service
        updateMovieFromInternet();

        //Create Session Type
        SessionType[] sessionTypes = new SessionType[]{
                new SessionType("2D", "2D"),
                new SessionType("3D", "3D"),
                new SessionType("IMAX", "IMAX")
        };
        moviePassDatabase.movieDAO().insertSessionType(sessionTypes);

        //Create Ticket Status
        TicketStatus[] ticketStatus = new TicketStatus[]{
                new TicketStatus("CONFIRMED", "Ticket is reserved waiting user to pick it up", 0.0),
                new TicketStatus("CONSUMED", "User has attended the session and hopefully enjoyed the movie", 0.0),
                new TicketStatus("NO-SHOW", "User reserved but did not show up to retrieve it, may have additional fees", 5.99),
                new TicketStatus("CANCELED", "User managed to cancel the reservation on time, NO additional fees", 0.0)
        };
        moviePassDatabase.movieDAO().insertTicketStatus(ticketStatus);

        //Create Movie Theaters
        MovieTheater[] movieTheaters = new MovieTheater[]{
                new MovieTheater("Cineplex Odeon International Village Cinemas", "New Village", 15),
                new MovieTheater("Cineplex Odeon Park and Tilford Cinemas", "Coquitlam", 14),
                new MovieTheater("Landmark Cinemas New Westminster", "New Westminster", 10),
                new MovieTheater("Cineplex Cinemas Strawberry Hill", "Strawberry Hill", 10),
                new MovieTheater("Cineplex Cinemas Coquitlam VIP", "Coquitlam", 21),
                new MovieTheater("Cineplex Cinemas Metropolis", "Metrotown", 18),
                new MovieTheater("Cineplex Cinemas Langley", "Langley", 17),
                new MovieTheater("Landmark Cinemas", "Burnaby", 19)
        };
        moviePassDatabase.movieDAO().insertMovieTheater(movieTheaters);


        //Create Display Time
        DisplayTime[] displayTimes = new DisplayTime[]{
                new DisplayTime("10:00 AM", "11:30 AM", true),
                new DisplayTime("11:00 AM", "12:30 PM", true),
                new DisplayTime("12:10 PM", "01:50 PM", true),
                new DisplayTime("01:55 PM", "03:25 PM", true),
                new DisplayTime("01:30 PM", "03:10 PM", true),
                new DisplayTime("02:15 PM", "03:50 PM", true),
                new DisplayTime("02:50 PM", "04:00 PM", true),
                new DisplayTime("04:10 PM", "05:55 PM", true),
                new DisplayTime("04:40 PM", "06:00 PM", true),
                new DisplayTime("05:00 PM", "06:40 PM", true),
                new DisplayTime("06:10 PM", "08:30 PM", true),
                new DisplayTime("07:20 PM", "08:50 PM", true)
        };
        moviePassDatabase.movieDAO().insertDisplayTime(displayTimes);

    }


}
