package net.androidbootcamp.movietheatremonthlypass;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.journeyapps.barcodescanner.Util;

import java.util.Date;

public class CreditCardActivity extends AppCompatActivity {

    private EditText txtCreditCardNumber, txtCreditCardHolderName, txtCreditCardCVVNumber;

    private DatePicker dateCreditCardExpiration;

    private Button btnSaveCreditCard;

    private ImageView imgCreditCard, imgCVVNumber;

    private boolean cvvRequired = false;

    private String creditCardBrand = "";
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);

        //Prevent keyboard to appear
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //Sets the Toolbar
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        //Initialize variables
        txtCreditCardNumber = findViewById(R.id.txtCreditCardNumber);
        txtCreditCardHolderName = findViewById(R.id.txtCreditCardHolderName);
        txtCreditCardCVVNumber = findViewById(R.id.txtCreditCardCVVNumber);
        dateCreditCardExpiration = findViewById(R.id.dateCreditCardExpiration);
        btnSaveCreditCard = findViewById(R.id.btnSaveCreditCard);
        imgCreditCard = findViewById(R.id.imgCreditCard);
        imgCVVNumber = findViewById(R.id.imgCVVNumber);

        //Set the user's name
        String email = PrefSingle.getInstance().userLogged();
        user = new DatabaseModule(getApplicationContext()).getUserByEmail(email);
        if(user != null){
            txtCreditCardHolderName.setText(user.getName());
        } else {
            //User not logged in - send user to homepage
            startActivity(new Intent(CreditCardActivity.this, MainActivity.class));
            finish();
        }


        //Set the credit card validation
        txtCreditCardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                validateCreditCard();
            }
        });

        btnSaveCreditCard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                saveCreditCard();
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
        MenuActionPerformed.menuItemAction(CreditCardActivity.this, item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    private void saveCreditCard(){
        String cardNumber = txtCreditCardNumber.getText().toString().trim();
        String holderName = txtCreditCardHolderName.getText().toString().trim();
        String cvvNumber = txtCreditCardCVVNumber.getText().toString().trim();
        Date expirationDate = Utility.getDateFromDatePicker(dateCreditCardExpiration);

        if(cardNumber.length() == 0 || holderName.length() == 0 || ( cvvRequired && cvvNumber.length() == 0)){
            Utility.showMessage(CreditCardActivity.this, "Sorry, the information provided is not correct, please try again!");
            return;
        }

        long userID = 0;
        System.out.println("user: " + user.getName());
        if(user != null)
            userID = user.getUserID();

        //Get the plan option
        Bundle extras = getIntent().getExtras();
        int plan;
        long planID = 1;
        if(extras != null){
            plan = extras.getInt("PLAN");
            planID = plan;
        }

        Payment payment = new Payment( creditCardBrand, cardNumber, holderName,
                expirationDate, cvvNumber, true, userID, planID);

        boolean inserted = new DatabaseModule(getApplicationContext()).savePayment(payment);

        if(inserted){
            PrefSingle.getInstance().setPlanType(planID);
            PrefSingle.getInstance().setPlan(true);

            startActivity(new Intent(CreditCardActivity.this, MoviesListActivity.class));
            finish();
        } else {
            Utility.showMessage(CreditCardActivity.this, "Sorry, something went wrong. Please, try again!");
        }
    }

    private void validateCreditCard(){
        String cardNumber = txtCreditCardNumber.getText().toString().trim();

        //Credit Card Codes - FICTIONAL
        if(cardNumber.length() == 1)
        {
            btnSaveCreditCard.setEnabled(true);
            String digit = cardNumber.substring(0, 1);
            switch (digit){
                case "0":
                case "1":
                    creditCardBrand = "Mastercard";
                    imgCreditCard.setImageResource(R.drawable.mastercard);
                    imgCVVNumber.setVisibility(ImageView.VISIBLE);
                    txtCreditCardCVVNumber.setEnabled(true);
                    cvvRequired = true;
                    break;
                case "2":
                case "3":
                case "4":
                    creditCardBrand = "Visa";
                    imgCreditCard.setImageResource(R.drawable.visa);
                    imgCVVNumber.setVisibility(ImageView.VISIBLE);
                    txtCreditCardCVVNumber.setEnabled(true);
                    cvvRequired = true;
                    break;
                case "5":
                case "6":
                    creditCardBrand = "Discover";
                    imgCreditCard.setImageResource(R.drawable.discover);
                    imgCVVNumber.setVisibility(ImageView.GONE);
                    txtCreditCardCVVNumber.setEnabled(false);
                    cvvRequired = false;
                    break;
                case "7":
                case "8":
                case "9":
                    creditCardBrand = "American Express";
                    imgCreditCard.setImageResource(R.drawable.americanexpress);
                    imgCVVNumber.setVisibility(ImageView.GONE);
                    txtCreditCardCVVNumber.setEnabled(false);
                    txtCreditCardCVVNumber.setText("");
                    cvvRequired = false;
                    break;
                default:
                    creditCardBrand = "";
                    imgCreditCard.setImageResource(R.drawable.blank);
                    imgCVVNumber.setVisibility(ImageView.GONE);
                    txtCreditCardCVVNumber.setEnabled(false);
                    txtCreditCardCVVNumber.setText("");
                    cvvRequired = false;
                    btnSaveCreditCard.setEnabled(false);
                    Utility.showMessage(CreditCardActivity.this, "Credit Card Number Invalid, please type again.");
            }

        } else {
            return;
        }
    }


}
