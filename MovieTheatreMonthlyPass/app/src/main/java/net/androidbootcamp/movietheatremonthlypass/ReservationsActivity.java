package net.androidbootcamp.movietheatremonthlypass;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.journeyapps.barcodescanner.Util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ReservationsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReservationAdapter adapter;
    private ArrayList<String[]> data;
    private Button btnCancel;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        //Test if the user is logged
        boolean logged = PrefSingle.getInstance().isLogged();
        if(!logged){
            startActivity(new Intent(ReservationsActivity.this, MainActivity.class));
            finish();
        } else {
            //Get the User ID
            String email = PrefSingle.getInstance().userLogged();
            userId = new DatabaseModule(getApplicationContext()).getUserByEmail(email).getUserID();
        }

        //Sets the Toolbar
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        //Populate with Ticket data
        data = getTicketData();

        //Initialize the recycler view
        recyclerView = findViewById(R.id.recycler_ticket);

        //Set the adapter
        adapter = new ReservationAdapter(data, ReservationsActivity.this);

//        //Configure the recycler views
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
        new PagerSnapHelper().attachToRecyclerView(recyclerView);

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
        MenuActionPerformed.menuItemAction(ReservationsActivity.this, item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    private ArrayList<String[]> getTicketData(){

        //Need - Poster img, ticket id, movie title, movie theater name, purchase date, start end date, ticket status
        //qrcode info

        //Instantiate database
        DatabaseModule database = new DatabaseModule(getApplicationContext());

        //Get Tickets from logged user
        List<Ticket> allTickets = database.getTicketsByUser(userId);
        ArrayList<String[]> ticketData = new ArrayList<>();

        if(allTickets != null && allTickets.size() > 0){

            String imgURL, ticketID, movieTitle, movieTheaterName, purchaseDate, startDate, endDate, ticketStatus,
                    QRCodeInfo;

            for(Ticket item : allTickets){

                MovieSession movieSession = database.getMovieSessionById(item.getMovieSessionID()).get(0);
                Movie tempMovie = database.getMovieById(movieSession.getMovieID()).get(0);
                MovieTheater tempMovieTheater = database.getMovieTheaterById(movieSession.getMovieTheaterID()).get(0);
                DisplayTime tempDisplayTime = database.getDisplayTimeById(movieSession.getDisplayTimeID()).get(0);
                TicketStatus tempTicketStatus = database.getTicketStatusById(item.getTicketStatusID()).get(0);

                //Populate variables
                imgURL = tempMovie.getImageUrl();
                ticketID = String.valueOf(item.getTicketID());
                movieTitle = tempMovie.getTitle();
                movieTheaterName = tempMovieTheater.getName();
                purchaseDate = new SimpleDateFormat("dd/MM/yyyy").format(item.getPurchaseDate());
                startDate = tempDisplayTime.getStartTime();
                endDate = tempDisplayTime.getEndTime();
                ticketStatus = tempTicketStatus.getName();
                QRCodeInfo = item.getQRCodeInfo();

                //Create an array and add to the main ArrayList
                ticketData.add(new String[] {
                        imgURL, ticketID, movieTitle, movieTheaterName, purchaseDate, startDate,
                        endDate, ticketStatus, QRCodeInfo
                });
            }
        }

        return ticketData;
    }

}
