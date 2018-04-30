package net.androidbootcamp.movietheatremonthlypass;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.journeyapps.barcodescanner.Util;

import java.util.Calendar;
import java.util.Date;

public class ProfileActivity extends AppCompatActivity {

    private EditText txtEmail, txtProfileName, txtPassCurrent, txtPassOne, txtPassTwo;

    private DatePicker dateProfileBirthday;

    private Button btnSaveProfile;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Prevent keyboard to appear
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //Set the Toolbar
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        if(!PrefSingle.getInstance().isLogged()){
            startActivity(new Intent(ProfileActivity.this ,MainActivity.class));
        } else {
            String email = PrefSingle.getInstance().userLogged();
            user = new DatabaseModule(getApplicationContext()).getUserByEmail(email);
        }

        txtEmail = findViewById(R.id.txtEmail);
        txtProfileName = findViewById(R.id.txtProfileName);
        txtPassCurrent = findViewById(R.id.txtPassCurrent);
        txtPassOne = findViewById(R.id.txtPassOne);
        txtPassTwo = findViewById(R.id.txtPassTwo);

        dateProfileBirthday = findViewById(R.id.dateProfileBirthday);

        btnSaveProfile = findViewById(R.id.btnSaveProfile);

        //Load information
        loadInformation();

        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });

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
        MenuActionPerformed.menuItemAction(ProfileActivity.this, item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    private void saveProfile(){

        if(user == null){
            Utility.showMessage(ProfileActivity.this, "Sorry, something went wrong, please try again!");
            return;
        }

        boolean updated = false;
        boolean updatePass = false;
        String name = txtProfileName.getText().toString().trim();
        String passCurrent =  txtPassCurrent.getText().toString().trim();
        String passOne = txtPassOne.getText().toString().trim();
        String passTwo = txtPassTwo.getText().toString().trim();

        Date birthday = Utility.getDateFromDatePicker(dateProfileBirthday);

        if(passOne.length() > 0 || passTwo.length() > 0)
            updatePass = true;
        else
            updatePass = false;

        if(!passCurrent.equals(user.getPassword())){
            Utility.showMessage(ProfileActivity.this, "Please, type your password.");

        } else if((updatePass && (!passOne.equals(passTwo))) || name.length() == 0){
            Utility.showMessage(ProfileActivity.this, "Your name should not be blank and your new password must be typed in both boxes, please try again!");
        } else {
            user.setBirthdayDate(birthday);
            user.setName(name);
            if(updatePass)
                user.setPassword(passOne);
            updated = new DatabaseModule(getApplicationContext()).updateUser(user);
        }

        if(updated){
            Utility.showMessage(ProfileActivity.this, "Information updated successfully");

            //Update information on screen;
            loadInformation();
        }
    }

    private void loadInformation(){

        if(user == null)
            return;

        txtEmail.setText(user.getEmail());
        txtProfileName.setText(user.getName());

        //Get Birthday date
        Date birthday = user.getBirthdayDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(birthday);

        //Set the data into date Picker
        dateProfileBirthday.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

    }
}
