package net.androidbootcamp.movietheatremonthlypass;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        PrefSingle.getInstance().initialize(getApplicationContext());
        final boolean userLogged = PrefSingle.getInstance().isLogged();
        final boolean databaseCreated = PrefSingle.getInstance().isDatabaseCreated();

        Thread task = new Thread(){
            @Override
            public void run(){
                if(!databaseCreated){
                    new DatabaseModule(getApplicationContext()).populateDatabase();
                    PrefSingle.getInstance().setDatabaseCreated(true);
                } else {
                    //Update local database with new movies - if exist
//                    new DatabaseModule(getApplicationContext()).deleteAllMovies();
                    new DatabaseModule(getApplicationContext()).updateMovieFromInternet();
                }

                finish();
                if(userLogged)
                    startActivity(new Intent(SplashActivity.this, MoviesListActivity.class));
                else
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));

            }
        };
        ((TextView) findViewById(R.id.textActivityResponse)).setText("Updating Database");
        task.start();

    }
}
