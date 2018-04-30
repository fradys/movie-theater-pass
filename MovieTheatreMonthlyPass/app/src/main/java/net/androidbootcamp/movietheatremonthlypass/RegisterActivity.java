package net.androidbootcamp.movietheatremonthlypass;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.TtsSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.journeyapps.barcodescanner.Util;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

public class RegisterActivity extends AppCompatActivity {

    EditText fullName,
             userEmail,
             passwordOne,
             passwordTwo;

    DatePicker dateBirthday;
    Button btnMember;

    /**
     * NUM_YEARS Quantity of years to consider inside the Year Spinner control
     */
    private final static int NUM_YEARS = 16;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Initialize components variables
        fullName = findViewById(R.id.registerUserName);
        userEmail = findViewById(R.id.registerUserEmail);
        passwordOne = findViewById(R.id.txtPassOne);
        passwordTwo = findViewById(R.id.txtPassTwo);
        dateBirthday = findViewById(R.id.dateBirthday);

        //TEST - Delete all movie Sessions
//        new DatabaseModule(getApplicationContext()).deleteAllMovieSession();


        //Set the date picker a minimum birthday date
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -NUM_YEARS);
        dateBirthday.setMaxDate(calendar.getTimeInMillis());


        //final CheckBox checkPrivacy = findViewById(R.id.checkPrivacy);
        btnMember = findViewById(R.id.btnMember);
        btnMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUser();
            }
        });


    }


    public void onCheckboxClicked(View view){
        boolean check = ((CheckBox)view).isChecked();

        switch(view.getId()){
            case R.id.checkPrivacy:
                btnMember.setEnabled(check);
                break;
        }
    }

    private void saveUser(){
        //Prepare data to be saved in database
        String userName = fullName.getText().toString().trim();
        String email = userEmail.getText().toString().trim();
        String passOne = passwordOne.getText().toString().trim();
        String passTwo = passwordTwo.getText().toString().trim();
        Date birthday = Utility.getDateFromDatePicker(dateBirthday);

        //Test if user already has an account
        User tempUser = new DatabaseModule(getApplicationContext()).getUserByEmail(email);
        if(tempUser != null){
            Utility.showMessage(RegisterActivity.this, "Sorry, this email is already registered. Please, sign-in to continue.");
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            finish();
            return;
        }

        if(userName.length() == 0 || email.length() == 0 || passOne.length() == 0 || passTwo.length() == 0 ){
            String message;
            if(!passOne.equals(passTwo)){
                message = "Passwords does not match, please type again.";
            } else {
                message = "Please, fill all fields before continue.";
            }

            Utility.showMessage(RegisterActivity.this, message);
            return;
        }

        boolean inserted = new DatabaseModule(getApplicationContext()).saveUser(new User(userName, birthday, email, passOne));

        if(inserted){
            //Show message to user
            Utility.showMessage(RegisterActivity.this, "Your registration was successfully completed. Thank You!");

            //Add user information to SharedPreferences
            PrefSingle.getInstance().setLogged(true);
            PrefSingle.getInstance().setUserLogged(email);

            //Instantiate new Activity
            startActivity(new Intent(RegisterActivity.this, PlansActivity.class));
            finish();
        }
    }


}
