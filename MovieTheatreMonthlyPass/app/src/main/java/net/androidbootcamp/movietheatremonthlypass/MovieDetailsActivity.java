package net.androidbootcamp.movietheatremonthlypass;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MovieDetailsActivity extends AppCompatActivity {

    private static final float RATING_RATE = 2.0f;

    private Spinner spinnerMovieTheater;
    private EditText txtMovieDetailsDate;
    private Button btnChooseDate;
    private ArrayList<String[]> dataset = null;
    private SessionAdapter sessionAdapter = null;
    private List<DisplayTime> displayTimes = null;
    private Date receivedDate = null;
    private Button reserveTicket;
    private long planType = -1;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        //Test if the user is logged
        boolean logged = PrefSingle.getInstance().isLogged();
        if (!logged) {
            startActivity(new Intent(MovieDetailsActivity.this, MainActivity.class));
            finish();
        } else {
            //Get the User ID
            String email = PrefSingle.getInstance().userLogged();
            planType = PrefSingle.getInstance().getPlanType();
            userId = new DatabaseModule(getApplicationContext()).getUserByEmail(email).getUserID();
        }

        if(PrefSingle.getInstance().isPlanSet() == false){
            startActivity(new Intent(MovieDetailsActivity.this, PlansActivity.class));
            finish();
        }

        //Prevent keyboard appear when TextEdit gains focus
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //Sets the Toolbar
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        //Load the Movie Details
        loadMovieDetails();

        //Initialize the Spinner
        spinnerMovieTheater = findViewById(R.id.spinnerMovieDetailsTheater);
        initSpinner(spinnerMovieTheater);

        //Instantiate EditText
        txtMovieDetailsDate = findViewById(R.id.txtMovieDetailsDate);

        //Enable DatePicker
        final Calendar pickedDate = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                pickedDate.set(year, month, dayOfMonth);
                //Verify if date is correct
                long milliDate = pickedDate.getTimeInMillis();
                receivedDate = new Date(milliDate);

                if (receivedDate.compareTo(new Date()) < 0) {
                    Toast.makeText(MovieDetailsActivity.this, "Sorry, tickets must be reserved at least one day in advance.", Toast.LENGTH_LONG).show();
                    txtMovieDetailsDate.setText("");
                    return;
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String result = dateFormat.format(pickedDate.getTime());
                txtMovieDetailsDate.setText("");
                txtMovieDetailsDate.setText(result);
                loadMovieSessions(receivedDate);
            }
        }, pickedDate.get(Calendar.YEAR), pickedDate.get(Calendar.MONTH), pickedDate.get(Calendar.DAY_OF_MONTH));


        //Enable DatePicker action when button pressed
        btnChooseDate = findViewById(R.id.btnMovieDetailsChooseDate);
        btnChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        txtMovieDetailsDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        //Instantiate button and add listener
        reserveTicket = findViewById(R.id.btnReservation);
        reserveTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMovieSession();
            }
        });

    }

    private void loadMovieSessions(Date targetDate) {
        //Load movie sessions in the GridView
        DatabaseModule database = new DatabaseModule(getApplicationContext());
        GridView gridView = findViewById(R.id.gridButtonSession);
        gridView.setVisibility(View.VISIBLE);

        //Need - Date, day of week, start, end, type
        String dateFormatted = new SimpleDateFormat("dd/MM").format(targetDate);
        String dayOfWeek = new SimpleDateFormat("EE").format(targetDate);

        List<SessionType> sessionTypes = database.getSessionTypes();
        if (sessionTypes != null) {
            int index; //get random type
            int qnt; //get random size for the list

            displayTimes = database.getDisplayTimes();
            if (displayTimes != null) {
                dataset = new ArrayList<>(); // restart dataset

                int size = displayTimes.size();
                if (size > 0 && size <= 6)
                    qnt = size;
                else
                    qnt = 6; //Maximum quantity of buttons to be placed into the container

                //Randomize the number
                qnt = new Random().nextInt(qnt) + 1;

                for (int i = 0; i < qnt; i++) {

                    //Get a random session type - just to simulate different examples
                    index = new Random().nextInt(sessionTypes.size());
                    String typeName = sessionTypes.get(index).getType();
                    dataset.add(new String[]{
                            dateFormatted,
                            dayOfWeek,
                            displayTimes.get(i).getStartTime(),
                            displayTimes.get(i).getEndTime(),
                            typeName
                    });
                }
            }
        }

        if (dataset != null && dataset.size() > 0) {

            //Using custom Adapter
            sessionAdapter = new SessionAdapter(this, dataset);
            gridView.setAdapter(sessionAdapter);
        }
    }

    private void saveMovieSession() {
        String errorMessage = "";

        //If null, user didn't picked a date
        View selectedButton = ((sessionAdapter != null) ? sessionAdapter.getSelectedView() : null);

        //If null, user didn't select any movie session button
        if (selectedButton != null) {

            //Instantiate Database Object
            DatabaseModule database = new DatabaseModule(getApplicationContext());

            //Declare the new MovieSession Object
            MovieSession movieSession;

            //Need: MovieTheaterID, DisplayTimeID, MovieID, SessionTypeID

            //MovieTheaterID
            String movieTheaterName = spinnerMovieTheater.getSelectedItem().toString();
            MovieTheater movieTheater = database.getMovieTheaterByName(movieTheaterName).get(0);

            //DisplayTimeID
            DisplayTime displayTime = displayTimes.get(sessionAdapter.getSelectedPosition());

            //MovieID
            Movie movie = database.getMovieById(getSelectedMovie().getId()).get(0);

            //SessionTypeID
            String type = ((TextView) selectedButton.findViewById(R.id.txtButtonMovieType)).getText().toString();
            SessionType sessionType = database.getSessionTypeByName(type).get(0);

            //Insert into Database
            long insertedID;
            boolean typeSupported = true;

            if (planType == 1 && !sessionType.getType().equals(SessionType._2D)) {
                typeSupported = false;
            }

            //If user has selected a movie session accordingly with the type of his/her account
            if (typeSupported) {

                //Create a Movie Session Object
                movieSession = new MovieSession(
                        movieTheater.getMovieTheaterID(),
                        displayTime.getDisplayTimeID(),
                        movie.getMovieId(),
                        sessionType.getSessionTypeID(),
                        true);

                insertedID = database.saveMovieSession(movieSession);

                if (insertedID >= 0) {

                    //Prepare status and QRCode values
                    TicketStatus ticketStatus = database.getTicketStatusByName("CONFIRMED");
                    String qrCode = userId + ":" + insertedID + ":" + receivedDate.toString();

                    //Create and insert Ticket
                    boolean ticketInserted = database.saveTicket(
                            new Ticket(insertedID, receivedDate, ticketStatus.getTicketStatusID(), qrCode, userId));

                    if (ticketInserted) {

                        //Show success message
                        Utility.showMessage(MovieDetailsActivity.this, "Ticket reserved successfully. Enjoy your Movie!");

                        //Start new Intent
                        Intent intent = new Intent(MovieDetailsActivity.this, ReservationsActivity.class);
                        intent.putExtra("EXHIBITION_DATE", receivedDate.getTime());
                        startActivity(intent);
                        finish();
                    } else {
                        errorMessage = "Sorry, unable to create your ticket. Please, try again!";
                    }
                } else {
                    errorMessage = "Sorry, the selected movie session data is incorrect. Please, try again!";
                }
            } else {
                errorMessage = "Sorry, the selected session is incompatible with your account. Please, select another one or update your account in Settings -> Billing Information";
            }
        } else {
            errorMessage = ("Please, select a date before proceed.");
        }

        //Something went wrong, thus show the error message to user
        Utility.showMessage(MovieDetailsActivity.this, errorMessage);
    }


    private void loadMovieDetails() {
        Movie movieData = getSelectedMovie();
        if (movieData != null) {
            new DownloadImage(((ImageView) findViewById(R.id.imgDetailsMoviePoster))).execute(movieData.getImageUrl());
            ((TextView) findViewById(R.id.detailsMovieTitle)).setText(movieData.getTitle());
            ((TextView) findViewById(R.id.detailsMovieRuntime)).setText(movieData.getRuntime() + " min");
            double rating = movieData.getRating();
            float finalRating = Math.round(((float) rating / RATING_RATE));
            RatingBar ratingBar = findViewById(R.id.detailsRatingBar);
            ratingBar.setRating(finalRating);
        }
    }

    private void initSpinner(Spinner spinner) {

        //Get Movie Theaters data - AFTERWARDS GET FROM DATABASE
        List<MovieTheater> movieTheaters = new DatabaseModule(getApplicationContext()).getMovieTheaters();
        String[] allMovieTheaters = new String[movieTheaters.size()];
        int count = 0;
        for (MovieTheater item : movieTheaters) {
            allMovieTheaters[count] = item.getName();
            count++;
        }

        spinner.setAdapter(new ArrayAdapter<>(this, R.layout.spinner_small_item, allMovieTheaters));
    }

    private Movie getSelectedMovie() {
        Bundle extras = getIntent().getExtras();
        String movieID = "";
        if (extras != null) {
            movieID = extras.getString("MOVIE_ID");
        }
        return new MovieWebService().getMovieById(movieID);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        Call function to handle the clicked menu item
        if(item.getItemId() == R.id.item_logout)
            finish();
        MenuActionPerformed.menuItemAction(MovieDetailsActivity.this, item.getItemId());
        return super.onOptionsItemSelected(item);
    }
}
