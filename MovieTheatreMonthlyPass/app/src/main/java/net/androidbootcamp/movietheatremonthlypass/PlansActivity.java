package net.androidbootcamp.movietheatremonthlypass;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class PlansActivity extends AppCompatActivity {

    private boolean btnOneClicked = false;
    private boolean btnTwoClicked = false;
    private int selectedPlan;
    private LinearLayout btnPlanOne;
    private LinearLayout btnPlanTwo;
    private Button btnSavePlan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans);

        //Set the Toolbar
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        //Get the layout an treat it like a button
        btnPlanOne = findViewById(R.id.btnPlanOne);
        btnPlanTwo = findViewById(R.id.btnPlanTwo);
        btnSavePlan = findViewById(R.id.btnSavePlan);

        btnPlanOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonItemClicked(btnPlanOne);
            }
        });

        btnPlanTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonItemClicked(btnPlanTwo);
            }
        });

        btnSavePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlansActivity.this, CreditCardActivity.class);
                intent.putExtra("PLAN", selectedPlan);
                startActivity(intent);
            }
        });
    }

    private void buttonItemClicked(LinearLayout buttonPressed){
        int normal = R.drawable.rounded_edge_normal;
        int pressed = R.drawable.rounded_edge_pressed;

        //Clean the background of all buttons
        btnPlanOne.setBackgroundResource(normal);
        btnPlanTwo.setBackgroundResource(normal);

        //Set the pressed button with the "pressed" background
        buttonPressed.setBackgroundResource(pressed);

        //Activate the "Next" Button, after the first click, it will always be a button pressed.
        btnSavePlan.setEnabled(true);

        if(buttonPressed.equals(btnPlanOne)){
            btnOneClicked = true;
            btnTwoClicked = false;
            selectedPlan = 1;
        } else {
            btnOneClicked = false;
            btnTwoClicked = true;
            selectedPlan = 2;
        }
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
        MenuActionPerformed.menuItemAction(PlansActivity.this, item.getItemId());
        return super.onOptionsItemSelected(item);
    }
}
