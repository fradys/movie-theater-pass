package net.androidbootcamp.movietheatremonthlypass;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btnInvite, btnEnter;
    private EditText txtLogin, txtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Check if is logged
//        PrefSingle.getInstance().initialize(getApplicationContext());
//        System.out.println("is logged: " + PrefSingle.getInstance().isLogged());
        if(PrefSingle.getInstance().isLogged()){
            System.out.println("User: " + PrefSingle.getInstance().userLogged());
            redirect(true);
        }

        //Setting text button as underline
        btnInvite = findViewById(R.id.btnInvite);
        btnEnter = findViewById(R.id.btnEnter);
        txtLogin = findViewById(R.id.loginEmail);
        txtPass = findViewById(R.id.loginPass);

        btnInvite.setPaintFlags(btnInvite.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        btnInvite.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                redirect(false);

            }
        });

        btnEnter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                validateLogin();
            }
        });
    }

    private void redirect(boolean toMoviesList){
        Class<?> target;
        if(toMoviesList)
            target = MoviesListActivity.class;
        else
            target = RegisterActivity.class;

        startActivity(new Intent(MainActivity.this, target));
        finish();
    }

    private void validateLogin(){
        final String email = txtLogin.getText().toString().trim();
        final String pass = txtPass.getText().toString().trim();

        boolean loginCorrect = new DatabaseModule(getApplicationContext()).isUserLoginCorrect(email, pass);
        if(loginCorrect){
            PrefSingle.getInstance().setLogged(true);
            PrefSingle.getInstance().setUserLogged(email);
            redirect(true);
        } else {
            Utility.showMessage(MainActivity.this, "Sorry, the information provided is not correct, please try again!");
        }
    }

}
