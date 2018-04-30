package net.androidbootcamp.movietheatremonthlypass;

import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MoviesListActivity extends AppCompatActivity {

    private static final String ACTION = "Action";
    private static final String FAMILY = "Family";
    private static final int DAYS = 45; //Days which a movie still are considered new

    private ArrayList<Movie> movies;
    private RecyclerView recyclerViews[];
    private int recyclerIDs[];
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> dataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_list);

        if (PrefSingle.getInstance().isLogged() == false) {
            startActivity(new Intent(MoviesListActivity.this, MainActivity.class));
        }

        if(PrefSingle.getInstance().isPlanSet() == false){
            startActivity(new Intent(MoviesListActivity.this, PlansActivity.class));
            finish();
        }


        //Prevent keyboard to appear
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //Allow the services to be executed without time restriction, this code allows http services
        //to be executed indefinitely until the results are retrieved, this could slow the app
        //response but it's necessary to use this type of connection. Another option is to set the
        //http service to be executed within a separated Thread.
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Load Movies from source
        loadMoviesFromSource();


        //Sets the Toolbar
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        //Set the RecyclerView IDs
        recyclerIDs = new int[]{R.id.recycler_releases, R.id.recycler_action, R.id.recycler_family, R.id.recycler_all};
        recyclerViews = new RecyclerView[recyclerIDs.length];


        //Populate recycler views
        for (int i = 0; i < recyclerIDs.length; i++) {
            recyclerViews[i] = findViewById(recyclerIDs[i]);
            recyclerViews[i].setHasFixedSize(true);
            recyclerViews[i].setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            recyclerViews[i].setAdapter(adapter);
        }

        //Set individual Adapters
        {
            //New Released movies
            recyclerViews[0].setAdapter(new MainAdapter(getMovieList(null, false)));
            //Action
            recyclerViews[1].setAdapter(new MainAdapter(getMovieList(ACTION, false)));
            //Family
            recyclerViews[2].setAdapter(new MainAdapter(getMovieList(FAMILY, false)));
            //All On Display
            recyclerViews[3].setAdapter(new MainAdapter(getMovieList(null, true)));
        }

    }

    public void onClick(View view) {
        String movieID = ((TextView) view.findViewById(R.id.movieID)).getText().toString();
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("MOVIE_ID", movieID);
        startActivity(intent);
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
        MenuActionPerformed.menuItemAction(MoviesListActivity.this, item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    /**
     * String null, true == Get ALL the movies no matter what the genres are
     * String null, false == Get All New Released movies
     * String genre, true/false == Get movies of that specific genre ONLY.
     */
    private ArrayList<Movie> getMovieList(String genre, boolean allMovies) {
        if (movies == null)
            loadMoviesFromSource();
        ArrayList<Movie> movieDataset = new ArrayList<>();
        for (Movie movieData : movies) {
            if (genre == null && allMovies) {
                //Get ALL Movies
                movieDataset.add(movieData);
            } else if (genre == null) {
                //Get ALL Release movies - movies released from the past month
                if (isNowPlayingMovie(movieData))
                    movieDataset.add(movieData);
            } else if (movieData.getGenre().contains(genre)) {
                movieDataset.add(movieData);
            }
        }
        return movieDataset;
    }

    private boolean isNowPlayingMovie(Movie targetMovie) {

        if (targetMovie == null)
            return false;

        Date release = targetMovie.getReleaseDate();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -DAYS);

        return (release.after(calendar.getTime()));
    }

    private void loadMoviesFromSource() {
        if (movies != null)
            return;

        //Update movies from internet
        movies = new MovieWebService().getMoviesInTheater();
    }


}
