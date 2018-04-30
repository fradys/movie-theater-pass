package net.androidbootcamp.movietheatremonthlypass;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public abstract class Utility {

    public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    public static void showErrorMessage (Context context, boolean errorMessage){
        String message;
        if(errorMessage)
            message = "Please, fill all fields before continue.";
        else
            message = "User created successfully.";

        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void showMessage (Context context, String message){
        if(message == null || message.trim().length() == 0)
            return;

        SpannableStringBuilder text = new SpannableStringBuilder(message);
        text.setSpan(new RelativeSizeSpan(1.35f), 0, text.length(), 0);

        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }


}
