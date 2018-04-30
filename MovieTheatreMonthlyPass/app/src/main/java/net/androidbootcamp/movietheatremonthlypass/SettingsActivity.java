package net.androidbootcamp.movietheatremonthlypass;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private final String SEND_MESSAGE_ON;
    private final String SEND_MESSAGE_OFF;


    {
        SEND_MESSAGE_ON = "You will receive promotional emails from now on.";
        SEND_MESSAGE_OFF = "We will no longer send you promotional emails.";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Set the back button
        final Button btnBack = findViewById(R.id.btnSettingsOk);
        final Switch switchEmail = findViewById(R.id.switch_email);

        if(!PrefSingle.getInstance().isLogged()){
            startActivity(new Intent(SettingsActivity.this ,MainActivity.class));
        } else {
            boolean checked = PrefSingle.getInstance().getSendEmail();
            switchEmail.setChecked(checked);
        }


        //Set the toolbar
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);


        switchEmail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                boolean checked = switchEmail.isChecked();
                PrefSingle.getInstance().setSendEmail(checked);
                if (checked) {
                    showMessage(SEND_MESSAGE_ON);
                } else {
                    showMessage(SEND_MESSAGE_OFF);
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
    }

    private void showMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
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
        MenuActionPerformed.menuItemAction(SettingsActivity.this, item.getItemId());
        return super.onOptionsItemSelected(item);
    }

}
